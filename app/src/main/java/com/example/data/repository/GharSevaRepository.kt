package com.example.data.repository

import com.example.data.model.Address
import com.example.data.model.AppLanguage
import com.example.data.model.BookingOrder
import com.example.data.model.BookingStatus
import com.example.data.model.CartItem
import com.example.data.model.ServiceCategory
import com.example.data.model.ServiceItem
import com.example.data.model.ServiceProvider
import com.example.data.model.UserProfile
import com.example.data.model.WalletTransaction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.random.Random

class GharSevaRepository {

    // --- User Profile ---
    private val _userProfile = MutableStateFlow(
        UserProfile(
            isLoggedIn = true,
            name = "Aman Verma",
            phone = "9876543210",
            email = "aman.verma@example.com",
            walletBalance = 1250,
            selectedCity = "Bhopal",
            language = AppLanguage.ENGLISH
        )
    )
    val userProfile: StateFlow<UserProfile> = _userProfile.asStateFlow()

    // --- Saved Addresses ---
    private val _addresses = MutableStateFlow(
        listOf(
            Address(
                id = "addr_1",
                label = "Home",
                houseNo = "123",
                street = "Green Park",
                landmark = "Near City Center",
                locality = "Arera Hills",
                city = "Bhopal, Madhya Pradesh",
                pincode = "462016",
                isDefault = true
            ),
            Address(
                id = "addr_2",
                label = "Office",
                houseNo = "Plot 45",
                street = "Commercial Complex, MP Nagar Zone 2",
                landmark = "Opposite DB City Mall",
                locality = "MP Nagar",
                city = "Bhopal, Madhya Pradesh",
                pincode = "462011",
                isDefault = false
            )
        )
    )
    val addresses: StateFlow<List<Address>> = _addresses.asStateFlow()

    // --- Wallet Transactions ---
    private val _walletTransactions = MutableStateFlow(
        listOf(
            WalletTransaction(
                id = "tx_1",
                title = "Payment to Rohit Sharma",
                date = "12 Apr 2025",
                amount = 329,
                isCredit = false
            ),
            WalletTransaction(
                id = "tx_2",
                title = "Wallet Added",
                date = "10 Apr 2025",
                amount = 1000,
                isCredit = true
            ),
            WalletTransaction(
                id = "tx_3",
                title = "Payment to Sunita Devi",
                date = "8 Apr 2025",
                amount = 450,
                isCredit = false
            )
        )
    )
    val walletTransactions: StateFlow<List<WalletTransaction>> = _walletTransactions.asStateFlow()

    // --- Service Categories (16 Services from exact HomeFix design) ---
    val categories: List<ServiceCategory> = listOf(
        ServiceCategory(
            id = "electrician",
            name = "Electrician",
            hindiName = "बिजली मिस्त्री",
            iconName = "Bolt",
            tagLine = "Wiring, Switches, Lighting",
            startingPrice = 299,
            accentColor = 0xFFF59E0B, // Yellow
            rating = 4.8f,
            reviewsCountString = "2.5k reviews",
            includes = listOf(
                "Home wiring & rewiring",
                "Switch board installation",
                "Fan, light, and appliance setup",
                "Electrical fault finding"
            )
        ),
        ServiceCategory(
            id = "plumber",
            name = "Plumber",
            hindiName = "प्लंबर",
            iconName = "Plumbing",
            tagLine = "Leakage, Pipe Fitting",
            startingPrice = 249,
            accentColor = 0xFF0D6EFD, // Royal Blue
            rating = 4.8f,
            reviewsCountString = "1.8k reviews",
            includes = listOf(
                "Tap leakage repair & replacement",
                "Pipe fitting & water blockages",
                "Wash basin & sink installation",
                "Flush tank & water motor servicing"
            )
        ),
        ServiceCategory(
            id = "carpenter",
            name = "Carpenter",
            hindiName = "बढ़ई",
            iconName = "Build",
            tagLine = "Furniture, Wood Work",
            startingPrice = 299,
            accentColor = 0xFFEAB308, // Golden
            rating = 4.7f,
            reviewsCountString = "1.2k reviews",
            includes = listOf(
                "Door lock repair & installation",
                "Bed & modular furniture assembly",
                "Wardrobe hinges & drawer channels",
                "Wooden shelf & curtain rod mounting"
            )
        ),
        ServiceCategory(
            id = "painter",
            name = "Painter",
            hindiName = "पेंटर",
            iconName = "FormatPaint",
            tagLine = "Interior & Exterior",
            startingPrice = 799,
            accentColor = 0xFFEF4444, // Red
            rating = 4.9f,
            reviewsCountString = "980 reviews",
            includes = listOf(
                "Complete room painting (2 coats)",
                "Wall putty, sanding & primer coat",
                "Waterproofing & dampness treatment",
                "Grill & metal gate enamel painting"
            )
        ),
        ServiceCategory(
            id = "ac_repair",
            name = "AC Repair",
            hindiName = "AC रिपेयर",
            iconName = "AcUnit",
            tagLine = "AC Service & Gas Filling",
            startingPrice = 399,
            accentColor = 0xFF06B6D4, // Cyan
            rating = 4.9f,
            reviewsCountString = "3.1k reviews",
            includes = listOf(
                "High pressure jet foam cleaning",
                "Complete refrigerant gas refill",
                "Split & window AC installation",
                "Indoor unit water dripping solution"
            )
        ),
        ServiceCategory(
            id = "ro_service",
            name = "RO Service",
            hindiName = "RO सर्विस",
            iconName = "WaterDrop",
            tagLine = "Water Purifier Service",
            startingPrice = 249,
            accentColor = 0xFF0D9488, // Teal
            rating = 4.8f,
            reviewsCountString = "1.5k reviews",
            includes = listOf(
                "Sediment, carbon & membrane replacement",
                "TDS level testing & calibration",
                "Continuous leakage & O-ring repair",
                "Booster pump & adapter diagnostics"
            )
        ),
        ServiceCategory(
            id = "refrigerator",
            name = "Refrigerator Repair",
            hindiName = "फ्रिज रिपेयर",
            iconName = "Kitchen",
            tagLine = "Fridge Repair & Maintenance",
            startingPrice = 299,
            accentColor = 0xFF2563EB, // Blue
            rating = 4.7f,
            reviewsCountString = "890 reviews",
            includes = listOf(
                "Cooling issue & gas charging",
                "Thermostat & defrost timer repair",
                "Compressor relay replacement",
                "Door magnetic rubber gasket renewal"
            )
        ),
        ServiceCategory(
            id = "washing_machine",
            name = "Washing Machine",
            hindiName = "वॉशिंग मशीन",
            iconName = "LocalLaundryService",
            tagLine = "Washing Machine Repair",
            startingPrice = 299,
            accentColor = 0xFF6366F1, // Indigo
            rating = 4.8f,
            reviewsCountString = "1.1k reviews",
            includes = listOf(
                "Front/Top load motor vibration fix",
                "Drain pump blockage removal",
                "Drum spin & bearing troubleshooting",
                "Deep tub descaling & sanitize cycle"
            )
        ),
        ServiceCategory(
            id = "tv_repair",
            name = "TV Repair",
            hindiName = "टीवी रिपेयर",
            iconName = "Tv",
            tagLine = "Screen & Wall Mounting",
            startingPrice = 249,
            accentColor = 0xFF8B5CF6,
            rating = 4.8f,
            reviewsCountString = "750 reviews",
            includes = listOf(
                "Smart LED wall mount bracket installation",
                "Backlight & display board diagnostics",
                "Sound & speaker replacement",
                "HDMI & motherboard repair"
            )
        ),
        ServiceCategory(
            id = "cctv",
            name = "CCTV",
            hindiName = "सीसीटीवी",
            iconName = "Videocam",
            tagLine = "Security Camera Setup",
            startingPrice = 399,
            accentColor = 0xFF0284C7,
            rating = 4.9f,
            reviewsCountString = "640 reviews",
            includes = listOf(
                "Bullet & dome camera installation",
                "DVR/NVR wiring & setup",
                "Mobile app remote live stream",
                "Wi-Fi 360-degree camera pairing"
            )
        ),
        ServiceCategory(
            id = "inverter_service",
            name = "Inverter Service",
            hindiName = "इन्वर्टर सर्विस",
            iconName = "BatteryChargingFull",
            tagLine = "Battery & Inverter Wiring",
            startingPrice = 199,
            accentColor = 0xFF1E40AF,
            rating = 4.8f,
            reviewsCountString = "820 reviews",
            includes = listOf(
                "Battery distilled water top-up",
                "Terminal corrosion cleaning & lugs",
                "Backup dropping diagnostics",
                "New inverter trolley installation"
            )
        ),
        ServiceCategory(
            id = "home_cleaning",
            name = "Home Cleaning",
            hindiName = "घर की सफाई",
            iconName = "CleaningServices",
            tagLine = "Deep Sanitization & Wash",
            startingPrice = 499,
            accentColor = 0xFFD97706,
            rating = 4.9f,
            reviewsCountString = "2.2k reviews",
            includes = listOf(
                "Bathroom tile scrub & tap descaling",
                "Kitchen degreasing & exhaust clean",
                "Full flat mechanized deep clean",
                "Sofa shampoo & mite extraction"
            )
        ),
        ServiceCategory(
            id = "pest_control",
            name = "Pest Control",
            hindiName = "पेस्ट कंट्रोल",
            iconName = "BugReport",
            tagLine = "Cockroach & Termite Fix",
            startingPrice = 599,
            accentColor = 0xFF0EA5E9,
            rating = 4.8f,
            reviewsCountString = "910 reviews",
            includes = listOf(
                "Odorless herbal cockroach gel dots",
                "Anti-termite wood injection drilling",
                "Bed bug 2-visit eradication protocol",
                "Mosquito & insect fogging"
            )
        ),
        ServiceCategory(
            id = "water_tank",
            name = "Water Tank",
            hindiName = "पानी की टंकी",
            iconName = "Waves",
            tagLine = "Mechanized Scrub & UV",
            startingPrice = 499,
            accentColor = 0xFF0284C7,
            rating = 4.9f,
            reviewsCountString = "580 reviews",
            includes = listOf(
                "Sludge removal with trash pump",
                "High-pressure jet algae scrubbing",
                "UV light radiation disinfection",
                "Anti-bacterial safe water rinse"
            )
        ),
        ServiceCategory(
            id = "mason",
            name = "Mason",
            hindiName = "राजमिस्त्री",
            iconName = "Construction",
            tagLine = "Tiles, Plaster & Civil Work",
            startingPrice = 349,
            accentColor = 0xFFDC2626,
            rating = 4.7f,
            reviewsCountString = "430 reviews",
            includes = listOf(
                "Broken tile replacement & grouting",
                "Wall plaster patch & crack filling",
                "Balcony parapet & threshold slope",
                "Half-day skilled ustad + helper"
            )
        ),
        ServiceCategory(
            id = "gardener",
            name = "Gardener",
            hindiName = "माली",
            iconName = "Yard",
            tagLine = "Lawn & Plant Nourishment",
            startingPrice = 249,
            accentColor = 0xFF10B981,
            rating = 4.9f,
            reviewsCountString = "520 reviews",
            includes = listOf(
                "Lawn mowing & edge trimming",
                "Pot soil aeration (gudai) & manure",
                "Hedge pruning & branch shaping",
                "Organic neem oil pest spray"
            )
        )
    )

    // Providers
    val sampleProviders: List<ServiceProvider> = listOf(
        ServiceProvider(
            id = "prov_1",
            name = "Rohit Sharma",
            phone = "+91 98765 43210",
            rating = 4.8f,
            reviewsCount = 245,
            jobsCompleted = 520,
            experienceYears = 5,
            locality = "Bhopal",
            specialization = "Electrician",
            verifiedBadge = "Verified",
            about = "Professional electrician with 5 years of experience in home and office electrical work. Quality work with safety.",
            serviceTags = listOf("Wiring", "Switch Board", "Fan Installation")
        ),
        ServiceProvider(
            id = "prov_2",
            name = "Mukesh Yadav",
            phone = "+91 98260 12345",
            rating = 4.9f,
            reviewsCount = 310,
            jobsCompleted = 640,
            experienceYears = 8,
            locality = "Bhopal",
            specialization = "Plumber",
            verifiedBadge = "Verified",
            about = "Certified sanitary and pipe fitting specialist with 8 years of trusted work in Bhopal.",
            serviceTags = listOf("Pipe Fitting", "Tap Leakage", "Water Motor")
        ),
        ServiceProvider(
            id = "prov_3",
            name = "Sunita Devi",
            phone = "+91 97550 54321",
            rating = 4.95f,
            reviewsCount = 420,
            jobsCompleted = 780,
            experienceYears = 6,
            locality = "Bhopal",
            specialization = "Home Cleaning",
            verifiedBadge = "Verified",
            about = "Expert deep home and kitchen sanitization with professional cleaning equipment.",
            serviceTags = listOf("Deep Clean", "Kitchen Scrub", "Bathroom Sanitization")
        )
    )

    // Bookings Flow
    private val _bookings = MutableStateFlow<List<BookingOrder>>(emptyList())
    val bookings: StateFlow<List<BookingOrder>> = _bookings.asStateFlow()

    init {
        val defAddr = _addresses.value.first()
        val elecProvider = sampleProviders[0]
        val plumbProvider = sampleProviders[1]
        val cleanProvider = sampleProviders[2]

        // Seed 3 upcoming bookings matching the mockup in screen 11!
        val upcomingElec = BookingOrder(
            id = "HF123456J",
            categoryId = "electrician",
            categoryName = "Electrician",
            items = listOf(
                CartItem(
                    service = ServiceItem(
                        id = "elec_std",
                        categoryId = "electrician",
                        title = "Electrician Service",
                        hindiTitle = "बिजली मिस्त्री",
                        description = "Complete home electrical wiring, switchboard and diagnostics",
                        price = 299,
                        originalPrice = 399,
                        durationMinutes = 45
                    ),
                    quantity = 1
                )
            ),
            scheduledDate = "12 Apr 2025",
            scheduledTimeSlot = "11:00 AM",
            address = defAddr,
            provider = elecProvider,
            status = BookingStatus.CONFIRMED,
            paymentMethod = "UPI / Google Pay / PhonePe",
            serviceCharges = 299,
            platformFee = 30,
            totalAmount = 329
        )

        val upcomingPlumb = BookingOrder(
            id = "HF847192P",
            categoryId = "plumber",
            categoryName = "Plumber",
            items = listOf(
                CartItem(
                    service = ServiceItem(
                        id = "plumb_std",
                        categoryId = "plumber",
                        title = "Plumbing Leakage & Tap Fix",
                        hindiTitle = "नल लीकेज",
                        description = "Tap repair and pipe joint inspection",
                        price = 249,
                        originalPrice = 350,
                        durationMinutes = 40
                    ),
                    quantity = 1
                )
            ),
            scheduledDate = "15 Apr 2025",
            scheduledTimeSlot = "2:00 PM",
            address = defAddr,
            provider = plumbProvider,
            status = BookingStatus.CONFIRMED,
            paymentMethod = "Cash on Delivery",
            serviceCharges = 249,
            platformFee = 30,
            totalAmount = 279
        )

        val upcomingClean = BookingOrder(
            id = "HF938471C",
            categoryId = "home_cleaning",
            categoryName = "Home Cleaning",
            items = listOf(
                CartItem(
                    service = ServiceItem(
                        id = "clean_std",
                        categoryId = "home_cleaning",
                        title = "Bathroom & Kitchen Clean",
                        hindiTitle = "डीप क्लीन",
                        description = "Sanitization and stain removal",
                        price = 499,
                        originalPrice = 699,
                        durationMinutes = 60
                    ),
                    quantity = 1
                )
            ),
            scheduledDate = "18 Apr 2025",
            scheduledTimeSlot = "10:00 AM",
            address = defAddr,
            provider = cleanProvider,
            status = BookingStatus.CONFIRMED,
            paymentMethod = "UPI / Google Pay",
            serviceCharges = 499,
            platformFee = 30,
            totalAmount = 529
        )

        // Seed completed bookings matching screen 14
        val completed1 = BookingOrder(
            id = "HF110948A",
            categoryId = "ac_repair",
            categoryName = "AC Repair",
            items = listOf(
                CartItem(
                    service = ServiceItem(
                        id = "ac_std",
                        categoryId = "ac_repair",
                        title = "Power Jet AC Foam Service",
                        hindiTitle = "AC जेट सर्विस",
                        description = "Condenser & blower deep jet wash",
                        price = 399,
                        originalPrice = 599,
                        durationMinutes = 60
                    ),
                    quantity = 1
                )
            ),
            scheduledDate = "28 Mar 2025",
            scheduledTimeSlot = "12:00 PM",
            address = defAddr,
            provider = elecProvider,
            status = BookingStatus.COMPLETED,
            paymentMethod = "UPI / Google Pay",
            serviceCharges = 399,
            platformFee = 30,
            totalAmount = 429
        )

        _bookings.value = listOf(upcomingElec, upcomingPlumb, upcomingClean, completed1)
    }

    fun createBooking(
        category: ServiceCategory,
        scheduledDate: String,
        scheduledTimeSlot: String,
        specialNotes: String,
        address: Address,
        paymentMethod: String
    ): BookingOrder {
        val randomNum = Random.nextInt(100000, 999999)
        val newId = "HF${randomNum}J"
        val serviceCharge = category.startingPrice
        val platformFee = 30
        val total = serviceCharge + platformFee

        val provider = sampleProviders.find { it.specialization.equals(category.name, ignoreCase = true) }
            ?: sampleProviders.first()

        val order = BookingOrder(
            id = newId,
            categoryId = category.id,
            categoryName = category.name,
            items = listOf(
                CartItem(
                    service = ServiceItem(
                        id = "${category.id}_item",
                        categoryId = category.id,
                        title = "${category.name} Service",
                        hindiTitle = category.hindiName,
                        description = category.tagLine,
                        price = serviceCharge,
                        originalPrice = serviceCharge + 100,
                        durationMinutes = 45,
                        includedPoints = category.includes
                    ),
                    quantity = 1
                )
            ),
            scheduledDate = scheduledDate,
            scheduledTimeSlot = scheduledTimeSlot,
            specialNotes = specialNotes,
            address = address,
            provider = provider,
            status = BookingStatus.CONFIRMED,
            paymentMethod = paymentMethod,
            serviceCharges = serviceCharge,
            platformFee = platformFee,
            totalAmount = total
        )

        // If paid with wallet, deduct balance
        if (paymentMethod.contains("Wallet", ignoreCase = true)) {
            _userProfile.update { it.copy(walletBalance = (it.walletBalance - total).coerceAtLeast(0)) }
            _walletTransactions.update { current ->
                listOf(
                    WalletTransaction(
                        id = "tx_${System.currentTimeMillis()}",
                        title = "Payment to ${provider.name}",
                        date = scheduledDate,
                        amount = total,
                        isCredit = false
                    )
                ) + current
            }
        }

        _bookings.update { listOf(order) + it }
        return order
    }

    fun cancelBooking(orderId: String) {
        _bookings.update { current ->
            current.map {
                if (it.id == orderId) it.copy(status = BookingStatus.CANCELLED) else it
            }
        }
    }

    fun addMoneyToWallet(amount: Int) {
        _userProfile.update { it.copy(walletBalance = it.walletBalance + amount) }
        _walletTransactions.update { current ->
            listOf(
                WalletTransaction(
                    id = "tx_${System.currentTimeMillis()}",
                    title = "Wallet Added",
                    date = "Today",
                    amount = amount,
                    isCredit = true
                )
            ) + current
        }
    }

    fun addAddress(address: Address) {
        _addresses.update { it + address }
    }

    fun setDefaultAddress(addressId: String) {
        _addresses.update { list ->
            list.map { it.copy(isDefault = it.id == addressId) }
        }
    }

    fun updateProfile(name: String, phone: String, email: String) {
        _userProfile.update { it.copy(name = name, phone = phone, email = email, isLoggedIn = true) }
    }

    fun updateCity(city: String) {
        _userProfile.update { it.copy(selectedCity = city) }
    }

    fun updateLanguage(lang: AppLanguage) {
        _userProfile.update { it.copy(language = lang) }
    }
}
