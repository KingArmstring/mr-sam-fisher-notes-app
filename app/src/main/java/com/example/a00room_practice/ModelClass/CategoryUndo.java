package com.example.a00room_practice.ModelClass;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;





public class CategoryUndo {


    private  int Id;

    private int categoryTotal;


    private  int categoryId;



    private boolean selected;

    public CategoryUndo(int id, int categoryTotal, int categoryId) {
        Id = id;
        this.categoryTotal = categoryTotal;
        this.categoryId = categoryId;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getCategoryTotal() {
        return categoryTotal;
    }

    public void setCategoryTotal(int categoryTotal) {
        this.categoryTotal = categoryTotal;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
