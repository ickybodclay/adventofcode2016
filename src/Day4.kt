import java.io.BufferedReader
import java.io.FileReader
import java.util.regex.Pattern

class Day4 {
    data class Room(val name: String, val sectorId: Int, val checksum: String)

    fun readInput() : List<Room> {
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
        val roomList = readInput()
        println(roomList)
    }
}

fun main(args: Array<String>) {
    Day4().printSumOfRealRoomSectorIds()
}