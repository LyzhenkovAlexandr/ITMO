#!/bin/bash

sudo awk -F: '{print $1,$3}' /etc/passwd | sort -n -k2 | column -t
