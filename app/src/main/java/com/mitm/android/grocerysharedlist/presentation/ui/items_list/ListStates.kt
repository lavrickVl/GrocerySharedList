package com.mitm.android.grocerysharedlist.presentation.ui.items_list

import com.mitm.android.grocerysharedlist.model.Item

data class ListStates(
    val list: List<Item> = emptyList(),
    val inputItem: String = "",
    val counter: Int = 0,
    val loading: Boolean = false
)
