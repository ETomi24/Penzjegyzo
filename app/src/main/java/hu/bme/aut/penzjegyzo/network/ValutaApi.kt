package hu.bme.aut.penzjegyzo.network

import retrofit2.http.GET
import hu.bme.aut.penzjegyzo.data.Valuta
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Path
import retrofit2.http.Query

interface ValutaApi {
    @GET("/latest")
    fun getValuta(
        @Query("amount") amount : Double,
        @Query("from") from : String,
        @Query("to") to : String,
    ):Call<Valuta?>
}