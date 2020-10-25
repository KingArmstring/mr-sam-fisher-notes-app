package com.example.a00room_practice.Adapters;

import android.content.Context;
import android.os.Build;
import android.util.Log;
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
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a00room_practice.Activity.NoteActivity;
import com.example.a00room_practice.ModelClass.Note;
import com.example.a00room_practice.Miscellaneous.QuickNotesActivity;
import com.example.a00room_practice.R;
import com.example.a00room_practice.contracts.NotesContract;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> implements Filterable{
    private static final String TAG = NoteAdapter.class.getSimpleName();
    private onItemClickListener listener;
    private List<Note> notes = new ArrayList<>();
    private List<Note> notesfull;
    private String days_f_time, hours_f_time, mins_f_time, sec_f_time;

    Note currentNote;
    NoteActivity noteActivity;
    Context context;
    NotesContract callback;

    public NoteAdapter(NoteActivity noteActivity, Context context, NotesContract callback) {
        this.noteActivity = noteActivity;
        this.context = noteActivity;
        this.callback = callback;
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_note, parent, false);

        Log.d(TAG, "onCreateViewHolder: ");
        return  new NoteHolder(itemView, noteActivity);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {

        currentNote  = notes.get(position);
        holder.tvtitle.setText(currentNote.getNoteTitle());
        holder.tvinfo.setText(currentNote.getNoteInfo());
        holder.tvreps.setText(QuickNotesActivity.REPS+currentNote.getNoteReps());
        holder.tvtimecreated.setText("Created "+currentNote.getNoteTimeCreated());
        holder.tvlastused.setText("Last Used: "+currentNote.getNoteLastUsed());
        holder.checkBox.setChecked(currentNote.isSelected());
        format_time_HMS(currentNote.getNoteTotalTime(),holder.tvtime,"Total Time");
        if (!noteActivity.boolean_actionmode) {
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
        holder.setOnItemClickedListener();
        holder.setOnItemLongClickedListener(callback);
    }

    class NoteHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView tvtitle;
        private TextView tvinfo;
        private TextView tvreps;
        private TextView tvtime;
        private TextView tvtimecreated;
        private TextView tvlastused;
        private LinearLayout ll_RT;
        private LinearLayout ll_CL;


        private CheckBox checkBox;
        View view;

        public NoteHolder(@NonNull View itemView, NoteActivity noteActivity) {
            super(itemView);
            tvtitle = itemView.findViewById(R.id.NI_Title);
            tvinfo = itemView.findViewById(R.id.NI_description);
            tvreps = itemView.findViewById(R.id.NI_reps);
            tvtime = itemView.findViewById(R.id.NI_time);
            tvtimecreated = itemView.findViewById(R.id.NI_timecreated);
            tvlastused = itemView.findViewById(R.id.NI_lastused);
            ll_CL = itemView.findViewById(R.id.NI_LL_CL);
            ll_RT = itemView.findViewById(R.id.NI_LL_RT);
            checkBox = itemView.findViewById(R.id.NI_Checkbox);
            view = itemView;
            checkBox.setOnClickListener(this);
        }

        void setOnItemLongClickedListener(NotesContract callback) {
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (noteActivity.actionMode != null) {
                        return false;
                    }
                    noteActivity.actionMode  = itemView.startActionMode(noteActivity.actionModeCallback);
                    noteActivity.boolean_actionmode = true;
                    checkBox.setChecked(false);
                    for (int i = 0; i < notes.size() ; i++) {
                        notes.get(i).setSelected(false);
                    }
                    callback.myNotifyDataSetChanged(true);
                    return true;
                }});
        }

        void setOnItemClickedListener() {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (noteActivity.actionMode != null) {


                    }
                    int position = getAdapterPosition();

                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(notes.get(position));
                    }
                }
            });
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
            noteActivity.MakeSelection(v,getAdapterPosition());


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
            List<Note> filtered_list = new ArrayList<>();


            if (constraint == null || constraint.length() == 0) {
                filtered_list.addAll(notesfull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Note item : notesfull)
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


//    public void setNotes(List<Note> notes) {
//        this.notes = notes;
//        notesfull = new ArrayList<>(notes);
//        notifyDataSetChanged();
//    }

    public Note getNoteAt(int position) {
        return notes.get(position);
    }


    public void setOnItemClickListener(onItemClickListener listener){
        this.listener = listener;
    }


    public interface onItemClickListener{
        void onItemClick(Note note);

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

        if (seconds_MT < 1 && minutes_MT  < 1 && hours_MT  < 1 && days_MT  < 1)
        {
            String MT_time_hour_Mins_Secs = text+" No Activity";
            textView.setText(MT_time_hour_Mins_Secs);
        }else {

            String MT_time_hour_Mins_Secs = text + " " + days_f_time + " " + hours_f_time + " " + mins_f_time + " " + sec_f_time;

            textView.setText(MT_time_hour_Mins_Secs);}

    }

    public void diffUtilChangeData(List <Note> newList){
        PracticeDiffUtils practiceDiffUtils = new PracticeDiffUtils(this.notes, newList);
        DiffUtil.DiffResult diffResult =  DiffUtil.calculateDiff(practiceDiffUtils);
        this.notes = newList;
        notesfull = new ArrayList<>(newList);
        diffResult.dispatchUpdatesTo(this);
    }


}





