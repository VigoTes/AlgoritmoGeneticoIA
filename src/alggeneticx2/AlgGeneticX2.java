
package alggeneticx2;

import java.util.ArrayList;

public class AlgGeneticX2 {
        // DOMINIO 0-31 
        /*
            5 BITS 
        */
        
//        Individuo n = new Individuo(4,true);
//        System.out.println("numero=" + n );
//        
//        System.out.println(n.genes.toString()   );
//        System.out.println(n.genes.get(0) + " " + n.genes.get(1) + " " + n.genes.get(2) + " " + n.genes.get(3) );
//        System.out.println("****************************************************************");
//        
    public static void main(String[] args) {

        Poblacion x = new Poblacion(31);
        x.setProbabilidades(0.4f, 0.1f);
        // CREAMOS A NUESTRA PRIMERA POB 
        
        float desvEstandarMenor=10000;
        int posicion=-1;
        Poblacion pobMenor=null;
        
        int c=0;
        do{
            System.out.println("\nIMPRESION POBLACION N°"+c+" \n" + x.toString());
            if(desvEstandarMenor > x.calcularDesvEstandar()){
                    desvEstandarMenor = x.calcularDesvEstandar();
                    pobMenor = x;
                    posicion = c;
            }
        
        
            x.seleccionYReproduccion();


             System.out.println("\nIMPRESION POBLACION N°"+c+" LUEGO DE SELECCION Y REPRODUCCION \n" + x.toString());

            x.cruzamiento();

             System.out.println("\nIMPRESION POBLACION N°"+c+" LUEGO DE CRUZAMIENTO \n" + x.toString());

             x.mutacion();

              System.out.println("\nIMPRESION POBLACION N°"+c+" LUEGO DE MUTACION \n" + x.toString());
            x.reCalcular();
            c++;
        }while(c<100);
          
        System.out.println("\n\n\n\n\n ");
        System.out.println("********* POBLACION CON MENOR DESVIACION ESTANDAR ****************");
        System.out.println("\nIMPRESION POBLACION N°"+ posicion +" \n" + x.toString());  
        
        Individuo optimo = pobMenor.poblacionActual.get(pobMenor.getIndividuoOptimo());
        
        System.out.println("El individuo más optimo es el Individuo N°" +  pobMenor.getIndividuoOptimo()
        + " con un valor de " + optimo.getNumero() + " y una funcion objetivo de " + pobMenor.funcionObjetivo( optimo.getNumero())
        
        
        );
       
        
    }
    
   
    
}
