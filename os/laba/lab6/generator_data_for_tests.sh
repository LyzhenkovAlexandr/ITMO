#!/bin/bash

if [[ $# -eq 0 ]]; then
	  exit 1
fi

if [[ -d data ]]; then
	  rm -r data
fi
mkdir data

for ((i=1; i<=$1; i++)); do
    filename="data/test$i"
    > "$filename"
	  for ((j=0; j<$2; j++)); do
       	echo -n $((RANDOM % 10))" " >> "$filename"
   	done
done
