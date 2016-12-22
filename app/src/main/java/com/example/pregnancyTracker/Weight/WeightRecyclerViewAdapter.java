package com.example.pregnancyTracker.Weight;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pregnancyTracker.Model.WeightEntry;
import com.example.pregnancyTracker.v1.R;

import java.text.DateFormat;
import java.util.Date;

import io.realm.RealmResults;

public class WeightRecyclerViewAdapter extends RecyclerView.Adapter<WeightRecyclerViewAdapter.WeightDataViewHolder>{

    private RealmResults<WeightEntry> list;

    public WeightRecyclerViewAdapter(RealmResults<WeightEntry> list) {
        this.list=list;
    }

    @Override
    public WeightDataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the layout, initialize the View Holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_weight_list_item, parent, false);
        return new WeightDataViewHolder(v);
    }

    @Override
    public void onBindViewHolder(WeightDataViewHolder holder, int position) {
        // Use the provided View Holder on the onCreateViewHolder method to populate the current row on the RecyclerView
        WeightEntry entry = list.get(position);

        DateFormat dateFormat = android.text.format.DateFormat.getLongDateFormat(holder.date.getContext());
        Date dateObject = entry.getTimestamp();
        String date = dateFormat.format(dateObject);

        holder.date.setText(date);
        holder.measures.setText(entry.getWeight());
        holder.unit.setText("kg");
    }

    @Override
    public int getItemCount() {
        // Returns the number of elements the RecyclerView will display
        return list.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    static class WeightDataViewHolder extends RecyclerView.ViewHolder{
        TextView date;
        TextView measures;
        TextView unit;

        private WeightDataViewHolder(View itemView) {
            super(itemView);

            date = (TextView) itemView.findViewById(R.id.date_of_measurement);
            measures = (TextView) itemView.findViewById(R.id.measurement);
            unit = (TextView) itemView.findViewById(R.id.unit);
        }
    }

}
