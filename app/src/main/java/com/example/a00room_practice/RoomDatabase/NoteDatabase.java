package com.example.a00room_practice.RoomDatabase;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.a00room_practice.ModelClass.AllNotes;
import com.example.a00room_practice.ModelClass.Category;
import com.example.a00room_practice.ModelClass.Note;
import com.example.a00room_practice.ModelClass.NoteAndCategory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities ={ Note.class, Category.class, NoteAndCategory.class},version = 2,
        exportSchema = false)
public abstract class NoteDatabase extends RoomDatabase {


private static volatile NoteDatabase instance;
public abstract NoteDao notedao();
private static final int NUMBER_OF_THREADS = 1;
public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);


public static synchronized  NoteDatabase getInstance(Context context){
    if(instance == null){
        instance = Room.databaseBuilder(context.getApplicationContext(),
                NoteDatabase.class,"note_database" )
                .fallbackToDestructiveMigration()
                //.addCallback(roomCallback)
                .build();
    }
    return instance;
}
/*
private  static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
    @Override
    public void onCreate(@NonNull SupportSQLiteDatabase db) {
        super.onCreate(db);
        new PopulateDbAsyncTask(instance).execute();
    }
};

private static class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void> {
private  NoteDao noteDao;

private  PopulateDbAsyncTask(NoteDatabase db){
    noteDao = db.notedao();
}


    @Override
    protected Void doInBackground(Void... voids) {

    noteDao.insert(new Category(0,"All Notes",0));

        return null;

}
 }*/

}
