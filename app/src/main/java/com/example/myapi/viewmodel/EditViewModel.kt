package com.example.myapi.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapi.modeldata.DetailSiswa
import com.example.myapi.modeldata.UIStateSiswa
import com.example.myapi.modeldata.toDataSiswa
import com.example.myapi.repositori.RepositoryDataSiswa
import com.example.myapi.uicontroller.route.DestinasiDetail
import kotlinx.coroutines.launch
import retrofit2.Response
import com.example.myapi.R
import com.example.myapi.modeldata.toUIStateSiswa

class EditViewModel(savedStateHandle: SavedStateHandle,  private val repositoryDataSiswa: RepositoryDataSiswa): ViewModel(){
    var uiStateSiswa by mutableStateOf(UIStateSiswa())
    private set

    private val idSiswa: Int =  checkNotNull(savedStateHandle[DestinasiDetail.itemIdArg])
    init {
        viewModelScope.launch {
            uiStateSiswa = repositoryDataSiswa.getSatuSiswa(idSiswa)
                .toUIStateSiswa(true)
        }
    }
    fun updateUiState(detailSiswa: DetailSiswa){
        uiStateSiswa =
            UIStateSiswa(detailSiswa = detailSiswa, isEntryValid = validasiInput(detailSiswa))
    }
    private fun validasiInput(uiState: DetailSiswa = uiStateSiswa.detailSiswa ):Boolean{
        return with(uiState){
            nama.isNotBlank() && alamat.isNotBlank() && telpon.isNotBlank()
        }
    }
    suspend fun editSatuSiswa(){
        if (validasiInput(uiStateSiswa.detailSiswa)) {
            val call: Response<Void> =
                repositoryDataSiswa.editSatuSiswa(
                    idSiswa,uiStateSiswa.detailSiswa.toDataSiswa()
                )

            if (call.isSuccessful) {
                println("Sukses Tambah Data : ${call.message()}")
            } else {
                println("Gagal tambah data : ${call.errorBody()}")
            }
        }
    }

}