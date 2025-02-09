#!/bin/bash

if [[ "$PWD" = "$HOME" ]]; then
  echo "$HOME"
  exit 0
fi
echo "Error, you are not in your home directory"
exit 1
