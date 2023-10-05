package dipl.project.loyaltyperks.model

import androidx.lifecycle.ViewModel
import dipl.project.loyaltyperks.data.CardData
import dipl.project.loyaltyperks.repos.AuthRepository
import dipl.project.loyaltyperks.repos.CardRepository

class CardViewModel : ViewModel() {

    private val authRepository: AuthRepository = AuthRepository()
    private val cardRepository: CardRepository = CardRepository()


    fun addCardForUser(card: CardData, callback: (String?) -> Unit) {
        authRepository.getSignedInUserId {
            if (it != null) {
                cardRepository.addCardForClient(it, card) {
                    if (it != null) {
                        callback.invoke("Error saving data!")
                    } else {
                        callback.invoke(null)
                    }
                }
            } else {
                callback.invoke(null)
            }
        }
    }

    fun returnUserCards(callback: (ArrayList<CardData>?) -> Unit) {
        authRepository.getSignedInUserId {
            if (it != null) {
                cardRepository.returnUserCards(it) { cards ->
                    callback.invoke(cards)
                }
            } else {
                callback.invoke(null)
            }
        }
    }
}
