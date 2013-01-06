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
	int diploidNumber = 0;
	
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
		diploidNumber = g.diploidNumber;
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
		System.out.println(name + " " + newName+  " " + chrNumber + " " + positionStart + " " + positionEnd+" " + orientation+" "+synSize+ " "+synIndex+ " "+genomeIndex+"  "+diploidNumber);
		//System.out.println("**" +name + "newname " + newName+  " synIndex "+synIndex+ "  newSynIndex "+newSynIndex);
	}

	
	
	/*public void printGene(){
	 System.out.print(name + "\t" + chrNumber + "\t" + positionStart + "\t" + positionEnd+"\t" + orientation+ "\t"+genomeIndex);
	 if(diploid==true){
	 System.out.println("\tdiploid");
	 }else{
	 System.out.println("\ttetraploid");
	 }
	 //System.out.println("**" +name + "newname " + newName+  " synIndex "+synIndex+ "  newSynIndex "+newSynIndex);
	 }
	 */
	public GeneInfo(String printedGene){
		//System.out.println(printedGene);
		String[] infos = splitBS(printedGene);
		name = infos[0];
		newName  = infos[1];
		chrNumber = (new Integer(infos[2])).intValue();
		positionStart = (new Integer(infos[3])).intValue();
		positionEnd = (new Integer(infos[4])).intValue();
		orientation = infos[5];
		synSize = (new Integer(infos[6])).intValue();
		synIndex = (new Integer(infos[7])).intValue();
		genomeIndex = (new Integer(infos[8])).intValue();
		if(infos.length ==10){
			//System.out.println("infos[9]" + infos[9]);
			diploidNumber = (new Integer(infos[9])).intValue();
		}
		
	}
	
	
	public String[] splitBS(String s){
        String[] tmp = s.trim().split(" ");
        int index = 0;
        for(int i = 0; i < tmp.length; i++){
            if(tmp[i].trim().equals("") ==false){
                index++;
            }
        }
        String[] result = new String[index];
        index = 0;
        for(int j = 0; j <tmp.length; j++){
            if(tmp[j].trim().equals("") == false){
                result[index] = tmp[j].trim();
                index++;
            }
        }
        return result;
    }
	//public void printGene(){
	//	System.out.print("   name : " +name );
	//}
	
	public String[] splitByTwoLine(String as){
		//System.out.println("====="+as);
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
