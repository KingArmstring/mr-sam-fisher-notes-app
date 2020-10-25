package com.example.a00room_practice.RoomDatabase;

import android.app.Application;
import android.content.SharedPreferences;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.a00room_practice.ModelClass.AllNotes;
import com.example.a00room_practice.ModelClass.Category;
import com.example.a00room_practice.ModelClass.Note;
import com.example.a00room_practice.ModelClass.NoteAndCategory;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {
        private  NoteRepository repository;
        private LiveData<List<Note>> allNotes;
        private LiveData<List<Category>> allCategories;
        private LiveData<List<Category>> allCategoriesbyTitle;
        private MutableLiveData<List<AllNotes>> searchResults;
       private LiveData<Integer> max ;



    public NoteViewModel(@NonNull Application application) {
        super(application);

        repository = new NoteRepository(application);
        allNotes = repository.getAllNotes();
        allCategories = repository.getAllCategories();
        allCategoriesbyTitle = repository.getAllCategories();
        searchResults = repository.getSearchresults();
         max = repository.getMax();
    }


    public LiveData<Integer> getMax() { return max; }

    public LiveData<List<Note>> getAllNotes(){ return allNotes; }

  /*  public void insert(Note note){
        repository.insert(note);
    }*/

    public void update(Note note){
        repository.update(note);
    }
    public void delete(Note note){
        repository.delete(note);
    }
    public void insert(Note note){
        repository.insert(note);
    }



    public MutableLiveData<List<AllNotes>> getCategorySearchResults() { return searchResults; }
    public void findID(int ID){repository.findCategoryID(ID);}

    public LiveData<List<Category>>getAllCategories(){ return allCategories; }
    public LiveData<List<Category>>getAllCategoriesbyTitle(){ return allCategoriesbyTitle; }
    public Long insert(Category category){  return repository.insert(category);}
    public void insertNew(Category category, TextView textView){repository.insertNew(category,textView);}
    public void update(Category category){
        repository.update(category);
    }
    public void delete(Category category){
        repository.delete(category);
    }



    public void insert(NoteAndCategory noteAndCategory) { repository.insert(noteAndCategory);}
    public void update(NoteAndCategory noteAndCategory){ repository.update(noteAndCategory);}
    public void delete(NoteAndCategory noteAndCategory){
        repository.delete(noteAndCategory);
    }


}
