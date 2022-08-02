package com.example.crudfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class ResgistrationActivity extends AppCompatActivity {

    private TextInputEditText userNameEdt,pwdEdt,cnfPwdWEdt;
    private Button registerBtn;
    private ProgressBar loadingPB;
    private TextView loginTv;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resgistration);
        userNameEdt = findViewById(R.id.idEdtUserName);
        pwdEdt = findViewById(R.id.idEdtPwd);
        cnfPwdWEdt = findViewById(R.id.idEdtConfPwd);
        registerBtn = findViewById(R.id.idBtnRegister);
        loadingPB = findViewById(R.id.idPBLoading);
        loginTv = findViewById(R.id.idTVLogin);
        mAuth = FirebaseAuth.getInstance();
        loginTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ResgistrationActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loadingPB.setVisibility(View.VISIBLE);
                String userName = userNameEdt.getText().toString();
                String pwd = pwdEdt.getText().toString();
                String cnfPwd = cnfPwdWEdt.getText().toString();
                if(!pwd.equals(cnfPwd)){
                    Toast.makeText(ResgistrationActivity.this,"Confira sua senha", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(userName) && TextUtils.isEmpty(pwd) && TextUtils.isEmpty(cnfPwd)){
                    Toast.makeText(ResgistrationActivity.this, "Entre com sua credenciais",Toast.LENGTH_SHORT).show();
                }else{

                    mAuth.createUserWithEmailAndPassword(userName,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                loadingPB.setVisibility(View.GONE);
                                Toast.makeText(ResgistrationActivity.this, "Usuario registrado",Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(ResgistrationActivity.this,LoginActivity.class);
                                startActivity(i);
                                finish();

                            }else{
                                loadingPB.setVisibility(View.GONE);
                                Toast.makeText(ResgistrationActivity.this,"Falha ao registrar usuario",Toast.LENGTH_SHORT).show();

                            }


                        }
                    });
                }


            }
        });
    }
}