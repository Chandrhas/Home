package com.example

import com.example.data.model.BookingStatus
import com.example.data.repository.GharSevaRepository
import org.junit.Assert.*
import org.junit.Test

class ExampleUnitTest {
  @Test
  fun testCategoriesLoaded() {
    val repository = GharSevaRepository()
    assertTrue(repository.categories.isNotEmpty())
    assertEquals(16, repository.categories.size)
    assertTrue(repository.categories.any { it.name == "Electrician" })
    assertTrue(repository.categories.any { it.name == "Plumber" })
    assertTrue(repository.categories.any { it.name == "Gardener" })
  }

  @Test
  fun testWalletBalanceUpdate() {
    val repository = GharSevaRepository()
    val initialBalance = repository.userProfile.value.walletBalance
    repository.addMoneyToWallet(500)
    assertEquals(initialBalance + 500, repository.userProfile.value.walletBalance)
    assertTrue(repository.walletTransactions.value.any { it.amount == 500 && it.isCredit })
  }

  @Test
  fun testBookingCancellation() {
    val repository = GharSevaRepository()
    val firstBooking = repository.bookings.value.first()
    repository.cancelBooking(firstBooking.id)
    val cancelledOrder = repository.bookings.value.first { it.id == firstBooking.id }
    assertEquals(BookingStatus.CANCELLED, cancelledOrder.status)
  }
}
