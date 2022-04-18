package com.mitm.android.grocerysharedlist.core

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AppSettings @Inject constructor(private val context: Context) {

    private var roomId: String? = null

    fun readOwnRoom() = roomId
    fun writeOwnRoom(_roomId: String?){
        roomId = _roomId
    }


}