package com.justeat.domain.usecases

import com.justeat.domain.model.RestaurantDomainModel
import com.justeat.domain.repository.RestaurantRepository
import kotlinx.coroutines.flow.Flow

typealias SearchRestaurantBaseUseCase = BaseUseCase<String?, Flow<List<RestaurantDomainModel>>>

class SearchRestaurantUseCase(
    private val restaurantRepository: RestaurantRepository
) : SearchRestaurantBaseUseCase {

    override suspend fun invoke(params: String?): Flow<List<RestaurantDomainModel>> =
        restaurantRepository.fetchRestaurants(params)
}