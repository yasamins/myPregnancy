package com.example.pregnancyTracker.Weight;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pregnancyTracker.Model.User;
import com.example.pregnancyTracker.Model.WeightEntry;
import com.example.pregnancyTracker.v1.R;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import io.realm.Realm;
import io.realm.RealmResults;

public class WeightFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout myView = (LinearLayout) inflater.inflate(R.layout.fragment_weight_portfolio, container, false);

        Realm realm = Realm.getDefaultInstance();
        User user = realm.where(User.class).findFirst();
        RealmResults<WeightEntry> data = realm.where(WeightEntry.class).findAll();

        long milliseconds = user.getDueDate().getTime() - Calendar.getInstance().getTime().getTime();
        long days = TimeUnit.DAYS.convert(milliseconds, TimeUnit.MILLISECONDS);

        TextView daysLeft = (TextView) myView.findViewById(R.id.day_count_down);
        daysLeft.setText(days + " days left");

        RecyclerView recyclerView = (RecyclerView) myView.findViewById(R.id.recycler_list_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        Adapter adapter = new WeightRecyclerViewAdapter(data);
        recyclerView.setAdapter(adapter);
        return myView;
    }

}