--CREATE TABLE tb_usuarios (
--    id BIGSERIAL PRIMARY KEY,
--    nome VARCHAR(100) NOT NULL,
--    usuario VARCHAR(100) NOT NULL UNIQUE,
--    senha VARCHAR(255) NOT NULL,
--    foto TEXT
--);
--
--CREATE TABLE tb_temas (
--    id BIGSERIAL PRIMARY KEY,
--    descricao VARCHAR(255) NOT NULL
--);
--
--CREATE TABLE tb_postagens (
--    id BIGSERIAL PRIMARY KEY,
--    titulo VARCHAR(255) NOT NULL,
--    texto TEXT NOT NULL,
--    data TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
--    tema_id BIGINT,
--    usuario_id BIGINT,
--    CONSTRAINT fk_postagem_tema FOREIGN KEY (tema_id) REFERENCES tb_temas(id) ON DELETE CASCADE,
--    CONSTRAINT fk_postagem_usuario FOREIGN KEY (usuario_id) REFERENCES tb_usuarios(id) ON DELETE CASCADE
--);
