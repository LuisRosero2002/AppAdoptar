package com.example.appfirebaselogin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


class PerroAdapter(private var listaPokemones:List<PerroResult>): RecyclerView.Adapter<PerroViewHolder>() {
    //obtengo la cantidad de datos de la lista
    override fun getItemCount(): Int=listaPokemones.size


    override fun onBindViewHolder(holder:PerroViewHolder,position:Int) {
        val item=listaPokemones[position]
        holder.bind(item)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType:Int):PerroViewHolder {
        val layout= LayoutInflater.from(parent.context)
        return PerroViewHolder(layout.inflate(R.layout.item_pokemon,parent,false))
    }


    fun actualizarLista(lista:List<PerroResult>){
        this.listaPokemones=lista
        notifyDataSetChanged()
    }
}

