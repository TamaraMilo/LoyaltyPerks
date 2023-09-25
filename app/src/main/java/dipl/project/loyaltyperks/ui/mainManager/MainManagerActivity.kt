package dipl.project.loyaltyperks.ui.mainManager

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import dipl.project.loyaltyperks.R
import dipl.project.loyaltyperks.databinding.ActivityMainManagerBinding

class MainManagerActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainManagerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainManagerBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }


}