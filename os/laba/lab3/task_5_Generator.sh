#!/bin/bash

echo $$ > .generator_pid
current_pipe=pipe

while true; do
    read line
    if [[ "$line" == "SWITCH_PIPE "* ]]; then
        new_pipe=$(echo $line | cut -d' ' -f2)
        current_pipe=$new_pipe
        if ! [[ -p $current_pipe ]]; then
            echo "Error: file '$current_pipe' does not exist."
            rm .generator_pid
            exit 1
        fi
        continue
    fi
    echo $line > $current_pipe
done
