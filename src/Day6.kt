import java.io.BufferedReader
import java.io.FileReader
import java.util.*

class Day6 {
    fun readInput() : List<String> {
        val reader : BufferedReader = BufferedReader(FileReader("day6_input.txt"))
        return reader.readLines()
    }

    fun printErrorCorrectedVersion() {
        val inputList = readInput()

        val errorCorrectList = mutableListOf<MutableMap<Char, Int>>()

        for (i in 1..inputList.first().length) {
            errorCorrectList.add(mutableMapOf<Char, Int>())
        }

        for (line in inputList) {
            var index = 0
            line.map {
                errorCorrectList[index][it] = errorCorrectList[index][it]?.plus(1) ?: 1
                index++
            }
        }

        print("error-corrected version = ")
        for (charMap in errorCorrectList) {
            print(sortByValue(charMap).keys.first())
        }
        println()
    }

    fun <K, V : Comparable<V>> sortByValue(map: Map<K, V>): Map<K, V> {
        val list = LinkedList(map.entries)
        Collections.sort(list) { o1, o2 -> o2.value.compareTo(o1.value) }

        val result = LinkedHashMap<K, V>()
        for ((key, value) in list) {
            result.put(key, value)
        }
        return result
    }
}

fun main(args: Array<String>) {
    Day6().printErrorCorrectedVersion()
}