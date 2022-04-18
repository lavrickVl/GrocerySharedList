package com.mitm.android.grocerysharedlist.presentation

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.mitm.android.grocerysharedlist.Counter
import com.mitm.android.grocerysharedlist.ItemItem
import com.mitm.android.grocerysharedlist.repository.RepositoryGrocery
import com.mitm.android.grocerysharedlist.presentation.theme.GrocerySharedListTheme
import com.mitm.android.grocerysharedlist.core.Constants.TAG
import com.mitm.android.grocerysharedlist.model.Item
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


//                val db = Firebase.firestore
//                db.collection("GroceryLists")
//                    .get()
//                    .addOnSuccessListener { result ->
//                        for (document in result) {
//                            Log.d(TAG, "${document.id} => ${document.data}")
//                        }
//                    }
//                    .addOnFailureListener { exception ->
//                        Log.w(TAG, "Error getting documents.", exception)
//                    }
//
//                val user = hashMapOf(
//                    "first" to "Ada",
//                    "last" to "Lovelace",
//                    "last" to "Lovelace",
//                    "last" to "Lovelace",
//                    "last" to "Lovelace",
//                    "last" to "Lovelace",
//                    "last" to "Lovelace",
//                    "born" to 1815
//                )
//
//// Add a new document with a generated ID
//                db.document("users/SMM3S7rl79DFlyzDWQ3T").set(user)
//                    .addOnSuccessListener { documentReference ->
//                        Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference}")
//                    }
//                    .addOnFailureListener { e ->
//                        Log.w(TAG, "Error adding document", e)
//                    }
//
//
//
//                val alovelaceDocumentRef = db.collection("users").document("SMM3S7rl79DFlyzDWQ3T")
//                alovelaceDocumentRef.get().addOnSuccessListener {
//                    Log.d(TAG, "get document ${it.data}")
//                    Toast.makeText(applicationContext,it.data.toString() , Toast.LENGTH_SHORT).show()
//
//                }.addOnFailureListener { e ->
//                    Log.d(TAG, "Error get document", e)
//                }
//
//
//

    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GrocerySharedListTheme {
                Navigation()
            }
        }
    }
}


@Composable
fun InputField(hint: String = "Enter text...", title: String = "Title") {
    var text by remember {
        mutableStateOf(hint)
    }

    OutlinedTextField(value = text, onValueChange = {
        text = it
    },
        label = { Text(title, Modifier.padding(2.dp)) },
        leadingIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Filled.Email,
                    contentDescription = "email"
                )
            }
        },
        trailingIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "check"
                )
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Counter()
}

