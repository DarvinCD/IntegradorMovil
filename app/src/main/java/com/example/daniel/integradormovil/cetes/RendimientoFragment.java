package com.example.daniel.integradormovil.cetes;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.daniel.integradormovil.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class RendimientoFragment extends Fragment {

    private TextView unmes,tresmeses, seismeses,docemeses;
    private TextView cetes1, cetes2, cetes3, cetes4;
    private DatabaseReference dbRendimiento;
    private ValueEventListener eventListener;

    double preci=0;

    public RendimientoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_rendimiento, container, false );
        unmes=(TextView) view.findViewById(R.id.mes1);
        tresmeses=(TextView)view.findViewById(R.id.mes3);
        seismeses=(TextView)view.findViewById(R.id.mes6);
        docemeses=(TextView)view.findViewById(R.id.mes12);
        cetes1=(TextView)view.findViewById(R.id.precio1);
        cetes2=(TextView)view.findViewById(R.id.precio2);
        cetes3=(TextView)view.findViewById(R.id.precio3);
        cetes4=(TextView)view.findViewById(R.id.precio4);
        final TextView fecha_actual = (TextView)view.findViewById(R.id.cetes);
        final Calendar c = Calendar.getInstance();
        int anio = c.get(Calendar.YEAR); //obtenemos el año
        String mes ;

        String MES[]={"Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"};

        //Los meses se presentan del 0 al 11, representando el cero a Enero y el once a diciembre, por lo cual para su presentación

//sumaremos 1 a la variable entera MES.
        // mes = mes + 1;
        mes=MES[c.get(Calendar.MONTH)];
        int dia = c.get(Calendar.DAY_OF_MONTH); // obtemos el día.
        fecha_actual.setText("CETES " +dia+" de "+mes+" "+anio); //cambiamos el texto que tiene el TextView por la fecha actual.

        dbRendimiento=FirebaseDatabase.getInstance().getReference().child("CETES");

        // dbRendimiento=FirebaseDatabase.getInstance().getReference().child("CETES").child("3meses").child("interes");

        dbRendimiento.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // String valor=dataSnapshot.getValue().toString();
                // String valor2=dataSnapshot.getValue().toString();

                unmes.setText(dataSnapshot.child("1mes").child("interes").getValue().toString());
                tresmeses.setText(dataSnapshot.child("3meses").child("interes").getValue().toString());
                seismeses.setText(dataSnapshot.child("6meses").child("interes").getValue().toString());
                docemeses.setText(dataSnapshot.child("12meses").child("interes").getValue().toString());

                DecimalFormat decimalFormat=new DecimalFormat("#.0000");

                double interes = Double.parseDouble(unmes.getText().toString().trim());
                double interesp=interes/100;
                //double preci=0;
                preci=(10/(1+((interesp*28)/360)));
                cetes1.setText(decimalFormat.format(preci));

                double interes2 = Double.parseDouble(tresmeses.getText().toString().trim());
                double interesp2=interes2/100;
                double precio2=0;
                precio2=(10/(1+((interesp2*91)/360)));
                cetes2.setText(decimalFormat.format(precio2));

                double interes3 = Double.parseDouble(seismeses.getText().toString().trim());
                double interesp3=interes3/100;
                double precio3=0;
                precio3=(10/(1+((interesp3*182)/360)));
                cetes3.setText(decimalFormat.format(precio3));

                double interes4 = Double.parseDouble(docemeses.getText().toString().trim());
                double interesp4=interes4/100;
                double precio4=0;
                precio4=(10/(1+((interesp4*364)/360)));
                cetes4.setText(decimalFormat.format(precio4));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Fragment fragment = new Fragment();
        Bundle bundle=new Bundle();
        bundle.putDouble("precio",preci);
        fragment.setArguments(bundle);

        return view;

    }

    public interface OnFragmentInteractionListener {
    }
}
