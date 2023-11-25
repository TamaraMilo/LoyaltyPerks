package dipl.project.loyaltyperks.repos

import com.google.firebase.database.FirebaseDatabase
import dipl.project.loyaltyperks.model.UserInfoData

class UserRepository {


    private val database = FirebaseDatabase.getInstance().reference

    fun getUserInfo(userId: String, callback: (UserInfoData?) -> Unit) {
        database.child("user").child(userId).get().addOnSuccessListener {
                callback(it.getValue(UserInfoData::class.java))
        }
    }

    fun setUserInfo(data: UserInfoData, callback: (String?) -> Unit) {
        database.child("user").child(data.id).setValue(data)
            .addOnSuccessListener {
                callback.invoke(null)
            }
            .addOnFailureListener {
                callback.invoke(it.message)
            }
    }






}