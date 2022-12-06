fun main() {

    /**
     * https://theasciicode.com.ar/
     */
    fun Char.toElfIndex() = if (isLowerCase()) {
        (code - 96)
    } else {
        (code - 38)
    }

    /**
     * Lowercase item types a through z have priorities 1 through 26.
     * Uppercase item types A through Z have priorities 27 through 52.
     *
     * Find the item type that appears in both compartments of each rucksack.
     * What is the sum of the priorities of those item types?
     */
    fun part1(input: List<String>): Int {
        return input.map {
            val leftCompartment = it.take(it.length / 2)
            val rightCompartment = it.substring(it.length / 2)
            var dupe = ' '
            for (c in leftCompartment) {
                for (rC in rightCompartment) {
                    if (rC == c) {
                        dupe = c
                    }
                }
            }
            dupe
        }.sumOf {
            it.toElfIndex()
        }
    }

    fun part2(input: List<String>): Int {
        return input.chunked(3)
            .map {
                for (a in it[0]) {
                    for (b in it[1]) {
                        for (c in it[2]) {
                            if (a == b && b == c) {
                                return@map a.toElfIndex().also { n -> println("F $a $n") }
                            }
                        }
                    }
                }
                throw IllegalStateException("No match!")
            }
            .sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part2(testInput) == 70) { "[${part2(testInput)}] is wrong." }

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
