package com.example.daniel.integradormovil;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.daniel.integradormovil.model.Meta;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class MetasActivity extends AppCompatActivity implements View.OnClickListener,DatePickerDialog.OnDateSetListener{

    private EditText mNombre, mMonto,mFecha;
    private Button bGuardar, bCancelar;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metas);

        //getSupportActionBar().hide();

        mNombre = (EditText) findViewById(R.id.edtnombre);
        mMonto = (EditText) findViewById(R.id.edtahorro);
        mFecha = (EditText) findViewById(R.id.edttiempo);
        bGuardar = (Button) findViewById(R.id.boton_metas);
        bCancelar = (Button) findViewById(R.id.btncancelarmeta);

        mFecha.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

               /* if() {
                    Toast.makeText(MetasActivity.this, "META GUARDADA", Toast.LENGTH_SHORT).show();
                }
                else{
                    // Toast.makeText(MainActivity.this, "DATOS INCORRECTOS", Toast.LENGTH_SHORT).show();
                }*/

            }
        };



        bGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegistrarMetas();
            }
        });

        bCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MetasActivity.this,NavegationActivity.class));
                finish();
            }
        });


    }

    private void RegistrarMetas(){

        final String nombre_meta = mNombre.getText().toString();
        final String monto = mMonto.getText().toString();
        final String fecha = mFecha.getText().toString();


        if (!nombre_meta.isEmpty() && !monto.isEmpty() && !fecha.isEmpty()){



            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference refUsuario2 = database.getReference("/usuario");
            Query query = refUsuario2.orderByChild("idFirebase").equalTo(mAuth.getUid());
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    String texto = dataSnapshot.getChildren().iterator().next().getKey().toString();
                    Log.e("onChild",texto);
                    DatabaseReference refUsuario = database.getReference("usuario/"+texto+"/meta");

                    Meta meta = new Meta();
                    meta.setNombre_meta(nombre_meta);
                    meta.setMonto_meta(monto);
                    meta.setFecha_meta(fecha);

                    refUsuario.setValue(meta);
                    Toast.makeText(getApplicationContext(),
                            "Meta Guardada",
                            Toast.LENGTH_SHORT).show();

                    LimpiarCampos();

                    startActivity(new Intent(MetasActivity.this, NavegationActivity.class));
                    finish();


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });




        }
        else
        {
            Toast.makeText(MetasActivity.this, "CAMPOS VACIOS", Toast.LENGTH_SHORT).show();
        }

    }


    private void LimpiarCampos(){

        mNombre.setText("");
        mMonto.setText("");
        mFecha.setText("");
    }

    public void showToolbar(String title, boolean upButton) {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.edttiempo:
               // showDatePickerDialog();
                final Calendar c = Calendar.getInstance();
                int year=c.get(Calendar.YEAR);
                int month=c.get(Calendar.MONTH);
                int day=c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                       //  String selectedDate = twoDigits(dayOfMonth) + "/" + twoDigits(month+1) + "/" + year;

                       mFecha.setText(twoDigits(dayOfMonth) + "/" + twoDigits(month+1) + "/" + year);
                        //mFecha.setText(year + "/" + twoDigits(month+1) + "/" + twoDigits(dayOfMonth));
                    }
                },year,month,day
                );

                datePickerDialog.show();
                break;
        }
    }

    /*private void showDatePickerDialog() {


        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because january is zero
                final String selectedDate = twoDigits(day) + "/" + twoDigits(month+1) + "/" + year;
                mFecha.setText(selectedDate);
            }
        });
       newFragment.show();


    } */

    private String twoDigits(int n) {
        return (n<=9) ? ("0"+n) : String.valueOf(n);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }
}
