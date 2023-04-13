#!/bin/bash
filename=$1
tmp=$2
lines=10
linecount=$(wc -l < "$filename")
start=1
while [ $start -le $linecount ]
do
    sed -n "$start,$((start+lines-1))p" "$filename" > $tmp
    nc -l -N -p 9999 < $tmp
    start=$((start+lines))
    sleep 0.05
done
