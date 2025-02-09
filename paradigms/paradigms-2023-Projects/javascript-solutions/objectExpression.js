"use strict"

function OperationWithZeroArity(ev, str, pref) {
    this.evaluate = ev
    this.toString = str
    this.prefix = pref
}

function AnyOperation(sign, f, ...args) {
    this.evaluate = (...values) => f(...args.map(el => el.evaluate(...values)))
    this.toString = () => args.map(el => el + "").join(" ").concat(" ", sign)
    this.prefix = () => "(" + sign + " " + args.map(el => el.prefix()).join(" ") + ")"
}

function Const(x) {
    OperationWithZeroArity.call(this, () => Number(x), () => String(x), () => String(x))
}

function Variable(key) {
    OperationWithZeroArity.call(this, (...values) => values[mapIndex[key]], () => String(key), () => String(key))
}

const mapIndex = {"x": 0, "y": 1, "z": 2}

function Negate(...args) {
    AnyOperation.call(this, "negate", a => -a, ...args)
}

function Multiply(...args) {
    AnyOperation.call(this, "*", (a, b) => a * b, ...args)
}

function Divide(...args) {
    AnyOperation.call(this, "/", (a, b) => a / b, ...args)
}

function Add(...args) {
    AnyOperation.call(this, "+", (a, b) => a + b, ...args)
}

function Subtract(...args) {
    AnyOperation.call(this, "-", (a, b) => a - b, ...args)
}

function ArcTan(...args) {
    AnyOperation.call(this, "atan", Math.atan, ...args)
}

function ArcTan2(...args) {
    AnyOperation.call(this, "atan2", Math.atan2, ...args)
}

function Sum(...args) {
    AnyOperation.call(this, "sum", (...args) => args.reduce((a, b) => a + b, 0), ...args)
}

function Avg(...args) {
    AnyOperation.call(this, "avg", (...args) => args.reduce((a, b) => a + b, 0) / args.length, ...args)
}


const OPERATION = {
    "*": [2, Multiply, "*"],
    "/": [2, Divide, "/"],
    "+": [2, Add, "+"],
    "-": [2, Subtract, "-"],
    "negate": [1, Negate, "negate"],
    "atan": [1, ArcTan, "atan"],
    "atan2": [2, ArcTan2, "atan2"],
    "sum": [-1, Sum, "sum"],
    "avg": [-1, Avg, "avg"]
}


const VARIABLE = {
    "x": Variable,
    "y": Variable,
    "z": Variable
}

function ParserError(message) {
    Error.call(this, message)
    this.message = message
}

ParserError.prototype = Object.create(Error.prototype)
ParserError.prototype.constructor = ParserError
ParserError.prototype.name = "ParserError"

function parse(str) {
    let stackExpr = []
    str.split(" ").filter(Boolean).forEach(token => {
        if (token in OPERATION) {
            stackExpr.push(new OPERATION[token][1](...stackExpr.splice(-OPERATION[token][0])))
        } else if (token in VARIABLE) {
            stackExpr.push(new VARIABLE[token](token))
        } else {
            stackExpr.push(new Const(token))
        }
    })
    return stackExpr.pop();
}

let index = 0
function parsePrefix(str) {
    str = str.replaceAll(")", " ) ").replaceAll("(", " ( ").split(" ").filter(Boolean)
    index = 0
    let res = parseExpr(str)
    if (index !== str.length) {
        throw new ParserError("Invalid expression")
    }
    return res
}


function parseExpr(str) {
    let token = str[index++]
    if (str.length === 0) {
        throw new ParserError("Input is empty")
    }
    if (token === "(") {
        let operation = prefixHelperParse(str)
        index++
        return operation
    } else if (token in VARIABLE) {
        let variable = token
        return new VARIABLE[variable](variable)
    } else if (!isNaN(token)) {
        return new Const(Number(token))
    } else {
        throw new ParserError("Invalid token")
    }
}

function prefixHelperParse(str) {
    let operator = parseOperator(str);
    let args = parseArgs(str);
    if (!operator) {
        throw new ParserError("Wrong number of arguments")
    } else if (args.length !== operator[0] && operator[0] !== -1) {
        throw new ParserError("For function '" + operator[2] + "' expected " + operator[0] + " arguments, but found " + args.length)
    }
    return new operator[1](...args)
}

function parseOperator(str) {
    let token = str[index++]
    if (token in VARIABLE) {
        throw new ParserError("Variable not expected")
    }
    if (!isNaN(token)) {
        throw new ParserError("Const not expected")
    }
    if (token in OPERATION) {
        return OPERATION[token]
    }
}

function parseArgs(str) {
    let stackExpr = []
    let token = str[index]
    while (!(token in OPERATION) && (token !== ")")) {
        stackExpr.push(parseExpr(str))
        token = str[index]
    }
    return stackExpr
}