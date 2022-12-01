fun main() {
    /**
     * This list represents the Calories of the food carried by five Elves.
     *
     * In case the Elves get hungry and need extra snacks, they need to know which Elf to ask:
     * they'd like to know how many Calories are being carried by the Elf carrying the most Calories.
     *
     * Find the Elf carrying the most Calories. How many total Calories is that Elf carrying?
     */
    fun part1(input: List<String>): Int {

        val caloriesByTotal = mutableMapOf<Int, List<String>>()
        var elvesCalories = mutableListOf<String>()
        var runningTotal = 0
        for (calories in input) {
            if (calories.isBlank()) {
                caloriesByTotal[runningTotal] = elvesCalories
                elvesCalories = mutableListOf()
                runningTotal = 0
                continue
            }
            runningTotal += calories.toInt()
            elvesCalories.add(calories)
        }

        return caloriesByTotal.keys.max()
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 1000)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
