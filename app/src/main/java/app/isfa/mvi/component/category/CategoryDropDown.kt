package app.isfa.mvi.component.category

import androidx.compose.animation.AnimatedVisibility
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
            AnimatedVisibility(data.items.isNotEmpty()) {
                ComponentDropDown(
                    type = DropDownType("Category"),
                    items = data.items,
                ) {}
            }
        }
        is UiState.Fail -> {
            Text(text = "Category Kosong")
        }
        else -> Unit
    }
}