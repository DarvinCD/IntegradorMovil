package com.example.daniel.integradormovil.cetes;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.daniel.integradormovil.R;
import com.example.daniel.integradormovil.model.Inversion;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class InversionesFragment extends Fragment {

    ListView lista;
    DatabaseReference bbdd;



    public InversionesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_inversiones, container, false);
        lista = (ListView)view.findViewById(R.id.listView);

        //bbdd = FirebaseDatabase.getInstance().getReference().child("Inversiones");
        bbdd = FirebaseDatabase.getInstance().getReference(getString(R.string.nodo_inversiones));
        //DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        //DatabaseReference inversionRef = ref.child("Inversiones");

        bbdd.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayAdapter<String> adaptador;
                ArrayList<String> listado = new ArrayList<String>();

                for(DataSnapshot datasnapshot: dataSnapshot.getChildren()){
                    Inversion inversion = datasnapshot.getValue(Inversion.class);

                    Long monto = inversion.getMonto();
                    Long tic = inversion.getTitcetes();
                    Long tib = inversion.getTitbonddia();

                    String montoo=monto.toString();;
                    String titbb=tib.toString();;
                    String ticc=tic.toString();;

                    String periodo=inversion.getPeriodo();
                    String gananciafinal=inversion.getGananciatotal();

                    listado.add("Monto Invertido: " +montoo + '\n' +
                            "Periodo de Inversión: " + periodo + '\n' +
                            "Título CETES: " + ticc + '\n' +
                            "Títulos BONDDIA: " + titbb + '\n' +
                            "Ganancia Final: " + gananciafinal + '\n' +""

                    );

                }

                adaptador = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1,listado);
                lista.setAdapter(adaptador);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;
    }

}
