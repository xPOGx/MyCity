package com.example.mycity.ui

import androidx.lifecycle.ViewModel
import com.example.mycity.data.City
import com.example.mycity.data.CityCategory
import com.example.mycity.data.CityUiState
import com.example.mycity.data.DataSource
import com.example.mycity.data.Place
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CityViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(CityUiState(DataSource.cities))
    val uiState: StateFlow<CityUiState> = _uiState.asStateFlow()

    fun changeCity(city: City?) {
        _uiState.update { currentState ->
            currentState.copy(
                cityPicked = city,
                categoryPicked = null,
                placePicked = null
            )
        }
    }

    fun changeCategory(category: CityCategory?) {
        _uiState.update { currentState ->
            currentState.copy(
                categoryPicked = category,
                placePicked = null
            )
        }
    }

    fun changePlace(place: Place?) {
        _uiState.update { currentState ->
            currentState.copy(
                placePicked = place
            )
        }
    }

    fun changeSearchField(text: String) {
        changeCustomList(text)
        _uiState.update { currentState ->
            currentState.copy(
                searchText = text
            )
        }
    }

    private fun changeCustomList(text: String) {
        if (text.length < 3) {
            if (_uiState.value.customPlaces.isNotEmpty()) {
                _uiState.update {
                    it.copy(
                        customPlaces = listOf()
                    )
                }
            }
        } else {
            val list = DataSource.allPlaces.filter { place ->
                place.name.lowercase().contains(text.lowercase())
            }
            _uiState.update { currentState ->
                currentState.copy(
                    customPlaces = list
                )
            }
        }
    }
}