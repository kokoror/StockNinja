package edu.neu.madcourse.numad22sp_codebuster_stockninja.controllers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import edu.neu.madcourse.numad22sp_codebuster_stockninja.R;
import edu.neu.madcourse.numad22sp_codebuster_stockninja.controllers.TimeSeriesAdapter.TimeSeriesHolder;
import edu.neu.madcourse.numad22sp_codebuster_stockninja.models.TimeSeries;
import java.util.List;

public class TimeSeriesAdapter extends Adapter<TimeSeriesHolder> {

  private List<TimeSeries> timeSeries;

  public TimeSeriesAdapter() {
  }

  public TimeSeriesAdapter(List<TimeSeries> timeSeries) {
    this.timeSeries = timeSeries;
  }

  @NonNull
  @Override
  public TimeSeriesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.time_series, parent, false);
    return new TimeSeriesHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull TimeSeriesHolder holder, int position) {
    TimeSeries currTimeSeries = this.timeSeries.get(position);
    holder.txtRecordTimestamp.setText(currTimeSeries.getTimestamp());
    holder.txtRecordPrice.setText(String.valueOf(currTimeSeries.getPrice()));
  }

  @Override
  public int getItemCount() {
    return this.timeSeries.size();
  }

  class TimeSeriesHolder extends ViewHolder {

    TextView txtRecordTimestamp;
    TextView txtRecordPrice;

    public TimeSeriesHolder(@NonNull View view) {
      super(view);
      this.txtRecordTimestamp = view.findViewById(R.id.txtRecordTimestamp);
      this.txtRecordPrice = view.findViewById(R.id.txtRecordPrice);
    }
  }
}
