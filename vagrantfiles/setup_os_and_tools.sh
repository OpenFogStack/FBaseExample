#!/bin/bash

# Update everything
sudo apt-get -y upgrade
sudo apt-get -y update

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
sudo add-apt-repository -y ppa:webupd8team/java
sudo apt-get update
sudo apt-get -y upgrade
echo debconf shared/accepted-oracle-license-v1-1 select true | sudo debconf-set-selections
echo debconf shared/accepted-oracle-license-v1-1 seen true | sudo debconf-set-selections
sudo apt-get -y install oracle-java8-installer

### Install maven
echo "INSTALLING MAVEN"
sudo apt-get -y install maven
