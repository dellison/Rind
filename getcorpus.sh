#!/bin/sh

mkdir resources
cd resources

echo "Downloading Enron spam corpus to ./resources directory..."
wget -nv http://www.aueb.gr/users/ion/data/enron-spam/preprocessed/enron1.tar.gz
tar xzf enron1.tar.gz

wget -nv http://www.aueb.gr/users/ion/data/enron-spam/preprocessed/enron2.tar.gz
tar xzf enron2.tar.gz

wget -nv http://www.aueb.gr/users/ion/data/enron-spam/preprocessed/enron3.tar.gz
tar xzf enron3.tar.gz

wget -nv http://www.aueb.gr/users/ion/data/enron-spam/preprocessed/enron4.tar.gz
tar xzf enron4.tar.gz

wget -nv http://www.aueb.gr/users/ion/data/enron-spam/preprocessed/enron5.tar.gz
tar xzf enron5.tar.gz

wget -nv http://www.aueb.gr/users/ion/data/enron-spam/preprocessed/enron6.tar.gz
tar xzf enron6.tar.gz

wget -nv http://www.aueb.gr/users/ion/data/enron-spam/readme.txt

echo "Download complete."

echo "Cleaning up..."
rm enron1.tar.gz
rm enron2.tar.gz
rm enron3.tar.gz
rm enron4.tar.gz
rm enron5.tar.gz
rm enron6.tar.gz

echo "Complete."
