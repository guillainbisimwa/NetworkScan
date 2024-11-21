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
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.FragmentBluetoothDevicesBinding
import com.example.myapplication.ui.bluetooth.adapters.BluetoothDeviceAdapter
import java.io.File
import java.io.FileWriter
import androidx.core.content.FileProvider

class BluetoothDevicesFragment : Fragment() {

    private var _binding: FragmentBluetoothDevicesBinding? = null
    private lateinit var bluetoothDeviceAdapter: BluetoothDeviceAdapter
    private lateinit var recyclerView: RecyclerView
    private val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
    private var deviceCount = 0 // Variable to keep track of the number of devices found

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

        // Set up the export button
        binding.exportButton.setOnClickListener {
            Log.d("BluetoothDevicesFragment", "Export button clicked")
            exportDevicesToCSV()
        }

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

                // Get the device name or use the MAC address as a fallback
                val deviceName = device.name ?: "Unknown Device"
                val deviceAddress = device.address

                // Log the device name and address
                Log.d("BluetoothDevicesFragment", "Discovered device: $device, Address: $deviceAddress")

                // Add the device to the adapter
                bluetoothDeviceAdapter.addDevice(device)

                // Increment the device count and update the TextView
                deviceCount++
                binding.numberOfDevicesTextView.text = "Number of devices found: $deviceCount"
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
                    // All permissions granted, you can call exportDevicesToCSV() here if needed
                } else {
                    // Handle the case where permissions are denied
                    Log.e("BluetoothDevicesFragment", "Permissions denied for exporting CSV")
                }
            }
        }
    }

    private fun exportDevicesToCSV() {
        val fileName = "discovered_devices.csv"
        val file = File(requireContext().getExternalFilesDir(null), fileName)

        try {
            val writer = FileWriter(file)
            writer.append("Device Name,Device Address\n")
            for (device in bluetoothDeviceAdapter.getDevices()) {
                writer.append("${device.name ?: "Unknown Device"},${device.address}\n")
            }
            writer.flush()
            writer.close()

            Log.d("BluetoothDevicesFragment", "Devices exported to $fileName")
            
            // Share the CSV file
            shareCSVFile(file)

            // Show a Toast to notify the user
            requireActivity().runOnUiThread {
                Toast.makeText(requireContext(), "Devices exported to $fileName", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Log.e("BluetoothDevicesFragment", "Error writing to CSV file", e)
            requireActivity().runOnUiThread {
                Toast.makeText(requireContext(), "Failed to export devices to CSV", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // New method to share the CSV file
    private fun shareCSVFile(file: File) {
        val uri = FileProvider.getUriForFile(requireContext(), "${requireContext().packageName}.fileprovider", file)
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_STREAM, uri)
            type = "text/csv"
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        startActivity(Intent.createChooser(shareIntent, "Share CSV"))
    }

}