package dipl.project.loyaltyperks.response


import com.google.gson.annotations.SerializedName

data class Payload(
    @SerializedName("loyaltyObjects")
    val loyaltyObjects: List<LoyaltyObject?>?
)