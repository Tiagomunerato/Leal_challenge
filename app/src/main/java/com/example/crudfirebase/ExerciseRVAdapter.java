package com.example.crudfirebase;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.sql.ClientInfoStatus;
import java.util.ArrayList;

public class ExerciseRVAdapter extends RecyclerView.Adapter<ExerciseRVAdapter.ViewHolder> {

    private ArrayList<ExerciseRVModal>exerciseRVModals;
    private Context context;
    int lastPos = -1;
    private  ExerciseClickInterface exerciseClickInterface;

    public ExerciseRVAdapter(ArrayList<ExerciseRVModal> exerciseRVModals, Context context, ExerciseClickInterface exerciseClickInterface) {
        this.exerciseRVModals = exerciseRVModals;
        this.context = context;
        this.exerciseClickInterface = exerciseClickInterface;
    }

    @NonNull
    @Override
    public ExerciseRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.exercise_rv_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseRVAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ExerciseRVModal exerciseRVModal = exerciseRVModals.get(position);
        holder.exerciseNameTV.setText(exerciseRVModal.getExerciseName());
        holder.tutorialPriceTV.setText("Rs."+exerciseRVModal.getTutorialPrice());
        Picasso.get().load(exerciseRVModal.getExerciseImg()).into(holder.exerciseIV);
        setAnimation(holder.itemView,position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exerciseClickInterface.onExerciseClick(position);
            }
        });



    }

    private void setAnimation(View itemView, int position){
        if (position > lastPos){
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            itemView.setAnimation(animation);
            lastPos = position;
        }

    }

    @Override
    public int getItemCount() {
        return exerciseRVModals.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView exerciseNameTV,tutorialPriceTV;
        private ImageView exerciseIV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            exerciseNameTV =  itemView.findViewById(R.id.idIVExercise);
            tutorialPriceTV = itemView.findViewById(R.id.idPriceTutorial);
            exerciseIV = itemView.findViewById(R.id.idIVExercise);

        }
    }
    public interface ExerciseClickInterface{
        void onExerciseClick(int position);
    }
}
