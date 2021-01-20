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
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.chip.Chip
import com.justeat.core.extensions.hide
import com.justeat.core.extensions.show
import com.justeat.presentation.R
import com.justeat.presentation.data.Restaurant
import com.justeat.presentation.databinding.ActivityMainBinding
import com.justeat.presentation.ui.adapter.RestaurantRecyclerViewAdapter
import com.justeat.presentation.ui.base.BindingActivity
import com.justeat.presentation.ui.viewmodel.RestaurantsViewModel
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

        binding.recyclerView.itemAnimator = null
        binding.recyclerView.adapter = restaurantRecyclerViewAdapter

        setUpRestaurantsList()
        setUpSearch()
        setUpFilters()
    }

    private fun setUpRestaurantsList() {
        restaurantsViewModel.fetchRestaurants()
        observeData()
    }

    private fun onFavourite(restaurant: Restaurant) {
        val updateRestaurant = restaurant.copy(isFavourite = true)
        restaurantsViewModel.favouriteRestaurant(updateRestaurant)
    }

    private fun setUpSearch() {
        binding.toolbar.searchInput.doAfterTextChanged { searchString ->
            if (!searchString.isNullOrEmpty()) {
                restaurantsViewModel.searchRestaurants(searchString = searchString.toString())
            } else {
                setUpRestaurantsList()
            }
        }
    }

    private fun setUpFilters() {
        binding.toolbar.chipGroup.setOnCheckedChangeListener { _, checkedId ->
            val selectedChip = binding.toolbar.chipGroup.findViewById<Chip>(checkedId)
            if (selectedChip?.isChecked == true) {
                val sortBy = selectedChip.text.toString().returnSortByFilter()
                restaurantsViewModel.filterRestaurants(sortBy = sortBy)
            } else {
                observeData()
            }
        }
    }

    private fun observeData() {
        restaurantsViewModel.restaurants.observe(this) { restaurants ->
            if (!restaurants.isNullOrEmpty()) {
                showListState()
                restaurantRecyclerViewAdapter.submitList(restaurants)
            } else {
                showEmptyState()
            }
        }
    }

    private fun showListState() {
        binding.swipeRefreshLayout.show()
        binding.layoutEmpty.hide()
    }

    private fun showEmptyState() {
        binding.swipeRefreshLayout.hide()
        binding.layoutEmpty.show()
    }

    private fun String.returnSortByFilter(): String? = when (this) {
        resources.getString(R.string.chip_best_match) -> "bestMatch"
        resources.getString(R.string.chip_newest) -> "newest"
        resources.getString(R.string.chip_rating_average) -> "ratingAverage"
        resources.getString(R.string.chip_distance) -> "distance"
        resources.getString(R.string.chip_popularity) -> "popularity"
        resources.getString(R.string.chip_average_product_price) -> "averageProductPrice"
        resources.getString(R.string.chip_delivery_cost) -> "deliveryCosts"
        resources.getString(R.string.chip_minimum_cost) -> "minCost"
        else -> null
    }

    override val layoutResId: Int
        get() = R.layout.activity_main
}