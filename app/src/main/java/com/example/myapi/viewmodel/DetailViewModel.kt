package com.example.myapi.viewmodel

import com.example.myapi.modeldata.DataSiswa

sealed interface StatusUIDetail{
    data class Success(val satusiswa: DataSiswa) : StatusUIDetail
    object Error : StatusUIDetail
    object Loading : StatusUIDetail
}
