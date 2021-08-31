package com.hafizcode.moviesandtv.ui.home.view

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.hafizcode.moviesandtv.R
import com.hafizcode.moviesandtv.ui.home.view.helper.betterScrollTo
import com.hafizcode.moviesandtv.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class HomeActivityTest {

    @get:Rule
    var activityRule = ActivityScenarioRule(HomeActivity::class.java)

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.espressoTestIdlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.espressoTestIdlingResource)
    }

    @Test
    fun loadMovies() {
        onView(withText(R.string.film_tab)).perform(click())
        onView(withId(R.id.rv_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movie)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                20
            )
        )
    }

    @Test
    fun loadDetailMovies() {
        onView(withText(R.string.film_tab)).perform(click())
        onView(withId(R.id.rv_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movie)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                0
            )
        )
        onView(withId(R.id.rv_movie)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )

        onView(withId(R.id.image_item)).check(matches(isDisplayed()))
        onView(withId(R.id.text_title)).check(matches(isDisplayed()))
        onView(withId(R.id.text_rating_film)).check(matches(isDisplayed()))
        onView(withId(R.id.text_genre)).check(matches(isDisplayed()))
        onView(withId(R.id.text_rating_hour)).check(matches(isDisplayed()))
        onView(withId(R.id.text_date)).check(matches(isDisplayed()))
        onView(withId(R.id.action_fav)).perform(click())

        onView(withId(R.id.text_description)).perform(betterScrollTo())
        onView(withId(R.id.text_description)).check(matches(isDisplayed()))

        pressBack()
    }

    @Test
    fun loadTV() {
        onView(withText(R.string.tv_tab)).perform(click())
        onView(withId(R.id.rv_tv)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tv)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                20
            )
        )
    }

    @Test
    fun loadDetailTV() {
        onView(withText(R.string.tv_tab)).perform(click())
        onView(withId(R.id.rv_tv)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tv)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                0
            )
        )
        onView(withId(R.id.rv_tv)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )

        onView(withId(R.id.image_item)).check(matches(isDisplayed()))
        onView(withId(R.id.text_title)).check(matches(isDisplayed()))
        onView(withId(R.id.text_rating_film)).check(matches(isDisplayed()))
        onView(withId(R.id.text_genre)).check(matches(isDisplayed()))
        onView(withId(R.id.text_rating_hour)).check(matches(isDisplayed()))
        onView(withId(R.id.text_date)).check(matches(isDisplayed()))
        onView(withId(R.id.action_fav)).perform(click())

        onView(withId(R.id.text_description)).perform(betterScrollTo())
        onView(withId(R.id.text_description)).check(matches(isDisplayed()))

        pressBack()
    }

    @Test
    fun loadBookmarkedMovies() {
        onView(withId(R.id.action_to_fav)).perform(click())
        onView(withText(R.string.film_tab)).perform(click())
        onView(withId(R.id.rv_movie)).check(matches(isDisplayed()))
    }

    @Test
    fun loadDetailBookmarkedMovies() {
        onView(withText(R.string.film_tab)).perform(click())
        onView(withId(R.id.rv_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movie)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                0
            )
        )
        onView(withId(R.id.rv_movie)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )

        onView(withId(R.id.image_item)).check(matches(isDisplayed()))
        onView(withId(R.id.text_title)).check(matches(isDisplayed()))
        onView(withId(R.id.text_rating_film)).check(matches(isDisplayed()))
        onView(withId(R.id.text_genre)).check(matches(isDisplayed()))
        onView(withId(R.id.text_rating_hour)).check(matches(isDisplayed()))
        onView(withId(R.id.text_date)).check(matches(isDisplayed()))
//        onView(withId(R.id.action_fav)).check(matches(R.drawable.ic_baseline_star_24))

        onView(withId(R.id.text_description)).perform(betterScrollTo())
        onView(withId(R.id.text_description)).check(matches(isDisplayed()))

        pressBack()
    }

    @Test
    fun loadBookmarkedTvs() {
        onView(withId(R.id.action_to_fav)).perform(click())
        onView(withText(R.string.tv_tab)).perform(click())
        onView(withId(R.id.rv_tv)).check(matches(isDisplayed()))
    }

    @Test
    fun loadDetailBookmarkedTvs() {
        onView(withText(R.string.tv_tab)).perform(click())
        onView(withId(R.id.rv_tv)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tv)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                0
            )
        )
        onView(withId(R.id.rv_tv)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )

        onView(withId(R.id.image_item)).check(matches(isDisplayed()))
        onView(withId(R.id.text_title)).check(matches(isDisplayed()))
        onView(withId(R.id.text_rating_film)).check(matches(isDisplayed()))
        onView(withId(R.id.text_genre)).check(matches(isDisplayed()))
        onView(withId(R.id.text_rating_hour)).check(matches(isDisplayed()))
        onView(withId(R.id.text_date)).check(matches(isDisplayed()))
//        onView(withId(R.id.action_fav)).check(matches(R.drawable.ic_baseline_star_24))

        onView(withId(R.id.text_description)).perform(betterScrollTo())
        onView(withId(R.id.text_description)).check(matches(isDisplayed()))

        pressBack()
    }
}