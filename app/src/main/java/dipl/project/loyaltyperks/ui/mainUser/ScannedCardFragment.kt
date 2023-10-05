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
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.google.android.gms.pay.Pay
import com.google.android.gms.pay.PayApiAvailabilityStatus
import com.google.android.gms.pay.PayClient
import com.google.android.gms.wallet.Wallet
import com.google.android.gms.wallet.WalletObjectsClient
import com.google.gson.Gson
import dipl.project.loyaltyperks.R
import dipl.project.loyaltyperks.data.CardData
import dipl.project.loyaltyperks.data.QRCodeData
import dipl.project.loyaltyperks.databinding.FragmentScannedCardBinding
import dipl.project.loyaltyperks.model.CardViewModel
import dipl.project.loyaltyperks.response.*
import dipl.project.loyaltyperks.utils.Constants.ISSUER_ACC
import dipl.project.loyaltyperks.utils.Constants.ISSUER_ID
import dipl.project.loyaltyperks.utils.Constants.REQUEST_WALLET
import org.koin.android.ext.android.inject
import java.util.*
import kotlin.collections.ArrayList

class ScannedCardFragment(var data: String) : DialogFragment() {


    private lateinit var binding: FragmentScannedCardBinding
    private lateinit var walletClient: PayClient
    private val cardViewModel:CardViewModel by inject()
    private lateinit var loyaltyClassId:String
    private val passId = UUID.randomUUID().toString()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentScannedCardBinding.inflate(inflater, container, false)

        var obj = Gson().fromJson(data, QRCodeData::class.java)


        walletClient = Pay.getClient(this.context!!)


        fetchCanUseGoogleWalletApi()
        loyaltyClassId = obj.loyaltyClass
        binding.tvProgramName.text = obj.programName

        Log.d("TAG_DATA","novo"+loyaltyClassId)

        binding.bAddCard.setOnClickListener {
            val card= CardData(passId)
            card.image = obj.imageUrl
            card.loyaltyProgramName = obj.programName
            card.color = obj.cardColor

            cardViewModel.addCardForUser(card) {
                if(it==null) {val objectNeki = """
{
  "aud": "google",
   "origins": [ "http://localhost:3000/Manager"],
  "iss": "loyalty-perks@loyaltyperks.iam.gserviceaccount.com",
  "iat": ${Date().time / 1000L},
  "typ": "savetowallet",
  "payload": {
    "loyaltyObjects": [
{
        "id": "3388000000022260768.nekiPonovo",
        "loyaltyPoints": {
          "balance": {
            "string": "300"
          },
          "label": "Points"
        },
        "classId": "3388000000022260768.probaMolimTe",
        "state": "active"
      }
    ]
  }
}



      """
                    //var uuid = UUID.randomUUID().toString()
                    var uuid = "nesto"
                    var balance = Balance(string = obj.balance.toString())
                    var loyaltyPoints = LoyaltyPoints(balance = balance, label = "Points")
                    var barcode = Barcode(alternateText = uuid,type="qrCode",value = uuid)
                    var loyaltyObject = LoyaltyObject(classId = "$ISSUER_ID.${obj.loyaltyClass}",
                            id = "$ISSUER_ID.$uuid",
                            loyaltyPoints = loyaltyPoints,
                            state = "active",
                            barcode =  barcode
                        )
                    var listOfLoyaltyObjects = ArrayList<LoyaltyObject>()
                    listOfLoyaltyObjects.add(loyaltyObject)
                    var payload = Payload(loyaltyObjects = listOf(loyaltyObject))
                    var payloadResponse = PayloadResponse(
                        aud = "google",
                        origins= listOf("http://localhost:3000/Manager"),
                        iss =ISSUER_ACC,
                        iat = Date().time / 1000L,
                        typ = "savetowallet",
                        payload = payload
                    )
                    val stringForWallet = Gson().toJson(payloadResponse)



                    walletClient.savePasses(stringForWallet, requireActivity(), REQUEST_WALLET)
                } else {
                    Toast.makeText(this.context, it, Toast.LENGTH_SHORT).show()
                }
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
                    Log.d("TAG_ERROR", "MOZE")
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
                    Log.d("TAG_ERROR", "Added???")
                }

                RESULT_CANCELED -> {
                    // Save operation canceled
                    Log.d("TAG_ERROR", "Canceled???")
                }

                PayClient.SavePassesResult.SAVE_ERROR -> data?.let { intentData ->
                    val errorMessage = intentData.getStringExtra(PayClient.EXTRA_API_ERROR_MESSAGE)
                    errorMessage?.let { Log.d("TAG_ERROR", it) }
                }

                else -> {
                    // Handle unexpected (non-API) exception
                    Log.d("TAG_ERROR", "Something else")
                }
            }
        }
    }


    //private val passClassId = "$ISSUER_ID.$loyaltyClassId"
//    private val objectId = "$ISSUER_ID.$passId"
//    private val objectNew = """
//{
//  "aud": "google",
//  "origins": [ ],
//  "iss": "loyalty-perks@loyaltyperks.iam.gserviceaccount.com",
//  "iat": ${Date().time / 1000L},
//  "typ": "savetowallet",
//  "payload": {
//    "loyaltyObjects": [
//{
//        "id": "$objectId",
//        "loyaltyPoints": {
//          "balance": {
//            "string": "300"
//          },
//          "label": "Points"
//        },
//        "classId": "$passClassId",
//        "state": "active"
//      }
//    ]
//  }
//}
//
//
//
//      """


}