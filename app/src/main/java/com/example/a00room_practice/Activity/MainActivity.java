package com.example.a00room_practice.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.a00room_practice.Miscellaneous.QuickNotesActivity;
import com.example.a00room_practice.R;
import com.example.a00room_practice.RoomDatabase.NoteViewModel;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;


public class MainActivity extends AppCompatActivity   {
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Test_function();



    }


    public void intentactiviy2(View view) {
        Intent intent = new Intent(view.getContext(), NoteActivity.class);
        startActivity(intent);
    }

    public void intentOuicknotes(View view) {
        Intent intent = new Intent(view.getContext(), QuickNotesActivity.class);
        startActivity(intent);
    }

    public void intentButton(View view) {
        Intent intent = new Intent(view.getContext(), CategoryActivity.class);
        startActivity(intent);


    }


    public  void Test_function() {
        TextView textView = findViewById(R.id.AM_textview);
        NoteViewModel noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);

        Date date =  Calendar.getInstance().getTime();
        String formattedDate = DateFormat.getDateInstance(DateFormat.SHORT).format(date);

        Calendar calendar = Calendar.getInstance();

        String currentdate = DateFormat.getDateTimeInstance().format(calendar.getTime());

        String [] splitdate = currentdate.split(" ");


    }


    public void maintwo(View view) {
        Intent intent = new Intent(this, CategoryReorderList.class);
        startActivity(intent);
    }






}