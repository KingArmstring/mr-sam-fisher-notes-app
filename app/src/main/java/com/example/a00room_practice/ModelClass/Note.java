package com.example.a00room_practice.ModelClass;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "note")

public class Note {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "noteId")
    private  int noteId;

    @ColumnInfo(name = "noteTitle")
    private String noteTitle;

    @ColumnInfo(name = "noteInfo")
    private String noteInfo;


    @ColumnInfo(name = "noteReps")
    private  int noteReps;

    @ColumnInfo(name = "noteTotalTime")
    private  long noteTotalTime;

    @ColumnInfo(name = "noteLastUsed")
    private  String noteLastUsed;

    @ColumnInfo(name = "noteTimeCreated")
    private String noteTimeCreated;

    private boolean selected;

    private boolean isInActionMode;

    public Note(String noteTitle, String noteInfo, int noteReps, long noteTotalTime, String noteLastUsed, String noteTimeCreated) {
        this.noteTitle = noteTitle;
        this.noteInfo = noteInfo;
        this.noteReps = noteReps;
        this.noteTotalTime = noteTotalTime;
        this.noteLastUsed = noteLastUsed;
        this.noteTimeCreated = noteTimeCreated;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        Note temp = (Note) obj ;
        return temp.noteTitle.equals(this.noteTitle) &&
                temp.noteInfo.equals(this.noteInfo) &&
                temp.noteReps == this.noteReps &&
                temp.noteTotalTime == this.noteTotalTime &&
                temp.noteLastUsed.equals(this.noteLastUsed) &&
                temp.noteTimeCreated.equals(this.noteTimeCreated) &&
                temp.isInActionMode == this.isInActionMode;
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

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getNoteTimeCreated() {
        return noteTimeCreated;
    }

    public void setNoteTimeCreated(String noteTimeCreated) {
        this.noteTimeCreated = noteTimeCreated;
    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
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

    public boolean isInActionMode() {
        return isInActionMode;
    }

    public void setInActionMode(boolean inActionMode) {
        isInActionMode = inActionMode;
    }
}

