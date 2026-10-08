package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.CategoryIconHelper
import com.example.ui.components.HomeFixBlue
import com.example.ui.components.HomeFixBlueLight
import com.example.ui.viewmodel.GharSevaViewModel
import com.example.ui.viewmodel.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingPageScreen(
    categoryId: String,
    viewModel: GharSevaViewModel,
    modifier: Modifier = Modifier
) {
    BackHandler {
        viewModel.navigateBack()
    }

    val category = viewModel.categories.find { it.id == categoryId } ?: viewModel.categories.first()
    val selectedDate by viewModel.bookingDate.collectAsState()
    val selectedTime by viewModel.bookingTime.collectAsState()
    val specialRequest by viewModel.bookingSpecialRequest.collectAsState()

    val dateOptions = listOf("Today 12 Apr", "Sat 13 Apr", "Sun 14 Apr", "Mon 15 Apr", "Tue 16 Apr")
    val timeOptions = listOf("9:00 AM", "11:00 AM", "2:00 PM", "4:00 PM", "6:00 PM")

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Book Service",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { viewModel.navigateBack() },
                        modifier = Modifier.testTag("book_service_back_button")
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
            Surface(
                color = Color.White,
                shadowElevation = 8.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 14.dp)
                ) {
                    Button(
                        onClick = { viewModel.navigateTo(Screen.SelectAddress) },
                        colors = ButtonDefaults.buttonColors(containerColor = HomeFixBlue),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("booking_continue_button")
                    ) {
                        Text(
                            text = "Continue",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = Color.White
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Color(0xFFF8FAFC))
                .padding(paddingValues)
                .padding(20.dp)
        ) {
            // Selected Service Preview Card
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color(category.accentColor).copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = CategoryIconHelper.getIcon(category.iconName),
                                contentDescription = null,
                                tint = Color(category.accentColor),
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column {
                            Text(
                                text = category.name,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = Color(0xFF0F172A)
                            )
                            Text(
                                text = "₹${category.startingPrice} onwards",
                                fontSize = 13.sp,
                                color = Color(0xFF16A34A),
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    Text(
                        text = "Change",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = HomeFixBlue,
                        modifier = Modifier.clickable { viewModel.navigateBack() }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Select Date
            Text(
                text = "Select Date",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0F172A)
            )
            Spacer(modifier = Modifier.height(10.dp))

            val dateScroll = rememberScrollState()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(dateScroll),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                dateOptions.forEach { dateStr ->
                    val isSelected = selectedDate == dateStr
                    val parts = dateStr.split(" ")
                    val dayPart = parts.firstOrNull() ?: ""
                    val numPart = parts.drop(1).joinToString(" ")

                    Box(
                        modifier = Modifier
                            .width(82.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (isSelected) HomeFixBlueLight else Color.White)
                            .border(
                                1.5.dp,
                                if (isSelected) HomeFixBlue else Color(0xFFE2E8F0),
                                RoundedCornerShape(12.dp)
                            )
                            .clickable { viewModel.setBookingDate(dateStr) }
                            .padding(vertical = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = dayPart,
                                fontSize = 11.sp,
                                color = if (isSelected) HomeFixBlue else Color(0xFF64748B),
                                fontWeight = FontWeight.Medium
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = numPart,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isSelected) HomeFixBlue else Color(0xFF0F172A)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Select Time
            Text(
                text = "Select Time",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0F172A)
            )
            Spacer(modifier = Modifier.height(10.dp))

            val timeScroll = rememberScrollState()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(timeScroll),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                timeOptions.forEach { timeStr ->
                    val isSelected = selectedTime == timeStr
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (isSelected) HomeFixBlueLight else Color.White)
                            .border(
                                1.5.dp,
                                if (isSelected) HomeFixBlue else Color(0xFFE2E8F0),
                                RoundedCornerShape(12.dp)
                            )
                            .clickable { viewModel.setBookingTime(timeStr) }
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = timeStr,
                            fontSize = 13.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) HomeFixBlue else Color(0xFF0F172A)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Add Special Request
            Text(
                text = "Add Special Request (Optional)",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0F172A)
            )
            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = specialRequest,
                onValueChange = { viewModel.setBookingSpecialRequest(it) },
                placeholder = {
                    Text(
                        text = "e.g. Fan installation, extra switches...",
                        fontSize = 13.sp,
                        color = Color(0xFF94A3B8)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(96.dp)
                    .testTag("special_request_input"),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = HomeFixBlue,
                    unfocusedBorderColor = Color(0xFFE2E8F0)
                )
            )
        }
    }
}
