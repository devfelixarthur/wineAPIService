CREATE TABLE cadastro_wine (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    uva VARCHAR(100) NOT NULL,
    descricao TEXT,
    safra INT NOT NULL CHECK (safra BETWEEN 1000 AND 9999),
    adega VARCHAR(255) NOT NULL,
    pais VARCHAR(100) NOT NULL,
    imagem VARCHAR(500),
    data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_update TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(50) NOT NULL DEFAULT 'Ativo' CHECK (status IN ('Ativo', 'Inativo'))
);