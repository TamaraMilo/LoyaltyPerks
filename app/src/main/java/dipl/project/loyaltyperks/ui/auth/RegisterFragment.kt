package dipl.project.loyaltyperks.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import dipl.project.loyaltyperks.R
import dipl.project.loyaltyperks.databinding.FragmentRegisterBinding
import dipl.project.loyaltyperks.viewmodel.UserViewModel
import dipl.project.loyaltyperks.utils.Constants.RC_SIGN_IN
import org.koin.android.ext.android.inject


class RegisterFragment : Fragment() {

    // Binding
    private lateinit var binding: FragmentRegisterBinding

    // ViewModel
    private val userViewModel: UserViewModel by inject()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentRegisterBinding.inflate(inflater, container, false)


        binding.bSignUp.setOnClickListener {
            val email = binding.etEmailSignUp.text.toString()
            val password = binding.etPasswordSignUp.text.toString()
            val conformPassword = binding.etConformPasswordSignUp.text.toString()
            userViewModel.signUpUser(email, password, conformPassword) {
                if (it == null) {
                    Toast.makeText(this.context, "Account successfully created", Toast.LENGTH_SHORT)
                        .show()
                    findNavController().navigate(R.id.action_RegisterFragment_to_UserInfoFragment)
                } else {
                    Toast.makeText(this.context, "Error: $it", Toast.LENGTH_SHORT).show()
                }
            }
        }
        binding.bGoogleSignIn.setOnClickListener {
            signIn()
        }

        binding.tvHaveAccount.setOnClickListener {
            findNavController().navigate(R.id.action_RegisterFragment_to_LoginFragment)
        }

        return binding.root

    }

    private fun signIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(this.requireActivity(), gso)
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Toast.makeText(
                    this.context,
                    "Google sign in failed: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val auth = FirebaseAuth.getInstance()
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this.requireActivity()) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    Toast.makeText(
                        this.context,
                        "Signed up as ${user?.displayName}",
                        Toast.LENGTH_SHORT
                    ).show()
                    findNavController().navigate(R.id.action_RegisterFragment_to_UserInfoFragment)
                } else {
                    Toast.makeText(this.context, "Authentication failed", Toast.LENGTH_SHORT).show()
                }
            }
    }


}