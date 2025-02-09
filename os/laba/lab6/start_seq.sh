#!/bin/bash

for (( i=1; i <= $1; i++ )); do
	  ./calculation.sh $i
done
