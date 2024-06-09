package com.example.demo

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.demo.db.Place
import java.net.URLEncoder

object CircleParametersDefaults {

    private val defaultCircleRadius = 12.dp

    fun circleParameters(
        radius: Dp = defaultCircleRadius,
        stroke: StrokeParameters? = null
    ) = CircleParameters(
        radius,
        stroke
    )
}

object LineParametersDefaults {

    private val defaultStrokeWidth = 3.dp

    fun dashedLine(
        strokeWidth: Dp = defaultStrokeWidth,
        color: Color = Color.Black
    ): LineParameters {
        val brush = Brush.horizontalGradient(listOf(color, color))
        return LineParameters(strokeWidth, brush)
    }
}

data class LineParameters(
    val strokeWidth: Dp,
    val brush: Brush
)

data class StrokeParameters(
    val color: Color,
    val width: Dp
)

enum class TimelineNodePosition {
    MIDDLE,
    LAST
}

data class CircleParameters(
    val radius: Dp,
    val stroke: StrokeParameters? = null
)

@Composable
fun TimelineNode(
    position: TimelineNodePosition,
    circleParameters: CircleParameters,
    lineParameters: LineParameters? = null,
    contentStartOffset: Dp = 11.dp,
    spacer: Dp = 1.dp, // 32.dp
    content: @Composable BoxScope.(modifier: Modifier) -> Unit
) {
    Box(
        modifier = Modifier
            .wrapContentSize()
            .drawBehind {
                val startX = 12.dp
                val startXPx = startX.toPx()

                val circleRadiusInPx = circleParameters.radius.toPx()

                val dashEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)

                lineParameters?.let {
                    drawLine(
                        brush = it.brush,
                        start = Offset(x = startXPx, y = circleRadiusInPx * 2),
                        end = Offset(x = startXPx, y = circleRadiusInPx * 2 + 100f),
//                        end = Offset(x = startXPx, y = this.size.height),
                        strokeWidth = it.strokeWidth.toPx(),
                        pathEffect = dashEffect
                    )
                }

                drawCircle(
                    Color.Transparent,
                    circleRadiusInPx,
                    center = Offset(x = circleRadiusInPx, y = circleRadiusInPx)
                )

                circleParameters.stroke?.let { stroke ->
                    val strokeWidthInPx = stroke.width.toPx()
                    drawCircle(
                        color = stroke.color,
                        radius = circleRadiusInPx - strokeWidthInPx / 2,
                        center = Offset(x = startXPx, y = circleRadiusInPx),
                        style = Stroke(width = strokeWidthInPx)
                    )
                }
            }
    ) {
        content(
            Modifier
                .defaultMinSize(minHeight = circleParameters.radius * 2)
                .padding(
                    start = circleParameters.radius * 2 + contentStartOffset,
                    bottom = if (position != TimelineNodePosition.LAST) spacer else 0.dp
                )
        )
    }
}

@Composable
fun PlaceBubble(place: Place, modifier: Modifier) {
    Row(
        modifier = modifier
            .height(58.dp)
    ) {
        Text(text = place.name)
        if (place.time != null)
            Text(
                text = place.time,
                fontSize = 12.sp
            )
    }
}

@Composable
fun RouteBubble(place: Place, nextPlace: Place, modifier: Modifier) {
    val context = LocalContext.current

    Row(
        modifier = modifier
            .height(50.dp)
    ) {
        Spacer(modifier = Modifier.width(10.dp))
        place.route?.let {
            Text(
                text = it,
                fontSize = 12.sp
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = { handleDirectionsClick(origin = place.name, destination = nextPlace.name, context = context) },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFF9730)
            ),
            modifier = Modifier
                .width(70.dp)
                .height(28.dp),
            contentPadding = PaddingValues(2.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.outline_map_24),
                contentDescription = "more",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = "길찾기",
                color = Color.White,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
fun Timeline(places: List<Place>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        places.forEachIndexed { index, place ->
            if (index == places.size-1)
                TimelineNode(
                    TimelineNodePosition.LAST,
                    circleParameters = CircleParametersDefaults.circleParameters(
                        radius = 12.dp,
                        stroke = StrokeParameters(color = Color(0xFFFF9730), width = 2.dp)
                    )
                ) { modifier -> PlaceBubble(place, modifier) }
            else {
                TimelineNode(
                    position = TimelineNodePosition.MIDDLE,
                    circleParameters = CircleParametersDefaults.circleParameters(
                        radius = 12.dp,
                        stroke = StrokeParameters(color = Color(0xFFFF9730), width = 2.dp)
                    ),
                    lineParameters = LineParametersDefaults.dashedLine()
                ) { modifier -> PlaceBubble(place, modifier) }
                TimelineNode(
                    position = TimelineNodePosition.MIDDLE,
                    circleParameters = CircleParametersDefaults.circleParameters(
                        radius = 8.dp,
                        stroke = StrokeParameters(color = Color(0xFF000000), width = 1.dp)
                    ),
                    lineParameters = LineParametersDefaults.dashedLine()
                ) { modifier -> RouteBubble(place, places[index+1], modifier) }
            }
        }
    }
}

fun handleDirectionsClick(origin: String, destination: String, context: Context) {
    // 구글 지도 길찾기 URL 템플릿
    val directionsUrlTemplate = "https://www.google.com/maps/dir/?api=1&origin=%s&destination=%s"

    // 출발지와 도착지를 URL 인코딩
    val encodedOrigin = URLEncoder.encode(origin, "UTF-8")
    val encodedDestination = URLEncoder.encode(destination, "UTF-8")
    val directionsUrl = String.format(directionsUrlTemplate, encodedOrigin, encodedDestination)

    // 구글 지도 앱 인텐트 생성
    val mapIntent = Intent(Intent.ACTION_VIEW, Uri.parse(directionsUrl)).apply {
        setPackage("com.google.android.apps.maps")
    }

    // 구글 지도 앱이 설치되어 있는 경우 앱에서 열기, 그렇지 않으면 브라우저에서 열기
    if (mapIntent.resolveActivity(context.packageManager) != null) {
        context.startActivity(mapIntent)
    } else {
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(directionsUrl)))
    }
}