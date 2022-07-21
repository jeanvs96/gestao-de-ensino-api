create schema gestao_ensino_api

    CREATE TABLE ENDERECO (
                              ID_ENDERECO bigint NOT NULL,
                              LOGRADOURO text NOT NULL,
                              NUMERO bigint NOT NULL,
                              COMPLEMENTO text,
                              CIDADE text NOT NULL,
                              ESTADO text NOT NULL,
                              CEP text NOT NULL,
                              PRIMARY KEY (ID_ENDERECO)
    );

CREATE SEQUENCE SEQ_ENDERECO
    INCREMENT 1
START 1;

CREATE TABLE PROFESSOR (
                           ID_PROFESSOR bigint NOT NULL,
                           NOME text NOT NULL,
                           TELEFONE text NOT NULL,
                           EMAIL text NOT NULL,
                           REGISTRO_TRABALHO bigint NOT NULL,
                           CARGO text NOT NULL,
                           SALÁRIO DECIMAL(7,2) NOT NULL,
                           ID_ENDERECO bigint,
                           PRIMARY KEY (ID_PROFESSOR),
                           CONSTRAINT FK_PROFESSOR_ID_ENDERECO
                               FOREIGN KEY (ID_ENDERECO)
                                   REFERENCES ENDERECO(ID_ENDERECO)
);

CREATE SEQUENCE SEQ_PROFESSOR
    INCREMENT 1
START 1;

CREATE SEQUENCE SEQ_REGISTRO_TRABALHO
    INCREMENT 1
START 1000;

CREATE TABLE DISCIPLINA (
                            ID_DISCIPLINA bigint NOT NULL,
                            NOME text NOT NULL,
                            ID_PROFESSOR bigint,
                            PRIMARY KEY (ID_DISCIPLINA),
                            CONSTRAINT FK_DISCIPLINA_ID_PROFESSOR
                                FOREIGN KEY (ID_PROFESSOR)
                                    REFERENCES PROFESSOR(ID_PROFESSOR)
);

CREATE SEQUENCE SEQ_DISCIPLINA
    INCREMENT 1
START 1;

CREATE TABLE CURSO (
                       ID_CURSO bigint NOT NULL,
                       NOME text NOT NULL,
                       PRIMARY KEY (ID_CURSO)
);

CREATE SEQUENCE SEQ_CURSO
    INCREMENT 1
START 1;

CREATE TABLE ALUNO (
                       ID_ALUNO bigint NOT NULL,
                       NOME text NOT NULL,
                       TELEFONE text NOT NULL,
                       EMAIL text NOT NULL,
                       MATRICULA bigint NOT NULL,
                       ID_CURSO bigint,
                       ID_ENDERECO bigint,
                       PRIMARY KEY (ID_ALUNO),
                       CONSTRAINT FK_ALUNO_ID_CURSO
                           FOREIGN KEY (ID_CURSO)
                               REFERENCES CURSO(ID_CURSO),
                       CONSTRAINT FK_ALUNO_ID_ENDERECO
                           FOREIGN KEY (ID_ENDERECO)
                               REFERENCES ENDERECO(ID_ENDERECO)
);

CREATE SEQUENCE SEQ_ALUNO
    INCREMENT 1
START 1;

CREATE SEQUENCE SEQ_ALUNO_MATRICULA
    INCREMENT 1
START 1000;

CREATE TABLE NOTAS (
                       ID_NOTAS bigint NOT NULL,
                       N1 DECIMAL(4, 2),
                       N2 DECIMAL(4, 2),
                       N3 DECIMAL(4, 2),
                       N4 DECIMAL(4, 2),
                       MEDIA DECIMAL(4, 2),
                       ID_DISCIPLINA bigint NOT NULL,
                       ID_ALUNO bigint NOT NULL,
                       PRIMARY KEY (ID_NOTAS),
                       CONSTRAINT FK_NOTAS_ID_ALUNO
                           FOREIGN KEY (ID_ALUNO)
                               REFERENCES ALUNO(ID_ALUNO),
                       CONSTRAINT FK_NOTAS_ID_DISCIPLINA
                           FOREIGN KEY (ID_DISCIPLINA)
                               REFERENCES DISCIPLINA(ID_DISCIPLINA)
);

CREATE SEQUENCE SEQ_NOTAS
    INCREMENT 1
START 1;

CREATE TABLE DISCIPLINA_X_CURSO (
                                    ID_DISCIPLINA_X_CURSO bigint NOT NULL,
                                    ID_DISCIPLINA bigint NOT NULL,
                                    ID_CURSO bigint NOT NULL,
                                    PRIMARY KEY (ID_DISCIPLINA_X_CURSO),
                                    CONSTRAINT FK_DISCIPLINA_X_CURSO_ID_CURSO
                                        FOREIGN KEY (ID_CURSO)
                                            REFERENCES CURSO(ID_CURSO),
                                    CONSTRAINT FK_D_X_C_ID_DISCIPLINA
                                        FOREIGN KEY (ID_DISCIPLINA)
                                            REFERENCES DISCIPLINA(ID_DISCIPLINA)
);

CREATE SEQUENCE SEQ_DISCIPLINA_X_CURSO
    INCREMENT 1
START 1;

INSERT INTO GESTAO_ENSINO_API.ENDERECO (ID_ENDERECO, LOGRADOURO, NUMERO, COMPLEMENTO, CIDADE, ESTADO, CEP)
VALUES (nextval('SEQ_ENDERECO'), 'Rua das Ruas', 8, 'Apartament 30', 'São Paulo', 'São Paulo','02256-325');

INSERT INTO GESTAO_ENSINO_API.ENDERECO (ID_ENDERECO, LOGRADOURO, NUMERO, COMPLEMENTO, CIDADE, ESTADO, CEP)
VALUES (nextval('SEQ_ENDERECO'), 'Rua das Máquinas', 22, NULL, 'São Paulo', 'São Paulo','05586-325');

INSERT INTO GESTAO_ENSINO_API.PROFESSOR (ID_PROFESSOR, NOME, TELEFONE, EMAIL, REGISTRO_TRABALHO, CARGO, SALÁRIO, ID_ENDERECO)
VALUES (nextval('SEQ_PROFESSOR'), 'José Antônio', '5511999995555', 'jose.antonio@gmail.com', nextval('SEQ_REGISTRO_TRABALHO'), 'Professor', 5500, 1);

INSERT INTO GESTAO_ENSINO_API.CURSO (ID_CURSO, NOME)
VALUES (nextval('SEQ_CURSO'), 'Engenharia Mecânica');

INSERT INTO GESTAO_ENSINO_API.DISCIPLINA (ID_DISCIPLINA, NOME, ID_PROFESSOR)
VALUES (nextval('SEQ_DISCIPLINA'), 'Física', 1);

INSERT INTO GESTAO_ENSINO_API.ALUNO (ID_ALUNO, NOME, TELEFONE, EMAIL, MATRICULA, ID_CURSO, ID_ENDERECO)
VALUES (nextval('SEQ_ALUNO'), 'Fernando Fernandes', '5522888883333', 'fernando.fernandes@gmail.com', nextval('SEQ_ALUNO_MATRICULA'), 1, 2);

INSERT INTO GESTAO_ENSINO_API.NOTAS (ID_NOTAS, N1, N2, N3, N4, MEDIA, ID_DISCIPLINA, ID_ALUNO)
VALUES (nextval('SEQ_NOTAS'), NULL, NULL, NULL, NULL, NULL, 1, 1)

    INSERT INTO GESTAO_ENSINO_API.DISCIPLINA_X_CURSO (ID_DISCIPLINA_X_CURSO, ID_DISCIPLINA, ID_CURSO)
VALUES (nextval('SEQ_DISCIPLINA_X_CURSO'), 1, 1);

SELECT * FROM CURSO;
SELECT * FROM ENDERECO;
SELECT * FROM ALUNO;



DROP TABLE DISCIPLINA_X_CURSO;
DROP TABLE NOTAS;
DROP TABLE ALUNO;
DROP TABLE DISCIPLINA;
DROP TABLE CURSO;
DROP TABLE PROFESSOR;
DROP TABLE ENDERECO;

TRUNCATE TABLE ENDERECO;
