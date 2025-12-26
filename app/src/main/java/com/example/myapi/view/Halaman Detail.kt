package com.example.myapi.view

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ModifierInfo
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NoOpNavigator
import com.example.myapi.modeldata.DetailSiswa
import com.example.myapi.viewmodel.DetailViewModel
import com.example.myapi.viewmodel.provider.PenyediaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailSiswaScreen(
    navigateToEditItem: (Int) -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel= viewModel ( factory = PenyediaViewModel.Factory )
    ){

}