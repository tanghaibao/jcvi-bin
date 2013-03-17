#!/bin/bash

set +x
CC=/usr/local/packages/gcc-4.4.3/bin/gcc
CXX=/usr/local/packages/gcc-4.4.3/bin/g++

# Follow the instructions
# <http://sourceforge.net/apps/mediawiki/wgs-assembler/index.php?title=Check_out_and_Compile>
rm -rf wgs
mkdir wgs
cd wgs

cvs -d:pserver:anonymous@wgs-assembler.cvs.sf.net:/cvsroot/wgs-assembler login
cvs -d:pserver:anonymous@wgs-assembler.cvs.sf.net:/cvsroot/wgs-assembler co -P src

svn co https://kmer.svn.sourceforge.net/svnroot/kmer/trunk kmer
cd kmer
sh configure.sh
make -j 16
make install
cd ..

cd src
make -j 16
