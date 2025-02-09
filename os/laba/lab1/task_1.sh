#!/bin/bash

n=3
if [[ "$#" -ne "$n" ]]; then
    echo "Expected to get $n parameter, but found $#"
    exit 1
fi

if [[ "$1" -gt "$2" ]] && [[ "$1" -gt "$3" ]]; then
    echo "Max: $1"
elif [[ "$2" -gt "$1" ]] && [[ "$2" -gt "$3" ]]; then
    echo "Max: $2"
else
    echo "Max: $3"
fi
