package dipl.project.loyaltyperks.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dipl.project.loyaltyperks.R
import dipl.project.loyaltyperks.databinding.FragmentIntroBinding
import dipl.project.loyaltyperks.viewmodel.UserViewModel
import dipl.project.loyaltyperks.ui.main.MainActivity
import org.koin.android.ext.android.inject


class IntroFragment : Fragment() {

    // Binding
    private lateinit var binding: FragmentIntroBinding

    // ViewModel
    private val userViewModel: UserViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentIntroBinding.inflate(inflater, container, false)

        userViewModel.isUserSignIn {
            if(it!=null && it!=false) {
                var intent = Intent(activity, MainActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
        }


        binding.bIntroLogin.setOnClickListener {
            findNavController().navigate(R.id.action_IntroFragment_to_LoginFragment)
        }

        binding.bIntroRegister.setOnClickListener {
            findNavController().navigate(R.id.action_IntroFragment_to_RegisterFragment)
        }

        return binding.root
    }

}