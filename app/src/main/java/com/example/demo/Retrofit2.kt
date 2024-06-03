package com.example.demo

import android.util.Log
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Field
import retrofit2.http.Query

data class PlaceInfo(
    val result: Result
)

data class Result(
    val name: String?,
    val formatted_address: String?,
    val rating: Float?,
    val photos: List<Photo>?
)

data class Photo(
    val photo_reference: String,
    val height: Int,
    val width: Int
)

data class DirectionsResponse(
    val routes: List<Route>
)

data class Route(
    val legs: List<Leg>
)

data class Leg(
    val steps: List<Step>
)

data class Step(
    val transitDetails: TransitDetails?
)

data class TransitDetails(
    val arrival_stop: Stop,
    val departure_stop: Stop
)

data class Stop(
    val name: String
)


interface ApiService {
    @GET("api/giveInfo")
    fun getPlaceData(@Query("PLACE_ID") placeId: String): Call<PlaceInfo>

    @FormUrlEncoded
    @POST("api/searchPlaceInfo")
    fun getPlaceFromQuery(@Field("query") query: String): Call<PlaceInfo>

    @FormUrlEncoded
    @POST("api/searchByTextInfo")
    fun getNearPlace(@Field("query") query: String): Call<PlaceInfo>

    @POST("api/directions")
    fun getDirections(@Body request: Map<String, String>): Call<DirectionsResponse>
}

object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:8080/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)
}

fun fetchPlaceData(placeId: String) {
    val call = RetrofitClient.apiService.getPlaceData(placeId)
    call.enqueue(object : Callback<PlaceInfo> {
        override fun onResponse(call: Call<PlaceInfo>, response: Response<PlaceInfo>) {
            if (response.isSuccessful) {
                val placeData = response.body()
                Log.d("API_CALL", "Place Data: $placeData")
            } else {
                Log.e("API_CALL", "Response not successful: ${response.code()}")
            }
        }

        override fun onFailure(call: Call<PlaceInfo>, t: Throwable) {
            Log.e("API_CALL", "Error: ${t.message}")
        }
    })
}

fun fetchPlaceFromQuery(query: String) {
    val call = RetrofitClient.apiService.getPlaceFromQuery(query)
    call.enqueue(object : Callback<PlaceInfo> {
        override fun onResponse(call: Call<PlaceInfo>, response: Response<PlaceInfo>) {
            if (response.isSuccessful) {
                val placeInfo = response.body()
                Log.d("API_CALL", "Place Info: $placeInfo")
            } else {
                Log.e("API_CALL", "Response not successful: ${response.code()}")
            }
        }

        override fun onFailure(call: Call<PlaceInfo>, t: Throwable) {
            Log.e("API_CALL", "Error: ${t.message}")
        }
    })
}

fun fetchNearPlace(query: String) {
    val call = RetrofitClient.apiService.getNearPlace(query)
    call.enqueue(object : Callback<PlaceInfo> {
        override fun onResponse(call: Call<PlaceInfo>, response: Response<PlaceInfo>) {
            if (response.isSuccessful) {
                val nearPlace = response.body()
                Log.d("API_CALL", "Near Place: ${nearPlace?.result?.name}")
            } else {
                Log.e("API_CALL", "Response not successful: ${response.code()}")
            }
        }

        override fun onFailure(call: Call<PlaceInfo>, t: Throwable) {
            Log.e("API_CALL", "Error: ${t.message}")
        }
    })
}

fun fetchDirections(origin: String, destination: String) {
    val request = mapOf("origin" to origin, "destination" to destination)
    val call = RetrofitClient.apiService.getDirections(request)
    call.enqueue(object : Callback<DirectionsResponse> {
        override fun onResponse(call: Call<DirectionsResponse>, response: Response<DirectionsResponse>) {
            if (response.isSuccessful) {
                val directions = response.body()
                Log.d("API_CALL", "Directions: $directions")
            } else {
                Log.e("API_CALL", "Response not successful: ${response.code()}")
            }
        }

        override fun onFailure(call: Call<DirectionsResponse>, t: Throwable) {
            Log.e("API_CALL", "Error: ${t.message}")
        }
    })
}
