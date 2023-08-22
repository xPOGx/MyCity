package com.example.mycity.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.example.mycity.R
import com.example.mycity.data.Place

@Composable
fun PlacesScreen(
    places: List<Place>,
    onPlaceClicked: (Place) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small)),
        modifier = modifier
            .padding(dimensionResource(id = R.dimen.padding_medium))
    ) {
        items(places) { place ->
            ItemCard(
                itemName = place.name,
                itemImageRes = place.imageRes,
                modifier = Modifier.clickable {
                    onPlaceClicked(place)
                }
            )
        }
    }
}