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

    private var _startScreen: String = Routes.MyTrip.route
    val startScreen: String
        get() = _startScreen

    fun selectTrip(trip: Trip?) {
        selectedTrip.value = trip
        Log.d("SelectedTrip", selectedTrip.value.toString())
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
            repository.getDaysByTripId(tripId).collect{
                _currentDays.value = it
            }
        }
    }

//    suspend fun getPlacesByDayId(dayId: Int): List<Place> {
//        return withContext(Dispatchers.IO) {
//            repository.getPlacesByDayId(dayId)
//        }
//    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val currentDate = LocalDate.now()
            val day = getDate(currentDate)
            _startScreen = if (day != null) {
                Routes.MainScreen.route
            } else {
                Routes.MyTrip.route
            }
        }
    }

}