package com.example.demo.db

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.demo.Routes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate

class TripViewModelFactory(private val repository: Repository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TripViewModel::class.java)) {
            return TripViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class TripViewModel (private val repository: Repository) : ViewModel(){

    private var _tripList = MutableStateFlow<List<Trip>>(emptyList())
    val tripList = _tripList.asStateFlow()

    var selectedTrip = mutableStateOf<Trip?>(null)

    private var _currentDays = MutableStateFlow<List<Day>>(emptyList())
    val currentDays = _currentDays.asStateFlow()

    private var _currentPlaces = MutableStateFlow<List<Place>>(emptyList())
    val currentPlaces = _currentPlaces.asStateFlow()

    // 수정 필요
    val currentTrip:Trip? = null

    init {
        viewModelScope.launch(Dispatchers.IO) {
//            val currentDate = LocalDate.now()
            val currentDate = LocalDate.of(2024, 6, 20)
            val currentTrip = getTripByDate(currentDate)
            Log.d("시작", currentTrip.toString())
        }
    }

    val startScreen = if (currentTrip != null) {
        Routes.MainScreen.route
    } else {
        Routes.MyTrip.route
    }

    fun selectTrip(trip: Trip?) {
        selectedTrip.value = trip
    }

    fun insertTrip(trip: Trip){
        viewModelScope.launch {
            repository.insertTrip(trip)
            getAllTrips()
        }
    }
    fun updateTrip(trip: Trip){
        viewModelScope.launch {
            repository.updateTrip(trip)
        }
    }
    fun deleteTrip(trip: Trip){
        viewModelScope.launch {
            repository.deleteTrip(trip)
            getAllTrips()
        }
    }
    fun insertDay(day: Day){
        viewModelScope.launch {
            repository.insertDay(day)
            getAllTrips()
        }
    }
    fun updateDay(day: Day){
        viewModelScope.launch {
            repository.updateDay(day)
        }
    }
    fun deleteDay(day: Day){
        viewModelScope.launch {
            repository.deleteDay(day)
        }
    }
    fun insertPlace(place: Place){
        viewModelScope.launch {
            repository.insertPlace(place)
            getAllTrips()
        }
    }
    fun updatePlace(place: Place){
        viewModelScope.launch {
            repository.updatePlace(place)
        }
    }
    fun deletePlace(place: Place){
        viewModelScope.launch {
            repository.deletePlace(place)
        }
    }

    fun getAllTrips(){
        viewModelScope.launch {
            repository.getAllTrips().collect{
                _tripList.value = it
            }
        }
    }

    suspend fun getDate(date: LocalDate): Day? {
        return repository.getDate(date)
    }

//    fun getTripById(tripId: Int){
//        viewModelScope.launch {
//            repository.getTripById(tripId)
//        }
//    }
//
//    fun getTripIdByDate(date: LocalDate){
//        viewModelScope.launch {
//            repository.getTripIdByDate(date)
//        }
//    }

    suspend fun getTripByDate(date: LocalDate): Trip? {
        return withContext(Dispatchers.IO) {
            repository.getTripByDate(date)
        }
    }

    //    suspend fun getDaysByTripId(tripId: Int): List<Day> {
//        return withContext(Dispatchers.IO) {
//            repository.getDaysByTripId(tripId)
//        }
//    }
    fun getDaysByTripId(tripId: Int) {
        viewModelScope.launch {
            repository.getDaysByTripId(tripId).collect {
                _currentDays.value = it
            }
        }
    }

    fun getPlacesByDayId(dayId: Int) {
        viewModelScope.launch {
            repository.getPlacesByDayId(dayId).collect {
                _currentPlaces.value = it
            }
        }
    }
}