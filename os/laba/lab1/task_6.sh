#!/bin/bash

grep -e '(WW)' -e '(II)' /var/log/anaconda/X.log | sed -e 's/\(WW\)/Warning:/g' -e 's/\(II\)/Information:/g' > full.log
cat full.log
