package com.example.ui.screens

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.BookingOrder
import com.example.data.model.BookingStatus
import com.example.ui.components.CategoryIconHelper
import com.example.ui.components.HomeFixBlue
import com.example.ui.components.HomeFixBottomBar
import com.example.ui.components.HomeFixGreen
import com.example.ui.components.HomeFixRed
import com.example.ui.viewmodel.GharSevaViewModel
import com.example.ui.viewmodel.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyBookingsScreen(
    viewModel: GharSevaViewModel,
    modifier: Modifier = Modifier
) {
    BackHandler {
        viewModel.selectTab(0)
    }

    val context = LocalContext.current
    val bookings by viewModel.bookings.collectAsState()
    val selectedTabNav by viewModel.selectedTab.collectAsState()
    var selectedFilterTab by remember { mutableIntStateOf(0) } // 0: Upcoming, 1: Completed, 2: Cancelled

    val filteredBookings = when (selectedFilterTab) {
        0 -> bookings.filter { it.status == BookingStatus.CONFIRMED || it.status == BookingStatus.ON_THE_WAY || it.status == BookingStatus.IN_PROGRESS || it.status == BookingStatus.REACHED_LOCATION || it.status == BookingStatus.EXPERT_ASSIGNED }
        1 -> bookings.filter { it.status == BookingStatus.COMPLETED }
        2 -> bookings.filter { it.status == BookingStatus.CANCELLED }
        else -> bookings
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "My Bookings",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { viewModel.selectTab(0) },
                        modifier = Modifier.testTag("my_bookings_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = {
            HomeFixBottomBar(
                selectedTab = selectedTabNav,
                onTabSelected = { viewModel.selectTab(it) }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Color(0xFFF8FAFC))
                .padding(paddingValues)
        ) {
            // Tabs: Upcoming, Completed, Cancelled (Matching Screen 11)
            TabRow(
                selectedTabIndex = selectedFilterTab,
                containerColor = Color.White,
                contentColor = HomeFixBlue,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        Modifier.tabIndicatorOffset(tabPositions[selectedFilterTab]),
                        color = HomeFixBlue
                    )
                }
            ) {
                Tab(
                    selected = selectedFilterTab == 0,
                    onClick = { selectedFilterTab = 0 },
                    text = {
                        Text(
                            text = "Upcoming",
                            fontWeight = if (selectedFilterTab == 0) FontWeight.Bold else FontWeight.Normal,
                            fontSize = 13.sp
                        )
                    }
                )
                Tab(
                    selected = selectedFilterTab == 1,
                    onClick = { selectedFilterTab = 1 },
                    text = {
                        Text(
                            text = "Completed",
                            fontWeight = if (selectedFilterTab == 1) FontWeight.Bold else FontWeight.Normal,
                            fontSize = 13.sp
                        )
                    }
                )
                Tab(
                    selected = selectedFilterTab == 2,
                    onClick = { selectedFilterTab = 2 },
                    text = {
                        Text(
                            text = "Cancelled",
                            fontWeight = if (selectedFilterTab == 2) FontWeight.Bold else FontWeight.Normal,
                            fontSize = 13.sp
                        )
                    }
                )
            }

            if (filteredBookings.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No bookings found in this section",
                        color = Color(0xFF64748B),
                        fontSize = 14.sp
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp, vertical = 14.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp),
                    contentPadding = PaddingValues(bottom = 20.dp)
                ) {
                    items(filteredBookings, key = { it.id }) { order ->
                        HomeFixBookingCard(
                            order = order,
                            onViewDetails = {
                                viewModel.navigateTo(Screen.ProviderProfile(order.provider))
                            },
                            onCancel = {
                                viewModel.cancelBooking(order.id)
                                Toast.makeText(context, "Booking cancelled successfully", Toast.LENGTH_SHORT).show()
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HomeFixBookingCard(
    order: BookingOrder,
    onViewDetails: () -> Unit,
    onCancel: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier.fillMaxWidth().testTag("booking_card_${order.id}")
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0xFFFEF3C7)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = CategoryIconHelper.getIcon("Bolt"),
                            contentDescription = null,
                            tint = Color(0xFFD97706),
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(
                            text = order.categoryName,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = Color(0xFF0F172A)
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "${order.scheduledDate} • ${order.scheduledTimeSlot}",
                            fontSize = 12.sp,
                            color = Color(0xFF64748B)
                        )
                    }
                }

                // Status Badge (Confirmed in green)
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(
                            when (order.status) {
                                BookingStatus.CONFIRMED -> Color(0xFFDCFCE7)
                                BookingStatus.COMPLETED -> Color(0xFFDCFCE7)
                                BookingStatus.CANCELLED -> Color(0xFFFEE2E2)
                                else -> Color(0xFFFEF3C7)
                            }
                        )
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = order.status.displayName,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = when (order.status) {
                            BookingStatus.CONFIRMED -> HomeFixGreen
                            BookingStatus.COMPLETED -> HomeFixGreen
                            BookingStatus.CANCELLED -> HomeFixRed
                            else -> Color(0xFFD97706)
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = Color(0xFFF1F5F9))
            Spacer(modifier = Modifier.height(10.dp))

            // Actions: View Details & Cancel
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "View Details",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = HomeFixBlue,
                    modifier = Modifier.clickable { onViewDetails() }
                )

                if (order.status != BookingStatus.COMPLETED && order.status != BookingStatus.CANCELLED) {
                    Text(
                        text = "Cancel",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = HomeFixRed,
                        modifier = Modifier.clickable { onCancel() }
                    )
                }
            }
        }
    }
}
