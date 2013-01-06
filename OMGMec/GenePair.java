
public class GenePair{
	GeneInfo gene1;
	GeneInfo gene2;
	int weight;
	
	public GenePair(GeneInfo g1, GeneInfo g2, int w){
		gene1 =new GeneInfo(g1);
		gene2 = new GeneInfo(g2);
		weight = w;
		
	}
	
	
	public GenePair(GeneInfo g1, GeneInfo g2){
		gene1 =new GeneInfo(g1);
		gene2 = new GeneInfo(g2);
		weight = 0;
		
	}
	
	public GenePair(GenePair agp){
		gene1 = new GeneInfo(agp.gene1);
		gene2 = new GeneInfo(agp.gene2);
		weight = agp.weight;
		
	}
	
	public void printAGenePair(){
		System.out.println("weight "+ weight);
		gene1.printGene();
		gene2.printGene();
		
	}
	
	public boolean sameEdge(GenePair gp1, GenePair gp2){
		String gn1 = gp1.gene1.name;
		String gn2 = gp1.gene2.name;
		String gn3 = gp2.gene1.name;
		String gn4 = gp2.gene2.name;
		if(gn1.equals(gn3) && gn2.equals(gn4)){return true;}
		if(gn1.equals(gn4) && gn2.equals(gn3)){return true;}
		return false;
	}
		
}
		
