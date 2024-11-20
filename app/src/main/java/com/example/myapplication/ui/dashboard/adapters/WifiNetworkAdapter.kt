package com.example.myapplication.ui.dashboard.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.net.wifi.ScanResult
import com.example.myapplication.R

class WifiNetworkAdapter(private val wifiList: List<ScanResult>) : RecyclerView.Adapter<WifiNetworkAdapter.WifiViewHolder>() {

    // ViewHolder class to hold the views for each item
    class WifiViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ssidTextView: TextView = itemView.findViewById(R.id.ssid_text_view)
        val bssidTextView: TextView = itemView.findViewById(R.id.bssid_text_view)
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WifiViewHolder {
        // Inflate the item layout for each Wi-Fi network
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_wifi_network, parent, false)
        return WifiViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: WifiViewHolder, position: Int) {
        // Get the Wi-Fi network at the current position
        val wifiNetwork = wifiList[position]
        // Set the SSID and BSSID in the respective TextViews
        holder.ssidTextView.text = wifiNetwork.SSID
        holder.bssidTextView.text = wifiNetwork.BSSID
    }

    // Return the size of the dataset (invoked by the layout manager)
    override fun getItemCount(): Int = wifiList.size
}
