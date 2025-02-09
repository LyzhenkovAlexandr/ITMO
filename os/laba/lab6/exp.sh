#!/bin/bash

log=$1
script=$2
echo "" > $log

for (( n=1; n<=20; n++ )); do
    echo "$n series: " >> $log
    for (( i=0; i<10; i++ )); do
        { \time -f "%E" ./$script $n; } 1>>.trash 2>>$log
    done
done
