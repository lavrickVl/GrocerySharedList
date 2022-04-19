package com.mitm.android.grocerysharedlist.presentation.ui.items_list

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mitm.android.grocerysharedlist.core.Constants.TAG
import com.mitm.android.grocerysharedlist.domain.repository.RepositoryGrocery
import com.mitm.android.grocerysharedlist.model.Item
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemsListViewModel @Inject constructor(
    private val repository: RepositoryGrocery
): ViewModel() {


    private val _state = mutableStateOf<ListStates>(ListStates())
    val state: State<ListStates> = _state


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

    fun counterReset(){
        _state.value = state.value.copy(
            counter = 0
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
                insertList.add(Item(_state.value.counter, _state.value.inputItem))
                repository.insertItem(insertList)
            }

            is ListEvent.UpdateListInRoom -> {
                _state.value = state.value.copy(
                    loading = !state.value.loading
                )

                getItemsList()
            }

            is ListEvent.DeleteAllItems -> {
                _state.value = state.value.copy(
                    list = emptyList()
                )
            }

            is ListEvent.DeleteItem -> {
                val list = _state.value.list.toMutableList()
                list.remove(event.item)
                Log.d(TAG, "onEvent temp list: $list")
                _state.value = state.value.copy(
                    list = list
                )
            }
        }
    }



    private fun getItemsList(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getItemsList().collect {
                _state.value = state.value.copy(
                    list = it
                )

                Log.d(TAG, "getItemsList: $it")
            }
        }
    }

    private fun subscribeToRealtimeUpdates(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.subscribeToRealtimeUpdates().collect {
                _state.value = state.value.copy(
                    list = it
                )

                Log.d(TAG, "subscribeToRealtimeUpdates: $it")
            }
        }
    }

}