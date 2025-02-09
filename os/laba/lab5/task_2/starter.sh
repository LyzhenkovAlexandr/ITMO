#!/bin/bash

N=$1
K=$2

for i in $(seq 1 $K); do
	exec "./newmem.bash" "$N" &
	sleep 1
done
