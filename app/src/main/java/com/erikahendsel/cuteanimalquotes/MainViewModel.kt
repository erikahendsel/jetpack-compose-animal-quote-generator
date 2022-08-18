package com.erikahendsel.cuteanimalquotes

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erikahendsel.cuteanimalquotes.data.Animal
import com.erikahendsel.cuteanimalquotes.data.AnimalApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val api: AnimalApi
) :ViewModel() {

    private val _state = mutableStateOf(AnimalState())
    val state: State<AnimalState> = _state

    init {
        getRandomAnimal()
    }

    fun getRandomAnimal() {
        viewModelScope.launch {
            try {
                _state.value = state.value.copy(isLoading = true)
                _state.value = state.value.copy(
                    animal = api.getRandomAnimal(),
                    isLoading = false
                )
            } catch (e: Exception) {
                Log.e("MainViewModel", "getRandomAnimal: ", e)
                _state.value = state.value.copy(isLoading = false)
            }
        }
    }

    data class AnimalState(
        val animal: Animal? = null,
        val isLoading: Boolean = false
    )
}