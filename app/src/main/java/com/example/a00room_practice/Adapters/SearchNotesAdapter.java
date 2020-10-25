 package com.example.a00room_practice.Adapters;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a00room_practice.ModelClass.AllNotes;
import com.example.a00room_practice.Activity.NoteSearchActiviy;
import com.example.a00room_practice.Miscellaneous.QuickNotesActivity;
import com.example.a00room_practice.R;

import java.util.ArrayList;
import java.util.List;

 public class SearchNotesAdapter extends RecyclerView.Adapter<SearchNotesAdapter.NoteHolder> implements Filterable{
      private onItemClickListener listener;
     private List<AllNotes> notes = new ArrayList<>();
     private List<AllNotes> notesfull;
     private String days_f_time, hours_f_time, mins_f_time, sec_f_time;




     AllNotes currentNote;



     NoteSearchActiviy noteSearchActiviy;
     Context context;

     public SearchNotesAdapter(NoteSearchActiviy noteSearchActiviy, Context context) {
         this.noteSearchActiviy = noteSearchActiviy;
         this.context = context;
     }






     @NonNull
     @Override
     public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         View itemView = LayoutInflater.from(parent.getContext())
                 .inflate(R.layout.item_note, parent, false);

        // return new NoteHolder(itemView, noteRecyclerViewActivity);
         return  new NoteHolder(itemView, noteSearchActiviy);
     }

     @Override
     public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
          currentNote = notes.get(position);
         holder.textViewtitle.setText(currentNote.getNoteTitle());
         holder.textViewinfo.setText(currentNote.getNoteInfo());
         holder.textViewreps.setText(QuickNotesActivity.REPS+ currentNote.getNoteReps());
         holder.checkBox.setChecked(currentNote.isSelected());
         holder.tvtimecreated.setText("Created "+ currentNote.getNoteTimeCreated());
         holder.tvlastused.setText("Last Used: "+ currentNote.getNoteLastUsed());

         format_time_HMS(currentNote.getNoteTotalTime(),holder.textViewtime,"Total Time");

         if (!noteSearchActiviy.boolean_actionmode){
             holder.checkBox.setVisibility(View.GONE);
             holder.checkBox.setChecked(false);
         }
         else{
             holder.checkBox.setVisibility(View.VISIBLE);
         }

         if (notes.get(holder.getAdapterPosition()).isSelected()){
             holder.checkBox.setChecked(true);
         }else {
             holder.checkBox.setChecked(false);
         }









     }

     class NoteHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
         private TextView textViewtitle;
         private TextView textViewinfo;
         private TextView textViewreps;
         private TextView textViewtime;
         private TextView tvtimecreated;
         private TextView tvlastused;
         private LinearLayout ll_RT;
         private LinearLayout ll_CL;
         private CheckBox checkBox;
         View view;

         public NoteHolder(@NonNull View itemView, NoteSearchActiviy noteSearchActiviy) {
             super(itemView);
             textViewtitle = itemView.findViewById(R.id.NI_Title);
             textViewinfo = itemView.findViewById(R.id.NI_description);
             textViewreps = itemView.findViewById(R.id.NI_reps);
             textViewtime = itemView.findViewById(R.id.NI_time);
             tvtimecreated = itemView.findViewById(R.id.NI_timecreated);
             tvlastused = itemView.findViewById(R.id.NI_lastused);
             ll_CL = itemView.findViewById(R.id.NI_LL_CL);
             ll_RT = itemView.findViewById(R.id.NI_LL_RT);
             checkBox = itemView.findViewById(R.id.NI_Checkbox);
             view = itemView;


             itemView.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     if (noteSearchActiviy.actionMode != null) {


                     }
                     int position = getAdapterPosition();

                     if (listener != null && position != RecyclerView.NO_POSITION) {
                         listener.onItemClick(notes.get(position));
                     }
                 }
             });

             itemView.setOnLongClickListener(new View.OnLongClickListener() {
                 @Override
                 public boolean onLongClick(View v) {
                     if (noteSearchActiviy.actionMode != null) {
                         return false;

                     }
                     noteSearchActiviy.actionMode  = itemView.startActionMode(noteSearchActiviy.actionModeCallback);
                     noteSearchActiviy.boolean_actionmode = true;
                     checkBox.setChecked(false);
                     for (int i = 0; i < notes.size() ; i++) {
                         notes.get(i).setSelected(false);


                     }



                     notifyDataSetChanged();

                     return true;
                 }});


            checkBox.setOnClickListener(this);







         }


         @RequiresApi(api = Build.VERSION_CODES.N)
         @Override
         public void onClick(View v) {
             int adapterPosition = getAdapterPosition();
             if (notes.get(adapterPosition).isSelected()){
                 checkBox.setChecked(false);
                 notes.get(adapterPosition).setSelected(false);
             }else {
                 checkBox.setChecked(true);
                 notes.get(adapterPosition).setSelected(true);
             }
             noteSearchActiviy.MakeSelection(v,getAdapterPosition());


         }
     }



     @Override
     public int getItemCount() {
         return notes.size();
     }

     @Override
     public Filter getFilter() {
         return exampleFilter;
     }


     private Filter exampleFilter = new Filter() {
         @Override
         protected FilterResults performFiltering(CharSequence constraint) {
             List<AllNotes> filtered_list = new ArrayList<>();


             if (constraint == null || constraint.length() == 0) {
                 filtered_list.addAll(notesfull);
             } else {
                 String filterPattern = constraint.toString().toLowerCase().trim();

                 for (AllNotes item : notesfull)
                     if (item.getNoteTitle().toLowerCase().contains(filterPattern)||
                             item.getNoteInfo().toLowerCase().contains(filterPattern)) {
                         filtered_list.add(item);
                     }

             }
             FilterResults results = new FilterResults();
             results.values = filtered_list;

             return results;

         }

         @Override
         protected void publishResults(CharSequence constraint, FilterResults results) {

              notes.clear();
             notes.addAll(((List) results.values));
             notifyDataSetChanged();

         }
     };


     public void setNotes(List<AllNotes> notes) {
         this.notes = notes;
         notesfull = new ArrayList<>(notes);
         notifyDataSetChanged();
     }


     public AllNotes getAllNotesAt(int position) {
         return notes.get(position);
     }


     public void setOnItemClickListener(onItemClickListener listener){
         this.listener = listener;
     }


     public interface onItemClickListener{
         void onItemClick(AllNotes notes);

     }

     public void format_time_HMS(long time, TextView textView, String text){
         int days_MT = (int) (time) / 86400;
         int hours_MT = (int) (time) / 3600 % 24;
         int minutes_MT = (int) (time) / 60 % 60;
         int seconds_MT = (int) (time) % 60;


         if (days_MT > 1) {
             days_f_time = "" + days_MT + " days";

         } else if ((days_MT == 1)) {
             days_f_time = "" + days_MT + " day";
         } else if (days_MT < 1) {
             days_f_time = "";
         }


         if (hours_MT > 1) {
             hours_f_time = "" + hours_MT + " hours";

         } else if ((hours_MT == 1)) {
             hours_f_time = "" + hours_MT + " hour";
         } else if (hours_MT < 1) {
             hours_f_time = "";
         }

         if (minutes_MT > 1) {
             mins_f_time = "" + minutes_MT + " mins";

         } else if ((minutes_MT == 1)) {
             mins_f_time = "" + minutes_MT + " min";
         } else if (minutes_MT < 1) {
             mins_f_time = "";
         }

         if (seconds_MT > 1) {
             sec_f_time = "" + seconds_MT + " secs";

         } else if ((seconds_MT == 1)) {
             sec_f_time = "" + seconds_MT + " sec";
         } else if (seconds_MT < 1) {
             sec_f_time = "";
         }

         String MT_time_hour_Mins_Secs = text+" " + days_f_time + " " + hours_f_time + " " + mins_f_time + " " + sec_f_time;

         textView.setText(MT_time_hour_Mins_Secs);
     }


 }





