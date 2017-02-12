import java.io.BufferedReader
import java.io.FileReader
import java.util.regex.Pattern

class Day9 {
    val markerPattern: Pattern = Pattern.compile("""\((\d+)x(\d+)\)([(\dx\d)]*)([A-Z]+)""")

    fun readInputAndDecompress() : String {
        val reader = BufferedReader(FileReader("day9_input.txt"))
        var decompressedString = ""

        reader.readLines().map {
            val markerMatcher = markerPattern.matcher(it)
            var i=0

            while (markerMatcher.find(i)) {
                val charCount = Integer.parseInt(markerMatcher.group(1))
                val repeatCount = Integer.parseInt(markerMatcher.group(2))
                var end = markerMatcher.end()

                var data = ""
                if (markerMatcher.group(3) != null) {
                    data += markerMatcher.group(3)
                }
                data += markerMatcher.group(4)

                if (charCount > data.length) {
                    end = markerMatcher.end() + (charCount - data.length)
                    data += it.substring(markerMatcher.end(), end)
                }

                println("$charCount x $repeatCount -> $data")

                for (j in 1..repeatCount) {
                    decompressedString += data.substring(0, charCount)
                }

                decompressedString += data.substring(charCount)

                i = end
            }
        }

        return decompressedString
    }

    fun printDecompressedLengthOfFile() {
        val decompressedFile = readInputAndDecompress()
        println("decompressed file length = ${decompressedFile.length}")
        println("decompressed file = $decompressedFile")
    }
}

fun main(args: Array<String>) {
    Day9().printDecompressedLengthOfFile()
}