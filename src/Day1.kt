import java.io.BufferedReader
import java.io.FileReader
import java.util.regex.Pattern

class Day1 {
    enum class Direction {
        NORTH, SOUTH, WEST, EAST
    }

    data class Instruction(val turn: String, val distance: Int)

    data class Point(var x: Int, var y: Int)

    fun readInput() : List<Instruction> {
        val instructionList = mutableListOf<Instruction>()
        val reader : BufferedReader = BufferedReader(FileReader("day1_input.txt"))

        for (line in reader.readLines()) {
            instructionList.addAll(parseLine(line))
            parseLine (line)
        }

        return instructionList
    }

    fun parseLine(line : String) : List<Instruction> {
        val instructionList = mutableListOf<Instruction>()
        val instructionPattern = Pattern.compile("""(\p{Upper})(\d+)""")

        line.split(", ").map {
            val instructionMatcher = instructionPattern.matcher(it)
            if (instructionMatcher.matches()) {
                val instruction = Instruction(turn = instructionMatcher.group(1), distance = (Integer.parseInt(instructionMatcher.group(2))))
                instructionList.add(instruction)
            }
        }

        return instructionList
    }

    fun calculateDistance() {
        var currentPoint = Point(0, 0)
        var currentDirection = Direction.NORTH
        val instructions = readInput()
        println("Instructions = " + instructions)
        instructions.map {
            currentDirection = turn(currentDirection, it.turn)
            currentPoint = move(currentDirection, it.distance, currentPoint)
        }

        val distance = Math.abs(currentPoint.x) + Math.abs(currentPoint.y)
        println("End point = " + currentPoint)
        println("Distance = " + distance)
    }

    fun turn(dir: Direction, turn: String) : Direction {
        if (turn == "L") {
            when (dir) {
                Direction.NORTH -> return Direction.WEST
                Direction.SOUTH -> return Direction.EAST
                Direction.WEST -> return Direction.SOUTH
                Direction.EAST -> return Direction.NORTH
            }
        }
        else if (turn == "R") {
            when (dir) {
                Direction.NORTH -> return Direction.EAST
                Direction.SOUTH -> return Direction.WEST
                Direction.WEST -> return Direction.NORTH
                Direction.EAST -> return Direction.SOUTH
            }
        }
        return dir
    }

    fun move(dir: Direction, distance: Int, location: Point) : Point {
        var newPoint = Point(location.x, location.y)

        when (dir) {
            Direction.NORTH -> newPoint.y += distance
            Direction.SOUTH -> newPoint.y -= distance
            Direction.WEST -> newPoint.x -= distance
            Direction.EAST -> newPoint.x += distance
        }

        return newPoint
    }
}


fun main(args: Array<String>) {
    Day1().calculateDistance()
}