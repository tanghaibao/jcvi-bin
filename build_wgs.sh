#!/bin/bash

set +x
CC=/usr/local/packages/gcc-4.4.3/bin/gcc
CXX=/usr/local/packages/gcc-4.4.3/bin/g++

# Follow the instructions
# <http://sourceforge.net/apps/mediawiki/wgs-assembler/index.php?title=Check_out_and_Compile>
#rm -rf wgs
mkdir -p wgs
cd wgs

svn co svn://svn.code.sf.net/p/wgs-assembler/svn/trunk/src src
svn co svn://svn.code.sf.net/p/kmer/code/trunk kmer

cd kmer
gmake -j 16
gmake install
cd ..

cd src
gmake -j 16
