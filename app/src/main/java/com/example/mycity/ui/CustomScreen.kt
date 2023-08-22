package com.example.mycity.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.mycity.R
import com.example.mycity.data.Place

@Composable
fun CustomScreen(
    customPlaces: List<Place>,
    onPlaceClicked: (Place) -> Unit,
    modifier: Modifier = Modifier
) {
    if (customPlaces.isEmpty()) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.fillMaxSize()
        ) {
            Text(
                text = stringResource(R.string.not_find),
                modifier = Modifier
            )
        }
    } else {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small)),
            modifier = modifier
                .padding(dimensionResource(id = R.dimen.padding_medium))
        ) {
            items(customPlaces) { place ->
                ItemCard(
                    itemName = place.name,
                    itemImageRes = place.imageRes,
                    modifier = Modifier
                        .clickable {
                            onPlaceClicked(place)
                        }
                )
            }
        }
    }
}