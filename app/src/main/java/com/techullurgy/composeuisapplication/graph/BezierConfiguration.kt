package com.techullurgy.composeuisapplication.graph

import androidx.compose.ui.geometry.Offset
import com.techullurgy.composeuisapplication.math.lerp

/*
* https://exploringswift.com/blog/Drawing-Smooth-Cubic-Bezier-Curve-through-prescribed-points-using-Swift
* */

data class BezierSegmentControlPoints(
    val firstControlPoint: Offset,
    val secondControlPoint: Offset
)

class BezierConfiguration private constructor() {
    private var firstControlPoints: MutableList<Offset?> = mutableListOf()
    private val secondControlPoints: MutableList<Offset?> = mutableListOf()

    fun configureControlPoints(data: List<Offset>): List<BezierSegmentControlPoints> {
        val segments = data.size - 1

        if(segments == 1) {
            val p0 = data[0]
            val p1 = data[1]

            return listOf(BezierSegmentControlPoints(p0, p1))
        } else if(segments > 1) {

            //left hand side coefficients
            val ad = mutableListOf<Float>()
            val d = mutableListOf<Float>()
            val bd = mutableListOf<Float>()

            val rhsArray = mutableListOf<Offset>()

            for (i in 0 until segments) {
                var rhsXValue: Float
                var rhsYValue: Float

                val p0 = data[i]
                val p3 = data[i + 1]

                when(i) {
                    0 -> {
                        bd.add(0.0f)
                        d.add(2.0f)
                        ad.add(1.0f)

                        rhsXValue = p0.x + 2 * p3.x
                        rhsYValue = p0.y + 2 * p3.y
                    }
                    segments - 1 -> {
                        bd.add(2.0f)
                        d.add(7.0f)
                        ad.add(0.0f)

                        rhsXValue = 8 * p0.x + p3.x
                        rhsYValue = 8 * p0.y + p3.y
                    }
                    else -> {
                        bd.add(1.0f)
                        d.add(4.0f)
                        ad.add(1.0f)

                        rhsXValue = 4 * p0.x + 2 * p3.x
                        rhsYValue = 4 * p0.y + 2 * p3.y
                    }
                }

                rhsArray.add(Offset(rhsXValue, rhsYValue))
            }

            return thomasAlgorithm(bd, d, ad, rhsArray, segments, data)
        }
        return emptyList()
    }

    private fun thomasAlgorithm(bd: List<Float>, d: List<Float>, ad1: List<Float>, rhsArray1: List<Offset>, segments: Int, data: List<Offset>): List<BezierSegmentControlPoints> {
        val controlPoints = mutableListOf<BezierSegmentControlPoints>()

        val ad = ad1.toMutableList()
        val rhsArray = rhsArray1.toMutableList()

        val solutionSet1 = Array<Offset?>(segments) { null }

        // First segment
        ad[0] = ad[0] / d[0]
        rhsArray[0] = Offset(rhsArray[0].x / d[0], rhsArray[0].y / d[0])

        // Middle Segments
        if(segments > 2) {
            for (i in 1..segments - 2) {
                val rhsValueX = rhsArray[i].x
                val preRhsValueX = rhsArray[i-1].x

                val rhsValueY = rhsArray[i].y
                val preRhsValueY = rhsArray[i-1].y

                ad[i] = ad[i] / (d[i] - bd[i] * ad[i-1])

                val exp1x = (rhsValueX - (bd[i] * preRhsValueX))
                val exp1y = (rhsValueY - (bd[i] * preRhsValueY))
                val exp2 = (d[i] - bd[i]*ad[i-1])

                rhsArray[i] = Offset(exp1x / exp2, exp1y / exp2)
            }
        }

        // Last Segment
        val lastElementIndex = segments - 1
        val exp1x = (rhsArray[lastElementIndex].x - bd[lastElementIndex] * rhsArray[lastElementIndex - 1].x)
        val exp1y = (rhsArray[lastElementIndex].y - bd[lastElementIndex] * rhsArray[lastElementIndex - 1].y)
        val exp2 = (d[lastElementIndex] - bd[lastElementIndex] * ad[lastElementIndex - 1])
        rhsArray[lastElementIndex] = Offset(exp1x / exp2, exp1y / exp2)

        solutionSet1[lastElementIndex] = rhsArray[lastElementIndex]

        for (i in (0 until lastElementIndex).reversed()) {
            val controlPointX = rhsArray[i].x - (ad[i] * solutionSet1[i+1]!!.x)
            val controlPointY = rhsArray[i].y - (ad[i] * solutionSet1[i+1]!!.y)
            solutionSet1[i] = Offset(controlPointX, controlPointY)
        }

        firstControlPoints = solutionSet1.toMutableList()

        for(i in 0 until segments) {
            if(i == (segments-1)) {
                val lastDataPoint = data[i+1]
                val p1 = firstControlPoints[i]
                p1?.let {
                    val controlPoint1 = it

                    val controlPoint2X = (0.5f)*(lastDataPoint.x + controlPoint1.x)
                    val controlPoint2Y = (0.5f)*(lastDataPoint.y + controlPoint1.y)

                    secondControlPoints.add(Offset(controlPoint2X, controlPoint2Y))
                } ?: continue
            } else {
                val dataPoint = data[i+1]
                val p1 = firstControlPoints[i+1]
                p1?.let {
                    val controlPoint1 = it

                    val controlPoint2X = 2*dataPoint.x - controlPoint1.x
                    val controlPoint2Y = 2*dataPoint.y - controlPoint1.y

                    secondControlPoints.add(Offset(controlPoint2X, controlPoint2Y))
                } ?: continue
            }
        }

        for(i in 0 until segments) {
            val firstCP = firstControlPoints[i] ?: continue
            val secondCP = secondControlPoints[i] ?: continue

            val segmentControlPoint = BezierSegmentControlPoints(firstControlPoint = firstCP, secondControlPoint = secondCP)
            controlPoints.add(segmentControlPoint)
        }

        return controlPoints
    }

    companion object {
        fun getLerpedDetailsOfPoints(offsets: List<Offset>, timeStep: Int): List<Offset> {
            val controlPoints = BezierConfiguration().configureControlPoints(offsets)

            val result: MutableList<Offset> = mutableListOf()

            for (i in 1 until offsets.size) {
                val segment = controlPoints[i - 1]
                var time = 0f
                while (time <= 1f) {
                    result.add(
                        cubicLerp(
                            offsets[i - 1],
                            segment.firstControlPoint,
                            segment.secondControlPoint,
                            offsets[i],
                            time
                        )
                    )
                    time += (1f / timeStep)
                }
            }

            return result.toList()
        }

        private fun cubicLerp(p0: Offset, p1: Offset, p2: Offset, p3: Offset, fraction: Float): Offset {
            val q1 = quadraticLerp(p0, p1, p2, fraction)
            val q2 = quadraticLerp(p1, p2, p3, fraction)

            return Offset(
                x = lerp(q1.x, q2.x, fraction),
                y = lerp(q1.y, q2.y, fraction)
            )
        }

        private fun quadraticLerp(p0: Offset, p1: Offset, p2: Offset, fraction: Float): Offset {
            val x1 = lerp(p0.x, p1.x, fraction)
            val y1 = lerp(p0.y, p1.y, fraction)
            val x2 = lerp(p1.x, p2.x, fraction)
            val y2 = lerp(p1.y, p2.y, fraction)

            return Offset(
                x = lerp(x1, x2, fraction),
                y = lerp(y1, y2, fraction)
            )
        }
    }
}

fun lagrangeInterpolation(
    dataPoints: List<Offset>,
    x: Float
): Float {
    val n = dataPoints.size

    var result = 0f

    for(i in 0 until n) {
        var numerator = 1f
        var denominator = 1f
        for(j in 0 until n) { // single term calculation
            if(i != j) {
                numerator *= (x - dataPoints[j].x)
                denominator *= (dataPoints[i].x - dataPoints[j].x)
            }
        }
        result += (numerator / denominator) * dataPoints[i].y
    }
    return result
}