#!/bin/bash

if [[ $# -ne 1 ]]; then
    exit 1
fi
echo $1
number=$1
file_name="data/test$number"

if ! [[ -f "$file_name" ]]; then
    exit 1
fi

line=$(cat "$file_name")
for num in $line; do
    res=$(($num * 2))
	  echo -n "$res " >> "$file_name"
done
