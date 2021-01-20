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
package com.justeat.presentation.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.junit.runners.model.Statement

@ExperimentalCoroutinesApi
class CoroutineTestRule(
    private val dispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()
) :
    TestWatcher() {

    val testCoroutineScope = TestCoroutineScope(dispatcher)

    override fun apply(base: Statement?, description: Description?): Statement {
        return object : Statement() {

            override fun evaluate() {
                Dispatchers.setMain(dispatcher)
                base?.evaluate()
                Dispatchers.resetMain()
                try {
                    testCoroutineScope.cleanupTestCoroutines()
                } catch (exception: Exception) {
                    exception.printStackTrace()
                }
            }
        }
    }
}