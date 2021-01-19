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
package com.justeat.core.binding

import android.text.TextUtils
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.justeat.core.R
import com.justeat.core.extensions.formatCurrency
import com.justeat.core.extensions.formatLocationToKms

@BindingAdapter(value = ["formattedCurrency"])
fun showFormattedCurrency(view: TextView, amount: Int) {
    when {
        view.context != null && !TextUtils.isEmpty(amount.toString()) -> {
            view.text =
                String.format(
                    "%s %s",
                    view.context.getString(R.string.currency_euro),
                    amount.formatCurrency()
                )
        }
    }
}

@BindingAdapter(value = ["formattedLocation"])
fun showFormattedLocation(view: TextView, distance: String) {
    when {
        view.context != null && !TextUtils.isEmpty(distance) -> {
            view.text =
                String.format(
                    "%d %s",
                    distance.toFloat().toInt().formatLocationToKms(),
                    view.context.getString(R.string.distance_kilometers)
                )
        }
    }
}