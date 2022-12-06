fun main() {

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
            // https://theasciicode.com.ar/
            if (it.isLowerCase()) {
                (it.code - 96)
            } else {
                (it.code - 38)
            }
        }
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 157) { "[${part1(testInput)}] is wrong." }

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
