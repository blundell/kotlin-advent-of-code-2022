package day10

import readInput

fun main() {
    data class Instruction(val op: String, val value: Int) {
        override fun toString(): String {
            return "$op $value"
        }
    }

    fun part1(input: List<String>): Int {
        val instructions = input.map {
            val op = it.substring(0..3)
            val value: Int
            if (it.contains(' ')) {
                value = it.substringAfter(' ').toInt()
            } else {
                value = 0
            }
            Instruction(op, value)
        }

        val totalCycles = instructions.map {
            if (it.op == "addx") {
                2
            } else { // noop
                1
            }
        }.sum()

        var x = 1
        var currentInstruction: Instruction?
        var instructionIndex = 0
        var firstAdd = false
        var inspectionX = 0
        val inspectionCycles = listOf(20, 60, 100, 140, 180, 220)
        for (cycle in 0 until totalCycles) {
            println("Cycle $cycle")
            currentInstruction = instructions[instructionIndex]
            println("Instr $currentInstruction X $x")
            if (inspectionCycles.contains(cycle)) {
                println("Sum ${(cycle * x)}")
                inspectionX += (cycle * x)
                if (cycle == 220) {
//                    inspectionX -= 220
                }
                println("InspectionX $inspectionX")
            }
            if (currentInstruction.op == "addx") {
                if (!firstAdd) {
                    println("first add")
                    firstAdd = true
                } else {
                    println("second add")
                    firstAdd = false
                    x += currentInstruction.value
                    instructionIndex += 1
                }
            } else { // noop
                println("noop")
                firstAdd = false
                instructionIndex += 1
            }
            println("--")
        }

        return inspectionX
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day10/Day10_test")
    val testResult = part1(testInput)
    check(testResult == 13140) { "Expected 13140 got [$testResult]." }

//    val input = readInput("day10/Day10")
//    println(part1(input))
//    println(part2(input))
}
