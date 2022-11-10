package edu.upc.dsa.models;
import java.util.LinkedList;
import java.util.List;
public class GameResults {
    String id;
    List<LevelResults> resultados;
    int final_score;

    public GameResults(String id, int final_score) {
        this.setId(id);
        this.setFinal_score(final_score);
        resultados= new LinkedList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<LevelResults> getResultados() {
        return resultados;
    }

    public void setResultados(List<LevelResults> resultados) {
        this.resultados = resultados;
    }

    public int getFinal_score() {
        return final_score;
    }

    public void setFinal_score(int final_score) {
        this.final_score = final_score;
    }
    public int aumentar_score(int i){
        int act=this.getFinal_score();
        int desp= act-i;
        if (desp>0){
            this.setFinal_score(desp);
            return 0;
        }
        else {
            this.setFinal_score(act);
            return -1;
        }
    }
}
