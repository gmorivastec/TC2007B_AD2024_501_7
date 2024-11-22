package mx.itesm.nativeexploration

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var textInput : EditText
    private lateinit var button1 : Button
    private lateinit var button2 : Button

    // lifecycle
    // https://developer.android.com/guide/components/activities/activity-lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // there are several strategies to deal with UI from code in views
        // we are going to use the most basic one

        // how to get a reference to a widget on the running activity
        // VERY IMPORTANT - findviewbyid MIGHT fail
        // reason: it has no compilation time verification for the existence of the widget
        textInput = findViewById(R.id.editTextText)
        button1 = findViewById(R.id.button)
        button2 = findViewById(R.id.button2)

        button1.text = "SAY HELLO"
        button2.text = "GO TO COMPOSE ACTIVITY"

        button2.setOnClickListener {

            // change activity
            // in android we request the new activity, we don't order
            // we need an object that contains the request
            // the type of this object is intent
            // new activities can be started with an explicit type
            // OR with an action we want to complete
            var intent = Intent(this, ComposeActivity::class.java)
            startActivity(intent)

        }

        // notes on variable declaration in kotlin
        // two possibilities for our variables depending on their mutability
        // let's start with mutable variables

        // how to deal with types in kotlin when working with variables
        // kotlin is a strongly typed language
        // option 1 - type can be inferred
        var var1 = "string"
        // option 2 - explicitely specify the type in the declaration
        var var2 : String

        var1 = "SOME OTHER STRING"
        var2 = "I CAN ALSO CHANGE!"

        // immutable variables
        // assigned once, value cannot change
        val var3 = "HI GUYS I'M A CONSTANT VALUE NICE TO MEET YOU"
        //var3 = "some other value!!"
    }

    // 2 ways to add logic to buttons
    // 1 - directly subscribe a listener (we'll do this later)
    // 2 - define a function that can listen for a button click

    // if your function will be bound to a UI event it must receive a View
    fun sayHi(view : View) {

        // the view we receive is a reference to the widget that invoked this function
        // let's do a toast!

        // factory method!
        Toast.makeText(this, "HEY MY FRIEND: ${textInput.text}", Toast.LENGTH_LONG).show()

        // logs and log levels
        Log.i("LOGS", "INFO")
        Log.d("LOGS", "DEBUG")
        Log.w("LOGS", "WARNING")
        Log.e("LOGS", "ERROR")
        Log.wtf("LOGS", "WHAT A TERRIBLE FAILURE!")
    }

    fun gotoComposeNavigation(view : View) {
        var intent = Intent(this, ComposeNavigationActivity::class.java)
        startActivity(intent)
    }
}