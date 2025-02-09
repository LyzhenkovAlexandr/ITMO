#!/bin/bash


pids=$(ps -eo pid=)
tmpfile=$(mktemp)
for pid in $pids; do
    proc_dir="/proc/$pid"
    if [[ -d "$proc_dir" ]]; then
        readed=$(grep -s "read_bytes:" "$proc_dir/io" | awk '{print $2}')
        echo "$pid:$readed" >> "$tmpfile"
    fi
done

sleep 60

sort -t ':' -k2 -n -r "$tmpfile" | head -n 3 | while IFS=: read -r pid start; do
    proc_dir="/proc/$pid"
    if [[ -d "$proc_dir" ]]; then
        end=$(grep -s "read_bytes:" "$proc_dir/io" | awk '{print $2}')
        dist=$((end - start))
        cmd=$(ps -o cmd fp "$pid" | tail -1)
        echo "PID:$pid, Command:$cmd, Read Data:$dist"
    fi
done

rm "$tmpfile"