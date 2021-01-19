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
package com.justeat.data.data

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.justeat.data.data.entity.RestaurantList

fun String.deserializeRestaurants(): RestaurantList {

    val root = JsonParser().parse(this).asJsonObject
    val jsonArray = root["restaurants"].asJsonArray
    val finalObject = JsonObject()
    finalObject.add("restaurants", JsonArray())

    jsonArray.forEach { jsonElement ->
        val jsonObject = jsonElement.asJsonObject
        val flatObject = JsonObject()
        jsonObject.keySet().forEach { key ->
            if (jsonObject[key].isJsonObject) {
                val innerObject = jsonObject[key].asJsonObject
                innerObject.keySet().forEach { nestedKey ->
                    flatObject.add(nestedKey, innerObject[nestedKey])
                }
            } else {
                flatObject.add(key, jsonObject[key])
            }
        }
        finalObject["restaurants"].asJsonArray.add(flatObject)
    }

    return Gson().fromJson(finalObject, RestaurantList::class.java)
}