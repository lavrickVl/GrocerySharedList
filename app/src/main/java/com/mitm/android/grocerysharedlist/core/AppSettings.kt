package com.mitm.android.grocerysharedlist.core

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject


class AppSettings @Inject constructor(private val context: Context) {

    private var _roomId: String? = null

    fun updateRoomID(roomId: String) {
        _roomId = roomId
    }

    fun getRoomID() = _roomId


}

