package dipl.project.loyaltyperks.ui.mainManager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dipl.project.loyaltyperks.R
import dipl.project.loyaltyperks.databinding.FragmentAddCustomerBinding


class AddCustomerFragment : Fragment() {


    private lateinit var binding: FragmentAddCustomerBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddCustomerBinding.inflate(inflater, container, false)

        return binding.root
    }


}