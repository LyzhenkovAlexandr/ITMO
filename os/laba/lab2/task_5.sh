#!/bin/bash


source="./result.txt"

temp_file=$(mktemp)
current_ppid=$(head -n 1 "$source" | tr ' : ' '=' | awk -F'=' '{print $6}')
sum_art=0
count=0

tr ' : ' '=' < $source | while IFS= read -r line; do
    ppid=$(echo "$line" | awk -F'=' '{print $6}')
    art=$(echo "$line" | awk -F'=' '{print $10}')

    if [[ "$ppid" != "$current_ppid" ]]; then
        if [[ "$count" -ne 0 ]]; then
            average_art=$(awk "BEGIN {printf \"%.6f\", $sum_art / $count}")
            echo "Average_Running_Children_of_ParentID=$current_ppid is $average_art" >> "$temp_file"
        fi

        current_ppid="$ppid"
        sum_art=0
        count=0
    fi

    sum_art=$(awk "BEGIN {printf \"%.6f\", $sum_art + $art}")
    count=$((count + 1))
    echo "$line" >> "$temp_file"
done


if [[ "$count" -ne 0 ]]; then
    average_art=$(awk "BEGIN {printf \"%.6f\", $sum_art / $count}")
    echo "Average_Running_Children_of_ParentID=$current_ppid is $average_art" >> "$temp_file"
fi

cat "$temp_file" | sed 's/===/ : /g' > res.txt
rm "$temp_file"
