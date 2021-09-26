package com.example.materialdesign.viewmodel

import com.example.materialdesign.repository.PODServerResponseData

sealed class PODData{
    data class Success(val serverResponseData: PODServerResponseData) : PODData()
    data class SuccessMars(val serverResponseData: MarsPhotosServerResponseData) : PODData()
    data class Error(val error: Throwable) : PODData()
    data class Loading(val progress: Int?) : PODData()
}
