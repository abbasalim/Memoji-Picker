package ir.mowjapp.memojipicker

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import dev.shreyaspatil.capturable.Capturable
import dev.shreyaspatil.capturable.controller.rememberCaptureController


@Composable
fun MemojiPicker(onDismiss: () -> Unit, onSelect: (circleMemoji: ImageBitmap,memoji: Bitmap,color :Color) -> Unit) {
    val context = LocalContext.current
    val f = context.assets.list("memoji/laugh") ?: arrayOf()
    val colors = listOf(
        Color.Transparent,
        Color(android.graphics.Color.parseColor("#DADADA")),
        Color(android.graphics.Color.parseColor("#CBEDFD")),//blue
        Color(android.graphics.Color.parseColor("#FFC2BD")),//red
        Color(android.graphics.Color.parseColor("#FECBD7")),//pink
        Color(android.graphics.Color.parseColor("#FFEAAC")),//yellow
        Color(android.graphics.Color.parseColor("#DBD1FB")),//purple
        Color(android.graphics.Color.parseColor("#BEF0C8")),//green
    )
    val selectedColor = remember { mutableStateOf(Color.Transparent) }

    BottomCard(fabButtons = {}, onClose = onDismiss) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            items(items = colors) { color ->
                IconButton(
                    onClick = { selectedColor.value = color },
                    modifier = Modifier
                        .size(32.dp)
                        .background(shape = CircleShape, color = color)
                ) {
                    if (color == selectedColor.value) {
                        Icon(Icons.Rounded.Done, contentDescription = "selected Color")
                    }
                }
            }
        }


        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 80.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            items(items = f) { memoji ->
                val b = BitmapFactory.decodeStream(context.assets.open("memoji/laugh/$memoji"))
                val captureController = rememberCaptureController()
                Box(contentAlignment = Alignment.Center) {

                    Capturable(
                        controller = captureController,
                        onCaptured = { bitmap, error ->
                            if (bitmap != null) {
                                onSelect(bitmap,b,selectedColor.value)
                            }
                        }
                    ) {
                        Box(
                            modifier = Modifier
                                .size(width = 80.dp, height = 80.dp)
                                .background(shape = CircleShape, color = selectedColor.value)
                                .clickable { captureController.capture()},
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                bitmap = b.asImageBitmap(),
                                "assetsImage"
                            )
                        }

                    }



                }
            }
        }
    }

}