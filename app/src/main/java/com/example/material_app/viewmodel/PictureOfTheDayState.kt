package com.example.material_app.viewmodel

import com.example.material_app.repository.MarsPhotosServerResponseData
import com.example.material_app.repository.PictureOfTheDayResponseData

sealed class PictureOfTheDayState {
    data class Success(val pictureOfTheDayResponseData: PictureOfTheDayResponseData):PictureOfTheDayState()
    data class SuccessMars(val serverResponseData: MarsPhotosServerResponseData) : PictureOfTheDayState()
    data class Loading(val progress: Int?):PictureOfTheDayState()
    data class Error(val error: Throwable):PictureOfTheDayState()
}