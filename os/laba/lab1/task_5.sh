#!/bin/bash

sudo awk '$2 == "INFO"' /var/log/anaconda/syslog > info.log
cat info.log
