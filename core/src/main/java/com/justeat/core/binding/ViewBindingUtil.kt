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
fun showFormattedLocation(view: TextView, distance: Int) {
    when {
        view.context != null && !TextUtils.isEmpty(distance.toString()) -> {
            view.text =
                String.format(
                    "%d %s",
                    distance.formatLocationToKms(),
                    view.context.getString(R.string.distance_kilometers)
                )
        }
    }
}