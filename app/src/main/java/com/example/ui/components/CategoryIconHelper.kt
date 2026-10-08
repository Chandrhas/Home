package com.example.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.BatteryChargingFull
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CleaningServices
import androidx.compose.material.icons.filled.Construction
import androidx.compose.material.icons.filled.FormatPaint
import androidx.compose.material.icons.filled.Handyman
import androidx.compose.material.icons.filled.HomeRepairService
import androidx.compose.material.icons.filled.Kitchen
import androidx.compose.material.icons.filled.LocalLaundryService
import androidx.compose.material.icons.filled.Plumbing
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.Waves
import androidx.compose.material.icons.filled.Yard
import androidx.compose.ui.graphics.vector.ImageVector

object CategoryIconHelper {
    fun getIcon(iconName: String): ImageVector {
        return when (iconName) {
            "Bolt" -> Icons.Default.Bolt
            "Plumbing" -> Icons.Default.Plumbing
            "AcUnit" -> Icons.Default.AcUnit
            "Build" -> Icons.Default.Build
            "WaterDrop" -> Icons.Default.WaterDrop
            "LocalLaundryService" -> Icons.Default.LocalLaundryService
            "Kitchen" -> Icons.Default.Kitchen
            "Tv" -> Icons.Default.Tv
            "CleaningServices" -> Icons.Default.CleaningServices
            "BugReport" -> Icons.Default.BugReport
            "FormatPaint" -> Icons.Default.FormatPaint
            "Videocam" -> Icons.Default.Videocam
            "BatteryChargingFull" -> Icons.Default.BatteryChargingFull
            "Waves" -> Icons.Default.Waves
            "Construction" -> Icons.Default.Construction
            "Yard" -> Icons.Default.Yard
            "Handyman" -> Icons.Default.Handyman
            else -> Icons.Default.HomeRepairService
        }
    }
}
