CREATE TABLE cadastro_avaliacao (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    id_vinho INT NOT NULL,
    avaliacao INT NOT NULL CHECK (avaliacao BETWEEN 1 AND 5),
    status VARCHAR(50) NOT NULL DEFAULT 'Ativo' CHECK (status IN ('Ativo', 'Inativo')),
    data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_update TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_avaliacao_usuario FOREIGN KEY (id_usuario) REFERENCES cadastro_usuarios (id) ON DELETE CASCADE,
    CONSTRAINT fk_avaliacao_vinho FOREIGN KEY (id_vinho) REFERENCES cadastro_wine (id) ON DELETE CASCADE
);