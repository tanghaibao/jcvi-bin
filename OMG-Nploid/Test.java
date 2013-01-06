//
import java.util.*;
import java.io.*;

public class Test{
	public static void main( String[] args) throws Exception{

		//Section 1: read in a gene family
		//String fileName = "sampleData/geneFamily1.txt";
		//int genomeNumber = 4;
        String fileName = args[0];
        int genomeNumber = (new Integer(args[1])).intValue();

		FileReader fr;
		BufferedReader br;
		fr = new FileReader(fileName);
		System.out.println(" input file "+ fileName);
		br = new BufferedReader(fr);

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
		// end of Section 1

		Homolog agenefamily = new Homolog(edges);
		int[] numberOfTransitivity = new int[1];
		numberOfTransitivity[0] = 10000;
		Homolog[] goodH = agenefamily.checkGoodOrNot(agenefamily, genomeNumber);
		Homolog[] orthologSet = null;
		if(goodH.length==1){
			orthologSet = new Homolog[1];
			orthologSet=goodH;
		}else{
			orthologSet= agenefamily.minimumDeletion2(agenefamily, genomeNumber, numberOfTransitivity);
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

	}

}





