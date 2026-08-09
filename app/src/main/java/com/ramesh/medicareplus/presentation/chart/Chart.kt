package com.ramesh.medicareplus.presentation.chart

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramesh.medicareplus.core.ui.theme.*

@Composable
fun StatisticsScreen(
    onBackClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .padding(horizontal = 20.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(50.dp))

        // Header
        Box(modifier = Modifier.fillMaxWidth()) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Secondary)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = TextPrimary,
                    modifier = Modifier.size(20.dp)
                )
            }

            Text(
                text = "Your Statistics",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Filters
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            FilterButton(text = "All Medicine", modifier = Modifier.weight(1f))
            FilterButton(text = "This Month", modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Progress Section
        SectionTitle(title = "Progress")
        Spacer(modifier = Modifier.height(16.dp))
        ProgressCard()

        Spacer(modifier = Modifier.height(32.dp))

        // Analysis Section
        SectionTitle(title = "Analysis")
        Spacer(modifier = Modifier.height(16.dp))
        AnalysisGrid()

        Spacer(modifier = Modifier.height(32.dp))

        // Achievement Section
        SectionTitle(title = "Achievement")
        Spacer(modifier = Modifier.height(16.dp))
        AchievementCard()
        
        Spacer(modifier = Modifier.height(100.dp))
    }
}

@Composable
fun FilterButton(text: String, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.height(48.dp),
        shape = RoundedCornerShape(12.dp),
        color = Surface
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = text, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
            Icon(imageVector = Icons.Default.ExpandMore, contentDescription = null, tint = TextSecondary)
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = TextPrimary
    )
}

@Composable
fun ProgressCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Surface)
    ) {
        Row(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Circular Progress Chart
            Box(contentAlignment = Alignment.Center) {
                Canvas(modifier = Modifier.size(140.dp)) {
                    val strokeWidth = 8.dp.toPx()
                    
                    // Background circles
                    drawCircle(color = Color(0xFFE0E0E0), style = Stroke(strokeWidth))
                    drawCircle(color = Color(0xFFE0E0E0), radius = size.minDimension / 2 - 20.dp.toPx(), style = Stroke(strokeWidth))
                    drawCircle(color = Color(0xFFE0E0E0), radius = size.minDimension / 2 - 40.dp.toPx(), style = Stroke(strokeWidth))

                    // Progress arcs
                    drawArc(
                        color = ChartGreen,
                        startAngle = -90f,
                        sweepAngle = 260f,
                        useCenter = false,
                        style = Stroke(strokeWidth, cap = StrokeCap.Round)
                    )
                    drawArc(
                        color = ChartBlue,
                        startAngle = -90f,
                        sweepAngle = 180f,
                        useCenter = false,
                        style = Stroke(strokeWidth, cap = StrokeCap.Round),
                        size = size.copy(width = size.width - 40.dp.toPx(), height = size.height - 40.dp.toPx()),
                        topLeft = center.copy(x = 20.dp.toPx(), y = 20.dp.toPx())
                    )
                    drawArc(
                        color = ChartOrange,
                        startAngle = -90f,
                        sweepAngle = 120f,
                        useCenter = false,
                        style = Stroke(strokeWidth, cap = StrokeCap.Round),
                        size = size.copy(width = size.width - 80.dp.toPx(), height = size.height - 80.dp.toPx()),
                        topLeft = center.copy(x = 40.dp.toPx(), y = 40.dp.toPx())
                    )
                }
                Text(text = "68%", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
            }

            // Legend
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                LegendItem(color = ChartGreen, text = "Week 01")
                LegendItem(color = ChartBlue, text = "Week 02")
                LegendItem(color = ChartOrange, text = "Week 03")
            }
        }
    }
}

@Composable
fun LegendItem(color: Color, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .clip(CircleShape)
                .background(color)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = text, fontSize = 14.sp, color = TextPrimary)
    }
}

@Composable
fun AnalysisGrid() {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            AnalysisCard(label = "Active", value = "03", modifier = Modifier.weight(1f))
            AnalysisCard(label = "Completed Task", value = "04", modifier = Modifier.weight(1f))
        }
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            AnalysisCard(label = "Incomplete", value = "05", modifier = Modifier.weight(1f))
            AnalysisCard(label = "Reward", value = "03", modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun AnalysisCard(label: String, value: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.height(80.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = label, fontSize = 12.sp, color = TextSecondary)
            Text(text = value, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
        }
    }
}

@Composable
fun AchievementCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Surface)
    ) {
        Row(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            AchievementItem(color = MedalPurple, label = "Level 01")
            AchievementItem(color = MedalGold, label = "Level 02")
            AchievementItem(color = MedalRed, label = "Level 03")
        }
    }
}

@Composable
fun AchievementItem(color: Color, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(contentAlignment = Alignment.Center) {
            // Medal Shape Placeholder
            Icon(
                imageVector = Icons.Default.Favorite, // Replace with medal icon if available
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(56.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = label, fontSize = 12.sp, color = TextSecondary)
    }
}

@Preview(showBackground = true)
@Composable
fun StatisticsScreenPreview() {
    MedicareplusTheme {
        StatisticsScreen()
    }
}
