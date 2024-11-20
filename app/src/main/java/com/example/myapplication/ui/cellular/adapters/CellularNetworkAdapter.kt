package com.example.myapplication.ui.cellular.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

data class CellularNetwork(val name: String, val signalStrength: String)

class CellularNetworkAdapter(private val networks: List<CellularNetwork>) : RecyclerView.Adapter<CellularNetworkAdapter.CellularViewHolder>() {

    class CellularViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val networkNameTextView: TextView = itemView.findViewById(R.id.network_name)
        val networkSignalStrengthTextView: TextView = itemView.findViewById(R.id.network_signal_strength)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CellularViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cellular_network, parent, false)
        return CellularViewHolder(view)
    }

    override fun onBindViewHolder(holder: CellularViewHolder, position: Int) {
        val network = networks[position]
        holder.networkNameTextView.text = network.name
        holder.networkSignalStrengthTextView.text = network.signalStrength
    }

    override fun getItemCount(): Int = networks.size
}
