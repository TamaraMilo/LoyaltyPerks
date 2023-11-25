package dipl.project.loyaltyperks.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.UserInfo
import dipl.project.loyaltyperks.model.SignInfoData
import dipl.project.loyaltyperks.model.UserInfoData
import dipl.project.loyaltyperks.repos.AuthRepository
import dipl.project.loyaltyperks.repos.UserRepository

class UserViewModel : ViewModel() {


    private val authRepository: AuthRepository = AuthRepository()
    private val userRepository: UserRepository = UserRepository()

    var user: MutableLiveData<UserInfoData> = MutableLiveData()

    fun signInUser(signInData: SignInfoData, callback: (String?) -> Unit) {
        if (signInData.email.isNotEmpty() && signInData.password.isNotEmpty()) {
            authRepository.signInUser(signInData) {
                if (it != null) {
                    callback.invoke("Error occurred signing in!")
                } else {
                    callback.invoke(null)
                }
            }
        } else {
            callback.invoke("Email and password cannot be empty!")
        }
    }

    fun signUpUser(
        email: String, password: String, conformPassword: String, callback: (String?) -> Unit
    ) {
        if (email.isNotEmpty() && password.isNotEmpty() && conformPassword.isNotEmpty()) {
            if (password.equals(conformPassword)) {
                authRepository.signUpUser(SignInfoData(email, password)) {
                    if (it != null) {
                        callback.invoke("Error creating account")
                    } else {
                        callback.invoke(null)
                    }
                    user.value = null
                }
            } else {
                callback.invoke("Passwords are not matching!")
            }
        } else {
            callback.invoke("There cannot be empty fields")
        }
    }

    fun currentUser(callback: (UserInfoData?) -> Unit) {
        authRepository.getSignedInUserId { itUserId ->
            if (itUserId != null) {
                userRepository.getUserInfo(itUserId) {
                    callback.invoke(it)
                }
            } else {
                callback.invoke(null)
            }
        }
    }

    fun setUserData(userInfoData: UserInfoData, callback: (String?) -> Unit) {
        authRepository.getSignedInUserId {
            it?.let { itId ->
                authRepository.getSignInUserMail { itMail ->
                    itMail?.let { mail ->
                        userInfoData.id = itId
                        userInfoData.email = mail
                        userRepository.setUserInfo(userInfoData) { itRes ->
                            callback.invoke(itRes)
                        }
                    }
                }
            }
        }
    }


    fun isUserSignIn(callback: (Boolean?) -> Unit) {
        authRepository.getSignedInUserId {
            if (it != null) {
                callback.invoke(true)
            } else {
                callback.invoke(false)
            }
        }
    }

    fun getUserDisplayName(callback: (String?) -> Unit) {
        authRepository.getCurrentUserDefaultName {
            callback.invoke(it)
        }
    }

    fun signOutUser() {
        authRepository.signOut()
    }

}

