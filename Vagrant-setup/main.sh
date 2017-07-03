sudo apt-get update

echo "Installing Java 8.."
gpg --keyserver hkp://pgp.mit.edu  --recv-keys C2518248EEA14886
oracle-java8-installer shared/accepted-oracle-license-v1-1 select true | sudo /usr/bin/debconf-set-selections
sudo add-apt-repository -y ppa:webupd8team/java
sudo apt-get update
sudo apt-get install -y oracle-java8-installer
echo "Setting environment variables for Java 8.."
sudo apt-get install -y oracle-java8-set-default

echo "Installing Maven..."
sudo apt-get install -y maven