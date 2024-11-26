package mx.itesm.nativeexploration

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import mx.itesm.nativeexploration.ui.theme.NativeExplorationTheme

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil3.compose.AsyncImage
import androidx.compose.runtime.livedata.observeAsState

class ComposeNavigationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NativeExplorationTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Navigation(this)
                }
            }
        }
    }
}

@Composable
fun PuppyInterface(
    returnLogic : () -> Unit
) {
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AsyncImage(
            model = "https://www.warrenphotographic.co.uk/photography/sqrs/01145.jpg",
            contentDescription = "a doggy."
        )

        Button( onClick = returnLogic ) {
            Text("return")
        }
    }
}

@Composable
fun KittenInterface(
    returnLogic : () -> Unit,
    name : String? = "",
    weight : Float? = 1.0f
) {
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AsyncImage(
            model = "https://www.warrenphotographic.co.uk/photography/sqrs/01149.jpg",
            contentDescription = "a kitty."
        )

        Button( onClick = returnLogic ) {
            Text("return")
        }

        Text("name: $name")
        Text("weight: $weight")
    }
}

// the actual main menu
@Composable
fun MainMenu(
    kittenInterfaceButtonLogic : () -> Unit,
    puppyInterfaceButtonLogic : () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button( onClick = kittenInterfaceButtonLogic ) {
            Text("go to kitten UI")
        }

        Button( onClick = puppyInterfaceButtonLogic ) {
            Text("go to puppy UI")
        }
    }
}

// our main composable in charge of navigation
@Composable
fun Navigation(activity: Activity) {

    // to do navigation on compose we need an object called controller
    // the controller is the object that has the view exchange logic
    // (ui and data)

    val navController = rememberNavController()

    // host - structure where the interfaces live
    // the actual container
    NavHost(
        navController = navController,
        startDestination = "mainMenu"
    ){
        // inside the host we declare several composables
        // that will be made available for navigation
        composable("mainMenu") {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                MainMenu(
                    kittenInterfaceButtonLogic = {
                        navController.navigate("kittenInterface/Gatito/3.3")
                    },
                    puppyInterfaceButtonLogic = {
                        navController.navigate("puppyInterface")
                    }
                )

                // how to get values from save state handle
                val result = navController.currentBackStackEntry
                    ?.savedStateHandle
                    ?.getLiveData<String>("nombreDePerrito")
                    ?.observeAsState()

                // let - scope function
                // space where we run code in a particular context
                // in this case if the value is defined
                result?.value?.let { name ->
                    Text("the puppy is called: $name")

                    // if you wish you can purge the values
                    navController
                        .currentBackStackEntry
                        ?.savedStateHandle
                        ?.remove<String>("nombreDePerrito")
                }
            }
        }

        composable(
            "kittenInterface/{name}/{weight}",
            arguments = listOf(
                navArgument("name") { type = NavType.StringType},
                navArgument("weight") { type = NavType.FloatType}
            )
        ){ backStackEntry ->
            KittenInterface(
                returnLogic = {
                    navController.popBackStack()
                },
                name = backStackEntry.arguments?.getString("name"),
                weight = backStackEntry.arguments?.getFloat("weight")
            )
        }

        composable("puppyInterface") {
            PuppyInterface(
                returnLogic = {

                    navController
                        .previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("nombreDePerrito", "Firualis")
                    navController.popBackStack()
                }
            )
        }
    }
}

@Composable
fun Greeting2(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    NativeExplorationTheme {
        Greeting2("Android")
    }
}