#!/bin/bash

backupName=""
date=$(date '+%Y-%m-%d')
prevDiff="-1"
for savedBackup in $(ls -d ~/* | grep -E '^/home/'$USER'/Backup-[0-9]{4}-[0-9]{2}-[0-9]{2}$'); do
    savedBackupName=$(echo "$savedBackup" | awk -F/ '{print $NF}' )
    savedBackupDate=$(echo "$savedBackupName" | cut -c 8-)
    savedBackupSeconds=$(date -d "$savedBackupDate" +"%s")
    nowSeconds=$(date -d "$date" +"%s")
    diff=$(("$nowSeconds" - "$savedBackupSeconds"))
    if [[ "$diff" -lt "$prevDiff" ]] || [[ "$prevDiff" -eq -1 ]]; then
        prevDiff="$diff"
        backupName="$savedBackupName"
    fi
done

restoreDir=~/restore
if [[ -z "$backupName" ]]; then
    echo "Could not find backup"
else
    if ! [[ -d "$restoreDir" ]]; then
        mkdir "$restoreDir"
    fi
    for file in $(ls "$HOME/$backupName"); do
        if [[ -e "$restoreDir/$file" || -d "$restoreDir/$file" ]]; then
            echo "File '$file' is already backed up"
        else
            if [[ ! "$file" =~ ^.*\.[0-9]{4}-[0-9]{2}-[0-9]{2}$ ]]; then
                cp -R "$HOME/$backupName/$file" "$restoreDir"/"$file"
            fi
        fi
    done
fi
