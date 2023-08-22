package com.example.mycity.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.mycity.R
import com.example.mycity.data.Place

@Composable
fun DetailScreen(
    place: Place,
    contentType: MyCityContentType,
    modifier: Modifier = Modifier
) {
    when (contentType) {
        MyCityContentType.Standard -> {
            Column(modifier = modifier) {
                Image(
                    painter = painterResource(id = place.imageRes),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = stringResource(id = place.description),
                    modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium))
                )
            }
        }

        MyCityContentType.Medium -> DetailContent(place = place)
        MyCityContentType.Expanded -> {
            Card(modifier = modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)) {
                DetailContent(place = place)
            }
        }
    }
}

@Composable
fun DetailContent(
    place: Place,
    modifier: Modifier = Modifier
) {
    Row {
        Image(
            painter = painterResource(id = place.imageRes),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxHeight()
                .weight(2f)
        )
        Text(
            text = stringResource(id = place.description),
            modifier = modifier
                .padding(dimensionResource(id = R.dimen.padding_medium))
                .verticalScroll(rememberScrollState())
                .weight(1f)
        )
    }
}