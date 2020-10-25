package com.example.a00room_practice.UserInputs;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a00room_practice.Activity.UpdateNoteActivity;
import com.example.a00room_practice.R;
import com.example.a00room_practice.Miscellaneous.SharedPreferencesClass;

public class AddTimeActivity extends AppCompatActivity implements View.OnClickListener {
    public static String EXTRA_RADIO_Value = "com.example.dialog_test_EXTRA_RADIO_VALUE";

    private long value_sec,value_mins,value_hour,value_day,cr_value, total_value,value_new;
    private TextView tv_newValue,tv_currentValue,tv_sec,tv_min,tv_day,tv_hour;
    private String days_f_time, hours_f_time, mins_f_time, sec_f_time,kk,
            kk1,kk2,kk3,kk4,kk5,kk6,kk7,title,info,last_usted,time_created;
    private String category_title;
    int sv_sec,sv_mins,sv_hour,sv_day,id,reps,category_id,category_total;
    private EditText et_input;
    private Intent editActivity,addActivity,getdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_time);
        getdata = getIntent();
        cr_value = getdata.getLongExtra(UpdateNoteActivity.EXTRA_TIME,0);
        tv_sec = findViewById(R.id.DTIL_tv_sec);
        tv_min= findViewById(R.id.DTIL_tv_min);
        tv_day = findViewById(R.id.DTIL_tv_day);
        tv_hour = findViewById(R.id.DTIL_tv_hour);
        tv_currentValue = findViewById(R.id.DTIL_tv_current_value);
        tv_newValue = findViewById(R.id.DTIL_tv_new_value);
        et_input = findViewById(R.id.DTIL_et_input);
        editActivity = new Intent(this, UpdateNoteActivity.class);
        addActivity = new Intent(this, AddNoteActivity.class);


        id = getdata.getIntExtra(UpdateNoteActivity.EXTRA_ID, -1);
        reps = getdata.getIntExtra(UpdateNoteActivity.EXTRA_REPS,0);
        title = getdata.getStringExtra(UpdateNoteActivity.EXTRA_TITLE);
        info = getdata.getStringExtra(UpdateNoteActivity.EXTRA_INFO);
        category_id = getdata.getIntExtra(UpdateNoteActivity.EXTRA_CATEGORY_ID,0);
        category_total= getdata.getIntExtra(UpdateNoteActivity.EXTRA_CATEGORY_TOTAL,0);
        category_title = getdata.getStringExtra(UpdateNoteActivity.EXTRA_CATEGORY_TITLE);
        time_created = getdata.getStringExtra(UpdateNoteActivity.EXTRA_TIME_CREATED);
        last_usted = getdata.getStringExtra(UpdateNoteActivity.EXTRA_LAST_USED);

        setup_textwatcher();

        format_time_HMS(cr_value,tv_currentValue,"Current value:");
    }



    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.DTIL_btn_0:
                if (!et_input.getText().toString().isEmpty()){
                    et_input.setText(et_input.getText().toString()+"0");}
                break;
            case R.id.DTIL_btn_1:
                et_input.setText(et_input.getText().toString()+"1");
                break;
            case R.id.DTIL_btn_2:
                et_input.setText(et_input.getText().toString()+"2");
                break;
            case R.id.DTIL_btn_3:
                et_input.setText(et_input.getText().toString()+"3");
                break;
            case R.id.DTIL_btn_4:
                et_input.setText(et_input.getText().toString()+"4");
                break;
            case R.id.DTIL_btn_5:
                et_input.setText(et_input.getText().toString()+"5");
                break;
            case R.id.DTIL_btn_6:
                et_input.setText(et_input.getText().toString()+"6");
                break;
            case R.id.DTIL_btn_7:
                et_input.setText(et_input.getText().toString()+"7");
                break;
            case R.id.DTIL_btn_8:
                et_input.setText(et_input.getText().toString()+"8");
                break;
            case R.id.DTIL_btn_9:
                et_input.setText(et_input.getText().toString()+"9");
                break;

            case R.id.DTIL_btn_delete:
                int length = et_input.getText().length();
                if (length > 0) {
                    et_input.getText().delete(length - 1, length);}
                break;

            case R.id.DTIL_btn_save:



                    if (id > -1 ){
                        editActivity.putExtra(UpdateNoteActivity.EXTRA_ID,id);
                editActivity.putExtra(UpdateNoteActivity.EXTRA_TIME,value_new);
                editActivity.putExtra(UpdateNoteActivity.EXTRA_TITLE, title);
                editActivity.putExtra(UpdateNoteActivity.EXTRA_INFO, info);
                editActivity.putExtra(UpdateNoteActivity.EXTRA_REPS,reps);
                editActivity.putExtra(UpdateNoteActivity.EXTRA_CATEGORY_ID, category_id);
                editActivity.putExtra(UpdateNoteActivity.EXTRA_CATEGORY_TOTAL, category_total);
                editActivity.putExtra(UpdateNoteActivity.EXTRA_CATEGORY_TITLE, category_title);
                editActivity.putExtra(UpdateNoteActivity.EXTRA_TIME_CREATED, time_created);
                editActivity.putExtra(UpdateNoteActivity.EXTRA_LAST_USED, last_usted);

                startActivity(editActivity);
                finish();
                    }else
                    {   addActivity.putExtra(UpdateNoteActivity.EXTRA_TIME,value_new);
                        addActivity.putExtra(UpdateNoteActivity.EXTRA_TITLE, title);
                        addActivity.putExtra(UpdateNoteActivity.EXTRA_INFO, info);
                        addActivity.putExtra(UpdateNoteActivity.EXTRA_REPS,reps);
                        editActivity.putExtra(UpdateNoteActivity.EXTRA_CATEGORY_TOTAL, category_total);
                        editActivity.putExtra(UpdateNoteActivity.EXTRA_CATEGORY_TITLE, category_title);

                        startActivity(addActivity);
                        finish();

                    }


                break;

            case R.id.DTIL_btn_cancel:
                if (id > -1 ) {
                    editActivity.putExtra(UpdateNoteActivity.EXTRA_ID,id);
                    editActivity.putExtra(UpdateNoteActivity.EXTRA_TIME,cr_value);
                    editActivity.putExtra(UpdateNoteActivity.EXTRA_TITLE, title);
                    editActivity.putExtra(UpdateNoteActivity.EXTRA_INFO, info);
                    editActivity.putExtra(UpdateNoteActivity.EXTRA_REPS,reps);
                    editActivity.putExtra(UpdateNoteActivity.EXTRA_CATEGORY_ID, category_id);
                    editActivity.putExtra(UpdateNoteActivity.EXTRA_CATEGORY_TOTAL, category_total);
                    editActivity.putExtra(UpdateNoteActivity.EXTRA_CATEGORY_TITLE, category_title);
                    editActivity.putExtra(UpdateNoteActivity.EXTRA_TIME_CREATED, time_created);
                    editActivity.putExtra(UpdateNoteActivity.EXTRA_LAST_USED, last_usted);

                    startActivity(editActivity);
                    finish();
                }else {

                    addActivity.putExtra(UpdateNoteActivity.EXTRA_TIME,cr_value);
                    addActivity.putExtra(UpdateNoteActivity.EXTRA_TITLE, title);
                    addActivity.putExtra(UpdateNoteActivity.EXTRA_INFO, info);
                    addActivity.putExtra(UpdateNoteActivity.EXTRA_REPS,reps);

                    startActivity(addActivity);
                    finish();

                }
                break;

        }
    }




    public void setup_textwatcher(){
        et_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {



                switch (s.length()){
                    case 0:
                        tv_sec.setText("");
                        tv_day.setText("");
                        tv_min.setText("");
                        tv_hour.setText("");
                        value_new = 0;

                        switch (SharedPreferencesClass.retriveDataString(AddTimeActivity.this,EXTRA_RADIO_Value,"add")){

                            case "add":
                                format_time_HMS(value_new,tv_newValue,"New value: 0");
                                break;
                            case "sub":
                                format_time_HMS(value_new,tv_newValue,"New value: 0");
                                break;
                            case "new":
                                format_time_HMS(value_new,tv_newValue,"New value: 0");
                                break; }


                        format_time_HMS(0,tv_newValue,"New value: 0");

                        break;
                    case 1:
                        kk = s.toString().substring(0,1);
                        tv_sec.setText(kk+" s");
                        tv_day.setText("");
                        tv_min.setText("");
                        tv_hour.setText("");
                        sv_sec = Integer.parseInt(kk);
                        total_value = sv_sec;
                        value_sec = sv_sec;

                        switch (SharedPreferencesClass.retriveDataString(AddTimeActivity.this,EXTRA_RADIO_Value,"add")){

                            case "add":
                                    value_new = ((total_value)+(cr_value));
                                    format_time_HMS(value_new,tv_newValue,"New value:");
                                break;
                            case "sub":
                                if (total_value < cr_value)
                                { value_new = ((cr_value)-(total_value));
                                    format_time_HMS(value_new,tv_newValue,"New value:");
                                }else {
                                    value_new = ((total_value)-(cr_value));
                                    format_time_HMS(value_new,tv_newValue,"New value:");
                                }
                                break;
                            case "new":
                                value_new= total_value;
                                format_time_HMS(value_new,tv_newValue,"New value:");
                                break; }
                        break;


                    case 2:
                        kk1 = s.toString().substring(1,2);
                        tv_sec.setText(kk+kk1+" s");
                        tv_day.setText("");
                        tv_min.setText("");
                        tv_hour.setText("");
                        sv_sec = Integer.parseInt(kk+kk1);
                        total_value = sv_sec;
                        value_sec = sv_sec;

                        switch (SharedPreferencesClass.retriveDataString(AddTimeActivity.this,EXTRA_RADIO_Value,"add")){

                            case "add":
                                value_new = ((total_value)+(cr_value));
                                format_time_HMS(value_new,tv_newValue,"New value:");
                                break;
                            case "sub":
                                if (total_value < cr_value)
                                { value_new = ((cr_value)-(total_value));
                                    format_time_HMS(value_new,tv_newValue,"New value:");
                                }else {
                                    value_new = ((total_value)-(cr_value));
                                    format_time_HMS(value_new,tv_newValue,"New value:");
                                }
                                break;
                            case "new":
                                value_new= total_value;
                                format_time_HMS(value_new,tv_newValue,"New value:");
                                break; }
                        break;
                    case 3:

                        kk2 = s.toString().substring(2,3);
                        tv_day.setText("");
                        tv_hour.setText("");
                        tv_min.setText(kk+" m");
                        tv_sec.setText(kk1+kk2+" s");
                        sv_sec = Integer.parseInt(kk1+kk2);
                        sv_mins= Integer.parseInt(kk);
                        value_mins = (sv_mins * 60);
                        value_sec = sv_sec;
                        total_value = ((value_mins ) +(value_sec));

                        switch (SharedPreferencesClass.retriveDataString(AddTimeActivity.this,EXTRA_RADIO_Value,"add")){

                            case "add":
                                value_new = ((total_value)+(cr_value));
                                format_time_HMS(value_new,tv_newValue,"New value:");
                                break;
                            case "sub":
                                if (total_value < cr_value)
                                { value_new = ((cr_value)-(total_value));
                                    format_time_HMS(value_new,tv_newValue,"New value:");
                                }else {
                                    value_new = ((total_value)-(cr_value));
                                    format_time_HMS(value_new,tv_newValue,"New value:");
                                }
                                break;
                            case "new":
                                value_new= total_value;
                                format_time_HMS(value_new,tv_newValue,"New value:");
                                break; }

                        break;
                    case 4:
                        kk3 = s.toString().substring(3,4);
                        tv_day.setText("");
                        tv_hour.setText("");
                        tv_min.setText(kk+kk1+" m");
                        tv_sec.setText(kk2+kk3+" s");

                        sv_sec = Integer.parseInt(kk2+kk3);
                        sv_mins= Integer.parseInt(kk+kk1);
                        value_mins = (sv_mins * 60);
                        value_sec = sv_sec;
                        total_value = ((value_mins ) +(value_sec));
                        switch (SharedPreferencesClass.retriveDataString(AddTimeActivity.this,EXTRA_RADIO_Value,"add")){

                            case "add":
                                value_new = ((total_value)+(cr_value));
                                format_time_HMS(value_new,tv_newValue,"New value:");
                                break;
                            case "sub":
                                if (total_value < cr_value)
                                { value_new = ((cr_value)-(total_value));
                                    format_time_HMS(value_new,tv_newValue,"New value:");
                                }else {
                                    value_new = ((total_value)-(cr_value));
                                    format_time_HMS(value_new,tv_newValue,"New value:");
                                }
                                break;
                            case "new":
                                value_new= total_value;
                                format_time_HMS(value_new,tv_newValue,"New value:");
                                break; }

                        break;
                    case 5:
                        kk4 = s.toString().substring(4,5);
                        tv_day.setText("");
                        tv_hour.setText(kk+" h");
                        tv_min.setText(kk1+kk2+" m");
                        tv_sec.setText(kk3+kk4+" s");
                        sv_sec = Integer.parseInt(kk3+kk4);
                        sv_mins= Integer.parseInt(kk1+kk2);
                        sv_hour = Integer.parseInt(kk);
                        value_hour = (sv_hour * 3600);
                        value_mins = (sv_mins * 60);
                        value_sec = sv_sec;
                        total_value = ((value_hour) + (value_mins ) +(value_sec));
                        switch (SharedPreferencesClass.retriveDataString(AddTimeActivity.this,EXTRA_RADIO_Value,"add")){

                            case "add":
                                value_new = ((total_value)+(cr_value));
                                format_time_HMS(value_new,tv_newValue,"New value:");
                                break;
                            case "sub":
                                if (total_value < cr_value)
                                { value_new = ((cr_value)-(total_value));
                                    format_time_HMS(value_new,tv_newValue,"New value:");
                                }else {
                                    value_new = ((total_value)-(cr_value));
                                    format_time_HMS(value_new,tv_newValue,"New value:");
                                }
                                break;
                            case "new":
                                value_new= total_value;
                                format_time_HMS(value_new,tv_newValue,"New value:");
                                break; }

                        break;
                    case 6:
                        kk5 = s.toString().substring(5,6);

                        tv_day.setText("");
                        tv_hour.setText(kk+kk1+" h");
                        tv_min.setText(kk2+kk3+" m");
                        tv_sec.setText(kk4+kk5+" s");
                        sv_sec = Integer.parseInt(kk4+kk5);
                        sv_mins= Integer.parseInt(kk2+kk3);
                        sv_hour = Integer.parseInt(kk+kk1);
                        value_hour = (sv_hour * 3600);
                        value_mins = (sv_mins * 60);
                        value_sec = sv_sec;
                        total_value = ((value_hour) + (value_mins ) +(value_sec));
                        switch (SharedPreferencesClass.retriveDataString(AddTimeActivity.this,EXTRA_RADIO_Value,"add")){

                            case "add":
                                value_new = ((total_value)+(cr_value));
                                format_time_HMS(value_new,tv_newValue,"New value:");
                                break;
                            case "sub":
                                if (total_value < cr_value)
                                { value_new = ((cr_value)-(total_value));
                                    format_time_HMS(value_new,tv_newValue,"New value:");
                                }else {
                                    value_new = ((total_value)-(cr_value));
                                    format_time_HMS(value_new,tv_newValue,"New value:");
                                }
                                break;
                            case "new":
                                value_new= total_value;
                                format_time_HMS(value_new,tv_newValue,"New value:");
                                break; }

                        break;
                    case 7:
                        kk6 = s.toString().substring(6,7);

                        tv_day.setText(kk+" d");
                        tv_hour.setText(kk1+kk2+" h");
                        tv_min.setText(kk3+kk4+" m");
                        tv_sec.setText(kk5+kk6+" s");
                        sv_sec = Integer.parseInt(kk5+kk6);
                        sv_mins= Integer.parseInt(kk4+kk3);
                        sv_hour = Integer.parseInt(kk2+kk1);
                        sv_day = Integer.parseInt(kk);
                        value_day = (sv_day * 86400);
                        value_hour = (sv_hour * 3600);
                        value_mins = (sv_mins * 60);
                        value_sec = sv_sec;
                        total_value = ((value_day)+ (value_hour) + (value_mins ) +(value_sec));
                        switch (SharedPreferencesClass.retriveDataString(AddTimeActivity.this,EXTRA_RADIO_Value,"add")){

                            case "add":
                                value_new = ((total_value)+(cr_value));
                                format_time_HMS(value_new,tv_newValue,"New value:");
                                break;
                            case "sub":
                                if (total_value < cr_value)
                                { value_new = ((cr_value)-(total_value));
                                    format_time_HMS(value_new,tv_newValue,"New value:");
                                }else {
                                    value_new = ((total_value)-(cr_value));
                                    format_time_HMS(value_new,tv_newValue,"New value:");
                                }
                                break;
                            case "new":
                                value_new= total_value;
                                format_time_HMS(value_new,tv_newValue,"New value:");
                                break; }

                        break;
                    case 8:
                        kk7 = s.toString().substring(7,8);

                        tv_day.setText(kk+kk1+" d");
                        tv_hour.setText(kk2+kk3+" h");
                        tv_min.setText(kk4+kk5+" m");
                        tv_sec.setText(kk6+kk7+" s");
                        sv_sec = Integer.parseInt(kk6+kk7);
                        sv_mins= Integer.parseInt(kk4+kk5);
                        sv_hour = Integer.parseInt(kk2+kk3);
                        sv_day = Integer.parseInt(kk+kk1);
                        value_day = (sv_day * 86400);
                        value_hour = (sv_hour * 3600);
                        value_mins = (sv_mins * 60);
                        value_sec = sv_sec;
                        total_value = ((value_day)+ (value_hour) + (value_mins ) +(value_sec));
                        switch (SharedPreferencesClass.retriveDataString(AddTimeActivity.this,EXTRA_RADIO_Value,"add")){

                            case "add":
                                value_new = ((total_value)+(cr_value));
                                format_time_HMS(value_new,tv_newValue,"New value:");
                                break;
                            case "sub":
                                if (total_value < cr_value)
                                { value_new = ((cr_value)-(total_value));
                                    format_time_HMS(value_new,tv_newValue,"New value:");
                                }else {
                                    value_new = ((total_value)-(cr_value));
                                    format_time_HMS(value_new,tv_newValue,"New value:");
                                }
                                break;
                            case "new":
                                value_new= total_value;
                                format_time_HMS(value_new,tv_newValue,"New value:");
                                break; }

                        break;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    public void onRadioButtonClicked(View view) {

        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.DTIL_rd_add:
                if (checked)
                    SharedPreferencesClass.insertDataString(this,EXTRA_RADIO_Value,"add");

                    value_new = ((total_value)+(cr_value));
                format_time_HMS(value_new,tv_newValue,"New value:");

                break;
            case R.id.DTIL_rd_sub:
                if (checked)
                    if (total_value < cr_value)
                    { value_new = ((cr_value)-(total_value));
                        format_time_HMS(value_new,tv_newValue,"New value:");
                    }else {
                        value_new = ((total_value)-(cr_value));
                        format_time_HMS(value_new,tv_newValue,"New value:");
                    }
                SharedPreferencesClass.insertDataString(this,EXTRA_RADIO_Value,"sub");
                break;
            case R.id.DTIL_rd_new:
                if (checked)
                    value_new= total_value;
                format_time_HMS(value_new,tv_newValue,"New value:");

                SharedPreferencesClass.insertDataString(this,EXTRA_RADIO_Value,"new");
                break;




        }
    }


    public void format_time_HMS(long time, TextView textView, String text){
        int days_MT = (int) (time) / 86400;
        int hours_MT = (int) (time) / 3600 % 24;
        int minutes_MT = (int) (time) / 60 % 60;
        int seconds_MT = (int) (time) % 60;


        if (days_MT > 1) {
            days_f_time = "" + days_MT + " days";

        } else if ((days_MT == 1)) {
            days_f_time = "" + days_MT + " day";
        } else if (days_MT < 1) {
            days_f_time = "";
        }


        if (hours_MT > 1) {
            hours_f_time = "" + hours_MT + " hours";

        } else if ((hours_MT == 1)) {
            hours_f_time = "" + hours_MT + " hour";
        } else if (hours_MT < 1) {
            hours_f_time = "";
        }

        if (minutes_MT > 1) {
            mins_f_time = "" + minutes_MT + " mins";

        } else if ((minutes_MT == 1)) {
            mins_f_time = "" + minutes_MT + " min";
        } else if (minutes_MT < 1) {
            mins_f_time = "";
        }

        if (seconds_MT > 1) {
            sec_f_time = "" + seconds_MT + " secs";

        } else if ((seconds_MT == 1)) {
            sec_f_time = "" + seconds_MT + " sec";
        } else if (seconds_MT < 1) {
            sec_f_time = "";
        }

        String MT_time_hour_Mins_Secs = text+" " + days_f_time + " " + hours_f_time + " " + mins_f_time + " " + sec_f_time;

        textView.setText(MT_time_hour_Mins_Secs);
    }


}
