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
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BindingActivity<ActivityMainBinding>() {

    private lateinit var restaurantRecyclerViewAdapter: RestaurantRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding.lifecycleOwner = this

        restaurantRecyclerViewAdapter = RestaurantRecyclerViewAdapter()
        recyclerView.adapter = restaurantRecyclerViewAdapter

        restaurantRecyclerViewAdapter.submitList(prepareDemoRestaurants())
    }

    override val layoutResId: Int
        get() = R.layout.activity_main

    private fun prepareDemoRestaurants(): List<Restaurant> {
        val restaurants = ArrayList<Restaurant>()
        restaurants.add(Restaurant("Tanoshii Sushi", "open", 4.5, 1190, 1000, 200))
        restaurants.add(Restaurant("Tandoori Express", "closed", 4.5, 2308, 1300, 150))
        restaurants.add(Restaurant("Royal Thai", "order_ahead", 4.5, 2639, 2500, 150))
        return restaurants
    }
}