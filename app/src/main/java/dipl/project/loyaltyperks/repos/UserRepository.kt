package dipl.project.loyaltyperks.repos

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue
import dipl.project.loyaltyperks.data.UserInfoData

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
    fun userRole(userId: String, callback: (String?) -> Unit) {
        database.child("user").child(userId).child("role").get()
            .addOnSuccessListener {
                callback.invoke(it.getValue(String::class.java))
            }
    }



}