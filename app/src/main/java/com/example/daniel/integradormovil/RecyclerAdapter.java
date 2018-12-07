package com.example.daniel.integradormovil;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import com.example.daniel.integradormovil.model.Meta;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MetasViewHolder> {


    List<Meta> metas;

    public RecyclerAdapter(List<Meta> metas) {
        this.metas = metas;
    }


/* public RecyclerAdapter(Context c , List<Meta> metas)
    {
        context = c;
        metas = metas;
    }*/

    @NonNull
    @Override
    public MetasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_metas,parent,false);
        MetasViewHolder holder = new MetasViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MetasViewHolder holder, int position) {

        Meta meta = metas.get(position);

        //int precio=Integer.valueOf(holder.montometa .getText().toString());



            holder.nombremeta.setText(meta.getNombre_meta());
            holder.montometa.setText("$" + meta.getMonto_meta());
            holder.fechameta.setText((meta.getFecha_meta()));
        }


    @Override
    public int getItemCount() {
        return metas.size();
    }

      class MetasViewHolder extends RecyclerView.ViewHolder{

        TextView nombremeta,montometa,fechameta;
        CardView cardView;

     public MetasViewHolder(View itemView) {
         super(itemView);
         fechameta =(TextView)itemView.findViewById(R.id.txtfechalimite);
         montometa =(TextView)itemView.findViewById(R.id.txtMontoMenta);
         nombremeta =(TextView)itemView.findViewById(R.id.txtNombreMeta);
        cardView=(CardView)itemView.findViewById(R.id.cvContainer);

     }
 }



}
