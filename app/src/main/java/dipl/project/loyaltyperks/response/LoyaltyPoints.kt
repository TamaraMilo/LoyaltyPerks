package dipl.project.loyaltyperks.response


import com.google.gson.annotations.SerializedName

data class LoyaltyPoints(
    @SerializedName("balance")
    val balance: Balance?,
    @SerializedName("label")
    val label: String?
)