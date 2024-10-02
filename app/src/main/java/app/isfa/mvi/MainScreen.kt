package app.isfa.mvi

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import app.isfa.mvi.component.product.ProductDropDownComponent

@Composable
fun MainScreen(
    state: MainUiState,
    modifier: Modifier = Modifier
) {
    var productItemSelected by remember { mutableStateOf("") }

    Column(modifier = modifier) {
        ProductDropDownComponent(state = state.productUiState) {
            productItemSelected = it
        }
    }
}