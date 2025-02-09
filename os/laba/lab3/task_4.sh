#!/bin/bash

(./infCycle.sh)&
pid1=$!
(./infCycle.sh)&
pid2=$!
(./infCycle.sh)&
pid3=$!

top -b -n 1 | grep -e "$pid1" -e "$pid2" -e "$pid3"
cpulimit -l 10 -p $pid1 &

kill $pid3
top -b -n 1 | grep -e "$pid1"
