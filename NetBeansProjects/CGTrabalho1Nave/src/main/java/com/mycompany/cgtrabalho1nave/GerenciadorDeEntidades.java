package com.mycompany.cgtrabalho1nave;

import java.util.ArrayList;
import java.util.List;
import model.Desenhavel;

public class GerenciadorDeEntidades {

    //Lista de desenháveis
    private List<Desenhavel> lFormas = new ArrayList<Desenhavel>();
    //Instância Singleton
    private static GerenciadorDeEntidades instance;

    /**
     * Método responsável por retornar a instancia singleton da classe
     * GerenciadorDeEntidades
     *
     * @return
     */
    public static GerenciadorDeEntidades getInstance() {
        if (instance == null) {
            instance = new GerenciadorDeEntidades();
        }
        return instance;
    }

    public void addForma(Desenhavel d) {
        lFormas.add(d);
    }

    public boolean removeForma(Desenhavel d) {
        return lFormas.remove(d);
    }

    public List<Desenhavel> getlFormas() {
        return lFormas;
    }

    /**
     * Renderiza os itens do lFormas
     */
    public void render() {
        lFormas.forEach(forma -> forma.desenhar());
    }

    /**
     * Verifica se os itens da lFormas ainda precisam ser desenhados na próxima
     * iteração do motor
     */
    public void stillDrawing() {
        for (int i = 0; i < getlFormas().size(); i++) {
            Desenhavel d = getlFormas().get(i);
            d.verificaColisao(getlFormas());
            if (!d.update(getlFormas())) {
                removeForma(d);
            }
        }
    }

    public void clear() {
        lFormas.clear();
    }

}
