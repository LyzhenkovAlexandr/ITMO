class IntMatrix {
    val rows: Int
    val columns: Int
    private val matrix: IntArray

    constructor(rows: Int, columns: Int) {
        require(rows > 0) { "The number of lines must be positive, but is obtained 'rows = $rows'" }
        require(columns > 0) { "The number of columns must be positive, but is obtained 'columns = $columns'" }
        this.rows = rows
        this.columns = columns
        this.matrix = IntArray(rows * columns)
    }

    operator fun get(row: Int, column: Int): Int {
        return matrix[calcIndex(row, column)]
    }

    operator fun set(row: Int, column: Int, value: Int) {
        matrix[calcIndex(row, column)] = value
    }

    private fun calcIndex(row: Int, column: Int): Int {
        checkIndexes(row, column)
        return row * rows + column
    }

    private fun checkIndexes(row: Int, column: Int) {
        require(row in 0..rows) { "Row out of bounds of matrix, $row rows received, but $rows available in total" }
        require(column in 0..columns) { "Column outside the bounds of the matrix, $column columns received, but $columns available in total" }
    }
}
