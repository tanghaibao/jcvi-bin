// case1: first speciation. then genome doubling. order sngs, then add gray edges to try to maximize the number of same gray edges
import java.util.*;
import java.io.*;

public class AllHomolog{
	Homolog[] homologs;
	GeneInfoGroup[] gigroups;
	int genomeNumber;
	
	public AllHomolog(Homolog[] hs, int gn){
		homologs=new Homolog[hs.length];
		homologs = hs;
		genomeNumber = gn;
	}
	
	public AllHomolog(AllHomolog al){
		genomeNumber=al.genomeNumber;
		homologs = new Homolog[al.homologs.length];
		for(int i = 0; i< homologs.length; i++){
			if(al.homologs[i]!=null){
				homologs[i] = new Homolog(al.homologs[i]);
			}
		}
	}
	public AllHomolog(GenomePair[] gps, int genomeNum){
		genomeNumber = genomeNum;
		homologs = new Homolog[100000];
		gigroups = new GeneInfoGroup[100000];
		int hindex = 0;
		
		for(int i = 0; i< gps.length; i++){
			//System.out.println("another gps ***************************");
			if(gps[i]!=null){
			for(int j  = 0; j<gps[i].blocks.length; j++){
				for(int k=0;  k <gps[i].blocks[j].genepairs.length; k++){
					if(gps[i].blocks[j].genepairs[k]!=null){
						boolean added = false;
						GeneInfo[] gl = new GeneInfo[2];
						gl[0] = gps[i].blocks[j].genepairs[k].gene1;
						gl[1] = gps[i].blocks[j].genepairs[k].gene2;
						if(gl[1].sameGene(gl[0])){gl[1] = null;}
						for(int l = 0; l < hindex; l++){
							if(gigroups[l].sameGeneInfoGroup(gigroups[l].geneInfos,gl)==true){
								gigroups[l].combineTwoGeneInfoGroup(gigroups[l].geneInfos,gl);
								homologs[l].addToGroup(gps[i].blocks[j].genepairs[k]);
								added=true;
								break;
							}
						}
						if(added==false){// add to a new homologGroup
							homologs[hindex]= new Homolog();
							homologs[hindex].genepairs[0]= new GenePair(gps[i].blocks[j].genepairs[k]);
							
							gigroups[hindex] = new GeneInfoGroup(gl);
							hindex++;
						}
					}
				}
			}}
			System.out.println("hindex  =  "+ hindex);
		}
		
		for(int i = 0; i< homologs.length-1; i++){
			if(homologs[i]!=null){
				boolean moreToCombine = true;
				while(moreToCombine==true){
			//System.out.println("to combine ");
					moreToCombine= false;
					for(int j=i+1; j < homologs.length; j++){
						if(homologs[j]!=null){
							//GeneInfo[] glist1 = homologs[i].getAllGeneList(homologs[i]);
							//GeneInfo[] glist2 = homologs[j].getAllGeneList(homologs[i]);
							//if(homologs[i].sameGroup(homologs[i],homologs[j])){
							if(gigroups[i].sameGeneInfoGroup(gigroups[i].geneInfos,gigroups[j].geneInfos)==true){
							//if(homologs[i].sameGroup(glist1,glist2)){
								moreToCombine=true;
								homologs[i].combineTwoGroup(homologs[j]);
								gigroups[i].combineTwoGeneInfoGroup(gigroups[i].geneInfos,gigroups[j].geneInfos);
								homologs[j]=null;
								gigroups[j] = null;
							}
						}
					}
				}
			}
		}
		int numberOfH = 0;
		for(int i = 0; i< homologs.length; i++){
			if(homologs[i]!=null){
				numberOfH++;
			}
		}
		System.out.println("after combine the number of Homologs "+numberOfH);
		Homolog[] tmp = new Homolog[numberOfH];
		int tmpIndex = 0;
		for(int i = 0; i< homologs.length; i++){
			if(homologs[i]!=null){tmp[tmpIndex] = homologs[i]; tmp[tmpIndex].orderGenePair(); tmpIndex++;}
		}
		homologs = new Homolog[tmp.length];
		homologs = tmp;
		
		
		for(int i = 0; i< homologs.length; i++){
			//if(homologs[i].genepairs.length<100){
			homologs[i].printAHomolog();
			//}
		}
		int totalEdge = 0;
		int totalGene = 0;
		int[] hl = new int[500000];
		int[] hlInGeneNumber = new int[500000];
		
		for(int i = 0; i< homologs.length; i++){
			totalEdge = totalEdge+ homologs[i].genepairs.length;
			
			hl[homologs[i].genepairs.length]++;
			GeneInfo[] gnlist = homologs[i].getAllGeneList(homologs[i]);
			int gnhere = gnlist.length;
			totalGene = totalGene+ gnhere;
			hlInGeneNumber[gnhere]++;
		//	System.out.println("check here ");
		//	System.out.println("in term of edge "+ homologs[i].genepairs.length);
		//	System.out.println("in term of gene "+ gnhere);
		//	homologs[i].printAHomolog();
		}
		System.out.println("remain Edge "+ totalEdge);
		System.out.println("remain Gene "+ totalGene);
		System.out.println("numberOf Sets "+ homologs.length);
		
		for(int i = 0; i< hl.length; i++){
			if(hl[i]!=0){
				System.out.println("length = "+i+"  : "+hl[i]);
			}
		}
		for(int i = 0; i< hlInGeneNumber.length; i++){
			if(hlInGeneNumber[i]!=0){
				System.out.println("length in gene number = "+i+"  : "+hlInGeneNumber[i]);
			}
		}
		
	}
	
	

	public AllHomolog(GenePair[] gps,int genomeNum){
		genomeNumber = genomeNum;
		homologs = new Homolog[30000];
		gigroups = new GeneInfoGroup[30000];
		int hindex = 0;
		for(int i = 0; i< gps.length; i++){
			if(gps[i]!=null){
				boolean added = false;
				GeneInfo[] gl = new GeneInfo[2];
				gl[0] = gps[i].gene1;
				gl[1] = gps[i].gene2;
				if(gl[1].sameGene(gl[0])){gl[1] = null;}
				for(int l = 0; l < hindex; l++){
					if(gigroups[l].sameGeneInfoGroup(gigroups[l].geneInfos,gl)==true){
						gigroups[l].combineTwoGeneInfoGroup(gigroups[l].geneInfos,gl);
						homologs[l].addToGroup(gps[i]);
						added=true;
						break;
					}
				}
				if(added==false){// add to a new homologGroup

						homologs[hindex]= new Homolog();
						homologs[hindex].genepairs[0]= new GenePair(gps[i]);

						gigroups[hindex] = new GeneInfoGroup(gl);
						hindex++;
				}
			}
				
		}
	//	System.out.println("before combine ");
		boolean moreToCombine = true;
		while(moreToCombine==true){
		
			moreToCombine= false;
		//
		for(int i = 0; i< homologs.length-1; i++){
			if(homologs[i]!=null){
				for(int j=i+1; j < homologs.length; j++){
					if(homologs[j]!=null){
						if(gigroups[i].sameGeneInfoGroup(gigroups[i].geneInfos,gigroups[j].geneInfos)==true){
							//if(homologs[i].sameGroup(glist1,glist2)){
							moreToCombine=true;
							homologs[i].combineTwoGroup(homologs[j]);
							gigroups[i].combineTwoGeneInfoGroup(gigroups[i].geneInfos,gigroups[j].geneInfos);
							homologs[j]=null;
							gigroups[j] = null;
						}
					}
				}
			}
		}}
		int numberOfH = 0;
		for(int i = 0; i< homologs.length; i++){
			if(homologs[i]!=null){
				numberOfH++;
			}
		}
		Homolog[] tmp = new Homolog[numberOfH];
		int tmpIndex = 0;
		for(int i = 0; i< homologs.length; i++){
			if(homologs[i]!=null){tmp[tmpIndex] = homologs[i]; tmp[tmpIndex].orderGenePair(); tmpIndex++;}
		}
		homologs = new Homolog[tmp.length];
		homologs = tmp;
		
		int remainEdges = 0;
		for(int i = 0; i< homologs.length; i++){
			remainEdges= remainEdges+homologs[i].genepairs.length;
		}
	//	System.out.println("remain edges "+ remainEdges);
	//	System.out.println("after combine ");
	//	for(int i = 0; i< homologs.length; i++){
			
	//	 homologs[i].printAHomolog();
	//	}
		
		int[] hl = new int[120000];
		int[] hlInGeneNumber = new int[120000];
		 remainEdges = 0;
		for(int i = 0; i< homologs.length; i++){
			remainEdges= remainEdges+homologs[i].genepairs.length;
			hl[homologs[i].genepairs.length]++;
			GeneInfo[] gnlist = homologs[i].getAllGeneList(homologs[i]);
			/////////////////////////////////
			if(gnlist.length>100){
				int[] frequence = new int[gnlist.length];
				for(int f = 0; f<homologs[i].genepairs.length; f++){
					if(homologs[i].genepairs[f]!=null){
						String g1 = homologs[i].genepairs[f].gene1.name;
						String g2 = homologs[i].genepairs[f].gene2.name;
						for(int p = 0; p< gnlist.length; p++){
							if(g1.equals(gnlist[p].name)){
								frequence[p]++;
							}
							if(g2.equals(gnlist[p].name)){
								frequence[p]++;
							}
						}
					}
				}
				for(int f = 0; f< frequence.length; f++){
					if(frequence[f]>15){
				//	System.out.println(gnlist[f].name+ "    frequence  "+ frequence[f]);
					}
				}
					
			}
			///////////////////////////////////////////////////
			int gnhere = gnlist.length;
			hlInGeneNumber[gnhere]++;
		}
		
		for(int i = 0; i< hl.length; i++){
			if(hl[i]!=0){
			//	System.out.println("length = "+i+"  : "+hl[i]);
			}
		}
		for(int i = 0; i< hlInGeneNumber.length; i++){
			if(hlInGeneNumber[i]!=0){
			//	System.out.println("length in gene number = "+i+"  : "+hlInGeneNumber[i]);
			}
		}
		for(int i = 0; i< homologs.length; i++){
			//if(homologs[i].genepairs.length>10){
			//	homologs[i].printAHomolog();
			//}
		}
		
	}
	
	public boolean hasThisGene(String gn){
		for(int i = 0; i< homologs.length; i++){
			if(homologs[i]!=null && homologs[i].hasThisGene(gn)){
				return true;
			}
		}
		return false;
	}
	
	public int hasThisGene2(String gn){
		for(int i = 0; i< homologs.length; i++){
			if(homologs[i]!=null && homologs[i].hasThisGene(gn)){
				return i;
			}
		}
		return -1;
	}
/*	public boolean inTheString(String ls, String ss){
		if(ls.length()<ss.length()){return false;}
		if(ls.equals(ss)==true){return true;}
		String[] genes = splitByOneLine(ls);
		for(int i = 0; i< genes.length; i++){
			if(ss.equals(genes[i])){
				return true;
			}
		}
		return false;
	}
	*/
	public boolean inTheString(String ls, String ss){
		if(ls.length()<ss.length()){return false;}
		if(ls.equals(ss)==true){return true;}
		
		for(int i = 0; i< (ls.length()-ss.length()); i++){
			if(ss.substring(i,i+ss.length()).equals(ss)){
				return true;
			}
		}
		return false;
	}
	
	public boolean inASubTree(Homolog ah){
		int[] subtree1 = new int[3]; subtree1[0] =6 ; subtree1[1]=1 ; subtree1[2]=3 ;
		int[] subtree2 = new int[3]; subtree2[0] =7 ; subtree2[1]=2 ; subtree2[2]=5 ;
		int[] subtree3 = new int[3]; subtree3[0] =0 ; subtree3[1]=8 ; subtree3[2]=4 ;
		int wst = -1;
		for(int i = 0; i< ah.genepairs.length; i++){
			int whichSubtree = findSubTree(ah.genepairs[i], subtree1, subtree2, subtree3);
			if(wst==-1){wst = whichSubtree;}else{ if(wst!=whichSubtree){return false;}}
			if(whichSubtree == 0){
				return false;
			}
		}
		return true;
	}
	
	public int findSubTree(GenePair agp, int[] st1, int[] st2, int[] st3){
		int g1 = agp.gene1.genomeIndex;
		int g2 = agp.gene2.genomeIndex;
		if(inList(g1,st1) && inList(g2,st1)){return 1;}
		if(inList(g1,st2) && inList(g2,st2)){return 2;}
		if(inList(g1,st3) && inList(g2,st3)){return 3;}
		return 0;
	}
	
	
	public boolean inList(int ai, int[] il){
		for(int i = 0; i< il.length; i++){
			if(ai==il[i]){return true;}
		}
		return false;
	}
	
	
	public String[] splitByOneLine(String as){
		String[] tmp = new String[as.length()];
		int index = 0;
		int fp = 0;
		for(int i = 1; i< as.length()-2; i++){
			String twoCha = as.substring(i, i+2);
			if(twoCha.equals("|")){
				tmp[index] = as.substring(fp, i);
				index++;
				fp = i+2;
			}
		}
		tmp[index] = as.substring(fp,as.length());
		index++;
		String[] result = new String[index];
		for(int i = 0; i< result.length; i++){
			result[i] = tmp[i];
		}
		return result;
	}
	
	public OrthologSet[] getOrthologSet(int minl){
		Homolog[] tmp2 = new Homolog[homologs.length];
		int index2 = 0;
		for(int i = 0; i< homologs.length; i++){
			if(homologs[i]!=null ){
				int homologLengthHere = 0;
				for(int ml = 0; ml<homologs[i].genepairs.length; ml++){
					if(homologs[i].genepairs[ml]!=null){
						homologLengthHere++;
					}
				}
				 if(homologLengthHere>=minl){
					 tmp2[index2] = homologs[i];
					 index2++;
				 }
				if(homologLengthHere<minl && inASubTree(homologs[i])==true){
					tmp2[index2] = homologs[i];
					index2++;
				}
			}
		}
		homologs = new Homolog[index2];
		for(int i = 0; i< homologs.length; i++){
			homologs[i] = tmp2[i];
		}
		System.out.println("the number of Homologs after remove small Homolog sets(genepairs<minL) "+homologs.length+ "  minl == "+ minl);
		int[] hl = new int[120000];
		int[] hlInGeneNumber = new int[120000];
		
		
		for(int i = 0; i< homologs.length; i++){
			hl[homologs[i].genepairs.length]++;
			GeneInfo[] gnlist = homologs[i].getAllGeneList(homologs[i]);
		
			int gnhere = gnlist.length;
			hlInGeneNumber[gnhere]++;
		}
		for(int i = 0; i< hl.length; i++){
			if(hl[i]!=0){
				System.out.println("length = "+i+"  : "+hl[i]);
			}
		}
		for(int i = 0; i< hlInGeneNumber.length; i++){
			if(hlInGeneNumber[i]!=0){
				System.out.println("length in gene number = "+i+"  : "+hlInGeneNumber[i]);
			}
		}
		
		for(int i = 0; i< homologs.length; i++){
			if(homologs[i]!=null){
				for(int j = 0; j< homologs[i].genepairs.length; j++){
					if(homologs[i].genepairs[j]!=null){
						homologs[i].genepairs[j].gene1.newName = (new Integer(i+1)).toString();
						homologs[i].genepairs[j].gene2.newName = (new Integer(i+1)).toString();
					}
						
				}
			}
		}
		

		OrthologSet[] tmp = new OrthologSet[homologs.length];
		int index = 0;
		for(int i = 0; i< homologs.length; i++){
			if(homologs[i]!=null ){
				String[] gns = new String[genomeNumber];
				for(int j = 0; j< gns.length; j++){
					gns[j] = "Missing";
				}
				GeneInfo[] gnlist = homologs[i].getAllGeneList(homologs[i]);
				for(int i5=0; i5<gnlist.length; i5++){
					int gihere = gnlist[i5].genomeIndex;
					if(gns[gihere].equals("Missing")){
						gns[gihere] = gnlist[i5].name;
					}else{
						gns[gihere] = gns[gihere]+"|"+gnlist[i5].name;
					}
				}
				tmp[index]= new OrthologSet(gns, index+1);
				index++;
			}
		}
		OrthologSet[] result = new OrthologSet[index];
		for(int i = 0; i< result.length; i++){
			result[i] = tmp[i];
		}
		System.out.println("++++++++++++++++++++++++++after all process, the homolog list ");
		for(int i = 0; i< homologs.length; i++){
			//if(homologs[i].genepairs.length<100){
		//	homologs[i].printAHomolog();
			//}
		}
		
		
		
		return result;
	}
	
	public GeneInfo[] getGeneInfo(int gi){
		GeneInfo[] tmp = new GeneInfo[50000];
		int index = 0;
		for(int i = 0; i< homologs.length; i++){
			if(homologs[i]!=null){
			for(int j = 0; j< homologs[i].genepairs.length; j++){
				if(homologs[i].genepairs[j]!=null){
				if(homologs[i].genepairs[j].gene1.genomeIndex==gi && notInGeneInfoList(homologs[i].genepairs[j].gene1,tmp)){
						tmp[index] = new GeneInfo(homologs[i].genepairs[j].gene1);
						tmp[index].newName = (new Integer(i+1)).toString();
						index++;
				}
				if(homologs[i].genepairs[j].gene2.genomeIndex==gi && notInGeneInfoList(homologs[i].genepairs[j].gene2,tmp)){
						tmp[index] = new GeneInfo(homologs[i].genepairs[j].gene2);
						tmp[index].newName = (new Integer(i+1)).toString();
						index++;
				}}
				
			}}
		}
		
		GeneInfo[] result = new GeneInfo[index];
		for(int i = 0; i< result.length; i++){
			result[i]= tmp[i];
		}
		return result;
	}
	public boolean notInGeneInfoList(GeneInfo ag, GeneInfo[] alist){
		for(int i = 0; i< alist.length; i++){
			if(alist[i]!=null && ag.sameGene(alist[i])){
				return false;
			}
		}
		return true;
	}
	public int countTotalBlockLength(Homolog hs){
		int result = 0;
		for(int i = 0; i< hs.genepairs.length; i++){
			result=result+hs.genepairs[i].gene1.synSize;
		}
		return result;
	}
	
	public int countGeneInAHomolog(Homolog ah){
		int[] gns = new int[genomeNumber];
		for(int i = 0; i< ah.genepairs.length; i++){
			int g1 = ah.genepairs[i].gene1.genomeIndex;
			int g2 = ah.genepairs[i].gene2.genomeIndex;
			gns[g1] = 1;
			gns[g2]=1;
		}
		int result =0;
		for(int i = 0; i< gns.length; i++){
			if(gns[i]==1){
				result++;
			}
		}
		return result;
		
	}
	
	public boolean hasDuplicates(Homolog ah){
		for(int j = 0; j<ah.genepairs.length-1; j++){
			if(ah.genepairs[j]!=null){
				for(int k = j+1; k<ah.genepairs.length; k++){
					if(ah.genepairs[k]!=null){
							String gn1 = ah.genepairs[j].gene1.name;
							String gn2 = ah.genepairs[j].gene2.name;
							String gn3 = ah.genepairs[k].gene1.name;
							String gn4 = ah.genepairs[k].gene2.name;
							int gi1 = ah.genepairs[j].gene1.genomeIndex;
							int gi2 = ah.genepairs[j].gene2.genomeIndex;
							int gi3 = ah.genepairs[k].gene1.genomeIndex;
							int gi4 = ah.genepairs[k].gene2.genomeIndex;
							if(gi1==gi3 && gn1.equals(gn3)==false){
								return true;
							}
							if(gi1==gi4 && gn1.equals(gn4)==false){
								return true;
							}
							if(gi2==gi3 && gn2.equals(gn3)==false){
								return true;
							}
							if(gi2==gi4 && gn2.equals(gn4)==false){
								return true;
							}
						}
					}
				}
			}
		return false;
	}
	
	/*public void getSmallSubGraph(int bigGraph){
		Homolog[] tmp = new Homolog[50000];
		int index = 0;
		for(int i = 0; i< homologs.length; i++){
			if(homologs[i]!=null){
				System.out.println("------------ a homolog------------- "+ homologs[i].genepairs.length);
				if(homologs[i].genepairs.length>bigGraph){
					Homolog[] newSubGroup = homologs[i].cutIntoSubGraph(genomeNumber,bigGraph, numberOfWorstGene);
				}
		
	}*/
	
	/*public void groupOrthologyMimumPartition(int[][] genomeOrder){
		Homolog[] tmp = new Homolog[80000];
		int index = 0;
		for(int i = 0; i< homologs.length; i++){
			if(homologs[i]!=null ){
				System.out.println(i+"--------a homolog------------------"+ homologs[i].genepairs.length);
			//	int c = countBiggestGeneNumber(homologs[i]);
				Homolog[] newSubGroup = homologs[i].minimumPartition(homologs[i], genomeNumber, genomeOrder);
				homologs[i] = null;
				
				//System.out.println("after minimum deletion new homologs");
				
				for(int nh = 0; nh <newSubGroup.length; nh++){
					if(newSubGroup[nh]!=null){
						tmp[index] = newSubGroup[nh];
						index++;
						//newSubGroup[nh].printAHomolog();
						//totalEdgeAfterDeletion=totalEdgeAfterDeletion+newSubGroup[nh].genepairs.length;
						
					}
				}
			}
		}
		homologs = new Homolog[index];
		for(int i = 0; i< homologs.length; i++){
			homologs[i] = tmp[i];
		}
		
		System.out.println("the number of Homologs "+homologs.length);
		int[] hl = new int[60000];
		for(int i = 0; i< homologs.length; i++){
			hl[homologs[i].genepairs.length]++;
		}
		for(int i = 0; i< hl.length; i++){
			if(hl[i]!=0){
				System.out.println("length = "+i+"  : "+hl[i]);
			}
		}
		
	}*/
/*	public void groupOrthologyMinimumDeletion(int bigGraph, double minimumSimilarity){
		//getSmallSubGraph(bigGraph);
		Homolog[] tmp = new Homolog[80000];
		int index = 0;
		for(int i = 0; i< homologs.length; i++){
			if(homologs[i]!=null ){
				System.out.println(i+"--------a homolog------------------"+ homologs[i].genepairs.length);
				if(homologs[i].genepairs.length>20000){
					GenePair[] tmp2 = new GenePair[homologs[i].genepairs.length];
					int tmpIndex = 0;
					for(int j = 0; j< homologs[i].genepairs.length; j++){
						if(homologs[i].genepairs[j].weight>minimumSimilarity){
							tmp2[tmpIndex] = homologs[i].genepairs[j];
							tmpIndex++;
						}
					}
					GenePair[] nh = new GenePair[tmpIndex];
					for(int j = 0; j< nh.length; j++){
						nh[j] = tmp2[j];
					}
					homologs[i] = new Homolog(nh);
					System.out.println("a big family, remove the edges with weight <="+ minimumSimilarity+ "   and then the big graph has edge = "+ tmpIndex  );
				}
				
				int totalEdgeAfterDeletion = 0;
				
				//homologs[i].printAHomolog();
			
				Homolog[] newSubGroup = homologs[i].minimumDeletion(homologs[i],genomeNumber,bigGraph);
				
				homologs[i] = null;
		
				//System.out.println("after minimum deletion new homologs");
					
					for(int nh = 0; nh <newSubGroup.length; nh++){
						if(newSubGroup[nh]!=null){
							tmp[index] = newSubGroup[nh];
							index++;
							//newSubGroup[nh].printAHomolog();
							totalEdgeAfterDeletion=totalEdgeAfterDeletion+newSubGroup[nh].genepairs.length;
							
						}
					}
				/*	if((index+newSubGroup.length)>(tmp.length-5)){
						Homolog[] newTmp = new Homolog[tmp.length+1000];
						for(int k = 0; k< tmp.length; k++){
							newTmp[k] = tmp[k];
						}
						tmp = new Homolog[newTmp.length];
						tmp = newTmp;
					}
					for(int j = 0; j< newSubGroup.length; j++){
						tmp[index] = newSubGroup[j];
						index++;
					}*/
					
					//System.out.println(" after deletion== "+ totalEdgeAfterDeletion);
				//}
	/*		}
		}
		Homolog[] newHomologs = new Homolog[homologs.length+index+1];
		int index1 = 0;
	/*	for(int i = 0; i< homologs.length; i++){
			if(homologs[i]!=null){
				newHomologs[index1] = homologs[i];
				index1++;
			}
		}*/
	/*	for(int i = 0; i<tmp.length; i++){
			if(tmp[i]!=null){
				newHomologs[index1] = tmp[i];
				index1++;
			}
		}
		homologs = new Homolog[index1];
		for(int i = 0; i< homologs.length; i++){
			homologs[i] = newHomologs[i];
		}
		
		System.out.println("the number of Homologs "+homologs.length);
		int[] hl = new int[60000];
		for(int i = 0; i< homologs.length; i++){
			hl[homologs[i].genepairs.length]++;
		}
		for(int i = 0; i< hl.length; i++){
			if(hl[i]!=0){
				System.out.println("length = "+i+"  : "+hl[i]);
			}
		}
	}
		
			
		*/	   
			
	
	public void removeAllDuplicate(){  // if a gene has a duplicate copy, remove all of them
		System.out.println("remove all duplicates");
		for(int i = 0; i< homologs.length; i++){
			if(homologs[i]!=null){
				boolean dup = hasDuplicates(homologs[i]);
				if(dup==true){
					homologs[i]=null;
				}
			}
		}
		System.out.println("rename ***************************");
		int geneNameIndex = 1;
		for(int i = 0; i< homologs.length; i++){
			if(homologs[i]!=null){
				//
				for(int j = 0; j< homologs[i].genepairs.length; j++){
					if(homologs[i].genepairs[j]!=null){
						homologs[i].genepairs[j].gene1.newName = (new Integer(geneNameIndex)).toString();
						homologs[i].genepairs[j].gene2.newName = (new Integer(geneNameIndex)).toString();
					}
				}
				//homologs[i].printAHomolog();
				geneNameIndex++;
			}
		}		
		return;
	}
	
	
	
/*	public void removeAllDuplicateWithCacao2(int maxPositionDiff, int genomeIndex){  // if a gene has a duplicate copy, remove all of them
		
		Homolog[] homolog = new Homolog[homologs.length];
		for(int i = 0; i< homolog.length; i++){
			if(homologs[i]!=null){
				homolog[i] = new Homolog(homologs[i]);
			}
		}
	//	System.out.println("rename cacao, if two genes are close to each,( <"+maxPositionDiff+"bp difference), then two genes are the same gene");
		// for each homologs[i], get all cacao, find the 
		
		int totalChanged = 0;
		for(int i = 0; i< homolog.length; i++){
			if(homolog[i]!=null){
				int[] chrs=new int[homolog[i].genepairs.length]; 
				int[] positions = new int[homolog[i].genepairs.length];
				int index = 0;
				for(int j = 0; j< homolog[i].genepairs.length; j++){
					if(homolog[i].genepairs[j]!=null && homolog[i].genepairs[j].gene1.genomeIndex == genomeIndex){
						chrs[index] = homolog[i].genepairs[j].gene1.chrNumber;
						positions[index] = homolog[i].genepairs[j].gene1.positionStart;
						index++;
					}
					
					if(homolog[i].genepairs[j]!=null && homolog[i].genepairs[j].gene2.genomeIndex == genomeIndex){
						chrs[index] = homolog[i].genepairs[j].gene2.chrNumber;
						positions[index] = homolog[i].genepairs[j].gene2.positionStart;
						index++;
					}
				}
				if(index>1){
					boolean samePosition = true;
					for(int k = 0; k< index; k++){
						if(chrs[0]!=chrs[k]){
							samePosition= false;
						}
					}
					for(int k = 0; k< index-1; k++){
						for(int l = k+1; l<index; l++){
							int diff = positions[k]-positions[l];
							if(diff>maxPositionDiff || diff<-maxPositionDiff){
								samePosition= false;
							}
						}
					}
					if(samePosition==true){
						totalChanged++;
						String nn = null;
						for(int j = 0; j< homolog[i].genepairs.length; j++){
							if(homolog[i].genepairs[j]!=null && homolog[i].genepairs[j].gene1.genomeIndex == genomeIndex){
								if(nn==null){
									nn = homolog[i].genepairs[j].gene1.name;
								}else{
									homolog[i].genepairs[j].gene1.name = nn;
								}
									
							}
							
							if(homolog[i].genepairs[j]!=null && homolog[i].genepairs[j].gene2.genomeIndex == genomeIndex){
								if(nn==null){
									nn = homolog[i].genepairs[j].gene2.name;
								}else{
									homolog[i].genepairs[j].gene2.name = nn;
								}
							}
						}
					}
				}
			}
		}
		//System.out.println("total changed "+ totalChanged);		
	//	System.out.println("remove all duplicates");
		for(int i = 0; i< homolog.length; i++){
			if(homolog[i]!=null){
				boolean dup = hasDuplicates(homolog[i]);
				if(dup==true){
					homolog[i]=null;
				}
			}
		}
	//	System.out.println("rename ***************************");
		int counthere = 0;
		for(int i = 0; i< homolog.length; i++){
			if(homolog[i]!=null){
				counthere++;
			}
		}
		//System.out.println("count here "+counthere);
				//System.out.println(
		int geneNameIndex = 1;
		for(int i = 0; i< homolog.length; i++){
			if(homolog[i]!=null){
				//
				for(int j = 0; j< homolog[i].genepairs.length; j++){
					if(homolog[i].genepairs[j]!=null){
						homolog[i].genepairs[j].gene1.newName = (new Integer(geneNameIndex)).toString();
						homolog[i].genepairs[j].gene2.newName = (new Integer(geneNameIndex)).toString();
					}
				}
				//homologs[i].printAHomolog();
				geneNameIndex++;
			}
		}
		System.out.println("<"+maxPositionDiff+"bp\t"+ (geneNameIndex-1));
		//System.out.println("*******total gene now*********"+(geneNameIndex-1));
		return;
	}
	
	
	public void removeAllDuplicateWithCacao(int maxPositionDiff, int genomeIndex){  // if a gene has a duplicate copy, remove all of them
		
		//	System.out.println("rename cacao, if two genes are close to each,( <"+maxPositionDiff+"bp difference), then two genes are the same gene");
		// for each homologs[i], get all cacao, find the 
		
		int totalChanged = 0;
		for(int i = 0; i< homologs.length; i++){
			if(homologs[i]!=null){
				int[] chrs=new int[homologs[i].genepairs.length]; 
				int[] positions = new int[homologs[i].genepairs.length];
				int index = 0;
				for(int j = 0; j< homologs[i].genepairs.length; j++){
					if(homologs[i].genepairs[j]!=null && homologs[i].genepairs[j].gene1.genomeIndex == genomeIndex){
						chrs[index] = homologs[i].genepairs[j].gene1.chrNumber;
						positions[index] = homologs[i].genepairs[j].gene1.positionStart;
						index++;
					}
					
					if(homologs[i].genepairs[j]!=null && homologs[i].genepairs[j].gene2.genomeIndex == genomeIndex){
						chrs[index] = homologs[i].genepairs[j].gene2.chrNumber;
						positions[index] = homologs[i].genepairs[j].gene2.positionStart;
						index++;
					}
				}
				if(index>1){
					boolean samePosition = true;
					for(int k = 0; k< index; k++){
						if(chrs[0]!=chrs[k]){
							samePosition= false;
						}
					}
					for(int k = 0; k< index-1; k++){
						for(int l = k+1; l<index; l++){
							int diff = positions[k]-positions[l];
							if(diff>maxPositionDiff || diff<-maxPositionDiff){
								samePosition= false;
							}
						}
					}
					if(samePosition==true){
						totalChanged++;
						String nn = null;
						for(int j = 0; j< homologs[i].genepairs.length; j++){
							if(homologs[i].genepairs[j]!=null && homologs[i].genepairs[j].gene1.genomeIndex == genomeIndex){
								if(nn==null){
									nn = homologs[i].genepairs[j].gene1.name;
								}else{
									homologs[i].genepairs[j].gene1.name = nn;
								}
								
							}
							
							if(homologs[i].genepairs[j]!=null && homologs[i].genepairs[j].gene2.genomeIndex == genomeIndex){
								if(nn==null){
									nn = homologs[i].genepairs[j].gene2.name;
								}else{
									homologs[i].genepairs[j].gene2.name = nn;
								}
							}
						}
					}
				}
			}
		}
		//System.out.println("total changed "+ totalChanged);		
		//	System.out.println("remove all duplicates");
		for(int i = 0; i< homologs.length; i++){
			if(homologs[i]!=null){
				boolean dup = hasDuplicates(homologs[i]);
				if(dup==true){
					homologs[i]=null;
				}
			}
		}
		//	System.out.println("rename ***************************");
		int counthere = 0;
		for(int i = 0; i< homologs.length; i++){
			if(homologs[i]!=null){
				counthere++;
			}
		}
		//System.out.println("count here "+counthere);
		//System.out.println(
		int geneNameIndex = 1;
		for(int i = 0; i< homologs.length; i++){
			if(homologs[i]!=null){
				//
				for(int j = 0; j< homologs[i].genepairs.length; j++){
					if(homologs[i].genepairs[j]!=null){
						homologs[i].genepairs[j].gene1.newName = (new Integer(geneNameIndex)).toString();
						homologs[i].genepairs[j].gene2.newName = (new Integer(geneNameIndex)).toString();
					}
				}
				//homologs[i].printAHomolog();
				geneNameIndex++;
			}
		}
		System.out.println("<"+maxPositionDiff+"bp\t"+ (geneNameIndex-1));
		//System.out.println("*******total gene now*********"+(geneNameIndex-1));
		return;
	}
	
	
	
	*/
					
				
	
	public int countTotalWholeSetOfCG(Homolog hs){
		int result=0;
		AllHomolog allh = new AllHomolog(hs.genepairs, genomeNumber);
		for(int i = 0; i< allh.homologs.length; i++){
			int genenumber = countGeneInAHomolog(allh.homologs[i]);
			if(genenumber==genomeNumber){
				result++;
			}
		}
		return result;
	}
	
	
			
}
