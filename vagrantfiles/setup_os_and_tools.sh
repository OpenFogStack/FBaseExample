#!/bin/bash

# Update everything
sudo apt-get -y update
sudo apt-get -y upgrade

# Set Encoding
sudo echo "LANG=en_US.UTF-8" >> /etc/environment
sudo echo "LANGUAGE=en_US.UTF-8" >> /etc/environment
sudo echo "LC_ALL=en_US.UTF-8" >> /etc/environment
sudo echo "LC_CTYPE=en_US.UTF-8" >> /etc/environment

# Git
echo "Installing git"
sudo apt-get -y install git
# git config --global user.name "XX"
# git config --global user.email XXXX@XX.de

### Install java 8
echo "INSTALLING JAVA 8"
sudo apt-get -y install openjdk-8-jdk

### Install maven
echo "INSTALLING MAVEN"
sudo apt-get -y install maven
