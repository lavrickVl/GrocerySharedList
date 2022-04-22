package com.mitm.android.grocerysharedlist.presentation.ui.items_list

import androidx.compose.ui.focus.FocusState
import com.mitm.android.grocerysharedlist.model.Item

sealed class ListEvent{
    object UpdateListInRoom: ListEvent() //val roomID: String
    data class EditInput(val inputText: String): ListEvent()
    data class ChangeContentFocus(val focusState: FocusState): ListEvent()
    object InsertItem: ListEvent()
    data class DeleteItem(val item:Item): ListEvent()
    object DeleteAllItems: ListEvent()
}
