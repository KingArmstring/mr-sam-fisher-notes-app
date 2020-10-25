package com.example.a00room_practice.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.a00room_practice.Adapters.ItemTouchHelperAdpater;
import com.example.a00room_practice.Adapters.PracticeAdapter;
import com.example.a00room_practice.ModelClass.Category;
import com.example.a00room_practice.R;
import com.example.a00room_practice.RoomDatabase.NoteViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class CategoryReorderList extends AppCompatActivity implements PracticeAdapter.Dragtestlistener,ItemTouchHelperAdpater.AnimationListerner {
    private static final String TAG = CategoryReorderList.class.getSimpleName() ;
    NoteViewModel noteViewModel;
    PracticeAdapter adapter = new PracticeAdapter();
    RecyclerView recyclerView;
    ItemTouchHelper mItemTouchHelper;
    ArrayList<Integer> fromPosArray = new ArrayList<>();
    ArrayList<Integer> toPosArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        recyclerView = findViewById(R.id.NRV_recyclerview);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(this, R.drawable.border)));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));


        ItemTouchHelper.Callback callback = new ItemTouchHelperAdpater(this,this);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);
        adapter.setDragtestlistener(CategoryReorderList.this);
        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);




        noteViewModel.getAllCategories().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {

              adapter.setCategories(categories);
                }
        });

        recyclerView.setAdapter(adapter);



    }

    @Override
    public void onDragStarted(PracticeAdapter.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);

    }

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

    public void  updatePosition(View view){
            if (toPosArray.size() > 0) {
                finish();
                for (int i = 0; i < toPosArray.size(); i++) {


                    Category category1 = new Category(adapter.getCategoryAt(fromPosArray.get(i)).getCategoryTitle(), adapter.getCategoryAt(fromPosArray.get(i)).getCategoryAmount(), (toPosArray.get(i) + (fromPosArray.get(i) - (toPosArray.get(i)))));
                    category1.setCategoryId(adapter.getCategoryAt(fromPosArray.get(i)).getCategoryId());
                    noteViewModel.update(category1);

                    Category category = new Category(adapter.getCategoryAt(toPosArray.get(i)).getCategoryTitle(), adapter.getCategoryAt(toPosArray.get(i)).getCategoryAmount(), (fromPosArray.get(i) + (toPosArray.get(i) - (fromPosArray.get(i)))));
                    category.setCategoryId(adapter.getCategoryAt(toPosArray.get(i)).getCategoryId());
                    noteViewModel.update(category);





                }


            }


        }



}

