#!/bin/bash

if [[ -s .generator_pid ]]; then
    generatorID=$(cat .generator_pid)
else
    echo "Error: file '.generator_pid' does not exist or is empty."
    exit 1
fi

mode="add"
result=1
tail -n 0 -f pipe | while true; do
    read line
    case $line in
        "QUIT")
            kill "$generatorID"
            echo "Planned exit"
            exit 0
            ;;
        "+")
            mode="add"
            ;;
        \*)
            mode="multiply"
            ;;
        *)
            if [[ "$line" =~ ^-?[0-9]+$ ]]
            then
                if [[ "$mode" == "add" ]]
                then
                    result=$(( result + $line ))
                else
                    result=$(( result * $line ))
                fi
            else
                kill "$generatorID"
                echo "Error"
                exit 1
            fi
            ;;
    esac
    echo $result
done
