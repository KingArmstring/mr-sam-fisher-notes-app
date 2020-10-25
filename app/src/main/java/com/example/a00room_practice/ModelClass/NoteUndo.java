package com.example.a00room_practice.ModelClass;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


public class NoteUndo {

    private  int noteId;
    private String noteTitle;
    private String noteInfo;
    private String noteTimeCreated;
    private int noteReps;
    private long noteTotalTime;
    private String noteLastUsed;


    public NoteUndo(int noteId, String noteTitle, String noteInfo, String noteTimeCreated,
                    int noteReps, long noteTotalTime, String noteLastUsed) {
        this.noteId = noteId;
        this.noteTitle = noteTitle;
        this.noteInfo = noteInfo;
        this.noteTimeCreated = noteTimeCreated;
        this.noteReps = noteReps;
        this.noteTotalTime = noteTotalTime;
        this.noteLastUsed = noteLastUsed;
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

}





