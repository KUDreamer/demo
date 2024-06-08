/*
view model

1. data model_view => 하나의 여행지 정보 (이름, 사진, 영업시간 정보 등등)

- html
- result (address, phonenumber, name, openinghours, photo, rating) - openinghours (open_now, periods, weekday)
periods - (close, open) - day, time
- status (영업 o x )
2.

start date<string> - 여행 시작날짜
end date<string> - 여행 끝나는 날짜
current date<string> - 현재 여행중인 날짜
dateModels<map <string,list<datamodel_view>> >

날짜를 key로 datamodel list를 담는 list
날짜 별로 여러가지 datamodel(하나의 여행지, 식당) 을 담음
 */

package com.example.demo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

// TimeInfo_view 클래스
class TimeInfo_view {
    var day: String? = null
        get() = field
        set(value) {
            field = value
        }

    var time: String? = null
        get() = field
        set(value) {
            field = value
        }
}

// Period_view 클래스
class Period_view {
    var close: TimeInfo_view? = null
        get() = field
        set(value) {
            field = value
        }

    var open: TimeInfo_view? = null
        get() = field
        set(value) {
            field = value
        }
}

// OpeningHours_view 클래스
class OpeningHours_view {
    var open_now: String? = null
        get() = field
        set(value) {
            field = value
        }

    var periods: List<Period_view>? = null
        get() = field
        set(value) {
            field = value
        }

    var weekday_text: List<String>? = null
        get() = field
        set(value) {
            field = value
        }
}

// Photo_view 클래스
class Photo_view {
    var url: String? = null
        get() = field
        set(value) {
            field = value
        }
}

// Result_view 클래스
class Result_view {
    var address: String? = null
        get() = field
        set(value) {
            field = value
        }

    var formatted_phone_number: String? = null
        get() = field
        set(value) {
            field = value
        }

    var name: String? = null
        get() = field
        set(value) {
            field = value
        }

    var opening_hours: OpeningHours_view? = null
        get() = field
        set(value) {
            field = value
        }

    var photo: Photo_view? = null
        get() = field
        set(value) {
            field = value
        }

    var rating: String? = null
        get() = field
        set(value) {
            field = value
        }

}

// DataModel_view 클래스
class DataModel_view {
    var html_attributions: List<String>? = null
        get() = field
        set(value) {
            field = value
        }

    var result: Result_view? = null
        get() = field
        set(value) {
            field = value
        }

    var status: String? = null
        get() = field
        set(value) {
            field = value
        }
}

// ViewModel class
class NavViewModel : ViewModel() {

    // fetchReturn: 구버전
    // fetchReturnNew 새로운 버전
    var fetchReturn: String? = null
    var fetchReturnNew: String? = null

    fun sendFetchReturn(output: String?, viewModel: NavViewModel) {
        viewModel.fetchReturn = output
    }

    fun sendFetchReturnNew(output: String?, viewModel: NavViewModel) {
        viewModel.fetchReturnNew = output
    }

    // data model -- > api에서 가져온 데이터들이 들어가있는 view model
    private val _dataModel = MutableLiveData<DataModel_view>()
    val dataModel: LiveData<DataModel_view> get() = _dataModel

    //여행 시작 및 종료 날짜
    //여행 날짜 시작 string
    private val _startDate = MutableLiveData<String>()
    //여행 끝나는 날짜 String
    private val _endDate = MutableLiveData<String>()
    private val _currentDate = MutableLiveData<String>()
    val startDate: LiveData<String> get() = _startDate
    val endDate: LiveData<String> get() = _endDate
    val currentDate : LiveData<String> get() = _currentDate

    // 날짜별 데이터 모델
    // 날짜 string을 key값으로 해서 데이터 Models(model) 배열을 가져올 수 있다
    //model -->하나의 activity
    private val _dateModels = MutableLiveData<Map<String, List<DataModel_view>>>(emptyMap())
    val dateModels: LiveData<Map<String, List<DataModel_view>>> get() = _dateModels

    fun setCurrentDate(currentDate: String) {
        _currentDate.value = currentDate
    }
    fun getCurrentDate(): String? {
        return _currentDate.value
    }

    // Set data method
    fun setData(data: DataModel_view) {
        _dataModel.value = data
    }

    // Get data method
    fun getData(): DataModel_view? {
        return _dataModel.value
    }

    // Address setting and getting method
    fun setAddress(address: String?) {
        _dataModel.value?.result?.address = address
    }

    fun getAddress(): String? {
        return _dataModel.value?.result?.address
    }

    // FormattedPhoneNumber setting and getting method
    fun setFormattedPhoneNumber(phoneNumber: String?) {
        _dataModel.value?.result?.formatted_phone_number = phoneNumber
    }

    fun getFormattedPhoneNumber(): String? {
        return _dataModel.value?.result?.formatted_phone_number
    }

    // Name setting and getting method
    fun setName(name: String?) {
        _dataModel.value?.result?.name = name
    }

    fun getName(): String? {
        return _dataModel.value?.result?.name
    }

    // OpeningHours setting and getting method
    fun setOpeningHours(openingHours: OpeningHours_view?) {
        _dataModel.value?.result?.opening_hours = openingHours
    }

    fun getOpeningHours(): OpeningHours_view? {
        return _dataModel.value?.result?.opening_hours
    }

    // Photo setting and getting method
    fun setPhoto(photo: Photo_view?) {
        _dataModel.value?.result?.photo = photo
    }

    fun getPhoto(): Photo_view? {
        return _dataModel.value?.result?.photo
    }

    // Rating setting and getting method
    fun setRating(rating: String?) {
        _dataModel.value?.result?.rating = rating
    }

    fun getRating(): String? {
        return _dataModel.value?.result?.rating
    }

    // HtmlAttributions setting and getting method
    fun setHtmlAttributions(htmlAttributions: List<String>?) {
        _dataModel.value?.html_attributions = htmlAttributions
    }

    fun getHtmlAttributions(): List<String>? {
        return _dataModel.value?.html_attributions
    }

    // Status setting and getting method
    fun setStatus(status: String?) {
        _dataModel.value?.status = status
    }

    fun getStatus(): String? {
        return _dataModel.value?.status
    }

    // 여행 시작 날짜 설정 및 가져오기
    fun setStartDate(startDate: String) {
        _startDate.value = startDate
    }

    fun getStartDate(): String? {
        return _startDate.value
    }

    // 여행 종료 날짜 설정 및 가져오기
    fun setEndDate(endDate: String) {
        _endDate.value = endDate
    }

    fun getEndDate(): String? {
        return _endDate.value
    }

    // 특정 날짜에 DataModel 추가하기
    fun addDataModelForDate(date: String, dataModel: DataModel_view) {
        val currentMap = _dateModels.value?.toMutableMap() ?: mutableMapOf()
        val currentList = currentMap[date]?.toMutableList() ?: mutableListOf()
        currentList.add(dataModel)
        currentMap[date] = currentList
        _dateModels.value = currentMap
    }

    // 특정 날짜의 DataModels 가져오기
    fun getDataModelsForDate(date: String): List<DataModel_view>? {
        return _dateModels.value?.get(date)
    }

    // 특정 날짜의 DataModels 설정하기
    fun setDataModelsForDate(date: String, dataModels: List<DataModel_view>) {
        val currentMap = _dateModels.value?.toMutableMap() ?: mutableMapOf()
        currentMap[date] = dataModels
        _dateModels.value = currentMap
    }
}
