package com.example.demo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


// AddressComponent_view 클래스

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
    var height: String? = null
        get() = field
        set(value) {
            field = value
        }

    var html_attributions: List<String>? = null
        get() = field
        set(value) {
            field = value
        }

    var photo_reference: String? = null
        get() = field
        set(value) {
            field = value
        }

    var width: String? = null
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

    var photos: List<Photo_view>? = null
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
// ViewModel 클래스
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

    var fetchReturn: String? = null

    fun sendFetchReturn(output: String?, viewModel: NavViewModel) {
        viewModel.fetchReturn = output
    }

    // data model -- > api에서 가져온 데이터들이 들어가있는 view model
    private val _dataModel = MutableLiveData<DataModel_view>()
    val dataModel: LiveData<DataModel_view> get() = _dataModel

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

    // Photos setting and getting method
    fun setPhotos(photos: List<Photo_view>?) {
        _dataModel.value?.result?.photos = photos
    }

    fun getPhotos(): List<Photo_view>? {
        return _dataModel.value?.result?.photos
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

    //data models -- > 현재 저장된 데이터 모델들을 관리하는 리스트
    //

    private val _dataModels = MutableLiveData<List<DataModel_view>>(emptyList())
    val dataModels: LiveData<List<DataModel_view>> get() = _dataModels
    fun addDataModel(dataModel: DataModel_view) {
        _dataModels.value = _dataModels.value.orEmpty() + dataModel
    }
    fun removeDataModel(index: Int) {
        _dataModels.value = _dataModels.value?.toMutableList()?.apply {
            removeAt(index)
        }
    }
    fun setDataModels(dataModels: List<DataModel_view>) {
        _dataModels.value = dataModels
    }
    fun getDataModels(): List<DataModel_view>? {
        return _dataModels.value
    }
}