package com.example.a00room_practice.RoomDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Ignore;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;


import com.example.a00room_practice.ModelClass.AllNotes;
import com.example.a00room_practice.ModelClass.Category;
import com.example.a00room_practice.ModelClass.Note;
import com.example.a00room_practice.ModelClass.NoteAndCategory;

import java.util.List;

@Dao
public interface NoteDao {
    public static final int IGNORE = 5;

    @Insert
    void insert(Note note);

    @Insert
    Long insert(Category category);

    @Insert
    Long insertNew(Category category);


    @Insert
    void insert(NoteAndCategory NoteAndCategory);


    @Update
    void update(Note note);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void update(Category category);

    @Update
    void update(NoteAndCategory NoteAndCategory);


    @Delete
    void delete(Note note);

    @Delete
    void delete(Category category);


    @Delete
    void delete(NoteAndCategory NoteAndCategory);


    @Query("SELECT * FROM note Order by notetitle asc ")
    LiveData<List <Note>> getALLNotes() ;

    @Query("SELECT * FROM category Order by categoryPosition asc ")
    LiveData<List<Category>> getALLCategories();

    @Query("SELECT * FROM category Order by categoryTitle asc ")
    LiveData<List<Category>> getALLCategoriesbyTitle();

    @Query("SELECT * FROM note "+
            " inner join noteAndCategory on noteAndCategory.noteId = note.noteId "+
            " inner join category on category.categoryId = noteAndCategory.categoryId "+
            " where category.categoryId like :ID "+
            " Order by categoryTitle asc ")
    List<AllNotes> findCategoryId(int ID);

    @Query("SELECT Max(categoryPosition) FROM category  ")
    LiveData<Integer> Max();



   /*   @Query("SELECT * FROM book " +
           "INNER JOIN loan ON loan.book_id = book.id " +
           "INNER JOIN user ON user.id = loan.user_id " +
           "WHERE user.name LIKE :userName")
   public List<Book> findBooksBorrowedByNameSync(String userName);
   *
   *
   *    @Query("Select * FROM note WHERE Category_ID like :name order by Title asc")
    List<Note> findID(int name);

   *
   *
     @Query("Select * From note "+
           " Inner Join CATEGORY on Category.category_id = note.category_id"+
           " Where note.category_id like :name order by title asc")
    List<Note> findID(int name);

   *    @Query("SELECT * FROM noteInfo "+
    " inner join allnotes on allnotes.noteId = noteInfo.noteId"+
    " inner join category on category.categoryId = allnotes.categoryId"+
    " order by noteTitle asc")
    LiveData<List <AllNotes>> getALLNotes();
   * */




}