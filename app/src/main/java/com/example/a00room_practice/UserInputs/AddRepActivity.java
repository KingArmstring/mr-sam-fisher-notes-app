package com.example.a00room_practice.UserInputs;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a00room_practice.Activity.UpdateNoteActivity;
import com.example.a00room_practice.R;
import com.example.a00room_practice.Miscellaneous.SharedPreferencesClass;

public class AddRepActivity extends AppCompatActivity implements View.OnClickListener {
    public static String EXTRA_REPValue = "com.example.dialog_test_EXTRA_INT_REPVALUE";
    public static String EXTRA_RADIOREP_Value = "com.example.dialog_test_EXTRA_RADIOREP_VALUE";

    private int  cr_value,value_new,total_value;
    private EditText et_input;
    private Intent editActivity,addActivity,getdata;
    private TextView tv_currentValue,tv_newValue;
    private String title,info,time_created,last_usted;
    private int reps,id,category_id;
    private  long time;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rep_layout);

        setup_variable();
        setup_textwatcher();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {

            case R.id.ARL_btn_save:
                if (!et_input.getText().toString().isEmpty()) {


                    if (id > -1) {
                        editActivity.putExtra(UpdateNoteActivity.EXTRA_ID, id);
                        editActivity.putExtra(UpdateNoteActivity.EXTRA_TIME, time);
                        editActivity.putExtra(UpdateNoteActivity.EXTRA_TITLE, title);
                        editActivity.putExtra(UpdateNoteActivity.EXTRA_INFO, info);
                        editActivity.putExtra(UpdateNoteActivity.EXTRA_REPS, value_new);
                        editActivity.putExtra(UpdateNoteActivity.EXTRA_CATEGORY_ID, category_id);
                        editActivity.putExtra(UpdateNoteActivity.EXTRA_TIME_CREATED, time_created);
                        editActivity.putExtra(UpdateNoteActivity.EXTRA_LAST_USED, last_usted);

                        startActivity(editActivity);
                        finish();
                    } else {
                        editActivity.putExtra(UpdateNoteActivity.EXTRA_TIME, time);
                        editActivity.putExtra(UpdateNoteActivity.EXTRA_TITLE, title);
                        editActivity.putExtra(UpdateNoteActivity.EXTRA_INFO, info);
                        editActivity.putExtra(UpdateNoteActivity.EXTRA_REPS, value_new);
                        editActivity.putExtra(UpdateNoteActivity.EXTRA_CATEGORY_ID, category_id);
                        editActivity.putExtra(UpdateNoteActivity.EXTRA_TIME_CREATED, time_created);
                        editActivity.putExtra(UpdateNoteActivity.EXTRA_LAST_USED, last_usted);

                        startActivity(addActivity);
                        finish();

                    }
                }
                else {
                    Toast.makeText(this, "Input Empty", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.ARL_btn_cancel:
                if (id > -1 ) {
                    editActivity.putExtra(UpdateNoteActivity.EXTRA_ID,id);
                    editActivity.putExtra(UpdateNoteActivity.EXTRA_TIME,time);
                    editActivity.putExtra(UpdateNoteActivity.EXTRA_TITLE, title);
                    editActivity.putExtra(UpdateNoteActivity.EXTRA_INFO, info);
                    editActivity.putExtra(UpdateNoteActivity.EXTRA_REPS,cr_value);
                    editActivity.putExtra(UpdateNoteActivity.EXTRA_CATEGORY_ID, category_id);
                    editActivity.putExtra(UpdateNoteActivity.EXTRA_TIME_CREATED, time_created);
                    editActivity.putExtra(UpdateNoteActivity.EXTRA_LAST_USED, last_usted);

                    startActivity(editActivity);
                    finish();
                    Toast.makeText(this, "edit", Toast.LENGTH_SHORT).show();

                }else {

                    editActivity.putExtra(UpdateNoteActivity.EXTRA_TIME,time);
                    editActivity.putExtra(UpdateNoteActivity.EXTRA_TITLE, title);
                    editActivity.putExtra(UpdateNoteActivity.EXTRA_INFO, info);
                    editActivity.putExtra(UpdateNoteActivity.EXTRA_REPS,cr_value);
                    editActivity.putExtra(UpdateNoteActivity.EXTRA_CATEGORY_ID, category_id);
                    editActivity.putExtra(UpdateNoteActivity.EXTRA_TIME_CREATED, time_created);
                    editActivity.putExtra(UpdateNoteActivity.EXTRA_LAST_USED, last_usted);

                    startActivity(addActivity);
                    finish();
                    Toast.makeText(this, "add", Toast.LENGTH_SHORT).show();

                }
                break;

        }

    }

    public void setup_variable() {
        getdata = getIntent();
        cr_value = getdata.getIntExtra(UpdateNoteActivity.EXTRA_REPS,0);

        tv_currentValue = findViewById(R.id.ARL_tv_currentValue);
        tv_newValue = findViewById(R.id.ARL_tv_newValue);
        et_input = findViewById(R.id.ARL_edit_text);

        tv_currentValue.setText("Current value: "+cr_value);

        editActivity = new Intent(this, UpdateNoteActivity.class);
        addActivity = new Intent(this, AddNoteActivity.class);

        id = getdata.getIntExtra(UpdateNoteActivity.EXTRA_ID, -1);
        title = getdata.getStringExtra(UpdateNoteActivity.EXTRA_TITLE);
        info = getdata.getStringExtra(UpdateNoteActivity.EXTRA_INFO);
        time = getdata.getLongExtra(UpdateNoteActivity.EXTRA_TIME,0);
        category_id = getdata.getIntExtra(UpdateNoteActivity.EXTRA_CATEGORY_ID,0);
        time_created = getdata.getStringExtra(UpdateNoteActivity.EXTRA_TIME_CREATED);
        last_usted = getdata.getStringExtra(UpdateNoteActivity.EXTRA_LAST_USED);



    }

    public void setup_textwatcher(){
        et_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!(s.length() >0)) {
                    tv_newValue.setText("New value: ");
                }else {
                   String s_input = s.toString();
                    total_value = Integer.parseInt(s_input);

                    switch (SharedPreferencesClass.retriveDataString(AddRepActivity.this,EXTRA_RADIOREP_Value,"add")){

                        case "add":
                            value_new = ((total_value)+(cr_value));
                            tv_newValue.setText("New value: "+value_new);
                            break;
                        case "sub":
                            if (total_value < cr_value)
                            { value_new = ((cr_value)-(total_value));
                                tv_newValue.setText("New value: "+value_new);
                            }else {
                                value_new = ((total_value)-(cr_value));
                                tv_newValue.setText("New value: "+value_new);
                            }
                            break;
                        case "new":
                            value_new= total_value;
                            tv_newValue.setText("New value: "+value_new);
                            break; }



                }


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    public void RadioButton_addrep(View view) {

        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.ARL_rd_add:
                if (checked)
                    SharedPreferencesClass.insertDataString(this,EXTRA_RADIOREP_Value,"add");
                if (!et_input.getText().toString().isEmpty()){
                value_new = ((total_value)+(cr_value));
                tv_newValue.setText("New value: "+value_new);}else
                    {
                        tv_newValue.setText("New value: ");
                    }

                break;
            case R.id.ARL_rd_sub:
                if (checked)
                    if (!et_input.getText().toString().isEmpty())

                        if (total_value < cr_value)
                    { value_new = ((cr_value)-(total_value));
                        tv_newValue.setText("New value: "+value_new);

                    }else {
                        value_new = ((total_value)-(cr_value));
                        tv_newValue.setText("New value: "+value_new);

                    }
                SharedPreferencesClass.insertDataString(this,EXTRA_RADIOREP_Value,"sub");
                break;
            case R.id.ARL_rd_new:
                if (checked)
                    if (!et_input.getText().toString().isEmpty()){

                        value_new= total_value;
                tv_newValue.setText("New value: "+value_new);}else
                    {
                        tv_newValue.setText("New value: ");
                    }

                SharedPreferencesClass.insertDataString(this,EXTRA_RADIOREP_Value,"new");
                break;




        }




    }


}
