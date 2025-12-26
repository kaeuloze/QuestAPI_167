package com.example.myapi.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapi.modeldata.UIStateSiswa
import com.example.myapi.repositori.RepositoryDataSiswa
import com.example.myapi.uicontroller.route.DestinasiDetail
import kotlinx.coroutines.launch

class EditViewModel(savedStateHandle: SavedStateHandle,  private val repositoryDataSiswa: RepositoryDataSiswa): ViewModel(){
    var uiStatusSiswa by mutableStateOf(UIStateSiswa())
    private set


}