package com.bluerocket.callernotse.activities;

import android.app.DatePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bluerocket.callernotse.R;
import com.bluerocket.callernotse.models.NoteModel;
import com.bluerocket.callernotse.viewmodel.AddNoteViewModel;

import java.util.Calendar;
import java.util.Date;


public class AddNoteActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private Date date;
    private DatePickerDialog datePickerDialog;
    private Calendar calendar;

    private EditText itemEditText;
    private EditText nameEditText;
    private TextView displaynameTextView;
    private Button pickContactButton, pickContactButton2;
    private static final int REQUEST_CONTACT = 1011;

    private AddNoteViewModel addNoteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        itemEditText = findViewById(R.id.itemName);
        nameEditText = findViewById(R.id.personName);
        pickContactButton = findViewById(R.id.pickContactBtn);
        displaynameTextView = findViewById(R.id.display_name);
        calendar = Calendar.getInstance();

        addNoteViewModel = ViewModelProviders.of(this).get(AddNoteViewModel.class);

        datePickerDialog = new DatePickerDialog(this, AddNoteActivity.this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        nameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    nameEditText.setHint("");
                else{
                    nameEditText.setHint("Please add caller note here....");
                }

            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemEditText.getText() == null || nameEditText.getText() == null || date == null)
                    Toast.makeText(AddNoteActivity.this, "Missing fields", Toast.LENGTH_SHORT).show();
                else {
                    addNoteViewModel.addBorrow(new NoteModel(
                            itemEditText.getText().toString(),
                            nameEditText.getText().toString(),
                            date
                    ));
                    finish();
                }
            }
        });


        findViewById(R.id.dateButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        pickContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddNoteActivity.this, ContactListActivity.class);
                startActivityForResult(intent, REQUEST_CONTACT);

            }
        });
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        date = calendar.getTime();

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CONTACT:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    String a = bundle.getString("phone");
                    String b = bundle.getString("name");
                    itemEditText.setText(a);
                    displaynameTextView.setText(b);


                }
                break;
        }

    }
}
