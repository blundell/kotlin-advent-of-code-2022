package day6

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        var lastFour = ""
        for ((index, c) in input[0].withIndex()) {
            if (lastFour.length < 4) {
                lastFour += c
                continue
            }
            lastFour = lastFour.removeRange(0, 1)
            lastFour += c
            var unique = true
            for ((i1, cF) in lastFour.withIndex()) {
                for ((i2, cf2) in lastFour.withIndex()) {
                    if (i1 == i2) {
                        continue
                    }
                    if (cF == cf2) {
                        unique = false
                    }
                }
            }
            if (unique) {
                return index + 1
            }
        }
        throw IllegalStateException("No unique start for [$input].")
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day6/Day6_test")
    val testResult = part1(testInput)
    check(testResult == 7) { "Expected 7 got [$testResult]" }

    val input = readInput("day6/Day6")
    println(part1(input))
    println(part2(input))
}
