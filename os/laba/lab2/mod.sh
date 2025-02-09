#!/bin/bash

current_user=$(id -u)
zombie_pids=$(ps -u $current_user -o pid= -o stat= | awk '$2=="Z" {print $1}')

if [[ -n "$zombie_pids" ]]; then
    for pid in $zombie_pids; do
        kill -9 "$pid"
    done
fi