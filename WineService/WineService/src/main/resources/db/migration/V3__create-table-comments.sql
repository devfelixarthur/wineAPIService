CREATE TABLE cadastro_comentarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_vinho INT NOT NULL,
    id_usuario INT NOT NULL,
    nome_usuario VARCHAR(255) NOT NULL,
    descricao VARCHAR(500) NOT NULL,
    data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_update TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    status VARCHAR(50) NOT NULL DEFAULT 'Ativo' CHECK (status IN ('Ativo', 'Inativo')),
    CONSTRAINT fk_comentarios_vinho FOREIGN KEY (id_vinho) REFERENCES cadastro_wine (id) ON DELETE CASCADE,
    CONSTRAINT fk_comentarios_usuario FOREIGN KEY (id_usuario) REFERENCES cadastro_usuarios (id) ON DELETE CASCADE
);