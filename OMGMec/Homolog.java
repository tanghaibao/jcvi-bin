
import java.util.*;
import java.io.*;

public class Homolog{
	GenePair[] genepairs;
	
	public Homolog(){
		genepairs = new GenePair[20];
	}
	
	public Homolog(GenePair[] gps){
		genepairs = new GenePair[gps.length];
		genepairs = gps;
	}
	
	public boolean belongsToGroup(GenePair agp){
		for(int i  = 0; i< genepairs.length; i++){
			if(genepairs[i]!=null){
				GeneInfo g1 = genepairs[i].gene1;
				GeneInfo g2 = genepairs[i].gene2;
				if(g1.sameGene(agp.gene1) || g1.sameGene(agp.gene2) || g2.sameGene(agp.gene1) || g2.sameGene(agp.gene2)){
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean hasPair(GenePair ap, GenePair[] gps){
		for(int i = 0; i< gps.length; i++){
			if(ap.sameEdge(ap, gps[i])){
				return true;
			}
		}
		return false;
	}
	public boolean sameHomolog(Homolog ah){
		if(genepairs.length!=ah.genepairs.length){
			return false;
		}
		for(int i = 0; i< genepairs.length; i++){
			if(hasPair(genepairs[i], ah.genepairs)==false){
				return false;
			}
		}
		return true;
	}
	
	public void addToGroup(GenePair agp){
		//boolean here = belongsToGroup(agp);
		boolean here = true;
		if(here == true){
			boolean added = false;
			for(int i = 0; i< genepairs.length; i++){
				if(genepairs[i]==null){
					genepairs[i] = new GenePair(agp);
					added=true;
					break;
				}
			}
			if(added==false){
				GenePair[] tmp = new GenePair[genepairs.length*2];
				for(int i = 0; i< genepairs.length; i++){
					tmp[i] = genepairs[i];
				}
				for(int i = 0; i< tmp.length; i++){
					if(tmp[i]==null){
						tmp[i] = new GenePair(agp);
						break;
					}
				}
				genepairs = new GenePair[tmp.length];
				genepairs=tmp;
			}
		}
		//return here;
	}
	
	public boolean sameGroup(Homolog h1, Homolog h2){
		for(int i = 0; i< h1.genepairs.length; i++){
			if(h1.genepairs[i]!=null){
			for(int j = 0; j < h2.genepairs.length; j++){
				if(h2.genepairs[j]!=null){
					boolean onegeoup = h1.genepairs[i].gene1.sameGene(h2.genepairs[j].gene1);
					if(onegeoup==true){return true;}
					onegeoup = h1.genepairs[i].gene1.sameGene(h2.genepairs[j].gene2);
					if(onegeoup==true){return true;}
					onegeoup = h1.genepairs[i].gene2.sameGene(h2.genepairs[j].gene1);
					if(onegeoup==true){return true;}
					onegeoup = h1.genepairs[i].gene2.sameGene(h2.genepairs[j].gene2);
					if(onegeoup==true){return true;}
				}
			}
			}
		}
		return false;
	}
	
	
	
			
	public boolean hasGenesForAllGenome(int genomeNumber){
		int[] gn = new int[genomeNumber];
		for(int i = 0; i<genepairs.length; i++){
			if(genepairs[i]!=null){
				gn[genepairs[i].gene1.genomeIndex] ++;
				gn[genepairs[i].gene2.genomeIndex] ++;
			}
		}
		for(int i = 0; i< gn.length; i++){
			if(gn[i]==0){return false;
			}
		}
		return true;
		
	}
	
	public boolean hasThisGene(String gn){
		for(int i = 0; i< genepairs.length; i++){
			if(genepairs[i]!=null){
				if(genepairs[i].gene1.name.equals(gn)  || genepairs[i].gene2.name.equals(gn)){
					return true;
				}
			}
		}
		return false;
	}
	
	public GeneInfo[] getGeneInfoInGenome(GenePair[] gps, int gi){
		GeneInfo[] tmp = new GeneInfo[gps.length*2];
		int index = 0;
		for(int i = 0; i< gps.length; i++){
			GeneInfo gi1 = gps[i].gene1;
			GeneInfo gi2 = gps[i].gene2;
			if(gi1.genomeIndex == gi){
				tmp[index] = gi1;
				index++;
			}
			if(gi2.genomeIndex ==  gi){
				tmp[index] = gi2;
				index++;
			}
		}
		GeneInfo[] result = new GeneInfo[index];
		for(int i = 0; i< result.length; i++){
			result[i] = tmp[i];
		}
		return result;
	}
	
	public int getIndexOfTetraEdges(Homolog[] tes, int tg){
		for(int i = 0; i< tes.length; i++){
			if(tes[i].genepairs[0].gene1.genomeIndex == tg && tes[i].genepairs[0].gene2.genomeIndex == tg){
				return i;
			}
		}
		return -1;
	}
	
	public GenePair[] getTetraEdge(GeneInfo[] tetraGenes, Homolog tetraEdges){
		GenePair[] edgesToBeAdd = new GenePair[tetraEdges.genepairs.length];
		int index = 0;
		for(int i = 0; i< tetraEdges.genepairs.length; i++){
			if(tetraEdges.genepairs[i]!=null){
				if(inGeneList(tetraGenes, tetraEdges.genepairs[i].gene1, tetraEdges.genepairs[i].gene2)==true){
					edgesToBeAdd[index] = tetraEdges.genepairs[i];
					index++;
				}
			}
		}
		GenePair[] result = new GenePair[index];
		for(int i = 0; i< result.length; i++){
			result[i] = edgesToBeAdd[i];
		}
		return result;
	}
	
	public boolean inGeneList(GeneInfo[] geneList, GeneInfo g1, GeneInfo g2){
		boolean has1 = false;
		boolean has2 = false;
		for(int i = 0; i< geneList.length; i++){
			if(g1.sameGene(geneList[i])){
				has1 = true;
			}
			if(g2.sameGene(geneList[i])){
				has2 = true;
			}
			if(has1==true && has2==true){
				return true;
			}
		}
		return false;
	}
		
	
	public void combineTwoGroup(Homolog h2){
		//System.out.println("combine");
		GenePair[] totalgp = new GenePair[genepairs.length+h2.genepairs.length];
		int indexh = 0;
		for(int i = 0; i< genepairs.length; i++){
			if(genepairs[i]!=null){
				totalgp[indexh] = genepairs[i];
				indexh++;
			}
		}
		for(int i = 0; i< h2.genepairs.length; i++){
			if(h2.genepairs[i]!=null){
				totalgp[indexh] = h2.genepairs[i];
				indexh++;
			}
		}
		genepairs = new GenePair[totalgp.length];
		genepairs = totalgp;
		
	}
	
	public void printAHomolog(){
		//System.out.println();
		System.out.println();
		System.out.println("a group of genes  :length ="+genepairs.length );
		for(int i = 0; i< genepairs.length; i++){
			if(genepairs[i]!=null){
				genepairs[i].printAGenePair();
				System.out.println();
			//System.out.print("(");genepairs[i].gene1.printGene();genepairs[i].gene2.printGene();System.out.println("  "+genepairs[i].gene1.synSize+")");//System.out.println("-----------");
			}}
	}
	

	public Homolog(Homolog ah){
		genepairs=new GenePair[ah.genepairs.length];
		for(int i = 0; i< genepairs.length; i++){
			if(ah.genepairs[i]!=null){
				genepairs[i] = new GenePair(ah.genepairs[i]);
			}
		}
	}
	
	
	public Homolog[] copyHomolog(Homolog[] hs){
		Homolog[] result = new Homolog[hs.length];
		for(int i = 0; i< result.length; i++){
			result[i] = new Homolog(hs[i]);
		}
		return result;
	}
	
	public boolean notInList(String ag, GeneInfo[] gis){
		for(int i = 0; i< gis.length; i++){
			if(gis[i]!=null){
				if(gis[i].name.equals(ag)){
					return false;
				}
			}
		}
		return true;
	}
	
		
	//public int countTheBiggestGeneNumber(Homolog ah){
		
	public GeneInfo[] getAllGeneList(Homolog ah){
		GeneInfo[] allGenes = new GeneInfo[ah.genepairs.length*2];
		//int[] frequence = new int[allGenes.length];
		
		int index = 0;
		for(int i = 0; i< ah.genepairs.length; i++){
			if(ah.genepairs[i]!=null){
				String g1 = ah.genepairs[i].gene1.name;
				String g2 = ah.genepairs[i].gene2.name;
				if(notInList(g1, allGenes)==true){
					allGenes[index] = ah.genepairs[i].gene1;
					//frequence[index] = 1;
					index++;
				}
				if(notInList(g2,allGenes)==true){
					allGenes[index] = ah.genepairs[i].gene2;
					//frequence[index] = 1;
					index++;
				}
			}
		}
		GeneInfo[] genes = new GeneInfo[index];
		//System.out.println("All gene in this group "+genes.length);

		for(int i = 0; i< genes.length; i++){
			genes[i] = allGenes[i];
		//	genes[i].printGene();
		}
		for(int i = 0; i< genes.length-1; i++){
			for(int j = i+1; j< genes.length; j++){
				if(genes[i].genomeIndex>genes[j].genomeIndex){
					GeneInfo tmp =  genes[j];
					genes[j] = genes[i];
					genes[i] = tmp;
				}
			}
		}
		//for(int i = 0; i< genes.length; i++){
		//	genes[i].printGene();
		//}
		return genes;
		
	}
	
	public int findTheEntry(GeneInfo[] gl, String ag){
		for(int i = 0; i<gl.length; i++){
			if(gl[i].name.equals(ag)){
				return i;
			}
		}
		System.out.println("something wrong in findTheEntry");
		return -1;
	}
	
	public void printAMatrix(int[][] m){
		for(int i = 0; i< m.length; i++){
			for(int j = 0; j< m.length; j++){
				System.out.print(m[i][j]+"  ");
			}
			System.out.println();
		}
	}
	public int[][] getGenePairMatrix(GeneInfo[] genes, Homolog ah){
		//System.out.println("----"+genes.length);
		int[][] matrix = new int[genes.length][genes.length];
		for(int i = 0; i< ah.genepairs.length; i++){
			if(ah.genepairs[i]!=null){
				String g1 = ah.genepairs[i].gene1.name;
				String g2 = ah.genepairs[i].gene2.name;
				int entry1 = findTheEntry(genes, g1);
				int entry2 = findTheEntry(genes, g2);
				matrix[entry1][entry2] = 1;
				matrix[entry2][entry1] = 1;
			}
		}
		for(int i = 0; i< matrix.length; i++){
			matrix[i][i] = 1;
		}
	//	System.out.println("a matrix ");
	//	printAMatrix(matrix);
		return matrix;
	}
	
	
	
	public int[][] getGenePairWeightMatrix(GeneInfo[] genes, Homolog ah){
		//System.out.println("----"+genes.length);
		int[][] matrix = new int[genes.length][genes.length];
		for(int i = 0; i< ah.genepairs.length; i++){
			if(ah.genepairs[i]!=null){
				String g1 = ah.genepairs[i].gene1.name;
				String g2 = ah.genepairs[i].gene2.name;
				int entry1 = findTheEntry(genes, g1);
				int entry2 = findTheEntry(genes, g2);
				matrix[entry1][entry2] = ah.genepairs[i].weight;
				matrix[entry2][entry1] =  ah.genepairs[i].weight;
			}
		}
		for(int i = 0; i< matrix.length; i++){
			matrix[i][i] = 100;
		}
		//	System.out.println("a matrix ");
		//	printAMatrix(matrix);
		return matrix;
	}
	
	public int[][] MatrixMultiple(int[][] matrix1, int[][] matrix2){
		//System.out.println("--MatrixMultiplication: input :");
		//printAMatrix(matrix1);
		//printAMatrix(matrix2);
		//System.out.println("--MatrixMultiplication: output :");
		int[][] result = new int[matrix1.length][matrix1.length];
		for(int i = 0; i< matrix1.length; i++){
			for(int j = 0; j<matrix1[0].length; j++){
				result[i][j] = 0;
				for(int k = 0; k<matrix1.length; k++){
					result[i][j] = result[i][j]+ matrix1[i][k]*matrix2[k][j];
					if(result[i][j]>0){result[i][j]=1;}
				}
			}
		}
		//printAMatrix(result);
		return result;
	}
	
	public int[][] MatrixAddition(int[][] matrix1, int[][] matrix2){
		int[][] result = new int[matrix1.length][matrix1.length];
		for(int i = 0; i< matrix1.length; i++){
			for(int j = 0; j<matrix1[0].length; j++){
				result[i][j] = matrix1[i][j]+matrix2[i][j];
				if(result[i][j]>0){result[i][j]=1;}
			}
		}
		return result;
	}
	
	public boolean sameMatrix(int[][] matrix1, int[][] matrix2){
		for(int i = 0; i< matrix1.length; i++){
			for(int j = 0; j< matrix1.length; j++){
				if(matrix1[i][j]!=matrix2[i][j]){
					return false;
				}
			}
		}
		//System.out.print("t");
		return true;
	}
		
	public int[] checkGoodMatric(int[][] matrix, GeneInfo[] allGenes, int genomeNumber, int numberOfEdges, int numberOfTransitivity){
		//System.out.println("genomeNumber "+ genomeNumber);
		/*int[][] finalMatrix = new int[matrix.length][matrix.length];
		int[][] an =new int[matrix.length][matrix.length];
		for(int i = 0; i< matrix.length; i++){
			for(int j  =0; j < matrix.length; j++){
				finalMatrix[i][j] = matrix[i][j];
				an[i][j] = matrix[i][j];
			}
		}
		for(int i = 0; i<numberOfTransitivity; i++){
			an = finalMatrix;
			finalMatrix = MatrixMultiple(finalMatrix, matrix);
			if(sameMatrix(finalMatrix,an)){
				break;
			}
		}*/
	 //warshall method
		int[][] finalMatrix = new int[matrix.length][matrix.length];
		for(int i = 0; i< matrix.length; i++){
			for(int j  =0; j < matrix.length; j++){
				finalMatrix[i][j] = matrix[i][j];
			}
		}
		for(int k = 0; k< matrix.length; k++){
			for(int i = 0; i< matrix.length; i++){
				if(finalMatrix[i][k]==1){
					for(int j = 0; j< matrix.length; j++){
						if(finalMatrix[i][j] == 0 && finalMatrix[k][j] == 0){
							finalMatrix[i][j] = 0;
						}else{
							finalMatrix[i][j] = 1;
						}
					}
				}
			}
		}
						
		// 10 genomes
	
		
		//System.out.println("int check good matrix final matrix ");
		//printAMatrix(finalMatrix);
		int badUnit = 0;
		int emptyUnit = 0;
		int goodUnit = 0;
		int ones = 0;
		
		for(int i = 0; i< finalMatrix.length; i++){
			for(int j = 0; j< finalMatrix[0].length; j++){
				if(finalMatrix[i][j] == 1){
					ones++;
				}
			}
		}
		int goodOnes = 0;  
		for(int i = 0 ; i< matrix.length; i++){//for each gene
			for(int j = 0; j < genomeNumber; j++){  //for each genome
				int positiveNumber = 0;
				int totalNumber = 0;
				int genekindex = -1;
				for(int k = 0; k< matrix.length; k++){
					if(allGenes[k].genomeIndex==j){
						genekindex = k;
						totalNumber++;
						if(finalMatrix[i][k]>0){
							positiveNumber++;
						}
					}
				}
			//	if(allGenes[i].diploid==true){System.out.println("i a deploid");}else{System.out.println("i a tetra");}
			//	if(genekindex == -1 || allGenes[genekindex].diploid==true){System.out.println("j a deploid");}else{System.out.println("j a tetra");}
				
			//	System.out.println("****for a gene,totalUnit"+totalNumber+"   positiveNumber "+positiveNumber);
				
				boolean findPosition = false;
				if(findPosition == false && positiveNumber==1 && allGenes[i].diploid==true && allGenes[genekindex].diploid == true){
					goodUnit++;
					findPosition=true;
					goodOnes = goodOnes+1;
					//System.out.println("case1");
				}
				if(findPosition== false && (positiveNumber==1 || positiveNumber==2) && allGenes[i].diploid==false && allGenes[genekindex].diploid == false){
					goodUnit++;
					findPosition=true;
					goodOnes = goodOnes+positiveNumber;
					//System.out.println("case2");
				}
				if(findPosition== false && (positiveNumber==1|| positiveNumber==2) && allGenes[i].diploid==true && allGenes[genekindex].diploid == false){
					goodUnit++;
					findPosition=true;
					goodOnes = goodOnes+positiveNumber;
					//System.out.println("case3");
				}
				if(findPosition== false && positiveNumber==1 && allGenes[i].diploid==false && allGenes[genekindex].diploid == true){
					goodUnit++;
					findPosition=true;
					goodOnes = goodOnes+positiveNumber;
					//System.out.println("case4");
				}
				
				if(findPosition== false && totalNumber>0 && positiveNumber==0 || genekindex==-1){
					emptyUnit++;
					findPosition=true;
					//System.out.println("case5");
				}
				
				if(findPosition== false ){
					badUnit++;
					findPosition=true;
				}
								
				
			}
		}
		int[] result = new int[4];
		result[0] = goodUnit;
		result[1]= emptyUnit;
		result[2] = badUnit;
		result[3] = ones-goodOnes;
		
		return result;
	}
	
	
	public int getVertexIndex(GeneInfo ag, GeneInfo[] ags){
		for(int i = 0; i< ags.length; i++){
			if(ags[i]!=null && ags[i].name.equals(ag.name)){
				return i;
			}
		}
		return -1;
	}
	
	public int getGeneListInAGenome(Homolog ah, int g){
		GeneInfo[] genes =  new GeneInfo[ah.genepairs.length*2];
		int index = 0;
		for(int i = 0; i< ah.genepairs.length; i++){
			if(ah.genepairs[i]!=null){
				String g1 = ah.genepairs[i].gene1.name;
				String g2 = ah.genepairs[i].gene2.name;
				if(ah.genepairs[i].gene1.genomeIndex==g && notInList(g1, genes)==true){
					genes[index] = ah.genepairs[i].gene1;
					index++;
				}
				if(ah.genepairs[i].gene2.genomeIndex==g && notInList(g2,genes)==true){
					genes[index] = ah.genepairs[i].gene2;
					index++;
				}
			}
		}
		return index;
	}
	public int[] getJsBasedOni(int i, int[][] matrix){
		int[] tmp = new int[matrix.length];
		int index = 0;
		for(int j = 0; j< matrix.length; j++){
			if(matrix[i][j] == 1){
				tmp[index] = j;
				index++;
			}
		}
		int[] result = new int[index];
		for(int j = 0; j< result.length; j++){
			result[j] = tmp[j];
		}
		return result;
	}
		
	public int[][] matrixAfterDropEdges(int[][] GenePairMatrix,int[][] weightMatrix, GeneInfo[] allGenes, int genomeNumber, int numberOfEdges, int numberOfTransitivity){
		int[] goodMatrix = checkGoodMatric(GenePairMatrix,allGenes, genomeNumber,numberOfEdges, numberOfTransitivity);
		Random r = new Random(0);
		while(goodMatrix[2]!=0){
		//	System.out.println("*******bad one****************");
			int besti = -1;
			int bestj = -1;
			int besti2 = -1;
			int bestj2 = -1;
			int biggestGoodChange = -1000;
			int biggestOneChange = 0;
			int smallestWeight = 100;
			//	System.out.println("    value before  a try:"+goodMatrix[0]+ "  "+goodMatrix[1] + "  ,"+goodMatrix[2]+"  "+ goodMatrix[3]);
			//int biggestBadChange = 0;
			//	System.out.println("biggestGoodChange "+ biggestGoodChange);
			//printAMatrix(GenePairMatrix);
			for(int i = 0; i< GenePairMatrix.length; i++){
				for(int j = i+1; j< GenePairMatrix.length; j++){
					if(GenePairMatrix[i][j]==1){
						int[][] matrixDropAnEdge = new int[GenePairMatrix.length][GenePairMatrix.length];
						for(int k = 0; k<matrixDropAnEdge.length; k++){
							for(int l = 0; l<matrixDropAnEdge.length; l++){
								matrixDropAnEdge[k][l] = GenePairMatrix[k][l];
							}
						}
						matrixDropAnEdge[i][j] = 0;
						matrixDropAnEdge[j][i] = 0;
						int[] goodMatrix2 = checkGoodMatric(matrixDropAnEdge,allGenes,genomeNumber,numberOfEdges, numberOfTransitivity);
						
						if(goodMatrix2[0]-goodMatrix[0]==biggestGoodChange && goodMatrix[3]-goodMatrix2[3]==biggestOneChange && weightMatrix[i][j]==smallestWeight){
							int replace = r.nextInt(2);
							//	System.out.println("Here" );
							if(replace==1){
							biggestGoodChange = goodMatrix2[0]-goodMatrix[0];
							biggestOneChange = goodMatrix[3]-goodMatrix2[3];
							smallestWeight = weightMatrix[i][j];																												 
							besti = i;
								bestj = j;}
						}
						
						
						
						
						if(goodMatrix2[0]-goodMatrix[0]==biggestGoodChange && goodMatrix[3]-goodMatrix2[3]==biggestOneChange && weightMatrix[i][j]<smallestWeight){
							//	System.out.println("Here" );
							biggestGoodChange = goodMatrix2[0]-goodMatrix[0];
							biggestOneChange = goodMatrix[3]-goodMatrix2[3];
							smallestWeight = weightMatrix[i][j];																												 
							besti = i;
							bestj = j;
						}
						if(goodMatrix2[0]-goodMatrix[0]>biggestGoodChange  || goodMatrix2[0]-goodMatrix[0]==biggestGoodChange && goodMatrix[3]-goodMatrix2[3]>biggestOneChange){
							//	System.out.println("Here" );
							biggestGoodChange = goodMatrix2[0]-goodMatrix[0];
							biggestOneChange = goodMatrix[3]-goodMatrix2[3];
							smallestWeight = weightMatrix[i][j];	
							besti = i;
							bestj = j;
						}
					}
				}
			}
			//	System.out.println("============== best i amd best j : "+ besti+ "   "+ bestj);
			if(besti==-1){
				System.out.println("!!!!!!!!!!");
				besti=besti2;
				bestj = bestj2;
			}
			//	System.out.print("d");
			//System.out.print("drop the line "+besti+"  ,  "+ bestj);
			GenePairMatrix[besti][bestj] = 0;
			GenePairMatrix[bestj][besti] = 0;
			goodMatrix = checkGoodMatric(GenePairMatrix,allGenes,genomeNumber,numberOfEdges, numberOfTransitivity);
			//System.out.println(" :"+ goodMatrix[0]+"-" +goodMatrix[1]+"-"+goodMatrix[2]);
		}
		int[][] orderedIndex = getOrderedIndex(weightMatrix);
		for(int i = 0; i< orderedIndex.length; i++){
			if(orderedIndex[i][2]!=0){
				int indexi = orderedIndex[i][0];
				int indexj = orderedIndex[i][1];
				GenePairMatrix[indexi][indexj] = 1;
				GenePairMatrix[indexj][indexi] = 1;
				int[] goodMatrix2 = checkGoodMatric(GenePairMatrix,allGenes,genomeNumber,numberOfEdges, numberOfTransitivity);
				if(goodMatrix2[2]!=0){
					GenePairMatrix[indexi][indexj] = 0;
					GenePairMatrix[indexj][indexi] = 0;
				}
			}
		}
		return GenePairMatrix;
	}
	
	public int[][] getOrderedIndex(int[][] w){
		int[][] indexAndWeight = new int[w.length*w.length][3];
		int index = 0;
		for(int i = 0; i< w.length-1; i++){
			for(int j = i+1; j<w.length; j++){
				if(w[i][j]!=0){
					indexAndWeight[index][0] = i;
					indexAndWeight[index][1] = j;
					indexAndWeight[index][2] = w[i][j];
					index++;
				}
			}
		}
		for(int i = 0; i< indexAndWeight.length-1; i++){
			for(int j = i+1; j<indexAndWeight.length; j++){
				if(indexAndWeight[i][2]<indexAndWeight[j][2]){
					int temp1 = indexAndWeight[i][0];
					int temp2 = indexAndWeight[i][1];
					int temp3 = indexAndWeight[i][2];
					indexAndWeight[i][0] = indexAndWeight[j][0];
					indexAndWeight[i][1] = indexAndWeight[j][1];
					indexAndWeight[i][2] = indexAndWeight[j][2];
					
					indexAndWeight[j][0] = temp1;
					indexAndWeight[j][1] = temp2;
					indexAndWeight[j][2] = temp3;
				}
				
			}
		}
		return indexAndWeight;
	}
	
	
	public Homolog[] checkGoodOrNot(Homolog ah, int genomeNumber){
		Homolog[] result = new Homolog[1];
		GeneInfo[] allGenes = getAllGeneList(ah);
		//System.out.println(allGenes.length);
		int[][] GenePairMatrix = getGenePairMatrix(allGenes, ah);
		int numberOfEdges = ah.genepairs.length;
		
		int[] units= checkGoodMatric(GenePairMatrix, allGenes, genomeNumber,numberOfEdges,5000);
		//System.out.println("-----"+units[2]);
		if(units[2]==0){
			result[0] = ah;
		}else{
			result=new Homolog[0];
		}
		return result;
	}
		
	
	public Homolog[] minimumDeletion2(Homolog ah, int genomeNumber, int[] numberOfTransitivity){
		Calendar time = Calendar.getInstance();
		long totalTime = 0;
		long st =0;
		GeneInfo[] allGenes = getAllGeneList(ah);
		//System.out.println(allGenes.length);
		
		int remainEdges1 = 0;
		int remainEdges2 = 0;
		int[][] GenePairMatrix = getGenePairMatrix(allGenes, ah);
		int[][] GenePairWeightMatrix = getGenePairWeightMatrix(allGenes,ah);
		int numberOfEdges = ah.genepairs.length;
	
			for(int ntindex = 0; ntindex< numberOfTransitivity.length; ntindex++){
				int nt = numberOfTransitivity[ntindex];
				GenePairMatrix = matrixAfterDropEdges(GenePairMatrix, GenePairWeightMatrix, allGenes,genomeNumber,numberOfEdges, nt);
			}
			GenePair[] gps = new GenePair[ah.genepairs.length];
			int index = 0;
			for(int i = 0; i< GenePairMatrix.length; i++){
				for(int j = i+1; j < GenePairMatrix.length; j++){
					if(GenePairMatrix[i][j]>0){
						gps[index] = new GenePair(allGenes[i],allGenes[j], GenePairWeightMatrix[i][j]);
						index++;
					}
				}
			}
			AllHomolog result = new AllHomolog(gps,genomeNumber);
	
		for(int i = 0; i< result.homologs.length; i++){
			remainEdges1 = remainEdges1+ result.homologs[i].genepairs.length;
			
			GenePair[] tmp = new GenePair[ah.genepairs.length];
			int index2 = 0;
			
			GeneInfo[] geneInASet = getAllGeneList(result.homologs[i]);
			for(int j = 0; j< geneInASet.length-1; j++){
				for(int k = j+1; k< geneInASet.length; k++){
					GenePair agp = aEdge(geneInASet[j], geneInASet[k], ah.genepairs);
					if(agp!=null){
						tmp[index2] = agp;
						index2++;
					}
				}
			}
			GenePair[] now = new GenePair[index2];
			for(int m = 0; m< now.length; m++){
				now[m] = tmp[m];
			}
			result.homologs[i] = new Homolog(now);
	
			remainEdges2 = remainEdges2+ result.homologs[i].genepairs.length;
			
		}
	
			return result.homologs;
	}
	
	
	public GenePair aEdge(GeneInfo g1, GeneInfo g2, GenePair[] edges){
		for(int i = 0; i< edges.length; i++){
			if(edges[i].gene1.sameGene(g1) && edges[i].gene2.sameGene(g2)){
				return new GenePair(edges[i]);
			}
			if(edges[i].gene1.sameGene(g2) && edges[i].gene2.sameGene(g1)){
				return new GenePair(edges[i]);
			}
		}
		return null;
	}
	public int[] getGenesConnectToThisGene(int[][] m, int i){
		int[] tmp = new int[m.length];
		int index = 0;
		for(int j = 0; j< m.length; j++){
			if(m[i][j]>0){
				tmp[index] = j;
				index++;
			}
		}
		int[] result = new int[index];
		for(int j = 0; j< result.length; j++){
			result[j] = tmp[j];
		}
		return result;
	}
	
	
	
	
	public Homolog[] divideIntoSubgraph(int[][] matrix, GeneInfo[] ags, int genomeNumber, int lengthOfBigGraph){
			
		//find a group of gene with the most of inGroup edges
		int geneIndex = -1;
		int biggest = -1;
		for(int i = 0; i< matrix.length; i++){
			int ones = 0;
			int[] gl = getGenesConnectToThisGene(matrix,i);
			for(int j = 0; j< gl.length-1; j++){
				for(int k = j+1; k<gl.length; k++){
					if(matrix[gl[j]][gl[k]]>0 || matrix[gl[k]][gl[j]]>0){ones++;}
				}
			}
			if(ones>biggest){
				biggest = ones;
				geneIndex = i;
			}
		}
		
		GenePair[] gphere = new GenePair[biggest];
		int gpIndex = 0;
		int[] gl = getGenesConnectToThisGene(matrix, geneIndex);
		for(int j = 0; j< gl.length-1; j++){
			for(int k = j+1; k<gl.length; k++){
				if(matrix[gl[j]][gl[k]]>0 || matrix[gl[k]][gl[j]]>0){
					gphere[gpIndex] = new GenePair(ags[gl[j]],ags[gl[k]]);
					gpIndex++;
				}
			}
		}
		for(int i = 0; i< matrix.length; i++){
			for(int j = 0; j<gl.length; j++){
				matrix[i][gl[j]] = 0;
				matrix[gl[j]][i] = 0;
			}
		}
		
		GenePair[] leftGp = new GenePair[lengthOfBigGraph];
		int leftIndex = 0;
		for(int i = 0; i< matrix.length; i++){
			for(int j = i+1; j< matrix.length; j++){
				if(matrix[i][j] >0 && i!=j){
					leftGp[leftIndex] = new GenePair(ags[i], ags[j]);
					leftIndex++;
				}
			}
		}
		Homolog theOne = new Homolog(gphere);
		AllHomolog left = new AllHomolog(leftGp,genomeNumber);
		Homolog[] result = new Homolog[left.homologs.length+1];
		result[0] = theOne;
		for(int i = 0; i<result.length-1; i++){
			result[i+1] = left.homologs[i];
		}
		
		return result;
	}
	
	

	
		
	public void orderGenePair(){
		int number = 0;
		for(int i = 0; i< genepairs.length; i++){
			if(genepairs[i]!=null){
				number++;
			}
		}
		GenePair[] tmp = new GenePair[number];
		int tmpIndex = 0;
		for(int i = 0; i< genepairs.length; i++){
			if(genepairs[i]!=null){
				tmp[tmpIndex] = genepairs[i];
				tmpIndex++;
			}
		}
		
		for(int i = 0; i< tmp.length-1; i++){
			for(int j = i+1; j< tmp.length; j++){
				if(tmp[i].gene1.genomeIndex>tmp[j].gene1.genomeIndex){
					GenePair agp = tmp[i];
					tmp[i] = tmp[j];
					tmp[j]=agp;
				}
				if(tmp[i].gene1.genomeIndex==tmp[j].gene1.genomeIndex  && tmp[i].gene2.genomeIndex>tmp[j].gene2.genomeIndex){
					GenePair agp = tmp[i];
					tmp[i] = tmp[j];
					tmp[j]=agp;
				}
			/*	if(tmp[i].gene1.genomeIndex==tmp[j].gene1.genomeIndex  && tmp[i].gene2.genomeIndex==tmp[j].gene2.genomeIndex){
					int nn1 = (new Integer(tmp[i].gene1.newName)).intValue();
					int nn2 = (new Integer(tmp[j].gene1.newName)).intValue();
					if(nn1>nn2){
						GenePair agp = tmp[i];
						tmp[i] = tmp[j];
						tmp[j]=agp;
					}
				}
				if(tmp[i].gene1.genomeIndex==tmp[j].gene1.genomeIndex  && tmp[i].gene2.genomeIndex==tmp[j].gene2.genomeIndex){
					int nn1 = (new Integer(tmp[i].gene1.newName)).intValue();
					int nn2 = (new Integer(tmp[j].gene1.newName)).intValue();
					int nn3 = (new Integer(tmp[i].gene2.newName)).intValue();
					int nn4 = (new Integer(tmp[j].gene2.newName)).intValue();
					if(nn1==nn2 && nn3>nn4){
						GenePair agp = tmp[i];
						tmp[i] = tmp[j];
						tmp[j]=agp;
					}
				}*/
			}
		}
		genepairs = new GenePair[tmp.length];
		genepairs = tmp;
	
	}
	
}
