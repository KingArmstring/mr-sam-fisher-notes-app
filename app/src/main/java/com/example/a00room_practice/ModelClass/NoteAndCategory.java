package com.example.a00room_practice.ModelClass;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "noteAndCategory",
        foreignKeys = {@ForeignKey(entity = Note.class,parentColumns = "noteId",childColumns = "noteId"),
        @ForeignKey(entity = Category.class,parentColumns = "categoryId",childColumns = "categoryId")},
        indices = {@Index(name = "indexNoteId",value = "noteId"),
        @Index(name = "indexCategoryId",value = "categoryId")
        })
public class NoteAndCategory {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "nacId")
    private int nacId;

    @ColumnInfo(name = "noteId")
   private int noteId;


    @ColumnInfo(name = "categoryId")
    private int categoryId;


    public NoteAndCategory( int noteId, int categoryId) {
        this.noteId = noteId;
        this.categoryId = categoryId;
    }

    public int getNoteId() {
        return noteId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public int getNacId() {
        return nacId;
    }

    public void setNacId(int nacId) {
        this.nacId = nacId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
