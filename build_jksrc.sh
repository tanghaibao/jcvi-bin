#!/bin/bash

rm -rf kent
wget http://hgdownload.cse.ucsc.edu/admin/jksrc.zip
unzip jksrc.zip
rm -f jksrc.zip
cd kent/src
make
