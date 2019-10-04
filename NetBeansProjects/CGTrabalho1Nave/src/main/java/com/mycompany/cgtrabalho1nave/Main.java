package com.mycompany.cgtrabalho1nave;

public class Main {

    public static Jogo jogo = new Jogo(800, 700);

    public static void main(String[] args) {

        //Criando e rodando a engine do jogo
        jogo.init();
        jogo.engine();
    }

}
