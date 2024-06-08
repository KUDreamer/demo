package com.example.demo

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
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

// TODO: 장소 데이터 얻어올때 place_id, geometry도 같이 -> 길찾기 할때 이용
// TODO: searchByTextInfo 수정해서 파라미터 하나 더 만들고 type 지정해 놓기(tourist_attraction)

data class PlaceInfo(
    val result: Result
)
data class PlaceInfoA(
    val photo: String
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
    fun getPlaceData(@Query("PLACE_ID") placeId: String): Call<Object>

    @FormUrlEncoded
    @POST("api/searchPlaceInfo")
    suspend fun getPlaceFromQuery(@Field("query") query: String): Response<Object>

    @FormUrlEncoded
    @POST("api/searchByTextInfo")
    fun getNearPlace(@Field("query") query: String, @Field("place_type") type: String): Call<Object>

    @POST("api/directions")
    fun getDirections(@Body request: Map<String, String>): Call<Object>

    @GET("api/giveInfo")
    fun getPlaceDataNew(@Query("PLACE_ID") placeId: String): Call<ResponseBody>

    @FormUrlEncoded
    @POST("api/searchPlaceInfo")
    suspend fun getPlaceFromQueryNew(@Field("query") query: String): Response<ResponseBody>

    @FormUrlEncoded
    @POST("api/searchPlaceInfo")
    suspend fun getPlaceInfo(@Field("query") query: String, @Field("size") size: Int): Response<PlaceInfoA>

    @FormUrlEncoded
    @POST("api/searchByTextInfo")
    fun getNearPlaceNew(@Field("query") query: String, @Field("place_type") type: String): Call<ResponseBody>

    @POST("api/directions")
    fun getDirectionsNew(@Body request: Map<String, String>): Call<ResponseBody>
}

object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:8080/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    val gson: Gson = GsonBuilder()
        .setLenient()
        .create()

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)
}

fun fetchPlaceData(placeId: String, viewModel: NavViewModel) {
    val call = RetrofitClient.apiService.getPlaceData(placeId)
    call.enqueue(object : Callback<Object> {
        override fun onResponse(call: Call<Object>, response: Response<Object>) {
            if (response.isSuccessful) {
                val placeData = response.body()
                Log.d("API_CALL", "Place Data: $placeData")
                viewModel.sendFetchReturn(response.body()?.toString(), viewModel)
            } else {
                Log.e("API_CALL", "Response not successful: ${response.code()}")
            }
        }

        override fun onFailure(call: Call<Object>, t: Throwable) {
            Log.e("API_CALL", "Error: ${t.message}")
        }
    })
}

suspend fun fetchPlaceFromQuery(query: String, viewModel: NavViewModel) {
    try {
        val response = RetrofitClient.apiService.getPlaceFromQuery(query)
        if (response.isSuccessful) {
            Log.d("API_CALL", "Place Info: ${response.body()}")
            viewModel.sendFetchReturn(response.body()?.toString(), viewModel)
        } else {
            Log.e("API_CALL", "Response not successful: ${response.code()}")
        }
    } catch (e: Exception) {
        Log.e("API_CALL", "Error: ${e.message}")
    }
}

suspend fun fetchgetPlaceInfo(query: String, size: Int, viewModel: NavViewModel) {
    try {
        // API 호출
        val response = RetrofitClient.apiService.getPlaceInfo(query, size)
        // 응답이 성공적인 경우
        if (response.isSuccessful) {
            val placeInfoA = response.body() // 이미 변환된 PlaceInfo 객체를 얻음
            if (placeInfoA != null) {
                // 데이터를 ViewModel에 전달
                viewModel.sendFetchReturn(placeInfoA.toString(), viewModel)
                Log.d("API_CALL", "Place Info: $placeInfoA")
            } else {
                Log.e("API_CALL", "Response body is null")
            }
        } else {
            // 성공적이지 않은 응답 코드 처리
            Log.e("API_CALL", "Response not successful: ${response.code()} - ${response.errorBody()?.string()}")
        }
    } catch (e: Exception) {
        // 예외 처리 및 로깅
        Log.e("API_CALL", "Error: ${e.message}", e)
    }
}

fun fetchNearPlace(query: String, type:String, viewModel: NavViewModel) {
    val call = RetrofitClient.apiService.getNearPlace(query, type)
    call.enqueue(object : Callback<Object> {
        override fun onResponse(call: Call<Object>, response: Response<Object>) {
            if (response.isSuccessful) {
                val nearPlace = response.body()
                Log.d("API_CALL", "Near Place: ${nearPlace?.toString()}")
                viewModel.sendFetchReturn(response.body()?.toString(), viewModel)
            } else {
                Log.e("API_CALL", "Response not successful: ${response.code()}")
            }
        }

        override fun onFailure(call: Call<Object>, t: Throwable) {
            Log.e("API_CALL", "Error: ${t.message}")
        }
    })
}

fun fetchDirections(origin: String, destination: String, viewModel: NavViewModel) {
    val request = mapOf("origin" to origin, "destination" to destination)

    val call = RetrofitClient.apiService.getDirections(request)
    call.enqueue(object : Callback<Object> {
        override fun onResponse(call: Call<Object>, response: Response<Object>) {
            if (response.isSuccessful) {
                val directions = response.body()
                Log.d("API_CALL", "Directions: $directions")
                viewModel.sendFetchReturn(response.body()?.toString(), viewModel)
            } else {
                Log.e("API_CALL", "Response not successful: ${response.code()}")
            }
        }

        override fun onFailure(call: Call<Object>, t: Throwable) {
            Log.e("API_CALL", "Error: ${t.message}")
        }
    })
}




fun fetchPlaceDataNew(placeId: String, viewModel: NavViewModel) {
    val call = RetrofitClient.apiService.getPlaceDataNew(placeId)
    call.enqueue(object : Callback<ResponseBody> {
        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
            if (response.isSuccessful) {
                val placeData = response.body()
                var output:String? = placeData?.string()
                Log.d("API_CALL", "Place Data: $output")
                if (output == null) {
                    output = "null"
                }
                viewModel.sendFetchReturnNew(output, viewModel)
//                viewModel.sendFetchReturnNew(response.body()?.string(), viewModel)
            } else {
                Log.e("API_CALL", "Response not successful: ${response.code()}")
            }
        }

        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            Log.e("API_CALL", "Error: ${t.message}")
        }
    })
}

suspend fun fetchPlaceFromQueryNew(query: String, viewModel: NavViewModel) {
    try {
        val response = RetrofitClient.apiService.getPlaceFromQueryNew(query)
        if (response.isSuccessful) {
            val placeInfo = response.body()
            var output:String? = placeInfo?.string()
            Log.d("API_CALL", "Place Info: ${output}")
            if (output == null) {
                output = "null"
            }
            viewModel.sendFetchReturnNew(output, viewModel)
        } else {
            Log.e("API_CALL", "Response not successful: ${response.code()}")
        }
    } catch (e: Exception) {
        Log.e("API_CALL", "Error: ${e.message}")
    }
}

fun fetchNearPlaceNew(query: String, type:String, viewModel: NavViewModel) {
    val call = RetrofitClient.apiService.getNearPlaceNew(query, type)
    call.enqueue(object : Callback<ResponseBody> {
        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
            if (response.isSuccessful) {
                val nearPlace = response.body()
                var output:String? = nearPlace?.string()
                Log.d("API_CALL", "Near Place: ${output}")
                if (output == null) {
                    output = "null"
                }
                viewModel.sendFetchReturnNew(output, viewModel)
            } else {
                Log.e("API_CALL", "Response not successful: ${response.code()}")
            }
        }

        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            Log.e("API_CALL", "Error: ${t.message}")
        }
    })
}

fun fetchDirectionsNew(origin: String, destination: String, viewModel: NavViewModel) {
    val request = mapOf("origin" to origin, "destination" to destination)

    val call = RetrofitClient.apiService.getDirectionsNew(request)
    call.enqueue(object : Callback<ResponseBody> {
        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
            if (response.isSuccessful) {
                val directions = response.body()
                var output:String? = directions?.string()
                Log.d("API_CALL", "Directions: $output")
                if (output == null) {
                    output = "null"
                }
                viewModel.sendFetchReturnNew(output, viewModel)
            } else {
                Log.e("API_CALL", "Response not successful: ${response.code()}")
            }
        }

        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            Log.e("API_CALL", "Error: ${t.message}")
        }
    })
}
