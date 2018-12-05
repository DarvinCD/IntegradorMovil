package com.example.daniel.integradormovil;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentAhorro.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class FragmentAhorro extends Fragment {

    private OnFragmentInteractionListener mListener;

    public FragmentAhorro() {
        // Required empty public constructor
    }
    private Button mReiniciarBtn;
    NotificationCompat.Builder notificacion;
    private  static  final int idunic = 001;

    public TextView mAhorroView;
    public  TextView valor;
    DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mRootChild = mDatabaseReference.child("usuario/12345678/ahorro");



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_fragment_ahorro, container, false);



        View v = inflater.inflate(R.layout.fragment_fragment_ahorro, container, false);
        mAhorroView = (TextView)v.findViewById(R.id.txtahorro);
        mReiniciarBtn = (Button)v.findViewById(R.id.btnreiniciar);

        //Reinicio de contador

        mReiniciarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference refUsuario = database.getReference();


                int Reinicio = 0;

                refUsuario.child("usuario").child("12345678").child("ahorro").setValue( Reinicio );


            }
        });




        valor=(TextView)v.findViewById(R.id.textView2);
       // boton = (Button)v.findViewById(R.id.button_noti);
        return v;

      /*  notificacion = new NotificationCompat.Builder(this);

        notificacion.setAutoCancel(true);

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                notificacion.setSmallIcon(R.mipmap.ic_launcher);
                notificacion.setTicker("Nueva Notificacion");
                notificacion.setWhen(System.currentTimeMillis());
                notificacion.setContentTitle("¡Felicidades!");
                notificacion.setContentText("Haz logrado tu meta");


                Intent intent = new Intent( getActivity() ,FragmentMetas.class);
                startActivity(intent);

                PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(),0,intent,PendingIntent.FLAG_UPDATE_CURRENT);

                notificacion.setContentIntent(pendingIntent);


                NotificationManager nm = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
                nm.notify(idunic,notificacion.build());
            }
        });*/

    }


    @Override
    public void onStart() {

        super.onStart();
        mRootChild.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String texto = dataSnapshot.getValue().toString();

              int texto2=Integer.parseInt(texto);

              //  mAhorroView.setText("$" +texto);
                if (texto2==0) {
                    //ejecuta este codigo si se cumple la condición
                    valor.setText("¡SIN AHORROS!");
                    mAhorroView.setText("$0");
                }
                else{
                    //Ejecuta este codigo si la condición no se cumple:
                    valor.setText("¡AHORRADO!");
                    mAhorroView.setText("$" +texto);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
