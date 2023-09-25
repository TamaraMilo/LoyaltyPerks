package dipl.project.loyaltyperks.repos


import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import dipl.project.loyaltyperks.data.SignInfoData


class AuthRepository {

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()


    fun signInUser(signInData: SignInfoData, callback: (String?) -> Unit) {
        auth.signInWithEmailAndPassword(signInData.password, signInData.password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    callback.invoke(null)
                } else {
                    callback.invoke(it.exception?.message)
                }
            }
    }

    fun signUpUser(signInData: SignInfoData, callback: (String?) -> Unit) {
        auth.createUserWithEmailAndPassword(signInData.email, signInData.password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    callback.invoke(null)
                } else {
                    callback.invoke(it.exception?.message)
                }
            }
    }

    fun getSignedInUserId(callback: (String?) -> Unit) {
        callback.invoke(auth.currentUser?.uid)
    }

    fun getSignInUserMail(callback: (String?) -> Unit) {
        callback.invoke(auth.currentUser?.email)
    }

    fun getCurrentUserDefaultName(callback: (String?) -> Unit) {
        callback(auth.currentUser?.displayName)
    }


}