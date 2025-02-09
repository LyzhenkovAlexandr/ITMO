#!/bin/bash

while true; do
    clear
    echo "Please enter the number of the desired item"
    echo "1) Open editor nano"
    echo "2) Open editor vi"
    echo "3) Open browser links"
    echo "4) Exit"

    read input
    case "$input" in
        1)
            nano
        ;;
        2)
            vi
        ;;
        3)
            links
        ;;
        4)
            exit 0
        ;;
        *)
        ;;
    esac
done
