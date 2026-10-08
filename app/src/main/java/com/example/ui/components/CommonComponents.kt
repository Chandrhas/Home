package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// --- HomeFix Brand Colors (From design image) ---
val HomeFixBlue = Color(0xFF0D6EFD)
val HomeFixBlueDark = Color(0xFF0B5ED7)
val HomeFixBlueLight = Color(0xFFE7F1FF)
val HomeFixNavy = Color(0xFF1E293B)
val HomeFixGreen = Color(0xFF198754)
val HomeFixYellow = Color(0xFFFFC107)
val HomeFixRed = Color(0xFFDC3545)
val HomeFixGray = Color(0xFF6C757D)
val HomeFixBorder = Color(0xFFE2E8F0)
val HomeFixSurface = Color(0xFFF8FAFC)

/**
 * HomeFix House + Wrench Icon & Branding Logo
 */
@Composable
fun HomeFixBrandLogo(
    modifier: Modifier = Modifier,
    size: Int = 72,
    showTagline: Boolean = true
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // House shape with embedded wrench icon
        Box(
            modifier = Modifier
                .size(size.dp)
                .clip(RoundedCornerShape((size / 4).dp))
                .background(HomeFixBlueLight),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Build,
                contentDescription = "HomeFix Logo",
                tint = HomeFixBlue,
                modifier = Modifier.size((size * 0.55).dp)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "HomeFix",
            fontWeight = FontWeight.ExtraBold,
            fontSize = 26.sp,
            color = HomeFixBlue,
            letterSpacing = (-0.5).sp
        )

        if (showTagline) {
            Text(
                text = "Your Home Our Service",
                fontSize = 13.sp,
                color = Color(0xFF475569),
                fontWeight = FontWeight.Medium
            )
        }
    }
}

/**
 * 4-Tab Bottom Navigation Bar from the design: Home, Bookings, Wallet, Profile
 */
@Composable
fun HomeFixBottomBar(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = Color.White,
        shadowElevation = 10.dp,
        tonalElevation = 6.dp
    ) {
        NavigationBar(
            containerColor = Color.White,
            tonalElevation = 0.dp,
            modifier = Modifier.height(68.dp)
        ) {
            NavigationBarItem(
                selected = selectedTab == 0,
                onClick = { onTabSelected(0) },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "Home",
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = { Text("Home", fontSize = 11.sp, fontWeight = if (selectedTab == 0) FontWeight.Bold else FontWeight.Normal) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = HomeFixBlue,
                    selectedTextColor = HomeFixBlue,
                    indicatorColor = HomeFixBlueLight,
                    unselectedIconColor = Color(0xFF94A3B8),
                    unselectedTextColor = Color(0xFF94A3B8)
                ),
                modifier = Modifier.testTag("nav_tab_home")
            )

            NavigationBarItem(
                selected = selectedTab == 1,
                onClick = { onTabSelected(1) },
                icon = {
                    Icon(
                        imageVector = Icons.Default.CalendarMonth,
                        contentDescription = "Bookings",
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = { Text("Bookings", fontSize = 11.sp, fontWeight = if (selectedTab == 1) FontWeight.Bold else FontWeight.Normal) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = HomeFixBlue,
                    selectedTextColor = HomeFixBlue,
                    indicatorColor = HomeFixBlueLight,
                    unselectedIconColor = Color(0xFF94A3B8),
                    unselectedTextColor = Color(0xFF94A3B8)
                ),
                modifier = Modifier.testTag("nav_tab_bookings")
            )

            NavigationBarItem(
                selected = selectedTab == 2,
                onClick = { onTabSelected(2) },
                icon = {
                    Icon(
                        imageVector = Icons.Default.AccountBalanceWallet,
                        contentDescription = "Wallet",
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = { Text("Wallet", fontSize = 11.sp, fontWeight = if (selectedTab == 2) FontWeight.Bold else FontWeight.Normal) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = HomeFixBlue,
                    selectedTextColor = HomeFixBlue,
                    indicatorColor = HomeFixBlueLight,
                    unselectedIconColor = Color(0xFF94A3B8),
                    unselectedTextColor = Color(0xFF94A3B8)
                ),
                modifier = Modifier.testTag("nav_tab_wallet")
            )

            NavigationBarItem(
                selected = selectedTab == 3,
                onClick = { onTabSelected(3) },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Profile",
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = { Text("Profile", fontSize = 11.sp, fontWeight = if (selectedTab == 3) FontWeight.Bold else FontWeight.Normal) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = HomeFixBlue,
                    selectedTextColor = HomeFixBlue,
                    indicatorColor = HomeFixBlueLight,
                    unselectedIconColor = Color(0xFF94A3B8),
                    unselectedTextColor = Color(0xFF94A3B8)
                ),
                modifier = Modifier.testTag("nav_tab_profile")
            )
        }
    }
}
