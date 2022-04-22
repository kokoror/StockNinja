package edu.neu.madcourse.numad22sp_codebuster_stockninja;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class NavigationActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_navigation);

    Button btnToSearchSymbolActivity = super.findViewById(R.id.btnToSearchSymbolActivity);
    btnToSearchSymbolActivity.setOnClickListener(view -> {
      Intent intent = new Intent(NavigationActivity.this, SearchSymbolActivity.class);
      this.startActivity(intent);
    });

    Button btnToPortfolioActivity = super.findViewById(R.id.btnToPortfolioActivity);
    btnToPortfolioActivity.setOnClickListener(view -> {
      Bundle bundle = getIntent().getExtras();
      Intent intent = new Intent(NavigationActivity.this, PortfolioActivity.class);
      intent.putExtras(bundle);
      this.startActivity(intent);
    });

    Button btnToDiscussionActivity = super.findViewById(R.id.btnToDiscussionActivity);
    btnToDiscussionActivity.setOnClickListener(view -> {
      Bundle bundle = getIntent().getExtras();
      Intent intent = new Intent(NavigationActivity.this, DiscussionBoardActivity.class);
      intent.putExtras(bundle);
      this.startActivity(intent);
    });
  }
}
