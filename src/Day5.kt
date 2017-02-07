import java.security.MessageDigest
import kotlin.comparisons.compareBy

class Day5 {
    var index = 0

    fun printPassword(input: String) {
        var password = ""
        index = 0

        for (i in 1..8) {
            password += getNextPasswordCharacter(input)
            index++
        }

        println("password = $password")
    }

    fun getNextPasswordCharacter(input: String) : Char {
        while (!md5(input+index).toHexString().startsWith("00000")) {
            index++
        }

        println(md5(input+index).toHexString())

        return md5(input+index).toHexString().substring(5).first()
    }

    fun printRealPassword(input: String) {
        var instructionList = mutableListOf<Pair<Int, Char>>()
        val checkList = mutableListOf<Boolean>()

        for (i in 1..8) {
            checkList.add(false)
        }

        while (!checkAllPositionsSet(checkList)) {
            val instruction = getRealNextPasswordCharacter(input)

            if (!checkList[instruction.first]) {
                instructionList.add(instruction)
                checkList[instruction.first] = true
            }

            index++
        }

        println("real password = ${instructionList.sortedWith(compareBy { it.first })}")
    }

    fun checkAllPositionsSet(checkArray: List<Boolean>) : Boolean {
        return checkArray.size == 8 && checkArray.all { it }
    }

    fun getRealNextPasswordCharacter(input: String) : Pair<Int, Char> {
        while (!md5(input+index).toHexString().startsWith("00000")) {
            index++
        }

        println(md5(input+index).toHexString())

        val instructionStr = md5(input+index).toHexString().substring(5).substring(0, 2)
        println("instruction = " + instructionStr)

        if (!instructionStr.first().isDigit()) {
            index++
            return getRealNextPasswordCharacter(input)
        }

        val instruction = Pair(Integer.parseInt(instructionStr.first().toString()), instructionStr.last())
        if (instruction.first >= 8) {
            index++
            return getRealNextPasswordCharacter(input)
        }

        return instruction
    }

    /**
     *  Set of chars for a half-byte.
     */
    private val CHARS = arrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f')

    /**
     *  Returns the string of two characters representing the HEX value of the byte.
     */
    internal fun Byte.toHexString() : String {
        val i = this.toInt()
        val char2 = CHARS[i and 0x0f]
        val char1 = CHARS[i shr 4 and 0x0f]
        return "$char1$char2"
    }

    /**
     *  Returns the HEX representation of ByteArray data.
     */
    internal fun ByteArray.toHexString() : String {
        val builder = StringBuilder()
        for (b in this) {
            builder.append(b.toHexString())
        }
        return builder.toString()
    }

    private fun md5(text: String) : ByteArray {
        val digester = MessageDigest.getInstance("MD5")
        return digester.digest(text.toByteArray())
    }
}

fun main(args: Array<String>) {
    Day5().printRealPassword("reyedfim")
}