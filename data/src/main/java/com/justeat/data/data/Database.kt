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

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.justeat.core.util.ioThread
import com.justeat.core.util.readFile
import com.justeat.data.data.dao.RestaurantDao
import com.justeat.data.data.entity.RestaurantEntity
import com.justeat.data.data.entity.RestaurantList

@androidx.room.Database(
    entities = [
        RestaurantEntity::class
    ],
    version = 1,
    exportSchema = false
)

abstract class Database : RoomDatabase() {

    companion object {
        private val INSTANCE: Database? = null

        fun getInstance(context: Context): Database {
            return when (INSTANCE) {
                null -> {
                    Room.databaseBuilder(
                        context,
                        Database::class.java,
                        "justeat-db"
                    )
                        .addCallback(object : Callback() {
                            override fun onCreate(db: SupportSQLiteDatabase) {
                                super.onCreate(db)
                                // moving to a new thread
                                ioThread {
                                    val restaurantsJson =
                                        context.assets.readFile("data.json")
                                    val mappedList: RestaurantList =
                                        restaurantsJson.deserializeRestaurants()
                                    getInstance(context).restaurantDao()
                                        .insertFromAsset(mappedList.restaurants)
                                }
                            }
                        })
                        .build()
                }
                else -> {
                    INSTANCE
                }
            }
        }
    }

    abstract fun restaurantDao(): RestaurantDao
}