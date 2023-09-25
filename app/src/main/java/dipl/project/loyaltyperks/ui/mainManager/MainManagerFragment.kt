package dipl.project.loyaltyperks.ui.mainManager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import dipl.project.loyaltyperks.R
import dipl.project.loyaltyperks.databinding.FragmentMainManagerBinding


class MainManagerFragment : Fragment() {

    private lateinit var binding: FragmentMainManagerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMainManagerBinding.inflate(inflater, container, false)


        binding.bAddCustomer.setOnClickListener {
            findNavController().navigate(R.id.action_MainManagerFragment_to_AddCustomerFragment)
        }

        binding.bScanCustomer.setOnClickListener {
            findNavController().navigate(R.id.action_MainManagerFragment_to_ScanCustomerFragment)
        }

        return binding.root
    }


}