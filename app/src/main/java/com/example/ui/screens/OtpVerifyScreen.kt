package com.example.ui.screens

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.HomeFixBlue
import com.example.ui.components.HomeFixBrandLogo
import com.example.ui.viewmodel.GharSevaViewModel
import com.example.ui.viewmodel.Screen
import kotlinx.coroutines.delay

@Composable
fun OtpVerifyScreen(
    phone: String,
    viewModel: GharSevaViewModel,
    modifier: Modifier = Modifier
) {
    BackHandler {
        viewModel.navigateBack()
    }

    val context = LocalContext.current
    var otpValue by remember { mutableStateOf("123456") }
    var secondsLeft by remember { mutableIntStateOf(45) }

    LaunchedEffect(Unit) {
        while (secondsLeft > 0) {
            delay(1000)
            secondsLeft -= 1
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp)
            .testTag("otp_verify_screen"),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Logo
        HomeFixBrandLogo(size = 76)

        Spacer(modifier = Modifier.height(36.dp))

        // Title
        Text(
            text = "Verify OTP",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0F172A)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "We have sent a 6 digit code to",
            fontSize = 13.sp,
            color = Color(0xFF64748B)
        )
        Text(
            text = "+91 $phone",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0F172A)
        )

        Spacer(modifier = Modifier.height(30.dp))

        // 6 Separate Digit Boxes with Hidden Input
        BasicTextField(
            value = otpValue,
            onValueChange = {
                if (it.length <= 6 && it.all { char -> char.isDigit() }) {
                    otpValue = it
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            decorationBox = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    (0..5).forEach { index ->
                        val char = otpValue.getOrNull(index)?.toString() ?: ""
                        val isFocused = otpValue.length == index || (otpValue.length == 6 && index == 5)

                        Box(
                            modifier = Modifier
                                .size(46.dp)
                                .background(Color(0xFFF8FAFC), RoundedCornerShape(10.dp))
                                .border(
                                    1.5.dp,
                                    if (isFocused) HomeFixBlue else Color(0xFFE2E8F0),
                                    RoundedCornerShape(10.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = char,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF0F172A),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("otp_boxes_input")
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Resend Timer
        Text(
            text = if (secondsLeft > 0) "Resend OTP in 00:${secondsLeft.toString().padStart(2, '0')}" else "Resend OTP",
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = if (secondsLeft > 0) Color(0xFF64748B) else HomeFixBlue,
            modifier = Modifier.clickable(enabled = secondsLeft == 0) {
                secondsLeft = 45
                Toast.makeText(context, "New OTP sent: 123456", Toast.LENGTH_SHORT).show()
            }
        )

        Spacer(modifier = Modifier.height(28.dp))

        // Verify Button
        Button(
            onClick = {
                if (otpValue.length == 6) {
                    Toast.makeText(context, "Verified Successfully!", Toast.LENGTH_SHORT).show()
                    viewModel.navigateTo(Screen.Home)
                } else {
                    Toast.makeText(context, "Please enter all 6 digits", Toast.LENGTH_SHORT).show()
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = HomeFixBlue),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .testTag("verify_otp_confirm_button")
        ) {
            Text(
                text = "Verify",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Change Mobile Number Link
        Text(
            text = "Change Mobile Number",
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = HomeFixBlue,
            modifier = Modifier.clickable {
                viewModel.navigateBack()
            }
        )
    }
}
