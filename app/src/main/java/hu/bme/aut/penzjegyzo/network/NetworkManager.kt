package hu.bme.aut.penzjegyzo.network

import hu.bme.aut.penzjegyzo.data.Valuta
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkManager {
    private val retrofit: Retrofit
    private val valutaApi: ValutaApi

    private const val SERVICE_URL = "https://api.frankfurter.app"

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(SERVICE_URL)
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        valutaApi = retrofit.create(ValutaApi::class.java)
    }

    fun getValuta(amount: Double, from: String): Call<Valuta?> {
        return valutaApi.getValuta(amount, from, "HUF")
    }


}