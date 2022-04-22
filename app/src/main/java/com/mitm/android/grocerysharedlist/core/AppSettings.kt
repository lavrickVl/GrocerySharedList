package com.mitm.android.grocerysharedlist.core

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore(
    name = "settings"
)

class AppSettings @Inject constructor(private val context: Context) {

    private val roomKey = stringPreferencesKey("ROOM_KEY")
    private var _roomId: String? = null


    private val job = CoroutineScope(Dispatchers.IO)

    init {
        readOwnRoom()
    }

    private fun generateRoomID() = Date().hashCode().toString()

    private fun readOwnRoom() {
        job.launch {
            _roomId = context.dataStore.data.first()[roomKey]
        }
    }

    private fun writeOwnRoom(){
        job.launch {
            context.dataStore.edit { settings ->
                val id = _roomId ?: generateRoomID()
                settings[roomKey] = id
                _roomId = id
            }
        }
    }

    fun retrieveRoomID() = _roomId

}


data class UserPreferences(val showCompleted: Boolean)