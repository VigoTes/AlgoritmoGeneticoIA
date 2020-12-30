
package alggeneticx2;

import java.util.ArrayList;

public class Individuo {
    
    public float supIntervaloK;
    public int contadorPalitos;
    public double nAleatorio;
    
    ArrayList<Double> aleatorios;
    
    public boolean aCruzar;

    public boolean getCruzar() {
        return aCruzar;
    }

    public void setCruzar(boolean aCruzar) {
        this.aCruzar = aCruzar;
    }

    public double getnAleatorio() {
        return nAleatorio;
    }

    public void setnAleatorio(double nAleatorio) {
        this.nAleatorio = nAleatorio;
    }
    
    
    
    
    
    ArrayList<Integer> genes = new ArrayList<>();
                        // numero 9 
                        // 0 1 0 0 1 VALOR 
                        // 0 1 2 3 4 POSICIONES  
    public Individuo(int num){
        int resto;
        
        do{
            resto = num%2;
            num = num/2;
            
            genes.add(0,resto);
      
       //     System.out.println("resto="+resto+" num="+num);
         }while(num > 0); //Haremos el bucle hasta que el cociente no se pueda dividir mas
        
        
         
    }
    
    public Individuo (int cantidadBits,boolean x){ //constructor aleatorio 
        int resto;
        genes.clear();
        
        for(int i=0;i<cantidadBits;i++){
            
            int aleatorio01 = ((int) (Math.random()*100)) % 2;
            genes.add(aleatorio01);
        
        }        
       
    }
    
    
    public int getNumero(){
        int sum=0;
        for(int i=0;i<genes.size();i++)
            sum+=genes.get(i)*Math.pow(2, genes.size() - i-1);
        
        return sum;
    }
    
    
    
    public String toString(){
        String cad="";
        for(int i=0;i<genes.size();i++)
            cad+=genes.get(i).toString();
        return cad;
    }
    
    
    

    public ArrayList<Integer> getGenes() {
        return genes;
    }

    public void setGenes(ArrayList<Integer> genes) {
        this.genes = genes;
    }
    
    
    
    
    
    
}
