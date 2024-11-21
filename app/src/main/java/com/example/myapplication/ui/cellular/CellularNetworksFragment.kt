package com.example.myapplication.ui.cellular

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.telephony.CellInfo
import android.telephony.CellInfoGsm
import android.telephony.CellInfoLte
import android.telephony.CellInfoCdma
import android.telephony.CellInfoWcdma
import android.telephony.CellIdentityGsm
import android.telephony.CellIdentityLte
import android.telephony.CellIdentityCdma
import android.telephony.CellIdentityWcdma
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

        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.e("CellularNetworksFragment", "Permissions for location are not granted.")
            return
        }

        val cellInfoList: List<CellInfo> = telephonyManager.allCellInfo ?: emptyList()

        for (cellInfo in cellInfoList) {
            val formattedInfo: String
            val signalStrength: String

            when (cellInfo) {
                is android.telephony.CellInfoWcdma -> {
                    val cellIdentity = cellInfo.cellIdentity as android.telephony.CellIdentityWcdma
                    signalStrength = "${cellInfo.cellSignalStrength.dbm} dBm"
                    formattedInfo = """
                        Type: WCDMA
                        Operator: ${cellIdentity.operatorAlphaLong}
                        MCC/MNC: ${cellIdentity.mcc}/${cellIdentity.mnc}
                        Location: LAC=${cellIdentity.lac}, CID=${cellIdentity.cid}
                        Signal: $signalStrength
                        Frequency: UARFCN=${cellIdentity.uarfcn}
                    """.trimIndent()
                }
                is android.telephony.CellInfoLte -> {
                    val cellIdentity = cellInfo.cellIdentity as android.telephony.CellIdentityLte
                    signalStrength = "${cellInfo.cellSignalStrength.dbm} dBm"
                    formattedInfo = """
                        Type: LTE
                        Operator: ${cellIdentity.operatorAlphaLong}
                        MCC/MNC: ${cellIdentity.mcc}/${cellIdentity.mnc}
                        Location: TAC=${cellIdentity.tac}, ECI=${cellIdentity.ci}
                        Signal: $signalStrength
                        Frequency: EARFCN=${cellIdentity.earfcn}
                    """.trimIndent()
                }
                is android.telephony.CellInfoGsm -> {
                    val cellIdentity = cellInfo.cellIdentity as android.telephony.CellIdentityGsm
                    signalStrength = "${cellInfo.cellSignalStrength.dbm} dBm"
                    formattedInfo = """
                        Type: GSM
                        Operator: ${cellIdentity.operatorAlphaLong}
                        MCC/MNC: ${cellIdentity.mcc}/${cellIdentity.mnc}
                        Location: LAC=${cellIdentity.lac}, CID=${cellIdentity.cid}
                        Signal: $signalStrength
                    """.trimIndent()
                }
                is android.telephony.CellInfoCdma -> {
                    val cellIdentity = cellInfo.cellIdentity as android.telephony.CellIdentityCdma
                    signalStrength = "${cellInfo.cellSignalStrength.dbm} dBm"
                    formattedInfo = """
                        Type: CDMA
                        Location: NID=${cellIdentity.networkId}, SID=${cellIdentity.systemId}, BID=${cellIdentity.basestationId}
                        Signal: $signalStrength
                    """.trimIndent()
                }
                else -> {
                    signalStrength = "N/A"
                    formattedInfo = "Unknown Network"
                }
            }

            Log.d("CellularNetworksFragment", formattedInfo)
            networks.add(CellularNetwork(formattedInfo, signalStrength))
        }

        // Mettre à jour l'adaptateur et l'affichage
        cellularNetworkAdapter = CellularNetworkAdapter(networks)
        recyclerView.adapter = cellularNetworkAdapter
        binding.numberOfNetworksTextView.text = "Number of networks found: ${networks.size}"
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
