create database biblioteca
default character set utf8
default collate utf8_general_ci;

use biblioteca;

create table usuario (
id_usuario integer NOT NULL AUTO_INCREMENT,
nome_usuario varchar(30) NOT NULL,
login varchar(20) NOT NULL,
senha varchar(20) NOT NULL,
PRIMARY KEY (id_usuario)
) default charset = utf8;

create table livro (
id_livro integer NOT NULL AUTO_INCREMENT,
titulo_livro varchar(30) NOT NULL,
autor_livro varchar(30) NOT NULL,
ano integer,
disponivel boolean,
retirada varchar(20),
PRIMARY KEY (id_livro)
) default charset = utf8;

/*INSERT PADRÃO PARA COMEÇO DO BANCO*/
INSERT INTO livro(titulo_livro, autor_livro, ano, disponivel) VALUES ('Como fazer sentido e bater o martelo', 'Alexandro Aolchique', '2017', true);

INSERT INTO livro(titulo_livro, autor_livro, ano, disponivel) VALUES ('Código Limpo', 'Tio Bob', '2001', true);

INSERT INTO livro(titulo_livro, autor_livro, ano, disponivel) VALUES ('Basquete 101', 'Hortência Marcari', '2010', true);
