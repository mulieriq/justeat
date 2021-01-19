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
import com.justeat.domain.usecases.RestaurantsUseCase
import com.justeat.presentation.data.Restaurant
import com.justeat.presentation.mappers.toPresentation
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class RestaurantsViewModel(
    private val restaurantsUseCase: RestaurantsUseCase
) : ViewModel() {

    private var _restaurants = MutableLiveData<List<Restaurant>>()
    val restaurants: LiveData<List<Restaurant>> get() = _restaurants

    fun fetchRestaurants(name: String?, sortBy: String?) {
        viewModelScope.launch {
            restaurantsUseCase.invoke("").collect { restaurants ->
                _restaurants.value = restaurants.map { it.toPresentation() }
            }
        }
    }
}