package com.example.a00room_practice.Adapters;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.example.a00room_practice.ModelClass.Category;
import com.example.a00room_practice.ModelClass.Note;

import java.util.List;

public  class CategoryDiffUtils extends DiffUtil.Callback {
    private List<Category> oldList;
    private List<Category> newList;

    public CategoryDiffUtils(List<Category> oldList, List<Category> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {

        return oldList.get(oldItemPosition).getCategoryId() == newList.get(newItemPosition).getCategoryId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getCategoryId() == newList.get(newItemPosition).getCategoryId() &&
                oldList.get(oldItemPosition).getCategoryTitle().equals( newList.get(newItemPosition).getCategoryTitle()) &&
                oldList.get(oldItemPosition).getCategoryPosition()==( newList.get(newItemPosition).getCategoryPosition()) &&
                oldList.get(oldItemPosition).getCategoryAmount()==( newList.get(newItemPosition).getCategoryAmount());


    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
