#!/bin/bash


temp_file=$(mktemp)

for pid_dir in /proc/[0-9]*/; do
    if [[ -d "$pid_dir" ]]; then
      pid=$(basename "$pid_dir")

      ppid=$(grep -E '^PPid:' "$pid_dir/status" | awk '{print $2}')

      sum_exec_runtime=$(grep -E '^se.sum_exec_runtime' "$pid_dir/sched" | awk '{print $3}')
      nr_switches=$(grep -E '^nr_switches' "$pid_dir/sched" | awk '{print $3}')

      if [[ "$nr_switches" -ne 0 ]]; then
          art=$(awk "BEGIN {printf \"%.6f\", $sum_exec_runtime / $nr_switches}")
      else
          art=0
      fi

      echo "ProcessID=$pid : Parent_ProcessID=$ppid : Average_Running_Time=$art" >> "$temp_file"
    fi
done

sorted_result=$(sort -t'=' -k3 -n "$temp_file")
echo "$sorted_result" > result.txt
rm "$temp_file"
