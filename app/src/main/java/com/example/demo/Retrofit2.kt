package com.example.demo


import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Field

// post mapping보고 해당하는 것은 다 해놓기

interface ApiService {
    // 참고용 코드
//    @GET("/searchByTextInfo?{id}")
//    fun getUser(@Path("id") id: Int): Call<String>
//
//    @POST("createUser")
//    fun createUser(@Body user: String): Call<String>
//
//    @FormUrlEncoded
//    @POST("createUser")
//    fun createUser(
//        @Field("name") name: String,
//        @Field("age") age: Int
//    ): Call<String>

    // getDirections 빼고 리턴 값 형태는 모두 동일


    @FormUrlEncoded
    @GET("/giveInfo")
    fun getPlaceData(@Field("PLACE_ID") placeId: String): Call<String>

    @FormUrlEncoded
    @POST("/searchPlaceInfo")
    fun getPlaceFromQuery(@Field("query") query: String): Call<String>

    @FormUrlEncoded
    @POST("/searchByTextInfo")
    fun getNearPlace(@Field("query") query: String): Call<String>

    @POST("/directions")
    fun getDirections(@Body query: String): Call<String>
}

object RetrofitClient {
    private const val BASE_URL = "https://www.example.com/"

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

// 참고용
//fun fetchDataFromServer(id: Int) {
//    val call = RetrofitClient.apiService.getUser(id)
//    call.enqueue(object : Callback<String> {
//        override fun onResponse(call: Call<String>, response: Response<String>) {
//            if (response.isSuccessful) {
//                val user = response.body()
//                Log.d("API_CALL", "User: $user")
//            } else {
//                Log.e("API_CALL", "Response not successful: ${response.code()}")
//            }
//        }
//
//        override fun onFailure(call: Call<String>, t: Throwable) {
//            Log.e("API_CALL", "Error: ${t.message}")
//        }
//    })
//}
