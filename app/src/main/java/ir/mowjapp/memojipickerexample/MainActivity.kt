package ir.mowjapp.memojipickerexample

import android.graphics.Bitmap
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.tooling.preview.Preview
import ir.mowjapp.memojipicker.MemojiPicker
import ir.mowjapp.memojipickerexample.ui.theme.MemojiPickerExampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MemojiPickerExampleTheme {
                val showPicker = remember {mutableStateOf(false)}
                val selectedImage = remember {mutableStateOf<Bitmap?>(null)}
                val selectedCircleImage = remember {mutableStateOf<ImageBitmap?>(null)}
                val selectedColor = remember {mutableStateOf<Color?>(null)}

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        Button(onClick = {
                            showPicker.value = true
                        }) {
                            Text(text = "click me")
                        }
                        selectedCircleImage.value?.let {b->
                            Image(
                                bitmap = b,
                            "selected Image"
                            )
                        }
                        selectedImage.value?.let {b->
                            Image(
                                bitmap = b.asImageBitmap(),
                            "selected Image"
                            )
                        }
                        selectedColor.value?.let {c->
                            Text(text =c.toString())
                        }

                    }

                }
                if (showPicker.value) {
                    MemojiPicker(onDismiss = { showPicker.value = false }) {circleMemoji, memoji,color ->
                        showPicker.value = false
                        selectedCircleImage.value = circleMemoji
                        selectedImage.value = memoji
                        selectedColor.value = color
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MemojiPickerExampleTheme {
        Greeting("Android")
    }
}