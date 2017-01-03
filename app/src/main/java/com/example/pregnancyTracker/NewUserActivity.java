package com.example.pregnancyTracker;

import android.app.DatePickerDialog;
import android.content.Intent;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import android.os.Bundle;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pregnancyTracker.Model.User;
import com.example.pregnancyTracker.v1.R;

import io.realm.Realm;

public class NewUserActivity extends AppCompatActivity {

    private Date dueDate;

    private TextView dueDateText;
    private Button datePickerButton;
    private Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        // Making notification bar transparent
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        dueDate = null;

        dueDateText = (TextView) findViewById(R.id.due_date_text);
        datePickerButton = (Button) findViewById(R.id.date_picker_button);
        startButton = (Button) findViewById(R.id.start_button);

        // disable button until due date is set
        startButton.setEnabled(false);

        setStartButtonClickListener();
        setDatePickerButtonClickListener();
    }

    public void setStartButtonClickListener() {
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Realm realm = Realm.getDefaultInstance();
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        User user = realm.createObject(User.class);
                        user.setDueDate(dueDate);
                    }
                });

                Intent intent = new Intent(NewUserActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void setDatePickerButtonClickListener() {
        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the current time
                Calendar calendar = Calendar.getInstance();
                // Add 280 days to current time, default due date
                calendar.add(Calendar.DAY_OF_YEAR, 280);
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                // Create date picker dialog with default due date
                DatePickerDialog dialog = new DatePickerDialog(NewUserActivity.this, myDateListener, year, month, day);
                dialog.show();
            }
        });
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(android.widget.DatePicker picker, int year, int month, int day) {
            Calendar dueDate = new GregorianCalendar();
            dueDate.set(Calendar.YEAR, year);
            dueDate.set(Calendar.MONTH, month);
            dueDate.set(Calendar.DAY_OF_MONTH, day);

            Calendar now = Calendar.getInstance();

            Calendar maxDueDate = Calendar.getInstance();
            maxDueDate.add(Calendar.DAY_OF_YEAR, 294);

            if (dueDate.before(now)) {
                // Due date should be in the future
                Toast.makeText(NewUserActivity.this, "Due date needs to be in the future", Toast.LENGTH_SHORT).show();
            } else if (dueDate.after(maxDueDate)) {
                // Due date is too late
                Toast.makeText(NewUserActivity.this, "The maximum pregnancy is 42 weeks", Toast.LENGTH_SHORT).show();
            } else {
                Date date = dueDate.getTime();
                setDueDate(date);
            }
        }
    };

    private void setDueDate(Date date) {
        dueDate = date;
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        dueDateText.setText(format.format(date));

        // Enable start button
        startButton.setEnabled(true);
    }

}