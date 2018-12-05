package com.example.daniel.integradormovil.cetes;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.daniel.integradormovil.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompraFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private Spinner spinner;
    private EditText monto;
    private TextView res, ahorro;
    private Button restar;
    private TextView can;

    int resultado;
    int mon;

    private DatabaseReference dbRendimiento;

    AlertDialog.Builder alerta;


    public CompraFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view=inflater.inflate(R.layout.fragment_compra, container, false );

        res = (TextView) view.findViewById(R.id.mostrar);
        ahorro=(TextView) view.findViewById(R.id.aho);
        restar = (Button) view.findViewById(R.id.calcu);
        monto = (EditText) view.findViewById(R.id.campo_monto);
        spinner = (Spinner) view.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.tasas, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        datos();

        restar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){

                String aux1 = (monto.getText().toString());
                String aux2 = (ahorro.getText().toString());

                if(aux1.length() == 0){
                    alerta = new AlertDialog.Builder(getContext());
                    //alerta.setTitle("Error");
                    alerta.setMessage("Ingresar el monto");
                    alerta.setCancelable(false);
                    alerta.setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogo, int id) {
                            dialogo.cancel();
                            monto.requestFocus();
                        }
                    });
                    alerta.show();
                } else {

                    mon=Integer.valueOf(monto.getText().toString());
                    if (mon < 100 ) {
                        alerta = new AlertDialog.Builder(getContext());
                        //alerta.setTitle("Tenemos un Problema");
                        alerta.setMessage("El monto minímo para invertir es 100 Pesos.");
                        alerta.setCancelable(false);
                        alerta.setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogo, int id) {
                                dialogo.cancel();
                                monto.setText("");
                                monto.requestFocus();
                            }
                        });
                        alerta.show();
                    }

                    int aho=Integer.valueOf(ahorro.getText().toString());

                    if (aho < 100 || mon>aho) {
                        alerta = new AlertDialog.Builder(getContext());
                        //alerta.setTitle("Tenemos un Problema");
                        alerta.setMessage("No Cuentas con el Ahorro Suficiente para Invertir." + '\n'+  "Tu Ahorro es de: $" + aux2);
                        alerta.setCancelable(false);
                        alerta.setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogo, int id) {
                                dialogo.cancel();
                                monto.setText("");
                                monto.requestFocus();
                            }
                        });
                        alerta.show();
                    } else {

                        if (mon>=100 && mon<=aho){
                            resultado = aho - mon;
                            //res.setText("" + resultado);

                            alerta = new AlertDialog.Builder(getContext());
                            //alerta.setTitle("Tenemos un Problema");
                            alerta.setMessage("Felicidades" + '\n'+  "Inversión Exitosa");
                            alerta.setCancelable(false);
                            alerta.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogo, int id) {
                                    dialogo.cancel();
                                    monto.setText("");
                                    monto.requestFocus();
                                }
                            });
                            alerta.show();

                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference refUsuario = database.getReference();
                            int Reinicio = resultado;
                            //refUsuario.child("Ahorro").child("total").setValue(Reinicio);
                            refUsuario.child("usuario").child("12345678").child("ahorro").setValue(Reinicio);




                            // String ultimoMonto = "0";

                            String ultimoMonto = "0";
                            String nuevoMonto =ultimoMonto+1;

                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                            DatabaseReference inversionRef = ref.child("Inversiones").child("CETES").push();

                            inversionRef.child("monto").setValue(mon);




                            //  inversionRef.child("nombres").setValue(nombres);
                            //refUsuario.child("Inversiones").child("monto").setValue(mon);
                        }



                    }

                }

            }
        });

        return view;
    }


    public void datos() {

        DatabaseReference dbTotal = FirebaseDatabase.getInstance().getReference().child("usuario").child("12345678").child("ahorro");

        dbTotal.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String valor = dataSnapshot.getValue().toString();
                ahorro.setText(valor);
                ahorro.setVisibility(View.INVISIBLE);

              //  if ((valor.isEmpty())) {
                    //ejecuta este codigo si se cumple la condición
                    //  texto.setText("Todavía no tienes ahorros");
                //    res.setText("$0");
                //} else {
                    //Ejecuta este codigo si la condición no se cumple:
                    //texto.setText("Usted tiene Ahorrado");
                  //  res.setText("$" + valor);
                //}

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
