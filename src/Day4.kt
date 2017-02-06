import java.io.BufferedReader
import java.io.FileReader
import java.util.*
import java.util.regex.Pattern


class Day4 {
    data class Room(val name: String, val sectorId: Int, val checksum: String)

    fun readInput(): List<Room> {
        val roomList = mutableListOf<Room>()
        val reader = BufferedReader(FileReader("day4_input.txt"))
        val inPattern = Pattern.compile("""^([a-z-]+)-(\d+)\[([a-z]+)\]$""")
        reader.readLines().map {
            val matcher = inPattern.matcher(it)
            if (matcher.matches()) {
                roomList.add(Room(matcher.group(1), Integer.parseInt(matcher.group(2)), matcher.group(3)))
            }
        }
        return roomList
    }

    fun printSumOfRealRoomSectorIds() {
        var sum = 0
        val roomList = readInput()

        roomList.map {
            if (isRealRoom(it)) {
                println("real room = $it")
                sum += it.sectorId
            }
        }

        println("sum of real room section ids = $sum")
    }

    fun isRealRoom(room: Room): Boolean {
        val charCountMap = getCharacterCountMap(room.name)
        val sortedCharCountMap = sortByValue(charCountMap)

        println(room.toString() + " " + sortedCharCountMap)

        var checksum = room.checksum
        var previousCheck = checksum.first()
        var previousCount = sortedCharCountMap.values.first()
        var checkEqual = false

        sortedCharCountMap.map {
            if (checksum.isNotEmpty()) {
                println("${it.key} ${checksum.first()} ${it.value}")

                if (checkEqual && it.value != previousCount) {
                    return false
                }

                previousCheck = checksum.first()

                if (it.key == checksum.first()) {
                    checksum = checksum.substring(1)
                    previousCount = it.value
                    checkEqual = false
                }
                else {
                    checkEqual = true
                }
            }
            else if (checkEqual) {
                println("$it $previousCheck $previousCount")

                if (it.value == previousCount) {
                    if (it.key == previousCheck) {
                        return true
                    }
                }
                else {
                    return false
                }
            }
        }

        return previousCheck == room.checksum.last()
    }

    fun getCharacterCountMap(str: String): Map<Char, Int> {
        val charCountMap = mutableMapOf<Char, Int>()

        str.map {
            if (it != '-') {
                charCountMap[it] = charCountMap[it]?.plus(1) ?: 1
            }
        }

        return charCountMap
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
    Day4().printSumOfRealRoomSectorIds()
}