import java.security.MessageDigest

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
    Day5().printPassword("reyedfim")
}