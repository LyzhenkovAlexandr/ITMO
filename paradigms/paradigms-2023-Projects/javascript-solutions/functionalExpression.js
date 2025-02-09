"use strict"
const operation = f => (...expressions) => (...values) => f(...expressions.map(g => g(...values)));
const add = operation((a, b) => a + b)
const subtract = operation((a, b) => a - b)
const multiply = operation((a, b) => a * b)
const divide = operation((a, b) => a / b)

const negate = operation(x => -x)
const cnst = x => () => Number(x)
const variable = x => (...values) => values[takeIndex[x]]
const takeIndex = {"x": 0, "y": 1, "z": 2}
const one = cnst(1)
const two = cnst(2)
const sinh = operation(Math.sinh)
const cosh = operation(Math.cosh)
