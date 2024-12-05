create table usuario(
                        id serial,
                        nome varchar(255),
                        email varchar(255),
                        senha varchar(255),
                        constraint pkUsuario primary key(id),
                        constraint ukUsuario unique (nome, email, senha)
);
