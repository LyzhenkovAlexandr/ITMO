#!/bin/bash

awk -F, -v univ=" $1" '$2==univ {sum+=$3;count++} END {if ($count > 0) print "Средний балл для", univ ":", sum / count; else print "Вуз не найден"}' students.csv