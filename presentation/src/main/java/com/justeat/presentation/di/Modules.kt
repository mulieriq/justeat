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
package com.justeat.presentation.di

import com.justeat.presentation.ui.viewmodel.RestaurantsViewModel
import org.koin.core.module.Module
import org.koin.dsl.module

private val viewModelModules: Module = module {
    single { RestaurantsViewModel(get(), get()) }
}

val presentationModule: List<Module> = listOf(
    viewModelModules
)