package com.ramesh.medicareplus.presentation.splash

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.scaleIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds
import com.ramesh.medicareplus.core.ui.theme.Primary

@Composable
fun SplashScreen(
    onFinished: () -> Unit = {}
) {
    val circleOffset = remember { Animatable(-900f) }
    val rippleRadius = remember { Animatable(0f) }
    var backgroundChanged by remember { mutableStateOf(false) }
    var showText by remember { mutableStateOf(false) }
    val bgColor by animateColorAsState(if (backgroundChanged) Primary else Color.White, label = "")

    LaunchedEffect(Unit) {
        circleOffset.animateTo(
            0f,
            animationSpec = tween(
                durationMillis = 700,
                easing = FastOutSlowInEasing
            )
        )
        backgroundChanged = true
        rippleRadius.animateTo(
            2200f,
            animationSpec = tween(
                durationMillis = 900,
                easing = LinearOutSlowInEasing
            )
        )
        showText = true
        delay(2500.milliseconds)
        onFinished()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bgColor),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            if (backgroundChanged) {
                drawCircle(
                    color = Primary,
                    radius = rippleRadius.value,
                    center = center
                )
            }

            drawCircle(
                color = Primary,
                radius = 80.dp.toPx(),
                center = Offset(
                    center.x,
                    center.y + circleOffset.value
                )
            )
        }

        AnimatedVisibility(
            modifier = Modifier.offset(y = 0.dp),
            visible = showText,
            enter = fadeIn(
                tween(500)
            ) + scaleIn(
                initialScale = .8f
            )
        ) {
            Column() {
                TypeWriterText(
                    text = "MediCarePlus"
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Your Health, Our Reminder.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White,
                    fontWeight = FontWeight.W700,
                    fontFamily = FontFamily.Cursive,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
@Composable
fun TypeWriterText(
    text: String
) {
    var visibleText by remember {
        mutableStateOf("")
    }

    LaunchedEffect(text) {
        visibleText = ""

        text.forEach {
            visibleText += it

            delay(50.milliseconds)
        }
    }

    Text(
        modifier = Modifier.fillMaxWidth(),
        text = visibleText,
        style = MaterialTheme.typography.displayMedium,
        color = Color.White,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        fontFamily = FontFamily.Cursive
    )
}
@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun SplashPreview() {
    MaterialTheme {
        SplashScreen()
    }
}
