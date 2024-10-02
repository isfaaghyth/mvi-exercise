package app.isfa.mvi.component.product

import androidx.compose.runtime.Composable
import app.isfa.mvi.component.ComponentDropDown
import app.isfa.mvi.component.DropDownType

@Composable
fun ProductDropDownComponent(
    state: ProductUiState,
    onProductDropDownClicked: (String) -> Unit
) {
    ComponentDropDown(
        type = DropDownType("Product"),
        items = state.items,
    ) {
        onProductDropDownClicked(it)
    }
}