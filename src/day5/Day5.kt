package day5

import readInput

data class Instruction(val moves: Int, val from: Int, val to: Int) {
    override fun toString(): String {
        return "move $moves from $from to $to"
    }
}

fun main() {
    fun parseStacks(input: List<String>): List<ArrayDeque<Char>> {
        val captureCrates = "( {3}|\\[[A-Z]]) ?+".toRegex()
        val rowsOfCrates = mutableListOf<List<Char>>()
        for (line in input) {
            if (line.startsWith(" 1")) {
                // no more crates to parse
                break
            }
            val matchResult = captureCrates.findAll(line)
            val row = matchResult.map {
                it.groupValues[1].trim().getOrElse(1) { ' ' }
            }.toList()
            rowsOfCrates.add(row)
        }
        // Convert parsed rows into stacks
        val stacks = mutableListOf<ArrayDeque<Char>>()
        var rowIndex = -1
        for (row in 0..rowsOfCrates.first().size) {
            stacks.add(ArrayDeque(mutableListOf()))
        }
        println(rowsOfCrates.size)
        println(stacks.size)
        for (row in rowsOfCrates) {
            for (crate in row) {
                rowIndex++
                if (crate != ' ') {
                    stacks[rowIndex].addLast(crate)
                }
            }
            rowIndex = -1
        }
        return stacks
    }

    fun parseInstructions(input: List<String>): List<Instruction> {
        val captureMoves = "[0-9]+".toRegex()
        var atInstructions = false
        val instructions = mutableListOf<Instruction>()
        for (line in input) {
            if (line.isBlank()) {
                atInstructions = true
                continue
            }
            if (!atInstructions) {
                continue
            }
            val matchResult = captureMoves.findAll(line)
            val v = matchResult.map { it.groupValues.first().toInt() }.toList()
            instructions.add(Instruction(v[0], v[1], v[2]))
        }
        return instructions
    }

    fun part1(input: List<String>): String {
        val stacks: List<ArrayDeque<Char>> = parseStacks(input)
        val instructions: List<Instruction> = parseInstructions(input)

        for (instruction in instructions) {
            for (n in 1..instruction.moves) {
                val toStack = stacks[instruction.to - 1]
                val fromStack = stacks[instruction.from - 1]
                toStack.addFirst(fromStack.removeFirst())
                println(instruction)
                println(stacks)
                println("--")
            }
        }

        return stacks.map { it.firstOrNull() ?: "" }.joinToString(separator = "")
    }

    fun part2(input: List<String>): String {
        return ""
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day5/Day5_test")
//    val part1Result = part1(testInput)
//    check(part1Result == "CMZ") { "Got [$part1Result instead of CMZ." }

    val input = readInput("day5/Day5")
    println(part1(input))
//    println(part2(input))
}
