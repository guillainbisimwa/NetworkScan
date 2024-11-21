package com.example.myapplication.ui

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import java.io.File
import java.io.FileWriter

class CombinedDataFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var exportButton: Button
    private lateinit var dataTextView: TextView

    private val combinedData = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_combined_data, container, false)

        recyclerView = view.findViewById(R.id.recycler_view)
        exportButton = view.findViewById(R.id.export_button)
        dataTextView = view.findViewById(R.id.data_text_view)

        recyclerView.layoutManager = LinearLayoutManager(context)

        fetchData()

        exportButton.setOnClickListener {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                exportDataToCSV()
            } else {
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
            }
        }

        return view
    }

    private fun fetchData() {
        // Fetch Bluetooth data
        fetchBluetoothData()
        // Fetch Wi-Fi data
        fetchWifiData()
        // Fetch Cellular data
        fetchCellularData()

        // Update RecyclerView with combined data
        recyclerView.adapter = CombinedDataAdapter(combinedData)
    }

    private fun fetchBluetoothData() {
        // Implement Bluetooth data fetching logic here
        combinedData.add("Bluetooth Device: DeviceName, Location: LocationInfo")
    }

    private fun fetchWifiData() {
        // Implement Wi-Fi data fetching logic here
        combinedData.add("Wi-Fi Network: NetworkName, Location: LocationInfo")
    }

    private fun fetchCellularData() {
        // Implement Cellular data fetching logic here
        combinedData.add("Cellular Network: NetworkName, Location: LocationInfo")
    }

    private fun exportDataToCSV() {
        val fileName = "combined_data.csv"
        val file = File(requireContext().getExternalFilesDir(null), fileName)

        try {
            val writer = FileWriter(file)
            writer.append("Type,Name,Location\n")
            for (data in combinedData) {
                writer.append(data).append("\n")
            }
            writer.flush()
            writer.close()
            Log.d("CombinedDataFragment", "Data exported to $fileName")
        } catch (e: Exception) {
            Log.e("CombinedDataFragment", "Error writing to CSV file", e)
        }
    }
} 