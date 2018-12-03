package com.example.daniel.integradormovil;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.daniel.integradormovil.model.Persona;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "Antut";
    private FirebaseAuth mAuth;
    private EditText mEmailField,mPasswordField;
    private Button mLoginBtn;
    private TextView mRegistrarTxt;
    private FirebaseAuth.AuthStateListener mAuthStateListener;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        getSupportActionBar().hide();

        mEmailField = (EditText) findViewById(R.id.email);
        mPasswordField = (EditText) findViewById(R.id.password);

        mAuth = FirebaseAuth.getInstance();

        mLoginBtn=(Button) findViewById(R.id.login);

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if(firebaseAuth.getCurrentUser()!=null) {
                    startActivity(new Intent(LoginActivity.this,NavegationActivity.class));
                }
                else{
                    // Toast.makeText(MainActivity.this, "DATOS INCORRECTOS", Toast.LENGTH_SHORT).show();
                }

            }
        };

        mLoginBtn.setOnClickListener(   new View.OnClickListener(){
            @Override
            public void onClick(View view){

                LoguearUsuario();

            }
        });

        mRegistrarTxt=(TextView) findViewById(R.id.txtregistro);

        mRegistrarTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });
    }

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        mAuth.addAuthStateListener(mAuthStateListener);
    }

    public void onTokenRefresh() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference refUsuario = database.getReference();

        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        refUsuario.child("usuario").child("12345678").child("registrationToken").setValue(refreshedToken);
    }



    public void LoguearUsuario(){

        final String email = mEmailField.getText().toString();
        final String password = mPasswordField.getText().toString();


        if (email.isEmpty() && password.isEmpty()){
            Toast.makeText(LoginActivity.this, "Escriba su correo y contraseña", Toast.LENGTH_SHORT).show();
        }else{

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d(TAG,"signInWithEmail:onComplete:" + task.isSuccessful());


                            onTokenRefresh();


                            if (!task.isSuccessful()) {
                                Log.w(TAG, "signInWithEmail", task.getException());
                                Toast.makeText(LoginActivity.this, "Usuario o contraseña incorrecto.",
                                        Toast.LENGTH_SHORT).show();

                            }

                        }
                    });
        }

    }








}
