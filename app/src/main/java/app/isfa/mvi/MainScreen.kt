package app.isfa.mvi

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import app.isfa.mvi.component.category.CategoryDropDown
import app.isfa.mvi.component.product.ProductDropDown

@Composable
fun MainScreen(
    state: MainUiState,
    effects: MainEffects?,
    sendEvent: (MainEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var productItemSelected by remember { mutableStateOf("") }

    LaunchedEffect(effects) {
        when (effects) {
            is ShowNetworkError -> {
                Toast.makeText(context, effects.message, Toast.LENGTH_LONG).show()
            }
            else -> Unit
        }
    }

    Column(modifier = modifier) {
        ProductDropDown(data = state.productUiState) {
            productItemSelected = it
            sendEvent(ProductItemClicked(it))
        }

        CategoryDropDown(data = state.categoryUiState)
    }
}