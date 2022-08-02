package com.example.crudfirebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements  ExerciseRVAdapter.ExerciseClickInterface {

    private RecyclerView exerciserRV;
    private ProgressBar loadingPB;
    private FloatingActionButton addFAB;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ArrayList<ExerciseRVModal> exerciseRVModalsArraylist;
    private RelativeLayout bottomSheetRL;
    private ExerciseRVAdapter exerciseRVAdapter;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        exerciserRV = findViewById(R.id.idEdtExerciseName);
        loadingPB = findViewById(R.id.idPBLoading);
        addFAB = findViewById(R.id.idFab);
        firebaseDatabase = FirebaseDatabase.getInstance();
        bottomSheetRL = findViewById(R.id.idRLBSheet);
        mAuth = FirebaseAuth.getInstance();
        databaseReference = firebaseDatabase.getReference("Exercise");
        exerciseRVModalsArraylist = new ArrayList<>();
        exerciseRVAdapter = new ExerciseRVAdapter(exerciseRVModalsArraylist,this,this);
        exerciserRV.setLayoutManager(new LinearLayoutManager(this));
        exerciserRV.setAdapter(exerciseRVAdapter);
        addFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,AddExerciseActivity.class));
            }
        });
        getAllExercises();


        }
    private void getAllExercises(){
        exerciseRVModalsArraylist.clear();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                exerciseRVModalsArraylist.add(snapshot.getValue(ExerciseRVModal.class));
                exerciseRVAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                exerciseRVAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                loadingPB.setVisibility(View.GONE);
                exerciseRVAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    @Override
    public void onExerciseClick(int position) {
        displayBottomSheet(exerciseRVModalsArraylist.get(position));

    }

    private void displayBottomSheet(ExerciseRVModal exerciseRVModal){
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View layout = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_dialog,bottomSheetRL);
        bottomSheetDialog.setContentView(layout);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.show();

        TextView exerciseNameTV = layout.findViewById(R.id.idRVExerciseName);
        TextView exerciseDescTV = layout.findViewById(R.id.idTVDescription);
        TextView chosen = layout.findViewById(R.id.idIVturorialPrice);
        ImageView exerciseIV = layout.findViewById(R.id.idIVExercise);
        Button editBtn = layout.findViewById(R.id.idIVExercise);
        Button viewDeteils = layout.findViewById(R.id.idTVDescription);

        exerciseNameTV.setText(exerciseRVModal.getExerciseName());
        exerciseDescTV.setText(exerciseRVModal.getExerciseDescription());
        chosen.setText(exerciseRVModal.getBestChosen());
        Picasso.get().load(exerciseRVModal.getExerciseImg()).into((Target) exerciseIV);

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, EditExerciseActivity.class);
                i.putExtra("exercise", exerciseRVModal);
                startActivity(i);


            }
        });

        viewDeteils.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData((Uri.parse(exerciseRVModal.getExerciseLink())));
                startActivity(i);

            }
        });

    }
    public boolean OnCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.idLogOut:
                Toast.makeText(this,"Desconectar",Toast.LENGTH_SHORT).show();
                mAuth.signOut();
                Intent i = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(i);
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }



    }
}