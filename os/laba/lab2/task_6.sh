#!/bin/bash


pid=$(ps aux --sort=-%mem | awk 'NR==2 {print $2}')
echo "Process with highest memory usage (PID: $pid):"

proc_info="/proc/$pid/status"
cat "$proc_info" | grep -E "^(Pid|Name|VmRSS)"

echo -e "\nOutput of 'top' command:"
top -o %MEM -b -n 1 | head -n 8
