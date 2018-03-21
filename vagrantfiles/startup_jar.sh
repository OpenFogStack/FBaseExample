#!/bin/bash

JAR=$1
PROPERTIES=$2
LOG=$3

echo "Starting $1 with properties stored at $2."
java -jar $JAR $PROPERTIES > $3 &
echo "Started jar."
