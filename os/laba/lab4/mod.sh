#!/bin/bash

declare -A equals_files
declare -A array_index
duplicate_count=0

function calculate_hash {
    md5sum "$1" | awk '{ print $1 }'
}

function check_for_duplicates {
    for file in "$1"/*; do
        if [[ -f "$file" ]]; then
            hash=$(calculate_hash "$file")
            if [[ -n "${equals_files[$hash]}" ]]; then
                equals_files[$hash]="${equals_files[$hash]} $file"
            else
                equals_files[$hash]="$file"
                array_index[$duplicate_count]="$hash"
                duplicate_count=$((duplicate_count+1))
            fi
        elif [[ -d "$file" ]]; then
            check_for_duplicates "$file"
        fi
    done
}

function print_duplicates {
    if [[ $duplicate_count -eq 0 ]]; then
        echo "No duplicates found."
    else
        echo "Duplicates:"
        for ((i=0; i<$duplicate_count; i++)); do
            echo "$((i+1))) ${equals_files[${array_index[$i]}]}"
        done

    fi
}

if [[ $# -eq 0 ]]; then
    echo "Usage: $0 <directory>"
    exit 1
fi

directory="$1"

if ! [[ -d "$directory" ]]; then
    echo "Error: The specified directory does not exist."
    exit 1
fi

check_for_duplicates "$directory"
print_duplicates
