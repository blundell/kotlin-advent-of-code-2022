package day8

import readInput

data class Height(
    val value: Int,
    val westHeights: List<Int>,
    val eastHeights: List<Int>,
    val northHeights: List<Int>,
    val southHeights: List<Int>,
) {
    val visible by lazy {
        return@lazy listOf(
            northHeights,
            southHeights,
            eastHeights,
            westHeights,
        ).any { heights -> heights.none { it >= value } }
    }
}

private fun List<Int>.subListFrom(fromIndex: Int): List<Int> {
    if (fromIndex >= this.size) {
        return emptyList()
    }
    return this.subList(fromIndex, this.size)
}

private fun List<Int>.subListTo(toIndex: Int): List<Int> {
    return this.subList(0, toIndex)
}

fun main() {
    fun getAsInts(input: List<String>): MutableList<MutableList<Int>> {
        val ints = mutableListOf<MutableList<Int>>()
        for (line in input) {
            val currentLineInts = mutableListOf<Int>()
            for (c in line) {
                currentLineInts.add(c.code - '0'.code)
            }
            ints.add(currentLineInts)
        }
        return ints
    }

    fun storeHeights(rows: MutableList<MutableList<Int>>): MutableList<MutableList<Height>> {
        val heights = mutableListOf<MutableList<Height>>()
        for ((y, row) in rows.withIndex()) {
            val currentRowHeights = mutableListOf<Height>()
            for ((x, h) in row.withIndex()) {

                val northHeights = mutableListOf<Int>()
                for (n in 0 until y) {
                    // Get the height in the same column of the previous rows
                    northHeights.add(rows[n][x])
                }

                val southHeights = mutableListOf<Int>()
                for (n in y + 1 until row.size) {
                    // Get the height in the same column of the previous rows
                    southHeights.add(rows[n][x])
                }

                currentRowHeights.add(
                    Height(
                        value = h,
                        eastHeights = row.subListFrom(x + 1),
                        westHeights = row.subListTo(x),
                        northHeights = northHeights,
                        southHeights = southHeights,
                    )
                )
            }
            heights.add(currentRowHeights)
        }
        return heights
    }

    fun part1(input: List<String>): Int {
        val rows = getAsInts(input)
        val heights = storeHeights(rows)

        var runningTotal = 0
        for ((y, row) in heights.withIndex()) {
            for ((x, height) in row.withIndex()) {
                if (height.visible) {
                    print("${height.value}")
                    runningTotal += 1
                } else {
                    print("-")
                }
            }
            println("")
        }

        return runningTotal
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day8/Day8_test")
    val part1Result = part1(testInput)
    check(part1Result == 21) { "Expected 21 but got [$part1Result]" }

    val input = readInput("day8/Day8")
    println(part1(input))
    println(part2(input))
}
