package com.example.myapplication.ui.dashboard

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import com.example.myapplication.ui.dashboard.adapters.WifiNetworkAdapter
import android.content.Context
import android.net.wifi.WifiManager
import android.net.wifi.ScanResult
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private lateinit var wifiNetworkAdapter: WifiNetworkAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var dashboardViewModel: DashboardViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val LOCATION_PERMISSION_REQUEST_CODE = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dashboardViewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Initialize RecyclerView to display Wi-Fi networks
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Set up the refresh button
        binding.refreshButton.setOnClickListener {
            checkPermissions() // Check permissions and fetch available Wi-Fi networks
        }

        // Check for permissions and fetch available Wi-Fi networks
        checkPermissions()

        return root
    }

    private fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
        } else {
            fetchAvailableNetworks()
        }
    }

    @SuppressLint("MissingPermission")
    private fun fetchAvailableNetworks() {
        val wifiManager = context?.getSystemService(Context.WIFI_SERVICE) as WifiManager

        // Start the Wi-Fi scan
        wifiManager.startScan()

        // Get the scan results
        val scanResults: List<ScanResult> = wifiManager.scanResults

        // Log the number of networks found
        Log.d("DashboardFragment", "Number of networks found: ${scanResults.size}")

        // Update the number of networks found TextView
        binding.numberOfNetworksTextView.text = "Number of networks found: ${scanResults.size}"

        // Check if scanResults is not empty
        if (scanResults.isNotEmpty()) {
            // Initialize the adapter with the scan results
            wifiNetworkAdapter = WifiNetworkAdapter(scanResults, binding.numberOfNetworksTextView)
            recyclerView.adapter = wifiNetworkAdapter
        } else {
            Log.d("DashboardFragment", "No Wi-Fi networks found.")
        }

        // Log the network details
        for (result in scanResults) {
            val ssid = result.SSID
            val bssid = result.BSSID
            val capabilities = result.capabilities
            Log.d("DashboardFragment", "SSID: $ssid, BSSID: $bssid, Capabilities: $capabilities")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}