package com.justeat.data.repository

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.justeat.data.data.Database
import com.justeat.data.data.dao.RestaurantDao
import com.justeat.data.data.deserializeRestaurants
import com.justeat.data.data.entity.RestaurantList
import com.justeat.domain.repository.RestaurantRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import java.io.IOException

@RunWith(AndroidJUnit4::class)
@Config(manifest = Config.NONE, sdk = [28])
class RestaurantRepositoryImplTest {
    private lateinit var db: Database
    private lateinit var restaurantDao: RestaurantDao
    private lateinit var repo: RestaurantRepository

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, Database::class.java)
            .allowMainThreadQueries()
            .build()
        val restaurantsJson = javaClass.classLoader.getResource("test.json").readText()
        val mappedList: RestaurantList =
            restaurantsJson.deserializeRestaurants()
        restaurantDao = db.restaurantDao()
        restaurantDao.insertFromAsset(mappedList.restaurants)
        repo = RestaurantRepositoryImpl(restaurantDao)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun restaurantsWithoutFilterAndSort() = runBlocking {
        val restaurants = repo.fetchRestaurants().first()
        assertThat(restaurants.size, `is`(19))
    }

    @Test
    @Throws(Exception::class)
    fun restaurantsWithoutFilter_withSort() = runBlocking {
        val restaurants = repo.fetchRestaurants(sortBy = "newest").first()
        assertThat(restaurants.first().restaurantName, `is`("Indian Kitchen"))
    }

    @Test
    @Throws(Exception::class)
    fun restaurantsWithFilter() = runBlocking {
        val restaurants = repo.fetchRestaurants(name = "aarti").first()
        assertThat(restaurants.size, `is`(1))
    }
}