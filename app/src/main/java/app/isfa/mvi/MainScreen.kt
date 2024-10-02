package app.isfa.mvi

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import app.isfa.mvi.component.category.CategoryDropDown
import app.isfa.mvi.component.product.ProductDropDown

@Composable
fun MainScreen(
    state: MainUiState,
    sendEvent: (MainEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    var productItemSelected by remember { mutableStateOf("") }

    Column(modifier = modifier) {
        ProductDropDown(state = state.productUiState) {
            productItemSelected = it
            sendEvent(ProductItemClicked(it))
        }

        AnimatedVisibility(state.categoryUiState.items.isNotEmpty()) {
            CategoryDropDown(state = state.categoryUiState)
        }
    }
}