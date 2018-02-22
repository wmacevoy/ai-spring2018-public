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

````bash
sudo apt install gnupg
cd ~/Downloads
VER=3.5.2
MAJOR=${VER%%.*}
FILE=apache-maven-$VER-bin.tar.gz
curl -o $FILE http://mirrors.koehn.com/apache/maven/maven-$MAJOR/$VER/binaries/$FILE
curl -o $FILE.asc https://www.apache.org/dist/maven/maven-$MAJOR/$VER/binaries/$FILE.asc
curl -o $FILE.keys https://www.apache.org/dist/maven/KEYS
gpg --import $FILE.keys
if ! gpg --verify $FILE.asc
then
   echo "corrupted file $FILE"
   exit 1
fi
sudo tar -C /usr/local -xf $FILE
cd /usr/local/bin
sudo ln -s ../apache-maven-$VER/bin/mvn mvn
````

3. Get project files

````bash
PROJECT=ai-spring2018-public
PROJECT_DIR=$HOME/projects/$PROJECT

mkdir -p $(basename $PROJECT_DIR)
cd $(basename $PROJECT_DIR)
git clone git@github.com:wmacevoy/$PROJECT.git
````

4. Compile parts

````bash
PROJECT=ai-spring2018-public
PROJECT_DIR=$HOME/projects/$PROJECT
cd $PROJECT_DIR/tictactoe-java
mvn compile
mvn test
mvn install
cd $PROJECT_DIR/gradientdescent
mvn compile
mvn test
mvn exec:java -Dexec.mainClass=tictactoeopt.OptMinMaxAgent
````
