package com.example.pregnancyTracker.Weight;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.pregnancyTracker.Model.User;
import com.example.pregnancyTracker.Model.WeightEntry;
import com.example.pregnancyTracker.v1.R;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

import static java.lang.Integer.parseInt;

public class WeightFragment extends Fragment implements View.OnClickListener {

    Realm realm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup myView = (ViewGroup) inflater.inflate(R.layout.fragment_weight_portfolio, container, false);

        realm = Realm.getDefaultInstance();
        User user = realm.where(User.class).findFirst();
        RealmResults<WeightEntry> data = realm.where(WeightEntry.class).findAll();

        long milliseconds = user.getDueDate().getTime() - Calendar.getInstance().getTime().getTime();
        long days = TimeUnit.DAYS.convert(milliseconds, TimeUnit.MILLISECONDS);

        TextView daysLeft = (TextView) myView.findViewById(R.id.day_count_down);
        daysLeft.setText(String.format(getString(R.string.days_left), days));

        RecyclerView recyclerView = (RecyclerView) myView.findViewById(R.id.recycler_list_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        final Adapter adapter = new WeightRecyclerViewAdapter(data);
        recyclerView.setAdapter(adapter);

        data.addChangeListener(new RealmChangeListener<RealmResults<WeightEntry>>() {
            @Override
            public void onChange(RealmResults<WeightEntry> data) {
                adapter.notifyDataSetChanged();
            }
        });

        FloatingActionButton addButton = (FloatingActionButton) myView.findViewById(R.id.fab);
        addButton.setOnClickListener(this);

        return myView;
    }

    @Override
    public void onClick(View view) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View content = inflater.inflate(R.layout.insert_weight_popup, null);

        final Button addButton = (Button) content.findViewById(R.id.add_weight_button);
        final EditText weightInput = (EditText) content.findViewById(R.id.weight);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(content).setTitle(getString(R.string.insert_your_weight));
        final AlertDialog dialog = builder.create();
        dialog.show();

        addButton.setEnabled(false);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        WeightEntry entry = realm.createObject(WeightEntry.class);
                        entry.setWeight(parseInt(weightInput.getText().toString()));
                        entry.setTimestamp(Calendar.getInstance().getTime());
                    }
                });
            }
        });

        weightInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
                    return;
                }
                int weight = parseInt(charSequence.toString());
                addButton.setEnabled(weight > 30 && weight < 300);
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

    }


}