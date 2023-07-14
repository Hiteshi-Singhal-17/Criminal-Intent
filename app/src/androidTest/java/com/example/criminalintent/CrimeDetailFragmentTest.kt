/*
 * Instrument tests for the CrimeDetailFragment class.
 */
package com.example.criminalintent

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isChecked
import androidx.test.espresso.matcher.ViewMatchers.isNotChecked
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CrimeDetailFragmentTest {
    private lateinit var scenario: FragmentScenario<CrimeDetailFragment>

    @Before
    fun setUp() {
        // Launch the fragment in a container
        scenario = launchFragmentInContainer()
    }

    @After
    fun tearDown() {
        // Close the fragment scenario
        scenario.close()
    }

    @Test
    fun isCheckboxChecked() {
        // Find the checkbox with id crime_solved
        onView(withId(R.id.crime_solved))
             // Check that checkbox starts as unchecked
            .check(matches(isNotChecked()))
             // Perform a click action on the checkbox
            .perform(click())
             // Check the checkbox is not checked
            .check(matches(isChecked()))
    }

    @Test
    fun isCrimeTitleCorrect() {
        val crimeTitle = "Tap Open"
        // Find the text view or crime field with id crime_title
        onView(withId(R.id.crime_title))
             // Enter the text to the field
            .perform(typeText(crimeTitle))
             // Check the text matches with the input
            .check(matches(withText(crimeTitle)))
    }

    @Test
    fun checkCrimeObject() {
        // Access the crime object from the fragment and modify its properties
        scenario.onFragment{
            val crime = it.crime
            crime.title = "Open tap"
            crime.isSolved = true

            // Perform assertions on the crime object
            assertNotNull(crime)
            assertEquals("Open tap",crime.title)
            assertTrue(crime.isSolved)
        }
    }
}