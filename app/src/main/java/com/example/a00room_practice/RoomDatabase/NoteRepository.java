package com.example.a00room_practice.RoomDatabase;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.example.a00room_practice.ModelClass.AllNotes;
import com.example.a00room_practice.ModelClass.Category;
import com.example.a00room_practice.ModelClass.Note;
import com.example.a00room_practice.ModelClass.NoteAndCategory;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.concurrent.Executor;

public class NoteRepository {

    private NoteDao noteDao;
    private Executor executor;
    private LiveData<List<Note>> allNotes;
    private LiveData<List<Category>> allCategories;
    private LiveData<List<Category>> allCategoriesbyTitle;
    private MutableLiveData<List<AllNotes>> searchresults = new MutableLiveData<>();
    private MutableLiveData<Long> idresults = new MutableLiveData<>();

    private LiveData<Integer> Max ;


  public LiveData<Integer> getMax() { return Max; }

    public NoteRepository(Application application) {
        NoteDatabase database = NoteDatabase.getInstance(application);

        noteDao = database.notedao();
        allNotes = noteDao.getALLNotes();
        allCategories = noteDao.getALLCategories();
        allCategoriesbyTitle = noteDao.getALLCategoriesbyTitle();
        Max = noteDao.Max();

    }


    public void insert(Note note) {

        Thread UpatedTread = new InsertThread(note);
        UpatedTread.start();
    }

    public void update(Note note) {
        Thread updateThread = new UpdateThread(note);
        updateThread.start();
    }

    public void delete(Note note) {
        Thread deleteThread = new DeleteThread(note);
        deleteThread.start();

    }




    public void findCategoryID(int ID) {
        CategorySearchAsyncTask task = new CategorySearchAsyncTask(noteDao);
        task.delegate = this;
        task.execute(ID);
    }

    public LiveData<List<Note>> getAllNotes() { return allNotes; }




    private static class UpdateNoteAsyncTask extends AsyncTask<Note, Void, Void> {

        private NoteDao noteDao;

        private UpdateNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.update(notes[0]);
            return null;
        }

        private static class DeleteNoteAsyncTask extends AsyncTask<Note, Void, Void> {
            private NoteDao noteDao;

            private DeleteNoteAsyncTask(NoteDao noteDao) {
                this.noteDao = noteDao;
            }

            @Override
            protected Void doInBackground(Note... notes) {
                noteDao.delete(notes[0]);
                return null;
            }
        }

    }



    public class InsertThread extends Thread {
        private Note note;

        public InsertThread(Note... notes) {
            this.note = notes[0];
        }

        @Override
        public void run() {
            noteDao.insert(note);
        }
    }

    public class UpdateThread extends Thread {
        private Note note;

        public UpdateThread(Note... notes) {
            this.note = notes[0];
        }

        @Override
        public void run() {
            noteDao.update(note);
        }
    }

    public class DeleteThread extends Thread {
        private Note note;

        public DeleteThread(Note... notes) {
            this.note = notes[0];
        }

        @Override
        public void run() {
            noteDao.delete(note);
        }
    }


    //------------------------------------------------Category --------------------------------------

    public void insertNew(Category category, TextView textView) {
        new InsertNewCategoryAsyncTask(noteDao,textView).execute(category);

    }


   public Long insert(Category category) {
          new InsertCategoryAsyncTask (noteDao).execute(category);

        return null;
    }

    public void update(Category category) {
        new UpdateCategoryAsyncTask(noteDao).execute(category);

    }

    public void delete(Category category) {
        new UpdateCategoryAsyncTask.DeleteCategoryAsyncTask(noteDao).execute(category);

    }




    public LiveData<List<Category>> getAllCategories() {
        return allCategories;
    }
    public LiveData<List<Category>> getAllCategoriesbyTitle() {
        return allCategoriesbyTitle;
    }


    public static class InsertCategoryAsyncTask extends AsyncTask<Category, Void, Long> {
        private WeakReference<TextView> mTextview;
        private static final String TAG ="NoteRepository" ;
        private NoteDao noteDao;
        private NoteRepository delegate = null;


        InsertCategoryAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }


        @Override
        protected Long doInBackground(Category... categories) {

            return  noteDao.insert(categories[0]);
        }

        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);

        }
    }

    public static class InsertNewCategoryAsyncTask extends AsyncTask<Category, Void,Long > {
        private WeakReference<TextView> mTextview;
        private static final String TAG ="NoteRepository" ;
        private NoteDao noteDao;
        testInterface testInterface;


        InsertNewCategoryAsyncTask(NoteDao noteDao,TextView  textView) {
            this.noteDao = noteDao;
            mTextview = new WeakReference<>(textView);

        }



        @Override
        protected Long doInBackground(Category... categories) {

            return noteDao.insertNew(categories[0]);
        }

        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);
            Log.d(TAG, "onPostExecute: "+aLong);
            mTextview.get().setText(""+aLong);

        }


    }

    private static class UpdateCategoryAsyncTask extends AsyncTask<Category, Void, Void> {

        private NoteDao noteDao;

        private UpdateCategoryAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Category... categories) {
            noteDao.update(categories[0]);
            return null;
        }

        private static class DeleteCategoryAsyncTask extends AsyncTask<Category, Void, Void> {
            private NoteDao noteDao;

            private DeleteCategoryAsyncTask(NoteDao noteDao) {
                this.noteDao = noteDao;
            }

            @Override
            protected Void doInBackground(Category... categories) {
                noteDao.delete(categories[0]);
                return null;
            }
        }


    }



//------------------------------------------------Note and Category --------------------------------------



    public void insert(NoteAndCategory noteAndCategory) {
        new InsertNoteAndCategoryAsyncTask(noteDao).execute(noteAndCategory);
    }

    public void update(NoteAndCategory noteAndCategory) {
        new UpdateNoteAndCategoryAsyncTask(noteDao).execute(noteAndCategory);
    }

    public void delete(NoteAndCategory noteAndCategory) {
        new DeleteNoteAndCategoryAsyncTask(noteDao).execute(noteAndCategory);
    }




    public static class InsertNoteAndCategoryAsyncTask extends AsyncTask<NoteAndCategory, Void, Long> {
        private NoteDao noteDao;

        public InsertNoteAndCategoryAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }


        @Override
        protected Long doInBackground(NoteAndCategory... noteAndCategory) {
            noteDao.insert(noteAndCategory[0]);
            return null;
        }


    }

    public static class UpdateNoteAndCategoryAsyncTask extends AsyncTask<NoteAndCategory, Void, Void> {
        NoteDao noteDao;

        public UpdateNoteAndCategoryAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(NoteAndCategory... noteAndCategory) {
            noteDao.update(noteAndCategory[0]);
            return null;
        }
    }

    public static class DeleteNoteAndCategoryAsyncTask extends AsyncTask<NoteAndCategory, Void, Void> {
        NoteDao noteDao;

        public DeleteNoteAndCategoryAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(NoteAndCategory... noteAndCategory) {
            noteDao.delete(noteAndCategory[0]);
            return null;
        }
    }



//------------------------------------------------CategorySearchAsyncTask --------------------------------------

    public MutableLiveData<List<AllNotes>> getSearchresults() { return searchresults;}

    private static class CategorySearchAsyncTask extends AsyncTask<Integer, Void, List<AllNotes>>
    {

        private NoteDao noteDao;
        private NoteRepository delegate = null;

        CategorySearchAsyncTask(NoteDao noteDao) { this.noteDao = noteDao; }

        @Override
        protected List<AllNotes> doInBackground(Integer... integers) {
            return noteDao.findCategoryId(integers[0]);
        }

        @Override
        protected void onPostExecute(List<AllNotes> results) {
            delegate.asyncCategoryFinished(results); }
    }


    private void asyncCategoryFinished(List<AllNotes> results) { searchresults.setValue(results); }








    public interface testInterface
    {
        static Long getId(Long ID) {
            Long num = ID;
            return num;
        }
    }

}
