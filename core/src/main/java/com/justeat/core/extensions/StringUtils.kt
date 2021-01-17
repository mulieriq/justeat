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
package com.justeat.core.extensions

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import timber.log.Timber
import java.text.NumberFormat
import java.util.*

inline fun <reified T> String.deserializeJsonToList(): MutableList<T> {
    return try {
        Gson().fromJson(
            this,
            TypeToken.getParameterized(MutableList::class.java, T::class.java).type
        )
    } catch (e: Exception) {
        Timber.e(e)
        mutableListOf()
    }
}

fun Number.formatCurrency(): String = NumberFormat.getNumberInstance(Locale.US).format(this)

fun Int.formatLocationToKms(): Int = this / 1000 // TODO - Return with 3 decimal point