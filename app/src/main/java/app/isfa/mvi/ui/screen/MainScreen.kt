package app.isfa.mvi.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import app.isfa.mvi.core.Event
import app.isfa.mvi.ui.MainUiState
import app.isfa.mvi.ui.component.category.CategoryDropDown
import app.isfa.mvi.ui.component.product.ProductClicked
import app.isfa.mvi.ui.component.product.ProductDropDown

@Composable
fun MainScreen(
    state: MainUiState,
    sendEvent: (Event) -> Unit,
    modifier: Modifier = Modifier
) {
    var productItemSelected by remember { mutableStateOf("") }

    Column(modifier = modifier) {
        ProductDropDown(data = state.productUiState) {
            productItemSelected = it
            sendEvent(ProductClicked(productItemSelected))
        }

        CategoryDropDown(data = state.categoryUiState)

        AgnosticDropDown(
            title = "Shared Feature A",
            data = state.reusableUiState.featureAUiState.items
        )

        AgnosticDropDown(
            title = "Shared Feature B",
            data = state.reusableUiState.featureBUiState.items
        )
    }
}