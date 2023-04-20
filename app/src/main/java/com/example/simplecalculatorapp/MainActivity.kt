package com.example.simplecalculatorapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.layout.BoxScopeInstance.align
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.materialIcon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Cyan
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
//import androidx.compose.ui.tooling.data.EmptyGroup.name
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.example.simplecalculatorapp.ui.theme.SimpleCalculatorAppTheme
import com.example.simplecalculatorapp.ui.theme.calc


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SimpleCalculatorAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    mainscreen()
                }
            }
        }
    }
}

@Composable
fun mainscreen(){

    Scaffold(topBar = { TopAppBar(title = {Text("Simple Calculator",color = androidx.compose.ui.graphics.Color.White)},backgroundColor = Color(0xFF1976D2)) },
        content = {  Content()
        })



}


@Composable
fun Content() {
    Column(modifier = Modifier.fillMaxSize() .wrapContentSize(Alignment.Center)) {
        var res by remember {
            mutableStateOf(0)
        }

        var input1 by remember { mutableStateOf("") }
        OutlinedTextField(value = input1 ,onValueChange = {input1 = it } , modifier = Modifier.padding(start = 20.dp) , label = {Text("Enter Value1")} )
        Spacer(modifier = Modifier.height(20.dp))
        var input2 by remember { mutableStateOf("") }


        OutlinedTextField(value = input2 ,onValueChange = {input2 = it } , modifier = Modifier.padding(start = 20.dp) , label = {Text("Enter Value2")} )



        var mexpanded by remember { mutableStateOf(false) }
        var op = listOf("Addition","Subtraction","Mutliplication","Division")
        var menutext by remember { mutableStateOf("") }
        var fieldsize by remember { mutableStateOf(Size.Zero) }
        var option by remember {
            mutableStateOf("")
        }
        val icon = if(mexpanded)
            Icons.Filled.KeyboardArrowUp
        else
            Icons.Filled.KeyboardArrowDown

        Column(Modifier.padding(20.dp)){
            OutlinedTextField(value = menutext, onValueChange =  { menutext = it},
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->

                        fieldsize = coordinates.size.toSize()
                    },
                label = {Text("Select Operation")},
                trailingIcon = {
                    Icon(icon,"Contentdesc",
                        Modifier.clickable { mexpanded = !mexpanded }
                    )
                }

            )
            DropdownMenu(expanded = mexpanded, onDismissRequest = { mexpanded = false},
                modifier = Modifier. width(with(LocalDensity.current){fieldsize.width.toDp()})
            ) {
                op.forEach { label ->
                    DropdownMenuItem(onClick = {
                        menutext = label
                        mexpanded = false
                        option = label
                    }) {
                        Text(text = label)

                    }
                }
            }
        }






        Row(modifier = Modifier.padding(end = 20.dp)
            .fillMaxWidth()
            ,

            horizontalArrangement = Arrangement.End
        ) {
            Button(onClick = {
                var Calc = calc()
//                if(Integer.parseInt(input2) == 0 && option=="Division" )
//                    res = "Error, input should not be zero"
               res  = when(option){
                   "Addition" -> Calc.add( Integer.parseInt(input1),Integer.parseInt(input2))
                    "Subtraction" -> Calc.subtract(Integer.parseInt(input1),Integer.parseInt(input2))
                   "Mutliplication" -> Calc.multiply(Integer.parseInt(input1),Integer.parseInt(input2))
                   else -> Calc.divison(Integer.parseInt(input1),Integer.parseInt(input2))
               }
             }

            ) {
                Text(text = "Calculate")
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "Result: $res", modifier = Modifier.padding(start = 20.dp)  , fontWeight = FontWeight.Bold , fontSize = 24.sp)

    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SimpleCalculatorAppTheme {
        mainscreen()
    }
}