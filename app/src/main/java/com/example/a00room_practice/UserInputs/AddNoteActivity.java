package com.example.a00room_practice.UserInputs;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a00room_practice.Activity.NoteSearchActiviy;
import com.example.a00room_practice.Activity.UpdateNoteActivity;
import com.example.a00room_practice.ModelClass.Note;
import com.example.a00room_practice.R;
import com.example.a00room_practice.RoomDatabase.NoteViewModel;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddNoteActivity extends AppCompatActivity {

    private static final String TAG ="AddNoteActivity" ;
    private NoteViewModel mNoteViewModel;
    private EditText mNoteTitleInput;
    private EditText mNoteInfoInput;
    private EditText mNoteRepsInput;
    private String mDaysFinishTime;
    private String mHoursFinishTime;
    private String mMinsFinishTime;
    private String mSecFinishTime;
    private String mFormattedDate;
    private long mNoteTime;
    private Intent mAddTimeAct,mGetdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        EditText mTimeInput = findViewById(R.id.AAN_text_time);
        Toolbar myToolbar =  findViewById(R.id.AAN_my_toolbar);
        mNoteTitleInput = findViewById(R.id.AAN_text_title);
        mNoteInfoInput = findViewById(R.id.AAN_text_info);
        mNoteRepsInput = findViewById(R.id.AAN_text_reps);
        mNoteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        mAddTimeAct = new Intent(this, AddTimeActivity.class);
         mGetdata = getIntent();


        setSupportActionBar(myToolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_close_back_arrow);
        setTitle("Add Note");

        Date date =  Calendar.getInstance().getTime();
        mFormattedDate = DateFormat.getDateInstance(DateFormat.SHORT).format(date);


        // Check if time was added to input and get the data
        if (mGetdata.hasExtra(UpdateNoteActivity.EXTRA_TIME)) {
            String mNoteTitle = mGetdata.getStringExtra(UpdateNoteActivity.EXTRA_TITLE);
            String mNoteInfo = mGetdata.getStringExtra(UpdateNoteActivity.EXTRA_INFO);
            mNoteTime = (mGetdata.getLongExtra(UpdateNoteActivity.EXTRA_TIME, 0));
            int mNoteReps = (mGetdata.getIntExtra(UpdateNoteActivity.EXTRA_REPS, 0));
            mNoteTitleInput.setText(mNoteTitle);
            mNoteInfoInput.setText(mNoteInfo);
            mNoteRepsInput.setText(String.valueOf(mNoteReps));

            if (mNoteTime <= 0 )
            {
                format_time_HMS(mNoteTime, mTimeInput,"Time: 0");
            }else {
                format_time_HMS(mNoteTime, mTimeInput,"Time:");}



        }




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

    // Get information from user and create note
    public void saveNote() {

        int mRepsInt;
        String mTitle = mNoteTitleInput.getText().toString().trim();
        String mInfo = mNoteInfoInput.getText().toString().trim();
        String mReps = mNoteRepsInput.getText().toString().trim();

        // format reps input to have user text or become zero
        if (!mReps.isEmpty()){
            mRepsInt = Integer.parseInt(mReps);}else {
            mRepsInt = 0;}


        // Check if inputs are empty
        if (mTitle.trim().isEmpty() || mInfo.trim().isEmpty()) {
            Toast.makeText(this, "Input empty", Toast.LENGTH_SHORT).show();
        } else{

            // create note with a category

            if (mGetdata.hasExtra(NoteSearchActiviy.EXTRA_SEARCH_ADD)) {
              /* int mNoteId = mGetdata.getIntExtra(CategoryActivity.EXTRA_ID,0);
                int mCategoryId = mGetdata.getIntExtra(CategoryActivity.EXTRA_CATEGORY_ID,0);
                int mCategoryTotal = mGetdata.getIntExtra(CategoryActivity.EXTRA_CATEGORY_TOTAL,0);
                String mCategoryTitle = mGetdata.getStringExtra(CategoryActivity.EXTRA_CATEGORY_NAME);



                Note note = new Note(mTitle,mInfo,mCategoryId);

                NoteStatistics noteStatistics = new NoteStatistics(mNoteId,mRepsInt,mNoteTime,mFormattedDate,"No Activity");

                mNoteViewModel.insert(note);

                Category category2 = new Category((mCategoryTotal+(1)),mCategoryTitle);
                category2.setCategory_id(mCategoryId);
                mNoteViewModel.update(category2);
                Intent intent = new Intent(AddNoteActivity.this,NoteSearchActiviy.class);
                intent.putExtra(CategoryActivity.EXTRA_CATEGORY_ID,mCategoryId);
                intent.putExtra(CategoryActivity.EXTRA_CATEGORY_TOTAL,mCategoryTotal);
                intent.putExtra(CategoryActivity.EXTRA_CATEGORY_NAME,mCategoryTitle);
                startActivity(intent);
                finish();*/

                // create note without  a category

            }else {
                Note note = new Note(mTitle,mInfo,mRepsInt,mNoteTime,"No Activiy",mFormattedDate);
                mNoteViewModel.insert(note);

            }


            Toast.makeText(this, "Note "+mTitle+" created", Toast.LENGTH_SHORT).show();

            finish();}

    }

    /// To go to time activity
    public void Time_Intent(View view){


// Get values from edit text
        String mNoteitle = mNoteTitleInput.getText().toString().trim();
        String mNoteInfo = mNoteInfoInput.getText().toString().trim();
        String mNoteReps = mNoteRepsInput.getText().toString().trim();


// Use if reps input is  empty  because it will cause an NumberFormatException
        if (!mNoteReps.isEmpty()){
            mAddTimeAct.putExtra(UpdateNoteActivity.EXTRA_REPS,Integer.parseInt(mNoteReps)); }


// Send values from edit text to time activity

        mAddTimeAct.putExtra(UpdateNoteActivity.EXTRA_TITLE,mNoteitle);
        mAddTimeAct.putExtra(UpdateNoteActivity.EXTRA_INFO, mNoteInfo);
        mAddTimeAct.putExtra(UpdateNoteActivity.EXTRA_TIME, mNoteTime);
        startActivity(mAddTimeAct);
        finish();


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
        }else if (days_MT == 0 && hours_MT == 0 && minutes_MT == 0 && seconds_MT == 0 ) {
            mSecFinishTime = "No Activity";
        }

        String MT_time_hour_Mins_Secs = text+" " + mDaysFinishTime + " " + mHoursFinishTime + " " + mMinsFinishTime + " " + mSecFinishTime;

        textView.setText(MT_time_hour_Mins_Secs);
    }

}


