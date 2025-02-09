#!/bin/bash

> status_parameters_mem_bash
> status_parameters_memory
> first_5_processes

exec "./mem.bash" &
sleep 1

while true; do
    first_proc=$(top -b -n1)
    script_info=$(echo "$first_proc" | grep mem.bash)

    if [[ "$script_info" == "" ]]; then
        break
    fi

    echo "$first_proc" | grep МиБ >> status_parameters_memory
    echo "$first_proc" | grep mem.bash >> status_parameters_mem_bash
    echo "$first_proc" | head -12 | tail -5 >> first_5_processes
    echo >> first_5_processes
    sleep 2
done

sudo dmesg | grep "mem.bash" | tail -n 2 >> status_parameters_mem_bash
