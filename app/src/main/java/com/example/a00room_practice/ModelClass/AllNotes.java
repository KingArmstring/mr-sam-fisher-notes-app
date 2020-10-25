package com.example.a00room_practice.ModelClass;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

public class AllNotes {

    private int nacId;
    private  int noteId;
    private  int categoryId;
    private String noteTitle;
    private String noteInfo;
    private String noteTimeCreated;
    private int noteReps;
    private long noteTotalTime;
    private String noteLastUsed;
    private String categoryTitle;
    private int categoryAmount;
    private boolean selected;


    public AllNotes(int nacId, int noteId, int categoryId, String noteTitle, String noteInfo, String noteTimeCreated, int noteReps, long noteTotalTime, String noteLastUsed, String categoryTitle, int categoryAmount, boolean selected) {
        this.nacId = nacId;
        this.noteId = noteId;
        this.categoryId = categoryId;
        this.noteTitle = noteTitle;
        this.noteInfo = noteInfo;
        this.noteTimeCreated = noteTimeCreated;
        this.noteReps = noteReps;
        this.noteTotalTime = noteTotalTime;
        this.noteLastUsed = noteLastUsed;
        this.categoryTitle = categoryTitle;
        this.categoryAmount = categoryAmount;
        this.selected = selected;
    }

    public int getNacId() {
        return nacId;
    }

    public void setNacId(int nacId) {
        this.nacId = nacId;
    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }


    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteInfo() {
        return noteInfo;
    }

    public void setNoteInfo(String noteInfo) {
        this.noteInfo = noteInfo;
    }

    public String getNoteTimeCreated() {
        return noteTimeCreated;
    }

    public void setNoteTimeCreated(String noteTimeCreated) {
        this.noteTimeCreated = noteTimeCreated;
    }

    public int getNoteReps() {
        return noteReps;
    }

    public void setNoteReps(int noteReps) {
        this.noteReps = noteReps;
    }

    public long getNoteTotalTime() {
        return noteTotalTime;
    }

    public void setNoteTotalTime(long noteTotalTime) {
        this.noteTotalTime = noteTotalTime;
    }

    public String getNoteLastUsed() {
        return noteLastUsed;
    }

    public void setNoteLastUsed(String noteLastUsed) {
        this.noteLastUsed = noteLastUsed;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public int getCategoryAmount() {
        return categoryAmount;
    }

    public void setCategoryAmount(int categoryAmount) {
        this.categoryAmount = categoryAmount;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
