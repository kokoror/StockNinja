package edu.neu.madcourse.numad22sp_codebuster_stockninja;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;
import edu.neu.madcourse.numad22sp_codebuster_stockninja.controllers.TimeSeriesAdapter;
import edu.neu.madcourse.numad22sp_codebuster_stockninja.models.SearchResult;
import edu.neu.madcourse.numad22sp_codebuster_stockninja.models.TimeSeries;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;

public class FetchTimeSeriesActivity extends AppCompatActivity {

  private static final String NUM_OF_TIME_SERIES = "NUM_OF_TIME_SERIES";
  private static final String KEY_OF_TIME_SERIES = "KEY_OF_TIME_SERIES";
  private TextView txtStockSymbol;
  private TextView txtCompanyName;
  private ProgressBar vFetchStatus;
  private TextView txtErrorMsg;
  private final Handler handler = new Handler(Looper.getMainLooper());
  private TimeSeriesAdapter timeSeriesAdapter;
  private List<TimeSeries> timeSeries = new ArrayList<>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    super.setContentView(R.layout.activity_fetch_time_series);

    Bundle extras = super.getIntent().getExtras();
    if (extras == null || extras.getParcelable("searchResult") == null) {
      Intent intent = new Intent(this, SearchSymbolActivity.class);
      super.startActivity(intent);
      return;
    }

    SearchResult searchResult = extras.getParcelable("searchResult");
    this.txtStockSymbol = super.findViewById(R.id.txtStockSymbol2);
    this.txtCompanyName = super.findViewById(R.id.txtCompanyName2);
    this.txtStockSymbol.setText(searchResult.getStockSymbol());
    this.txtCompanyName.setText(searchResult.getCompanyName());

    Button btnStartTrading = super.findViewById(R.id.btnStartTrading);
    btnStartTrading.setOnClickListener(view -> {
      Intent intent = new Intent(this, TransactionHistoryActivity.class);
      intent.putExtra("stockSymbol", this.txtStockSymbol.getText().toString());
      intent.putExtra("companyName", this.txtCompanyName.getText().toString());
      intent.putExtra("username", extras.getString("username"));
      super.startActivity(intent);
    });

    this.vFetchStatus = super.findViewById(R.id.vFetchStatus);
    this.txtErrorMsg = super.findViewById(R.id.txtTSErrorMsg);

    this.init(savedInstanceState);
    this.createRecyclerView();

    FetchTimeSeriesTask task = new FetchTimeSeriesTask();
    new Thread(task).start();
  }

  private void init(Bundle savedInstanceState) {
    if (savedInstanceState == null || !savedInstanceState.containsKey(KEY_OF_TIME_SERIES)
        || this.timeSeries.size() > 0) {
      return;
    }
    this.timeSeries.addAll(savedInstanceState.getParcelable(KEY_OF_TIME_SERIES));
  }

  private void createRecyclerView() {
    LayoutManager layoutManger = new LinearLayoutManager(this);
    this.timeSeriesAdapter = new TimeSeriesAdapter(this.timeSeries);
    RecyclerView rvTimeSeries = super.findViewById(R.id.rvTimeSeries);
    rvTimeSeries.setHasFixedSize(true);
    rvTimeSeries.setLayoutManager(layoutManger);
    rvTimeSeries.setAdapter(this.timeSeriesAdapter);
  }

  private void addTimeSeries(List<TimeSeries> newTimeSeries) {
    int preSize = this.timeSeriesAdapter.getItemCount();
    this.timeSeries.addAll(newTimeSeries);
    this.timeSeriesAdapter.notifyItemRangeInserted(preSize, newTimeSeries.size());
  }

  @Override
  protected void onSaveInstanceState(@NonNull Bundle outState) {
    int numTimeSeries = this.timeSeries.size();
    outState.putInt(NUM_OF_TIME_SERIES, numTimeSeries);
    for (int i = 0; i < numTimeSeries; i++) {
      outState.putParcelable(KEY_OF_TIME_SERIES + i, this.timeSeries.get(i));
    }
    super.onSaveInstanceState(outState);
  }

  class FetchTimeSeriesTask implements Runnable {

    static final String QUERY_URL_PREFIX =
        "https://api.stockdata.org/v1/data/intraday?api_token=Hwn4qRX2NdR1L7eHgzYkw8I6valUIV6YUErPjso9&key_by_date=true";
    static final String STOCK_SYMBOL_PARAM = "&symbols=";
    static final String DATE_FROM_PARAM = "&date_from=";
    static final String ERROR_MSG = "Intraday data is currently unavailable for this stock.";

    @Override
    public void run() {
      try {
        URL url = new URL(this.generateQueryUrl());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setDoInput(true);
        connection.connect();

        InputStream inStream = connection.getInputStream();
        String response = this.convertStreamToString(inStream);

        JSONObject objectResult = new JSONObject(response).getJSONObject("data");
        Iterator<String> keys = objectResult.keys();
        List<TimeSeries> tempTimeSeries = new ArrayList<>();

        while (keys.hasNext()) {
          String timestamp = keys.next();
          JSONArray arrResults = (JSONArray) objectResult.get(Objects.requireNonNull(timestamp));
          JSONObject data = arrResults.getJSONObject(0).getJSONObject("data");
          double price = data.getDouble("open");
          tempTimeSeries.add(new TimeSeries(timestamp, price));
        }
        Collections.sort(tempTimeSeries);
        FetchTimeSeriesActivity.this.addTimeSeries(tempTimeSeries);
      } catch (Exception ignored) {
      }
      FetchTimeSeriesActivity.this.handler
          .post(() -> {
            FetchTimeSeriesActivity.this.vFetchStatus.setVisibility(View.GONE);
            if (FetchTimeSeriesActivity.this.timeSeries.size() == 0) {
              FetchTimeSeriesActivity.this.txtErrorMsg.setText(ERROR_MSG);
              FetchTimeSeriesActivity.this.txtErrorMsg.setVisibility(View.VISIBLE);
            }
          });
    }

    private String generateQueryUrl() {
      String stockSymbol = FetchTimeSeriesActivity.this.txtStockSymbol.getText().toString();
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
      String fromDate = LocalDateTime.now().minusDays(7).format(formatter);
      return QUERY_URL_PREFIX + STOCK_SYMBOL_PARAM + stockSymbol + DATE_FROM_PARAM + fromDate;
    }

    private String convertStreamToString(InputStream is) {
      Scanner scanner = new Scanner(is).useDelimiter("\\A");
      return scanner.hasNext() ? scanner.next() : "";
    }
  }
}
