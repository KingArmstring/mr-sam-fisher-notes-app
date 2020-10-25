package com.example.a00room_practice.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.example.a00room_practice.R;
import com.example.a00room_practice.RoomDatabase.NoteViewModel;
import com.example.a00room_practice.UserInputs.AddRepActivity;
import com.example.a00room_practice.UserInputs.AddTimeActivity;

public class UpdateNoteActivity extends AppCompatActivity {
    public static final String EXTRA_ID = "com.example.a00room_practice.EXTRA_ID";
    public static final String EXTRA_REPS = "com.example.a00room_practice.EXTRA_REPS";
    public static final String EXTRA_TIME = "com.example.a00room_practice.EXTRA_TIME";
    public static final String EXTRA_TITLE = "com.example.a00room_practice.EXTRA_TITLE";
    public static final String EXTRA_INFO = "com.example.a00room_practice.EXTRA_INFO";
    public static final String EXTRA_TIME_CREATED = "com.example.a00room_practice.EXTRA_TIME_CREATED";
    public static final String EXTRA_LAST_USED = "com.example.a00room_practice.EXTRA_LAST_USED";
    public static final String EXTRA_CATEGORY_ID = "com.example.a00room_practice.EXTRA_CATEGORY_ID";
    public static final String EXTRA_CATEGORY_TOTAL = "com.example.a00room_practice.EXTRA_CATEGORY_TOAL";
    public static final String EXTRA_CATEGORY_TITLE = "com.example.a00room_practice.EXTRA_CATEGORY_TITLE";
    public static final String EXTRA_NOTE_SEARCH_ACTIVITY = "com.example.a00room_practice.EXTRA_NOTE_SEARCH_ACTIVITY";


    private EditText mTitleInput;
    private EditText mInfoInput;
    private Intent mGetdata;
    private String mDaysFinishTime;
    private String mHoursFinishTime;
    private String mMinsFinishTime;
    private String mSecFinishTime;
    private int mNoteId, mNoteReps;
    private long mNoteTime;
    NoteViewModel noteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_note);
        Toolbar myToolbar =  findViewById(R.id.AEN_my_toolbar);
        setSupportActionBar(myToolbar);
        mGetdata = getIntent();
        mTitleInput = findViewById(R.id.AEN_text_title);
        mInfoInput = findViewById(R.id.AEN_text_info);
        EditText mNoteTimeInput = findViewById(R.id.AEN_text_time);
        EditText mNoteRepsInput = findViewById(R.id.AEN_text_reps);
        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        myToolbar.setNavigationIcon(R.drawable.ic_close_back_arrow);
        setTitle("Edit Note");


             mNoteId = getIntent().getIntExtra(EXTRA_ID, -1);
             mNoteReps = mGetdata.getIntExtra(EXTRA_REPS,0);
             mNoteTime = mGetdata.getLongExtra(EXTRA_TIME,0);

            mTitleInput.setText(mGetdata.getStringExtra(EXTRA_TITLE));
            mInfoInput.setText(mGetdata.getStringExtra(EXTRA_INFO));

            mNoteRepsInput.setText("Reps "+ mNoteReps);
            format_time_HMS(mNoteTime, mNoteTimeInput,"Time ");


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_menu, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menu_save :
                saveNote();
                break;
            case android.R.id.home:
             onBackPressed();
                this.finish();
                return true;

        }
        return true;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }



   // Get information from inputs and updated note
    public void saveNote() {
        String mNoteTitle = mTitleInput.getText().toString();
        String mNoteInfo = mInfoInput.getText().toString();

        if (mNoteTitle.trim().isEmpty() || mNoteInfo.trim().isEmpty()) {
            Toast.makeText(this, "Input empty", Toast.LENGTH_SHORT).show();

        } else {
/*
          // To get data sent from note
            int mCategoryId = mGetdata.getIntExtra(EXTRA_CATEGORY_ID,0);
            int mCategoryTotal = mGetdata.getIntExtra(EXTRA_CATEGORY_TOTAL,0);
            String mCategoryTitle = mGetdata.getStringExtra(EXTRA_CATEGORY_TITLE);
            String mNoteTimeCreated = mGetdata.getStringExtra(EXTRA_TIME_CREATED);
            String mNoteLastUsed = mGetdata.getStringExtra(EXTRA_LAST_USED);



            NoteInfo noteInfo = new NoteInfo(mNoteTitle, mNoteInfo, mCategoryId,mCategoryTotal,mCategoryTitle, mNoteReps, mNoteTime,mNoteTimeCreated,mNoteLastUsed);

            noteInfo.setId(mNoteId);

            noteViewModel.update(noteInfo);
            Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show();

            /// Use to send Category ID to the NoteSearchActivity
            Intent intent = new Intent(UpdateNoteActivity.this, NoteSearchActiviy.class);
            intent.putExtra(EXTRA_CATEGORY_ID,mCategoryId);

            /// Use to go to the Search Activity if you are updating form Note Search Activity

            if (mGetdata.hasExtra(EXTRA_NOTE_SEARCH_ACTIVITY)){
                noteViewModel.findID(mCategoryId);
                startActivity(intent);}

            finish();

            */
        }


    }





// Format the String time format to show Hour,Mins,Seconds, and days written in front of the time
    public void format_time_HMS(long time, TextView textView, String text){
            int days_MT = (int) (time) / 86400;
            int hours_MT = (int) (time) / 3600 % 24;
            int minutes_MT = (int) (time) / 60 % 60;
            int seconds_MT = (int) (time) % 60;


            if (days_MT > 1) {
                mDaysFinishTime = "" + days_MT + " days";

            } else if ((days_MT == 1)) {
                mDaysFinishTime = "" + days_MT + " day";
            } else if (days_MT < 1) {
                mDaysFinishTime = "";
            }


            if (hours_MT > 1) {
                mHoursFinishTime = "" + hours_MT + " hours";

            } else if ((hours_MT == 1)) {
                mHoursFinishTime = "" + hours_MT + " hour";
            } else if (hours_MT < 1) {
                mHoursFinishTime = "";
            }

            if (minutes_MT > 1) {
                mMinsFinishTime = "" + minutes_MT + " mins";

            } else if ((minutes_MT == 1)) {
                mMinsFinishTime = "" + minutes_MT + " min";
            } else if (minutes_MT < 1) {
                mMinsFinishTime = "";
            }

            if (seconds_MT > 1) {
                mSecFinishTime = "" + seconds_MT + " secs";

            } else if ((seconds_MT == 1)) {
                mSecFinishTime = "" + seconds_MT + " sec";
            } else if (seconds_MT < 1) {
                mSecFinishTime = "";
            }

            String MT_time_hour_Mins_Secs = text+" " + mDaysFinishTime + " " + mHoursFinishTime + " " + mMinsFinishTime + " " + mSecFinishTime;

            textView.setText(MT_time_hour_Mins_Secs);
        }



    // Use to go to Time or Reps activity
    public void Time_Rep_Intent (View v) {

        switch (v.getId())
        {

            case R.id.AEN_text_reps:
                Intent intent = new Intent(this, AddRepActivity.class);
                intent_activity(intent);
                break;

            case R.id.AEN_text_time:
                Intent intent1 = new Intent(this, AddTimeActivity.class);
                intent_activity(intent1);
                break;
        }

    }


// Send the data to Time or Reps activity
    public void intent_activity(Intent intent){

        int id = mGetdata.getIntExtra(EXTRA_ID, -1);
        int reps = mGetdata.getIntExtra(EXTRA_REPS,0);
        long time = (mGetdata.getLongExtra(EXTRA_TIME,0));
        int category_id = mGetdata.getIntExtra(EXTRA_CATEGORY_ID,0);
        int category_total = mGetdata.getIntExtra(EXTRA_CATEGORY_TOTAL,0);
        String category_title = mGetdata.getStringExtra(EXTRA_CATEGORY_TITLE);
        String title = this.mTitleInput.getText().toString().trim();
        String info = this.mInfoInput.getText().toString().trim();
        String time_created = mGetdata.getStringExtra(EXTRA_TIME_CREATED);
        String last_used = mGetdata.getStringExtra(EXTRA_LAST_USED);


        intent.putExtra(EXTRA_ID,id);
        intent.putExtra(EXTRA_REPS,reps);
        intent.putExtra(EXTRA_TITLE,title);
        intent.putExtra(EXTRA_INFO, info);
        intent.putExtra(EXTRA_TIME,time);
        intent.putExtra(EXTRA_CATEGORY_ID,category_id);
        intent.putExtra(EXTRA_CATEGORY_TOTAL,category_total);
        intent.putExtra(EXTRA_CATEGORY_TITLE,category_title);
        intent.putExtra(EXTRA_TIME_CREATED,time_created);
        intent.putExtra(EXTRA_LAST_USED,last_used);


        startActivity(intent);
        finish();
    }






    public  static  String FinishTime(int time,String stime){
        String results = "";
        if (time > 1) {
            stime = "" + time + " days";
            results = stime;
            ;


        } else if ((time == 1)) {
            stime = "" + time + " day";
            results = stime;


        } else if (time < 1) {
            stime = "";
            results = stime;


        }




        return results;
    }

}


