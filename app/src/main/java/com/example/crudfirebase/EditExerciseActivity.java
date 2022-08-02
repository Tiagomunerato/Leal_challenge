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

import java.util.HashMap;
import java.util.Map;

public class EditExerciseActivity extends AppCompatActivity {

    private TextInputEditText exerciseNameEDt,tutorialPriceEDT, exerciseSuiteEDT, exerciseLinkEDT, exerciseImageEdt,exercersiseDescEdt;
    private Button updateExerciseBtn, deletExecerciseBtn;
    private ProgressBar loadingPB;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    public String exerciseID;
    private ExerciseRVModal exerciseRVModal;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_exercise);

        firebaseDatabase = FirebaseDatabase.getInstance();

        exerciseNameEDt = findViewById(R.id.idEdtExerciseName);
        tutorialPriceEDT= findViewById(R.id.idEdtExercisePrice);
        exerciseSuiteEDT = findViewById(R.id.idEdtExerciseChosen);
        exerciseImageEdt = findViewById(R.id.idEdtExerciseImageLink);
        exercersiseDescEdt = findViewById(R.id.idEdtExerciseDescritpion);
        exerciseLinkEDT = findViewById(R.id.idEdtExerciseLink);

        updateExerciseBtn = findViewById(R.id.idBtnUpdateExercicese);
        deletExecerciseBtn = findViewById(R.id.idBtnDeleteExercicese);
        loadingPB = findViewById(R.id.idPBLoading);

        exerciseRVModal = getIntent().getParcelableExtra("exercise");
        if(exerciseRVModal !=null){
            exerciseNameEDt.setText(exerciseRVModal.getExerciseName());
            tutorialPriceEDT.setText(exerciseRVModal.getTutorialPrice());
            exerciseSuiteEDT.setText(exerciseRVModal.getBestChosen());
            exerciseImageEdt.setText(exerciseRVModal.getExerciseImg());
            exercersiseDescEdt.setText(exerciseRVModal.getExerciseDescription());
            exerciseLinkEDT.setText(exerciseRVModal.getExerciseLink());
            exerciseID = exerciseRVModal.getExerciseID();
        }


        databaseReference = firebaseDatabase.getReference("Exercise").child(exerciseID);
        updateExerciseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingPB.setVisibility(View.VISIBLE);
                String exerciseNAme = exerciseNameEDt.getText().toString();
                String exerciseprice = tutorialPriceEDT.getText().toString();
                String exerciseChosen = exerciseSuiteEDT.getText().toString();
                String exerciseImg = exerciseImageEdt.getText().toString();
                String exerciseLink = exerciseLinkEDT.getText().toString();
                String exerciseDesc = exercersiseDescEdt.getText().toString();

                Map<String,Object> map = new HashMap<>();
                map.put("exerciseName", exerciseNAme);
                map.put("tutorialPrice", exerciseprice);
                map.put("bestChosen", exerciseChosen);
                map.put("exerciseImg", exerciseImg);
                map.put("exerciseLink", exerciseLink);
                map.put("exerciseDescription", exerciseDesc);
                map.put("exerciseID", exerciseID);


                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        loadingPB.setVisibility(View.GONE);
                        databaseReference.updateChildren(map);
                        Toast.makeText(EditExerciseActivity.this,"Exercício Atualizado.",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditExerciseActivity.this,MainActivity.class));

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(EditExerciseActivity.this, "Falha ao atualizar", Toast.LENGTH_SHORT).show();


                    }
                });


            }
        });

        deletExecerciseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteExercise();

            }
        });

    }

    private void deleteExercise(){
        databaseReference.removeValue();
        Toast.makeText(this,"Exercicio Excluido",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(EditExerciseActivity.this,MainActivity.class));

    }
}