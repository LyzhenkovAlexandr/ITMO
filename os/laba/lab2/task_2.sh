#!/bin/bash


pgrep -f "/sbin/" | awk '{print $1}' > pid_list.txt
