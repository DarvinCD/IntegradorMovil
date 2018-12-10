package com.example.daniel.integradormovil;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

import com.example.daniel.integradormovil.model.Meta;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MetasViewHolder> {

    String texto;


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
            DecimalFormatSymbols nformat = new DecimalFormatSymbols();
            nformat.setDecimalSeparator('.');
            DecimalFormat formateador= new DecimalFormat("###,###",nformat);


            int precio=Integer.valueOf(meta.getMonto_meta());
            holder.nombremeta.setText(meta.getNombre_meta());
            holder.montometa.setText("$" +(formateador.format(precio)));
            holder.fechameta.setText(meta.getFecha_meta());
            //int faltan=3500000-precio;
           // holder.ahorradometa.setText(formateador.format(texto2));



    }


    @Override
    public int getItemCount() {
        return metas.size();
       // return ahorros.size();
    }



    class MetasViewHolder extends RecyclerView.ViewHolder{

        TextView nombremeta,montometa,fechameta, ahorradometa;
        CardView cardView;
         public MetasViewHolder(View itemView) {
         super(itemView);
         fechameta =(TextView)itemView.findViewById(R.id.txtfechalimite);
         montometa =(TextView)itemView.findViewById(R.id.txtMontoMenta);
         nombremeta =(TextView)itemView.findViewById(R.id.txtNombreMeta);
      //  cardView=(CardView)itemView.findViewById(R.id.cvContainer);
         ahorradometa=(TextView)itemView.findViewById(R.id.txtAhorroMeta);
     }

    }

}
