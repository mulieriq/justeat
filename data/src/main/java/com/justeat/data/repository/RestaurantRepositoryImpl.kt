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
package com.justeat.data.repository

import androidx.sqlite.db.SimpleSQLiteQuery
import com.justeat.data.data.dao.RestaurantDao
import com.justeat.data.mappers.toDomain
import com.justeat.data.mappers.toEntity
import com.justeat.domain.model.RestaurantDomainModel
import com.justeat.domain.repository.RestaurantRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RestaurantRepositoryImpl(
    private val restaurantDao: RestaurantDao
) : RestaurantRepository {

    override fun fetchRestaurants(
        name: String?,
        sortBy: String?
    ): Flow<List<RestaurantDomainModel>> {
        var queryString = "SELECT * FROM restaurant"

        val args = mutableListOf<Any>()

        if (name != null) {
            queryString += " WHERE name LIKE '%' || ? || '%'"
            args.add(name)
        }

        queryString += " ORDER BY"

        // Sort by current opening state first
        queryString += """
             CASE WHEN status = 'open' THEN 1
                 WHEN status = 'order ahead' THEN 2
                 ELSE 3
            END ASC
        """.trimIndent()

        // Favourites should come first
        queryString += """
            , CASE WHEN isFavourite = 1 THEN 1
                 ELSE 2
            END ASC
        """.trimIndent()

        // Sort by other selection
        if (sortBy != null) {
            queryString += ", $sortBy DESC"
        }

        val query = SimpleSQLiteQuery(queryString, args.toTypedArray())

        return restaurantDao.fetchRestaurants(query).map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun favouriteRestaurant(restaurantDomainModel: RestaurantDomainModel) {
        restaurantDao.update(restaurantDomainModel.toEntity())
    }
}