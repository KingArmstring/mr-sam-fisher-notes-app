package com.example.a00room_practice.ModelClass;

import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.example.a00room_practice.Adapters.PracticeAdapter;


@Entity(tableName = "category")
public class Category {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "categoryId")
    private  int categoryId;


    @ColumnInfo(name = "categoryTitle")
    private String categoryTitle;


    @ColumnInfo(name = "categoryAmount")
    private  int categoryAmount;

    @ColumnInfo(name = "categoryPosition")
    private  int categoryPosition;

    private boolean selected;


    public Category( String categoryTitle, int categoryAmount, int categoryPosition) {
        this.categoryTitle = categoryTitle;
        this.categoryAmount = categoryAmount;
        this.categoryPosition = categoryPosition;
    }

    public int getCategoryAmount() {
        return categoryAmount;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public void setCategoryAmount(int categoryAmount) {
        this.categoryAmount = categoryAmount;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public int getCategoryPosition() {
        return categoryPosition;
    }

    public void setCategoryPosition(int categoryPosition) {
        this.categoryPosition = categoryPosition;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
