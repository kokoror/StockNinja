package edu.neu.madcourse.numad22sp_codebuster_stockninja;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

import edu.neu.madcourse.numad22sp_codebuster_stockninja.controllers.TransactionRecyclerAdapter;
import edu.neu.madcourse.numad22sp_codebuster_stockninja.models.Transaction;

public class TransactionHistoryActivity extends AppCompatActivity {
    private String stockSymbol = null;
    private String currentUser = null;
    private ArrayList<Transaction> transactionList;
    private RecyclerView recyclerView;
    private TransactionRecyclerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);
        getIntentData();
        TextView comSymbol = findViewById(R.id.com_symbol);
        comSymbol.setText(stockSymbol);

        transactionList = new ArrayList<>();
        recyclerView = findViewById(R.id.transaction_list);
        recyclerView.setNestedScrollingEnabled(false);

        setAdapter();
//        fetchDataFromDB(currentStock, currentUser);

        transactionList.add(new Transaction("USER1", "Goog", "Buy", 100, 200, null));
        transactionList.add(new Transaction("USER1", "AMA", "Sell", 200, 222, null));
        transactionList.add(new Transaction("USER1", "XXX", "Sell", 22.6, 123, null));
        transactionList.add(new Transaction("USER1", "ABC", "Buy", 22.3, 129, null));
        transactionList.add(new Transaction("USER1", "XXX", "Sell", 22.6, 123, null));
        transactionList.add(new Transaction("USER1", "ABC", "Buy", 22.3, 129, null));
        transactionList.add(new Transaction("USER1", "XXX", "Sell", 22.6, 123, null));
        transactionList.add(new Transaction("USER1", "ABC", "Buy", 22.3, 129, null));

    }

    private void getIntentData() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            currentUser = extras.getString("currUser");
            stockSymbol = extras.getString("stockSymbol");
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
//        adapter.notifyItemInserted(receivedMessages.size()-1);
//    }


    private void setAdapter() {
        adapter = new TransactionRecyclerAdapter(transactionList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }


}

