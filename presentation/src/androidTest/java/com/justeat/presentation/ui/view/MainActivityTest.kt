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

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ScrollToAction
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.LargeTest
import androidx.test.runner.AndroidJUnit4
import com.agoda.kakao.recycler.KRecyclerItem
import com.agoda.kakao.recycler.KRecyclerView
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KTextView
import com.justeat.presentation.R
import com.justeat.presentation.fake.fakeRestaurant
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.Matcher
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTest

class RecyclerviewScrollActions(private val original: ScrollToAction = ScrollToAction()) :
    ViewAction by original {

    override fun getConstraints(): Matcher<View> = CoreMatchers.anyOf(
        CoreMatchers.allOf(
            ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE),
            ViewMatchers.isDescendantOfA(ViewMatchers.isAssignableFrom(RecyclerView::class.java))
        ),
        original.constraints
    )
}

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest : KoinTest {

    @Test
    fun test_check_not_displayed() = runBlocking {

        ActivityScenario.launch(MainActivity::class.java)

        Screen.onScreen<JustEatScreen> {

            this.restaurants {

                act {
                    RecyclerviewScrollActions()
                }

                Screen.idle(3000)

                isDisplayed()

                firstChild<Item> {
                    isVisible()
                    restaurantName {
                        hasText(fakeRestaurant.first().restaurantName)
                    }
                    restaurantDistance {
                        hasText(fakeRestaurant.first().restaurantDistance)
                    }
                }
            }
        }

        Screen.idle(3000)
    }

    class JustEatScreen : Screen<JustEatScreen>() {
        val restaurants: KRecyclerView = KRecyclerView(
            {
                withId(R.id.recyclerView)
            },
            itemTypeBuilder = {
                itemType(::Item)
            }
        )
    }

    class Item(parent: Matcher<View>) : KRecyclerItem<Item>(parent) {
        val restaurantName = KTextView { withId(R.id.textViewRestaurantName) }
        val restaurantDistance = KTextView { withId(R.id.textViewRestaurantDistance) }
    }
}