package dipl.project.loyaltyperks.repos

import com.google.firebase.database.FirebaseDatabase
import dipl.project.loyaltyperks.model.CardData

class CardRepository {

    private val database = FirebaseDatabase.getInstance().reference

    fun addCardForClient(userId: String, card: CardData, callback: (String?) -> Unit) {
        database.child("user").child(userId).child("cards").child(card.objectId).setValue(card)
            .addOnSuccessListener {
                callback.invoke(null)
            }.addOnFailureListener {
                callback.invoke(it.message)
            }
    }

    fun returnUserCards(userId:String, callback:(ArrayList<CardData>?)-> Unit) {
        database.child("user").child(userId).child("cards").get()
            .addOnSuccessListener {
                val cards = ArrayList<CardData>()
                for(snapshot in it.children) {
                    snapshot.getValue(CardData::class.java)?.let { cardData->
                        cards.add(cardData)
                    }
                }
                callback.invoke(cards)
            }

    }


}