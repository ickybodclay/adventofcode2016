import java.io.BufferedReader
import java.io.FileReader
import java.util.regex.Pattern

class Day10 {
    data class ValueInstruction(val value: Int, val botId: Int)
    data class BotInstruction(val botId: Int, val lowDestination: String, val lowDestinationId: Int, val highDestination: String, val highDestinationId: Int)

    class Bot (botId: Int) {
        val id = botId
        val chipList = mutableListOf<Int>()
        val instructionList = mutableListOf<BotInstruction>()
    }

    val valuePattern: Pattern = Pattern.compile("""value (\d+) goes to bot (\d+)""")
    val botPattern: Pattern = Pattern.compile("""bot (\d+) gives low to (bot|output) (\d+) and high to (bot|output) (\d+)""")

    val botMap = mutableMapOf<Int, Bot>()
    val outputMap = mutableMapOf<Int, Int>()

    var checkLow: Int = 0
    var checkHigh: Int = 0

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

        while (true) {
            var hasFollowedInstruction = false

            botMap.map {
                if (it.value.chipList.size >= 2 && it.value.instructionList.size > 0) {
                    followInstruction(it.value.instructionList.removeAt(0))
                    hasFollowedInstruction = true
                }
            }

            if (!hasFollowedInstruction) break
        }
    }

    fun followInstruction(instruction: Any) {
        if (instruction is ValueInstruction) {
            //println("ValueInstruction>\t" + instruction)
            if (!botMap.containsKey(instruction.botId)) {
                botMap[instruction.botId] = Bot(instruction.botId)
            }

            botMap[instruction.botId]!!.chipList.add(instruction.value)
        }
        else if (instruction is BotInstruction) {
            //println("BotInstruction>\t\t" + instruction)
            if (!botMap.containsKey(instruction.botId)) {
                botMap[instruction.botId] = Bot(instruction.botId)
            }

            val bot = botMap[instruction.botId]
            if (bot!!.chipList.size >= 2) {
                val botInstruction: BotInstruction

                if (bot.instructionList.size > 0) {
                    botInstruction = bot.instructionList.removeAt(0)
                    bot.instructionList.add(instruction)
                }
                else {
                    botInstruction = instruction
                }

                bot.chipList.sort()
                val low = bot.chipList.first()
                val high = bot.chipList.last()

                if (checkLow == low && checkHigh == high) {
                    println("bot ${bot.id} checking low $low and high $high")
                }

                if (botInstruction.lowDestination == "bot") {
                    if (!botMap.containsKey(botInstruction.lowDestinationId)) {
                        botMap[botInstruction.lowDestinationId] = Bot(botInstruction.lowDestinationId)
                    }

                    bot.chipList.remove(low)
                    botMap[botInstruction.lowDestinationId]!!.chipList.add(low)
                }
                else if (botInstruction.lowDestination == "output") {
                    bot.chipList.remove(low)
                    outputMap[botInstruction.lowDestinationId] = low
                }

                if (botInstruction.highDestination == "bot") {
                    if (!botMap.containsKey(botInstruction.highDestinationId)) {
                        botMap[botInstruction.highDestinationId] = Bot(botInstruction.highDestinationId)
                    }

                    bot.chipList.remove(high)
                    botMap[botInstruction.highDestinationId]!!.chipList.add(high)
                }
                else if (botInstruction.highDestination == "output") {
                    bot.chipList.remove(high)
                    outputMap[botInstruction.highDestinationId] = high
                }
            }
            else {
                botMap[instruction.botId]!!.instructionList.add(instruction)
            }
        }
    }

    fun printBotNumberResponsibleForComparing(low: Int, high: Int) {
        checkLow = low
        checkHigh = high
        readInput()

        val outputsMultiplied: Int = outputMap[0]!! * outputMap[1]!! * outputMap[2]!!
        println("chip in outputs bin 0, 1, & 2 multiplied together = $outputsMultiplied")
    }
}

fun main(args: Array<String>) {
    Day10().printBotNumberResponsibleForComparing(17, 61)
}