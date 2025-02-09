#!/bin/bash

sudo find /var/log/ -type f -name "*.log" -exec cat {} \; | wc -l
