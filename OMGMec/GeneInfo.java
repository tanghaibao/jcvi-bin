// case1: first speciation. then genome doubling. order sngs, then add gray edges to try to maximize the number of same gray edges
import java.util.*;
import java.io.*;

public class GeneInfo{
	String name;
	String newName;
	int chrNumber;
	int positionStart;
	int positionEnd;
	String orientation;
	int synSize;
	int synIndex;
	int genomeIndex;
	int nnIndex;
	int newSynIndex = -1;
	
	
	boolean diploid = true;
	
	public GeneInfo(String gn, int gi){
		name = gn;
		genomeIndex = gi;
	}
	public GeneInfo(){
	}
	public GeneInfo(GeneInfo g){
		name = g.name;
		newName = g.newName;
		chrNumber = g.chrNumber;
		positionStart = g.positionStart;
		positionEnd = g.positionEnd;
		orientation = g.orientation;
		synSize = g.synSize;
		synIndex=g.synIndex;
		genomeIndex=g.genomeIndex;
		newSynIndex = g.newSynIndex;
		diploid = g.diploid;
	}
	
	public boolean sameGene(GeneInfo ag){
		if(genomeIndex==ag.genomeIndex){
			//if(ag.chrNumber==chrNumber){
				if(name.equals(ag.name)){
				//if(chrNumber!=ag.chrNumber || positionStart!=ag.positionStart || positionEnd!=ag.positionEnd){
				//	System.out.println("same name, different position");this.printGene();ag.printGene();
				//	System.out.print("&");
				//}
				return true;
			//}
			}}
		return false;
	}
	
	public void printGene(){
		System.out.print(name + "\t"  + chrNumber + "\t" + positionStart + "\t" + positionEnd+"\t" + orientation+ "\t"+genomeIndex);
		if(diploid==true){
			System.out.println("\tdiploid");
		}else{
			System.out.println("\ttetraploid");
		}
		//System.out.println("**" +name + "newname " + newName+  " synIndex "+synIndex+ "  newSynIndex "+newSynIndex);
	}

	public GeneInfo(String printedGene){
		String[] infos = printedGene.split("\t");
		name = infos[0];
		chrNumber = (new Integer(infos[1])).intValue();
		positionStart = (new Integer(infos[2])).intValue();
		positionEnd = (new Integer(infos[3])).intValue();
		orientation = infos[4];
		genomeIndex = (new Integer(infos[5])).intValue();
		//System.out.println("-----"+infos[6]);
		if(infos[6].equals("diploid")){
			diploid = true;
		}else{
			diploid = false;
		}
		
	}
	
	//public void printGene(){
	//	System.out.print("   name : " +name );
	//}
	
	public String[] splitByTwoLine(String as){
		String[] tmp = new String[as.length()];
		int index = 0;
		int fp = 0;
		for(int i = 1; i< as.length()-2; i++){
			String twoCha = as.substring(i, i+2);
			if(twoCha.equals("||")){
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
}
