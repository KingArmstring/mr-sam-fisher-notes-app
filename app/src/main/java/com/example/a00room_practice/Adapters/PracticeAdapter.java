package com.example.a00room_practice.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a00room_practice.Activity.CategoryReorderList;
import com.example.a00room_practice.ModelClass.Category;
import com.example.a00room_practice.R;

import java.util.ArrayList;
import java.util.List;

public class PracticeAdapter extends RecyclerView.Adapter<PracticeAdapter.ViewHolder >  {
    private List<Category> categories = new ArrayList<>();
    private onItemClickListener listener;
    private PracticeAdapter.Dragtestlistener dragtestlistener;



    Category currentCategory;
    CategoryReorderList categoryReorderList;
    Context context;




    public void setDragtestlistener(PracticeAdapter.Dragtestlistener dragtestlistener) {
        this.dragtestlistener = dragtestlistener;
    }




    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View singleRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_reorder,parent,false);
        return new ViewHolder(singleRow);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        currentCategory = categories.get(position);

        holder.tv_title.setText(currentCategory.getCategoryTitle());
        holder.tv_total.setText("Total "+currentCategory.getCategoryAmount());
        holder.tv_postion.setText(String.valueOf(currentCategory.getCategoryPosition()));


        holder.handler.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN){

                    dragtestlistener.onDragStarted(holder);

                }
                return false;
            }
        });


    }

    @Override
    public int getItemCount() {
        return categories.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
    private TextView tv_total;
    private TextView tv_title;
    private TextView tv_postion;
    private ImageView handler;

    View view;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);

        tv_title = itemView.findViewById(R.id.CI_Category_Title);
        tv_total = itemView.findViewById(R.id.CI_Total);
        handler = itemView.findViewById(R.id.CI_ImageView);
        tv_postion = itemView.findViewById(R.id.CI_Category_Position);

        view = itemView;


    }


}
    public void setOnItemClickListener(PracticeAdapter.onItemClickListener listener){
        this.listener = listener;

    }


    public interface onItemClickListener{
        void onItemClick(Category category);


    }


    public interface Dragtestlistener {
        void onDragStarted(PracticeAdapter.ViewHolder viewHolder);
    }


    public Category getCategoryAt(int position) {
        return categories.get(position);
    }


    public void setCategories(List<Category> categories) {
        this.categories = categories;
        notifyDataSetChanged();


    }


}

