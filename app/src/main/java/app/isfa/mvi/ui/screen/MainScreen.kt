package app.isfa.mvi.ui.screen

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import app.isfa.mvi.core.Event
import app.isfa.mvi.ui.MainUiState
import app.isfa.mvi.ui.component.category.CategoryDropDown
import app.isfa.mvi.ui.component.product.ProductDropDown
import app.isfa.mvi.ui.component.product.ProductEvent

@Composable
fun MainScreen(
    state: MainUiState,
    sendEvent: (Event) -> Unit,
    modifier: Modifier = Modifier
) {
    var productItemSelected by remember { mutableStateOf("") }

    LazyColumn {
        items(state.positionUiState.components) {
            if (it == "product_list") {
                ProductDropDown(data = state.productUiState) {
                    productItemSelected = it
                    sendEvent(ProductEvent.ProductClicked(productItemSelected))
                }
            } else if (it == "category_title") {
                Text(text = "Ini component title")
            } else {
                CategoryDropDown(data = state.categoryUiState)
            }
        }
    }
}