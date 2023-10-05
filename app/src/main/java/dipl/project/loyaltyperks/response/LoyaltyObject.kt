package dipl.project.loyaltyperks.response



import com.google.gson.annotations.SerializedName

data class LoyaltyObject(
    @SerializedName("barcode")
    val barcode: Barcode?,
    @SerializedName("classId")
    val classId: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("loyaltyPoints")
    val loyaltyPoints: LoyaltyPoints?,
    @SerializedName("state")
    val state: String?
)