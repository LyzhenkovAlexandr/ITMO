#!/bin/bash

> report2.log

step=0
declare -a array

while true; do
    step=$(($step + 1))
    mod=$(($step % 100000))
    array+=(1 2 3 4 5 6 7 8 9 10)
    
    if [[ $mod -eq 0 ]]; then
        cnt=$(($step * 10))
        echo "size: $cnt" >> report2.log
    fi
done