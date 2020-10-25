package com.example.a00room_practice.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
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
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a00room_practice.Adapters.CategoryAdapterAllNotes;
import com.example.a00room_practice.Miscellaneous.DialogClass;
import com.example.a00room_practice.Adapters.ItemTouchHelperAdpater;
import com.example.a00room_practice.ModelClass.CategoryUndo;
import com.example.a00room_practice.ModelClass.Category;
import com.example.a00room_practice.ModelClass.Note;
import com.example.a00room_practice.R;
import com.example.a00room_practice.RoomDatabase.NoteViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class CategoryActivity extends AppCompatActivity implements DialogClass.CategoryDialogListener, CategoryAdapterAllNotes.Dragtestlistener,
        ItemTouchHelperAdpater.AnimationListerner
{
    private static final String TAG = "CategoryActivity";
    public ActionMode actionMode;
    public static final String EXTRA_NOTE_ID = "com.example.a00room_practice.EXTRA_NOTE_ID";
    public static final String EXTRA_CATEGORY = "com.example.a00room_practice.EXTRA_CATEGORY";
    public static final String EXTRA_CATEGORY_TOTAL = "com.example.a00room_practice.EXTRA_CATEGORY_TOTAL";
    public static final String EXTRA_CATEGORY_NAME = "com.example.a00room_practice.EXTRA_CATEGORY_NAME";
    public static final String EXTRA_CATEGORY_ID = "com.example.a00room_practice.EXTRA_CATEGORY_ID";
    public static final String EXTRA_CATEGORY_POSITION = "com.example.a00room_practice.EXTRA_CATEGORY_POSTION";
    public Boolean boolean_actionmode;
    public ArrayList<Integer> selection_list = new ArrayList<>();
    private ArrayList<Integer> fromPosArray = new ArrayList<>();
    private ArrayList<Integer> toPosArray = new ArrayList<>();
    private ItemTouchHelper mItemTouchHelper;
    private  int maxnum = 0;
    private NoteViewModel noteViewModel;
    private Toolbar myToolbar;
    private CategoryAdapterAllNotes adapter = new CategoryAdapterAllNotes(boolean_actionmode = false, this, this);
    private RecyclerView recyclerView;
     ArrayList<CategoryUndo> insertListNote = new ArrayList<>();


    CategoryUndo categoryUndo, addnote;
    TextView emptymessage;
    Intent data;
    Intent getData;
    DialogClass dialog_class = new DialogClass();
    TextView textView;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category__recycler_view);
        data = new Intent(CategoryActivity.this, NoteSearchActiviy.class);
        recyclerView = findViewById(R.id.NRV_recyclerview);
        emptymessage = findViewById(R.id.NRV_emptymessge);
        myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        myToolbar.setNavigationIcon(R.drawable.ic_close_back_arrow);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        getData = getIntent();
        textView = findViewById(R.id.ACL_textView);


   // Used to get the selected adapter position and insert them to a list to use to updated the note
         addNoteFromSelection();

        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(this, R.drawable.border)));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));


        noteViewModel.getAllCategories().observe(this, categories -> {
            if (categories.size() < 1) {
                emptymessage.setVisibility(View.VISIBLE);
            } else {
                emptymessage.setVisibility(View.GONE);
            }

            //adapter.setCategories(categories);
            adapter.diffUtilChangeData(categories);


        });

        myToolbar.setTitle("Categories");

        recyclerView.setAdapter(adapter);

       ItemTouchHelper.Callback callback = new ItemTouchHelperAdpater(this,this);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);
        adapter.setDragtestlistener(this);



        adapter.setOnItemClickListener(new CategoryAdapterAllNotes.onItemClickListener() {
            @Override
            public void onItemClick(Category category) {
                if (category!= null){
                    data.putExtra(EXTRA_CATEGORY_ID,category.getCategoryId());
                    data.putExtra(EXTRA_CATEGORY_NAME,category.getCategoryTitle());
                    data.putExtra(EXTRA_CATEGORY_TOTAL,category.getCategoryAmount());
                    startActivity(data);
                }

            }
        });





        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });




    }


    // Check Box
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void MakeSelection(View v, int adapterPosition) {
        CheckBox mycheckbox = ((CheckBox) v);
        if (mycheckbox.isChecked()) {


          int mCategoryTotal = noteViewModel.getAllCategories().getValue().get(adapterPosition).getCategoryId();
          int mCategoryID = noteViewModel.getAllCategories().getValue().get(adapterPosition).getCategoryId();


            selection_list.add(adapterPosition);

            Toast.makeText(this, "" + adapterPosition, Toast.LENGTH_SHORT).show();


            categoryUndo = new CategoryUndo(adapterPosition, mCategoryTotal, mCategoryID);
            insertListNote.add(categoryUndo);

            actionMode.setTitle("Selected " + selection_list.size());

        } else {


            selection_list.removeIf(pos -> pos == adapterPosition);


            insertListNote.removeIf(ID -> ID.getId() == adapterPosition);


            actionMode.setTitle("Selected " + selection_list.size());

        }

    }

    // Action Mode
    public android.view.ActionMode.Callback actionModeCallback = new android.view.ActionMode.Callback() {

        // Called when the action mode is created; startActionMode() was called
        @Override
        public boolean onCreateActionMode(android.view.ActionMode mode, Menu menu) {
            // Inflate a menu resource providing context menu items
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.contextual_menu_category, menu);
            selection_list.clear();
            insertListNote.clear();

            mode.setTitle("Selected " + insertListNote.size());

            if (actionMode!= null && selection_list.size()>4){
                            Toast.makeText(CategoryActivity.this, "working", Toast.LENGTH_SHORT).show();
            }

            return true;
        }

        // Called each time the action mode is shown. Always called after onCreateActionMode, but
        // may be called multiple times if the mode is invalidated.
        @Override
        public boolean onPrepareActionMode(android.view.ActionMode mode, Menu menu) {
            return true; // Return false if nothing is done
        }

        // Called when the user selects a contextual menu item
        @Override
        public boolean onActionItemClicked(android.view.ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menu_delete:

//Get the adapter position from selection list and remove it using getCategoryAt method
                    if (selection_list.size() > 0) {
                        for (int i : selection_list) {
                            noteViewModel.delete(adapter.getcategoryAt(i));
                         //   adapter.notifyItemRemoved(i);
                        }

                     // Action picked, so close the CAB
                        mode.finish();

                    }
                    break;

                case R.id.menu_rename:
                    if (selection_list.size() == 1) {
                        int categoryId = Objects.requireNonNull(noteViewModel.getAllCategories().getValue()).get(selection_list.get(0)).getCategoryId();
                        String categoryTitle = noteViewModel.getAllCategories().getValue().get(selection_list.get(0)).getCategoryTitle();
                        int categoryPosition = noteViewModel.getAllCategories().getValue().get(selection_list.get(0)).getCategoryPosition();

                        data.putExtra(EXTRA_CATEGORY, categoryTitle);
                        data.putExtra(EXTRA_NOTE_ID, categoryId);
                        data.putExtra(EXTRA_CATEGORY_POSITION, categoryPosition);


                        dialog_class.show(getSupportFragmentManager(), null);

                        mode.finish();

                    } else {
                        Toast.makeText(CategoryActivity.this,
                                "Select one Category", Toast.LENGTH_SHORT).show();

                    }



                    break;



            }
            return true;
        }

        // Called when the user exits the action mode
        @Override
        public void onDestroyActionMode(ActionMode mode) {

            boolean_actionmode = false;
            actionMode = null;
            mode.setTitle("Selected " + insertListNote.size());
            updateCategoryPos();
            adapter.notifyDataSetChanged();






        }


    };


    @Override
    public void onmove(int fromPos, int toPos) {

        noteViewModel.getAllCategories().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                Collections.swap(categories, fromPos, toPos);

                adapter.notifyItemMoved(fromPos, toPos);
            }
        });

        fromPosArray.add(fromPos);
        toPosArray.add(toPos);
    }


    // Menu creation
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu_category, menu);

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
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menu_add:
                ArrayList<Integer> categoryIdArrayList = new ArrayList<>();
                ArrayList<Integer> categoryTotalArrayList = new ArrayList<>();
                ArrayList<String> categoryTitleArrayList = new ArrayList<>();
                dialog_class.show(getSupportFragmentManager(), null);
                data.putExtra(EXTRA_NOTE_ID, -1);
                noteViewModel.getMax().observe(CategoryActivity.this, new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer integer) {
                        if (integer != null) {
                            maxnum = integer;
                        } else {
                            maxnum = 1;
                        }
                    }
                });

                // List of adapter positions from Note Activity

                if (getData.hasExtra(NoteActivity.EXTRA_ADAPTER_LIST)) {

// Get the list and use a loop to get the note by the adapter position
                    ArrayList<Integer> NoteAdapterPositions = getData.getIntegerArrayListExtra(NoteActivity.EXTRA_ADAPTER_LIST);
                    noteViewModel.getAllNotes().observe(CategoryActivity.this, new Observer<List<Note>>() {
                        @RequiresApi(api = Build.VERSION_CODES.N)
                        @Override
                        public void onChanged(List<Note> notes) {
                            for (int i : NoteAdapterPositions) {
                                int mNoteId = notes.get(i).getNoteId();


                            }
                        }
                    });
                    Map<Integer, Integer> idmap = new HashMap<>();

                    for (Integer idNum : categoryIdArrayList) {
                        Integer count = idmap.get(idNum);
                        if (count == null) {
                            idmap.put(idNum, 1);
                        } else {
                            idmap.put(idNum, ++count);

                        }
                    }
                    Set<Map.Entry<Integer, Integer>> entrySet = idmap.entrySet();
                    for (Map.Entry<Integer, Integer> entry : entrySet) {
                        if (entry.getKey() > 0) {


                        }
                    }


                }


                break;


            case R.id.menu_view:
                data = new Intent(this, NoteActivity.class);
                startActivity(data);
                finish();

                break;

            case R.id.menu_edit_pos:

                Intent intent = new Intent(CategoryActivity.this, CategoryReorderList.class);
                startActivity(intent);

                break;

        }


        return true;
    }


    // Dialog Buttons
    @Override
    public void onDialogPositiveClick(DialogClass dialog){
        ArrayList<Integer> NoteAdapterPositions = getData.getIntegerArrayListExtra(NoteActivity.EXTRA_ADAPTER_LIST);
        String dialogInput = dialog.editText.getText().toString().trim();
        int categoryId = data.getIntExtra(EXTRA_NOTE_ID, -1);
        int categoryTotal = data.getIntExtra(EXTRA_CATEGORY_TOTAL, 0);
        int categoryPosition = data.getIntExtra(EXTRA_CATEGORY_POSITION,0);

        if (!dialogInput.isEmpty()) {
            // Use to Updated note by checking for categoryId
            if (categoryId > -1) {
                Category category1 = new Category(dialogInput,categoryTotal,categoryPosition);
                category1.setCategoryId(categoryId);
                noteViewModel.update(category1);

            }else {
                // Use to insert new category with notes by checking for an intent or a new category
                insertCategory(dialogInput);
            }


        }
    }


    @Override
    public void onDialogNegativeClick(DialogClass dialog) {

    }

    @Override
    public void Edit_text(EditText input) {
        int id = data.getIntExtra(EXTRA_NOTE_ID, -2);

        if (id >= 1) {
            input.setText(data.getStringExtra(EXTRA_CATEGORY));
        } else {
            input.setText("");
        }

    }


    @Override
    public void onDragStarted(CategoryAdapterAllNotes.NoteHolder noteHolder) {

        mItemTouchHelper.startDrag(noteHolder);

    }






    // Add notes from menu to a New category on click
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void addNotesToNewCategory(){
        ArrayList<Integer> NoteAdapterPositions = getData.getIntegerArrayListExtra(NoteActivity.EXTRA_ADAPTER_LIST);

// list from notes add when user clicks add button and had notes selected

 // Updated notes with the newly created category Id




// Go to newly created category







    }





        // Add notes from Onclick to a existing category
    public void addNoteFromSelection() {


        }



        // Update category size
    public void updateCategorySize(int idNum, String categoryTitle){



}

    // Get category total
    public void getCategoryTotal(int categoryIdNum){

        }


    //Update category position
    public  void updateCategoryPos(){
        if (toPosArray.size() > 0) {

            for (int i = 0; i < toPosArray.size(); i++) {


                Category category1 = new Category(adapter.getcategoryAt(fromPosArray.get(i)).getCategoryTitle(), adapter.getcategoryAt(fromPosArray.get(i)).getCategoryAmount(), (toPosArray.get(i) + (fromPosArray.get(i) - (toPosArray.get(i)))));
                category1.setCategoryId(adapter.getcategoryAt(fromPosArray.get(i)).getCategoryId());
                noteViewModel.update(category1);

                Category category = new Category(adapter.getcategoryAt(toPosArray.get(i)).getCategoryTitle(), adapter.getcategoryAt(toPosArray.get(i)).getCategoryAmount(), (fromPosArray.get(i) + (toPosArray.get(i) - (fromPosArray.get(i)))));
                category.setCategoryId(adapter.getcategoryAt(toPosArray.get(i)).getCategoryId());
                noteViewModel.update(category);





            }


        }


    }
    public void insertCategory(String input){
        noteViewModel.getMax().observe(CategoryActivity.this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer != null){
                    maxnum = integer;
                }else {maxnum = 0;}




            }
        });


        Category category = new Category(input,0,(++maxnum));

                        // Use to insert new category with notes by checking for an intent
                     if (getData.hasExtra(NoteActivity.EXTRA_ADAPTER_LIST)) {
                        noteViewModel.insert(category);

                        // Use to get newly created categoryId and to update notes
                        new Timer().schedule(new TimerTask() {
                            @RequiresApi(api = Build.VERSION_CODES.N)
                            @Override
                            public void run() {
                                addNotesToNewCategory();

                            }
                        }, 500L);

                        // Use to create a new category
                    } else {
                        noteViewModel.insert(category);
                    }

    }


}

