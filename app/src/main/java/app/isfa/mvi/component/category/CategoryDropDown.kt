package app.isfa.mvi.component.category

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import app.isfa.mvi.base.UiState
import app.isfa.mvi.component.ComponentDropDown
import app.isfa.mvi.component.DropDownType

@Composable
fun CategoryDropDown(
    data: CategoryUiState
) {
    when (data.state) {
        is UiState.Success -> {
            ComponentDropDown(
                type = DropDownType("Category"),
                items = data.items,
            ) {}
        }
        is UiState.Fail -> {
            Text(text = "tidak ditemukan")
        }
        else -> Unit
    }
}