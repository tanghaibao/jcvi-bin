//10 genomes, the biggraph is based on 45 pairs of data + 5 tetraVsTetra,
import java.util.*;
import java.io.*;

public class TestOMGMec{
	public static void main( String[] args) throws Exception{


		// Section 1: Information about your input file
		//String fileName = "geneFamily1.txt";  // name of inout file
		//int totalGenome = 7;     // the total number of input genomes
        String fileName = args[0];
        int totalGenome = (new Integer(args[1])).intValue();
		FileReader fr;
		BufferedReader br;
		fr = new FileReader(fileName);
		System.out.println(" input file "+ fileName);
		br = new BufferedReader(fr);
		// end of section 1



		int similarity1 = 60;
		int similarity2 = 70;
		int similarity3 = 80;
		int biggraph= 500;
		System.out.println(" big graph>= "+ biggraph);
		String aline = br.readLine();
		int numberOfEdges = (new Integer(aline.substring(27))).intValue();
		GenePair[] edges = new GenePair[numberOfEdges];
		int index2 = 0;
		for(int i = 0; i< numberOfEdges; i++){
			aline = br.readLine(); //weight
			int weight1 = (new Integer(aline.substring(7))).intValue();
			aline = br.readLine();
			GeneInfo gi1 = new GeneInfo(aline);
			aline = br.readLine();
			GeneInfo gi2 = new GeneInfo(aline);
			edges[index2] = new GenePair(gi1,gi2, weight1);
			index2++;
			aline = br.readLine();
		}
		//for(int i  =0; i< edges.length; i++){
		//	edges[i].printAGenePair();
		//}

		int[] numberOfTransitivity = new int[1];
		numberOfTransitivity[0] = 10000;

		if(edges.length<biggraph){
			Homolog agenefamily = new Homolog(edges);
			Homolog[] goodH = agenefamily.checkGoodOrNot(agenefamily, totalGenome);
			Homolog[] orthologSet = null;
			if(goodH.length==1){
				//System.out.println("hre----");
				orthologSet = new Homolog[1];
				orthologSet=goodH;
			}else{
				//System.out.println("apply OMG");
				orthologSet= agenefamily.minimumDeletion2(agenefamily, totalGenome, numberOfTransitivity);
			}
			for(int i = 0; i< orthologSet.length; i++){
				System.out.println("\n\n=== an ortholog set ");
				orthologSet[i].printAHomolog();
				GeneInfo[] gnlist = orthologSet[i].getAllGeneList(orthologSet[i]);
				System.out.println("------geneList");
				for(int j = 0; j< gnlist.length; j++){
					gnlist[j].printGene();
				}
			}
		}else{
			System.out.println( "===================big graph,"+ edges.length + " remove edges with similarity< " + similarity1);
			for(int i = 0; i< edges.length; i++){
				if(edges[i].weight < similarity1){
					edges[i]= null;
				}
			}
			AllHomolog allh = new AllHomolog(edges,totalGenome);
			for(int i = 0; i< allh.homologs.length; i++){
				if(allh.homologs[i].genepairs.length<biggraph){
					Homolog agenefamily2 = new Homolog(allh.homologs[i]);
					Homolog[] goodH2 = agenefamily2.checkGoodOrNot(agenefamily2, totalGenome);
					Homolog[] orthologSet2 = null;
					if(goodH2.length==1){
						orthologSet2 = new Homolog[1];
						orthologSet2 = goodH2;
					}else{
						orthologSet2= agenefamily2.minimumDeletion2(agenefamily2, totalGenome, numberOfTransitivity);
					}
					for(int o = 0; o< orthologSet2.length; o++){
						System.out.println("\n\n=== an ortholog set ");
						orthologSet2[o].printAHomolog();
						GeneInfo[] gnlist2 = orthologSet2[o].getAllGeneList(orthologSet2[o]);
						System.out.println("------geneList");
						for(int j = 0; j< gnlist2.length; j++){
							gnlist2[j].printGene();
						}
					}
				}else{
					System.out.println("=== still a big graph, " +allh.homologs[i].genepairs.length + "  remove edges with higher similarity< " + similarity2);
					for(int j = 0; j< allh.homologs[i].genepairs.length; j++){
						if(allh.homologs[i].genepairs[j].weight < similarity2){
							allh.homologs[i].genepairs[j]= null;
						}
					}
					AllHomolog allh2 = new AllHomolog(allh.homologs[i].genepairs,totalGenome);
					for(int k = 0; k< allh2.homologs.length; k++){
						if(allh2.homologs[k].genepairs.length<biggraph){
							Homolog agenefamily3 = new Homolog(allh2.homologs[k].genepairs);
							Homolog[] goodH3 = agenefamily3.checkGoodOrNot(agenefamily3, totalGenome);
							Homolog[] orthologSet3 = null;
							if(goodH3.length==1){
								orthologSet3 = new Homolog[1];
								orthologSet3 = goodH3;
							}else{
								orthologSet3= agenefamily3.minimumDeletion2(agenefamily3, totalGenome, numberOfTransitivity);
							}
							for(int o = 0; o< orthologSet3.length; o++){
								System.out.println("\n\n=== an ortholog set ");
								orthologSet3[o].printAHomolog();
								GeneInfo[] gnlist3 = orthologSet3[o].getAllGeneList(orthologSet3[o]);
								System.out.println("------geneList");
								for(int j = 0; j< gnlist3.length; j++){
									gnlist3[j].printGene();
								}
							}
						}else{
							System.out.println(" a big graph again "+ allh2.homologs[k].genepairs.length + " remove edges < "+similarity3);
							for(int t = 0; t< allh2.homologs[k].genepairs.length; t++){
								if(allh2.homologs[k].genepairs[t].weight< similarity3){
									allh2.homologs[k].genepairs[t] = null;
								}
							}
							AllHomolog allh3 = new AllHomolog(allh2.homologs[k].genepairs, totalGenome);
								for(int t = 0; t< allh3.homologs.length; t++){
									if(allh3.homologs[t].genepairs.length<biggraph){
										Homolog agenefamily4 = new Homolog(allh3.homologs[t].genepairs);
										Homolog[] goodH4 = agenefamily4.checkGoodOrNot(agenefamily4, totalGenome);
										Homolog[] orthologSet4 = null;
										if(goodH4.length == 1){
											orthologSet4 = new Homolog[1];
											orthologSet4 = goodH4;
										}else{
											orthologSet4= agenefamily4.minimumDeletion2(agenefamily4, totalGenome, numberOfTransitivity);

										}
										for(int m = 0; m< orthologSet4.length; m++){
											System.out.println("\n\n=== an ortholog set ");
											orthologSet4[m].printAHomolog();
											GeneInfo[] gnlist4 = orthologSet4[m].getAllGeneList(orthologSet4[m]);
											System.out.println("------geneList");
											for(int j = 0; j< gnlist4.length; j++){
												gnlist4[j].printGene();
											}
										}

									}else{
										System.out.println("drop a graph with "+ allh3.homologs[t].genepairs.length + " edges > "+similarity3);
									}
								}

							}
					}
				}
			}
		}

	}
}





