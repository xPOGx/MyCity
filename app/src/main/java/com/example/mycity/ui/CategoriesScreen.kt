package com.example.mycity.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.example.mycity.R
import com.example.mycity.data.CityCategory

@Composable
fun CategoriesScreen(
    categories: List<CityCategory>,
    onCategoryClicked: (CityCategory) -> Unit,
    contentType: MyCityContentType,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small)),
        modifier = when (contentType) {
            MyCityContentType.Standard, MyCityContentType.Medium -> modifier.padding(dimensionResource(id = R.dimen.padding_medium))
            else -> modifier.padding(top = 16.dp, bottom = 16.dp)
        }
    ) {
        items(categories) { category ->
            ItemCard(
                itemName = category.name,
                itemImageRes = category.imageRes,
                modifier = Modifier.clickable {
                    onCategoryClicked(category)
                }
            )
        }
    }
}