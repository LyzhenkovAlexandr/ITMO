#!/bin/bash

result=""
while true; do
    read input
    if [[ "$input" == "q" ]]; then
        break
    fi
    result="${result}${input}"
done
echo "$result"