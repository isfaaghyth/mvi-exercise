package app.isfa.mvi.component.product

import androidx.compose.runtime.Composable
import app.isfa.mvi.component.ComponentDropDown
import app.isfa.mvi.component.DropDownType

@Composable
fun ProductDropDown(
    data: ProductUiState,
    onProductDropDownClicked: (String) -> Unit
) {
    ComponentDropDown(
        type = DropDownType("Product"),
        items = data.items.map { it.second },
    ) {
        onProductDropDownClicked(it)
    }
}