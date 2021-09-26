package com.example.materialdesign.viewmodel


import com.example.materialdesign.repository.dto.MarsPhotosServerResponseData
import com.example.materialdesign.repository.dto.PODServerResponseData

sealed class PODData{
    data class SuccessPOD(val serverResponseData: PODServerResponseData) : PODData()
    data class SuccessMars(val serverResponseData: MarsPhotosServerResponseData) : PODData()
    data class Error(val error: Throwable) : PODData()
    object Loading : PODData()
}
