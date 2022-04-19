package com.mitm.android.grocerysharedlist.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mitm.android.grocerysharedlist.presentation.theme.GrocerySharedListTheme
import dagger.hilt.android.AndroidEntryPoint

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
fun InputItem(hint: String = "Enter text...") {
    var text by remember {
        mutableStateOf(hint)
    }

    OutlinedTextField(value = text, onValueChange = {
        text = it
    },
        leadingIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Filled.Edit,
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
    InputItem()
}

