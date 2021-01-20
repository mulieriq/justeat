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

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.justeat.domain.usecases.FavouriteUseCase
import com.justeat.domain.usecases.FilterRestaurantsUseCase
import com.justeat.domain.usecases.RestaurantsUseCase
import com.justeat.domain.usecases.SearchRestaurantUseCase
import com.justeat.presentation.data.Restaurant
import com.justeat.presentation.mappers.toDomain
import com.justeat.presentation.mappers.toPresentation
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class RestaurantsViewModel(
    private val restaurantsUseCase: RestaurantsUseCase,
    private val favouriteUseCase: FavouriteUseCase,
    private val searchRestaurantUseCase: SearchRestaurantUseCase,
    private val filterRestaurantsUseCase: FilterRestaurantsUseCase
) : ViewModel() {

    private var _restaurants = MutableLiveData<List<Restaurant>>()
    val restaurants: LiveData<List<Restaurant>> get() = _restaurants

    fun fetchRestaurants() {
        viewModelScope.launch {
            restaurantsUseCase.invoke(Unit).collect { restaurants ->
                _restaurants.value = restaurants.map { it.toPresentation() }
            }
        }
    }

    fun favouriteRestaurant(restaurant: Restaurant) {
        viewModelScope.launch {
            favouriteUseCase.invoke(restaurant.toDomain())
        }
    }

    fun searchRestaurants(searchString: String?) {
        viewModelScope.launch {
            searchRestaurantUseCase.invoke(searchString).collect { restaurants ->
                _restaurants.value = restaurants.map {
                    it.toPresentation()
                }
            }
        }
    }

    fun filterRestaurants(sortBy: String?) {
        viewModelScope.launch {
            filterRestaurantsUseCase.invoke(sortBy).collect { restaurants ->
                _restaurants.value = restaurants.map {
                    it.toPresentation()
                }
            }
        }
    }
}