package day2

import readInput

fun main() {

    /**
     * Rock defeats Scissors, Scissors defeats Paper, and Paper defeats Rock.
     * If both players choose the same shape, the round instead ends in a draw.
     *
     * A for Rock, B for Paper, and C for Scissors
     * X for Rock, Y for Paper, and Z for Scissors
     * 1 for Rock, 2 for Paper, and 3 for Scissors
     * 0 if you lost, 3 if the round was a draw, and 6 if you won
     */
    fun part1(input: List<String>): Int {
        return input
            .map { it[0] to it[2] }
            .map {
                when (it.first) {
                    'A' -> {
                        when (it.second) {
                            'X' -> {
                                // Draw
                                return@map 3 + 1
                            }

                            'Y' -> {
                                // I win
                                return@map 6 + 2
                            }

                            else -> {
                                // They win
                                return@map 0 + 3
                            }
                        }
                    }

                    'B' -> {
                        when (it.second) {
                            'Y' -> {
                                // Draw
                                return@map 3 + 2
                            }

                            'Z' -> {
                                // I win
                                return@map 6 + 3
                            }

                            else -> {
                                // They win
                                return@map 0 + 1
                            }
                        }
                    }

                    'C' -> {
                        when (it.second) {
                            'Z' -> {
                                // Draw
                                return@map 3 + 3
                            }

                            'X' -> {
                                // I win
                                return@map 6 + 1
                            }

                            else -> {
                                // They win
                                return@map 0 + 2
                            }
                        }
                    }
                }
                throw IllegalStateException("Unhandled move [$it].")
            }
            .sum()
    }

    /**
     * X means you need to lose,
     * Y means you need to end the round in a draw,
     * and Z means you need to win.
     */
    fun part2(input: List<String>): Int {
        return input
            .map { it[0] to it[2] }
            .map {
                when (it.first) {
                    'A' -> {
                        when (it.second) {
                            'X' -> {
                                // I need to lose (c)
                                return@map 0 + 3
                            }

                            'Y' -> {
                                // I need to draw (a)
                                return@map 3 + 1
                            }

                            else -> {
                                // I need to win (b)
                                return@map 6 + 2
                            }
                        }
                    }

                    'B' -> {
                        when (it.second) {
                            'Y' -> {
                                // I need to draw (b)
                                return@map 3 + 2
                            }

                            'Z' -> {
                                // I need to win (c)
                                return@map 6 + 3
                            }

                            else -> {
                                // I need to lose  (a)
                                return@map 0 + 1
                            }
                        }
                    }

                    'C' -> {
                        when (it.second) {
                            'Z' -> {
                                // I need to win (a)
                                return@map 6 + 1
                            }

                            'X' -> {
                                // I need to lose (b)
                                return@map 0 + 2
                            }

                            else -> {
                                // I need to draw (c)
                                return@map 3 + 3
                            }
                        }
                    }
                }
                throw IllegalStateException("Unhandled move [$it].")
            }
            .sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day02/Day02_test")
    check(part1(testInput) == 9)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
