#!/bin/bash

if [[ $# -ne 1 ]]; then
	  exit 1
fi

calc_sum() {
    local x=$1
    local sum=1
    for ((i = 1; i <= 100; i++)); do
        sum=$(bc -l <<< "$sum + $x / $i")
    done
    echo $sum
}

echo $(calc_sum $1)
