#!/bin/bash

JAR=/vagrant/FBaseExampleClients/target/$1
ADDRESS=$2
PORT=$3
PARAMETER=$4

echo "Running $JAR to $ADDRESS:$PORT with $PARAMETER"
java -jar $JAR $ADDRESS $PORT $PARAMETER
echo "Client execution finished."
