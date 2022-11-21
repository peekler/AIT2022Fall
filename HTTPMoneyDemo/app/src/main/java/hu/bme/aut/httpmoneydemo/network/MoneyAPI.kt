package hu.bme.aut.httpmoneydemo.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


//
// Host: http://data.fixer.io/
// PATH: api/latest
// ?
// Query parameters: access_key=969c37b5a73f8cb2d12c081dcbdc35e6

interface MoneyAPI {

    @GET("/api/latest")
    fun getMoneyRates(@Query("access_key") accessKey: String)
      : Call<MoneyResult>

}