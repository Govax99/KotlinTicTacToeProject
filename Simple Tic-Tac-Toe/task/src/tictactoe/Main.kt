package tictactoe

import java.lang.NumberFormatException
import kotlin.math.abs


fun fromBoardToString(board: MutableList<MutableList<Char>>): String {
    val str = mutableListOf<String>("")
    for (i in 0 until 3) {
        for (j in 0 until 3) {
            str.add(board[i][j].toString())
        }
    }
    return str.joinToString("")
}

fun allSecondaryDiagonal(board: MutableList<MutableList<Char>>, char: Char): Boolean {
    return (board[0][2] == char) && (board[1][1] == char) && (board[2][0] == char)
}

class TicTacToe() {
    var board = MutableList<MutableList<Char>>(3) { mutableListOf<Char>('_', '_', '_')}

    fun initializeBoard() {
        val initString: String = readln()
        var k = 0
        for (i in 0 until 3) {
            for (j in 0 until 3) {
                board[i][j] = initString[k]
                k++
            }
        }
    }

    fun initializeBoardStatic(initString: String) {
        var k = 0
        for (i in 0 until 3) {
            for (j in 0 until 3) {
                board[i][j] = initString[k]
                k++
            }
        }
    }

    fun transposeBoard(): MutableList<MutableList<Char>> {
        val boardTranspose = MutableList<MutableList<Char>>(3) { mutableListOf<Char>('_', '_', '_')}
        var k = 0
        for (i in 0 until 3) {
            for (j in 0 until 3) {
                boardTranspose[j][i] = board[i][j]
                k++
            }
        }
        return boardTranspose
    }

    fun printBoard() {
        println("-".repeat(9))
        board.forEach { println("| ${it.joinToString(" ")} |") }
        println("-".repeat(9))
    }

    fun playerWon(char: Char): Boolean {
        val allRows = board.any { it.all { x -> x == char } }
        val boardTranspose = this.transposeBoard()
        val allColumns = boardTranspose.any { it.all { x -> x == char } }
        val stringBoard = fromBoardToString(board)
        val stringBoardTranspose = fromBoardToString(boardTranspose)
        val allDiagonalPrimary = stringBoard.filterIndexed { ind, _ -> ind % 4 == 0}.all { it == char }
        val allDiagonalSecondary = allSecondaryDiagonal(board, char)
        return allRows || allColumns || allDiagonalPrimary || allDiagonalSecondary

    }

    fun isNotFinished(): Boolean {
        val hasEmpty = board.any { it.any { x -> x == '_' } }
        return (!this.playerWon('X') && !this.playerWon('O') &&  hasEmpty)
    }

    fun isDraw(): Boolean {
        val hasEmpty = board.any { it.any { x -> x == '_' } }
        return (!this.playerWon('X') && !this.playerWon('O') &&  !hasEmpty)
    }

    fun isImpossible(): Boolean {
        val bothWon = this.playerWon('X') && this.playerWon('O')
        val stringBoard = fromBoardToString(board)
        val numX = stringBoard.count { it == 'X' }
        val numO = stringBoard.count { it == 'O' }
        val tooMuch = abs(numX - numO) >= 2
        return bothWon || tooMuch
    }

    fun analyzeBoard() {
        if(this.isImpossible()) {
            println("Impossible")
        } else if (this.isDraw()) {
            println("Draw")
        } else if (this.isNotFinished()) {
            println("Game not finished")
        } else if (this.playerWon('X')) {
            println("X wins")
        } else if (this.playerWon('O')) {
            println("O wins")
        }
    }

    fun checkIfWonDraw(): Boolean {
        if (this.playerWon('X')) {
            println("X wins")
            return true
        } else if (this.playerWon('O')) {
            println("O wins")
            return true
        } else if (this.isDraw()) {
            println("Draw")
            return true
        }
        return false
    }

    fun makeMove(char: Char) {
        try {
            val (x, y) = readln().split(" ").map { it.toInt() - 1 }
            if (x !in 0..2 || y !in 0..2) {
                println("Coordinates should be from 1 to 3!")
                this.makeMove(char)
            } else if (board[x][y] == 'X' || board[x][y] == 'O') {
                println("This cell is occupied! Choose another one!")
                this.makeMove(char)
            } else {
                board[x][y] = char
                this.printBoard()
            }
        } catch (e: NumberFormatException) {
            println("You should enter numbers!")
            this.makeMove(char)
        }

    }
}

fun main() {
    // write your code here
    val game = TicTacToe()
    val players = mutableListOf<Char>('X', 'O')
    game.initializeBoardStatic("_".repeat(9))
    game.printBoard()
    var endGame = false
    var turnCount = 0
    while (!endGame) {
        game.makeMove(players[turnCount % 2])
        endGame = game.checkIfWonDraw()
        turnCount++
    }
}