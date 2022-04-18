package edu.neu.madcourse.numad22sp_codebuster_stockninja;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import edu.neu.madcourse.numad22sp_codebuster_stockninja.controllers.PortfolioRecyclerAdapter;
import edu.neu.madcourse.numad22sp_codebuster_stockninja.models.Stock;

public class PortfolioActivity extends AppCompatActivity {
    private String currentUser = null;
    private ArrayList<Stock> portfolioList;
    private RecyclerView recyclerView;
    private PortfolioRecyclerAdapter adapter;
    private PortfolioRecyclerAdapter.RecyclerViewClickerListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portfolio);
        TextView todayDate = findViewById(R.id.date);
        String timeNow = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMM dd"));
        todayDate.setText(timeNow);

        portfolioList = new ArrayList<>();
        recyclerView = findViewById(R.id.portfolio_list);
        recyclerView.setNestedScrollingEnabled(false);
        getIntentData();
        setAdapter();
//        fetchDataFromDB(currentStock, currentUser);

        portfolioList.add(new Stock("GOOG", 25, 20));
        portfolioList.add(new Stock("AMAZON", 15, 20));
        portfolioList.add(new Stock("NIN", 12, 30));
        portfolioList.add(new Stock("ZZZ", 25, 20));
        portfolioList.add(new Stock("AMAZON", 15, 20));
        portfolioList.add(new Stock("NIN", 12, 30));
        portfolioList.add(new Stock("ZZZ", 25, 20));
    }

        private void getIntentData() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            currentUser = extras.getString("user");
        }
    }

//    private void fetchDataFromDB(String currUser) {
//        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
//        DatabaseReference msgRef = databaseRef.child("messages");
//
//        msgRef.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                ReceivedMessage newMessage = snapshot.getValue(ReceivedMessage.class);
//                if (newMessage.getReceiver().equalsIgnoreCase(currUser)) {
//                    addMessage(0, newMessage.getReceiver(), newMessage.getSender(), newMessage.getSticker(), newMessage.getTimestamp());
//                }
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//
//        });
//    }


//    private void addMessage(int position, String receiver, String sender, String sticker, String time) {
//        ReceivedMessage newMessage = new ReceivedMessage(receiver, sender, sticker, time);
//        receivedMessages.add(newMessage);
//        adapter.notifyItemInserted(0);
//    }


    private void setAdapter() {
        setOnClickListener();
        adapter = new PortfolioRecyclerAdapter(portfolioList, listener);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void setOnClickListener() {
        listener = new PortfolioRecyclerAdapter.RecyclerViewClickerListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), TransactionHistoryActivity.class);
                intent.putExtra("currUser", currentUser);
                intent.putExtra("stockSymbol", portfolioList.get(position).getStockSymbol());
                startActivity(intent);
            }
        };
    }
}