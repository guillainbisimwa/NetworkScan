package com.example.myapplication.ui.bluetooth

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.FragmentBluetoothDevicesBinding
import com.example.myapplication.ui.bluetooth.adapters.BluetoothDeviceAdapter

class BluetoothDevicesFragment : Fragment() {

    private var _binding: FragmentBluetoothDevicesBinding? = null
    private lateinit var bluetoothDeviceAdapter: BluetoothDeviceAdapter
    private lateinit var recyclerView: RecyclerView
    private val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()

    private val binding get() = _binding!!

    private val REQUEST_ENABLE_BT = 1
    private val REQUEST_PERMISSION_BT = 2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBluetoothDevicesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Initialize RecyclerView to display Bluetooth devices
        recyclerView = binding.recyclerView
        bluetoothDeviceAdapter = BluetoothDeviceAdapter()
        recyclerView.adapter = bluetoothDeviceAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Check for Bluetooth permissions
        checkBluetoothPermissions()

        return root
    }

    private fun checkBluetoothPermissions() {
        val permissionsNeeded = mutableListOf<String>()

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED) {
            permissionsNeeded.add(Manifest.permission.BLUETOOTH)
        }
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.BLUETOOTH_ADMIN) != PackageManager.PERMISSION_GRANTED) {
            permissionsNeeded.add(Manifest.permission.BLUETOOTH_ADMIN)
        }
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
            permissionsNeeded.add(Manifest.permission.BLUETOOTH_SCAN)
        }
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            permissionsNeeded.add(Manifest.permission.BLUETOOTH_CONNECT)
        }

        if (permissionsNeeded.isNotEmpty()) {
            // Request the permissions
            ActivityCompat.requestPermissions(requireActivity(), permissionsNeeded.toTypedArray(), REQUEST_PERMISSION_BT)
        } else {
            // Permissions are granted, start discovery
            startBluetoothDiscovery()
        }
    }

    private fun startBluetoothDiscovery() {
        // Register for broadcasts when a device is discovered
        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        requireActivity().registerReceiver(receiver, filter)

        // Start discovery
        bluetoothAdapter?.startDiscovery()
    }

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val action: String? = intent?.action
            if (BluetoothDevice.ACTION_FOUND == action) {
                // Get the BluetoothDevice object from the Intent
                val device: BluetoothDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)!!
                
                // Log the device name and address
                Log.d("BluetoothDevicesFragment", "Discovered device: ${device.name}, Address: ${device.address}")

                // Add the device to the adapter
                bluetoothDeviceAdapter.addDevice(device)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        requireActivity().unregisterReceiver(receiver) // Unregister the receiver
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_PERMISSION_BT -> {
                if (grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                    // All permissions granted, start discovery
                    startBluetoothDiscovery()
                } else {
                    // Handle the case where permissions are denied
                    // You can show a message to the user or disable Bluetooth features
                }
            }
        }
    }
}