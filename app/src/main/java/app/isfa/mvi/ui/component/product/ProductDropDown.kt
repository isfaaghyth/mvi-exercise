package app.isfa.mvi.ui.component.product

import androidx.compose.runtime.Composable
import app.isfa.mvi.shared.ComponentDropDown
import app.isfa.mvi.shared.DropDownType

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