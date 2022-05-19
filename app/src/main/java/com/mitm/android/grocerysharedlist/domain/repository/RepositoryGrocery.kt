package com.mitm.android.grocerysharedlist.domain.repository

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.toObject
import com.mitm.android.grocerysharedlist.core.AppSettings
import com.mitm.android.grocerysharedlist.core.Constants
import com.mitm.android.grocerysharedlist.core.Constants.PREF_HOME_ROOM_KEY
import com.mitm.android.grocerysharedlist.core.Constants.PREF_ROOM_KEY
import com.mitm.android.grocerysharedlist.core.Constants.ROOT
import com.mitm.android.grocerysharedlist.core.Constants.TAG
import com.mitm.android.grocerysharedlist.model.Item
import com.mitm.android.grocerysharedlist.model.remote.ListFirestore
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.*
import java.util.*
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine


private val Context.dataStore by preferencesDataStore("settings")

class RepositoryGrocery @Inject constructor(
    private val remoteDB: FirebaseFirestore,
    private val appSettings: AppSettings,
    private val context: Context
) {
    private var path = remoteDB.collection(ROOT)
        .document(appSettings.getRoomID() ?: "0")

    private lateinit var callback: ListenerRegistration
    private var job: Job? = null


    private suspend fun generateRoomID() = Date().hashCode().toString().apply {
        appSettings.updateRoomID(this)
        context.dataStore.edit {
            it[PREF_ROOM_KEY] = this
        }
        context.dataStore.edit {
            it[PREF_HOME_ROOM_KEY] = this
        }
    }

    suspend fun setHomeRoomID(): Boolean {
        val homeID = context.dataStore.data.first()[PREF_HOME_ROOM_KEY] ?: generateRoomID()

        appSettings.updateRoomID(homeID)
        context.dataStore.edit {
            it[PREF_ROOM_KEY] = homeID
        }

        return true
    }

    private fun updatePath() {
        path = remoteDB
            .collection(ROOT)
            .document(appSettings.getRoomID() ?: "0")

        Log.d(Constants.TAG, "path: $path")
    }



    suspend fun checkPathIsExists(roomId: String): Boolean {
        return suspendCoroutine { cont ->
            remoteDB.collection(ROOT)
                .document(roomId).get().addOnSuccessListener {
                    cont.resumeWith(Result.success(it.exists()))
                }.addOnCanceledListener {
                    cont.resumeWith(Result.success(false))
                }.addOnFailureListener {
                    cont.resumeWith(Result.success(false))
                }
        }
    }

    @ExperimentalCoroutinesApi
    fun updatePath(roomId: String) {
        callback.remove()

        job = CoroutineScope(Dispatchers.IO).launch {
            context.dataStore.edit {
                it[PREF_ROOM_KEY] = roomId
            }
        }

        job?.start()

        appSettings.updateRoomID(roomId)
        path = remoteDB.collection(ROOT)
            .document(roomId)

        Log.d(Constants.TAG, "path: $path")
        Log.d(Constants.TAG, "job?.isCompleted\n: $job")

        subscribeToRealtimeUpdates()

    }


    @ExperimentalCoroutinesApi
    fun subscribeToRealtimeUpdates(): Flow<List<Item>> = callbackFlow {
        if (appSettings.getRoomID() == null) {
            val deferred = async { context.dataStore.data.first()[Constants.PREF_ROOM_KEY] }
            val roomIdDataStore = deferred.await()
            appSettings.updateRoomID(roomIdDataStore ?: generateRoomID())
            updatePath()
        }

        callback = path.addSnapshotListener { value, error ->
            error?.let {
                Log.d(TAG, "subscribeToRealtimeUpdates err: ${error.message}")
            }

            if (value != null) {
                trySendBlocking(value.toObject<ListFirestore>()?.list ?: emptyList())
            }
        }


        awaitClose {}
    }


    fun insertItem(itemList: List<Item>) {
        path.set(ListFirestore(list = itemList))
            .addOnSuccessListener {
                Log.d(TAG, "insertItem: $itemList")
            }
            .addOnFailureListener {
                Log.d(TAG, "insertItem failure: $itemList")
            }
    }

    fun removeItem(itemList: List<Item>) {
        path.set(ListFirestore(list = itemList))
            .addOnSuccessListener {
                Log.d(TAG, "removeItem: $itemList")
            }
            .addOnFailureListener {
                Log.d(TAG, "removeItem failure: $itemList")
            }
    }

    fun clearList() {
        path.set(ListFirestore(list = emptyList()))
            .addOnSuccessListener {
                Log.d(TAG, "clearList emptyList")
            }
            .addOnFailureListener {
                Log.d(TAG, "clearList emptyList failure")
            }
    }


    fun getItemsList(): Flow<List<Item>> = flow {
        updatePath()

//        if (appSettings.retrieveRoomID() == null) {
//            return@flow emit(emptyList<Item>())
//        }

        val deferred = CompletableDeferred<DocumentSnapshot>()

        path.get()
            .addOnSuccessListener {
                deferred.complete(it)
                Log.d(TAG, "result deferred data: ${it.data}")
            }
            .addOnFailureListener {
                Log.d(TAG, "getItemsList err: ${it.message}")
            }


        val fire = deferred.await().toObject<ListFirestore>()
        emit(fire?.list ?: emptyList<Item>())
    }

}