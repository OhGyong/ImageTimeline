package com.android.imagerecordapp

import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class HiltTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Test
    fun hiltTest() {
    }
}