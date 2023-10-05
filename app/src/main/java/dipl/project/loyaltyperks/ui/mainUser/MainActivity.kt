package dipl.project.loyaltyperks.ui.mainUser

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.pay.Pay
import com.google.android.gms.pay.PayClient
import com.google.android.material.tabs.TabLayoutMediator
import dipl.project.loyaltyperks.R
import dipl.project.loyaltyperks.databinding.ActivityMainBinding
import dipl.project.loyaltyperks.ui.mainUser.adapters.ViewPageAdapter
import dipl.project.loyaltyperks.utils.Constants

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        if (isGooglePayInstalled(this)) {
            Toast.makeText(this, "Installed", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Not installed", Toast.LENGTH_SHORT).show()
            Toast.makeText(
                this,
                "Must have google wallet application and account!",
                Toast.LENGTH_SHORT
            ).show()
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.walletnfcrel")
                )
            )
        }



        setContentView(binding.root)
        init()
    }

    private fun init() {
        binding.pagerMain.adapter = ViewPageAdapter(this)

        TabLayoutMediator(binding.tabLayout, binding.pagerMain) { _, _ -> }.attach()

        binding.tabLayout.getTabAt(0)?.setIcon(R.drawable.baseline_credit_card_24)
        binding.tabLayout.getTabAt(1)?.setIcon(R.drawable.baseline_person_24)
    }


    fun isGooglePayInstalled(context: Context): Boolean {

        var packageManager = context.packageManager
        try {
            packageManager.getPackageInfo(
                "com.google.android.apps.walletnfcrel",
                PackageManager.GET_ACTIVITIES
            )
            return true
        } catch (error: PackageManager.NameNotFoundException) {
            return false
        }

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