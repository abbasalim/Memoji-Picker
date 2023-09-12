package ir.mowjapp.memojipicker

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun BottomCard(
    fabButtons: @Composable RowScope.() -> Unit,
    onClose: (() -> Unit)?,
    content: @Composable ColumnScope.() -> Unit
) {
    val maxWidth = 632.dp
    var enabled by rememberSaveable { mutableStateOf(false) }
    LaunchedEffect(key1 = Unit) {
        enabled = true
    }

    val alpha: Float by animateFloatAsState(
        if (enabled) 0.5f else 0.0f,
        animationSpec = tween(300, 300, LinearEasing), label = "alpha"
    )
    Column(
        Modifier
            .fillMaxSize()
            .clickable(indication = null,
                interactionSource = remember { MutableInteractionSource() } // This is mandatory
            ) { }
            .background(Color.Black.copy(alpha = alpha)),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        FabButtons({ fabButtons() }, onClose = onClose)
        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 14.dp),
            shape = RoundedCornerShape(topEnd = 14.dp, topStart = 14.dp),
            colors = CardDefaults.elevatedCardColors(
                containerColor = MaterialTheme.colorScheme.background.copy(alpha = 0.99f)
            ),
            modifier = Modifier.widthIn(0.dp, maxWidth)
        ) {
            Column(
                modifier = Modifier
                    .padding(12.dp),
//                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                content()
            }
        }

    }
}




@Composable
private fun FabButtons(fabButtons: @Composable RowScope.() -> Unit, onClose: (() -> Unit)?) {
    Row(
        Modifier
            .padding(7.dp)
//            .widthIn(0.dp, 632.dp),
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.End)
        ) {
            fabButtons()
            Spacer(modifier = Modifier.weight(1f))
            onClose?.let {
                FloatingActionButton(
                    onClick = { onClose() },
                ) {
                    Icon(
                        imageVector = Icons.Rounded.KeyboardArrowDown,
                        contentDescription = "بستن"
                    )
                }
            }


        }

    }
}