SELECT pg_terminate_backend(pg_stat_activity.pid)
FROM pg_stat_activity
WHERE pg_stat_activity.datname = 'clubemaritimo'
  AND pid <> pg_backend_pid();
DROP
DATABASE clubemaritimo_;
CREATE
DATABASE clubemaritimo;
--apos isto use a base de dados clubemaritimo


drop schema sisclube cascade;
CREATE SCHEMA sisclube;
ALTER
SCHEMA sisclube OWNER TO postgres;

drop schema sisclube_historic cascade;
CREATE SCHEMA sisclube_historic;
ALTER
SCHEMA sisclube_historic OWNER TO postgres;

CREATE
EXTENSION IF NOT EXISTS unaccent;

-- gender
insert into master.gender(id, name)
values (1, 'Feminino');
insert into master.gender(id, name)
values (2, 'Masculino');
select setval('master.gender_id_sequence', 2);

-- value_type
insert into master.value_type(id, name)
values (1, 'Fixo');
insert into master.value_type(id, name)
values (2, 'Percentual');
select setval('master.value_type_id_sequence', 2);

-- service_type
insert into master.service_type(id, name)
values (1, 'Factura');
insert into master.service_type(id, name)
values (2, 'Aviso de Cobraça');
select setval('master.service_type_id_sequence', 2);

-- client_type
insert into master.client_type(id, name)
values (1, 'Sócio');
insert into master.client_type(id, name)
values (2, 'Pessoa Singular');
insert into master.client_type(id, name)
values (3, 'Pessoa Colectiva');
select setval('master.client_type_id_sequence', 3);

-- metric_unit
insert into master.metric_unit(id, name)
values (1, 'm3');
insert into master.metric_unit(id, name)
values (2, 'Kw/h');
insert into master.metric_unit(id, name)
values (3, 'Mt');
select setval('master.metric_unit_id_sequence', 3);

-- notification_type
insert into master.notification_type(id, name)
values (1, 'Sócio para dar parecer');
insert into master.notification_type(id, name)
values (2, 'Agregado do sócio completou 21 anos');
insert into master.notification_type(id, name)
values (3, 'Agregado do sócio completou 25 anos');
select setval('master.notification_type_id_sequence', 3);

-- member_review_status
insert into master.member_review_status(id, name)
values (1, 'Aprovado');
insert into master.member_review_status(id, name)
values (2, 'Desaprovado');
-- insert into master.member_review_status(id, name)
-- values (3, 'Pendente');
select setval('master.member_review_status_id_sequence', 3);

-- member_status
insert into master.member_status(id, name)
values (1, 'Activo');
insert into master.member_status(id, name)
values (2, 'Inactivo');
insert into master.member_status(id, name)
values (3, 'Ausente');
insert into master.member_status(id, name)
values (4, 'Falecido');
select setval('master.member_status_id_sequence', 4);

-- blood_group
insert into master.blood_group(id, name)
values (1, 'A+');
insert into master.blood_group(id, name)
values (2, 'A-');
insert into master.blood_group(id, name)
values (3, 'B+');
insert into master.blood_group(id, name)
values (4, 'B-');
insert into master.blood_group(id, name)
values (5, 'AB+');
insert
intomaster.blood_group(id, name)
values (6, 'AB-');
insert
intomaster.blood_group(id, name)
values (7, 'O-');
insert
intomaster.blood_group(id, name)
values (8, 'O+');
select setval('sisclube.blood_group_id_sequence', 8);

-- household_type
insert
intomaster.household_type(id, name)
values (1, 'Cônjuge');
insert
intomaster.household_type(id, name)
values (2, 'Pai/Mãe');
insert
intomaster.household_type(id, name)
values (3, 'Filho/Filha');
insert
intomaster.household_type(id, name)
values (4, 'Parente (Sobrinho/a, Tio/a, Primo/a, Cunhado/a, etc)');
insert
intomaster.household_type(id, name)
values (5, 'Associado');
insert
intomaster.household_type(id, name)
values (6, 'Ascendente');
insert
intomaster.household_type(id, name)
values (7, 'Descendente');
select setval('sisclube.household_type_id_sequence', 7);

-- identity_document_denomination
insert
intomaster.identity_document_type_denomination(id, name)
values (1, 'Nacional');
insert
intomaster.identity_document_type_denomination(id, name)
values (2, 'Estrangeiro');
select setval('sisclube.identity_document_type_denomination_id_sequence', 2);

-- marital_status
insert
intomaster.marital_status(id, code, name)
values (1, 'S', 'Solteiro/a');
insert
intomaster.marital_status(id, code, name)
values (2, 'C', 'Casado/a');
insert
intomaster.marital_status(id, code, name)
values (3, 'V', 'Viuvo/a');
insert
intomaster.marital_status(id, code, name)
values (4, 'D', 'Divorciado/a');
insert
intomaster.marital_status(id, code, name)
values (5, 'UF', 'Uniao de Factos');
select setval('sisclube.marital_status_id_sequence', 5);

-- identification_document_type
insert
intomaster.identity_document_type(id, name, abbreviation, identity_document_type_denomination_id)
values (1, 'Bilhete de Identidade', 'BI', 1);
insert
intomaster.identity_document_type(id, name, abbreviation, identity_document_type_denomination_id)
values (2, 'Documento de Identificao de Residencia de Estrangeiro', 'DIRE', 2);
insert
intomaster.identity_document_type(id, name, abbreviation, identity_document_type_denomination_id)
values (3, 'Cédula', 'Cédula', 1);
insert
intomaster.identity_document_type(id, name, abbreviation, identity_document_type_denomination_id)
values (4, 'Cartão Diplomático', 'Cartão Diplomático', 2);
insert
intomaster.identity_document_type(id, name, abbreviation, identity_document_type_denomination_id)
values (5, 'Carta de Condução', 'Carta de Condução', 1);
insert
intomaster.identity_document_type(id, name, abbreviation, identity_document_type_denomination_id)
values (6, 'Cartão de Cidadão de Portugal', 'Cartão de Cidadão de Portugal', 2);
insert
intomaster.identity_document_type(id, name, abbreviation, identity_document_type_denomination_id)
values (7, 'Passaporte', 'Passaporte', 1);
select setval('sisclube.identity_document_type_id_sequence', 7);

-- billing_cycle
insert
intomaster.billing_cycle(id, name)
values (1, 'Pagamento único');
insert
intomaster.billing_cycle(id, name)
values (2, 'Mensalmente');
select setval('sisclube.billing_cycle_id_sequence', 2);

-- fee
insert
intomaster.fee(id, name, template_text, billing_cycle_id, apply_penalty)
values (1, 'Jóia', 'Jóia', 1, false);
insert
intomaster.fee(id, name, template_text, billing_cycle_id, apply_penalty)
values (2, 'Quota', 'Quota - {monthYear}.', 2, false);
insert
intomaster.fee(id, name, template_text, billing_cycle_id, apply_penalty)
values (3, 'Aluguer de Espaço para Embarcação', 'Aluguer do espaço {space}, {boat} - {monthYear}', 2, true);
select setval('sisclube.fee_id_sequence', 3);

-- fee_fine
insert
intomaster.fee_fine(id, days_limit, percentage, fee_id)
values (1, 5, 50, 3);
insert
intomaster.fee_fine(id, days_limit, percentage, fee_id)
values (2, -1, 100, 3);
select setval('sisclube.fee_fine_id_sequence', 2);

-- member_type
insert
intomaster.member_type(id, name, pay_gem, gem_value, pay_membership_fee, membership_fee_value, suffix)
values (1, 'Fundador', false, 0, false, 0, 'F');
insert
intomaster.member_type(id, name, pay_gem, gem_value, pay_membership_fee, membership_fee_value, suffix)
values (2, 'Mérito', true, 7500.0, false, 0, 'MR');
insert
intomaster.member_type(id, name, pay_gem, gem_value, pay_membership_fee, membership_fee_value, suffix)
values (3, 'Honorário', false, 0, false, 0, 'H');
insert
intomaster.member_type(id, name, pay_gem, gem_value, pay_membership_fee, membership_fee_value, suffix)
values (4, 'Praticante', true, 7500, true, 700, 'P');
insert
intomaster.member_type(id, name, pay_gem, gem_value, pay_membership_fee, membership_fee_value, suffix)
values (5, 'Ordinário', true, 7500, true, 1000, 'O');
insert
intomaster.member_type(id, name, pay_gem, gem_value, pay_membership_fee, membership_fee_value, suffix)
values (6, 'Menor', false, 0, true, 750, 'M');
insert
intomaster.member_type(id, name, pay_gem, gem_value, pay_membership_fee, membership_fee_value, suffix)
values (7, 'Pessoa Colectiva', false, 0, true, 0, 'PC');
select setval('sisclube.member_type_id_sequence', 9);

-- member_type_history
INSERT
INTOmaster.member_type_history (id, member_type_id, pay_gem, gem_value, pay_membership_fee,
                                        membership_fee_value, start_date_time, end_date_time)
VALUES (1, 2, true, 7500, false, 0, current_timestamp, null),
       (2, 4, true, 7500, true, 700, current_timestamp, null),
       (3, 5, true, 7500, true, 1000, current_timestamp, null),
       (4, 6, false, 0, true, 700, current_timestamp, null),
       (5, 7, false, 0, true, 0, current_timestamp, null);

select setval('sisclube.member_type_history_id_sequence', 5);


-- member_type_vs_fee
INSERT
INTOmaster.member_type_vs_fee (member_type_id, fee_id)
VALUES (5, 1);
INSERT INTO master.member_type_vs_fee (member_type_id, fee_id)
VALUES (5, 2);
INSERT INTO master.member_type_vs_fee (member_type_id, fee_id)
VALUES (2, 1);
INSERT INTO master.member_type_vs_fee (member_type_id, fee_id)
VALUES (4, 1);
INSERT INTO master.member_type_vs_fee (member_type_id, fee_id)
VALUES (4, 2);
INSERT INTO master.member_type_vs_fee (member_type_id, fee_id)
VALUES (4, 3);
INSERT INTO master.member_type_vs_fee (member_type_id, fee_id)
VALUES (6, 1);
INSERT INTO master.member_type_vs_fee (member_type_id, fee_id)
VALUES (6, 2);
INSERT INTO master.member_type_vs_fee (member_type_id, fee_id)
VALUES (7, 2);

-- payment_method
insert into master.payment_method(id, name, has_proof_number)
values (1, 'Dinheiro', false);
insert into master.payment_method(id, name, has_proof_number)
values (2, 'Cheque', true);
insert into master.payment_method(id, name, has_proof_number)
values (3, 'POS', true);
insert into master.payment_method(id, name, has_proof_number)
values (4, 'POS BIM', true);
insert into master.payment_method(id, name, has_proof_number)
values (5, 'Ponto 24 BCI', true);
insert into master.payment_method(id, name, has_proof_number)
values (6, 'Ponto 24 ABSA', true);
insert into master.payment_method(id, name, has_proof_number)
values (7, 'Ponto 24 Barclays', true);
insert into master.payment_method(id, name, has_proof_number)
values (8, 'Deposito Bancario', true);
insert into master.payment_method(id, name, has_proof_number)
values (9, 'Transferencia Bancaria', true);
select setval('sisclube.payment_method_id_sequence', 9);

-- payment_status
insert into master.payment_status(id, name)
values (1, 'Pago');
insert into master.payment_status(id, name)
values (2, 'Pendente');
insert into master.payment_status(id, name)
values (3, 'Cancelado');
select setval('sisclube.paying_status_id_sequence', 3);

-- profession
INSERT INTO master.profession (id, name)
VALUES (1, 'Contabilista');
INSERT INTO master.profession (id, name)
VALUES (3, 'Programador');
INSERT INTO master.profession (id, name)
VALUES (4, 'Motorista');
INSERT INTO master.profession (id, name)
VALUES (5, 'Sem Profissao');
INSERT INTO master.profession (id, name)
VALUES (6, 'Torneiro Mecanico');
INSERT INTO master.profession (id, name)
VALUES (7, 'Domestica');
INSERT INTO master.profession (id, name)
VALUES (8, 'Guarda Livros');
INSERT INTO master.profession (id, name)
VALUES (9, 'Secretaria Administrativa');
INSERT INTO master.profession (id, name)
VALUES (10, 'Economista');
INSERT INTO master.profession (id, name)
VALUES (11, 'Documentalista');
INSERT INTO master.profession (id, name)
VALUES (12, 'Industrial');
INSERT INTO master.profession (id, name)
VALUES (13, 'Engenheiro (a) Informatico (a)');
INSERT INTO master.profession (id, name)
VALUES (14, 'Consultor');
INSERT INTO master.profession (id, name)
VALUES (15, 'Reitor');
INSERT INTO master.profession (id, name)
VALUES (16, 'Director (a)');
INSERT INTO master.profession (id, name)
VALUES (17, 'Biologo (a)');
INSERT INTO master.profession (id, name)
VALUES (18, 'Criador de Gado');
INSERT INTO master.profession (id, name)
VALUES (19, 'Gestor (a)');
INSERT INTO master.profession (id, name)
VALUES (20, 'Jornalista');
INSERT INTO master.profession (id, name)
VALUES (21, 'Engenheiro (a)');
INSERT INTO master.profession (id, name)
VALUES (22, 'Director Comercial');
INSERT INTO master.profession (id, name)
VALUES (23, 'Colector Preparador');
INSERT INTO master.profession (id, name)
VALUES (24, 'Secretaria');
INSERT INTO master.profession (id, name)
VALUES (25, 'Engenheriro Electronico');
INSERT INTO master.profession (id, name)
VALUES (26, 'Tecnico de Planificacao');
INSERT INTO master.profession (id, name)
VALUES (27, 'Jurista');
INSERT INTO master.profession (id, name)
VALUES (28, 'Consultora Publica');
INSERT INTO master.profession (id, name)
VALUES (29, 'Advogado (a)');
INSERT INTO master.profession (id, name)
VALUES (30, 'Sociologa');
INSERT INTO master.profession (id, name)
VALUES (31, 'Empresario (a)');
INSERT INTO master.profession (id, name)
VALUES (32, 'Arquitecto');
INSERT INTO master.profession (id, name)
VALUES (33, 'Tecnico  Comercial');
INSERT INTO master.profession (id, name)
VALUES (34, 'Engenheriro Tecnico');
INSERT INTO master.profession (id, name)
VALUES (35, 'Engenheriro Tecnico Civil');
INSERT INTO master.profession (id, name)
VALUES (36, 'Gerente');
INSERT INTO master.profession (id, name)
VALUES (37, 'Consultor (a)');
INSERT INTO master.profession (id, name)
VALUES (38, 'Administrativo (a)');
INSERT INTO master.profession (id, name)
VALUES (39, 'Comerciante');
INSERT INTO master.profession (id, name)
VALUES (40, 'Tecnico de Mercado');
INSERT INTO master.profession (id, name)
VALUES (41, 'Pescador');
INSERT INTO master.profession (id, name)
VALUES (42, 'Director de Vendas');
INSERT INTO master.profession (id, name)
VALUES (43, 'Arquitecto (a)');
INSERT INTO master.profession (id, name)
VALUES (44, 'Bancario (a)');
INSERT INTO master.profession (id, name)
VALUES (45, 'Director (a) Geral');
INSERT INTO master.profession (id, name)
VALUES (46, 'Professor (a)');
INSERT INTO master.profession (id, name)
VALUES (47, 'Professor (a) Universitario (a)');
INSERT INTO master.profession (id, name)
VALUES (48, 'Engenheiro (a) Civil');
INSERT INTO master.profession (id, name)
VALUES (49, 'Hoteleira');
INSERT INTO master.profession (id, name)
VALUES (50, 'Pedagogo (a)');
INSERT INTO master.profession (id, name)
VALUES (51, 'Despachante');
INSERT INTO master.profession (id, name)
VALUES (52, 'Tecnico de Frios');
INSERT INTO master.profession (id, name)
VALUES (53, 'Tecnicas de Contas');
INSERT INTO master.profession (id, name)
VALUES (54, 'Estudante');
INSERT INTO master.profession (id, name)
VALUES (55, 'Empregada  Banc');
INSERT INTO master.profession (id, name)
VALUES (56, 'Director Bancario');
INSERT INTO master.profession (id, name)
VALUES (57, 'Tradutor (a)');
INSERT INTO master.profession (id, name)
VALUES (58, 'Engenheiro (a) Florestal');
INSERT INTO master.profession (id, name)
VALUES (59, 'Assistente de Programa');
INSERT INTO master.profession (id, name)
VALUES (60, 'Funcionario Publico');
INSERT INTO master.profession (id, name)
VALUES (61, 'Assessor (a)');
INSERT INTO master.profession (id, name)
VALUES (62, 'Oficial de Projecto');
INSERT INTO master.profession (id, name)
VALUES (63, 'Informatico (a)');
INSERT INTO master.profession (id, name)
VALUES (64, 'Engenheiro (a) de Maquinas');
INSERT INTO master.profession (id, name)
VALUES (65, 'Auditor (a)');
INSERT INTO master.profession (id, name)
VALUES (66, 'Administrador (a) de Escritorio');
INSERT INTO master.profession (id, name)
VALUES (67, 'Antropologo (a)');
INSERT INTO master.profession (id, name)
VALUES (68, 'Diplomata');
INSERT INTO master.profession (id, name)
VALUES (69, 'Publicitario (a)');
INSERT INTO master.profession (id, name)
VALUES (70, 'Agronomo (a)');
INSERT INTO master.profession (id, name)
VALUES (71, 'Tecnico (a) de Maquinas');
INSERT INTO master.profession (id, name)
VALUES (72, 'Docente');
INSERT INTO master.profession (id, name)
VALUES (73, 'Gestor (a) de Empresas');
INSERT INTO master.profession (id, name)
VALUES (74, 'Medico (a)');
INSERT INTO master.profession (id, name)
VALUES (75, 'Serralheiro de Aluminios');
INSERT INTO master.profession (id, name)
VALUES (76, 'Tecnico (a) Bancario (a)');
INSERT INTO master.profession (id, name)
VALUES (77, 'Tecnico (a) de Aluminio');
INSERT INTO master.profession (id, name)
VALUES (78, 'Acessor (a)');
INSERT INTO master.profession (id, name)
VALUES (79, 'Engenheiro (a) Mecanico');
INSERT INTO master.profession (id, name)
VALUES (80, 'Promotor (a) de Vendas');
INSERT INTO master.profession (id, name)
VALUES (81, 'Piloto aviador (a)');
INSERT INTO master.profession (id, name)
VALUES (82, 'Secretaria Executiva');
INSERT INTO master.profession (id, name)
VALUES (83, 'Tecnico Informatico');
INSERT INTO master.profession (id, name)
VALUES (84, 'Auditor Financeiro');
INSERT INTO master.profession (id, name)
VALUES (85, 'Torneiro');
INSERT INTO master.profession (id, name)
VALUES (86, 'Acessor (a) da Direccao');
INSERT INTO master.profession (id, name)
VALUES (87, 'Oficial de Comunicacao');
INSERT INTO master.profession (id, name)
VALUES (88, 'Piloto');
INSERT INTO master.profession (id, name)
VALUES (89, 'Director de Obras');
INSERT INTO master.profession (id, name)
VALUES (90, 'Dentista');
INSERT INTO master.profession (id, name)
VALUES (91, 'Engenheiro (a) de Software');
INSERT INTO master.profession (id, name)
VALUES (92, 'Engenheiro (a) Agronomo');
INSERT INTO master.profession (id, name)
VALUES (93, 'Enfermeiro (a)');
INSERT INTO master.profession (id, name)
VALUES (94, 'Geografo (a)');
INSERT INTO master.profession (id, name)
VALUES (95, 'Instrutor');
INSERT INTO master.profession (id, name)
VALUES (96, 'Auxiliar Administrativo');
INSERT INTO master.profession (id, name)
VALUES (97, 'Actriz');
INSERT INTO master.profession (id, name)
VALUES (98, 'Gestor (a) Comercial');
INSERT INTO master.profession (id, name)
VALUES (99, 'Psicologo (a)');
INSERT INTO master.profession (id, name)
VALUES (100, 'Fotografo (a)');
INSERT INTO master.profession (id, name)
VALUES (101, 'Tecnico (a)');
INSERT INTO master.profession (id, name)
VALUES (102, 'Mergulhador (a)');
INSERT INTO master.profession (id, name)
VALUES (103, 'Quadro Superior');
INSERT INTO master.profession (id, name)
VALUES (104, 'Assistente');
INSERT INTO master.profession (id, name)
VALUES (105, 'Chefe de Departamento Electronico');
INSERT INTO master.profession (id, name)
VALUES (106, 'Chefe de Departamento de Stock');
INSERT INTO master.profession (id, name)
VALUES (107, 'Tecnico (a) Supervisao Higiene e Seguranca');
INSERT INTO master.profession (id, name)
VALUES (108, 'Coordenador (a)');
INSERT INTO master.profession (id, name)
VALUES (109, 'Agente Imobiliaria');
INSERT INTO master.profession (id, name)
VALUES (110, 'Gestor (a) de Comunicacao');
INSERT INTO master.profession (id, name)
VALUES (111, 'Analista');
INSERT INTO master.profession (id, name)
VALUES (112, 'Tecnico de Seguros');
INSERT INTO master.profession (id, name)
VALUES (113, 'Administrador (a)');
INSERT INTO master.profession (id, name)
VALUES (114, 'Engenheiro (a) Ambiental');
INSERT INTO master.profession (id, name)
VALUES (115, 'Transitario (a)');
INSERT INTO master.profession (id, name)
VALUES (116, 'Designer');
INSERT INTO master.profession (id, name)
VALUES (117, 'Director (a) Financeiro (a)');
INSERT INTO master.profession (id, name)
VALUES (118, 'Engenheiro (a) Aeronautico');
INSERT INTO master.profession (id, name)
VALUES (119, 'Militar');
INSERT INTO master.profession (id, name)
VALUES (120, 'Escriturario (a)');
INSERT INTO master.profession (id, name)
VALUES (121, 'Educador (a)');
INSERT INTO master.profession (id, name)
VALUES (122, 'Cooperante');
INSERT INTO master.profession (id, name)
VALUES (123, 'Director (a) Tecnico (a)');
INSERT INTO master.profession (id, name)
VALUES (124, 'Investigador (a)');
INSERT INTO master.profession (id, name)
VALUES (125, 'Editor (a)');
INSERT INTO master.profession (id, name)
VALUES (126, 'Saude Publica');
INSERT INTO master.profession (id, name)
VALUES (127, 'Engenheiro (a) Industrial');
INSERT INTO master.profession (id, name)
VALUES (128, 'Tecnico (a) de Construcao Civil');
INSERT INTO master.profession (id, name)
VALUES (129, 'Relacoes Publicas');
INSERT INTO master.profession (id, name)
VALUES (130, 'Enginheiro (a) Geologo (a)');
INSERT INTO master.profession (id, name)
VALUES (131, 'Auditor (a) Bancario (a)');
INSERT INTO master.profession (id, name)
VALUES (132, 'Director (a) de Projectos');
INSERT INTO master.profession (id, name)
VALUES (133, 'Assistente de Projectos');
INSERT INTO master.profession (id, name)
VALUES (134, 'Secretario (a) Geral');
INSERT INTO master.profession (id, name)
VALUES (135, 'Electricista');
INSERT INTO master.profession (id, name)
VALUES (136, 'Financeiro (a)');
INSERT INTO master.profession (id, name)
VALUES (137, 'Gestor (a) Financeiro (a)');
INSERT INTO master.profession (id, name)
VALUES (138, 'Estilista');
INSERT INTO master.profession (id, name)
VALUES (139, 'Reformado (a)');
INSERT INTO master.profession (id, name)
VALUES (140, 'Treinador');
INSERT INTO master.profession (id, name)
VALUES (141, 'Tecnico (a) de Obras');
INSERT INTO master.profession (id, name)
VALUES (142, 'Musico');
INSERT INTO master.profession (id, name)
VALUES (143, 'Logistico (a)');
INSERT INTO master.profession (id, name)
VALUES (144, 'Assistente Social');
INSERT INTO master.profession (id, name)
VALUES (145, 'Estelicista');
INSERT INTO master.profession (id, name)
VALUES (146, 'Cozinheiro (a)');
INSERT INTO master.profession (id, name)
VALUES (147, 'Chefe da Cozinha');
INSERT INTO master.profession (id, name)
VALUES (148, 'Controller');
INSERT INTO master.profession (id, name)
VALUES (149, 'Artista');
INSERT INTO master.profession (id, name)
VALUES (150, 'Missionario (a)');
INSERT INTO master.profession (id, name)
VALUES (151, 'Maestro (a)');
INSERT INTO master.profession (id, name)
VALUES (152, 'Construcao Civil');
INSERT INTO master.profession (id, name)
VALUES (153, 'Comunicologo (a)');
INSERT INTO master.profession (id, name)
VALUES (154, 'Tecnico (a) Aeronautica');
INSERT INTO master.profession (id, name)
VALUES (155, 'Cooperante Internacional');
INSERT INTO master.profession (id, name)
VALUES (156, 'Chef');
INSERT INTO master.profession (id, name)
VALUES (157, 'Radiologista');
INSERT INTO master.profession (id, name)
VALUES (158, 'Negociante');
INSERT INTO master.profession (id, name)
VALUES (159, 'Tecnico (a) de Supervisao de Laboratorio');
INSERT INTO master.profession (id, name)
VALUES (160, 'Metalomecanico (a)');
INSERT INTO master.profession (id, name)
VALUES (161, 'Tecnico (a) de Marketing');
INSERT INTO master.profession (id, name)
VALUES (162, 'Cartografo');
INSERT INTO master.profession (id, name)
VALUES (163, 'Agente Contratual');
INSERT INTO master.profession (id, name)
VALUES (164, 'HR');
INSERT INTO master.profession (id, name)
VALUES (165, 'Engenheiro (a) Alimentar');
INSERT INTO master.profession (id, name)
VALUES (166, 'Director (a) Executivo (a)');
INSERT INTO master.profession (id, name)
VALUES (167, 'Cientista Politica');
INSERT INTO master.profession (id, name)
VALUES (168, 'Agente de Viagem');
INSERT INTO master.profession (id, name)
VALUES (169, 'Analista de Sistema');
INSERT INTO master.profession (id, name)
VALUES (170, 'Guia');
INSERT INTO master.profession (id, name)
VALUES (171, 'Nutricionista');
INSERT INTO master.profession (id, name)
VALUES (172, 'Especialista Agricola');
INSERT INTO master.profession (id, name)
VALUES (173, 'Bailarina (o)');
INSERT INTO master.profession (id, name)
VALUES (174, 'Aduaneiro (a)');
INSERT INTO master.profession (id, name)
VALUES (175, 'Pesquisador (a)');
INSERT INTO master.profession (id, name)
VALUES (176, 'Porteiro (a)');
INSERT INTO master.profession (id, name)
VALUES (177, 'Carpinteiro (a)');
INSERT INTO master.profession (id, name)
VALUES (178, 'Emissario (a)');
INSERT INTO master.profession (id, name)
VALUES (179, 'Adido de Seguranca');
INSERT INTO master.profession (id, name)
VALUES (180, 'Conselheiro (a)');
INSERT INTO master.profession (id, name)
VALUES (181, 'Topografo (a)');
INSERT INTO master.profession (id, name)
VALUES (182, 'Veterinario (a)');
INSERT INTO master.profession (id, name)
VALUES (183, 'Tecnico (a) de Supervisao de Desenvolvimento');
INSERT INTO master.profession (id, name)
VALUES (184, 'Embaixador (a)');
INSERT INTO master.profession (id, name)
VALUES (185, 'Oficial');
INSERT INTO master.profession (id, name)
VALUES (186, 'Procurador (a)');
INSERT INTO master.profession (id, name)
VALUES (187, 'Engenheiro (a) Quimico');
INSERT INTO master.profession (id, name)
VALUES (188, 'Tecnico (a) Recursos Humanos');
INSERT INTO master.profession (id, name)
VALUES (189, 'Tecnico (a) de Cooperacao');
INSERT INTO master.profession (id, name)
VALUES (190, 'Adido');
INSERT INTO master.profession (id, name)
VALUES (191, 'Encarregada da S.C');
INSERT INTO master.profession (id, name)
VALUES (192, 'Marketing');
INSERT INTO master.profession (id, name)
VALUES (193, 'Tecnico (a) de Seguros');
INSERT INTO master.profession (id, name)
VALUES (194, 'Tecnico (a) Informatico (a)');
INSERT INTO master.profession (id, name)
VALUES (195, 'Acessor (a) Juridico (a)');
INSERT INTO master.profession (id, name)
VALUES (196, 'Director Tecnico e Comercial');
INSERT INTO master.profession (id, name)
VALUES (197, 'Cientista');
INSERT INTO master.profession (id, name)
VALUES (198, 'Delegado (a)');
INSERT INTO master.profession (id, name)
VALUES (199, 'Programa Manageer');
INSERT INTO master.profession (id, name)
VALUES (200, 'Voluntario (a)');
INSERT INTO master.profession (id, name)
VALUES (201, 'Especialista Prot. Crianca');
INSERT INTO master.profession (id, name)
VALUES (202, 'Funcionaria Consular');
INSERT INTO master.profession (id, name)
VALUES (203, 'Monitor');
INSERT INTO master.profession (id, name)
VALUES (204, 'Caixa');
INSERT INTO master.profession (id, name)
VALUES (205, 'Consultor Financeiro(a)');
INSERT INTO master.profession (id, name)
VALUES (206, 'Chefe de Missao');
INSERT INTO master.profession (id, name)
VALUES (207, 'Avicultor(a)');
INSERT INTO master.profession (id, name)
VALUES (208, 'Hospedeira de avião');
INSERT INTO master.profession (id, name)
VALUES (209, 'Analista de negócios');
INSERT INTO master.profession (id, name)
VALUES (210, 'Esteticista');
INSERT INTO master.profession (id, name)
VALUES (211, 'Produtora cultural');
INSERT INTO master.profession (id, name)
VALUES (212, 'Conta Própria');
INSERT INTO master.profession (id, name)
VALUES (213, 'Farmaceutico (a)');
INSERT INTO master.profession (id, name)
VALUES (214, 'Agente de Protecção e Género');
INSERT INTO master.profession (id, name)
VALUES (215, 'Free Lancer');
INSERT INTO master.profession (id, name)
VALUES (216, 'Emissora de bilhetes de avião');
INSERT INTO master.profession (id, name)
VALUES (217, 'Epidemiologista');
INSERT INTO master.profession (id, name)
VALUES (218, 'Funcionaria');
INSERT INTO master.profession (id, name)
VALUES (219, 'Oficial de Chancelaria');
INSERT INTO master.profession (id, name)
VALUES (220, 'Criminologista');
INSERT INTO master.profession (id, name)
VALUES (221, 'Reformado/a');
INSERT INTO master.profession (id, name)
VALUES (222, 'Supervisor(a)');
INSERT INTO master.profession (id, name)
VALUES (223, 'Juiz/a');
INSERT INTO master.profession (id, name)
VALUES (224, 'Magistrado(a) Judicial');
INSERT INTO master.profession (id, name)
VALUES (225, 'Terapeuta ocupacional');
INSERT INTO master.profession (id, name)
VALUES (226, 'Assistente de Projectos Humanitários');
select setval('sisclube.profession_id_sequence', 226);

-- countries
INSERT INTO master.country (id, gentilic, name, phone_code, abbreviation)
VALUES (1, 'Moçambicano', 'Moçambique', '+258', 'MOZ');
INSERT INTO master.country (id, gentilic, name, phone_code, abbreviation)
VALUES (2, 'Sul Africana', 'Republica Africa do Sul', '3', 'RSA');
INSERT INTO master.country (id, gentilic, name, phone_code, abbreviation)
VALUES (4, 'Portuguesa', 'Portugal', null, 'PT');
INSERT INTO master.country (id, gentilic, name, phone_code, abbreviation)
VALUES (5, 'Francesa', 'Franca', null, 'FR');
INSERT INTO master.country (id, gentilic, name, phone_code, abbreviation)
VALUES (6, 'Zambiana', 'Zambia', null, 'ZA');
INSERT INTO master.country (id, gentilic, name, phone_code, abbreviation)
VALUES (7, 'Brasileira', 'Brasil', null, 'BR');
INSERT INTO master.country (id, gentilic, name, phone_code, abbreviation)
VALUES (8, 'Alemã', 'Alemanha', null, 'AL');
INSERT INTO master.country (id, gentilic, name, phone_code, abbreviation)
VALUES (9, 'Italiana', 'Italia', null, 'IT');
INSERT INTO master.country (id, gentilic, name, phone_code, abbreviation)
VALUES (10, 'Holandesa', 'Holanda', null, 'H');
INSERT INTO master.country (id, gentilic, name, phone_code, abbreviation)
VALUES (11, 'Angolana', 'Angola', null, 'AO');
INSERT INTO master.country (id, gentilic, name, phone_code, abbreviation)
VALUES (12, 'Britanico (a)', 'Englaterra', null, 'EN');
INSERT INTO master.country (id, gentilic, name, phone_code, abbreviation)
VALUES (13, 'Bulgaro', 'Bulgaria', null, 'BGR');
INSERT INTO master.country (id, gentilic, name, phone_code, abbreviation)
VALUES (14, 'Australiana', 'Australia', null, 'AUS');
INSERT INTO master.country (id, gentilic, name, phone_code, abbreviation)
VALUES (15, 'Ucraniana', 'Ucrania', null, 'UKR');
INSERT INTO master.country (id, gentilic, name, phone_code, abbreviation)
VALUES (16, 'Boliviana', 'Bolivia', null, 'BO');
INSERT INTO master.country (id, gentilic, name, phone_code, abbreviation)
VALUES (17, 'Espanhola', 'Espanha', null, 'ESP');
INSERT INTO master.country (id, gentilic, name, phone_code, abbreviation)
VALUES (18, 'Botswana', 'Botswana', null, 'BW');
INSERT INTO master.country (id, gentilic, name, phone_code, abbreviation)
VALUES (19, 'Canadiano (a)', 'Canada', null, 'CA');
INSERT INTO master.country (id, gentilic, name, phone_code, abbreviation)
VALUES (20, 'Belga', 'Belgica', null, 'BE');
INSERT INTO master.country (id, gentilic, name, phone_code, abbreviation)
VALUES (21, 'Irlandesa', 'Irlanda', null, 'IE');
INSERT INTO master.country (id, gentilic, name, phone_code, abbreviation)
VALUES (22, 'Americana', 'Estados Unidos America', null, 'EUA');
INSERT INTO master.country (id, gentilic, name, phone_code, abbreviation)
VALUES (23, 'Sueca', 'Suica', null, 'SWI');
INSERT INTO master.country (id, gentilic, name, phone_code, abbreviation)
VALUES (24, 'Dinamarquesa', 'Dinamarca', null, 'DK');
INSERT INTO master.country (id, gentilic, name, phone_code, abbreviation)
VALUES (25, 'Argentina', 'Argentina', null, 'ARG');
INSERT INTO master.country (id, gentilic, name, phone_code, abbreviation)
VALUES (26, 'Chilena', 'Chile', null, 'CR');
INSERT INTO master.country (id, gentilic, name, phone_code, abbreviation)
VALUES (27, 'Venezuelana', 'Venezuela', null, 'VEN');
INSERT INTO master.country (id, gentilic, name, phone_code, abbreviation)
VALUES (28, 'Coreana', 'Coreia', null, 'KR');
INSERT INTO master.country (id, gentilic, name, phone_code, abbreviation)
VALUES (29, 'Mexicana', 'Mexico', null, 'MEX');
INSERT INTO master.country (id, gentilic, name, phone_code, abbreviation)
VALUES (30, 'Japonesa', 'Japao', null, 'JP');
INSERT INTO master.country (id, gentilic, name, phone_code, abbreviation)
VALUES (31, 'Estoniana', 'Estonia', null, 'EE');
INSERT INTO master.country (id, gentilic, name, phone_code, abbreviation)
VALUES (32, 'Indiana', 'India', null, 'IN');
INSERT INTO master.country (id, gentilic, name, phone_code, abbreviation)
VALUES (33, 'Kenyana', 'Kenia', null, 'KE');
INSERT INTO master.country (id, gentilic, name, phone_code, abbreviation)
VALUES (34, 'Ganesa', 'Gana', null, 'GH');
INSERT INTO master.country (id, gentilic, name, phone_code, abbreviation)
VALUES (35, 'Finlandesa', 'Finlandia', null, 'FIN');
INSERT INTO master.country (id, gentilic, name, phone_code, abbreviation)
VALUES (36, 'Polonia', 'Polonia', null, 'POL');
INSERT INTO master.country (id, gentilic, name, phone_code, abbreviation)
VALUES (37, 'Chinesa', 'China', null, 'CHN');
INSERT INTO master.country (id, gentilic, name, phone_code, abbreviation)
VALUES (38, 'Turca', 'Turquia', null, 'TUR');
INSERT INTO master.country (id, gentilic, name, phone_code, abbreviation)
VALUES (39, 'Eritreia', 'Eritreia', null, 'ER');
INSERT INTO master.country (id, gentilic, name, phone_code, abbreviation)
VALUES (40, 'Hungara', 'Hungria', null, 'HR');
INSERT INTO master.country (id, gentilic, name, phone_code, abbreviation)
VALUES (41, 'Palestiniana', 'Palestina', null, 'Pl');
INSERT INTO master.country (id, gentilic, name, phone_code, abbreviation)
VALUES (42, 'Kazakh', 'Kasaquistao', null, 'KQ');
INSERT INTO master.country (id, gentilic, name, phone_code, abbreviation)
VALUES (43, 'Neo Zelandese', 'Nova Zelandia', null, 'NZ');
INSERT INTO master.country (id, gentilic, name, phone_code, abbreviation)
VALUES (44, 'Russa', 'Russia', null, 'RS');
INSERT INTO master.country (id, gentilic, name, phone_code, abbreviation)
VALUES (45, 'Egipcia', 'Egipto', null, 'EG');
INSERT INTO master.country (id, gentilic, name, phone_code, abbreviation)
VALUES (46, 'Luxemburga', 'Luxemburgo', null, 'LX');
INSERT INTO master.country (id, gentilic, name, phone_code, abbreviation)
VALUES (47, 'Ugandesa', 'Uganda', null, 'UG');
INSERT INTO master.country (id, gentilic, name, phone_code, abbreviation)
VALUES (48, 'Cubana', 'Cuba', null, 'CU');
INSERT INTO master.country (id, gentilic, name, phone_code, abbreviation)
VALUES (49, 'Zimbabweana', 'Zimbabwe', null, 'ZW');
INSERT INTO master.country (id, gentilic, name, phone_code, abbreviation)
VALUES (50, 'Checa', 'República Checa', null, 'CZE');
INSERT INTO master.country (id, gentilic, name, phone_code, abbreviation)
VALUES (51, 'Islandesa', 'Islândia', null, 'ISL');
INSERT INTO master.country (id, gentilic, name, phone_code, abbreviation)
VALUES (52, 'Indonésia', 'Indonésia', null, 'INDO');
INSERT INTO master.country (id, gentilic, name, phone_code, abbreviation)
VALUES (53, 'Libanesa', 'Libano', null, 'LB');
INSERT INTO master.country (id, gentilic, name, phone_code, abbreviation)
VALUES (54, 'Peruano/a', 'Perú', null, 'Perú');
INSERT INTO master.country (id, gentilic, name, phone_code, abbreviation)
VALUES (55, 'Nigeriano/a', 'Nigeria', null, 'NIG');
INSERT INTO master.country (id, gentilic, name, phone_code, abbreviation)
VALUES (56, 'Namibiana', 'Namíbia', null, 'NA');
select setval('sisclube.user_id_sequence', 56);


-- modality
INSERT INTO master.modality (id, code, name)
VALUES (1, '003', 'WindSurf');
INSERT INTO master.modality (id, code, name)
VALUES (2, '004', 'Natacao');
INSERT INTO master.modality (id, code, name)
VALUES (3, '001', 'Vela');
INSERT INTO master.modality (id, code, name)
VALUES (4, '002', 'Canoagem');
INSERT INTO master.modality (id, code, name)
VALUES (5, '005', 'Pesca');
INSERT INTO master.modality (id, code, name)
VALUES (6, '006', 'Baskete');
INSERT INTO master.modality (id, code, name)
VALUES (7, '007', 'Futsal');
INSERT INTO master.modality (id, code, name)
VALUES (8, '008', 'Desportos Radicias');
INSERT INTO master.modality (id, code, name)
VALUES (9, 'Ginásio', 'Ginásio');
INSERT INTO master.modality (id, code, name)
VALUES (10, 'Beach Tenis', 'Frescobol');
INSERT INTO master.modality (id, code, name)
VALUES (11, 'Am', 'Artes Marciais');
INSERT INTO master.modality (id, code, name)
VALUES (12, 'Surf', 'Surf');
INSERT INTO master.modality (id, code, name)
VALUES (13, 'Mergulho', 'Mergulho');
INSERT INTO master.modality (id, code, name)
VALUES (14, 'Motonáutica', 'Motonáutica');
INSERT INTO master.modality (id, code, name)
VALUES (15, 'Vólei', 'Vólei');
select setval('sisclube.modality_id_sequence', 15);

-- allergy
INSERT INTO master.allergy (id, name)
VALUES (1, 'A Mofo');
INSERT INTO master.allergy (id, name)
VALUES (2, 'A Picada de Insectos');
INSERT INTO master.allergy (id, name)
VALUES (3, 'A Poeira');
INSERT INTO master.allergy (id, name)
VALUES (4, 'A Vegetais');
INSERT INTO master.allergy (id, name)
VALUES (5, 'A Cafeina');
INSERT INTO master.allergy (id, name)
VALUES (6, 'Sulfamidas');
INSERT INTO master.allergy (id, name)
VALUES (7, 'Penicilina');
INSERT INTO master.allergy (id, name)
VALUES (8, 'Ciprofloxicin');
INSERT INTO master.allergy (id, name)
VALUES (9, 'mexilhão - ostras');
INSERT INTO master.allergy (id, name)
VALUES (10, 'Pimentos');
INSERT INTO master.allergy (id, name)
VALUES (11, 'Amoxicilina');
INSERT INTO master.allergy (id, name)
VALUES (12, 'Ceclor (medicamento)');
INSERT INTO master.allergy (id, name)
VALUES (13, 'Atopia');
INSERT INTO master.allergy (id, name)
VALUES (14, 'Polen');
INSERT INTO master.allergy (id, name)
VALUES (15, 'Mariscos');
INSERT INTO master.allergy (id, name)
VALUES (16, 'Relva');
INSERT INTO master.allergy (id, name)
VALUES (17, 'Açúcar');
INSERT INTO master.allergy (id, name)
VALUES (18, 'Rinite Alérgica');
INSERT INTO master.allergy (id, name)
VALUES (19, 'Tetraciclina');
INSERT INTO master.allergy (id, name)
VALUES (20, 'Ibrufen');
INSERT INTO master.allergy (id, name)
VALUES (21, 'Metamizol ou Dipirona');
INSERT INTO master.allergy (id, name)
VALUES (22, 'Aspirina');
INSERT INTO master.allergy (id, name)
VALUES (23, 'Ácaros');
INSERT INTO master.allergy (id, name)
VALUES (24, 'Urticária');
INSERT INTO master.allergy (id, name)
VALUES (25, 'Sinusite');
INSERT INTO master.allergy (id, name)
VALUES (26, 'lactose');
INSERT INTO master.allergy (id, name)
VALUES (27, 'Gluten');
INSERT INTO master.allergy (id, name)
VALUES (28, 'Latex');
INSERT INTO master.allergy (id, name)
VALUES (29, 'Asma');
select setval('sisclube.allergy_id_sequence', 29);

-- user
INSERT INTO master."user" (id, password, tries, username, active)
VALUES (1, '{noop}admin', 5, 'admin', true);
select setval('sisclube.user_id_sequence', 1);

-- module
insert into master.module(id, code, name)
values (1, 'MEMBER', 'Gestão de Sócios');
insert into master.module(id, code, name)
values (2, 'REPORTS', 'Relatórios');
insert into master.module(id, code, name)
values (3, 'PAYMENT_ORDER', 'Ordem de Pagamento');
insert into master.module(id, code, name)
values (4, 'HOUSEHOLD', 'Ordem de Pagamento');
insert into master.module(id, code, name)
values (5, 'PAYMENT', 'Pagamentos');

select setval('sisclube.module_id_sequence', 5);

-- user_group
insert into master.user_group(id, code, name, description)
values (1, 'ADMIN', 'Administrador', 'Administrador do sistema com acesso total');
insert into master.user_group(id, code, name, description)
values (2, 'TECHNICIAN', 'Técnico', 'Técnico responsável por resolver relatórios');
insert into master.user_group(id, code, name, description)
values (3, 'MANAGER', 'Gestor', 'Gestor com permissões de supervisão');
insert into master.user_group(id, code, name, description)
values (4, 'SUPERVISOR', 'Supervisor', 'Supervisor com permissões de monitoramento');
insert into master.user_group(id, code, name, description)
values (5, 'USER', 'Utilizador', 'Utilizador básico do sistema');

select setval('sisclube.user_group_id_sequence', 5);

-- permission
insert into master.permission(id, code, name, module_id)
values (1, 'REVIEW_MEMBER', 'Dar parecer ao Sócio', 1);

insert into master.permission(id, code, name, module_id)
values (2, 'MEMBERS_LIST', 'Lista de Sócios', 2);
insert into master.permission(id, code, name, module_id)
values (3, 'BOAT_DEPARTURE_TO_SEA', 'Lista de Saida de Embarcações para o Mar', 2);

insert into master.permission(id, code, name, module_id)
values (4, 'PAYMENT_ORDER_CREATE', 'Cria ordem de paramento?', 3);

insert into master.permission(id, code, name, module_id)
values (5, 'HOUSEHOLD_AGE_ALERT', 'Ver notificação sobre idade do agregado', 4);

insert into master.permission(id, code, name, module_id)
values (6, 'MEMBER_RENUMERATE', 'Renumerar os sócios', 1);

insert into master.permission(id, code, name, module_id)
values (7, 'MEMBER_GENERATE_MULTIPLE_CARDS', 'Pode gerar cartão mais de uma vez!', 1);

insert into master.permission(id, code, name, module_id)
values (8, 'DELETE_RECEIPT', 'Pode apagar recibos de Serviço, Imediatos e de Sócios !', 5);

insert into master.permission(id, code, name, module_id)
values (9, 'CREATE_SERVICE_INVOICE', 'Pode criar facturas de serviço!', 5);

insert into master.permission(id, code, name, module_id)
values (10, 'CREATE_SERVICE_RECEIPT', 'Pode criar recibo de serviço!', 5);

insert into master.permission(id, code, name, module_id)
values (11, 'CREATE_IMMEDIATE_RECEIPT', 'Pode criar recibo imediatos!', 5);

insert into master.permission(id, code, name, module_id)
values (12, 'CREATE_MEMBER_RECEIPT', 'Pode criar recibo de sócios!', 5);

insert into master.permission(id, code, name, module_id)
values (13, 'CREATE_MEMBER_INVOICE', 'Pode criar factura de sócios!', 5);

insert into master.permission(id, code, name, module_id)
values (14, 'CREATE_BILLING_NOTICE', 'Pode criar aviso de cobrança!', 5);

insert into master.permission(id, code, name, module_id)
values (15, 'EDIT_BILLING_NOTICE', 'Pode editar aviso de cobrança!', 5);

insert into master.permission(id, code, name, module_id)
values (16, 'EDIT_SERVICE_INVOICE', 'Pode editar factura de serviço!', 5);

insert into master.permission(id, code, name, module_id)
values (17, 'DELETE_SERVICE_INVOICE', 'Pode apagar factura de serviço!', 5);

insert into master.permission(id, code, name, module_id)
values (18, 'DELETE_BILLING_NOTICE', 'Pode apagar aviso de cobrança!', 5);

insert into master.permission(id, code, name, module_id)
values (19, 'DELETE_MEMBER_INVOICE', 'Pode apagar factura de sócios!', 5);

insert into master.permission(id, code, name, module_id)
values (20, 'DELETE_DISCOUNT', 'Pode apagar descontos!', 5);

insert into master.permission(id, code, name, module_id)
values (21, 'CREATE_DISCOUNT', 'Pode criar descontos!', 5);

insert into master.permission(id, code, name, module_id)
values (22, 'CREATE_FINE_FORGIVENESS', 'Pode criar perdão de multas!', 5);

insert into master.permission(id, code, name, module_id)
values (23, 'DELETE_FINE_FORGIVENESS', 'Pode apagar perdão de multas!', 5);

-- Permissões para o sistema de relatórios
insert into master.permission(id, code, name, module_id)
values (24, 'REPORT_CREATE', 'Criar relatórios', 2);

insert into master.permission(id, code, name, module_id)
values (25, 'REPORT_EDIT', 'Editar relatórios', 2);

insert into master.permission(id, code, name, module_id)
values (26, 'REPORT_DELETE', 'Apagar relatórios', 2);

insert into master.permission(id, code, name, module_id)
values (27, 'REPORT_VIEW', 'Visualizar relatórios', 2);

insert into master.permission(id, code, name, module_id)
values (28, 'REPORT_TYPE_MANAGE', 'Gerir tipos de relatório', 2);

insert into master.permission(id, code, name, module_id)
values (29, 'REPORT_CLASSIFICATION_MANAGE', 'Gerir classificações de relatório', 2);

insert into master.permission(id, code, name, module_id)
values (30, 'INSTITUTION_MANAGE', 'Gerir instituições', 2);

insert into master.permission(id, code, name, module_id)
values (31, 'TEAM_MANAGE', 'Gerir equipas', 2);

insert into master.permission(id, code, name, module_id)
values (32, 'USER_GROUP_MANAGE', 'Gerir grupos de utilizadores', 2);

insert into master.permission(id, code, name, module_id)
values (33, 'PERMISSION_MANAGE', 'Gerir permissões', 2);

select setval('sisclube.permission_id_sequence', 33);

-- user_user_group_permission
delete
from master.user_vs_user_group_vs_permission uugp
where uugp.user_id = 1;

-- Administrador - todas as permissões
INSERT INTO master.user_vs_user_group_vs_permission (id, user_id, permission_id, user_group_id)
VALUES (1, 1, 1, 1);
INSERT INTO master.user_vs_user_group_vs_permission (id, user_id, permission_id, user_group_id)
VALUES (2, 1, 2, 1);
INSERT INTO master.user_vs_user_group_vs_permission (id, user_id, permission_id, user_group_id)
VALUES (3, 1, 3, 1);
INSERT INTO master.user_vs_user_group_vs_permission (id, user_id, permission_id, user_group_id)
VALUES (4, 1, 4, 1);
INSERT INTO master.user_vs_user_group_vs_permission (id, user_id, permission_id, user_group_id)
VALUES (5, 1, 5, 1);
INSERT INTO master.user_vs_user_group_vs_permission (id, user_id, permission_id, user_group_id)
VALUES (6, 1, 6, 1);
INSERT INTO master.user_vs_user_group_vs_permission (id, user_id, permission_id, user_group_id)
VALUES (7, 1, 7, 1);

-- Gestor - permissões de supervisão
INSERT INTO master.user_vs_user_group_vs_permission (id, user_id, permission_id, user_group_id)
VALUES (8, 3, 1, 3);
INSERT INTO master.user_vs_user_group_vs_permission (id, user_id, permission_id, user_group_id)
VALUES (9, 3, 2, 3);
INSERT INTO master.user_vs_user_group_vs_permission (id, user_id, permission_id, user_group_id)
VALUES (10, 3, 3, 3);
INSERT INTO master.user_vs_user_group_vs_permission (id, user_id, permission_id, user_group_id)
VALUES (11, 3, 4, 3);
INSERT INTO master.user_vs_user_group_vs_permission (id, user_id, permission_id, user_group_id)
VALUES (12, 3, 5, 3);
INSERT INTO master.user_vs_user_group_vs_permission (id, user_id, permission_id, user_group_id)
VALUES (13, 3, 6, 3);
INSERT INTO master.user_vs_user_group_vs_permission (id, user_id, permission_id, user_group_id)
VALUES (14, 3, 7, 3);

-- Supervisor - permissões de monitoramento
INSERT INTO master.user_vs_user_group_vs_permission (id, user_id, permission_id, user_group_id)
VALUES (15, 4, 1, 4);
INSERT INTO master.user_vs_user_group_vs_permission (id, user_id, permission_id, user_group_id)
VALUES (16, 4, 2, 4);
INSERT INTO master.user_vs_user_group_vs_permission (id, user_id, permission_id, user_group_id)
VALUES (17, 4, 3, 4);
INSERT INTO master.user_vs_user_group_vs_permission (id, user_id, permission_id, user_group_id)
VALUES (18, 4, 4, 4);
INSERT INTO master.user_vs_user_group_vs_permission (id, user_id, permission_id, user_group_id)
VALUES (19, 4, 5, 4);
INSERT INTO master.user_vs_user_group_vs_permission (id, user_id, permission_id, user_group_id)
VALUES (20, 4, 6, 4);
INSERT INTO master.user_vs_user_group_vs_permission (id, user_id, permission_id, user_group_id)
VALUES (21, 4, 7, 4);

-- Técnico - permissões de resolução
INSERT INTO master.user_vs_user_group_vs_permission (id, user_id, permission_id, user_group_id)
VALUES (22, 5, 1, 2);
INSERT INTO master.user_vs_user_group_vs_permission (id, user_id, permission_id, user_group_id)
VALUES (23, 5, 2, 2);
INSERT INTO master.user_vs_user_group_vs_permission (id, user_id, permission_id, user_group_id)
VALUES (24, 5, 3, 2);
INSERT INTO master.user_vs_user_group_vs_permission (id, user_id, permission_id, user_group_id)
VALUES (25, 5, 4, 2);
INSERT INTO master.user_vs_user_group_vs_permission (id, user_id, permission_id, user_group_id)
VALUES (26, 5, 5, 2);
INSERT INTO master.user_vs_user_group_vs_permission (id, user_id, permission_id, user_group_id)
VALUES (27, 5, 6, 2);
INSERT INTO master.user_vs_user_group_vs_permission (id, user_id, permission_id, user_group_id)
VALUES (28, 5, 7, 2);

-- Utilizador básico - permissões limitadas
INSERT INTO master.user_vs_user_group_vs_permission (id, user_id, permission_id, user_group_id)
VALUES (29, 6, 1, 5);
INSERT INTO master.user_vs_user_group_vs_permission (id, user_id, permission_id, user_group_id)
VALUES (30, 6, 2, 5);
INSERT INTO master.user_vs_user_group_vs_permission (id, user_id, permission_id, user_group_id)
VALUES (31, 6, 3, 5);
INSERT INTO master.user_vs_user_group_vs_permission (id, user_id, permission_id, user_group_id)
VALUES (32, 6, 4, 5);
INSERT INTO master.user_vs_user_group_vs_permission (id, user_id, permission_id, user_group_id)
VALUES (33, 6, 5, 5);
INSERT INTO master.user_vs_user_group_vs_permission (id, user_id, permission_id, user_group_id)
VALUES (34, 6, 6, 5);
INSERT INTO master.user_vs_user_group_vs_permission (id, user_id, permission_id, user_group_id)
VALUES (35, 6, 7, 5);

-- Outros utilizadores com permissões básicas
INSERT INTO master.user_vs_user_group_vs_permission (id, user_id, permission_id, user_group_id)
VALUES (36, 8, 1, 5);
INSERT INTO master.user_vs_user_group_vs_permission (id, user_id, permission_id, user_group_id)
VALUES (37, 8, 2, 5);
INSERT INTO master.user_vs_user_group_vs_permission (id, user_id, permission_id, user_group_id)
VALUES (38, 8, 3, 5);
INSERT INTO master.user_vs_user_group_vs_permission (id, user_id, permission_id, user_group_id)
VALUES (39, 8, 4, 5);
INSERT INTO master.user_vs_user_group_vs_permission (id, user_id, permission_id, user_group_id)
VALUES (40, 8, 5, 5);
INSERT INTO master.user_vs_user_group_vs_permission (id, user_id, permission_id, user_group_id)
VALUES (41, 8, 6, 5);
INSERT INTO master.user_vs_user_group_vs_permission (id, user_id, permission_id, user_group_id)
VALUES (42, 8, 7, 5);

INSERT INTO master.user_vs_user_group_vs_permission (id, user_id, permission_id, user_group_id)
VALUES (43, 9, 1, 5);
INSERT INTO master.user_vs_user_group_vs_permission (id, user_id, permission_id, user_group_id)
VALUES (44, 9, 2, 5);
INSERT INTO master.user_vs_user_group_vs_permission (id, user_id, permission_id, user_group_id)
VALUES (45, 9, 3, 5);
INSERT INTO master.user_vs_user_group_vs_permission (id, user_id, permission_id, user_group_id)
VALUES (46, 9, 4, 5);
INSERT INTO master.user_vs_user_group_vs_permission (id, user_id, permission_id, user_group_id)
VALUES (47, 9, 5, 5);
INSERT INTO master.user_vs_user_group_vs_permission (id, user_id, permission_id, user_group_id)
VALUES (48, 9, 6, 5);
INSERT INTO master.user_vs_user_group_vs_permission (id, user_id, permission_id, user_group_id)
VALUES (49, 9, 7, 5);

-- Permissão adicional para administrador
INSERT INTO master.user_vs_user_group_vs_permission (id, user_id, permission_id, user_group_id)
VALUES (50, 1, 8, 1);

select setval('sisclube.user_vs_user_group_vs_permission_id_sequence', 50);

-- functions
-- date_in_range
CREATE
OR REPLACE FUNCTION date_in_range(
    valueDate DATE,
    startDate DATE,
    endDate DATE
) RETURNS BOOLEAN AS
$$
DECLARE
startDateAdjusted DATE;
    endDateAdjusted
DATE;
BEGIN
    -- Se todas as datas forem NULL
    IF
valueDate IS NULL AND startDate IS NULL AND endDate IS NULL THEN
        RETURN FALSE;
END IF;

    -- Se a valueDate for NULL
    IF
valueDate IS NULL THEN
        RETURN FALSE;
END IF;

    -- Se startDate e endDate são NULL
    IF
startDate IS NULL AND endDate IS NULL THEN
        RETURN TRUE;
END IF;

    -- Ajuste de startDate
    IF
startDate IS NOT NULL THEN
        IF EXTRACT(DAY FROM startDate) >= 25 THEN
            startDateAdjusted := (DATE_TRUNC('MONTH', startDate) + INTERVAL '1 month')::DATE;
ELSE
            startDateAdjusted := DATE_TRUNC('MONTH', startDate)::DATE;
END IF;
ELSE
        startDateAdjusted := NULL;
END IF;

    -- Ajuste de endDate
    IF
endDate IS NOT NULL THEN
        IF EXTRACT(DAY FROM endDate) >= 25 THEN
            endDateAdjusted := (DATE_TRUNC('MONTH', endDate) + INTERVAL '1 month')::DATE;
ELSE
            endDateAdjusted := DATE_TRUNC('MONTH', endDate)::DATE;
END IF;
ELSE
        endDateAdjusted := NULL;
END IF;

    -- Comparações conforme a lógica
    IF
startDateAdjusted IS NULL AND valueDate < endDateAdjusted THEN
        RETURN TRUE;
    ELSIF
endDateAdjusted IS NULL AND valueDate >= startDateAdjusted THEN
        RETURN TRUE;
    ELSIF
valueDate >= startDateAdjusted AND valueDate < endDateAdjusted THEN
        RETURN TRUE;
ELSE
        RETURN FALSE;
END IF;
END;
$$
LANGUAGE plpgsql;

CREATE
OR REPLACE FUNCTION date_in_range(
    valueDate TIMESTAMP,
    startDate TIMESTAMP,
    endDate TIMESTAMP
) RETURNS BOOLEAN
    LANGUAGE plpgsql
AS
$$
DECLARE
startDateAdjusted TIMESTAMP;
    endDateAdjusted
TIMESTAMP;
BEGIN
    -- Se todas as datas forem NULL
    IF
valueDate IS NULL AND startDate IS NULL AND endDate IS NULL THEN
        RETURN FALSE;
END IF;

    -- Se valueDate for NULL
    IF
valueDate IS NULL THEN
        RETURN FALSE;
END IF;

    -- Se startDate e endDate são NULL
    IF
startDate IS NULL AND endDate IS NULL THEN
        RETURN TRUE;
END IF;

    -- Ajuste de startDate
    IF
startDate IS NOT NULL THEN
        IF EXTRACT(DAY FROM startDate) >= 25 THEN
            startDateAdjusted := DATE_TRUNC('MONTH', startDate) + INTERVAL '1 month';
ELSE
            startDateAdjusted := DATE_TRUNC('MONTH', startDate);
END IF;
ELSE
        startDateAdjusted := NULL;
END IF;

    -- Ajuste de endDate
    IF
endDate IS NOT NULL THEN
        IF EXTRACT(DAY FROM endDate) >= 25 THEN
            endDateAdjusted := DATE_TRUNC('MONTH', endDate) + INTERVAL '1 month';
ELSE
            endDateAdjusted := DATE_TRUNC('MONTH', endDate);
END IF;
ELSE
        endDateAdjusted := NULL;
END IF;

    -- Comparações com hora considerada
    IF
startDateAdjusted IS NULL AND valueDate < endDateAdjusted THEN
        RETURN TRUE;
    ELSIF
endDateAdjusted IS NULL AND valueDate >= startDateAdjusted THEN
        RETURN TRUE;
    ELSIF
valueDate >= startDateAdjusted AND valueDate < endDateAdjusted THEN
        RETURN TRUE;
ELSE
        RETURN FALSE;
END IF;
END;
$$;

ALTER FUNCTION date_in_range(timestamp, timestamp, timestamp) OWNER TO postgres;

-- update discount and forgiveness, boatOwnerHistory, BoatSpaceHistory, payment order, paymentMethod time to only date

--
