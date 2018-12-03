package com.example.daniel.integradormovil;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.daniel.integradormovil.model.Meta;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentMetas.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentMetas#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentMetas extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    //Variables...

    public FloatingActionButton fab;
    RecyclerView mRecyclerView;
    ArrayList<Meta> metas;
    RecyclerAdapter adapter;





    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FragmentMetas() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentMetas.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentMetas newInstance(String param1, String param2) {
        FragmentMetas fragment = new FragmentMetas();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View v = inflater.inflate(R.layout.fragment_fragment_metas, container, false);

        fab = (FloatingActionButton)v.findViewById(R.id.fab_meta);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();


                Intent intent = new Intent(getActivity(), MetasActivity.class);
                getActivity().startActivity(intent);
            }
        });


        //Parte Del Listado



        mRecyclerView = (RecyclerView) v.findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager( new LinearLayoutManager(getContext()));

        metas = new ArrayList<Meta>();

       FirebaseDatabase database = FirebaseDatabase.getInstance();


        mRecyclerView.setHasFixedSize(true);

        //adapter = new RecyclerAdapter(metas);
      //  mRecyclerView.setAdapter(adapter);
        //FirebaseDatabase database = FirebaseDatabase.getInstance();
       // adapter = new RecyclerAdapter(getContext(),metas);

        adapter = new RecyclerAdapter(metas);
        mRecyclerView.setAdapter(adapter);


        database.getReference().getRoot().child("usuario").child("12345678").child("meta").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                metas.removeAll(metas);
                Meta meta = dataSnapshot.getValue(Meta.class);
                metas.add(meta);
               /* for (DataSnapshot snapshot :
                        dataSnapshot.getChildren()) {
                    Meta meta = snapshot.getValue(Meta.class);
                    metas.add(meta);
                }*/

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //Toast.makeText(getContext(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });
        return v;
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
