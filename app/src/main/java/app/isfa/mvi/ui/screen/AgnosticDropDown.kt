package app.isfa.mvi.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.runtime.Composable
import app.isfa.mvi.shared.ComponentDropDown
import app.isfa.mvi.shared.DropDownType

@Composable
fun AgnosticDropDown(
    title: String,
    data: List<String>
) {
    AnimatedVisibility(data.isNotEmpty()) {
        ComponentDropDown(
            type = DropDownType(title),
            items = data,
        ) {}
    }
}