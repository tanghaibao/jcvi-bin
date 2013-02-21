kmerfreq -k 27 -l reads2cor.lst -p parrot -t 32 -i 400000000 -L 150 >kmerfreq.log 2>kmerfreq.err
correct_error -k 27 -l 2 -a 0 -e 1 -w 1 -q 35 -t 32 -j 1 parrot.freq.gz reads2cor.lst >correct.log 2>correct.err
