package dipl.project.loyaltyperks.response

import com.google.gson.annotations.SerializedName

data class Barcode(
    @SerializedName("alternateText")
    val alternateText: String?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("value")
    val value: String?
    )
