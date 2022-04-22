package com.mitm.android.grocerysharedlist.domain.repository

import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.mitm.android.grocerysharedlist.core.AppSettings
import com.mitm.android.grocerysharedlist.core.Constants
import com.mitm.android.grocerysharedlist.core.Constants.ROOT
import com.mitm.android.grocerysharedlist.core.Constants.TAG
import com.mitm.android.grocerysharedlist.model.Item
import com.mitm.android.grocerysharedlist.model.remote.ListFirestore
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RepositoryGrocery @Inject constructor(
    private val remoteDB: FirebaseFirestore,
    private val appSettings: AppSettings,
) {
    private val path = remoteDB
        .collection(ROOT)
        .document(appSettings.retrieveRoomID()!!)

    fun getItemsList(): Flow<List<Item>> = flow {

        if (appSettings.retrieveRoomID() == null) {
            return@flow emit(emptyList<Item>())
        }

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


    @ExperimentalCoroutinesApi
    fun subscribeToRealtimeUpdates(): Flow<List<Item>> = callbackFlow {
        val callback = path.addSnapshotListener { value, error ->
            error?.let {
                Log.d(TAG, "subscribeToRealtimeUpdates err: ${error.message}")
            }

            if (value != null) {
                trySendBlocking(value.toObject<ListFirestore>()?.list ?: emptyList())
            }
        }

        awaitClose {}
    }



    fun getRoom() = appSettings.retrieveRoomID()

    fun setRoom(): Flow<List<Item>> = flow {



        appSettings.retrieveRoomID()
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

}