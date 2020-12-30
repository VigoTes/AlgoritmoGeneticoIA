package alggeneticx2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;


public class Poblacion {
    
    public ArrayList<Individuo> poblacionActual = new ArrayList<>();
    private float desviacionEstandar;
    private float sumaFA;
    private int tamaño;
    private int cantidadBits;
    private int puntoDeCruce;
    
    private float probabilidadCruzamiento;
    private float probabilidadMutacion;
    
    
    public void setProbabilidades(float probabilidadCruzamiento, float probabilidadMutacion){
        this.probabilidadCruzamiento = probabilidadCruzamiento;
        this.probabilidadMutacion = probabilidadMutacion;
    }
    
    
    public Poblacion(int rangoSuperior){
            int aux=rangoSuperior;
            cantidadBits=0;
        while(aux>0)
        {
            aux=aux/2;
            cantidadBits++;
        }
        System.out.println("cantida de bits" + cantidadBits);
        
        tamaño= (int) Math.pow(2,cantidadBits);
        System.out.println("tamaño de la poblacion = "+ tamaño);
        
        generarPoblacionAleatoria();
        desviacionEstandar = calcularDesvEstandar();
        
        calcularIntervalosK();
        calcularPalitosPorAleatorios();
        
        puntoDeCruce = (int) (Math.random()*(cantidadBits-1) + 1 );
        
        
    }
    
    //constructor pero sin generar nuevos numeros aleatorios 
    public void reCalcular(){
        desviacionEstandar = calcularDesvEstandar();
        calcularIntervalosK();
        calcularPalitosPorAleatorios();
        puntoDeCruce = (int) (Math.random()*(cantidadBits-1) + 1 );
        
        
    }
    
    //retorna el individuo con mayor funcion objetivo
    public int getIndividuoOptimo(){
        float maximo=0;
        int posicion=0;
        for(int i=0;i<tamaño;i++)
            if( funcionObjetivo (poblacionActual.get(i).getNumero() ) > maximo )
            {
                maximo = funcionObjetivo( poblacionActual.get(i).getNumero() );
                posicion=i;
            }
        return (posicion);
        
    }
    
    public void mutacion(){
        
        double aleatorio; 
        int i,j;
        
        for(i=0;i<tamaño;i++) // recorremos cada individuo  
        {
            Random rand = new Random();
            poblacionActual.get(i).aleatorios = new ArrayList<Double>();
            for(j=0;j<cantidadBits;j++) //recorremos cada bit 
            {
                aleatorio = rand.nextDouble();
                poblacionActual.get(i).aleatorios.add(aleatorio);
//                System.out.println("i=" +i + "/"+j + "  aleatorio"+ aleatorio);
            }
            rand=null;
        }
        
       
        
        
        
        
        
        for(i=0;i<tamaño;i++) // recorremos cada individuo  
        {
            ArrayList<Integer> nuevosGenes = new ArrayList<Integer>();
            nuevosGenes = null;
            nuevosGenes = poblacionActual.get(i).getGenes();
            
//            System.out.println(" INDIVIDUO "+ i +
//                            poblacionActual.get(i).genes.toString());
                   
            
            for(j=0;j<cantidadBits;j++) //recorremos cada bit 
            {
                
                
                if(poblacionActual.get(i).aleatorios.get(j) < probabilidadMutacion )
                {
                    int bit = poblacionActual.get(i).genes.get(j);
                    
                    int nuevoBit;
//                    System.out.println("i="+i+" j="+j+"VALOR DE bit" + poblacionActual.get(i).genes.get(j));
                    
                    
                    
                    if (bit==1)
                        nuevoBit = 0; 
                    else
                        nuevoBit=1;
//                    System.out.println("nuevoBit= " + nuevoBit);
                    
                    
                    nuevosGenes.set(j, null );
                    nuevosGenes.set(j, new Integer(nuevoBit) );

                    System.out.println("El INDIVIDUO "+ i + " SUFRIO MUTACION DEL GEN " + j+"  ("+ bit + "->" + 
                            poblacionActual.get(i).genes.get(j) + ") por aleatorio=" + 
                            poblacionActual.get(i).aleatorios.get(j));
                }
                
            }
            
            poblacionActual.get(i).setGenes(null);
            
            poblacionActual.get(i).setGenes(nuevosGenes);
             
//            System.out.println(" luegoindividuo "+ i +
//                            poblacionActual.get(i).genes.toString()  + "\n");
        }
        
        
    
        
    
    
    }
    
    
    
    public void cruzamiento(){
         int i;
         Random rand = new Random();
        float aleatorio;
        
       // seleccionamos los individuos que se cruzarán 
        for(int j=0;j<tamaño;j++)  
        {
            aleatorio = rand.nextFloat();
            poblacionActual.get(j).setnAleatorio(aleatorio);
        }
        
        for(int j=0;j<tamaño;j++){
            poblacionActual.get(j).setCruzar(false); //por defecto false 
            poblacionActual.get(j).setCruzar(  poblacionActual.get(j).getnAleatorio()<probabilidadCruzamiento  );
        } //                                SI ES MENOR, SETEAMOS EN FALSE 
        
        
        
        
        
        /*
                
        System.out.println("---.-----------------------");
        for( i=0;i<tamaño;i++)  
        {
             if( poblacionActual.get(i).getCruzar() )
            System.out.println("valor de cruzar para i="+i + " aCruzar=" +poblacionActual.get(i).getCruzar()  );
        
        }
        System.out.println("---.-----------------------");
        */
        
        
        
        
        //capturamos el primer individuo con el aCruzar en True
        Individuo primeroGuardado = new Individuo(0); //inicializo pq si 
        boolean salir=false; int posicionPrimera=-1;
        for(i=0;i<tamaño && !salir ;i++)
            if(poblacionActual.get(i).getCruzar()){ 
                salir=true;
                posicionPrimera = i;
                primeroGuardado = poblacionActual.get(i);
                
            }
       
        
        //capturamos el ULTIMO individuo con el aCruzar en True
        Individuo ultimoGuardado = new Individuo(0); //inicializo pq si 
        
        salir=false; int posicionUltimo=-1;
        for(i=tamaño-1;i>0 && !salir ;i--)
            if(poblacionActual.get(i).getCruzar()){ 
                salir=true;
                posicionUltimo = i;
                ultimoGuardado = poblacionActual.get(i);
                
            }
       
        
        
        
        
        
        
        
        
        
        boolean bandera; int j=0;
        for( i=0;i<tamaño;i++)  
        {
            if(i==posicionUltimo)
            {
                        for(int p = puntoDeCruce; p<cantidadBits;p++  ){
                               poblacionActual.get(i).genes.set(p, primeroGuardado.genes.get(p));
                        }
                        System.out.println("     XXSe intercambian i=" + i +"  con la " + posicionPrimera );
                        break;
            }
            
            
            
          
            if(poblacionActual.get(i).getCruzar() && i<posicionUltimo)
            { 
                    salir=false;
                    for(j=i+1;j<tamaño && !salir; j++)  
                    {
//                        System.out.println("Preguntando i="+i+ "  j="+j);
                        if(poblacionActual.get(j).getCruzar())
                        { 
                            salir=true;
                                // ACA ES EL CRUZAMIENTO 
                             chancado(i,j);       
                             System.out.println("         Se intercambian i=" + i +"  j=" + j );
                        }
                        
                    }
            }
        }
        //si acabó el for y no encontró a nadie siguiente (o sea es el ultimo pa cambiar)
                     //hacemos el cambio con el primero 
                        
                        
                        
                    
        
        
        

    }
    
    
    // mete el contenido de los n bits del individuo j a la posicion i 
    public void chancado(int i,int j ){ // el i 
        
        // obtenemos el individuo j completo 
        Individuo ind = poblacionActual.get(j);
        //System.out.println("i="+ i+ " Antes del chancado con " +j  +": " +poblacionActual.get(i).genes);
        
        for(int p = puntoDeCruce; p<cantidadBits;p++  )
        {
           poblacionActual.get(i).genes.set(p, ind.genes.get(p));
            //System.out.println("VALOR DE P=" + p) ;
           
        }
        
        //System.out.println("i="+ i+ " Despues del chancado con " +j  +": " +poblacionActual.get(i).genes);
        
        
    }
    
    public void calcularIntervalosK(){
            float K=0;
            float nuevoIntervaloSuperior=0;
        for(int i=0;i<tamaño;i++)  
        {
            K = funcionObjetivo(poblacionActual.get(i).getNumero())/sumaFA;  
            
            nuevoIntervaloSuperior+=K;
            
            poblacionActual.get(i).supIntervaloK=nuevoIntervaloSuperior;
        }
        
        
    }
    
    
    
    
    
    
    public void calcularPalitosPorAleatorios(){
       
       boolean encontro;
        
        for(int j=0;j<tamaño;j++)  
        {
            //numero entre 0 y 1 float 
            float aleatorio = (float) Math.random();
//            System.out.println("Aleatorio palitos = " + aleatorio);
            encontro = false; 
            for(int i =0;i<tamaño && !encontro ;i++){
                if(i == tamaño-1) poblacionActual.get(i).supIntervaloK = 1.0f;
                
                if(aleatorio <= poblacionActual.get(i).supIntervaloK ){ 
                   encontro = true;
                   poblacionActual.get(i).contadorPalitos++;
                }
            
            }
            
        }
    
    
    }
    
    
    
    //Para quitar a los que no tienen palitos
    public void seleccionYReproduccion(){
        ArrayList<Individuo> poblacionAReproducirse = new ArrayList<>();
        
        //escogemos los individuos a reproducirse 
        for(int j=0;j<tamaño;j++)  
        {
            if(poblacionActual.get(j).contadorPalitos >=2 ) // con mayor cant de palos 
            {
                
                for(int k=0;k< poblacionActual.get(j).contadorPalitos-1 ; k++ )
                    poblacionAReproducirse.add( poblacionActual.get(j) );
                
                //ahora que ya lo metimos a la cola de repr, 
                poblacionActual.get(j).contadorPalitos=1;
                
            }
        }
        
        // recorremos el vector y si tiene 0 palitos, lo remplazamos por un elemento en la cola de reproduccion
        for(int j=0;j<tamaño;j++)  
        {
            if(poblacionActual.get(j).contadorPalitos ==0 ) // con mayor cant de palos 
            {
                poblacionActual.set(j,  poblacionAReproducirse.get(0));
                poblacionAReproducirse.remove(0);
                poblacionActual.get(j).contadorPalitos=1;
                
                
            }
        }
        
        
    
    
    }
    
    
    
    
    
    
    public void generarPoblacionAleatoria(){
        float suma=0;
         
        for(int i=0;i<tamaño;i++)  
        {
            
            Individuo nuevo = (new Individuo(cantidadBits,true)); // llamamos al constructor aleatorio 
            poblacionActual.add(nuevo);
            suma+=funcionObjetivo(nuevo.getNumero());
        
        }
        sumaFA = suma;
    
    }
    
    public float promedioPoblacion(){
       float suma=0;
        for(int i=0;i<tamaño;i++)  
        {
   
            suma+=funcionObjetivo(poblacionActual.get(i).getNumero());
        
        } 
        return suma/tamaño;
    }
    
    
    public float calcularDesvEstandar(){
        float sumatoria=0;
        float promedioTotal = promedioPoblacion();
        for(int i=0;i<tamaño;i++)  
        {
            //                                      xi                          -       xprom       al cuadr
            sumatoria+=Math.pow(funcionObjetivo(poblacionActual.get(i).getNumero()) - promedioTotal ,2);
        
        } 
        return (float) Math.sqrt(sumatoria/(tamaño-1));
    
    }
    
    public String toString(){
        String cad = "";
        cad+="Desv Estandar Poblacion=" + desviacionEstandar + "\n";
        cad+="PuntoDeCruce=" + puntoDeCruce + "\n";
        
   
        for(int i=0;i<poblacionActual.size();i++)  
        {
           
            cad += ("Individuo:" + i + 
                    " Valor: " + poblacionActual.get(i).getNumero() +
                    " f(x)= "+ funcionObjetivo(poblacionActual.get(i).getNumero()) + 
                    " valorBits=" +poblacionActual.get(i).toString() + 
                    " intervK Superior " + poblacionActual.get(i).supIntervaloK +
                    " cantPalitos = " + poblacionActual.get(i).contadorPalitos + "\n"
                    );
        
        }
    return cad; 
    }
    
    public float funcionObjetivo( int x){
        return x*x;
    }
}
