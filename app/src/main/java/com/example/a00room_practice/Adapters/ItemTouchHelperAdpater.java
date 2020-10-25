package com.example.a00room_practice.Adapters;

import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a00room_practice.Activity.CategoryActivity;
import com.example.a00room_practice.Activity.CategoryReorderList;

public class ItemTouchHelperAdpater extends ItemTouchHelper.Callback {

    private static final String TAG = "ItemTouchHelperAdpater";
    AnimationListerner animationListerner;
    CategoryActivity categoryActivity;
    CategoryReorderList categoryReorderList;



    public ItemTouchHelperAdpater(AnimationListerner animationListerner, CategoryActivity categoryActivity) {
        this.animationListerner = animationListerner;
        this.categoryActivity = categoryActivity;
    }




    public ItemTouchHelperAdpater(AnimationListerner animationListerner, CategoryReorderList categoryReorderList) {
        this.animationListerner = animationListerner;
        this.categoryReorderList = categoryReorderList;
    }



    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {


        return makeMovementFlags(ItemTouchHelper.UP|ItemTouchHelper.DOWN,0);

    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        if (animationListerner !=null){
            animationListerner.onmove(viewHolder.getAdapterPosition(),target.getAdapterPosition());




        }
        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

    }


    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }






    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
        if (actionState == ItemTouchHelper.ACTION_STATE_DRAG){
            assert viewHolder != null;
            viewHolder.itemView.setBackgroundColor(Color.LTGRAY);

        }

    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        viewHolder.itemView.setBackgroundColor(Color.WHITE);
    }

    public  interface AnimationListerner{
        void onmove(int frompos, int topos);


    }

}
