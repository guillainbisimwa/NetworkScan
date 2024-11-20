package com.example.myapplication.ui.dashboard.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.net.wifi.ScanResult
import com.example.myapplication.R

class WifiNetworkAdapter(private val wifiList: List<ScanResult>, private val numberOfNetworksTextView: TextView) : RecyclerView.Adapter<WifiNetworkAdapter.WifiViewHolder>() {

    // ViewHolder class to hold the views for each item
    class WifiViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ssidTextView: TextView = itemView.findViewById(R.id.ssid_text_view)
        val frequencyTextView: TextView = itemView.findViewById(R.id.frequency_text_view)
        val channelTextView: TextView = itemView.findViewById(R.id.channel_text_view)
        val locationTextView: TextView = itemView.findViewById(R.id.location_text_view)
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
        // Set the SSID, frequency (converted to GHz), channel, and location in the respective TextViews
        holder.ssidTextView.text = wifiNetwork.SSID
        holder.frequencyTextView.text = "Frequency: ${wifiNetwork.frequency / 1000.0} GHz" // Convert MHz to GHz
        holder.channelTextView.text = "Channel: ${getChannelFromFrequency(wifiNetwork.frequency)}"
        holder.locationTextView.text = "Location: ${wifiNetwork.BSSID}" // You can replace this with actual location data if available
    }

    // Return the size of the dataset (invoked by the layout manager)
    override fun getItemCount(): Int = wifiList.size

    // Helper function to determine the channel from frequency
    private fun getChannelFromFrequency(frequency: Int): Int {
        return when (frequency) {
            2412 -> 1
            2417 -> 2
            2422 -> 3
            2427 -> 4
            2432 -> 5
            2437 -> 6
            2442 -> 7
            2447 -> 8
            2452 -> 9
            2457 -> 10
            2462 -> 11
            2467 -> 12
            2472 -> 13
            2484 -> 14
            5180 -> 36
            5200 -> 40
            5220 -> 44
            5240 -> 48
            5260 -> 52
            5280 -> 56
            5300 -> 60
            5320 -> 64
            5500 -> 100
            5520 -> 104
            5540 -> 108
            5560 -> 112
            5580 -> 116
            5600 -> 120
            5620 -> 124
            5640 -> 128
            5660 -> 132
            5680 -> 136
            5700 -> 140
            5745 -> 149
            5765 -> 153
            5785 -> 157
            5805 -> 161
            5825 -> 165
            else -> 0 // Unknown channel
        }
    }
}
