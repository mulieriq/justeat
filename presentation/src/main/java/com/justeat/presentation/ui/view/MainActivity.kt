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
package com.justeat.presentation.ui.view

import android.os.Bundle
import com.justeat.presentation.R
import com.justeat.presentation.data.Restaurant
import com.justeat.presentation.databinding.ActivityMainBinding
import com.justeat.presentation.ui.adapter.RestaurantRecyclerViewAdapter
import com.justeat.presentation.ui.base.BindingActivity
import com.justeat.presentation.ui.viewmodel.RestaurantsViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BindingActivity<ActivityMainBinding>() {

    private lateinit var restaurantRecyclerViewAdapter: RestaurantRecyclerViewAdapter
    private val restaurantsViewModel: RestaurantsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this

        restaurantRecyclerViewAdapter = RestaurantRecyclerViewAdapter { restaurant ->
            onFavourite(restaurant)
        }
        recyclerView.adapter = restaurantRecyclerViewAdapter

        restaurantsViewModel.fetchRestaurants("", "")

        restaurantsViewModel.restaurants.observe(this) { restaurants ->
            restaurantRecyclerViewAdapter.submitList(restaurants)
        }
    }

    private fun onFavourite(restaurant: Restaurant) {
        val updateRestaurant = restaurant.copy(isFavourite = true)
        restaurantsViewModel.favouriteRestaurant(updateRestaurant)
    }

    override val layoutResId: Int
        get() = R.layout.activity_main
}