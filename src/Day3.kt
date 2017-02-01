import java.io.BufferedReader
import java.io.FileReader

class Day3 {
    data class Triangle(val a: Int, val b: Int, val c: Int)

    fun readInput(): List<Triangle> {
        val triangles = mutableListOf<Triangle>()
        val reader = BufferedReader(FileReader("day3_input.txt"))

        reader.readLines().mapTo(triangles) {
            val lengths = it.trim().split(Regex("""(\s+)"""))

            assert(lengths.count() == 3)

            Triangle(Integer.parseInt(lengths[0]), Integer.parseInt(lengths[1]), Integer.parseInt(lengths[2]))
        }

        return triangles
    }

    fun printValidTriangleCount() {
        val triangles = readInput()
        var validCount = 0

        triangles.map {
            if (isValidTriangle(it)) validCount++
        }

        println("total triangle count = ${triangles.count()}")
        println("valid triangle count = $validCount")
    }

    fun isValidTriangle(triangle: Triangle): Boolean {
        return (triangle.a + triangle.b > triangle.c) && (triangle.a + triangle.c > triangle.b) && (triangle.b + triangle.c > triangle.a)
    }
}

fun main(args: Array<String>) {
    Day3().printValidTriangleCount()
}