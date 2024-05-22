package com.bright.listofmessages

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bright.listofmessages.data.getProgramData
import com.bright.listofmessages.model.Program
import com.bright.listofmessages.ui.theme.ListOfMessagesTheme

//This is the main activity
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            //Here we are using the List of Messages Theme
            ListOfMessagesTheme {
                //Setting the surface container to fill the screen using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //Calls the program function
                    Program(
                        getProgramData()
                    )
                }
            }
        }
    }
}

//@Composable allows a function to become a composable function, allowing it to be called within other composable functions.
@Composable
fun Program(programs: List<Program>) {
    //Compose uses columns and rows to align and display items. Columns align coming vertically while rows align horizontally
    //add some padding around the edge of the screen
    Column (Modifier.padding(4.dp)) {
        Text(
            text = "MIU Programs",
            style = MaterialTheme.typography.titleLarge,
        )
        Spacer(modifier = Modifier.height(15.dp))

        //A lazy column allows for only rendering of that is displayed on the screen at the time
        LazyColumn {
            //This function is like a foreach statement in C# or Java, where for each program, the ProgramCard is called.
            items(programs) {program -> ProgramCard(program)}
        }
    }
}

@Composable
fun ProgramCard(program: Program) {
    Row {
        Image(
            //We get the image from the drawable resources
            painter = painterResource(id = R.drawable.program),
            contentDescription = "person",
            modifier = Modifier
                .clip(CircleShape)
                .size(70.dp)
                .border(width = 2.dp, shape = CircleShape, color = Color.Black)
        )
        Spacer(modifier = Modifier.width(4.dp))

        //Creates a mutable state variable that trigger UI updates when its value changes
        var isExpanded by remember {
            mutableStateOf(false)
        }
        //When the user clicks on a description to view more of its contents, it is expanded and if clicked again it is closed
        //This acts like a toggle.
        Column(
            modifier = Modifier.clickable { isExpanded = !isExpanded }
        ) {
            Text(
                text = program.name,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleSmall
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = program.description,
                style = MaterialTheme.typography.bodyMedium,
                //When the description is expanded the max display lines increases to the maximum integer value to make sure all the test is shown, else it only shows the first line
                maxLines = if(isExpanded) Int.MAX_VALUE else 1
            )
        }
    }
    Spacer(modifier = Modifier.height(5.dp))
}

//This allowed of a preview of how the code will look without having to build and run it on the emulator
@Preview(showBackground = true)
@Composable
fun ProgramPreview() {
    ListOfMessagesTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Program(
                getProgramData()
            )
        }
    }
}