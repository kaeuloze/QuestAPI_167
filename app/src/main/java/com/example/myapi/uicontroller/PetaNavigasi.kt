package com.example.myapi.uicontroller

import android.R.attr.type
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapi.uicontroller.route.DestinasiDetail
import com.example.myapi.uicontroller.route.DestinasiEdit
import com.example.myapi.uicontroller.route.DestinasiEntry
import com.example.myapi.uicontroller.route.DestinasiHome
import com.example.myapi.view.DetailSiswaScreen
import com.example.myapi.view.EditSiswaScreen
import com.example.myapi.view.EntrySiswaScreen
import com.example.myapi.view.HomeScreen

@Composable
fun DataSiswaApp(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    HostNavigasi(navController = navController, modifier = modifier)
}

@Composable
fun HostNavigasi(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHome.route,
        modifier = modifier
    ) {
        // Rute untuk Halaman Utama
        composable(DestinasiHome.route) {
            HomeScreen(
                navigateToItemEntry = {
                    navController.navigate(DestinasiEntry.route)
                },
                // Perbaikan Error: Nama parameter harus 'navigateToItemUpdate' sesuai HomeScreen.kt
                // Dan tambahkan tanda koma setelah navigateToItemEntry
                navigateToItemUpdate = {
                     navController.navigate("${DestinasiDetail.route}/$it")
                }
            )
        }

        // Rute untuk Halaman Entry Siswa
        composable(DestinasiEntry.route) {
            EntrySiswaScreen(navigateBack = { navController.navigate(DestinasiHome.route) },
                onNavigateUp = {
                    navController.popBackStack()
                })
        }

        composable(DestinasiDetail.routeWithArgs, arguments = listOf(navArgument(DestinasiDetail.itemIdArg){
            type = NavType.IntType})
        ){
            DetailSiswaScreen(navigateToEditItem = {navController.navigate("${DestinasiEdit.route}/$it")},
                navigateBack = { navController.navigate(DestinasiHome.route)})
        }

        composable(DestinasiEdit.routeWithArgs, arguments = listOf(navArgument(DestinasiEdit.itemIdArg){
            type= NavType.IntType })){
            EditSiswaScreen(navigateBack= {navController.navigate(DestinasiHome.route)},
                onNavigateUp = {
                    navController.navigateUp()
                }
            )
        }

    }
}