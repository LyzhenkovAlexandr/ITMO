#!/bin/bash

sudo grep -roEhI '[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,4}' /etc/ | sort | uniq | awk '{printf "%s,", $1 }' | sed 's/,$//' > emails.lst
cat emails.lst
