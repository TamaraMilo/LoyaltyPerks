package dipl.project.loyaltyperks.ui.mainManager

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import dipl.project.loyaltyperks.R
import dipl.project.loyaltyperks.databinding.FragmentScanCustomerBinding
import dipl.project.loyaltyperks.utils.Constants
import dipl.project.loyaltyperks.utils.Constants.REQUEST_CAMERA_CODE


class ScanCustomerFragment : Fragment() {


    private lateinit var binding:FragmentScanCustomerBinding

    private lateinit var codeScanner: CodeScanner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScanCustomerBinding.inflate(inflater, container, false)
        if (ContextCompat.checkSelfPermission(
                this.context!!, Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat.requestPermissions(
                this.requireActivity(), arrayOf(Manifest.permission.CAMERA),
                Constants.REQUEST_CAMERA_CODE
            )
        } else {
            startScanning()
        }


        return binding.root

    }
    private fun startScanning() {
        codeScanner = CodeScanner(this.context!!, binding.scannerView)
        codeScanner.camera = CodeScanner.CAMERA_BACK
        codeScanner.formats = CodeScanner.ALL_FORMATS

        codeScanner.autoFocusMode = AutoFocusMode.SAFE
        codeScanner.scanMode = ScanMode.SINGLE
        codeScanner.isAutoFocusEnabled = true
        codeScanner.isFlashEnabled = false

        codeScanner.decodeCallback = DecodeCallback {
            activity?.runOnUiThread {
                Toast.makeText(this.context, "Scanned: $it", Toast.LENGTH_SHORT).show()
            }
        }

        codeScanner.errorCallback = ErrorCallback {
            activity?.runOnUiThread {
                Toast.makeText(this.context, "Error: $it", Toast.LENGTH_SHORT).show()
            }
        }

        binding.scannerView.setOnClickListener {
            codeScanner.startPreview()
        }


    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CAMERA_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this.context, "Camera permission granted!", Toast.LENGTH_SHORT)
                    .show()
                startScanning()
            } else {
                Toast.makeText(this.context, "Camera permission denied!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (::codeScanner.isInitialized) {
            codeScanner.startPreview()
        }
    }

    override fun onPause() {
        super.onPause()
        if (::codeScanner.isInitialized) {
            codeScanner.releaseResources()
        }
    }

}