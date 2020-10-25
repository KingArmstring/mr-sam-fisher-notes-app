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
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.a00room_practice.Adapters.NoteAdapter;
import com.example.a00room_practice.ModelClass.Note;
import com.example.a00room_practice.ModelClass.NoteUndo;
import com.example.a00room_practice.R;
import com.example.a00room_practice.RoomDatabase.NoteViewModel;
import com.example.a00room_practice.UserInputs.AddNoteActivity;
import com.example.a00room_practice.contracts.NotesContract;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class NoteActivity extends AppCompatActivity implements NotesContract {
    private static final String TAG =NoteActivity.class.getSimpleName() ;
    public ActionMode actionMode;
    public static String EXTRA_ADAPTER_LIST = "com.example.a00room_practice.EXTRA_ADAPTER_LIST";
    public static String EXTRA_CATEGORY_ID_LIST = "com.example.a00room_practice.EXTRA_CATEGORY_ID_LIST";
    public static String EXTRA_CATEGORY_TOTAL_LIST = "com.example.a00room_practice.EXTRA_CATEGORY_TOTAL_LIST";
    public static String EXTRA_CATEGORY_TITLE_LIST = "com.example.a00room_practice.EXTRA_CATEGORY_TITLE_LIST";




    NoteViewModel noteViewModel;
    Toolbar myToolbar;
    NoteAdapter adapter = new NoteAdapter(this, this, this);
    public Boolean boolean_actionmode = false, boolean_statistics = false;

    RecyclerView recyclerView;
    ArrayList<Integer> mSelection_list = new ArrayList<>();
    ArrayList<Integer> idlist = new ArrayList<>();
    ArrayList<Integer> totallist = new ArrayList<>();
    ArrayList<String> titlelist = new ArrayList<>();
    ArrayList<NoteUndo> mInsert_list = new ArrayList<>();
    NoteUndo noteUndo;
    ImageView emptymessage;
    List<Note> mainList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_notes);
        recyclerView = findViewById(R.id.NRV_recyclerview);
        emptymessage = findViewById(R.id.NRV_emptymessge);
        myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        myToolbar.setNavigationIcon(R.drawable.ic_close_back_arrow);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);

        noteViewModel.getAllNotes().observe(NoteActivity.this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                if (notes.size() < 1){
                    emptymessage.setVisibility(View.VISIBLE);
                }else {
                    emptymessage.setVisibility(View.GONE);

                }
                mainList.clear();
                mainList.addAll(notes);
                myNotifyDataSetChanged(false);
            }
        });
        recyclerView.setAdapter(adapter);
        myToolbar.setTitle("All Notes");
        Buttons();
    }




    // Check Box
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void MakeSelection(View v, int adapterPosition) {
        CheckBox mycheckbox = ((CheckBox) v);
        if (mycheckbox.isChecked()) {

            int noteId = noteViewModel.getAllNotes().getValue().get(adapterPosition).getNoteId();
            String noteTitle = noteViewModel.getAllNotes().getValue().get(adapterPosition).
                    getNoteTitle();
            String noteInfo = noteViewModel.getAllNotes().getValue().get(adapterPosition).
                    getNoteInfo();
            int noteReps = noteViewModel.getAllNotes().getValue().get(adapterPosition)
                    .getNoteReps();
            long noteTotalTime = noteViewModel.getAllNotes().getValue().get(adapterPosition)
                    .getNoteTotalTime();
            String noteTimeCreated = noteViewModel.getAllNotes().getValue().get(adapterPosition)
                    .getNoteTimeCreated();
            String noteLastUsed = noteViewModel.getAllNotes().getValue().get(adapterPosition)
                    .getNoteLastUsed();
            mSelection_list.add(adapterPosition);
            idlist.add(noteId);
            noteUndo = new NoteUndo(noteId, noteTitle, noteInfo, noteTimeCreated, noteReps,
                    noteTotalTime, noteLastUsed);
            mInsert_list.add(noteUndo);
            actionMode.setTitle("Selected " + mInsert_list.size());
        } else {
            mSelection_list.removeIf(pos -> pos == adapterPosition);
            mInsert_list.removeIf(ID -> ID.getNoteId() == adapterPosition);
            actionMode.setTitle("Selected " + mInsert_list.size());
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
            mSelection_list.clear();
            mInsert_list.clear();


            mode.setTitle("Selected " + mInsert_list.size());

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

                    if (mInsert_list.size() > 0) {

                        for (int i: mSelection_list) {


                            noteViewModel.delete(adapter.getNoteAt(i));

                            boolean_actionmode = false;
                            Snackbar.make(recyclerView, "deleted ", Snackbar.LENGTH_LONG)
                                    .setAction("Undo", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            for (int i = 0; i < mInsert_list.size(); i++) {
                                                int mNoteId = mInsert_list.get(i).getNoteId();
                                                String mTitle = mInsert_list.get(i).getNoteTitle();
                                                String mInfo = mInsert_list.get(i).getNoteInfo();
                                                int mReps = mInsert_list.get(i).getNoteReps();
                                                long mTime = mInsert_list.get(i).getNoteTotalTime();
                                                String mTimeCreated = mInsert_list.get(i).getNoteTimeCreated();
                                                String mLastUsed = mInsert_list.get(i).getNoteLastUsed();
                                                Note note = new Note(mTitle, mInfo, mReps, mTime, mLastUsed, mTimeCreated);
                                                noteViewModel.insert(note);
                                                mode.finish();
                                            }
                                            mInsert_list.clear();
                                        }
                                    }).show();

                            mode.finish();
                        }
                    }else {
                        Toast.makeText(NoteActivity.this, "Selected a note ", Toast.LENGTH_SHORT).show();
                    }
                    break;


                case R.id.menu_send:
                    if(mInsert_list.size() > 0) {
                        Intent intent = new Intent(NoteActivity.this, CategoryActivity.class);
                        intent.putIntegerArrayListExtra(EXTRA_ADAPTER_LIST, mSelection_list);
                        intent.putIntegerArrayListExtra(EXTRA_CATEGORY_ID_LIST, idlist);
                        intent.putIntegerArrayListExtra(EXTRA_CATEGORY_TOTAL_LIST,totallist);
                        intent.putStringArrayListExtra(EXTRA_CATEGORY_TITLE_LIST,titlelist);
                        startActivity(intent);


                        actionMode.finish();
                    }else {
                        Toast.makeText(NoteActivity.this, "Selected a note ", Toast.LENGTH_SHORT).show();
                    }
                    break;

                case R.id.menu_share:
                    if(mInsert_list.size() == 1){

                        String Title =   noteUndo.getNoteTitle();
                        String Info = noteUndo.getNoteInfo();
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, Title+"\n"+Info);
                        sendIntent.setType("text/plain");

                        Intent shareIntent = Intent.createChooser(sendIntent, null);
                        startActivity(shareIntent);}else{
                        Toast.makeText(NoteActivity.this, "Selected one nore", Toast.LENGTH_SHORT).show();
                    }
                    break;

                case R.id.menu_test:
                    Set<Integer> set = new HashSet<>(mSelection_list);
                    mSelection_list.clear();
                    mSelection_list.addAll(set);
                    Log.d(TAG, "onActionItemClicked() returned: " + mSelection_list.size());
                    break;
            }
            return true;
        }

        // Called when the user exits the action mode
        @Override
        public void onDestroyActionMode(ActionMode mode) {
            boolean_actionmode = false;
            actionMode = null;
            mode.setTitle("Selected " + mInsert_list.size());
            myNotifyDataSetChanged(false);
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
                finish();

                break;

            case R.id.menu_add:
                Intent intent1 = new Intent(this, AddNoteActivity.class);
                startActivity(intent1);
                break;

            case R.id.c2:
                boolean_statistics = false;
                adapter.notifyDataSetChanged();
                break;

            case R.id.c3:
                boolean_statistics = true;
                adapter.notifyDataSetChanged();
                break;
        }


        return true;
    }

    //On click buttons
    public void Buttons() {

        adapter.setOnItemClickListener(note -> {
            if (actionMode == null) {
            }
        });


        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void myNotifyDataSetChanged(boolean isActionMode) {
        //new list creation.
        List<Note> newList = new ArrayList<>();
        for (int i = 0; i < mainList.size(); i++) {
            Note tempNote = new Note(
                    mainList.get(i).getNoteTitle(),
                    mainList.get(i).getNoteInfo(),
                    mainList.get(i).getNoteReps(),
                    mainList.get(i).getNoteTotalTime(),
                    mainList.get(i).getNoteLastUsed(),
                    mainList.get(i).getNoteTimeCreated()
            );
            tempNote.setInActionMode(isActionMode);
            tempNote.setNoteId(mainList.get(i).getNoteId());
            newList.add(tempNote);
        }
        adapter.diffUtilChangeData(newList);
    }
}

