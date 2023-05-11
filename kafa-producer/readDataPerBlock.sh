#!/bin/bash
while read line
do
  echo $line | nc -q 1 localhost 9997
done < EPL_20_21.csv
