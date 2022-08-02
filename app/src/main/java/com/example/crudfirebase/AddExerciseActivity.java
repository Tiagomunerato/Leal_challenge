package com.example.crudfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddExerciseActivity extends AppCompatActivity {

    private TextInputEditText exerciseNameEDt,tutorialPriceEDT, exerciseSuiteEDT, exerciseLinkEDT, exerciseImageEdt,exercersiseDescEdt;
    private Button addExerciseBtn;
    private ProgressBar loadingPB;
    private FirebaseDatabase  firebaseDatabase;
    private DatabaseReference databaseReference;
    public String exerciseID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise);

        exerciseNameEDt = findViewById(R.id.idEdtExerciseName);
        tutorialPriceEDT= findViewById(R.id.idEdtExercisePrice);
        exerciseSuiteEDT = findViewById(R.id.idEdtExerciseChosen);
        exerciseImageEdt = findViewById(R.id.idEdtExerciseImageLink);
        exercersiseDescEdt = findViewById(R.id.idEdtExerciseDescritpion);
        exerciseLinkEDT = findViewById(R.id.idEdtExerciseLink);

        addExerciseBtn = findViewById(R.id.idBtnAddExercicese);
        loadingPB = findViewById(R.id.idPBLoading);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Exercise");

        addExerciseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loadingPB.setVisibility(View.VISIBLE);
                String exerciseNAme = exerciseNameEDt.getText().toString();
                String exerciseprice = tutorialPriceEDT.getText().toString();
                String exerciseChosen = exerciseSuiteEDT.getText().toString();
                String exerciseImg = exerciseImageEdt.getText().toString();
                String exerciseLink = exerciseLinkEDT.getText().toString();
                String exerciseDesc = exercersiseDescEdt.getText().toString();
                exerciseID = exerciseNAme;

                ExerciseRVModal  exerciseRVModal = new ExerciseRVModal(exerciseNAme,exerciseprice,exerciseDesc,exerciseChosen,exerciseImg,exerciseLink,exerciseID);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        loadingPB.setVisibility(View.GONE);
                        databaseReference.child(exerciseID).setValue(exerciseRVModal);
                        Toast.makeText(AddExerciseActivity.this,"Exercício Adiconado",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddExerciseActivity.this,MainActivity.class));


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(AddExerciseActivity.this,"Erro ao adicionar!!",Toast.LENGTH_SHORT).show();

                    }
                });







            }
        });


    }
}