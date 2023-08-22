package com.example.mycity.data

import com.example.mycity.R


object DataSource {
    private val parkPlaces = listOf(
        Place(
            name = "Парк Пушкіна"
        ),
        Place(
            name = "Парк КПІ"
        )
    )

    private val restaurantsPlaces = listOf(
        Place(
            name = "Палітурка"
        ),
        Place(
            name = "Львівські Круасани"
        ),
        Place(
            name = "Коргі Кафе"
        ),
        Place(
            name = "MENYA MUSASHI"
        )
    )

    private val scPlaces = listOf(
        Place(
            name = "BLOCKBUSTER"
        ),
        Place(
            name = "DREAM TOWN"
        )
    )

    private val categories = listOf(
        CityCategory(
            name = "Parks",
            imageRes = R.drawable.park,
            places = parkPlaces
        ),
        CityCategory(
            name ="Restaurants",
            imageRes = R.drawable.restaraunt,
            places = restaurantsPlaces
        ),
        CityCategory(
            name ="Shopping centers",
            imageRes = R.drawable.shopping_mall,
            places = scPlaces
        )
    )

    val cities = listOf(
        City(
            name = "Kyiv",
            imageRes = R.drawable.kyiv,
            categories = categories
        ),
        City(
            name = "Odessa",
            imageRes = R.drawable.odessa,
            categories = categories
        ),
        City(
            name = "Cherkasy",
            imageRes = R.drawable.cherkasy,
            categories = categories
        )
    )

    val allPlaces = fillAllList()

    private fun fillAllList(): List<Place> {
        val list = mutableListOf<Place>()
        list.addAll(parkPlaces)
        list.addAll(scPlaces)
        list.addAll(restaurantsPlaces)

        return list
    }
}