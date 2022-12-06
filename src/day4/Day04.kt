package day4

import readInput

fun main() {
    fun IntRange.contains(other: IntRange): Boolean {
        return this.first >= other.first && this.last <= other.last
    }

    fun IntRange.overlaps(other: IntRange): Boolean {
        return this.first >= other.first && this.first <= other.last
                || this.last <= other.last && this.last >= other.first
    }

    fun getElfTasks(input: List<String>) = input
        .map {
            val elf1 = it.take(it.indexOf(","))
            val elf2 = it.substring(it.indexOf(",") + 1)
            elf1 to elf2
        }.map {
            val elf1Low = it.first.take(it.first.indexOf("-")).toInt()
            val elf1High = it.first.substring(it.first.indexOf("-") + 1).toInt()
            val elf1Range = elf1Low..elf1High

            val elf2Low = it.second.take(it.second.indexOf("-")).toInt()
            val elf2High = it.second.substring(it.second.indexOf("-") + 1).toInt()
            val elf2Range = elf2Low..elf2High
            elf1Range to elf2Range
        }

    fun part1(input: List<String>): Int {
        return getElfTasks(input).map { it: Pair<IntRange, IntRange> ->
            val elf1Range: IntRange = it.first
            val elf2Range: IntRange = it.second
            if (elf1Range.contains(elf2Range)) {
                return@map 1
            }
            if (elf2Range.contains(elf1Range)) {
                return@map 1
            }
            0
        }.sum()
    }


    fun part2(input: List<String>): Int {
        return getElfTasks(input).map { it: Pair<IntRange, IntRange> ->
            val elf1Range: IntRange = it.first
            val elf2Range: IntRange = it.second
            if (elf1Range.overlaps(elf2Range)) {
                return@map 1
            }
            if (elf2Range.overlaps(elf1Range)) {
                return@map 1
            }
            0
        }.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day4/Day04_test")
    check(part1(testInput) == 1)

    val input = readInput("day4/Day04")
    println(part1(input))
    println(part2(input))
}
