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
    montadora_id INT NOT NULL REFERENCES montadora(id) ON DELETE RESTRICT
);
