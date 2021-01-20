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
package com.justeat.presentation.ui.viewmodel

import com.jraska.livedata.test
import com.justeat.domain.usecases.FavouriteUseCase
import com.justeat.domain.usecases.FilterRestaurantsUseCase
import com.justeat.domain.usecases.RestaurantsUseCase
import com.justeat.domain.usecases.SearchRestaurantUseCase
import com.justeat.presentation.BaseViewModelTest
import com.justeat.presentation.utils.fakeRestaurant
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Test

class RestaurantViewModelTest : BaseViewModelTest() {

    private val restaurantUseCase = mockk<RestaurantsUseCase>()
    private val favouriteUseCase = mockk<FavouriteUseCase>()
    private val searchUseCase = mockk<SearchRestaurantUseCase>()
    private val filterUseCase = mockk<FilterRestaurantsUseCase>()

    private lateinit var restaurantsViewModel: RestaurantsViewModel

    @Before
    fun setup() {
        restaurantsViewModel =
            RestaurantsViewModel(restaurantUseCase, favouriteUseCase, searchUseCase, filterUseCase)
    }

    @Test
    fun `test list of restaurants is fetched`() {

        coEvery { restaurantUseCase.invoke(any()) } returns flowOf(fakeRestaurant)

        restaurantsViewModel.fetchRestaurants()

        coVerify { restaurantUseCase.invoke(any()) }
        restaurantsViewModel.restaurants.test().assertHasValue()
    }
}