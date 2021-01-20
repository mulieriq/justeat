/*
 * Copyright 2021 JustEat
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.justeat.ui.view

import android.view.View
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.agoda.kakao.chipgroup.KChipGroup
import com.agoda.kakao.recycler.KRecyclerItem
import com.agoda.kakao.recycler.KRecyclerView
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KTextView
import com.justeat.domain.usecases.FavouriteUseCase
import com.justeat.domain.usecases.FilterRestaurantsUseCase
import com.justeat.domain.usecases.RestaurantsUseCase
import com.justeat.domain.usecases.SearchRestaurantUseCase
import com.justeat.fake.fakeRestaurant
import com.justeat.presentation.R
import com.justeat.presentation.ui.view.MainActivity
import com.justeat.presentation.ui.viewmodel.RestaurantsViewModel
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matcher
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import org.koin.test.mock.declare

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest : KoinTest {

    private val restaurantUseCase = mockk<RestaurantsUseCase>(relaxUnitFun = true)
    private val favouriteUseCase = mockk<FavouriteUseCase>(relaxUnitFun = true)
    private val searchUseCase = mockk<SearchRestaurantUseCase>(relaxUnitFun = true)
    private val filterUseCase = mockk<FilterRestaurantsUseCase>(relaxUnitFun = true)

    @After
    fun tearDown() {
        clearMocks(restaurantUseCase, favouriteUseCase, searchUseCase, filterUseCase)
    }

    @Test
    fun test_check_chips_displayed_and_clickable() = runBlocking {

        coEvery {
            restaurantUseCase.invoke(Unit)
        } returns flowOf(fakeRestaurant)

        declare {
            RestaurantsViewModel(
                restaurantUseCase,
                favouriteUseCase,
                searchUseCase,
                filterUseCase
            )
        }

        ActivityScenario.launch(MainActivity::class.java)

        Screen.onScreen<JustEatScreen> {
            chips.isDisplayed()
            chips.isEnabled()
            chips.apply {
                hasSize(8)
            }
        }

        Screen.idle(3000)
    }

    @Test
    fun test_check_restaurants_displayed() = runBlocking {

        coEvery {
            restaurantUseCase.invoke(Unit)
        } returns flowOf(fakeRestaurant)

        declare {
            RestaurantsViewModel(
                restaurantUseCase,
                favouriteUseCase,
                searchUseCase,
                filterUseCase
            )
        }

        ActivityScenario.launch(MainActivity::class.java)

        Screen.onScreen<JustEatScreen> {

            restaurants {
                isDisplayed()
                firstChild<Item> {
                    isVisible()
                    restaurantName {
                        hasText(fakeRestaurant.first().restaurantName)
                    }
                    textViewRestaurantState {
                        hasText(fakeRestaurant.first().restaurantStatus)
                    }
                }
            }
        }

        Screen.idle(3000)
    }

    class JustEatScreen : Screen<JustEatScreen>() {
        val restaurants: KRecyclerView = KRecyclerView(
            {
                withId(R.id.recyclerView)
            },
            itemTypeBuilder = {
                itemType(MainActivityTest::Item)
            }
        )
        val chips = KChipGroup { withId(R.id.chipGroup) }
    }

    class Item(parent: Matcher<View>) : KRecyclerItem<Item>(parent) {
        val restaurantName = KTextView { withId(R.id.textViewRestaurantName) }
        val textViewRestaurantState = KTextView { withId(R.id.textViewRestaurantState) }
    }
}