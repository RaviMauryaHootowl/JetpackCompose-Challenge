/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.example.androiddevchallenge.ui.theme.MyTheme

/**
 * @property index
 * @property name
 * @property pic
 * @property location
 * @property desc
 */
data class Dogs(
    val index: Int,
    val name: String,
    val pic: Int,
    val location: String,
    val desc: String
)
val dogs = listOf<Dogs>(
    Dogs(
        0, "Dexter", R.drawable.img1, "Mumbai",
        "The Afador is a mixed breed dog–a cross between the Afghan Hound"
    ),
    Dogs(
        1, "Jim", R.drawable.img2, "Pune",
        "The Akita Chow is a mixed breed dog–a cross between the Akita"
    ),
    Dogs(
        2, "Tom", R.drawable.img3, "Kolkata",
        "The Aussiepom is a mixed breed dog — a cross between the Australian"
    ),
    Dogs(
        3, "Peter", R.drawable.img4, "LA",
        "The Australian Retriever is a mixed breed dog–a cross between the Australian"
    ),
    Dogs(4, "Jerry", R.drawable.img5, "Mumbai", "Basset Retrievers are generally considered to be healthy dogs"),
    Dogs(
        5, "Sans", R.drawable.img6, "California",
        "The Beaglier is a relatively new mixed breed, so there are few standards when it comes to size."
    ),
    Dogs(
        6, "Terry", R.drawable.img7, "Goa",
        "The Aussiepom is a mixed breed dog — a cross between the Australian Shepherd and Pomeranian dog breeds."
    ),
    Dogs(
        7, "Loopy", R.drawable.img8, "Texas",
        "The Australian Retriever is a mixed breed dog–a cross between the Australian Shepherd and Golden Retriever dog breeds."
    ),
    Dogs(8, "Cynthia", R.drawable.img9, "Mumbai", "The Aussiepom is a mixed breed dog — a cross between the Australian Shepherd and Pomeranian dog breeds. "),
    Dogs(9, "John", R.drawable.img6, "Paris", "The Beaglier is a relatively new mixed breed, so there are few standards when it comes to size. "),
)

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    appNavigation()
                }
            }
        }
    }

    @Composable
    fun appNavigation() {
        val navController = rememberNavController()

        NavHost(
            navController = navController, startDestination = "dogsListView",
            builder = {
                composable("dogsListView") { homeScreen(navController) }
                composable(
                    "detailsView/{dogIndex}",
                    arguments = listOf(
                        navArgument("dogIndex") { type = NavType.IntType }
                    )
                ) { arg ->
                    arg.arguments?.let { detailsView(dogIndex = it.getInt("dogIndex")) }
                }
            }
        )
    }
}

/**
 * @param navController
 */
@Composable
fun homeScreen(navController: NavHostController) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Doggy App") }) },
        content = {
            listOfDogs(navController = navController)
        }
    )
}

/**
 * @param dogIndex
 */
@Composable
fun detailsView(dogIndex: Int) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Doggy App") }) },
        content = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Image(
                    painter = painterResource(id = dogs[dogIndex].pic), contentDescription = "Doggy",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(dogs[dogIndex].name, modifier = Modifier.padding(start = 8.dp, top = 8.dp, end = 8.dp, bottom = 2.dp), fontSize = 26.sp, fontWeight = FontWeight.Medium)
                Text(dogs[dogIndex].location, modifier = Modifier.padding(start = 8.dp, bottom = 8.dp, end = 8.dp, top = 2.dp), fontSize = 22.sp, fontWeight = FontWeight.Normal)
                LazyColumn(
                    modifier = Modifier
                        .fillMaxHeight()
                ) {
                    items(
                        count = 1,
                        itemContent = { item ->
                            Text(dogs[dogIndex].desc, modifier = Modifier.padding(all = 8.dp), fontSize = 20.sp)
                        }
                    )
                }
            }
        }
    )
}

/**
 * @param dogIndex
 * @param navController
 */
fun navigateToDog(navController: NavHostController, dogIndex: Int) {
    navController.navigate(route = "detailsView/$dogIndex")
}

/**
 * @param navController
 */
@Composable
fun listOfDogs(navController: NavHostController) {
    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
            .padding(all = 12.dp)
    ) {
        items(
            items = dogs,
            itemContent = { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 8.dp)
                        .clickable {
                            navigateToDog(navController, item.index)
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = item.pic), contentDescription = "Doggy",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                    )
                    Text(item.name, modifier = Modifier.padding(start = 8.dp))
                }
            }
        )
    }
}

/**
 * @param name
 */
@Composable
fun greeting(name: String) {
    Text(text = "Hello $name!")
}
