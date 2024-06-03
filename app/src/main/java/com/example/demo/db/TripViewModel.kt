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

    private var _startScreen: String = Routes.MyTrip.route
    val startScreen: String
        get() = _startScreen

    fun selectItem(trip: Trip?) {
        selectedTrip.value = trip
    }

    fun InsertTrip(trip: Trip){
        viewModelScope.launch {
            repository.InsertTrip(trip)
        }
    }
    fun UpdateTrip(trip: Trip){
        viewModelScope.launch {
            repository.UpdateTrip(trip)
        }
    }
    fun DeleteTrip(trip: Trip){
        viewModelScope.launch {
            repository.DeleteTrip(trip)
        }
    }
    fun InsertDay(day: Day){
        viewModelScope.launch {
            repository.InsertDay(day)
        }
    }
    fun UpdateDay(day: Day){
        viewModelScope.launch {
            repository.UpdateDay(day)
        }
    }
    fun DeleteDay(day: Day){
        viewModelScope.launch {
            repository.DeleteDay(day)
        }
    }
    fun InsertPlace(place: Place){
        viewModelScope.launch {
            repository.InsertPlace(place)
        }
    }
    fun UpdatePlace(place: Place){
        viewModelScope.launch {
            repository.UpdatePlace(place)
        }
    }
    fun DeletePlace(place: Place){
        viewModelScope.launch {
            repository.DeletePlace(place)
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

    fun getTripById(tripId: Int){
        viewModelScope.launch {
            repository.getTripById(tripId)
        }
    }

    fun getTripIdByDate(date: LocalDate){
        viewModelScope.launch {
            repository.getTripIdByDate(date)
        }
    }

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

    fun insertSampleData() {
        viewModelScope.launch {
            repository.InsertTrip(Trip(id = 0, title = "부산 여행", startDate = LocalDate.of(2024, 6, 19), endDate = LocalDate.of(2024, 6, 23)))
            repository.InsertTrip(Trip(id = 1, title = "엄마랑 강릉 여행", startDate = LocalDate.of(2024, 9, 30), endDate = LocalDate.of(2024, 9, 30)))
            repository.InsertTrip(Trip(id = 2, title = "제주도 힐링 여행", startDate = LocalDate.of(2024, 12, 11), endDate = LocalDate.of(2024, 12, 12)))


            repository.InsertDay(Day(id = 0, tripId = 0, date = LocalDate.of(2024, 6, 19), dayOfWeek = LocalDate.of(2024, 6, 19).dayOfWeek))
            repository.InsertDay(Day(id = 1, tripId = 0, date = LocalDate.of(2024, 6, 20), dayOfWeek = LocalDate.of(2024, 6, 20).dayOfWeek))
            repository.InsertDay(Day(id = 2, tripId = 0, date = LocalDate.of(2024, 6, 21), dayOfWeek = LocalDate.of(2024, 6, 21).dayOfWeek))
            repository.InsertDay(Day(id = 3, tripId = 0, date = LocalDate.of(2024, 6, 22), dayOfWeek = LocalDate.of(2024, 6, 22).dayOfWeek))
            repository.InsertDay(Day(id = 4, tripId = 0, date = LocalDate.of(2024, 6, 23), dayOfWeek = LocalDate.of(2024, 6, 23).dayOfWeek))

            repository.InsertDay(Day(id = 5, tripId = 1, date = LocalDate.of(2024, 9, 30), dayOfWeek = LocalDate.of(2024, 9, 30).dayOfWeek))

            repository.InsertDay(Day(id = 6, tripId = 2, date = LocalDate.of(2024, 12, 11), dayOfWeek = LocalDate.of(2024, 12, 11).dayOfWeek))
            repository.InsertDay(Day(id = 7, tripId = 2, date = LocalDate.of(2024, 12, 12), dayOfWeek = LocalDate.of(2024, 12, 12).dayOfWeek))


            repository.InsertPlace(Place(id = 0, dayId = 0, name = "영심이네 순두부찌개"))
            repository.InsertPlace(Place(id = 1, dayId = 0, name = "베이커리 카페"))
            repository.InsertPlace(Place(id = 2, dayId = 0, name = "메가박스", time = "18:00"))
            repository.InsertPlace(Place(id = 3, dayId = 0, name = "숙소"))

            repository.InsertPlace(Place(id = 4, dayId = 1, name = "영심이네 순두부찌개"))
            repository.InsertPlace(Place(id = 5, dayId = 1, name = "스타벅스 **점"))

            repository.InsertPlace(Place(id = 8, dayId = 2, name = "해수욕장"))
            repository.InsertPlace(Place(id = 9, dayId = 2, name = "베이커리 카페"))
            repository.InsertPlace(Place(id = 11, dayId = 2, name = "숙소"))

            repository.InsertPlace(Place(id = 12, dayId = 5, name = "영심이네 순두부찌개"))
            repository.InsertPlace(Place(id = 13, dayId = 5, name = "베이커리 카페"))
            repository.InsertPlace(Place(id = 14, dayId = 5, name = "메가박스", time = "18:00"))
            repository.InsertPlace(Place(id = 15, dayId = 5, name = "숙소"))

            repository.InsertPlace(Place(id = 16, dayId = 6, name = "영심이네 순두부찌개"))
            repository.InsertPlace(Place(id = 17, dayId = 6, name = "베이커리 카페"))
            repository.InsertPlace(Place(id = 18, dayId = 6, name = "메가박스", time = "18:00"))
            repository.InsertPlace(Place(id = 19, dayId = 6, name = "숙소"))

        }
    }
}