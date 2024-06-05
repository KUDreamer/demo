package com.example.demo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


// AddressComponent_view 클래스
class AddressComponent_view {
    var long_name: String? = null
        get() = field
        set(value) {
            field = value
        }

    var short_name: String? = null
        get() = field
        set(value) {
            field = value
        }

    var types: List<String>? = null
        get() = field
        set(value) {
            field = value
        }
}

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
    var address_components: List<AddressComponent_view>? = null
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

// ViewModel 클래스
class NavViewModel : ViewModel() {

    private val _dataModel = MutableLiveData<DataModel_view>()
    var fetchReturn:String? = null

    fun sendFetchReturn(output:String?, viewModel: NavViewModel) {
        viewModel.fetchReturn = output
    }
    val dataModel: LiveData<DataModel_view> get() = _dataModel

    // 데이터 설정 메소드
    fun setData(data: DataModel_view) {
        _dataModel.value = data
    }

    // 데이터 가져오는 메소드
    fun getData(): DataModel_view? {
        return _dataModel.value
    }

    // AddressComponent 설정 및 가져오기 메소드
    fun setAddressComponents(addressComponents: List<AddressComponent_view>?) {
        _dataModel.value?.result?.address_components = addressComponents
    }

    fun getAddressComponents(): List<AddressComponent_view>? {
        return _dataModel.value?.result?.address_components
    }

    // FormattedPhoneNumber 설정 및 가져오기 메소드
    fun setFormattedPhoneNumber(phoneNumber: String?) {
        _dataModel.value?.result?.formatted_phone_number = phoneNumber
    }

    fun getFormattedPhoneNumber(): String? {
        return _dataModel.value?.result?.formatted_phone_number
    }

    // Name 설정 및 가져오기 메소드
    fun setName(name: String?) {
        _dataModel.value?.result?.name = name
    }

    fun getName(): String? {
        return _dataModel.value?.result?.name
    }

    // OpeningHours 설정 및 가져오기 메소드
    fun setOpeningHours(openingHours: OpeningHours_view?) {
        _dataModel.value?.result?.opening_hours = openingHours
    }

    fun getOpeningHours(): OpeningHours_view? {
        return _dataModel.value?.result?.opening_hours
    }

    // Photos 설정 및 가져오기 메소드
    fun setPhotos(photos: List<Photo_view>?) {
        _dataModel.value?.result?.photos = photos
    }

    fun getPhotos(): List<Photo_view>? {
        return _dataModel.value?.result?.photos
    }

    // Rating 설정 및 가져오기 메소드
    fun setRating(rating: String?) {
        _dataModel.value?.result?.rating = rating
    }

    fun getRating(): String? {
        return _dataModel.value?.result?.rating
    }

    // HtmlAttributions 설정 및 가져오기 메소드
    fun setHtmlAttributions(htmlAttributions: List<String>?) {
        _dataModel.value?.html_attributions = htmlAttributions
    }

    fun getHtmlAttributions(): List<String>? {
        return _dataModel.value?.html_attributions
    }

    // Status 설정 및 가져오기 메소드
    fun setStatus(status: String?) {
        _dataModel.value?.status = status
    }

    fun getStatus(): String? {
        return _dataModel.value?.status
    }
}

