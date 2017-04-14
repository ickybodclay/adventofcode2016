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
            }
        }

        return instructionList
    }

    fun printRegisterValue(label: String) {
        val registerMap = mutableMapOf<String, Int>()
        val instructionList = readInput()

        registerMap.put("a", 0)
        registerMap.put("b", 0)
        registerMap.put("c", 0)
        registerMap.put("d", 0)

        var i = 0
        while (i < instructionList.size) {
            val instruction = instructionList[i]
            println("# $instruction")

            var hasJumped = false

            when (instruction.action) {
                "cpy" -> cpy(registerMap, instruction.x, instruction.y!!)
                "inc" -> inc(registerMap, instruction.x)
                "dec" -> dec(registerMap, instruction.x)
                "jnz" -> {
                    val jumpToDistance = jnz(registerMap, instruction.x, instruction.y!!)
                    if (jumpToDistance != 0) {
                        i += jumpToDistance
                        hasJumped = true
                    }
                }
            }

            if (!hasJumped) i++
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
        registerMap[x] = registerMap[x]!!.plus(1)
    }

    fun dec(registerMap: MutableMap<String, Int>, x: String) {
        registerMap[x] = registerMap[x]!!.minus(1)
    }

    fun jnz(registerMap: MutableMap<String, Int>, x: String, y: String): Int {
        val xVal: Int

        if (isNumeric(x)) {
            xVal = x.toInt()
        } else {
            xVal = registerMap[x]!!.toInt()
        }

        if (xVal != 0)
            return y.toInt()

        return 0
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