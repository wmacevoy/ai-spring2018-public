# Notes for runing on Ubuntu 16.04

1. Install ubuntu 16.04
   sudo apt install rng-tools git emacs base-devel
2. Install Oracle Java JDK 9
[https://medium.com/@shaaslam/how-to-install-oracle-java-9-in-ubuntu-16-04-671e598f0116]
sudo add-apt-repository ppa:webupd8team/java
sudo apt-get update
sudo apt install oracle-java9-installer
sudo apt install oracle-java9-set-default
javac -version
3. Install maven:
sudo apt-install gnupg
VER=3.5.2
MAJOR=${VER%%.*}
curl -o maven-$VER-bin.tar.gz http://mirrors.koehn.com/apache/maven/maven-$MAJOR/$VER/binaries/apache-maven-$VER-bin.tar.gz
curl -o maven-$VER.tar.gz.asc https://www.apache.org/dist/maven/maven-$MAJOR/$VER/binaries/apache-maven-$VER-bin.tar.gz.asc


