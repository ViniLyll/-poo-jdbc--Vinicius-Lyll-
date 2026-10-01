package model;

import java.util.ArrayList;
import java.util.List;

public class Montadora {
    private int id;
    private String nome;
    private String paisSede;
    private List<ModeloCarro> modelos = new ArrayList<>();

    public Montadora() {
    }

    public Montadora(String nome, String paisSede) {
        this.nome = nome;
        this.paisSede = paisSede;
    }

    public Montadora(int id, String nome, String paisSede) {
        this.id = id;
        this.nome = nome;
        this.paisSede = paisSede;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getPaisSede() { return paisSede; }
    public void setPaisSede(String paisSede) { this.paisSede = paisSede; }

    public List<ModeloCarro> getModelos() { return modelos; }
    public void setModelos(List<ModeloCarro> modelos) {
        this.modelos = (modelos == null) ? new ArrayList<>() : modelos;
    }

    public void adicionarModelo(ModeloCarro modelo) {
        if (modelo != null) {
            modelos.add(modelo);
            modelo.setMontadora(this);
        }
    }

    @Override
    public String toString() {
        return "Montadora{" +
                "id=" + id +
                ", nome='" + nome + "'" +
                ", paisSede='" + paisSede + "'" +
                ", quantidadeModelos=" + (modelos == null ? 0 : modelos.size()) +
                '}';
    }
}
