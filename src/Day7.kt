import java.io.BufferedReader
import java.io.FileReader
import java.util.regex.Pattern

class Day7 {
    data class IP(val sequenceList : List<String>, val hypernetSequenceList : List<String>)

    val seqPattern: Pattern = Pattern.compile("""([a-z]+)""")
    val hypernetPattern: Pattern = Pattern.compile("""\[([a-z]+)\]""")

    fun readInput() : List<IP> {
        val ipList = mutableListOf<IP>()
        val reader = BufferedReader(FileReader("day7_input.txt"))

        reader.readLines().map {
            val ip = parseLine(it)
            ipList.add(ip)
        }

        return ipList
    }

    fun parseLine(line: String) : IP {
        val sequenceList = mutableListOf<String>()
        val hypernetSequenceList = mutableListOf<String>()

        val seqMatcher = seqPattern.matcher(line)
        val hypernetMatch = hypernetPattern.matcher(line)
        var i=0

        while(seqMatcher.find(i)) {
            sequenceList.add(seqMatcher.group(1))
            i = seqMatcher.end()

            if (hypernetMatch.find(i)) {
                hypernetSequenceList.add(hypernetMatch.group(1))
                i = hypernetMatch.end()
            }
        }

        return IP(sequenceList, hypernetSequenceList)
    }

    fun printTlsSupportedIpCount() {
        val ipList = readInput()
        val tlsSupportedIpCount = ipList.count{
            it.sequenceList.any { isAbba(it) } && it.hypernetSequenceList.none { isAbba(it) }
        }
        println("IPs that support TLS = $tlsSupportedIpCount")
    }

    /**
     * @return true if str contains ABBA (any four-character sequence which consists of a pair of two
     * different characters followed by the reverse of that pair, such as xyyx or abba)
     */
    fun isAbba(str: String) : Boolean {
        return (0..str.length-4)
                .map { str.substring(it, it +4) }
                .any { it[0] == it[3] && it[1] == it[2] && it[0] != it[1] }
    }
}

fun main(args: Array<String>) {
    Day7().printTlsSupportedIpCount()
}