package com.example.daniel.integradormovil.cetes;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
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
public class CalculadoraFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener  {

    private EditText editText1;
    private TextView textView, IntBruto, impuesto, totalfinal;
    private TextView inversion, totalinv, rema;
    private TextView monto, cetec, cetesin, gbruta,rcete, icete, tcete;
    private Spinner spinner;
    private CardView cardView;
    Button cal;
    private TextView c1,c2,c3,c4,in;
    private DatabaseReference dbRendimiento;
    private ValueEventListener eventListener;

    private Double preci, precio2,precio3, precio4;
    private Double interesp,interesp2, interesp3, interesp4;
    private TextView ti, ticete, titasa, ta,bonti;

    private Double interes, interes2, interes3, interes4;

    /// BONDDIA
    private Double bonddia, pBonddia, interesBonddia, interesRBonddia;
    private TextView bonddiain, bonddiainversion, valorbon, preciobonddia;
    private TextView GB, GBonddia;


//*****************************************************+


    private int valor1;

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

    AlertDialog.Builder alerta;

    /////////////////////////////////////////////////

    private int intRem;


    public CalculadoraFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view=inflater.inflate(R.layout.fragment_calculadora, container, false );

        editText1=(EditText)view.findViewById(R.id.campo_monto) ;
        textView=(TextView)view.findViewById(R.id.valor);
       // cal=(Button)view.findViewById(R.id.calcu);
        inversion=(TextView)view.findViewById(R.id.inversion);
        totalinv=(TextView)view.findViewById(R.id.total);
        rema=(TextView)view.findViewById(R.id.remanente);
        IntBruto=(TextView)view.findViewById(R.id.gananciaB);
        impuesto=(TextView)view.findViewById(R.id.ISR);

        //Textviews Encabezados
        monto=(TextView)view.findViewById(R.id.mon);
        cetec=(TextView)view.findViewById(R.id.cetescom);
        cetesin=(TextView)view.findViewById(R.id.invc);
        gbruta=(TextView)view.findViewById(R.id.ganc);
        rcete=(TextView)view.findViewById(R.id.remc);
        icete=(TextView)view.findViewById(R.id.is);
        tcete=(TextView)view.findViewById(R.id.tganado);
        ////

        c1=(TextView)view.findViewById(R.id.unmes);
        c2=(TextView)view.findViewById(R.id.dosmes);
        c3=(TextView)view.findViewById(R.id.seismes);
        c4=(TextView)view.findViewById(R.id.docemes);
        in=(TextView)view.findViewById(R.id.bonin);

        ti=(TextView)view.findViewById(R.id.titulo);
        bonti=(TextView)view.findViewById(R.id.bonta);
        //ticete=(TextView)view.findViewById(R.id.cetesti);
        titasa=(TextView)view.findViewById(R.id.tasati);
        ta=(TextView)view.findViewById(R.id.ta);

        //BONDDIA

        bonddiain=(TextView)view.findViewById(R.id.invB);
        bonddiainversion=(TextView)view.findViewById(R.id.inversionB);
        valorbon=(TextView)view.findViewById(R.id.bonvalor);
        preciobonddia=(TextView)view.findViewById(R.id.pb);
        GB=(TextView)view.findViewById(R.id.ganBonddia);
        GBonddia=(TextView)view.findViewById(R.id.gananciaBonddia);

        cardView=(CardView)view.findViewById(R.id.card2);

        //AhorroFragment ahorroFragment=(AhorroFragment) getFragmentManager().findFragmentById(R.id.nav_ahorro);
        totalfinal=(TextView)view.findViewById(R.id.tot);
        cal=(Button)view.findViewById(R.id.calcu);
        spinner=(Spinner)view.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(getContext(), R.array.tasas,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(this);

        datos();
        proceso();
        calcular();


        return view;

    }

    public void datos(){

        dbRendimiento=FirebaseDatabase.getInstance().getReference().child("CETES");
        // dbRendimiento=FirebaseDatabase.getInstance().getReference().child("CETES").child("3meses").child("interes");

        dbRendimiento.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // String valor=dataSnapshot.getValue().toString();
                // String valor2=dataSnapshot.getValue().toString();

                c1.setText(dataSnapshot.child("1mes").child("interes").getValue().toString());
                c2.setText(dataSnapshot.child("3meses").child("interes").getValue().toString());
                c3.setText(dataSnapshot.child("6meses").child("interes").getValue().toString());
                c4.setText(dataSnapshot.child("12meses").child("interes").getValue().toString());
                in.setText(dataSnapshot.child("BONDDIA").child("interes").getValue().toString());
                preciobonddia.setText(dataSnapshot.child("BONDDIA").child("precio").getValue().toString());

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

    public void calcular(){

        cal.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v){

                periodo();
            }
        });
    }

    public void periodo(){

        String selec=spinner.getSelectedItem().toString();

        try {

            if (selec.equals("Seleccionar Plazo")){

                textView.setText("");
                totalinv.setText("");
                textView.setText("");
                inversion.setText("");
                rema.setText("");
                IntBruto.setText("");
                impuesto.setText("");
                totalfinal.setText("");
                monto.setText("");
                cetec.setText("");
                cetesin.setText("");
                gbruta.setText("");
                rcete.setText("");
                icete.setText("");
                tcete.setText("");
                cardView.setVisibility(View.INVISIBLE);
                c1.setVisibility(View.INVISIBLE);
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
                alerta.show();


            } else { //else inicio

                valor1 = Integer.parseInt(editText1.getText().toString());

                if (valor1 >= 100 && valor1 <= 10000000) {

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

                        ti.setVisibility(View.VISIBLE);
                        ti.setText("CETES 28");
                        ta.setVisibility(View.VISIBLE);
                        ta.setText(interes + "%");
                        tasa1 = ((interesp / 360) * 28);
                        ISR = ((ISRanual / 365) * 28);
                        valor2 = preci;
                        interesRBonddia = ((interesBonddia / 360) * 28);
                        total = valor1 / valor2;

                    } else

                    if(selec.equals("3 Meses")){

                        ti.setVisibility(View.VISIBLE);
                        ti.setText("CETES 91");
                        ta.setVisibility(View.VISIBLE);
                        ta.setText(interes2 + "%");
                        tasa1 = ((interesp2 / 360) * 91);
                        ISR = ((ISRanual / 365) * 91);
                        valor2 = precio2;
                        interesRBonddia = ((interesBonddia / 360) * 91);
                        total = valor1 / valor2;

                    } else
                    if (selec.equals("6 Meses")){

                        ti.setVisibility(View.VISIBLE);
                        ti.setText("CETES 182");
                        ta.setVisibility(View.VISIBLE);
                        ta.setText(interes3 + "%");
                        tasa1 = ((interesp3 / 360) * 182);
                        ISR = ((ISRanual / 365) * 182);
                        valor2 = precio3;
                        interesRBonddia = ((interesBonddia / 360) * 182);
                        total = valor1 / valor2;

                    } else
                    if (selec.equals("1 Año")){

                        ti.setVisibility(View.VISIBLE);
                        ti.setText("CETES 365");
                        ta.setVisibility(View.VISIBLE);
                        ta.setText(interes4 + "%");
                        tasa1 = ((interesp4 / 360) * 365);
                        ISR = ((ISRanual / 365) * 365);
                        valor2 = precio4;
                        interesRBonddia = ((interesBonddia / 360) * 365);
                        total = valor1 / valor2;

                    }

                    String str = String.valueOf(total);
                    int intNumber = Integer.parseInt(str.substring(0, str.indexOf('.')));
                    float decNumbert = Float.parseFloat(str.substring(str.indexOf('.')));

                    inv = intNumber * valor2;
                    rem = valor1 - inv;

                    if (rem >= pBonddia) {

                        rem = rem / pBonddia;
                        //bonti.setVisibility(View.VISIBLE);
                        //bonti.setText(bonddia+"%")
                    }



                    String str2 = String.valueOf(rem);
                    intRem = Integer.parseInt(str2.substring(0, str2.indexOf('.')));
                    invBondia = intRem * pBonddia;
                    remnuevo = (valor1 - (inv + invBondia));

                    ganancia = inv * tasa1;
                    IB = invBondia * interesRBonddia;

                    ISRimpuesto = inv * (ISR / 100);
                    double gananciareal = (ganancia - ISRimpuesto);
                    double totalfin = inv + remnuevo + gananciareal + invBondia + IB;

                    //BODIIa
                    bonddiain.setVisibility(View.VISIBLE);
                    bonddiainversion.setVisibility(View.VISIBLE);
                    bonddiainversion.setText("$" + decimalFormat.format(invBondia));

                    GB.setVisibility(View.VISIBLE);
                    GBonddia.setVisibility(View.VISIBLE);
                    GBonddia.setText("$" + decimalFormat.format(IB));


                    valorbon.setText("" + intRem);
                    bonti.setVisibility(View.VISIBLE);
                    bonti.setText(bonddia + "%");
                    cardView.setVisibility(View.VISIBLE);
                    // ticete.setVisibility(View.VISIBLE);
                    titasa.setVisibility(View.VISIBLE);
                    //MONTO INVERTIDO EJEMPLO:100
                    monto.setText("Monto Invertido:");
                    totalinv.setText("$" + valor1);

                    // CANTIDAD DE CETES COMPRADOS
                    cetec.setText("Cetes:");
                    textView.setText("" + intNumber);

                    // Inversion en CETES
                    cetesin.setText("Inversión en Cetes:");
                    inversion.setText("$" + decimalFormat.format(inv));

                    // REMANENTE
                    rcete.setText("Remanente");
                    rema.setText("$" + decimalFormat.format(remnuevo));

                    //INTERES BRUTO
                    gbruta.setText("Interés Bruto:");
                    IntBruto.setText("$" + decimalFormat.format(ganancia));

                    //ISR
                    icete.setText("ISR:");
                    impuesto.setText("$" + decimalFormat.format(ISRimpuesto));

                    //GANANCIA TOTAL
                    tcete.setText("Obtienes al final:");
                    totalfinal.setText("$" + decimalFormat.format(totalfin));



                } //if final

                else {
                    alerta = new AlertDialog.Builder(getContext());
                    //alerta.setTitle("Tenemos un Problema");
                    alerta.setMessage("El Monto debe ser entre 100 y 10,000,000.");
                    alerta.setCancelable(false);
                    alerta.setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogo, int id) {
                            dialogo.cancel();
                            editText1.setText("");
                            editText1.requestFocus();
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
                    editText1.requestFocus();
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
                    editText1.requestFocus();
                }
            });
            alerta.show();
        }

    }


    public void proceso()
    {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                try {
                    if (position == 0) {

                        textView.setText("");
                        totalinv.setText("");
                        textView.setText("");
                        inversion.setText("");
                        rema.setText("");
                        IntBruto.setText("");
                        impuesto.setText("");
                        totalfinal.setText("");
                        monto.setText("");
                        cetec.setText("");
                        cetesin.setText("");
                        gbruta.setText("");
                        rcete.setText("");
                        icete.setText("");
                        tcete.setText("");
                        cardView.setVisibility(view.INVISIBLE);
                        c1.setVisibility(view.INVISIBLE);
                        c2.setVisibility(view.INVISIBLE);
                        c3.setVisibility(view.INVISIBLE);
                        c4.setVisibility(view.INVISIBLE);
                        ti.setVisibility(view.INVISIBLE);
                        //ticete.setVisibility(View.INVISIBLE);
                        titasa.setVisibility(View.INVISIBLE);
                        ta.setVisibility(View.INVISIBLE);
                        in.setVisibility(View.INVISIBLE);
                        bonti.setVisibility(View.INVISIBLE);
                        bonddiain.setVisibility(View.INVISIBLE);
                        bonddiainversion.setVisibility(View.INVISIBLE);
                        preciobonddia.setVisibility(view.INVISIBLE);
                        GB.setVisibility(View.INVISIBLE);
                        GBonddia.setVisibility(view.INVISIBLE);

                    }
                    else {

                        valor1=Integer.parseInt(editText1.getText().toString());

                        if(valor1>=100 && valor1<=10000000){

                            valor1=Integer.parseInt(editText1.getText().toString());

                            valor2=0;
                            total=0;
                            inv=0;
                            rem=0;
                            remnuevo=0;

                            traerta=0;

                            //Tasa a 28 Días
                            tasa1=0;
                            ganancia=0;

                            //Impuesto Anual
                            ISRanual=0.58;

                            //ISR a 28 dias
                            ISR=0;
                            ISRimpuesto=0;
                            DecimalFormat decimalFormat=new DecimalFormat("#.00");

                            //BONDDIA
                            invBondia=0;
                            IB=0;


                            switch(position){
                                case 1:

                                    ti.setVisibility(View.VISIBLE);
                                    ti.setText("CETES 28");
                                    ta.setVisibility(View.VISIBLE);
                                    ta.setText(interes+"%");
                                    tasa1=((interesp/360)*28);
                                    ISR=((ISRanual/365)*28);
                                    valor2=preci;
                                    interesRBonddia=((interesBonddia/360)*28);
                                    total=valor1/valor2;

                                    break;
                                case 2:

                                    ti.setVisibility(View.VISIBLE);
                                    ti.setText("CETES 91");
                                    ta.setVisibility(View.VISIBLE);
                                    ta.setText(interes2+"%");
                                    tasa1=((interesp2/360)*91);
                                    ISR=((ISRanual/365)*91);
                                    valor2=precio2;
                                    interesRBonddia=((interesBonddia/360)*91);
                                    total=valor1/valor2;
                                    break;
                                case 3:

                                    ti.setVisibility(View.VISIBLE);
                                    ti.setText("CETES 182");
                                    ta.setVisibility(View.VISIBLE);
                                    ta.setText(interes3+"%");
                                    tasa1=((interesp3/360)*182);
                                    ISR=((ISRanual/365)*182);
                                    valor2=precio3;
                                    interesRBonddia=((interesBonddia/360)*182);
                                    total=valor1/valor2;
                                    break;
                                case 4:

                                    ti.setVisibility(View.VISIBLE);
                                    ti.setText("CETES 365");
                                    ta.setVisibility(View.VISIBLE);
                                    ta.setText(interes4+"%");
                                    tasa1=((interesp4/360)*365);
                                    ISR=((ISRanual/365)*365);
                                    valor2=precio4;
                                    interesRBonddia=((interesBonddia/360)*365);
                                    total=valor1/valor2;
                            }

                            String str = String.valueOf(total);
                            int intNumber = Integer.parseInt(str.substring(0, str.indexOf('.')));
                            float decNumbert = Float.parseFloat(str.substring(str.indexOf('.')));

                            inv=intNumber*valor2;
                            rem=valor1-inv;

                            if (rem>=pBonddia){

                                rem=rem/pBonddia;
                                //bonti.setVisibility(View.VISIBLE);
                                //bonti.setText(bonddia+"%")
                            }

                            String str2 = String.valueOf(rem);
                            intRem = Integer.parseInt(str2.substring(0, str2.indexOf('.')));
                            invBondia=intRem*pBonddia;
                            remnuevo=(valor1-(inv+invBondia));

                            ganancia=inv*tasa1;
                            IB=invBondia*interesRBonddia;

                            ISRimpuesto=inv*(ISR/100);
                            double gananciareal=(ganancia-ISRimpuesto);
                            double totalfin=inv+remnuevo+gananciareal+invBondia+IB;

                            //BODIIa
                            bonddiain.setVisibility(View.VISIBLE);
                            bonddiainversion.setVisibility(View.VISIBLE);
                            bonddiainversion.setText("$"+decimalFormat.format(invBondia));

                            GB.setVisibility(View.VISIBLE);
                            GBonddia.setVisibility(View.VISIBLE);
                            GBonddia.setText("$"+decimalFormat.format(IB));


                            valorbon.setText(""+intRem);
                            bonti.setVisibility(View.VISIBLE);
                            bonti.setText(bonddia+"%");
                            cardView.setVisibility(View.VISIBLE);
                            // ticete.setVisibility(View.VISIBLE);
                            titasa.setVisibility(View.VISIBLE);
                            //MONTO INVERTIDO EJEMPLO:100
                            monto.setText("Monto Invertido:");
                            totalinv.setText("$"+valor1);

                            // CANTIDAD DE CETES COMPRADOS
                            cetec.setText("Cetes:");
                            textView.setText(""+intNumber);

                            // Inversion en CETES
                            cetesin.setText("Inversión en Cetes:");
                            inversion.setText("$" +decimalFormat.format(inv));

                            // REMANENTE
                            rcete.setText("Remanente");
                            rema.setText("$" +decimalFormat.format(remnuevo));

                            //INTERES BRUTO
                            gbruta.setText("Interés Bruto:");
                            IntBruto.setText("$" +decimalFormat.format(ganancia));

                            //ISR
                            icete.setText("ISR:");
                            impuesto.setText("$" +decimalFormat.format(ISRimpuesto));

                            //GANANCIA TOTAL
                            tcete.setText("Obtienes al final:");
                            totalfinal.setText("$" +decimalFormat.format(totalfin));

                        }
                        else{
                            alerta = new AlertDialog.Builder(getContext());
                            //alerta.setTitle("Tenemos un Problema");
                            alerta.setMessage("El Monto debe ser entre 100 y 10,000,000.");
                            alerta.setCancelable(false);
                            alerta.setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogo, int id) {
                                    dialogo.cancel();
                                    editText1.setText("");
                                    editText1.requestFocus();
                                }
                            });
                            alerta.show();

                        }


                    }
                }
                catch(NumberFormatException e)
                {
                    alerta = new AlertDialog.Builder(getContext());
                    //alerta.setTitle("Faltan Datos");
                    alerta.setMessage("Ingresa el Monto a calcular.");
                    alerta.setCancelable(false);
                    alerta.setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogo, int id) {
                            dialogo.cancel();
                            editText1.requestFocus();
                        }
                    });
                    alerta.show();
                }
                catch(ArithmeticException e)
                {
                    alerta = new AlertDialog.Builder(getContext());
                    //alerta.setTitle("Lo Sentimos");
                    alerta.setMessage("Ha ocurrido un error en la operación.");
                    alerta.setCancelable(false);
                    alerta.setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogo, int id) {
                            dialogo.cancel();
                            editText1.requestFocus();
                        }
                    });
                    alerta.show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }




    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {

    }

    public interface OnFragmentInteractionListener {
    }
}


