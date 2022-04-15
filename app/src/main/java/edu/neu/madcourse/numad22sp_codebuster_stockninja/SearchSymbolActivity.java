package edu.neu.madcourse.numad22sp_codebuster_stockninja;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;
import edu.neu.madcourse.numad22sp_codebuster_stockninja.controllers.OnClickListener;
import edu.neu.madcourse.numad22sp_codebuster_stockninja.controllers.SearchResultAdapter;
import edu.neu.madcourse.numad22sp_codebuster_stockninja.models.SearchResult;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;

public class SearchSymbolActivity extends AppCompatActivity {

  private static final String KEY_OF_SYMBOL_INPUT = "KEY_OF_SYMBOL_INPUT";
  private static final String KEY_OF_SEARCH_RESULT = "KEY_OF_SEARCH_RESULT";
  private static final String NUM_OF_SEARCH_RESULT = "NUM_OF_SEARCH_RESULT";
  private EditText inputSymbol;
  private ImageButton btnSearchSymbol;
  private ProgressBar vSearchStatus;
  private RecyclerView rvSearchResult;
  private final Handler handler = new Handler(Looper.getMainLooper());
  private SearchResultAdapter searchResultAdapter;
  private List<SearchResult> searchResults = new ArrayList<>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    super.setContentView(R.layout.activity_search_symbol);

    this.inputSymbol = super.findViewById(R.id.inputSymbol);
    this.btnSearchSymbol = super.findViewById(R.id.btnSearchSymbol);
    this.vSearchStatus = super.findViewById(R.id.vSearchStatus);
    this.rvSearchResult = super.findViewById(R.id.rvSearchResult);

    this.init(savedInstanceState);
    this.createRecyclerView();

    this.btnSearchSymbol.setOnClickListener(v -> {
      SearchSymbolActivity.this.vSearchStatus.setVisibility(View.VISIBLE);
      SearchSymbolTask task = new SearchSymbolTask();
      new Thread(task).start();
    });
  }

  private void init(Bundle savedInstanceState) {
    if (savedInstanceState == null || !savedInstanceState.containsKey(KEY_OF_SYMBOL_INPUT) || this.searchResults.size() > 0) {
      return;
    }
    this.inputSymbol.setText(savedInstanceState.getString(KEY_OF_SYMBOL_INPUT));
    int numSearchResults = savedInstanceState.getInt(NUM_OF_SEARCH_RESULT);
    for (int i = 0; i < numSearchResults; i++) {
      String symbol = savedInstanceState.getString(KEY_OF_SEARCH_RESULT + i + "0");
      String companyName = savedInstanceState.getString(KEY_OF_SEARCH_RESULT + i + "1");
      this.searchResults.add(new SearchResult(symbol, companyName));
    }
  }

  private void createRecyclerView() {
    LayoutManager layoutManger = new LinearLayoutManager(this);
    OnClickListener onClickListener = (position) -> {
      String symbol = this.searchResults.get(position).getSymbol();
      Intent intent = new Intent(this, StockInfoActivity.class);
      intent.putExtra("searchSymbol", symbol);
      startActivity(intent);
      this.searchResultAdapter.notifyItemChanged(position);
    };

    this.searchResultAdapter = new SearchResultAdapter(this.searchResults);
    this.searchResultAdapter.setOnClickListener(onClickListener);
    this.rvSearchResult.setHasFixedSize(true);
    this.rvSearchResult.setLayoutManager(layoutManger);
    this.rvSearchResult.setAdapter(this.searchResultAdapter);
  }

  private void addSearchResult(SearchResult newSearchResult) {
    this.searchResults.add(0, newSearchResult);
    this.searchResultAdapter.notifyItemInserted(0);
  }

  private void clearSearchResults() {
    int numSearchResults = this.searchResults.size();
    this.searchResults.clear();
    this.searchResultAdapter.notifyItemRangeRemoved(0, numSearchResults);
  }

  @Override
  protected void onSaveInstanceState(@NonNull Bundle outState) {
    outState.putString(KEY_OF_SYMBOL_INPUT, this.inputSymbol.getText().toString());
    int numSearchResults = this.searchResults.size();
    outState.putInt(NUM_OF_SEARCH_RESULT, numSearchResults);
    for (int i = 0; i < numSearchResults; i++) {
      outState.putString(KEY_OF_SEARCH_RESULT + i + "0", this.searchResults.get(i).getSymbol());
      outState.putString(KEY_OF_SEARCH_RESULT + i + "1", this.searchResults.get(i).getCompanyName());
    }
    super.onSaveInstanceState(outState);
  }

  class SearchSymbolTask implements Runnable {

    static final String QUERY_URL_PREFIX = "https://www.alphavantage.co/query?function=SYMBOL_SEARCH&apikey=89RX1CEV53MN7GVU&keywords=";

    @Override
    public void run() {
      try {
        SearchSymbolActivity.this.clearSearchResults();

        URL url = new URL(this.generateQueryUrl());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setDoInput(true);
        connection.connect();

        InputStream inStream = connection.getInputStream();
        String response = this.convertStreamToString(inStream);

        JSONArray arrResults = new JSONObject(response).getJSONArray("bestMatches");

        for (int i = arrResults.length() - 1; i >= 0; i--) {
          JSONObject objResult = arrResults.getJSONObject(i);
          String region = objResult.getString("4. region");
          if (!region.equals("United States")) {
            continue;
          }
          String symbol = objResult.getString("1. symbol");
          String companyName = objResult.getString("2. name");
          SearchSymbolActivity.this.addSearchResult(new SearchResult(symbol, companyName));
        }
      } catch (Exception ignored) {
      }
      SearchSymbolActivity.this.handler.post(()-> SearchSymbolActivity.this.vSearchStatus.setVisibility(View.GONE));
    }

    private String generateQueryUrl() {
      StringBuilder builder = new StringBuilder(QUERY_URL_PREFIX);
      String symbol = SearchSymbolActivity.this.inputSymbol.getText().toString();
      builder.append(symbol);
      return builder.toString();
    }

    private String convertStreamToString(InputStream is) {
      Scanner s = new Scanner(is).useDelimiter("\\A");
      return s.hasNext() ? s.next() : "";
    }
  }
}