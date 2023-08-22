package com.example.mycity.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.mycity.R

val IMAGE = R.drawable.ic_launcher_foreground

data class CityUiState(
    val cities: List<City> = listOf(),
    val cityPicked: City? = null,
    val categoryPicked: CityCategory? = null,
    val placePicked: Place? = null,
    val searchText: String = "",
    val customPlaces: List<Place> = listOf()
)

data class City(
    val name: String = "",
    val imageRes: Int = IMAGE,
    val categories: List<CityCategory> = listOf()
)

data class CityCategory(
    val name: String = "",
    val imageRes: Int = IMAGE,
    val places: List<Place> = listOf()
)

data class Place(
    val name: String = "",
    @DrawableRes val imageRes: Int = IMAGE,
    @StringRes val description: Int = R.string.big_text
)
