package com.example.myapplication.ui.dashboard.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.net.wifi.ScanResult
import com.example.myapplication.R

class WifiNetworkAdapter(private val wifiList: List<ScanResult>) : RecyclerView.Adapter<WifiNetworkAdapter.WifiViewHolder>() {

    class WifiViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ssidTextView: TextView = itemView.findViewById(R.id.ssid_text_view)
        val bssidTextView: TextView = itemView.findViewById(R.id.bssid_text_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WifiViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_wifi_network, parent, false)
        return WifiViewHolder(view)
    }

    override fun onBindViewHolder(holder: WifiViewHolder, position: Int) {
        val wifiNetwork = wifiList[position]
        holder.ssidTextView.text = wifiNetwork.SSID
        holder.bssidTextView.text = wifiNetwork.BSSID
    }

    override fun getItemCount(): Int = wifiList.size
} 