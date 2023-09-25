package dipl.project.loyaltyperks.ui.mainUser

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.android.gms.pay.Pay
import com.google.android.gms.pay.PayApiAvailabilityStatus
import com.google.android.gms.pay.PayClient
import com.google.gson.Gson
import dipl.project.loyaltyperks.R
import dipl.project.loyaltyperks.data.QRCodeData
import dipl.project.loyaltyperks.databinding.FragmentScannedCardBinding
import dipl.project.loyaltyperks.utils.Constants.ISSUER_ID
import dipl.project.loyaltyperks.utils.Constants.REQUEST_WALLET


class ScannedCardFragment(var data:String) : Fragment() {


    private lateinit var binding:FragmentScannedCardBinding
    private lateinit var walletClient: PayClient


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentScannedCardBinding.inflate(inflater, container, false)


        var obj = Gson().fromJson(data, QRCodeData::class.java)


        var fileName = "loyaltyclass.json"

//        var loyaltyClass: String? = context?.let {
//            it.assets.open(fileName).bufferedReader().use {
//                it.readText()
//            }
//        }

//        loyaltyClass = loyaltyClass?.replace("ISSUER_ID",ISSUER_ID)
//        loyaltyClass =loyaltyClass?.replace("LOYALTY_CLASS_ID",obj.loyaltyClass)
//        loyaltyClass =loyaltyClass?.replace("PROGRAM_NAME",obj.programName)
//        loyaltyClass =loyaltyClass?.replace("IMAGE_URL",obj.imageUrl)

//        fileName = "loyaltyobject.json"
//        Toast.makeText(this.context, loyaltyClass, Toast.LENGTH_SHORT).show()
//        var loyaltyObject  =  context?.let {
//            it.assets.open(fileName).bufferedReader().use { it.readText() }
//        }

//        loyaltyObject = loyaltyObject?.replace("ISSUER_ID", ISSUER_ID)
//        loyaltyObject =loyaltyObject?.replace("LOYALTY_CLASS_ID",obj.loyaltyClass)
//        loyaltyObject =loyaltyObject?.replace("OBJECT_ID", uid)
//        loyaltyObject =loyaltyObject?.replace("BALANCE", "100")
//        loyaltyObject = loyaltyObject?.replace("BARCODE_VALUE", uid)

//        Toast.makeText(this.context, loyaltyObject, Toast.LENGTH_SHORT).show()

        walletClient = Pay.getClient(this.context!!)

        fileName = "payload.json"
        var payload = context?.let {
            it.assets.open(fileName).bufferedReader().use { it.readText() }
        }

        //Toast.makeText(this.context, payload, Toast.LENGTH_SHORT).show()



            //payload = payload?.replace("TIME", System.currentTimeMillis().toString())
//        loyaltyClass?.let { payload =payload?.replace("LOYALTY_CLASS", it) }
//        loyaltyObject?.let { payload =payload?.replace("LOYALTY_OBJECT", it) }

        binding.tvProgramName.text = obj.programName
        binding.tvNumOfPoints.text = obj.balance.toString()

        Glide.with(this.context!!).load(obj.imageUrl).fitCenter().into(binding.ivProgramImage)

        binding.bAddCard.setOnClickListener {
            payload?.let {

                walletClient.savePasses(it, requireActivity(),REQUEST_WALLET)
            }
        }


        binding.root.setBackgroundColor(Color.parseColor(obj.cardColor))
        return binding.root
    }

    private fun fetchCanUseGoogleWalletApi() {
        walletClient
            .getPayApiAvailabilityStatus(PayClient.RequestType.SAVE_PASSES)
            .addOnSuccessListener { status ->
                if (status == PayApiAvailabilityStatus.AVAILABLE) {
                    Toast.makeText(this.context, "Moze", Toast.LENGTH_SHORT).show()
                } else {
                    // The user or device is not eligible for using the Pay API
                }
            }
            .addOnFailureListener {
                // Hide the button and optionally show an error message
            }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_WALLET) {
            when (resultCode) {
                RESULT_OK -> {
                    // Pass saved successfully
                }

                RESULT_CANCELED -> {
                    // Save operation canceled
                }

                PayClient.SavePassesResult.SAVE_ERROR -> data?.let { intentData ->
                    val errorMessage = intentData.getStringExtra(PayClient.EXTRA_API_ERROR_MESSAGE)
                    }

                else -> {
                    // Handle unexpected (non-API) exception
                }
            }
        }
    }


}