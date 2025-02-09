#!/bin/bash

backupName=""
date=$(date '+%Y-%m-%d')
for savedBackup in $(ls -d ~/* | grep -E '^/home/'$USER'/Backup-[0-9]{4}-[0-9]{2}-[0-9]{2}$'); do
    savedBackupName=$(echo "$savedBackup" | awk -F/ '{print $NF}' )
    savedBackupDate=$(echo "$savedBackupName" | cut -c 8-)
    savedBackupSeconds=$(date -d "$savedBackupDate" +"%s")
    nowSeconds=$(date -d "$date" +"%s")
    diff=$(("$nowSeconds" - "$savedBackupSeconds"))
    if [[ "$diff" -lt 7*24*60*60 ]]; then
        backupName="$savedBackupName"
    fi
done

reportFile="$HOME/backup-report"
pathToSource="$HOME/source/"
if [[ -z "$backupName" ]]; then
    newBackup="$HOME/Backup-$date"
    mkdir "$newBackup"
    cp -r "$pathToSource." "$newBackup"
    echo "---------> INFO <---------" >> "$reportFile"
    echo "($date): A new backup '$newBackup' has been created" >> "$reportFile"
    for file in "$pathToSource"*; do
        if [[ -f "$file" ]]; then
            echo "The file '$file' was copied" >> "$reportFile"
        else
            echo "The directory '$file/' was copied" >> "$reportFile"
        fi
    done
else
    echo "---------> INFO <---------" >> "$reportFile"
    echo "($date): Changes have been made to the backup" >> "$reportFile"
    for file in "$pathToSource"*; do
        pathToBackupFileOrDir="$HOME/$backupName/${file#"$pathToSource"}"
        if [[ -f "$file" ]]; then
            if ! [[ -f "$pathToBackupFileOrDir" ]]; then
                cp "$file" "$pathToBackupFileOrDir"
                echo "The file '$file' was copied" >> "$reportFile"
            else
                sizeFileSource=$(du "$file" | tail -n 1 | cut -f 1)
                sizeFileBackup=$(du "$pathToBackupFileOrDir" | tail -n 1 | cut -f 1)
                if [[ "$sizeFileSource" -ne "$sizeFileBackup" ]]; then
                    mv "$pathToBackupFileOrDir" "$pathToBackupFileOrDir.$date"
                    cp "$file" "$pathToBackupFileOrDir"
                    echo "The file '$pathToBackupFileOrDir' was renamed to '$pathToBackupFileOrDir.$date'" >> "$reportFile"
                    echo "The file '$file' was copied" >> "$reportFile"
                fi
            fi
        else
            if ! [[ -d "$pathToBackupFileOrDir" ]]; then
                mkdir "$pathToBackupFileOrDir"
                echo "The directory '$file/' was created" >> "$reportFile"
            fi
        fi
    done
fi
