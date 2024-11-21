package com.example.myapplication.ui.bluetooth.adapters

import android.bluetooth.BluetoothDevice
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

class BluetoothDeviceAdapter : RecyclerView.Adapter<BluetoothDeviceAdapter.BluetoothViewHolder>() {

    private val devices: MutableList<BluetoothDevice> = mutableListOf()

    class BluetoothViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.device_name)
        val addressTextView: TextView = itemView.findViewById(R.id.device_address)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BluetoothViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_bluetooth_device, parent, false)
        return BluetoothViewHolder(view)
    }

    override fun onBindViewHolder(holder: BluetoothViewHolder, position: Int) {
        val device = devices[position]
        holder.nameTextView.text = device.name ?: "Unknown Device"
        holder.addressTextView.text = device.address
    }

    override fun getItemCount(): Int = devices.size

    fun addDevice(device: BluetoothDevice) {
        devices.add(device)
        notifyItemInserted(devices.size - 1)
    }

    fun getDevices(): List<BluetoothDevice> {
        return devices
    }
}