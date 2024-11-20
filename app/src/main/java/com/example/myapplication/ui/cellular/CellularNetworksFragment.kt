package com.example.myapplication.ui.cellular

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.telephony.TelephonyManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.FragmentCellularNetworksBinding
import com.example.myapplication.ui.cellular.adapters.CellularNetwork
import com.example.myapplication.ui.cellular.adapters.CellularNetworkAdapter

class CellularNetworksFragment : Fragment() {

    private var _binding: FragmentCellularNetworksBinding? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var cellularNetworkAdapter: CellularNetworkAdapter
    private val binding get() = _binding!!

    private val REQUEST_PERMISSION = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCellularNetworksBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Initialize RecyclerView
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Check for permissions
        checkPermissions()

        return root
    }

    private fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_PERMISSION)
        } else {
            // Permissions are granted, fetch cellular networks
            fetchCellularNetworks()
        }
    }

    private fun fetchCellularNetworks() {
        val telephonyManager = requireContext().getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val networks = mutableListOf<CellularNetwork>()

        // This is a placeholder for actual network fetching logic
        // You would typically use telephonyManager to get the list of networks
        // For demonstration, we will add dummy data
        networks.add(CellularNetwork("Network A", "-85 dBm"))
        networks.add(CellularNetwork("Network B", "-70 dBm"))
        networks.add(CellularNetwork("Network C", "-90 dBm"))

        // Update the adapter
        cellularNetworkAdapter = CellularNetworkAdapter(networks)
        recyclerView.adapter = cellularNetworkAdapter

        // Update the number of networks found
        binding.numberOfNetworksTextView.text = "Number of networks found: ${networks.size}"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
