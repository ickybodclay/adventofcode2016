class Day13 {
    var board: Array<BooleanArray> = Array(0, { BooleanArray(0 )})

    fun printFewestNumberOfStepsRequired(x: Int, y: Int, favoriteNumber: Int) {
        board = Array(y, {BooleanArray(x)})

        for (i in 0..board.size-1) {
            for (j in 0..board[i].size-1) {
                board[i][j] = isWall(i, j, favoriteNumber)
            }
        }

        printGrid(board)

        // TODO will have to grow grid if x,y not reachable from 1,1
    }

    fun printGrid(board: Array<BooleanArray>) {
        for (row in board) {
            for (i in row) {
                print("${if (i) '#' else '.'}")
            }
            println()
        }
    }

    fun isWall(x: Int, y: Int, favoriteNumber: Int) : Boolean {
        val sum = x*x + 3*x + 2*x*y + y + y*y + favoriteNumber
        return numberOfSetBits(sum) % 2 != 0
    }

    fun numberOfSetBits(i: Int): Int {
        var j = i
        j -= (j shr 1 and 0x55555555)
        j = (j and 0x33333333) + (j shr 2 and 0x33333333)
        return (j + (j shr 4) and 0x0F0F0F0F) * 0x01010101 shr 24
    }
}

fun main(args: Array<String>) {
    Day13().printFewestNumberOfStepsRequired(31, 39, 1364)
}