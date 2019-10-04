package utils;

import model.Desenhavel;

public class MyUtils {

    public static int randInt(int first, int last, int step) {
        int nsteps = (last + 1 - first) / step;
        return first + step * (int) (nsteps * Math.random());
    }
    
    public static boolean calculaColisao(Desenhavel a, Desenhavel b){
        double distancia = Math.sqrt(Math.pow(a.getPosX() - b.getPosX(), 2) + Math.pow(a.getPosY() - b.getPosY(), 2));
        
        return distancia <= (a.getRaio() + b.getRaio());
    }

}
