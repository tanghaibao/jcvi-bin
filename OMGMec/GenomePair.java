 public class GenomePair{
	  
	  Block[] blocks;
	 int genomeIndex1;
	 int genomeIndex2;
	  
	  public GenomePair(GeneInfo[] g, GeneInfo[] g2, int[] w){
		  
		  for(int i = 0; i< g.length; i++){
			  if(g[i]!=null){
				  genomeIndex1= g[i].genomeIndex;
				  genomeIndex2 = g2[i].genomeIndex;
				  break;
			  }
		  }
		  int bn = 0;
		  for(int i = 0; i< g.length; i++){
			  if(g[i]!=null){
				  if(g[i].synIndex>bn){
					  bn=g[i].synIndex;
				  }
			  }
		  }
		 // System.out.println("block number "+(bn+1));
		  blocks = new Block[bn+1];
		  int[] blockLength = new int[10000];
		  
		  for(int i = 0; i < blocks.length; i++){
			  int lengthHere = getTheBlockLength2(i, g);
			  blocks[i]=new Block(lengthHere);
			//  System.out.println("lengthHere "+lengthHere);
			  blockLength[lengthHere]++;
			  int genePIndex = 0;
			  for(int j = 0; j < g.length; j++){
				  if(g[j]!=null && g[j].synIndex == i){
					  blocks[i].genepairs[genePIndex]= new GenePair(g[j],g2[j],w[j]);
					//  blocks[i].genepairs[genePIndex].printAGenePair();
					  genePIndex++;
				  }
			  }
			  
			  blocks[i].getTheSign();
		  }
		  
		  System.out.println("total number of synteny blocks:\t"+ blocks.length);
		  for(int i = 0; i< blockLength.length; i++){
			  if(blockLength[i]!=0){System.out.println(blockLength[i] + "\tblocks has \t"+ i+"\tgenepairs");}
		  }
		  
	  }
	 
	 public GeneInfo[] allGeneInfoInGenomePair(int size){
		 GeneInfo[] tmp = new GeneInfo[size];
		 int index = 0;
		 for(int i = 0; i< blocks.length; i++){
			 for(int j = 0; j< blocks[i].genepairs.length; j++){
				 tmp[index]= new GeneInfo(blocks[i].genepairs[j].gene1);
				 index++;
				 tmp[index]= new GeneInfo(blocks[i].genepairs[j].gene2);
				 index++;
			 }
		 }
		// System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&"+ index);
		 int rm = 0;
		 for(int i = 0; i< tmp.length-1; i++){
			 for(int j = i+1; j< tmp.length; j++){
				 if(tmp[i]!=null && tmp[j]!=null){
					 if(tmp[i].sameGene(tmp[j])){
						 tmp[j]=null;
						 rm++;
					 }
				 }
			 }
		 }
		 GeneInfo[] result = new GeneInfo[index-rm];
		 int rindex = 0;
		 for(int i = 0; i< tmp.length; i++){
			 if(tmp[i]!=null){
				 result[rindex]= tmp[i];
				 rindex++;
			 }
		 }
		 System.out.println("total gene in A genomepair**************"+result.length);
		 return result;
	 }
				 
	/* public boolean checkMoreBlock(){
		 for(int i = 0; i< blocks.length; i++){
			 if(blocks[i]!=null){
				 return true;
			 }
		 }
		 return false;
	 }
	 
	 public TripleBlock[] getTripleBlock(){
		 int[][] mergeBlock = new int[blocks.length*blocks.length][2];
		 for(int i = 0; i< mergeBlock.length; i++){
			 for(int j = 0; j< mergeBlock[0].length; j++){
				 mergeBlock[i][j]=-1;
			 }
		 }
		 int index = 0;
	
		 for(int i = 0; i< blocks-1; i++){
			for(int j = 0; j< blocks.length; j++){
				if(goodToMerge(blocks[i], blocks[j])){
					mergeBlock[index][0] = i;
					mergeBlock[index][1] = j;
				}
			}
		}
		 
		 boolean moreBlock = checkMoreBlock();
		 while(moreBlock = true){
			 int[] listToCombine = new int[];
			 int indexl = 0;
			 for(int i = 0; i< listToCombine.length; i++){
				 listToCombine[i] = -1;
			 }
			 // getFirstAvailbaleBlock;b 
			 
			 listToCombine[indexl] = b;
			 indexl++;
			 int sameGroupBlock = getSameGroupBlock(listToCombine, mergeBlock);
			 while(sameGroupBlock!=-1){
				 listToCombine[indexl] = sameGroupBlock;
				
				 sameGroupBlock = getSameGroupBlock(listToCombine, mergeBlock);
			 }
			 GenePair[] tmp = new GenePair[];
			 for(int i = 0; iM listToCombine.length; i++){
				 if(listToCombine[i]!=null){
					// include all gene pairs in these blocks here;
					 // get a tripleBlock;
				 }
			 }
			 for(int i = 0; iM listToCombine.length; i++){
				 if(listToCombine[i]!=null){
					 blocks[listToCombine[i]]=null;
				 }
			 }
		 }
			 
	 }
	 */
	 public GenomePair(GeneInfo[] g, GeneInfo[] g2, boolean ordered, int[] w){
		 genomeIndex1= g[0].genomeIndex;
		 genomeIndex2 = g2[0].genomeIndex;
		 int bn = 0;
		 for(int i = 0; i< g.length; i++){
			 if(g[i]!=null){
				 if(g[i].synIndex>bn){
					 bn=g[i].synIndex;
				 }
			 }
		 }
	//	System.out.println("block number "+(bn+1));
		 blocks = new Block[bn+1];
		 
		 for(int i = 0; i < blocks.length; i++){
			 int lengthHere = getTheBlockLength2(i, g);
			 blocks[i]=new Block(lengthHere);
		//	 System.out.println("lengthHere "+lengthHere);
			 int genePIndex = 0;
			 for(int j = 0; j < g.length; j++){
				 if(g[j]!=null && g[j].synIndex == i){
					 if(ordered==true){
						 blocks[i].genepairs[genePIndex]= new GenePair(g[j],g2[j],w[j]);}
					 else{
						 GeneInfo corrgi = getTheCorresGeneInfo(g[j],g2);
						  blocks[i].genepairs[genePIndex]= new GenePair(g[j],corrgi,w[j]);
					 }
					// blocks[i].genepairs[genePIndex].printAGenePair();
					 genePIndex++;
				 }
			 }
			 blocks[i].getTheSign();
		 }
		 
		 
	 }
	  
	 public GeneInfo getTheCorresGeneInfo(GeneInfo agi, GeneInfo[] gis){
		 for(int i = 0; i< gis.length; i++){
			 if(gis[i]!=null && gis[i].newName.equals(agi.newName)){
				 return gis[i];
			 }
		 }
		 return null;
	 }
	 public int getTheBlockLength2(int bi, GeneInfo[] gs){
		 int result = 0;
		 for(int i = 0; i< gs.length; i++){
			 if(gs[i]!=null && gs[i].synIndex==bi){
				 result++;
			 }
		 }
		 return result;
	 }
	 
	 
	  public int getTheBlockLength(int bi, GeneInfo[] gs){
		  for(int i = 0; i< gs.length; i++){
			  if(gs[i]!=null && gs[i].synIndex==bi){
				  return gs[i].synSize;
			  }
		  }
		  return -1;
	  }
	 
	/* public void mergeBlock(){
		 for(int i = 0; i< blocks.length; i++){
			 for(int j = i+1; j<blocks.length; j++){
				 if(GoodToMerge(blocks[i].blocks[j])){
				 }
			 }
		 }
	 }*/
	 public GeneInfo[] getGeneInfo2(int gi, OrthologSet[] cg, GenomePair[] gps){
		 GeneInfo[] result = new GeneInfo[cg.length];
		 for(int i = 0; i< cg.length; i++){
			 String gn =  cg[i].gns[gi];
			 GeneInfo ag = findTheGeneInfo(gps, gi, gn);
			 result[i] = new GeneInfo(ag);
			 result[i].newName = (new Integer(i+1)).toString();
		 }
		 return result;
	 }
	 
	 
	 public GeneInfo[] getGeneInfo4(int gi, OrthologSet[] cg, GenomePair[] gps, int pInCG){
		 GeneInfo[] result = new GeneInfo[cg.length];
		 for(int i = 0; i< cg.length; i++){
			 String gn =  cg[i].gns[pInCG];
			 GeneInfo ag = findTheGeneInfo(gps, gi, gn);
			 result[i] = new GeneInfo(ag);
			 result[i].newName = (new Integer(i+1)).toString();
		 }
		 return result;
	 }
	 
	 
	 public GeneInfo[] getGeneInfo3(int gi, OrthologSet[] cg, GenomePair[] gps){
		 GeneInfo[] result = new GeneInfo[cg.length];
		 for(int i = 0; i< cg.length; i++){
			 String gn =  cg[i].gns[4];
			 GeneInfo ag = findTheGeneInfo(gps, gi, gn);
			 result[i] = new GeneInfo(ag);
			 result[i].newName = (new Integer(i+1)).toString();
		 }
		 return result;
	 }
	 
	 public GeneInfo findTheGeneInfo(GenomePair[] gps, int gihere, String gn){
		 for(int i = 0; i< gps.length; i++){ if(gps[i]!=null && gps[i].genomeIndex1==gihere || gps[i].genomeIndex2==gihere){
			 for(int j = 0; j<gps[i].blocks.length; j++){ if(gps[i].blocks[j]!=null){
				 for(int k = 0; k< gps[i].blocks[j].genepairs.length; k++){ if(gps[i].blocks[j].genepairs[k]!=null){
					 if(gps[i].blocks[j].genepairs[k].gene1.genomeIndex==gihere && gps[i].blocks[j].genepairs[k].gene1.name.equals(gn)){
						 return gps[i].blocks[j].genepairs[k].gene1;
					 }
					 if(gps[i].blocks[j].genepairs[k].gene2.genomeIndex==gihere && gps[i].blocks[j].genepairs[k].gene2.name.equals(gn)){
						 return gps[i].blocks[j].genepairs[k].gene2;
					 }
				 }
				 }
			 }}}}
		 return null;
					 
	 }
	 
	 public String findTheCorrespondGene(String gn){
		 for(int i = 0; i< blocks.length; i++){
			 if(blocks[i]!=null){
				 for(int j = 0; j< blocks[i].genepairs.length; j++){
					 if(blocks[i].genepairs[j]!=null){
						 String gn1 = blocks[i].genepairs[j].gene1.name;
						  String gn2 = blocks[i].genepairs[j].gene2.name;
						 if(gn1.equals(gn)){return gn2;}
						 if(gn2.equals(gn)){return gn1;}
					 }
				 }
			 }
		 }
		 return null;
	 }
	 
	 public boolean sameBlockDiploidToTetra(int b1i, int b2i){
		 return true;
	 }
	 
	 public boolean sameBlockABBA(int b1i, int b2i){
		 Block b1 = blocks[b1i];
		 Block b2 = blocks[b2i];
		 int l1 = b1.blockLength;
		 int l2 = b2.blockLength;
		 int sharedgp = 0;
		 for(int i = 0; i<l1; i++){
			 boolean has = containThisGenePair(b1.genepairs[i], b2);
			 if(has==true){
				 sharedgp++;
			 }
		 }
		 if(sharedgp*2>l1 && sharedgp*2>l2){
			 System.out.println(b1i+"\t"+b2i+ "\t"+l1+"\t"+l2+"\t"+sharedgp); 
			 return true;
		 }
		 return false;
	 }
	 
	 public boolean containThisGenePair(GenePair ap, Block ab ){
		 for(int i = 0; i< ab.genepairs.length; i++){
			 if(ab.genepairs[i]!=null){
				 if(ap.sameEdge(ap, ab.genepairs[i])==true){
					 return true;
				 }
			 }
		 }
		 return false;
	 }
	 
	 
	 public int[] getTheBlockIndex(String gn){
		int[] tmp = new int[10];
		int tmpIndex = 0;
		for(int i = 0; i< blocks.length; i++){
			if(blocks[i]!=null){
				for(int j = 0; j< blocks[i].genepairs.length; j++){
					if(blocks[i].genepairs[j]!=null){
						String gn1 = blocks[i].genepairs[j].gene1.name;
						String gn2 = blocks[i].genepairs[j].gene2.name;
						if(gn1.equals(gn) || gn2.equals(gn) ){ tmp[tmpIndex]=i;tmpIndex++;}

					}
				}
			}
		}
		 int[] result = new int[tmpIndex];
		 for(int i = 0; i< tmpIndex; i++){
			 result[i] = tmp[i];
		 }
		 return result;
 }

						 
					 
		  
	
	  public GenomePair(GenomePair agp){
		  blocks = new Block[agp.blocks.length];
		  for(int i = 0; i< blocks.length; i++){
			  blocks[i] = new Block(agp.blocks[i]);
		  }
	  }
  }