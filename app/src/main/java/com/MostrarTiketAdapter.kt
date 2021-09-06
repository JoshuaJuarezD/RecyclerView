package com

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerview.R

class MostrarTiketAdapter(val tikets: List<TicketsResponseItem?>?): RecyclerView.Adapter<MostrarTiketAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val LayaoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(LayaoutInflater.inflate(R.layout.itemtiket,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tiket= tikets?.get(position)
        holder.folio.text = tiket?.folio.toString()
        holder.fecha.text = tiket?.fecha
        holder.descripcion.text = tiket?.descripcion.toString()
        holder.monto.text= tiket?.total
    }

    override fun getItemCount(): Int = tikets?.size!!

    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){


        var folio : TextView
        var fecha : TextView
        var descripcion : TextView
        var monto:  TextView
        var color: TextView
        init {
            folio =itemView.findViewById(R.id.tvFolioTiket)
            fecha =itemView.findViewById(R.id.tvfechaTiket)
            descripcion =itemView.findViewById(R.id.tvdescripcionTiket)
            monto =itemView.findViewById(R.id.tvMontoCompra)
            color=itemView.findViewById(R.id.tvcolorEstadoTiket)
        }
    }
}