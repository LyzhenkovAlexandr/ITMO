#!/bin/bash

if [[ $# -ne 1 ]]; then
    echo "Expected one argument, but found '$#'"
    exit 1
fi

if ! [[ -d ~/.trash ]]; then
    echo "The directory '.trash' file is missing from the home directory."
    exit 1
fi

if ! [[ -f ~/.trash.log ]]; then
    echo "The '.trash.log' file is missing from the home directory."
    exit 1
fi

cat ~/.trash.log | while IFS= read -r line; do
    originalPathFile=$(echo "$line" | awk -F: '{print $1}')
    strongLink=$(echo "$line" | awk -F: '{print $2}')
    if ! [[ -f ~/.trash/$strongLink ]] && ! [[ -d ~/.trash/$strongLink ]]; then
        continue
    fi
    fileNameFromOrigPath=$(basename "$originalPathFile")
    if [[ "$fileNameFromOrigPath" == "$1" ]]; then
        echo "$originalPathFile"
        while true; do
            read -rp "Do you want to restore? (y/n): " <&1 answer
            case $answer in
                [Yy]* ) break;;
                [Nn]* ) break;;
                * ) ;;
            esac
        done
        if [[ "$answer" == "y" ]] || [[ "$answer" == "Y" ]]; then
            dirNameFromOrigPath=$(dirname "$originalPathFile")
            if ! [[ -d "$dirNameFromOrigPath" ]]; then
                echo "The directory '$dirNameFromOrigPath' is missing, so let's restore the file in the home directory"
                dirNameFromOrigPath=$HOME
            fi

            while true; do
                if [[ -f "$dirNameFromOrigPath/$fileNameFromOrigPath" ]] || [[ -d "$dirNameFromOrigPath/$fileNameFromOrigPath" ]]; then
                    read -rp "There is a name conflict, please write a new name: " <&1 fileNameFromOrigPath
                else
                    break
                fi
            done
            ln ~/.trash/$strongLink $dirNameFromOrigPath/$fileNameFromOrigPath
            rm ~/.trash/$strongLink
            echo "Recovery was successful"
            exit
        fi
    fi
done
