package com.mitm.android.grocerysharedlist.repository

import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.mitm.android.grocerysharedlist.core.AppSettings
import com.mitm.android.grocerysharedlist.core.Constants.ROOT
import com.mitm.android.grocerysharedlist.core.Constants.TAG
import com.mitm.android.grocerysharedlist.model.Item
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class RepositoryGrocery @Inject constructor(
    private val remoteDB: FirebaseFirestore,
    private val appSettings: AppSettings,
) {


    fun getItemsList(): Flow<List<Item>> = flow {

        if (appSettings.readOwnRoom() == null) {
            return@flow emit(emptyList<Item>())
        }

        val deferred =  CompletableDeferred<DocumentSnapshot> ()

        val path = remoteDB
            .collection(ROOT)
            .document(appSettings.readOwnRoom()!!)
            .get()
            .addOnSuccessListener {
                deferred.complete(it)
                Log.d(TAG, "result deferred data: ${it.data}")
            }
            .addOnFailureListener {
                Log.d(TAG, "getItemsList err: ${it.message}")
            }

        val result = deferred.await()
        Log.d(TAG, "result deferred: $result")

    }


    fun getRoom() = appSettings.readOwnRoom()

    fun setRoom(): Flow<List<Item>> = flow {

        appSettings.readOwnRoom()
    }

    fun insertItem() {}
    fun removeItem() {}

}