package com.alorma.drawer_modules.actions

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.isFocused
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.tooling.preview.Preview
import com.alorma.drawer_modules.DebugDrawerAction

@Composable
fun <T> dropdownSelectorAction(
    tag: String? = null,
    extraModifier: Modifier = Modifier,
    items: List<T>,
    itemFormatter: (T) -> String = { itemFormat -> itemFormat.toString() },
    defaultValue: T? = null,
    label: String? = null,
    onItemSelected: (T) -> Unit
) = object : DebugDrawerAction() {

    override val tag: String
        get() = tag ?: super.tag

    @Composable
    override fun build(modifier: Modifier) {
        DropdownSelectorComponent(
            modifier = modifier.then(extraModifier),
            items = items,
            itemFormatter = itemFormatter,
            defaultValue = defaultValue,
            label = label,
            onItemSelected = onItemSelected,
        )
    }
}

@Composable
fun <T> DropdownSelectorComponent(
    modifier: Modifier,
    items: List<T>,
    itemFormatter: (T) -> String = { itemFormat -> itemFormat.toString() },
    defaultValue: T? = null,
    label: String? = null,
    onItemSelected: (T) -> Unit
) {
    val textState = remember { mutableStateOf(defaultValue?.let(itemFormatter) ?: "") }
    val isExpanded = remember { mutableStateOf(false) }

    TextField(
        modifier = Modifier
            .onFocusChanged { focusState ->
                isExpanded.value = focusState.isFocused
            }
            .then(modifier),
        value = textState.value,
        readOnly = true,
        trailingIcon = {
            IconButton(onClick = { isExpanded.value = !isExpanded.value }) {
                DropdownIcon(isExpanded = isExpanded.value)
            }
        },
        label = label?.let { labelText -> { Text(text = labelText) } },
        onValueChange = { },
    )
    DropdownMenu(
        dropdownModifier = Modifier.fillMaxWidth(),
        toggle = {

        },
        expanded = isExpanded.value,
        onDismissRequest = { isExpanded.value = false }) {
        items.forEach { item ->
            DropdownItemComponent(item) { clickItem ->
                textState.value = itemFormatter(clickItem)
                isExpanded.value = false
                onItemSelected(clickItem)
            }
        }
    }
}

@Composable
fun <T> ColumnScope.DropdownItemComponent(
    item: T,
    itemFormatter: (T) -> String = { itemFormat -> itemFormat.toString() },
    onClick: (T) -> Unit
) {
    DropdownMenuItem(
        modifier = Modifier.fillMaxWidth(),
        onClick = {
            onClick(item)
        }) {
        Text(
            text = itemFormatter(item),
            modifier = Modifier
                .wrapContentWidth()
                .align(alignment = Alignment.Start)
        )
    }
}

@Preview(
    name = "DropDown",
    showBackground = true
)
@Composable
fun DropDownComponentPreview() {
    val items = listOf(
        Forlayo("A"),
        Forlayo("B"),
        Forlayo("C"),
    )
    DropdownSelectorComponent(
        Modifier,
        items = items,
        itemFormatter = { forlayo -> forlayo.text },
        defaultValue = items.first()
    ) {

    }
}

@Preview(
    name = "DropDown Item",
    showBackground = true
)
@Composable
fun DropDownItemComponentPreview() {
    Column(modifier = Modifier.fillMaxWidth()) {
        DropdownItemComponent(
            item = Forlayo("ABCD"),
            itemFormatter = { forlayo -> forlayo.text }
        ) {

        }
    }
}

data class Forlayo(val text: String)