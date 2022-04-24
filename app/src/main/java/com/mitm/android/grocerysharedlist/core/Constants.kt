package com.mitm.android.grocerysharedlist.core

import androidx.datastore.preferences.core.stringPreferencesKey

object Constants {

    const val TAG = "myLog"

    const val ROOT = "GroceryLists"
    const val ROOM_KEY = "ROOM_KEY"

    val PREF_ROOM_KEY = stringPreferencesKey(ROOM_KEY)

}