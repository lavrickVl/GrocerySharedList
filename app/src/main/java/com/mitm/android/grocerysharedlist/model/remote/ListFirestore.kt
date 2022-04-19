package com.mitm.android.grocerysharedlist.model.remote

import com.mitm.android.grocerysharedlist.model.Item

data class ListFirestore(
    val list: List<Item> = emptyList()
)
