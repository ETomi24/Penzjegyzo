package hu.bme.aut.penzjegyzo.data

import com.google.gson.annotations.SerializedName
import java.util.*

data class Valuta (
    @SerializedName("amount") val amount : Int,
    @SerializedName("base") val base : String,
    @SerializedName("date") val date : String,
    @SerializedName("rates") val rates : Rates
)
data class Rates(
    @SerializedName("HUF") val HUF : Double
)