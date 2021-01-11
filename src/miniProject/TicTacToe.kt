package miniProject

var x : Int = 0
var y : Int = 0

fun main() {
    val board = Array(3) { CharArray(3) }

    initBoard(board)

    val names = arrayOf("Player 1", "Player 2")
    val marks = arrayOf('O', 'X')

    play(board, names, marks)
}

fun initBoard(board: Array<CharArray>){
    for (i in 0 .. 2){
        for(j in 0 ..2){
            board[i][j] = ' '
        }
    }
}

fun printBoard(board: Array<CharArray>) {
    print("  ")

    for (x in 0 ..2) print("$x ")
    println()

    for (y in 0 ..2) {
        print("$y ")
        for (x in 0..2) {
            print("${board[y][x]}")
            if (x!=2) print("|")
        }
        println()

        if (y != 2){
            print("  ")
            for (x in 0 .. 2){
                print("-")
                if (x!=2) print("+")
            }
            println()
        }
    }

}

val isInRange = {x: Int,y: Int -> x in 0 .. 2 && y in 0 .. 2 }

fun isValid(board: Array<CharArray>, x: Int, y:Int): Boolean{
    return isInRange(x,y) && board[y][x] == ' '
}

fun playerInput(name: String, board: Array<CharArray>):Boolean{
    print("$name 입력(줄,칸):")
    val input: String? = readLine()
    val token = input?.split(",")
    y = token?.get(0)?.toInt() ?: 0
    x = token?.get(1)?.toInt() ?: 0
    if (!isValid(board,x,y)) return false
    return true
}

fun isWin(board: Array<CharArray>, x: Int, y:Int) : Boolean{

    val dx = arrayOf(-1,1,0,0,-1,1,1,-1)
    val dy = arrayOf(0,0,-1,1,-1,1,-1,1)

    for (d in 0..3){
        var count = 1
        for (b in 0 ..1){
            val index = 2 * d +b
            var cx = x + dx[index]
            var cy = y + dy[index]
            while(isInRange(cx,cy)){
                if (board[cy][cx] == board[y][x]) count++
                cx += dx[index]
                cy += dy[index]
            }
        }
        if(count == 3) return true
    }
    return false
}

fun play(board: Array<CharArray>,name:Array<String>,marks:Array<Char>){
    var round = 0
    var turn = 0

    while (true){
        println("\n ${turn+1}번째 턴\n")
        printBoard(board)
        if(!playerInput(name[turn],board))
            continue
        board[y][x] = marks[turn]
        round++

        if (isWin(board,x,y)) {
            printBoard(board)
            println("${name[turn]} 승리!")
            break
        } else if (round == 9) {
          println("무승부!")
          break
        }

        when(turn) {
            0 -> turn = 1
            1 -> turn = 0
        }
    }

}