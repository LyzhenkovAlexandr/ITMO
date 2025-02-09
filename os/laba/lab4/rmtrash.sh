#!/bin/bash

if [[ $# -ne 1 ]]; then
    echo "Expected one argument, but found '$#'"
    exit 1
fi

if ! [[ -d ~/.trash ]]; then
	  mkdir ~/.trash
fi

if ! [[ -f ~/.trash/.nextSuffix ]]; then
	  echo "0" > ~/.trash/.nextSuffix
fi

fileName=$1
if ! [[ -f "$fileName" ]]; then
    echo "File '$fileName' does not exist"
    exit 1
fi

suffixID=$(cat ~/.trash/.nextSuffix)
ln "$fileName" ~/.trash/"$suffixID"
echo "$(realpath "$fileName"):$suffixID" >> ~/.trash.log
suffixID=$(( "$suffixID" + 1 ))
echo "$suffixID" > ~/.trash/.nextSuffix
rm "$fileName"
