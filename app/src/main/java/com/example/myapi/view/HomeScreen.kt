package com.example.myapi.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapi.R
import com.example.myapi.modeldata.DataSiswa
import com.example.myapi.uicontroller.route.DestinasiHome
import com.example.myapi.viewmodel.HomeViewModel
import com.example.myapi.viewmodel.provider.PenyediaViewModel
import com.example.myapi.viewmodel.StatusUiSiswa


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToItemEntry: () -> Unit,
    navigateToItemUpdate: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            SiswaTopAppBar(
                title = stringResource(DestinasiHome.titleRes),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    // FIX: Unresolved reference 'Icons' diatasi dengan import material-icons-filled
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.entry_siswa)
                )
            }
        }
    ) { innerPadding ->
        HomeBody(
            statusUiSiswa = viewModel.listSiswa,
            onSiswaClick = navigateToItemUpdate as (Int) -> Unit,
            retryAction = { viewModel.loadSiswa() },
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun HomeBody(
    statusUiSiswa: StatusUiSiswa,
    onSiswaClick: (Int) -> Unit,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        when (statusUiSiswa) {
            is StatusUiSiswa.Loading -> LoadingScreen()
            is StatusUiSiswa.Success -> {
                if (statusUiSiswa.siswa.isEmpty()) {
                    Text(text = "Tidak ada data siswa")
                } else {
                    DaftarSiswa(
                        itemSiswa = statusUiSiswa.siswa,
                        // FIX: Argument type mismatch diatasi dengan memastikan it.id adalah String
                        onSiswaClick = { onSiswaClick(it.id) }
                    )
                }
            }
            is StatusUiSiswa.Error -> ErrorScreen(
                retryAction = retryAction,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Loading...")
    }
}

@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(modifier = modifier, verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Gagal memuat data")
        Button(onClick = retryAction) { Text("Coba Lagi") }
    }
}

@Composable
fun DaftarSiswa(
    itemSiswa: List<DataSiswa>,
    onSiswaClick: (DataSiswa) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(itemSiswa) { siswa ->
            ItemSiswa(
                siswa = siswa,
                modifier = Modifier.clickable { onSiswaClick(siswa) }
            )
        }
    }
}

@Composable
fun ItemSiswa(
    siswa: DataSiswa,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = siswa.nama,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = siswa.telpon,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = siswa.alamat,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}