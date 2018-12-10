package com.example.daniel.integradormovil;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

public class RegisterActivity extends AppCompatActivity {


    private Button cancelar,registrar;
    private EditText mNombreFieldRegist, mEmailFieldRegist,mPasswordFieldRegist,mSerialFieldRegist;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private static final String TAG = "Antutos";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
       // showToolbar(getResources().getString(R.string.toolbar_Registro), true);

        mNombreFieldRegist = (EditText) findViewById(R.id.txtUsuario);
        mEmailFieldRegist = (EditText) findViewById(R.id.txtEmail);
        mPasswordFieldRegist = (EditText) findViewById(R.id.txtPassword);
        mSerialFieldRegist = (EditText) findViewById(R.id.txtSerial);


        mAuth = FirebaseAuth.getInstance();


        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if(firebaseAuth.getCurrentUser()!=null) {
                    startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                }
                else{
                    // Toast.makeText(MainActivity.this, "DATOS INCORRECTOS", Toast.LENGTH_SHORT).show();
                }

            }
        };



        registrar = (Button) findViewById(R.id.btn_registrar);

        registrar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                RegistrarUsuario();
            }
        });


        cancelar=(Button) findViewById(R.id.boton_cancelar);

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                finish();
            }
        });
    }




    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        mAuth.addAuthStateListener(mAuthStateListener);
    }

    private void RegistrarUsuario(){

        final String email = mEmailFieldRegist.getText().toString();
        final String password = mPasswordFieldRegist.getText().toString();
        final String usuario = mNombreFieldRegist.getText().toString();
        final String serial = mSerialFieldRegist.getText().toString();

        if (!email.isEmpty() && !password.isEmpty() && !usuario.isEmpty() && !serial.isEmpty()){

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d(TAG,"createInWithEmail:onComplete:" + task.isSuccessful());



                            if (task.isSuccessful()) {
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference refUsuario = database.getReference("usuario");


                                Persona persona = new Persona();

                                persona.setEmail(mAuth.getCurrentUser().getEmail());
                                persona.setNombre(usuario);
                                persona.setRegistrationToken(FirebaseInstanceId.getInstance().getToken());
                                persona.setIdFirebase(mAuth.getCurrentUser().getUid());
                                persona.setAhorro("0");

                                refUsuario.child(serial).setValue(persona);

                                Toast.makeText(getApplicationContext(),"Usuario Registrado",
                                        Toast.LENGTH_SHORT).show();

                            }


                            // ...
                        }
                    });
        }
        else
        {
            Toast.makeText(RegisterActivity.this, "CAMPOS VACIOS", Toast.LENGTH_SHORT).show();
        }
    }

    public void showToolbar(String title, boolean upButton) {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }
}
