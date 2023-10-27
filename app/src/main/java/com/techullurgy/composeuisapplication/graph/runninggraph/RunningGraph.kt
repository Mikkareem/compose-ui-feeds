package com.techullurgy.composeuisapplication.graph.runninggraph

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntSize
import com.techullurgy.composeuisapplication.graph.BezierConfiguration
import kotlin.random.Random

@Composable
fun rememberRunningGraphState(size: IntSize): RunningGraph? {
    return remember(size) {
        if(size.width > 0 && size.height > 0) RunningGraph(size) else null
    }
}

class RunningGraph(private val size: IntSize) {

    private val timeStep: Int get() = 20

    private val showablePointsCount = 10

    private var _lerpedPoints = mutableStateOf(emptyList<Offset>())

    val lerpedPoints: List<Offset>
        get() = _lerpedPoints.value.take(showablePointsCount*timeStep)

    val approachPoint: Offset
        get() = _lerpedPoints.value.take(showablePointsCount*timeStep + timeStep).last()

    val travelAnchorPoint: Offset
        get() = _lerpedPoints.value[showablePointsCount*timeStep-1]

    private val dataPoints: MutableList<Float> = mutableListOf()

    private val dataOffsets: MutableList<Offset> = mutableListOf()

    private var removedElements = 0

    private val drawableWidth = size.width * .75f
    private val drawableHeight = size.height * .85f

    private val gap = drawableWidth / showablePointsCount

    private var tickedTime = 0
    private val speed = 5

    private fun randomY(): Float
        = Random
            .nextInt(
                from = ((size.height - drawableHeight) / 2).toInt(),
                until = (((size.height - drawableHeight) / 2) + drawableHeight).toInt()
            ).toFloat()

    init {
        initiate()
    }

    private fun initiate() {
        (1..50).forEach { _ -> dataPoints.add(randomY()) }

        repeat(dataPoints.size) {
            val offset = Offset(it * gap, dataPoints[it])
            dataOffsets.add(offset)
        }

        _lerpedPoints.value = BezierConfiguration.getLerpedDetailsOfPoints(dataOffsets, timeStep)
    }

    private fun update(n: Int = 3) {
        repeat(n) {
            dataPoints.add(randomY())
        }

        val newDataPoints = dataPoints.takeLast(n)
        repeat(newDataPoints.size) {
            val offset = Offset( dataOffsets.last().x + gap, newDataPoints[it])
            dataOffsets.add(offset)
        }

        _lerpedPoints.value = _lerpedPoints.value.toMutableList().apply {
            val lerpedDetails = BezierConfiguration.getLerpedDetailsOfPoints(dataOffsets, timeStep)
                .takeLast(n * timeStep)
                .map { Offset(it.x - (tickedTime*speed), it.y) }

            addAll(lerpedDetails)
        }
    }

    private fun cleanupUnnecessaryPoints(): List<Offset> {
        val tempList = _lerpedPoints.value.filter {
            removedElements += if(it.x <= -1.5f) 1 else 0
            it.x > -1.5f
        }

        val unwantedDataPointsAnyMore = removedElements / (timeStep)


        for(i in 0 until unwantedDataPointsAnyMore) {
            dataPoints.removeAt(i)
            dataOffsets.removeAt(i)
        }

        if(removedElements >= timeStep) {
            removedElements = 0
        }

        return tempList
    }

    fun tick() {
        val tempList = cleanupUnnecessaryPoints()

        _lerpedPoints.value = tempList.map { Offset(it.x-speed, it.y) }.toMutableStateList()

        if(tempList.size - (showablePointsCount * timeStep) < (3 * timeStep)) {
            update(50)
        }
        tickedTime++
    }


}