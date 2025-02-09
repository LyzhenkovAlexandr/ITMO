#!/bin/bash

echo $$ > .pid

result=1
function add() {
    result=$(( result + 2 ))
}

function multiply() {
    result=$(( result * 2 ))
}

function killProc() {
    echo "Planned exit"
    exit
}

trap "add" USR1
trap "multiply" USR2
trap "killProc" SIGTERM

while true; do
    echo $result
    sleep 1s
done
