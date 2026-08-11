package com.ramesh.medicareplus.presentation.splash

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds
import com.ramesh.medicareplus.R
import com.ramesh.medicareplus.core.ui.theme.Primary

@Composable
fun SplashScreen(
    onFinished: () -> Unit = {}
) {
    val circleOffset = remember { Animatable(-1500f) }
    val rippleRadius = remember { Animatable(0f) }
    var backgroundChanged by remember { mutableStateOf(false) }
    var showText by remember { mutableStateOf(false) }
    val bgColor by animateColorAsState(if (backgroundChanged) Primary else Color.White, label = "")

    LaunchedEffect(Unit) {
        circleOffset.animateTo(
            0f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        )
        backgroundChanged = true
        showText = true

        rippleRadius.animateTo(
            2200f,
            animationSpec = tween(
                durationMillis = 900,
                easing = LinearOutSlowInEasing
            )
        )
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
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.animateContentSize()
        ) {
            Image(
                painter = painterResource(id = R.mipmap.ic_app_logo),
                contentDescription = "App Logo",
                colorFilter = ColorFilter.tint(Color.White),
                modifier = Modifier
                    .size(120.dp)
                    .offset {
                        IntOffset(x = 0, y = circleOffset.value.toInt())
                    }
            )

            Spacer(modifier = Modifier.height(24.dp))

            AnimatedVisibility(
                visible = showText,
                enter = fadeIn(
                    tween(500)
                ) + scaleIn(
                    initialScale = .8f
                )
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
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
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center
                    )
                }
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
