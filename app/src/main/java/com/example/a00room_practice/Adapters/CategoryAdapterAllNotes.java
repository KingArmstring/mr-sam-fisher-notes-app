 package com.example.a00room_practice.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a00room_practice.Activity.CategoryActivity;
import com.example.a00room_practice.ModelClass.Category;
import com.example.a00room_practice.ModelClass.Note;
import com.example.a00room_practice.R;

import java.util.ArrayList;
import java.util.List;

 public class CategoryAdapterAllNotes extends RecyclerView.Adapter<CategoryAdapterAllNotes.NoteHolder> implements Filterable {
     private onItemClickListener listener;
     private List<Category> categories = new ArrayList<>();
     private List<Category> categoriesfull;
     private  boolean aBoolean;
     public List<Integer> selection_list = new ArrayList<>();
     private  Dragtestlistener dragtestlistener;
     private static final String TAG = "CategoryAdapterAllNotes";
     private int categoryPosition;
     int count = 0;

     Category currentCategory;
     CategoryActivity category_Activity;
     Context context;

     public CategoryAdapterAllNotes(boolean aBoolean, CategoryActivity category_Activity, Context context) {
         this.aBoolean = aBoolean;
         this.category_Activity = category_Activity;
         this.context = context;
     }


     public void setDragtestlistener(Dragtestlistener dragtestlistener) {
         this.dragtestlistener = dragtestlistener;
     }

     @NonNull
     @Override
     public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         View itemView = LayoutInflater.from(parent.getContext())
                 .inflate(R.layout.item_category, parent, false);
        count++;
         Log.d(TAG, "onCreateViewHolder: "+count);
         return  new NoteHolder(itemView, category_Activity);
     }

     @SuppressLint("ClickableViewAccessibility")
     @Override
     public void onBindViewHolder(@NonNull NoteHolder holder, int position) {

          currentCategory = categories.get(position);


         holder.tv_title.setText(currentCategory.getCategoryTitle());
         holder.tv_total.setText("Total "+currentCategory.getCategoryAmount());
         holder.checkBox.setChecked(currentCategory.isSelected());
         holder.tv_postion.setText(String.valueOf(currentCategory.getCategoryPosition()));



         if (!category_Activity.boolean_actionmode){
             holder.checkBox.setVisibility(View.GONE);
             holder.handler.setVisibility(View.GONE);
             holder.checkBox.setChecked(false);
         }
         else{
             holder.checkBox.setVisibility(View.VISIBLE);
             holder.handler.setVisibility(View.VISIBLE);

         }

         if (categories.get(holder.getAdapterPosition()).isSelected()){
             holder.checkBox.setChecked(true);
         }else {
             holder.checkBox.setChecked(false);
         }


         holder.handler.setOnTouchListener(new View.OnTouchListener() {
             @Override
             public boolean onTouch(View v, MotionEvent event) {

                 if (event.getAction() == MotionEvent.ACTION_DOWN){

                     dragtestlistener.onDragStarted(holder);

                 }
                 return false;
             }
         });

            count ++;
         Log.d(TAG, "onBindViewHolder: "+count);
     }




     public class NoteHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
         private TextView tv_total;
         private TextView tv_title;
         private TextView tv_postion;
         public CheckBox checkBox;
         private ImageView handler;
         View view;

         public NoteHolder(@NonNull View itemView, CategoryActivity category_Activity) {
             super(itemView);
             tv_title = itemView.findViewById(R.id.CI_Category_Title);
             tv_total = itemView.findViewById(R.id.CI_Total);
             tv_postion = itemView.findViewById(R.id.CI_Category_Position);
             handler = itemView.findViewById(R.id.CI_ImageView);




             checkBox = itemView.findViewById(R.id.CI_Checkbox);
             view = itemView;




             itemView.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     if (category_Activity.actionMode != null) {

                     }

                     int position = getAdapterPosition();


                     if (listener != null && position != RecyclerView.NO_POSITION) {
                         listener.onItemClick(categories.get(position));

                     }


                 }
             });

             itemView.setOnLongClickListener(new View.OnLongClickListener() {
                 @Override
                 public boolean onLongClick(View v) {
                     if (category_Activity.actionMode != null) {

                         return false;

                     }
                     category_Activity.actionMode  = itemView.startActionMode(category_Activity.actionModeCallback);
                    category_Activity.boolean_actionmode = true;
                     checkBox.setChecked(true);

                     for (int i = 0; i <categories.size() ; i++) {
                         categories.get(i).setSelected(false);
                         selection_list.clear();
                     }

                    categories.get(getAdapterPosition()).setSelected(true);
                     category_Activity.selection_list.add(getAdapterPosition());
                     category_Activity.actionMode.setTitle("Selected "+ category_Activity.selection_list.size());

                     notifyDataSetChanged();
                     return true;
                 }});


            checkBox.setOnClickListener(this);
         }


         @RequiresApi(api = Build.VERSION_CODES.N)
         @Override
         public void onClick(View v) {
             int adapterPosition = getAdapterPosition();
             if (categories.get(adapterPosition).isSelected()){
                 checkBox.setChecked(false);
                 categories.get(adapterPosition).setSelected(false);
             }else {
                 checkBox.setChecked(true);
                 categories.get(adapterPosition).setSelected(true);
             }
             category_Activity.MakeSelection(v,getAdapterPosition());

         }


     }




     @Override
     public int getItemCount() { return categories.size();}


     @Override
     public Filter getFilter() {
         return exampleFilter;
     }


     private Filter exampleFilter = new Filter() {
         @Override
         protected FilterResults performFiltering(CharSequence constraint) {
             List<Category> filtered_list = new ArrayList<>();


             if (constraint == null || constraint.length() == 0) {
                 filtered_list.addAll(categoriesfull);
             } else {
                 String filterPattern = constraint.toString().toLowerCase().trim();

                 for (Category item : categoriesfull)
                     if (item.getCategoryTitle().toLowerCase().contains(filterPattern)) {
                         filtered_list.add(item);
                     }

             }
             FilterResults results = new FilterResults();
             results.values = filtered_list;

             return results;

         }

         @Override
         protected void publishResults(CharSequence constraint, FilterResults results) {

              categories.clear();
             categories.addAll(((List) results.values));
             notifyDataSetChanged();

         }
     };


     public void setCategories(List<Category> categories) {
         this.categories = categories;
         categoriesfull = new ArrayList<>(categories);
         notifyDataSetChanged();


     }



     public Category getcategoryAt(int position) {
         return categories.get(position);
     }





     public void diffUtilChangeData(List <Category> noteList ){
         CategoryDiffUtils categoryDiffUtils = new CategoryDiffUtils(categories,noteList);
         this.categories = noteList;
         categoriesfull = new ArrayList<>(noteList);
         DiffUtil.DiffResult diffResult =  DiffUtil.calculateDiff(categoryDiffUtils);
         diffResult.dispatchUpdatesTo(this);

     }



     public void setOnItemClickListener(onItemClickListener listener){
         this.listener = listener;

     }


     public interface onItemClickListener{
         void onItemClick(Category category);


     }


     public interface Dragtestlistener {
         void onDragStarted(CategoryAdapterAllNotes.NoteHolder noteHolder);
     }
 }





