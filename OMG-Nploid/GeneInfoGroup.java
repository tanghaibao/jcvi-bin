// case1: first speciation. then genome doubling. order sngs, then add gray edges to try to maximize the number of same gray edges
import java.util.*;
import java.io.*;

public class GeneInfoGroup{
	GeneInfo[] geneInfos;
	
	public GeneInfoGroup(GeneInfo[] gis){
		geneInfos = new GeneInfo[gis.length];
		geneInfos = gis;
	}
	
	public boolean sameGeneInfoGroup(GeneInfo[] gl1, GeneInfo[] gl2){
		for(int i = 0; i< gl1.length; i++){
			if(gl1[i]!=null){
				for(int j = 0; j< gl2.length; j++){
					if(gl2[j]!=null){
						if(gl1[i].sameGene(gl2[j])){
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	public void combineTwoGeneInfoGroup(GeneInfo[] gl1, GeneInfo[] gl2){
		GeneInfo[] tmp = new GeneInfo[gl1.length+gl2.length];
		int tmpIndex = 0;
		for(int i = 0; i< gl1.length; i++){
			if(gl1[i]!=null){
				tmp[tmpIndex]= gl1[i];
				tmpIndex++;
			}
		}
		for(int i = 0; i< gl2.length; i++){
			if(gl2[i]!=null && notInList(tmp, gl2[i])){
				tmp[tmpIndex] = gl2[i];
				tmpIndex++;
			}
		}
		geneInfos = new GeneInfo[tmpIndex];
		for(int i = 0; i<geneInfos.length; i++){
			geneInfos[i] = tmp[i];
		}
		
	}
	
	public boolean notInList(GeneInfo[] alist, GeneInfo ai){
		for(int i = 0; i< alist.length; i++){
			if(alist[i]!=null && ai.sameGene(alist[i])){
				return false;
			}
		}
		return true;
	}
	
}