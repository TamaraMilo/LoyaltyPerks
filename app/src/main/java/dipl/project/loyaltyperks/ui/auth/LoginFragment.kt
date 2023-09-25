package dipl.project.loyaltyperks.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dipl.project.loyaltyperks.R
import dipl.project.loyaltyperks.data.SignInfoData
import dipl.project.loyaltyperks.databinding.FragmentLoginBinding
import dipl.project.loyaltyperks.model.UserViewModel
import org.koin.android.ext.android.inject
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import dipl.project.loyaltyperks.ui.mainUser.MainActivity

class LoginFragment : Fragment() {

    // Binding
    private lateinit var binding: FragmentLoginBinding

    // ViewModel
    private val userViewModel:UserViewModel by inject()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentLoginBinding.inflate(inflater, container, false)

        binding.bSignIn.setOnClickListener {
            val email = binding.etEmailSignIn.text.toString()
            val password = binding.etPasswordSignIn.text.toString()
            userViewModel.signInUser(SignInfoData(email, password)) {
                if(it==null){
                    Toast.makeText(this.context, "Successfully sign in!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this.context, "Error: $it", Toast.LENGTH_SHORT ).show()
                }
            }
        }

        binding.bGoogleSignIn.setOnClickListener {
            signIn()
        }


        binding.tvCreateAccount.setOnClickListener {
            findNavController().navigate(R.id.action_LoginFragment_to_RegisterFragment)
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
                Toast.makeText(this.context, "Google sign in failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun firebaseAuthWithGoogle(idToken: String) {
        val  auth = FirebaseAuth.getInstance()
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this.requireActivity()) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    Toast.makeText(this.context, "Signed in as ${user?.displayName}", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this.context, MainActivity::class.java))
                    activity?.finish()
                } else {
                    Toast.makeText(this.context, "Authentication failed", Toast.LENGTH_SHORT).show()
                }
            }
    }



    companion object {
        private const val RC_SIGN_IN = 9001
    }


}