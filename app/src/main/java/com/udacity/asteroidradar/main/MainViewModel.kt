package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.*
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.repository.AsteroidRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import java.lang.Exception

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val viewModelJob = Job()

    private val database = AsteroidDatabase.getDatabase(application)
    private val asteroidRepository = AsteroidRepository(database)

    private val _navigateToDetailFragment = MutableLiveData<Asteroid>()
    val navigateToDetailFragment: LiveData<Asteroid>
        get() = _navigateToDetailFragment

    private var _asteroids = MutableLiveData<List<Asteroid>>()
    val asteroids: LiveData<List<Asteroid>>
        get() = _asteroids

    private val _pictureOfDay = MutableLiveData<PictureOfDay>()
    val pictureOfDay: LiveData<PictureOfDay>
        get() = _pictureOfDay

    private val _displaySnackbarEvent = MutableLiveData<Boolean>()
    val displaySnackbarEvent: LiveData<Boolean>
        get() = _displaySnackbarEvent

    init {
        onViewWeekAsteroidsClicked()
        viewModelScope.launch {
            try {
                asteroidRepository.refreshAsteroids()
                refreshPictureOfDay()
            } catch (e: Exception) {
                println("Exception refreshing data: $e.message")
                _displaySnackbarEvent.value = true
            }
        }
    }

    private suspend fun refreshPictureOfDay() {
      _pictureOfDay.value = getPictureOfDay()
   }

    fun onAsteroidClicked(asteroid: Asteroid) {
        _navigateToDetailFragment.value = asteroid
    }

    fun doneNavigating() {
        _navigateToDetailFragment.value = null
    }

    fun doneDisplayingSnackbar() {
        _displaySnackbarEvent.value = false
    }

    fun onViewWeekAsteroidsClicked() {
        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                _asteroids.value = database.asteroidDao.getAsteroidsByCloseApproachDate(
                    getToday(), getSeventhDay())
            }
        }
    }

    fun onTodayAsteroidsClicked() {
        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                _asteroids.value = database.asteroidDao.getAsteroidsByCloseApproachDate(getToday(), getToday())
            }
        }
    }

    fun onSavedAsteroidsClicked() {
        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                _asteroids.value = database.asteroidDao.getAllAsteroids()
            }
        }
    }
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}