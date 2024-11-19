package mx.itesm.nativeexploration

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import mx.itesm.nativeexploration.ui.theme.NativeExplorationTheme

// things to import in order to use state variables
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

// even if you are using compose you still are required to have a class
// and your compose based UI class must extend activity
class ComposeActivity : ComponentActivity() {

    // compose activity still has the same methods from the lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NativeExplorationTheme {
                Surface (
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    InputTest(activity = this)
                }
            }
        }
    }

    // SUPER IMPORTANT
    // SCOPE - composables are functions and can be defined within the scope of a class
    // or outside

    // within the class they have access to attributes / behaviors of the class itself
    // outside they don't

    // if defined within a class they can be restricted
    @Composable
    fun TheButton() {
        // composables can have only arguments
        // OR work as containers
        Button(
            onClick = {
                // whatever logic works on views activities will also work here
                // the context type:
                // - an object that can interact with native behavior in the OS
                // - an activity is also a context object
                Toast.makeText(this, "TOAST FROM A BUTTON", Toast.LENGTH_SHORT).show()
            }
        ) {
            Text( text = "SAY HI" )
        }
    }
}

// an example of a composable that contains more
@Composable
fun Example1(activity : Activity? = null) {

    // things to do here
    // something that contains our elements
    // to to add several composables into a single one we need a container
    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "THIS IS A TEXT")
        Text(text = "THIS IS ANOTHER TEXT")
        Image(
            painter = painterResource(id = R.drawable.puppy1),
            contentDescription = "a cute puppy"
        )
        HiButton(activity = activity)
    }
}

// INPUT TEST / STATE VARIABLES
@Composable
fun InputTest(activity: Activity? = null) {

    // state variables - internal variables of a composable
    // that can trigger an event if changed

    // event - something that happens that triggers a recompose
    // recompose - redraw

    var name by remember { mutableStateOf("doggy") }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("The doggy's name is: $name")
        TextField(
            value = name,
            onValueChange = {
                // "it" is the name of the argument received
                // when the name is not specified
                name = it
            }
        )
        Button(
            onClick = {
                Toast.makeText(activity, "HI $name", Toast.LENGTH_SHORT).show()
            }
        ) {
            Text(text = "SAY HI TO THE DOGGY")
        }
    }
}

// doing a new composable
// speaking of scope: if you declare a composable outside of a class
// you will not have access to its attributes / behaviors

// ? - nullable
// a type that can have a null object
@Composable
fun HiButton(activity: Activity? = null){
    Button( onClick = {
        Toast.makeText(activity, "HI FROM THE OUTSIDE!", Toast.LENGTH_SHORT).show()
    } ) {
        Text(" SAY HI FROM OUTSIDE THE CLASS")
    }
}

// UIs in compose are made up of building blocks called composables
// to declare a composable we need to:
// - declare a function that returns a composable
// - a @composable annotation

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

// you can have a special composable called preview
// that will only work on android studio

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NativeExplorationTheme {
        Greeting("Android")
    }
}