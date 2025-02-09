#!/bin/bash

step=0
declare -a array

while true; do
    step=$(($step + 1))
    array+=(1 2 3 4 5 6 7 8 9 10)
    cnt=$(($step * 10))
    
    if [[ $cnt -gt $1 ]]
    then
        break
    fi
done