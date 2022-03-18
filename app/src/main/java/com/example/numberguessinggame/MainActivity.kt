package com.example.numberguessinggame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.TextFieldValue
import kotlin.random.Random.Default.nextInt


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            game()
        }
    }
}


var random = nextInt(1,1000).toString()
var count = 0
var number = ""

@Composable
fun game() {

    var check = remember { mutableStateOf(false) }
    var x = remember { mutableStateOf("Try to guess the number \n From 1 - 1000 \n\n") }
    var Input = remember { mutableStateOf(TextFieldValue("")) }

    val checkAnswer = {
        number = Input.value.text

        if (number.isEmpty()) {
            x.value = "Try to guess the number \n" +
                    " From 1 - 1000 \n" +
                    "Please Fill Your Number \n"
        }
        else {
            try {
                number.toInt()
                when {
                    number == random -> {
                        check.value = true
                        x.value = " Congratulation \n Number is $random \n You Use $count Times\n"
                    }
                    random > number -> {
                        count++
                        x.value = "Try to guess the number \n" +
                                " From 1 - 1000 \n" +
                                "Last Number is $number \n " +
                                ">> Hint : It's Higher <<  "
                    }
                    random < number -> {
                        count++
                        x.value = "Try to guess the number \n" +
                                " From 1 - 1000 \n" +
                                "Last Number is $number \n " +
                                ">> Hint : It's Lower << "
                    }
                }
            } catch (e: Exception) {
                x.value = "Try to guess the number \n" +
                        " From 1 - 1000 \n" +
                        "Please Fill Your Number \n"
            }
        }
    }

    val reset = {
        random = Random().nextInt(1000).toString()
        count = 0
        x.value = "Try to guess the number \n From 1 - 1000 \n"
        number = ""
        check.value = false
    }

    Column(
        modifier = Modifier.fillMaxHeight().padding(30.dp),
//        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "${x.value} ",
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(32.dp)
                .fillMaxWidth()
        )
        TextField(
            value = Input.value,
            onValueChange = {
                Input.value = it
            },
            label = { Text(text = " Your Number") },
            placeholder = {
                Text(
                    text = stringResource(
                        id = R.string.hint,
                    )
                )
            },
            modifier = Modifier
                .padding(32.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words
            )
        )

        if (!check.value) {
            button(name = "CHECK", onClick = { checkAnswer() })
        } else {
            button(name = "PLAY AGAIN", onClick = { reset() })
        }

    }
}



@Composable
fun button (name : String, onClick: () -> Unit){
    Button(onClick = onClick)
    {
        Text(
            text = "$name",
        )
    }
}
