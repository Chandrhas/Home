package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.screens.BookingConfirmedScreen
import com.example.ui.screens.BookingPageScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.LanguageScreen
import com.example.ui.screens.LoginScreen
import com.example.ui.screens.MyBookingsScreen
import com.example.ui.screens.OtpVerifyScreen
import com.example.ui.screens.ProviderProfileScreen
import com.example.ui.screens.ReviewAndPayScreen
import com.example.ui.screens.SelectAddressScreen
import com.example.ui.screens.ServiceDetailsScreen
import com.example.ui.screens.ServiceListScreen
import com.example.ui.screens.SettingsScreen
import com.example.ui.screens.SplashScreen
import com.example.ui.screens.WalletScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.GharSevaViewModel
import com.example.ui.viewmodel.Screen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                HomeFixApp()
            }
        }
    }
}

@Composable
fun HomeFixApp(
    viewModel: GharSevaViewModel = viewModel()
) {
    val currentScreen by viewModel.currentScreen.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        when (val screen = currentScreen) {
            is Screen.Splash -> {
                SplashScreen(
                    viewModel = viewModel,
                    modifier = Modifier.padding(innerPadding)
                )
            }
            is Screen.Login -> {
                LoginScreen(
                    viewModel = viewModel,
                    modifier = Modifier.padding(innerPadding)
                )
            }
            is Screen.OtpVerification -> {
                OtpVerifyScreen(
                    phone = screen.phone,
                    viewModel = viewModel,
                    modifier = Modifier.padding(innerPadding)
                )
            }
            is Screen.Home -> {
                HomeScreen(
                    viewModel = viewModel,
                    modifier = Modifier.padding(innerPadding)
                )
            }
            is Screen.ServiceList -> {
                ServiceListScreen(
                    viewModel = viewModel,
                    modifier = Modifier.padding(innerPadding)
                )
            }
            is Screen.ServiceDetails -> {
                ServiceDetailsScreen(
                    categoryId = screen.categoryId,
                    viewModel = viewModel,
                    modifier = Modifier.padding(innerPadding)
                )
            }
            is Screen.BookService -> {
                BookingPageScreen(
                    categoryId = screen.categoryId,
                    viewModel = viewModel,
                    modifier = Modifier.padding(innerPadding)
                )
            }
            is Screen.SelectAddress -> {
                SelectAddressScreen(
                    viewModel = viewModel,
                    modifier = Modifier.padding(innerPadding)
                )
            }
            is Screen.ReviewAndPay -> {
                ReviewAndPayScreen(
                    viewModel = viewModel,
                    modifier = Modifier.padding(innerPadding)
                )
            }
            is Screen.BookingConfirmed -> {
                BookingConfirmedScreen(
                    orderId = screen.orderId,
                    viewModel = viewModel,
                    modifier = Modifier.padding(innerPadding)
                )
            }
            is Screen.MyBookings -> {
                MyBookingsScreen(
                    viewModel = viewModel,
                    modifier = Modifier.padding(innerPadding)
                )
            }
            is Screen.ProviderProfile -> {
                ProviderProfileScreen(
                    provider = screen.provider,
                    viewModel = viewModel,
                    modifier = Modifier.padding(innerPadding)
                )
            }
            is Screen.Wallet -> {
                WalletScreen(
                    viewModel = viewModel,
                    modifier = Modifier.padding(innerPadding)
                )
            }
            is Screen.BookingHistory -> {
                MyBookingsScreen(
                    viewModel = viewModel,
                    modifier = Modifier.padding(innerPadding)
                )
            }
            is Screen.Language -> {
                LanguageScreen(
                    viewModel = viewModel,
                    modifier = Modifier.padding(innerPadding)
                )
            }
            is Screen.Settings -> {
                SettingsScreen(
                    viewModel = viewModel,
                    modifier = Modifier.padding(innerPadding)
                )
            }
            is Screen.EditProfile -> {
                SettingsScreen(
                    viewModel = viewModel,
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}
