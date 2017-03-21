import java.io.BufferedReader
import java.io.FileReader
import java.util.regex.Pattern

class Day10 {
    data class ValueInstruction(val value: Int, val botId: Int)
    data class BotInstruction(val botId: Int, val lowDestination: String, val lowDestinationId: Int, val highDestination: String, val highDestinationId: Int)

    val valuePattern: Pattern = Pattern.compile("""value (\d+) goes to bot (\d+)""")
    val botPattern: Pattern = Pattern.compile("""bot (\d+) gives low to (bot|output) (\d+) and high to (bot|output) (\d+)""")

    fun readInput() {
        val reader = BufferedReader(FileReader("day10_input.txt"))

        reader.readLines().map {
            val valueMatcher = valuePattern.matcher(it)

            if (valueMatcher.matches()) {
                val instruction = ValueInstruction(Integer.parseInt(valueMatcher.group(1)), Integer.parseInt(valueMatcher.group(2)))
                followInstruction(instruction)
            }

            val botMatcher = botPattern.matcher(it)
            if (botMatcher.matches()) {
                val instruction = BotInstruction(
                        Integer.parseInt(botMatcher.group(1)),
                        botMatcher.group(2),
                        Integer.parseInt(botMatcher.group(3)),
                        botMatcher.group(4),
                        Integer.parseInt(botMatcher.group(5)))
                followInstruction(instruction)
            }
        }
    }

    fun followInstruction(instruction: Any) {
        if (instruction is ValueInstruction) {
            println("ValueInstruction>\t" + instruction)
        }
        else if (instruction is BotInstruction) {
            println("BotInstruction>\t\t" + instruction)
        }
    }

    fun printBotNumberResponsibleForComparing(low: Int, high: Int) {
        readInput()

    }
}

fun main(args: Array<String>) {
    Day10().printBotNumberResponsibleForComparing(17, 61)
}