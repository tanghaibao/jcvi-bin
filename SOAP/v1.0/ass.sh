#$ -S /bin/bash
SOAPdenovo-63mer pregraph -s Parrot.cfg -d 1 -p 32 -K 29 -o parrot >pregraph.log
SOAPdenovo-63mer contig -g parrot -M 3 >contig.log
SOAPdenovo-63mer map -s Parrot.map.cfg -g parrot -p 32 >map.log
SOAPdenovo-63mer scaff -g parrot -b 1.2 -F -p 32 >scaff.log
