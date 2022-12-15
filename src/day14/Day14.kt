package day14

import readInput
import java.awt.Point
import kotlin.math.max
import kotlin.math.min

private fun Point.moveDown(): Point {
    return inc(y = 1)
}

private fun Point.moveDownAndLeft(): Point {
    return inc(x = -1, y = 1)
}

private fun Point.moveDownAndRight(): Point {
    return inc(x = 1, y = 1)
}

private fun Point.inc(x: Int = 0, y: Int = 0): Point {
    return Point(this.x + x, this.y + y)
}

const val showVisuals = true

fun main() {
    fun expandRock(rockPaths: MutableList<List<Point>>): List<Point> {
        val allPoints = rockPaths.flatMap {
            var left: Point
            var right: Point
            val allPoints = mutableListOf<Point>()
            for ((i, point) in it.withIndex()) {
                left = point
                if (i == it.size - 1) {
                    break
                }
                right = it[i + 1]

                if (left.x == right.x) {
                    // horizontal
                    for (n in IntRange(min(left.y, right.y), max(left.y, right.y))) {
                        allPoints.add(Point(left.x, n))
                    }
                } else {
                    // vertical
                    for (n in IntRange(min(left.x, right.x), max(left.x, right.x))) {
                        allPoints.add(Point(n, left.y))
                    }
                }
            }
            allPoints
        }
        return allPoints
    }

    fun printScan(
        xRange: IntRange,
        yRange: IntRange,
        sandSpawn: Point,
        sandPoints: List<Point>,
        rockPoints: List<Point>
    ) {
        val firstA = xRange.first.toString()[0]
        val firstB = xRange.first.toString()[1]
        val firstC = xRange.first.toString()[2]
        val space = " ".repeat((xRange.last - xRange.first) - 1)
        val lastA = xRange.last.toString()[0]
        val lastB = xRange.last.toString()[1]
        val lastC = xRange.last.toString()[2]
        println("  $firstA$space$lastA")
        println("  $firstB$space$lastB")
        println("  $firstC$space$lastC")
        for ((i, y) in yRange.withIndex()) {
            print("$i ")
            for (x in xRange) {
                if (y == sandSpawn.y && x == sandSpawn.x) {
                    // sand source
                    print("+")
                } else if (rockPoints.contains(Point(x, y))) {
                    // rock
                    print("#")
                } else if (sandPoints.contains(Point(x, y))) {
                    // sand
                    print("o")
                } else {
                    // air
                    print(".")
                }
            }
            println()
        }
        println()
    }

    fun simulateSand(
        rockPoints: List<Point>,
        sandSpawn: Point,
        yRange: IntRange,
        xRange: IntRange
    ): MutableList<Point> {
        val sandPoints = mutableListOf<Point>()
        val collisionPoints = (rockPoints + sandPoints).toMutableList()
        var offTheGrid = false
        var moving: Boolean
        while (!offTheGrid) {
            var sandGrain = sandSpawn
            moving = true
            while (moving) {
                if (collisionPoints.contains(sandGrain.moveDown())) {
                    // If rock or sand in the way (below)
                    if (collisionPoints.contains(sandGrain.moveDownAndLeft())) {
                        // If rock or sand in the way (below and left)
                        if (collisionPoints.contains(sandGrain.moveDownAndRight())) {
                            // If rock or sand in the way (below and right)
                            // Blocked in all directions
                            moving = false
                        } else {
                            // Nothing below and right
                            sandGrain = sandGrain.moveDownAndRight()
                        }
                    } else {
                        // Nothing below and left
                        sandGrain = sandGrain.moveDownAndLeft()
                    }
                } else {
                    // Nothing below
                    sandGrain = sandGrain.moveDown()
                }
                if (sandGrain.y > yRange.last) {
                    // Off the grid
                    moving = false
                    offTheGrid = true
                }
            }

            // Sand starts at source
            // Down if possible (air)
            // If down blocked by rock or sand, down & left
            // If down and left blocked by rock or sand, down & right
            // if down & down left & down right blocked, comes to rest where it is
            // Next sand starts at source
            sandPoints.add(sandGrain)
            collisionPoints.add(sandGrain)
            if (showVisuals) {
                printScan(xRange, yRange, sandSpawn, sandPoints, rockPoints)
            }
        }
        return sandPoints
    }

    fun part1(input: List<String>): Int {
        val rockPaths = mutableListOf<List<Point>>()
        for (line in input) {
            val path = "([0-9]{3}),([0-9]{1,3})".toRegex().findAll(line)
                .map { it.groupValues }
                .map { Point(it[1].toInt(), it[2].toInt()) }
                .toList()
            rockPaths.add(path)
        }

        val sandSpawn = Point(500, 0)
        val allPathPoints = rockPaths.flatten() + listOf(sandSpawn)
        val xRange = IntRange(allPathPoints.minOf { it.x }, allPathPoints.maxOf { it.x })
        val yRange = IntRange(allPathPoints.minOf { it.y }, allPathPoints.maxOf { it.y })

        val rockPoints = expandRock(rockPaths)

        println("A grid of $xRange by $yRange")
        val sandPoints = simulateSand(rockPoints, sandSpawn, yRange, xRange)

        return sandPoints.size - 1
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day14/Day14_test")
    val part1Result = part1(testInput)
    check(part1Result == 24) { "Expected 24 but got [$part1Result]." }
//    val part2Result = part2(testInput)
//    check(part2Result == 91) { "Expected 91 but got [$part2Result]." }

    val input = readInput("day14/Day14")
    println(part1(input))
    println(part2(input))
}
