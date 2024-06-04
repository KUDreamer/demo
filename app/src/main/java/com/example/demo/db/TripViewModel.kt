package com.example.demo.db

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

    private var _currentPlaces = MutableStateFlow<Map<Int, List<Place>>>(emptyMap())
    val currentPlaces = _currentPlaces.asStateFlow()

    // 수정 필요
    val currentTrip:Trip? = null

    init {
        viewModelScope.launch(Dispatchers.IO) {
//            val currentDate = LocalDate.now()
            val currentDate = LocalDate.of(2024, 6, 20)
            val currentTrip = getTripByDate(currentDate)
//            Log.d("시작", currentTrip.toString())
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
            getAllTrips()
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
            getAllTrips()
        }
    }
    fun deleteDay(day: Day){
        viewModelScope.launch {
            repository.deleteDay(day)
            getAllTrips()
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
            getAllTrips()
        }
    }
    fun deletePlace(place: Place){
        viewModelScope.launch {
            repository.deletePlace(place)
            getAllTrips()
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

    suspend fun getTripByDate(date: LocalDate): Trip? {
        return withContext(Dispatchers.IO) {
            repository.getTripByDate(date)
        }
    }

    fun getDaysByTripId(tripId: Int) {
        viewModelScope.launch {
            repository.getDaysByTripId(tripId).collect {
                _currentDays.value = it
                getPlacesByDayId(it)
            }
        }
    }

    fun getPlacesByDayId(days: List<Day>) {
        viewModelScope.launch {
            days.forEach { day ->
                launch {
                    repository.getPlacesByDayId(day.id).collect { places ->
                        _currentPlaces.value = _currentPlaces.value.toMutableMap().apply {
                            put(day.id, places)
                        }
                    }
                }
            }
        }
    }
}