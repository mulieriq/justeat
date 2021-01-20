package com.justeat.domain.usecases

import com.justeat.domain.model.RestaurantDomainModel
import com.justeat.domain.repository.RestaurantRepository
import kotlinx.coroutines.flow.Flow

typealias FilterRestaurantsBaseUseCase = BaseUseCase<String?, Flow<List<RestaurantDomainModel>>>

class FilterRestaurantsUseCase(
    private val restaurantRepository: RestaurantRepository
) : FilterRestaurantsBaseUseCase {

    override suspend fun invoke(params: String?): Flow<List<RestaurantDomainModel>> =
        restaurantRepository.fetchRestaurants(null, sortBy = params)
}