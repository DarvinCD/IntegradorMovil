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

import java.text.DecimalFormat;

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
    int aho;

    /******************************/

    private TextView c1,c2,c3,c4,in;

    private Double preci, precio2,precio3, precio4;
    private Double interesp,interesp2, interesp3, interesp4;
    private TextView ti, ticete, titasa,bonti;

    private Double interes, interes2, interes3, interes4;

    /// BONDDIA
    private Double bonddia, pBonddia, interesBonddia, interesRBonddia;
    private TextView bonddiain, bonddiainversion, valorbon, preciobonddia;
    private TextView GB, GBonddia;


    private double valor2;
    private double total;
    private double inv;
    private double rem;
    private double remnuevo;

    private double traerta;

    //Tasa a 28 Días
    private double tasa1;
    private double ganancia;

    //Impuesto Anual
    private double ISRanual;

    //ISR a 28 dias
    private double ISR;
    private double ISRimpuesto;
    DecimalFormat decimalFormat=new DecimalFormat("#.00");

    //BONDDIA
    private double invBondia;
    private double IB;

    private String tit;
    private String ta;


    String ce1;
    String ce2;
    String ce3;
    String ce4;
    String in1;
    String preciobonddia1;
    private int intRem;

    private DatabaseReference dbRendimiento;

    /**********************/

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

        /***************************************************************************/

        c1=(TextView)view.findViewById(R.id.unmes);
        c2=(TextView)view.findViewById(R.id.dosmes);
        c3=(TextView)view.findViewById(R.id.seismes);
        c4=(TextView)view.findViewById(R.id.docemes);
        in=(TextView)view.findViewById(R.id.bonin);
        preciobonddia=(TextView)view.findViewById(R.id.pb);
        //ta=(TextView)view.findViewById(R.id.ta);


        /****************************************************************************/
        spinner = (Spinner) view.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.tasas, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        cetesdatos();
        datos();

        restar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){

                cetesdatos();
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

                    aho=Integer.valueOf(ahorro.getText().toString());

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

                        comprar();

                    }

                }

            }
        });


        return view;
    }


    public void cetesdatos(){

        dbRendimiento=FirebaseDatabase.getInstance().getReference().child("CETES");
        // dbRendimiento=FirebaseDatabase.getInstance().getReference().child("CETES").child("3meses").child("interes");

        dbRendimiento.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ce1=dataSnapshot.child("1mes").child("interes").getValue().toString();
                ce2=dataSnapshot.child("3meses").child("interes").getValue().toString();
                ce3=dataSnapshot.child("6meses").child("interes").getValue().toString();
                ce4=dataSnapshot.child("12meses").child("interes").getValue().toString();
                in1=dataSnapshot.child("BONDDIA").child("interes").getValue().toString();
                preciobonddia1=dataSnapshot.child("BONDDIA").child("precio").getValue().toString();

                // String valor2=dataSnapshot.getValue().toString();
                c1.setVisibility(View.INVISIBLE);
                c2.setVisibility(View.INVISIBLE);
                c3.setVisibility(View.INVISIBLE);
                c4.setVisibility(View.INVISIBLE);
                in.setVisibility(View.INVISIBLE);
                preciobonddia.setVisibility(View.INVISIBLE);

                c1.setText(ce1);
                c2.setText(ce2);
                c3.setText(ce3);
                c4.setText(ce4);
                in.setText(in1);
                preciobonddia.setText(preciobonddia1);

                DecimalFormat decimalFormat=new DecimalFormat("#.0000");


                bonddia=Double.parseDouble(in.getText().toString().trim());
                pBonddia=Double.parseDouble(preciobonddia.getText().toString().trim());
                interesBonddia=bonddia/100;

                interes = Double.parseDouble(c1.getText().toString().trim());;
                interesp=interes/100;
                //double preci=0;
                preci=(10/(1+((interesp*28)/360)));
                //cetes1=(decimalFormat.format(preci));

                interes2 = Double.parseDouble(c2.getText().toString().trim());
                interesp2=interes2/100;
                //double precio2=0;
                precio2=(10/(1+((interesp2*91)/360)));
                //cetes2.setText(decimalFormat.format(precio2));

                interes3 = Double.parseDouble(c3.getText().toString().trim());
                interesp3=interes3/100;
                //            double precio3=0;
                precio3=(10/(1+((interesp3*182)/360)));
                //        cetes3.setText(decimalFormat.format(precio3));

                interes4 = Double.parseDouble(c4.getText().toString().trim());
                interesp4=interes4/100;
                //  double precio4=0;
                precio4=(10/(1+((interesp4*365)/360)));
                //cetes4.setText(decimalFormat.format(precio4));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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

    public void comprar(){

        String selec=spinner.getSelectedItem().toString();

        try {

            if (selec.equals("Seleccionar Plazo")){

         /*       c1.setVisibility(View.INVISIBLE);
                c2.setVisibility(View.INVISIBLE);
                c3.setVisibility(View.INVISIBLE);
                c4.setVisibility(View.INVISIBLE);
                ti.setVisibility(View.INVISIBLE);
                //ticete.setVisibility(View.INVISIBLE);
                titasa.setVisibility(View.INVISIBLE);
                ta.setVisibility(View.INVISIBLE);
                in.setVisibility(View.INVISIBLE);
                bonti.setVisibility(View.INVISIBLE);
                bonddiain.setVisibility(View.INVISIBLE);
                bonddiainversion.setVisibility(View.INVISIBLE);
                preciobonddia.setVisibility(View.INVISIBLE);
                GB.setVisibility(View.INVISIBLE);
                GBonddia.setVisibility(View.INVISIBLE);

                alerta = new AlertDialog.Builder(getContext());
                //alerta.setTitle("Tenemos un Problema");
                alerta.setMessage("Debes Seleccionar un Plazo");
                alerta.setCancelable(false);
                alerta.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo, int id) {
                        dialogo.cancel();
                        monto.setText("");
                        monto.requestFocus();
                    }
                });
                alerta.show(); */


            } else { //else inicio

                if (mon>=100 && mon<=aho) {

                    valor2 = 0;
                    total = 0;
                    inv = 0;
                    rem = 0;
                    remnuevo = 0;

                    traerta = 0;

                    //Tasa a 28 Días
                    tasa1 = 0;
                    ganancia = 0;

                    //Impuesto Anual
                    ISRanual = 0.58;

                    //ISR a 28 dias
                    ISR = 0;
                    ISRimpuesto = 0;
                    DecimalFormat decimalFormat = new DecimalFormat("#.00");

                    //BONDDIA
                    invBondia = 0;
                    IB = 0;

                    if (selec.equals("1 Mes")){

                        //ti.setVisibility(View.VISIBLE);
                        tit=("1 Mes");
                        //ta.setVisibility(View.VISIBLE);
                        ta=ce1;
                        tasa1 = ((interesp / 360) * 28);
                        ISR = ((ISRanual / 365) * 28);
                        valor2 = preci;
                        interesRBonddia = ((interesBonddia / 360) * 28);
                        total = mon / valor2;

                    } else

                    if(selec.equals("3 Meses")){

                        //ti.setVisibility(View.VISIBLE);
                        tit=("3 Meses");
                        //ta.setVisibility(View.VISIBLE);
                        ta=(ce2);
                        tasa1 = ((interesp2 / 360) * 91);
                        ISR = ((ISRanual / 365) * 91);
                        valor2 = precio2;
                        interesRBonddia = ((interesBonddia / 360) * 91);
                        total = mon / valor2;

                    } else
                    if (selec.equals("6 Meses")){

                        //ti.setVisibility(View.VISIBLE);
                        tit=("6 Meses");
                        //ta.setVisibility(View.VISIBLE);
                        ta=(ce3);
                        tasa1 = ((interesp3 / 360) * 182);
                        ISR = ((ISRanual / 365) * 182);
                        valor2 = precio3;
                        interesRBonddia = ((interesBonddia / 360) * 182);
                        total = mon/ valor2;

                    } else
                    if (selec.equals("1 Año")){

                        //ti.setVisibility(View.VISIBLE);
                        tit=("1 Año");
                        //ta.setVisibility(View.VISIBLE);
                        ta=(ce4);
                        tasa1 = ((interesp4 / 360) * 365);
                        ISR = ((ISRanual / 365) * 365);
                        valor2 = precio4;
                        interesRBonddia = ((interesBonddia / 360) * 365);
                        total = mon / valor2;

                    }

                    String str = String.valueOf(total);
                    int intNumber = Integer.parseInt(str.substring(0, str.indexOf('.')));
                    float decNumbert = Float.parseFloat(str.substring(str.indexOf('.')));

                    inv = intNumber * valor2;
                    rem = mon - inv;

                    if (rem >= pBonddia) {

                        rem = rem / pBonddia;
                        //bonti.setVisibility(View.VISIBLE);
                        //bonti.setText(bonddia+"%")
                    }



                    String str2 = String.valueOf(rem);
                    intRem = Integer.parseInt(str2.substring(0, str2.indexOf('.')));
                    invBondia = intRem * pBonddia;
                    remnuevo = (mon - (inv + invBondia));

                    ganancia = inv * tasa1;
                    IB = invBondia * interesRBonddia;

                    ISRimpuesto = inv * (ISR / 100);
                    double gananciareal = (ganancia - ISRimpuesto);
                    double totalfin = inv + remnuevo + gananciareal + invBondia + IB;


                    /********************************************************///
                    //EJEMPLO INSERCION A FIREBASE

                    resultado = aho - mon;
                   // res.setText("" + resultado);

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
                    refUsuario.child("usuario").child("12345678").child("ahorro").setValue( Reinicio );

                    //String nuevoMonto ="1";
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                    DatabaseReference inversionRef = ref.child("Inversiones").push();

                    //int ultimoMonto = 0;

                    // String neww =Integer.valueOf(nuevoMonto).toString();
                    //Integer.valueOf(monto.getText().toString());


                    inversionRef.child("monto").setValue(mon);
                    inversionRef.child("inversionbonddia").setValue(decimalFormat.format(invBondia));
                    inversionRef.child("tasacetes").setValue(ta);
                    inversionRef.child("tasabonddia").setValue(in1);
                    inversionRef.child("periodo").setValue(tit);
                    inversionRef.child("titcetes").setValue(intNumber);
                    inversionRef.child("titbonddia").setValue(intRem);
                    inversionRef.child("inversioncetes").setValue(decimalFormat.format(inv));
                    inversionRef.child("interesbonddia").setValue(decimalFormat.format(IB));
                    inversionRef.child("interesbruto").setValue(decimalFormat.format(ganancia));
                    inversionRef.child("temanente").setValue(decimalFormat.format(remnuevo));
                    inversionRef.child("isr").setValue(decimalFormat.format(ISRimpuesto));
                    inversionRef.child("gananciatotal").setValue(decimalFormat.format(totalfin));





                    /*******************************************************/

                    //BODIIa
                    //bonddiain.setVisibility(View.VISIBLE);
                    //bonddiainversion.setVisibility(View.VISIBLE);
                    //** bonddiainversion.setText("$" + decimalFormat.format(invBondia));

                    //GB.setVisibility(View.VISIBLE);
                    //GBonddia.setVisibility(View.VISIBLE);
                    //**GBonddia.setText("$" + decimalFormat.format(IB));


                    //**valorbon.setText("" + intRem);
                    //bonti.setVisibility(View.VISIBLE);
                    //**bonti.setText(bonddia + "%");
                    //cardView.setVisibility(View.VISIBLE);
                    // ticete.setVisibility(View.VISIBLE);
                    //titasa.setVisibility(View.VISIBLE);
                    //MONTO INVERTIDO EJEMPLO:100
                    //monto.setText("Monto Invertido:");
                    //**totalinv.setText("$" + valor1);

                    // CANTIDAD DE CETES COMPRADOS
                    //cetec.setText("Cetes:");
                    //** textView.setText("" + intNumber);

                    // Inversion en CETES
                    //cetesin.setText("Inversión en Cetes:");
                    //** inversion.setText("$" + decimalFormat.format(inv));

                    // REMANENTE
                    //rcete.setText("Remanente");
                    //** rema.setText("$" + decimalFormat.format(remnuevo));

                    //INTERES BRUTO
                    //gbruta.setText("Interés Bruto:");
                    //**IntBruto.setText("$" + decimalFormat.format(ganancia));

                    //ISR
                    //icete.setText("ISR:");
                    //**impuesto.setText("$" + decimalFormat.format(ISRimpuesto));

                    //GANANCIA TOTAL
                    //tcete.setText("Obtienes al final:");
                    //** totalfinal.setText("$" + decimalFormat.format(totalfin));

                } //if final

                else {
                    alerta = new AlertDialog.Builder(getContext());
                    //alerta.setTitle("Tenemos un Problema");
                    alerta.setMessage("El Monto debe ser entre 100 y 10,000,000.");
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


            } //else final


        }

        catch (NumberFormatException e) {
            alerta = new AlertDialog.Builder(getContext());
            //alerta.setTitle("Faltan Datos");
            alerta.setMessage("Ingresa el Monto a calcular.");
            alerta.setCancelable(false);
            alerta.setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo, int id) {
                    dialogo.cancel();
                    monto.requestFocus();
                }
            });
            alerta.show();
        } catch (ArithmeticException e) {
            alerta = new AlertDialog.Builder(getContext());
            //alerta.setTitle("Lo Sentimos");
            alerta.setMessage("Ha ocurrido un error en la operación.");
            alerta.setCancelable(false);
            alerta.setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo, int id) {
                    dialogo.cancel();
                    monto.requestFocus();
                }
            });
            alerta.show();
        }

    }
}
