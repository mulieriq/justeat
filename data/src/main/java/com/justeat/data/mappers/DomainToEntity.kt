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
package com.justeat.data.mappers

import com.justeat.data.data.entity.RestaurantEntity
import com.justeat.domain.model.RestaurantDomainModel

// TODO Add other params
internal fun RestaurantDomainModel.toEntity() = RestaurantEntity(
    name = this.restaurantName,
    status = this.restaurantStatus,
    distance = this.restaurantDistance,
    ratingAverage = this.restaurantRating,
    minCost = this.restaurantMinCost,
    deliveryCosts = this.restaurantDeliveryCost,
    averageProductPrice = 0,
    bestMatch = 0.0,
    id = 0L,
    newest = 0.0,
    popularity = 0.0
)