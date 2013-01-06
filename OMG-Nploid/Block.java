public class Block{
	
	int blockLength;
	GenePair[] genepairs;
	String sign;
	
	/*public Block(GenePair[] gps, int bl ){
		blockLength = bl;
		genepairs = new GenePair[gps.length];
		for(int i = 0; i< gps.length; i++){
			if(gps[i]!=null){
				genepairs[i] = gps[i];
			}
		}
	}*/
	
	public Block(int l){
		blockLength=l;
		genepairs = new GenePair[l];
	}
	
	public Block(Block ab){
		blockLength=ab.blockLength;
		genepairs = new GenePair[ab.genepairs.length];
		for(int i = 0; i<genepairs.length; i++){
			genepairs[i]=new GenePair(ab.genepairs[i]);
		}
		sign= ab.sign;
	}
	
	public void getTheSign(){
		if(genepairs.length==1){sign = "+"; return;}
		//GenePair[] tmp = new GenePair[genepairs.length];
		GeneInfo[] g1 = new GeneInfo[genepairs.length];
		GeneInfo[] g2 = new GeneInfo[genepairs.length];
		for(int i = 0; i< genepairs.length; i++){
			g1[i] = new GeneInfo(genepairs[i].gene1);
			g1[i].nnIndex = i+1;
			g2[i] = new GeneInfo(genepairs[i].gene2);
			g2[i].nnIndex= i+1;
		}
		
			for(int i = 0; i< g1.length-1; i++){
			for(int j = i+1; j< g1.length; j++){
				if(g1[i].positionStart>g1[j].positionStart){
					GeneInfo tmp = g1[i];
					g1[i]=g1[j];
					g1[j] = tmp;
				}
				if(g2[i].positionStart>g2[j].positionStart){
					GeneInfo tmp = g2[i];
					g2[i]=g2[j];
					g2[j] = tmp;
				}
				
			}
		}
		
		/*System.out.println("list in g1");
		for(int i = 0; i< g1.length; i++){
			System.out.println("name: "+ g1[i].name+ "  start :"+g1[i].positionStart+"  nnIndex: "+g1[i].nnIndex);
		}
		System.out.println("list in g2");
		for(int i = 0; i< g2.length; i++){
			System.out.println("name: "+ g2[i].name+ "  start :"+g2[i].positionStart+"  nnIndex: "+g2[i].nnIndex);
		}
		*/
	
		int sameOrder = 0;
		int diffOrder = 0;
		for(int i = 0; i< g1.length-1;i++){
			for(int j = i+1; j< g1.length; j++){
				int p1 = getG2Position(g1[i].nnIndex, g2);
				int p2 = getG2Position(g1[j].nnIndex,g2);
				if(p1>p2){diffOrder++;}
				if(p1<p2){sameOrder++;}
			}
		}
		//System.out.println("diffOrder "+diffOrder+ "   sameOrder "+sameOrder);
		if(diffOrder>sameOrder){
			sign = "-";
		}else{
			sign = "+";
		}
	//	System.out.println("diffOrder "+diffOrder+ "   sameOrder "+sameOrder+ "   sign "+sign);
		
	}
	
	public int getG2Position(int ai, GeneInfo[] g2){
		for(int i = 0; i< g2.length; i++){
			if(g2[i].nnIndex==ai){
				return i;
			}
		}
		return -1;
	}
	
			
		
	/*public void addAGenePair(GeneInfo g1, GeneInfo g2, int index){
		genepairs[index] = new GenePair(g1,g2);
	}*/
		
			
}

		
