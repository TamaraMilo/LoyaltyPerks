package dipl.project.loyaltyperks.ui.mainUser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import dipl.project.loyaltyperks.databinding.FragmentProfileBinding
import dipl.project.loyaltyperks.model.UserViewModel
import org.koin.android.ext.android.inject


class ProfileFragment : Fragment() {

    // Binding
    private lateinit var binding: FragmentProfileBinding

    // viewModel
    private val userViewModel: UserViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        userViewModel.currentUser {
            if (it != null) {
                binding.tvProfileEmail.text = it.email
                binding.tvAddress.text = it.address
                binding.tvProfileFullName.text = it.fullName
                binding.tvDateOfBirth.text = it.dateOfBirth
            } else {
                Toast.makeText(this.context, "Error retrieving user data!", Toast.LENGTH_SHORT)
                    .show()
            }
        }


        return binding.root

    }


}

