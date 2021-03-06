package com.zfang.composedemo

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zfang.composedemo.login.LoginActivity
import com.zfang.composedemo.viewmodel.HelloViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            HelloScreen("Jon")
            ExpandScreen()
        }
    }



    @Composable
    fun NewsStory() {
        MaterialTheme() {
            val typography = MaterialTheme.typography
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Image(painter = painterResource(id = R.drawable.header),
                    contentDescription = null,
                    modifier = Modifier
                        .height(180.dp)
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(4.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "A day wandering through the sandhills " +
                        "in Shark Fin Cove, and a few of the " +
                        "sights I saw", style = typography.h6,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis)
                Text(text = "Davenport, California", style = typography.body2)
                Text(text = "December 2018", style = typography.body2)
            }
        }
    }
//    @Preview
    @Composable
    fun PreviewGreeting() {
        ExpandScreen()
    }

    @Preview(device = Devices.NEXUS_5)
    @Composable
    fun PreviewContent() {
        ExpandScreen()
    }

    @Composable
    fun HelloScreen2(helloViewModel: HelloViewModel) {
        val a = ""
//        val name: String by helloViewModel.name.observeAsState(a)
        HelloContent(name = "name", onNameChange = { helloViewModel.onNameChange(it) })
    }

    @Composable
    fun ExpandScreen() {
        Column() {
            ExpandingCard(title = "Good example", body = "## This file is automatically generated by Android Studio.\n" +
                    "# Do not modify this file -- YOUR CHANGES WILL BE ERASED!\n" +
                    "#\n" +
                    "# This file should *NOT* be checked into Version Control Systems,\n" +
                    "# as it contains information specific to your local configuration.\n" +
                    "#\n" +
                    "# Location of the SDK. This is only used by Gradle.\n" +
                    "# For customization when using a Version Control System, please read the\n" +
                    "# header note.")
            Button(onClick = { startActivity(Intent(this@MainActivity, LoginActivity::class.java)) },
                shape = MaterialTheme.shapes.small,
            ) {
                Text(text = "LoginScreen")
            }
        }
    }

//    @Parcelize
    data class City(val name: String, val country: String)

    @Composable
    fun MyExample() {
        val a = ""
        var selected by rememberSaveable{ mutableStateOf(City("a", "b")) }
        var str by rememberSaveable{ mutableStateOf(null) }

        val nameKey = "Name"
        val countryKey = "Country"
        val saver = mapSaver(
            save = { mapOf(nameKey to it.name, nameKey to it.country)},
            restore = {City(it[nameKey] as String, it[countryKey] as String)}
        )

//        var selectedCity = rememberSaveable(saver = saver) { mutableStateOf("null")}
    }

    @Composable
    fun ExpandingCard2(title: String, body: String) {
        var expanded by remember{ mutableStateOf(false)}
        ExpandingCard2(
            title = title,
            body = body,
            expanded = expanded,
            onExpand = { expanded = true},
            onCollapse = { expanded = false}
        )
    }

    @Composable
    fun ExpandingCard2(
        title: String,
        body: String,
        expanded: Boolean,
        onExpand: () -> Unit,
        onCollapse: () -> Unit
    ) {
        Card() {
            Column(
                Modifier
                    .width(280.dp)
                    .animateContentSize()
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp)
            ) {
                Text(text = title)
                if (expanded) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = body)
                    IconButton(onClick = onCollapse, Modifier.fillMaxWidth()) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = "Expand less"
                        )
                    }
                } else {
                    IconButton(onClick = onExpand, modifier = Modifier.fillMaxWidth()) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "Expand more"
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun ExpandingCard(title: String, body: String) {
        var expanded by remember{ mutableStateOf(false) }
        Card {
            Column (
                Modifier
                    .width(280.dp)
                    .animateContentSize()
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp)
            ) {
                Text(text = title)
                if (expanded) {
                    Text(text = body, Modifier.padding(top = 8.dp))
                    IconButton(onClick = { expanded = false },
                    modifier = Modifier.fillMaxWidth()) {
                        Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = "Expand less")
                    }
                } else {
                    IconButton(onClick = { expanded = true }, modifier = Modifier.fillMaxWidth()) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "Expand more"
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun FancyText(text: String) {
        val formattedText = remember(text) {"A"}
    }
    @Composable
    fun HelloScreen(defaultName: String) {
        var name by rememberSaveable {  mutableStateOf(defaultName)  }
        HelloContent(name = name, onNameChange = { name = it })
    }

    @Composable
    fun HelloContent(name: String, onNameChange: (String) -> Unit) {
        Column(modifier = Modifier.padding(16.dp)) {
            if (!name.isEmpty()) {
                Text(
                    text = "Hello, $name",
                    modifier = Modifier.padding(bottom = 8.dp),
                    style = MaterialTheme.typography.h5
                )
            }

            OutlinedTextField(
                value = name,
                onValueChange = { onNameChange(it) },
                label = { Text(text = "Input your name")}
            )
        }
    }
}