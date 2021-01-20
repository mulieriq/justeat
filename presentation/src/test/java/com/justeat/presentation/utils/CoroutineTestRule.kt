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
