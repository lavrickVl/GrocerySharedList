package com.mitm.android.grocerysharedlist.presentation.ui.items_list

import com.mitm.android.grocerysharedlist.model.Item

sealed class ListEvent{
    object UpdateListInRoom: ListEvent() //val roomID: String
    data class EditInput(val inputText: String): ListEvent()
    object InsertItem: ListEvent()
    data class DeleteItem(val item:Item): ListEvent()
    object DeleteAllItems: ListEvent()
}
