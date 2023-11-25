package dipl.project.loyaltyperks.response

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class PayloadResponse(
    @SerializedName("aud")
    val aud: String?,
    @SerializedName("origins")
    val origins: List<String?>?,
    @SerializedName("iss")
    val iss: String?,
    @SerializedName("iat")
    val iat: Long?,
    @SerializedName("typ")
    val typ: String?,
    @SerializedName("payload")
    val payload: Payload?

)
