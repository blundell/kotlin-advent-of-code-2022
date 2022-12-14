package day7

import readInput

data class FileDetails(
    val name: String,
    val size: Long,
)

data class Directory(
    val name: String,
    val parent: Directory?,
    val directories: MutableList<Directory> = mutableListOf(),
    val files: MutableList<FileDetails> = mutableListOf(),
) {
    val size: Long by lazy {
        var size = 0L
        for (directory in directories) {
            size += directory.size
        }
        for (file in files) {
            size += file.size
        }
        size
    }

    /**
     * Return any directory we contiain that is under 100,000 total size, including ourselves
     */
    fun smallsOrEmpty(output: MutableList<Directory>) {
        if (size <= 100_000) {
            output.add(this)
        }
        for (directory in directories) {
            directory.smallsOrEmpty(output)
        }
    }

    fun hasDir(directory: Directory): Boolean = this.directories.find {
        it.name == directory.name && it.parent == directory.parent
    } != null

    fun addFile(name: String, size: Long) {
        this.files.add(FileDetails(name, size))
    }

    fun findOver(sizeMin: Long, output: MutableList<Directory>) {
        if (this.size >= sizeMin) {
            output.add(this)
        }
        for (directory in directories) {
            directory.findOver(sizeMin, output)
        }
    }
}

fun main() {
    fun readFileSystem(input: List<String>): Directory {
        var fileSystem: Directory? = null
        var currentDirectory: Directory? = null
        for (line in input) {
            if (line.startsWith("$")) {
                // Found an instruction
                if (line.startsWith("$ cd")) {
                    // Found a directory
                    val directoryName = line.substring(5)
                    val directory: Directory
                    if (directoryName == "..") {
                        directory = currentDirectory!!.parent!!
                    } else {
                        directory = Directory(name = directoryName, parent = currentDirectory)
                        if (currentDirectory == null) {
                            fileSystem = directory
                        } else {
                            if (!currentDirectory.hasDir(directory)) {
                                currentDirectory.directories.add(directory)
                            }
                        }
                    }
                    currentDirectory = directory
                } else if (line.startsWith("$ ls")) {
                    // listing files & directories next
                }
            } else if (line.matches("^[0-9].*".toRegex())) {
                // Description of a file
                val size = line.substringBefore(' ').toLong()
                val name = line.substringAfter(' ')
                currentDirectory!!.addFile(name, size)
            } else if (line.startsWith("dir")) {
                // Description of a folder
                // We don't record this; it has no size, so it is not interesting
            } else {
                throw IllegalStateException("Unhandled line [$line]")
            }
        }
        return fileSystem!!
    }

    fun part1(input: List<String>): Int {
        val fileSystem: Directory = readFileSystem(input)

        val smalls = mutableListOf<Directory>()
        fileSystem.smallsOrEmpty(smalls)

        return smalls.sumOf { it.size }.toInt()
    }

    fun part2(input: List<String>): Int {
        val fileSystem: Directory = readFileSystem(input)
        val unusedSpace = 70_000_000 - fileSystem.size
        val neededSpace = 30_000_000 - unusedSpace
        val output = mutableListOf<Directory>()
        fileSystem.findOver(neededSpace, output)
        var smallest: Directory? = null
        for (directory in output) {
            if (smallest == null || directory.size < smallest.size) {
                smallest = directory
            }
        }
        return smallest!!.size.toInt()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day7/Day7_test")
    val part1Result = part1(testInput)
    check(part1Result == 95437) { "Expecting 95437 but got [$part1Result]." }
    val part2Result = part2(testInput)
    check(part2Result == 24933642) { "Expecting 24933642 but got [$part2Result]." }

    val input = readInput("day7/Day7")
    println(part1(input))
    println(part2(input))
}
