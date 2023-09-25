package dipl.project.loyaltyperks.ui.auth

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import dipl.project.loyaltyperks.data.UserInfoData
import dipl.project.loyaltyperks.data.enum.Roles
import dipl.project.loyaltyperks.databinding.FragmentUserInfoBinding
import dipl.project.loyaltyperks.model.UserViewModel
import dipl.project.loyaltyperks.ui.mainUser.MainActivity
import org.koin.android.ext.android.inject


class UserInfoFragment : Fragment() {

    // Binding
    private lateinit var binding: FragmentUserInfoBinding

    // ViewModel
    private val userViewModel: UserViewModel by inject()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserInfoBinding.inflate(inflater, container, false)

        userViewModel.getUserDisplayName {
            if (it != null) {
                binding.etUserNameAndSurname.text = Editable.Factory.getInstance().newEditable(it)
            }
        }



        binding.edDoneUserInfoButton.setOnClickListener {
            val fullName = binding.etUserNameAndSurname.text.toString()
            val address = binding.etAddress.text.toString()
            val dateOfBirth = binding.etDateOfBirth.text.toString()

            if (fullName.isNotEmpty() && address.isNotEmpty() && dateOfBirth.isNotEmpty()) {
                val user = UserInfoData("", "", fullName, dateOfBirth, address, Roles.USER.toString().lowercase())
                userViewModel.setUserData(user) {
                    if (it == null) {
                        Toast.makeText(
                            this.context, "User data saves successfully!", Toast.LENGTH_SHORT
                        ).show()
                        var intent = Intent(activity, MainActivity::class.java)
                        startActivity(intent)
                        activity?.finish()
                    } else {
                        Toast.makeText(this.context, "Error: $it", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(
                    this.context, "Error: All fields must be entered!", Toast.LENGTH_SHORT
                ).show()
            }
        }
        return binding.root
    }
}