#!/bin/bash

PROJECT_NAME=$1
URL=$2

# clone project if not existent
cd /home/vagrant/
if [ ! -d "$PROJECT_NAME" ]; then
  echo "Cloning $PROJECT_NAME from $URL."
  git clone $2
  cd $PROJECT_NAME
else
  echo "Directory $PROJECT_NAME found, don't need to clone."
  echo "Pulling latest changes"
  cd $PROJECT_NAME
  git pull origin master
fi

echo "Building $PROJECT_NAME"
mvn clean install package -DskipTests
