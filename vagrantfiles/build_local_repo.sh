#!/bin/bash

DIRECTORY=$1
PROJECT_NAME=$2

cd $DIRECTORY
if [ ! -d "$PROJECT_NAME" ]; then
  echo "PROJECT DOES NOT EXIST!!"
else
  echo "Building $PROJECT_NAME"
  cd $PROJECT_NAME
  mvn clean install package -DskipTests
fi
