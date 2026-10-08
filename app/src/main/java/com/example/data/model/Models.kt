package com.example.data.model

/**
 * Service category representing home repair and installation domains.
 */
data class ServiceCategory(
    val id: String,
    val name: String,
    val hindiName: String,
    val iconName: String,
    val tagLine: String,
    val startingPrice: Int,
    val badge: String? = null,
    val accentColor: Long = 0xFF0D6EFD,
    val totalServices: Int = 4,
    val rating: Float = 4.8f,
    val reviewsCountString: String = "2.5k reviews",
    val includes: List<String> = emptyList()
)

/**
 * Individual service item inside a category.
 */
data class ServiceItem(
    val id: String,
    val categoryId: String,
    val title: String,
    val hindiTitle: String,
    val description: String,
    val price: Int,
    val originalPrice: Int,
    val durationMinutes: Int,
    val rating: Float = 4.8f,
    val reviewsCount: Int = 245,
    val warrantyDays: Int = 30,
    val includedPoints: List<String> = emptyList(),
    val excludedPoints: List<String> = emptyList()
)

/**
 * Cart item representing selected service and quantity.
 */
data class CartItem(
    val service: ServiceItem,
    val quantity: Int
)

/**
 * Service Technician / Provider Profile.
 */
data class ServiceProvider(
    val id: String,
    val name: String,
    val phone: String,
    val rating: Float,
    val reviewsCount: Int,
    val jobsCompleted: Int,
    val experienceYears: Int,
    val locality: String,
    val specialization: String,
    val verifiedBadge: String = "Verified",
    val about: String,
    val serviceTags: List<String> = listOf("Wiring", "Switch Board", "Fan Installation")
)

/**
 * Customer Address.
 */
data class Address(
    val id: String,
    val label: String, // "Home", "Office", "Other"
    val houseNo: String,
    val street: String,
    val landmark: String,
    val locality: String,
    val city: String,
    val pincode: String,
    val isDefault: Boolean = false
) {
    fun fullAddress(): String = "$houseNo, $street, $locality, $city $pincode"
}

/**
 * Booking Lifecycle Status.
 */
enum class BookingStatus(val displayName: String, val stepIndex: Int) {
    CONFIRMED("Confirmed", 0),
    EXPERT_ASSIGNED("Expert Assigned", 1),
    ON_THE_WAY("On The Way", 2),
    REACHED_LOCATION("Reached Location", 3),
    IN_PROGRESS("In Progress", 4),
    COMPLETED("Completed", 5),
    CANCELLED("Cancelled", -1)
}

/**
 * Full Booking Order.
 */
data class BookingOrder(
    val id: String, // e.g. "HF123456J"
    val categoryId: String,
    val categoryName: String,
    val items: List<CartItem>,
    val scheduledDate: String,
    val scheduledTimeSlot: String,
    val isExpress: Boolean = false,
    val specialNotes: String = "",
    val address: Address,
    val provider: ServiceProvider,
    val status: BookingStatus = BookingStatus.CONFIRMED,
    val paymentMethod: String,
    val paymentStatus: String = "Paid",
    val serviceCharges: Int,
    val platformFee: Int = 30,
    val totalAmount: Int,
    val createdAt: Long = System.currentTimeMillis(),
    val etaMinutes: Int = 20,
    val startServiceOtp: String = "4819",
    val customerRating: Float? = null,
    val customerFeedback: String? = null
)

/**
 * Wallet Transaction Record.
 */
data class WalletTransaction(
    val id: String,
    val title: String,
    val date: String,
    val amount: Int,
    val isCredit: Boolean
)

/**
 * App Language options.
 */
enum class AppLanguage(val code: String, val displayName: String, val nativeName: String, val flag: String) {
    ENGLISH("en", "English", "English", "🇬🇧"),
    HINDI("hi", "Hindi", "हिंदी", "🇮🇳")
}

/**
 * User Profile State.
 */
data class UserProfile(
    val isLoggedIn: Boolean = true,
    val name: String = "Aman Verma",
    val phone: String = "9876543210",
    val email: String = "aman.verma@example.com",
    val walletBalance: Int = 1250,
    val selectedCity: String = "Bhopal",
    val language: AppLanguage = AppLanguage.ENGLISH
)
