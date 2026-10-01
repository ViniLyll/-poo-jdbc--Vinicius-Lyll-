package model;

public class ModeloCarro {
    private int id;
    private String nome;
    private String tipoCombustivel;
    private int potenciaCv;
    private Montadora montadora;

    public ModeloCarro() {
    }

    public ModeloCarro(String nome, String tipoCombustivel, int potenciaCv, Montadora montadora) {
        this.nome = nome;
        this.tipoCombustivel = tipoCombustivel;
        this.potenciaCv = potenciaCv;
        this.montadora = montadora;
    }

    public ModeloCarro(int id, String nome, String tipoCombustivel, int potenciaCv, Montadora montadora) {
        this.id = id;
        this.nome = nome;
        this.tipoCombustivel = tipoCombustivel;
        this.potenciaCv = potenciaCv;
        this.montadora = montadora;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getTipoCombustivel() { return tipoCombustivel; }
    public void setTipoCombustivel(String tipoCombustivel) { this.tipoCombustivel = tipoCombustivel; }

    public int getPotenciaCv() { return potenciaCv; }
    public void setPotenciaCv(int potenciaCv) { this.potenciaCv = potenciaCv; }

    public Montadora getMontadora() { return montadora; }
    public void setMontadora(Montadora montadora) { this.montadora = montadora; }

    @Override
    public String toString() {
        return "ModeloCarro{" +
                "id=" + id +
                ", nome='" + nome + "'" +
                ", tipoCombustivel='" + tipoCombustivel + "'" +
                ", potenciaCv=" + potenciaCv +
                ", montadora=" + (montadora == null ? "não informada" : montadora.getNome()) +
                '}';
    }
}
