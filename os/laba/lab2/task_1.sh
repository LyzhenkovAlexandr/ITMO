#!/bin/bash


user_name="root"
ps -eo user:$#user_name,pid,command | awk -v target="$user_name" '$1 == target {print $2 ":" $3}' > process_list.txt
echo "$(wc -l < process_list.txt)" | cat - process_list.txt > temp && mv temp process_list.txt
