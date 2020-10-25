package com.example.a00room_practice.Miscellaneous;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a00room_practice.Activity.MainActivity;
import com.example.a00room_practice.ModelClass.AllNotes;
import com.example.a00room_practice.ModelClass.Category;
import com.example.a00room_practice.ModelClass.Note;
import com.example.a00room_practice.ModelClass.NoteAndCategory;
import com.example.a00room_practice.R;
import com.example.a00room_practice.RoomDatabase.NoteDao;
import com.example.a00room_practice.RoomDatabase.NoteRepository;
import com.example.a00room_practice.RoomDatabase.NoteViewModel;
import com.example.a00room_practice.Activity.NoteActivity;
import com.example.a00room_practice.Activity.CategoryActivity;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class QuickNotesActivity extends AppCompatActivity    {
    private static final String TAG ="QuickNotesActivity" ;
    public static final String REPS = "Repetition ";
    public static final String TOTAL_TIME  = "Total time ";
    public NoteViewModel noteViewModel;
    String currentdate,formattedDate;
    private TextView mTextView;
    private  int maxnum = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_notes);
        Calendar calendar = Calendar.getInstance();

        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);

        currentdate = DateFormat.getDateTimeInstance().format(calendar.getTime());

        Date date =  Calendar.getInstance().getTime();

        formattedDate = DateFormat.getDateInstance(DateFormat.SHORT).format(date);

        mTextView = findViewById(R.id.QNA_tv);
    }





    public void insert_notes (View v){

    String [] Title = getResources().getStringArray(R.array.Title);
    String [] Description = getResources().getStringArray(R.array.Description);

    for (int i = 0; i <5; i++) {

       double num = Math.floor(Math.random() * 1000);
        double num_d = Math.floor(Math.random() * 100);
       int num1 = (int) num;
       int num2 = (int) num_d;


       // Use to get the current time
        /*String [] splitdate = currentdate.split(" ");
        +" "+splitdate[3].trim()+" "+splitdate[4].trim()*/

       Note note = new Note(Title[i],Description[i],num2,num1,"No Activity",formattedDate);

       // noteViewModel.insert(note);

       noteViewModel.insert(note);


    }

        Intent intent = new Intent(QuickNotesActivity.this, NoteActivity.class);
        startActivity(intent);
        Toast.makeText(QuickNotesActivity.this, "Inserted", Toast.LENGTH_SHORT).show();
        finish();




    }

    public void insert_categories (View v){

        check_category_max(v);
        Intent intent = new Intent(QuickNotesActivity.this, CategoryActivity.class);
      startActivity(intent);
        Toast.makeText(QuickNotesActivity.this, "Inserted", Toast.LENGTH_SHORT).show();
finish();

    }


    public void insert_notesAndCatgories (View v){



            NoteAndCategory noteAndCategory = new NoteAndCategory(1,1);
            noteViewModel.insert(noteAndCategory);




        Intent intent = new Intent(QuickNotesActivity.this, MainActivity.class);
       startActivity(intent);
        Toast.makeText(QuickNotesActivity.this, "Inserted", Toast.LENGTH_SHORT).show();
finish();
    }


    public void check_category_max(View v){


        noteViewModel.getMax().observe(QuickNotesActivity.this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {

                if (integer != null){
                    maxnum = integer;

                }else {maxnum = -1;}


            }
        });

       new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                for (int i = 0; i <5 ; i++) {


                    Category category = new Category("Category "+i,i, ++maxnum);
                    noteViewModel.insert(category);
                }
            }
        },1000L);

    }





}
