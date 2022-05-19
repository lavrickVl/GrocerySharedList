package com.mitm.android.grocerysharedlist.core

import androidx.datastore.preferences.core.stringPreferencesKey

object Constants {

    const val TAG = "myLog"

    const val ROOT = "GroceryLists"
    const val ROOM_KEY = "ROOM_KEY"
    const val HOME_ROOM_KEY = "HOME_ROOM_KEY"

    val PREF_ROOM_KEY = stringPreferencesKey(ROOM_KEY)
    val PREF_HOME_ROOM_KEY = stringPreferencesKey(HOME_ROOM_KEY)

}