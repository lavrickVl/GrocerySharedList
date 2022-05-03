package com.mitm.android.grocerysharedlist.presentation.ui.items_list

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mitm.android.grocerysharedlist.core.AppSettings
import com.mitm.android.grocerysharedlist.core.Constants.TAG
import com.mitm.android.grocerysharedlist.domain.repository.RepositoryGrocery
import com.mitm.android.grocerysharedlist.model.Item
import com.mitm.android.grocerysharedlist.presentation.ui.items_list.composable.InputItemState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class ItemsListViewModel @Inject constructor(
    private val repository: RepositoryGrocery,
    private val appSettings: AppSettings
): ViewModel() {

    private val _state = mutableStateOf<ListStates>(ListStates())
    val state: State<ListStates> = _state

    private val _itemContent = mutableStateOf(InputItemState(
        hint = "Enter some item"
    ))
    val itemContent: State<InputItemState> = _itemContent

    private var cacheList: List<Item> = emptyList()

    private var jobSubscriber: Job? = null

    init {
        subscribeToRealtimeUpdates()
    }


    fun counterPlus(){
        _state.value = state.value.copy(
            counter = state.value.counter + 1
        )
    }
    fun counterMinus(){
        _state.value = state.value.copy(
            counter = if (state.value.counter > 0 ) state.value.counter - 1 else 0
        )
    }

    fun onEvent(event: ListEvent){
        when (event){
            is ListEvent.EditInput -> {
                _state.value = state.value.copy(
                    inputItem = event.inputText
                )
            }

            is ListEvent.InsertItem -> {
                val insertList = _state.value.list.toMutableList()
                val item = Item(_state.value.counter, _state.value.inputItem)
                insertList.add(0,item)
                if (insertList.size < 100) repository.insertItem(insertList)

                _state.value = state.value.copy(
                    inputItem = "",
                    counter = 1
                )
            }

            is ListEvent.ChangeContentFocus -> {
                _itemContent.value = _itemContent.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            _itemContent.value.text.isBlank()
                )
            }


            is ListEvent.UpdateListInRoom -> {
                _state.value = state.value.copy(
                    loading = !state.value.loading
                )
            }

            is ListEvent.UpdateRoom -> {
                jobSubscriber?.cancel()
                subscribeToRealtimeUpdates()
            }

            is ListEvent.DeleteAllItems -> {
                cacheList = state.value.list
                repository.clearList()
            }

            is ListEvent.RestoreList -> {
                repository.insertItem(cacheList)
            }

            is ListEvent.DeleteItem -> {
                val list = _state.value.list.toMutableList()
                list.remove(event.item)

                repository.removeItem(list)
            }
        }
    }





    @ExperimentalCoroutinesApi
    private fun subscribeToRealtimeUpdates(){
        jobSubscriber = viewModelScope.launch(Dispatchers.IO) {
            repository.subscribeToRealtimeUpdates().collect {
                _state.value = state.value.copy(
                    list = it.distinct(),
                    roomID = appSettings.getRoomID() ?: "RoomID is empty"
                )

                Log.d(TAG, "subscribeToRealtimeUpdates: $it")
            }
        }
    }

}