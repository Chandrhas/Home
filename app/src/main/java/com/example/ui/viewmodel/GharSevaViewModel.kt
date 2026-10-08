package com.example.ui.viewmodel

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.Address
import com.example.data.model.AppLanguage
import com.example.data.model.BookingOrder
import com.example.data.model.BookingStatus
import com.example.data.model.ServiceCategory
import com.example.data.model.ServiceProvider
import com.example.data.model.UserProfile
import com.example.data.model.WalletTransaction
import com.example.data.repository.GharSevaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

sealed class Screen {
    data object Splash : Screen()
    data object Login : Screen()
    data class OtpVerification(val phone: String) : Screen()
    data object Home : Screen()
    data object ServiceList : Screen()
    data class ServiceDetails(val categoryId: String) : Screen()
    data class BookService(val categoryId: String) : Screen()
    data object SelectAddress : Screen()
    data object ReviewAndPay : Screen()
    data class BookingConfirmed(val orderId: String) : Screen()
    data object MyBookings : Screen()
    data class ProviderProfile(val provider: ServiceProvider) : Screen()
    data object Wallet : Screen()
    data object BookingHistory : Screen()
    data object Language : Screen()
    data object Settings : Screen()
    data object EditProfile : Screen()
}

class GharSevaViewModel(
    val repository: GharSevaRepository = GharSevaRepository()
) : ViewModel() {

    // --- Navigation Backstack ---
    private val _screenStack = MutableStateFlow<List<Screen>>(listOf(Screen.Home))
    val currentScreen: StateFlow<Screen> = _screenStack
        .combine(MutableStateFlow(Unit)) { stack, _ -> stack.lastOrNull() ?: Screen.Home }
        .stateIn(viewModelScope, SharingStarted.Eagerly, Screen.Home)

    fun navigateTo(screen: Screen) {
        _screenStack.update { it + screen }
    }

    fun navigateBack(): Boolean {
        if (_screenStack.value.size > 1) {
            _screenStack.update { it.dropLast(1) }
            return true
        }
        return false
    }

    fun navigateToHomeClearStack() {
        _screenStack.value = listOf(Screen.Home)
    }

    // --- Active Bottom Navigation Tab ---
    // 0: Home, 1: Bookings, 2: Wallet, 3: Profile/Settings
    private val _selectedTab = MutableStateFlow(0)
    val selectedTab: StateFlow<Int> = _selectedTab.asStateFlow()

    fun selectTab(index: Int) {
        _selectedTab.value = index
        when (index) {
            0 -> navigateToHomeClearStack()
            1 -> navigateTo(Screen.MyBookings)
            2 -> navigateTo(Screen.Wallet)
            3 -> navigateTo(Screen.Settings)
        }
    }

    // --- Data Streams from Repository ---
    val categories: List<ServiceCategory> = repository.categories
    val userProfile: StateFlow<UserProfile> = repository.userProfile
    val addresses: StateFlow<List<Address>> = repository.addresses
    val bookings: StateFlow<List<BookingOrder>> = repository.bookings
    val walletTransactions: StateFlow<List<WalletTransaction>> = repository.walletTransactions

    // --- Active Category for Details & Booking ---
    private val _activeCategory = MutableStateFlow(categories.first())
    val activeCategory: StateFlow<ServiceCategory> = _activeCategory.asStateFlow()

    fun selectCategoryForDetails(catId: String) {
        val cat = categories.find { it.id == catId } ?: categories.first()
        _activeCategory.value = cat
        navigateTo(Screen.ServiceDetails(cat.id))
    }

    // --- Booking Checkout Wizard State ---
    private val _bookingDate = MutableStateFlow("Today 12 Apr")
    val bookingDate: StateFlow<String> = _bookingDate.asStateFlow()

    fun setBookingDate(date: String) {
        _bookingDate.value = date
    }

    private val _bookingTime = MutableStateFlow("11:00 AM")
    val bookingTime: StateFlow<String> = _bookingTime.asStateFlow()

    fun setBookingTime(time: String) {
        _bookingTime.value = time
    }

    private val _bookingSpecialRequest = MutableStateFlow("")
    val bookingSpecialRequest: StateFlow<String> = _bookingSpecialRequest.asStateFlow()

    fun setBookingSpecialRequest(request: String) {
        _bookingSpecialRequest.value = request
    }

    private val _selectedAddressId = MutableStateFlow("addr_1")
    val selectedAddressId: StateFlow<String> = _selectedAddressId.asStateFlow()

    fun selectAddress(id: String) {
        _selectedAddressId.value = id
    }

    private val _paymentMethod = MutableStateFlow("UPI / Google Pay / PhonePe")
    val paymentMethod: StateFlow<String> = _paymentMethod.asStateFlow()

    fun setPaymentMethod(method: String) {
        _paymentMethod.value = method
    }

    // --- Search Query ---
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun searchServices(query: String): List<ServiceCategory> {
        if (query.isBlank()) return categories
        val q = query.trim().lowercase()
        return categories.filter {
            it.name.lowercase().contains(q) ||
                    it.hindiName.lowercase().contains(q) ||
                    it.tagLine.lowercase().contains(q)
        }
    }

    // --- Confirm Booking Execution ---
    fun confirmBooking(): BookingOrder {
        val cat = _activeCategory.value
        val addr = addresses.value.find { it.id == _selectedAddressId.value }
            ?: addresses.value.first()

        val order = repository.createBooking(
            category = cat,
            scheduledDate = _bookingDate.value,
            scheduledTimeSlot = _bookingTime.value,
            specialNotes = _bookingSpecialRequest.value,
            address = addr,
            paymentMethod = _paymentMethod.value
        )

        navigateTo(Screen.BookingConfirmed(order.id))
        return order
    }

    fun cancelBooking(orderId: String) {
        repository.cancelBooking(orderId)
    }

    // --- Wallet ---
    fun addMoney(amount: Int) {
        repository.addMoneyToWallet(amount)
    }

    // --- Address ---
    fun addNewAddress(label: String, houseNo: String, street: String, locality: String, city: String, pincode: String) {
        val newId = "addr_${System.currentTimeMillis()}"
        val newAddr = Address(
            id = newId,
            label = label,
            houseNo = houseNo,
            street = street,
            landmark = "",
            locality = locality,
            city = city,
            pincode = pincode,
            isDefault = false
        )
        repository.addAddress(newAddr)
        _selectedAddressId.value = newId
    }

    // --- Language ---
    fun updateLanguage(lang: AppLanguage) {
        repository.updateLanguage(lang)
    }

    // --- Profile & City ---
    fun updateProfile(name: String, phone: String, email: String) {
        repository.updateProfile(name, phone, email)
    }

    fun updateCity(city: String) {
        repository.updateCity(city)
    }

    // --- Intents ---
    fun callProvider(context: Context, phone: String) {
        try {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:${phone.replace(" ", "")}")
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "Could not open phone dialer", Toast.LENGTH_SHORT).show()
        }
    }

    fun chatWhatsApp(context: Context, provider: ServiceProvider, serviceName: String) {
        val message = "Hello ${provider.name}, I am contacting you regarding $serviceName booking via HomeFix."
        val cleanPhone = provider.phone.replace("+", "").replace(" ", "").replace("-", "")
        val url = "https://api.whatsapp.com/send?phone=$cleanPhone&text=${Uri.encode(message)}"

        try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(url)
                setPackage("com.whatsapp")
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            try {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                context.startActivity(browserIntent)
            } catch (e2: Exception) {
                Toast.makeText(context, "WhatsApp message: $message", Toast.LENGTH_LONG).show()
            }
        }
    }
}
