// case1: first speciation. then genome doubling. order sngs, then add gray edges to try to maximize the number of same gray edges
import java.util.*;
import java.io.*;

public class OrthologSet{
	String[] gns;
	int index;

	public OrthologSet(String[] genes, int ind){
		gns = new String[genes.length];
		gns = genes;
		index = ind;
	}
	
	public OrthologSet(String[] genesAndInd){
		gns = new String[genesAndInd.length-1];
		for(int i = 0; i< gns.length; i++){
			gns[i] = genesAndInd[i+1];
		}
		index = (new Integer(genesAndInd[0])).intValue();
	}
	
	public void printAOrthologSet(){
		System.out.print(index+"\t");
		for(int i = 0; i< gns.length; i++){
			System.out.print(gns[i]+"\t");
		}
		System.out.println();
	}
		
	
}
