package pw.edu.pl.domain

import java.io.File
import java.io.Serializable

class Matrix(val rows: Int, val columns: Int, private val array: Array<Array<Double>>) : Cloneable, Serializable {

    companion object {
        operator fun invoke(columns: Int, rows: Int): Matrix {
            return Matrix(rows, columns, Array(rows) { Array(columns) { 0.0 } })
        }

        operator fun invoke(filename: String): Matrix {
            val bufferedReader = File(filename).bufferedReader()

            val (rows, columns) = bufferedReader.readLine().split("\\s+".toRegex()).map { it.toInt() }

            val array = bufferedReader.useLines { sequence ->
                sequence
                    .filter { it.isNotBlank() }
                    .map { line ->
                        line.split("\\s+".toRegex())
                            .map { it.toInt().toDouble() }
                            .toTypedArray()
                    }
                    .toList()
                    .toTypedArray()
            }

            return Matrix(rows, columns, array)
        }
    }

    operator fun get(row: Int, column: Int): Double {
        return array[row][column]
    }

    operator fun set(row: Int, column: Int, t: Double) {
        array[row][column] = t
    }

    override fun toString(): String {
        return array.joinToString("\n") {
            it.contentToString()
        }
    }

    public override fun clone(): Matrix {
        return Matrix(rows, columns, array.map { it.clone() }.toTypedArray())
    }
}