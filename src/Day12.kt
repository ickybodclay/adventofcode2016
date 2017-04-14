import java.io.BufferedReader
import java.io.FileReader
import java.util.regex.Pattern

class Day12 {

    val instructionPattern: Pattern = Pattern.compile("""([a-z]+)(?:\s(\w+))(?:\s(\w+))?+""")

    data class Instruction(val action: String, val x: String, val y: String?)

    fun readInput(): List<Instruction> {
        val instructionList = mutableListOf<Instruction>()
        val reader = BufferedReader(FileReader("day12_input.txt"))

        reader.readLines().map {
            val instructionMatcher = instructionPattern.matcher(it)

            if (instructionMatcher.matches()) {

                val instruction = Instruction(instructionMatcher.group(1), instructionMatcher.group(2), instructionMatcher.group(3))
                instructionList.add(instruction)
                println(instruction)
            }
        }

        return instructionList
    }

    fun printRegisterValue(label: String) {
        val registerMap = mutableMapOf<String, Int>()
        val instructionList = readInput()

        for ((index, value) in instructionList.withIndex()) {
            when (value.action) {
                "cpy" -> cpy(registerMap, value.x, value.y!!)
                "inc" -> inc(registerMap, value.x)
                "dec" -> dec(registerMap, value.x)
                "jnz" -> {
                    val distance = jnz(value.x, value.y!!, index)
                    if (distance != 0) {

                    }
                }
            }
        }

        println("value of register $label = ${registerMap[label]}")
    }

    fun cpy(registerMap: MutableMap<String, Int>, x: String, y: String) {
        val xVal: Int

        if (isNumeric(x)) {
            xVal = x.toInt()
        } else {
            xVal = registerMap[x]!!.toInt()
        }

        registerMap[y] = xVal
    }

    fun inc(registerMap: MutableMap<String, Int>, x: String) {
        if (!registerMap.containsKey(x)) {
            registerMap.put(x, 0)
        }

        registerMap[x] = registerMap[x]!!.plus(1)
    }

    fun dec(registerMap: MutableMap<String, Int>, x: String) {
        if (!registerMap.containsKey(x)) {
            registerMap.put(x, 0)
        }

        registerMap[x] = registerMap[x]!!.plus(-1)
    }

    fun jnz(x: String, y: String, i: Int): Int {
        if (isNumeric(x) && x.toInt() == 0) return 0
        return i + y.toInt()
    }

    fun isNumeric(str: String): Boolean {
        try {
            str.toInt()
            return true
        } catch (e: NumberFormatException) {
            return false
        }
    }
}

fun main(args: Array<String>) {
    Day12().printRegisterValue("a")
}