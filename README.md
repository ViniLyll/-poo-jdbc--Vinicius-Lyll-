
# Projeto POO JDBC - Concessionária Automotiva e Fabricantes

## Identificação do aluno

- **Aluno:** Vinicius Lyll Coutinho Santos
- **Disciplina:** Programação Orientada a Objetos (POO)

## Descrição

Aplicação Java que utiliza JDBC para conectar ao PostgreSQL e
realizar operações CRUD nas entidades Montadora e ModeloCarro.
O relacionamento entre as entidades é de um para muitos (1:N).

## Script SQL

```sql
CREATE TABLE montadora (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(80) NOT NULL,
    pais_sede VARCHAR(60)
);

CREATE TABLE modelo_carro (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    tipo_combustivel VARCHAR(40) NOT NULL,
    potencia_cv INT NOT NULL,
    montadora_id INT NOT NULL
        REFERENCES montadora(id)
        ON DELETE RESTRICT
);
```
