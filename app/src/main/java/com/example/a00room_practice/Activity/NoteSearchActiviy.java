package com.example.a00room_practice.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.a00room_practice.Adapters.SearchNotesAdapter;
import com.example.a00room_practice.ModelClass.AllNotes;
import com.example.a00room_practice.ModelClass.Category;
import com.example.a00room_practice.ModelClass.NoteUndo;
import com.example.a00room_practice.R;
import com.example.a00room_practice.RoomDatabase.NoteViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NoteSearchActiviy extends AppCompatActivity {
    private static final String TAG ="NoteSeachActiviy" ;
    public static final String EXTRA_SEARCH_UPDATE = "com.example.a00room_practice.EXTRA_SEACH_UPDATE";
    public static final String EXTRA_SEARCH_ADD = "com.example.a00room_practice.EXTRA_SEACH_ADD";
    public static String NAME_CATEGORY = "com.example.a00room_practice_NAME_CATEGORY";
    public ActionMode actionMode;
    public Boolean boolean_actionmode = false;


    NoteViewModel noteViewModel;
    Toolbar myToolbar;
    SearchNotesAdapter adapter = new SearchNotesAdapter(this, this);
    RecyclerView recyclerView;
    ArrayList<Integer> selectionList = new ArrayList<>();
    ArrayList<NoteUndo> insertList = new ArrayList<>();
    String title, info, categoryTitle,time_created,last_used;
    NoteUndo noteUndo,addnote;
    int ID, reps, categoryId, categoryTotal;
    Category category;
    long time;
    Intent getData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_notes);
        myToolbar = findViewById(R.id.my_toolbar);
        getData = getIntent();
        categoryTotal = getData.getIntExtra(CategoryActivity.EXTRA_CATEGORY_TOTAL,0);
        categoryId = getData.getIntExtra(CategoryActivity.EXTRA_CATEGORY_ID,0);
        categoryTitle =  getData.getStringExtra(CategoryActivity.EXTRA_CATEGORY_NAME);

        recyclerView = findViewById(R.id.NRV_recyclerview);
        setSupportActionBar(myToolbar);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        myToolbar.setNavigationIcon(R.drawable.ic_close_back_arrow);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        noteViewModel.findID(categoryId);

        noteViewModel.getCategorySearchResults().observe(NoteSearchActiviy.this, new Observer<List<AllNotes>>() {
            @Override
            public void onChanged(List<AllNotes> allNotes) {
                adapter.setNotes(allNotes);
            }
        });
        addNoteFromSelection();



        recyclerView.setAdapter(adapter);
        myToolbar.setTitle(categoryTitle);



        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        adapter.setOnItemClickListener(new SearchNotesAdapter.onItemClickListener() {
            @Override
            public void onItemClick(AllNotes notes) {
                if (notes!= null){

                }
            }
        });
    }

    // Check Box
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void MakeSelection(View v, int adapterPosition) {
        CheckBox mycheckbox = ((CheckBox) v);
        if (mycheckbox.isChecked()) {


            selectionList.add(adapterPosition);




            actionMode.setTitle("Selected " + insertList.size());

        } else {


            selectionList.removeIf(pos -> pos == adapterPosition);


          //  insert_list.removeIf(ID -> ID.getId() == adapterPosition);


            actionMode.setTitle("Selected " + insertList.size());

        }

    }

    // Action Mode
    public android.view.ActionMode.Callback actionModeCallback = new android.view.ActionMode.Callback() {

        // Called when the action mode is created; startActionMode() was called
        @Override
        public boolean onCreateActionMode(android.view.ActionMode mode, Menu menu) {
            // Inflate a menu resource providing context menu items
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.contextual_menu, menu);
            selectionList.clear();
            insertList.clear();


            mode.setTitle("Selected " + insertList.size());

            return true;
        }

        // Called each time the action mode is shown. Always called after onCreateActionMode, but
        // may be called multiple times if the mode is invalidated.
        @Override
        public boolean onPrepareActionMode(android.view.ActionMode mode, Menu menu) {
            return false; // Return false if nothing is done
        }

        // Called when the user selects a contextual menu item
        @Override
        public boolean onActionItemClicked(android.view.ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menu_delete:

                {




                    Snackbar.make(recyclerView, "Removed from category ", Snackbar.LENGTH_LONG)
                                    .setAction("Undo", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {



                                              // insert_list.clear();




                                        }


                                    }).show();



                    mode.finish();
                }


                    break;



                case R.id.menu_send:
                    Intent intent = new Intent(NoteSearchActiviy.this, CategoryActivity.class);
                    intent.putIntegerArrayListExtra(EXTRA_SEARCH_UPDATE, selectionList);
                    intent.putExtra(CategoryActivity.EXTRA_CATEGORY_ID, categoryId);
                    intent.putExtra(CategoryActivity.EXTRA_CATEGORY_NAME, categoryTitle);
                    intent.putExtra(CategoryActivity.EXTRA_CATEGORY_TOTAL, categoryTotal);

                    for (int i = 0; i < selectionList.size() ; i++) {

                        int num = selectionList.get(i);
                        Log.d(TAG, "Adapter positions : "+num);


                    }



                    startActivity(intent);
                    finish();
                    break;

                case R.id.menu_share:
                    if(insertList.size() == 1){

                        String Title =   noteUndo.getNoteTitle();
                        String Info = noteUndo.getNoteInfo();
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, Title+"\n"+Info);
                        sendIntent.setType("text/plain");

                        Intent shareIntent = Intent.createChooser(sendIntent, null);
                        startActivity(shareIntent);}else{
                        Toast.makeText(NoteSearchActiviy.this, "Selected one nore", Toast.LENGTH_SHORT).show();
                    }

            }
            return true;
        }

        // Called when the user exits the action mode
        @Override
        public void onDestroyActionMode(ActionMode mode) {
            boolean_actionmode = false;
            actionMode = null;
            adapter.notifyDataSetChanged();
            mode.setTitle("Selected " + insertList.size());
           // noteViewModel.findID(category_id);



        }
    };



    // Menu creation
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }


    // Menu On click
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);

        switch (item.getItemId()) {
            case R.id.c1:
                Intent intent = new Intent(this, CategoryActivity.class);
                startActivity(intent);

                break;

            case R.id.menu_add:

                break;



        }


        return true;
    }




    public void addNoteFromSelection() {
        if (getData.hasExtra(NoteActivity.EXTRA_ADAPTER_LIST)) {
            ArrayList<Integer> NotesAdapterPositions = getData.getIntegerArrayListExtra(NoteActivity.EXTRA_ADAPTER_LIST);








        }


    }


    // Update category size
    public void updateCategorySize(int idNum, String categoryTitle){





    }


}
