import java.io.BufferedReader
import java.io.FileReader

class Day2 {
    data class Instruction(val directions: String)

    data class Point(var x: Int, var y: Int)

    fun readInput() : List<Instruction> {
        val instructionList = mutableListOf<Instruction>()
        val reader : BufferedReader = BufferedReader(FileReader("day2_input.txt"))

        reader.readLines().mapTo(instructionList, ::Instruction)

        return instructionList
    }

    val numpad = Array(3, { IntArray(3) })
    val currentPoint = Point(1,1)

    fun calculateBathroomCode() {
        val instructionList = readInput()
        var code = ""
        numpad[0][0] = 1
        numpad[1][0] = 2
        numpad[2][0] = 3
        numpad[0][1] = 4
        numpad[1][1] = 5
        numpad[2][1] = 6
        numpad[0][2] = 7
        numpad[1][2] = 8
        numpad[2][2] = 9

        currentPoint.x = 1
        currentPoint.y = 1

        instructionList.map {
            it.directions.split("").map {
                when(it) {
                    "U" -> move(0, -1)
                    "D" -> move(0, 1)
                    "L" -> move(-1, 0)
                    "R" -> move(1, 0)
                }
            }

            code += numpad[currentPoint.x][currentPoint.y]
        }

        println("code = $code")
    }

    fun move(x: Int, y: Int) {
        currentPoint.x += x
        currentPoint.y += y

        if (currentPoint.x < 0)
            currentPoint.x = 0
        if (currentPoint.x > 2)
            currentPoint.x = 2

        if (currentPoint.y < 0)
            currentPoint.y = 0
        if (currentPoint.y > 2)
            currentPoint.y = 2
    }
}

fun main(args: Array<String>) {
    Day2().calculateBathroomCode()
}
