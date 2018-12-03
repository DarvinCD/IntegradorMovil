package com.example.daniel.integradormovil;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    private FloatingActionButton fRegistrarBtn;
    private TextView mVolvertxt;
  //  private Button mRegistrarBtn;
    private EditText mNombreFieldRegist, mEmailFieldRegist,mPasswordFieldRegist,mSerialFieldRegist;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private static final String TAG = "Antutos";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().hide();

        mNombreFieldRegist = (EditText) findViewById(R.id.txtUsuario);
        mEmailFieldRegist = (EditText) findViewById(R.id.txtEmail);
        mPasswordFieldRegist = (EditText) findViewById(R.id.txtPassword);
        mSerialFieldRegist = (EditText) findViewById(R.id.txtSerial);
        fRegistrarBtn = (FloatingActionButton) findViewById(R.id.fab_register);
       // mRegistrarBtn = (Button) findViewById(R.id.botonRegistrar);

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

        fRegistrarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegistrarUsuario();
            }
        });

      /*  mRegistrarBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                RegistrarUsuario();
            }
        });*/

        mVolvertxt=(TextView) findViewById(R.id.txtregreso);

        mVolvertxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
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
    }
}
