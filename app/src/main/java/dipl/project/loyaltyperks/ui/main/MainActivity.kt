package dipl.project.loyaltyperks.ui.main

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.pay.PayClient
import com.google.android.material.tabs.TabLayoutMediator
import dipl.project.loyaltyperks.R
import dipl.project.loyaltyperks.databinding.ActivityMainBinding
import dipl.project.loyaltyperks.ui.main.adapters.ViewPageAdapter
import dipl.project.loyaltyperks.utils.Constants

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)


        setContentView(binding.root)
        init()
    }

    private fun init() {
        binding.pagerMain.adapter = ViewPageAdapter(this)

        TabLayoutMediator(binding.tabLayout, binding.pagerMain) { _, _ -> }.attach()

        binding.tabLayout.getTabAt(0)?.setIcon(R.drawable.baseline_credit_card_24)
        binding.tabLayout.getTabAt(1)?.setIcon(R.drawable.baseline_person_24)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == Constants.REQUEST_WALLET) {
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
}