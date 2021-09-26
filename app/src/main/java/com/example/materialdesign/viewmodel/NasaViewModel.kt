package com.example.materialdesign.viewmodel

import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.materialdesign.repository.RetrofitImpl
import com.example.materialdesign.repository.dto.MarsPhotosServerResponseData
import com.example.materialdesign.repository.dto.PODServerResponseData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class NasaViewModel(
    private val liveDataToObserve: MutableLiveData<PODData> = MutableLiveData(),
    private val retrofitImpl: RetrofitImpl = RetrofitImpl(),
) : ViewModel() {

    fun getLiveData(): LiveData<PODData> {
        return liveDataToObserve
    }

    fun getPODFromServer(day: Int) {
        val date = getDate(day)
        liveDataToObserve.postValue(PODData.Loading)
        val apiKey = com.example.materialdesign.BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            PODData.Error(Throwable(API_ERROR))
        } else {
            retrofitImpl.getPictureOfTheDay(apiKey, date, PODCallback)
        }
    }

    fun getMarsPicture() {
        liveDataToObserve.postValue(PODData.Loading)
        val earthDate = getDayBeforeYesterday()
        retrofitImpl.getMarsPictureByDate(earthDate,com.example.materialdesign.BuildConfig.NASA_API_KEY, marsCallback)
    }

    fun getDayBeforeYesterday(): String {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val yesterday = LocalDateTime.now().minusDays(2)
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            return yesterday.format(formatter)
        } else {
            val cal: Calendar = Calendar.getInstance()
            val s = SimpleDateFormat("yyyy-MM-dd")
            cal.add(Calendar.DAY_OF_YEAR, -2)
            return s.format(cal.time)
        }
    }

    private fun getDate(day: Int): String {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val yesterday = LocalDateTime.now().minusDays(day.toLong())
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            return yesterday.format(formatter)
        } else {
            val cal: Calendar = Calendar.getInstance()
            val s = SimpleDateFormat("yyyy-MM-dd")
            cal.add(Calendar.DAY_OF_YEAR, (-day))
            return s.format(cal.time)
        }
    }

    private val PODCallback = object : Callback<PODServerResponseData> {

        override fun onResponse(
            call: Call<PODServerResponseData>,
            response: Response<PODServerResponseData>,
        ) {
            if (response.isSuccessful && response.body() != null) {
                liveDataToObserve.postValue(PODData.SuccessPOD(response.body()!!))
            } else {
                val message = response.message()
                if (message.isNullOrEmpty()) {
                    liveDataToObserve.postValue(PODData.Error(Throwable(UNKNOWN_ERROR)))
                } else {
                    liveDataToObserve.postValue(PODData.Error(Throwable(message)))
                }
            }
        }

        override fun onFailure(call: Call<PODServerResponseData>, t: Throwable) {
            liveDataToObserve.postValue(PODData.Error(t))
        }
    }

    val marsCallback = object : Callback<MarsPhotosServerResponseData> {

        override fun onResponse(
            call: Call<MarsPhotosServerResponseData>,
            response: Response<MarsPhotosServerResponseData>,
        ) {
            if (response.isSuccessful && response.body() != null) {
                liveDataToObserve.postValue(PODData.SuccessMars(response.body()!!))
            } else {
                val message = response.message()
                if (message.isNullOrEmpty()) {
                    liveDataToObserve.postValue(PODData.Error(Throwable(UNKNOWN_ERROR)))
                } else {
                    liveDataToObserve.postValue(PODData.Error(Throwable(message)))
                }
            }
        }

        override fun onFailure(call: Call<MarsPhotosServerResponseData>, t: Throwable) {
            liveDataToObserve.postValue(PODData.Error(t))
        }
    }

    companion object {
        private const val API_ERROR = "You need API Key"
        private const val UNKNOWN_ERROR = "Unidentified error"
    }
}