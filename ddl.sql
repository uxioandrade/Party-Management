CREATE DATABASE partymanagement WITH OWNER = "postgres" ENCODING 'UTF8';
 
GRANT ALL PRIVILEGES ON DATABASE partymanagement to "postgres";
 
\connect partymanagement  postgres


CREATE TYPE coordenadas AS
(   latitude    FLOAT,
    lonxitude   FLOAT
);

CREATE DOMAIN partyCoin NUMERIC (7,2) NOT NULL
	CONSTRAINT partyCoin_test_positivo CHECK (value >= 0);

CREATE DOMAIN xenero CHAR(1) NOT NULL
	CONSTRAINT xenero_test_valor CHECK (value in ('M','F','O'));

CREATE DOMAIN puntuacion NUMERIC (1,0) NOT NULL
	CONSTRAINT puntuacion_test_rango CHECK (value in (1, 2, 3, 4, 5));

CREATE DOMAIN plataforma VARCHAR(20) NOT NULL
	CONSTRAINT plataforma_redes_acepatdas CHECK (value in ('twitter','instagram','linkedin','github','facebook','tinder'));

CREATE TABLE usuarios(
	nickname VARCHAR(30) NOT NULL,
	contrasinal CHAR(60) NOT NULL,
	nome VARCHAR(30) NOT NULL,
	email VARCHAR(60) UNIQUE NOT NULL,
	biografia VARCHAR(90),
	saldo NUMERIC(7,2) DEFAULT 0 NOT NULL,
	urlFotoPerfil VARCHAR(90) NOT NULL,
	PRIMARY KEY(nickname)
);

CREATE INDEX nickname_indice ON usuarios(nickname);

CREATE TABLE particulares(
	nickname VARCHAR(30) NOT NULL,
	fechaNacimiento DATE NOT NULL CHECK (fechaNacimiento<CURRENT_DATE),
	estadoSentimental VARCHAR(90),
	sexo XENERO NOT NULL,
	FOREIGN KEY (nickname) REFERENCES usuarios(nickname)
		ON DELETE CASCADE ON UPDATE CASCADE,
	PRIMARY KEY (nickname)
);

CREATE TABLE empresas(
	nickname VARCHAR(30) NOT NULL,
	cif CHAR(8) NOT NULL,
	direccion VARCHAR(90) NOT NULL,
	web VARCHAR(90),
	FOREIGN KEY (nickname) REFERENCES usuarios(nickname)
		ON DELETE CASCADE ON UPDATE CASCADE,
	PRIMARY KEY (nickname)
);

CREATE TABLE accesos(
	usuario VARCHAR(30) NOT NULL,
	fechaInicio TIMESTAMP DEFAULT now() NOT NULL,
	dispositivo VARCHAR(60) NOT NULL,
	fechaFin TIMESTAMP CHECK (fechaFin>=fechaInicio),
	PRIMARY KEY (usuario, fechaInicio),
	FOREIGN KEY (usuario) REFERENCES usuarios(nickname)
		ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE ubicacions(
	creador VARCHAR(30),
	coordenadas COORDENADAS NOT NULL,
	indicacions VARCHAR(90) NOT NULL,
	nome VARCHAR(60) NOT NULL, 
	ePrivada BOOLEAN NOT NULL,
	PRIMARY KEY( coordenadas, indicacions),
	FOREIGN KEY (creador) REFERENCES usuarios(nickname)
		ON DELETE CASCADE ON UPDATE CASCADE
	
);

create or replace function privacidadBorrado() returns trigger as $$
begin
	UPDATE ubicacions
	set creador=null;
	return null;	
end;
$$ Language plpgsql;

create trigger BeforeDeleteUbicacions 
	before delete on ubicacions
	for each row 
	when (old.ePrivada=false)
execute procedure privacidadBorrado();

create or replace function edicionUbicacionsPublicas() returns trigger as $$
begin
	RAISE EXCEPTION 'Non se poden editar as ubicacions publicas';
	return null;	
end;
$$ Language plpgsql;

create trigger UpdateUbicacionsPublicas
	before UPDATE on ubicacions
	for each row 
	when (old.ePrivada=false)
execute procedure edicionUbicacionsPublicas();

CREATE TABLE redes(
	plataforma plataforma,
	id_plataforma VARCHAR(30),
	url VARCHAR(60) NOT NULL,
	usuario VARCHAR(30) NOT NULL,
	PRIMARY KEY (usuario, url),
	FOREIGN KEY (usuario) REFERENCES usuarios(nickname)
		ON DELETE CASCADE ON UPDATE CASCADE
);



Create sequence seq_festas_id;

CREATE TABLE festas(
	id INTEGER default nextval('seq_festas_id') NOT NULL,
	organizador VARCHAR(30),
	nome VARCHAR(30) NOT NULL,
	fecha TIMESTAMP NOT NULL check(FECHA>=NOW()),
	descripcion VARCHAR(90),
	coordenadas COORDENADAS NOT NULL,
	indicacionsLocalizacion VARCHAR (100) NOT NULL,
	FOREIGN KEY (organizador) REFERENCES usuarios(nickname)
	ON DELETE SET NULL ON UPDATE CASCADE,
	FOREIGN KEY (coordenadas, indicacionsLocalizacion) 
		REFERENCES ubicacions(coordenadas,indicacions)
	ON DELETE RESTRICT ON UPDATE CASCADE, 
	PRIMARY KEY (id)
);

create or replace function comprobarFechaCelebracionFiestaPendiente() returns trigger as $csl$
BEGIN
	IF EXISTS ( SELECT organizador FROM festas WHERE festas.organizador=old.nickname AND festas.fecha>now()) THEN
		RAISE EXCEPTION 'Non se pode eliminar o usuario xa que e organizador de festas que ainda non tiveron lugar';
		return null;
	END IF;
	return old;		
END;
$csl$ Language plpgsql;

create trigger beforeDeleteUsuarios before delete on usuarios
for each row execute procedure comprobarFechaCelebracionFiestaPendiente();
	
create or replace function controla_secuencias_festas() returns trigger as $csl$
declare
	entrada INTEGER;
BEGIN
  case TG_OP
    when 'INSERT' then
      execute 'create sequence seq_entradas_festa'||cast(NEW.id as text);
      return new;
    when 'UPDATE' then
      IF OLD.id <> NEW.id THEN
	     entrada:=nextval('seq_entradas_festa'||cast(OLD.id as text));
	     execute 'drop sequence seq_entradas_festa'||cast(OLD.id as text);
	     execute 'create sequence seq_entradas_festa'||cast(NEW.id as text)|| ' start '||cast(entrada as text);
      END IF;
      return new;
    when 'DELETE' then 
      execute 'drop sequence seq_entradas_festa'||cast(OLD.id as text);
      return old;
    else null;
  end case;
end;
$csl$ Language plpgsql;


create trigger afterFestas after insert or delete on festas
for each row execute procedure controla_secuencias_festas();


CREATE TABLE  publicas(
	festa INTEGER default currval('seq_festas_id') NOT NULL,
	aforo INTEGER check (aforo>1) NOT NULL,
	precio PARTYCOIN NOT NULL DEFAULT 0.,
	cobrada BOOLEAN DEFAULT false,
	PRIMARY KEY (festa),
	FOREIGN KEY (festa)
		REFERENCES  festas (id)
	ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE privadas (
	festa INTEGER default currval('seq_festas_id')NOT NULL,
	solicitable BOOLEAN NOT NULL DEFAULT false,
	dataLimite DATE DEFAULT NULL check(dataLimite>= now()),
	PRIMARY KEY (festa),
	FOREIGN KEY (festa) REFERENCES  festas (id)
		ON DELETE CASCADE ON UPDATE CASCADE
);

create or replace function privacidadFiestasEmpresas() returns trigger as $$
begin
	IF EXISTS ( SELECT festas.id
		    FROM festas,empresas
		    WHERE NEW.festa=festas.id AND empresas.nickname=festas.organizador) THEN
	RAISE EXCEPTION 'Unha empresa non pode organizar festas privadas';
	return null;
	END IF;
	return NEW;	
end;
$$ Language plpgsql;

create trigger InsertPrivadas 
	before insert on privadas
	for each row 
execute procedure privacidadFiestasEmpresas();


CREATE TABLE entradas (
	festa INTEGER default currval('seq_festas_id') NOT NULL,
	codigoEntrada INTEGER NOT NULL,
	usuario VARCHAR(30) NOT NULL,
	fechaAdquisicion TIMESTAMP default now() NOT NULL,
	cantidade PARTYCOIN NOT NULL DEFAULT 0.,
	PRIMARY KEY(festa, codigoEntrada),
	FOREIGN KEY (festa) REFERENCES publicas (festa)
		ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (usuario) REFERENCES particulares (nickname)
		ON DELETE CASCADE ON UPDATE CASCADE
);

create or replace function introduce_codigoEntrada() returns trigger as $csl$
begin
	IF (SELECT count(*)
	FROM entradas as en
	WHERE en.festa =NEW.festa) >= (SELECT pu.aforo FROM publicas as pu WHERE pu.festa = NEW.festa LIMIT 1)  THEN
	RAISE EXCEPTION 'Xa se completou o aforo da festa';
	return null;
	END IF;

							 
  	new.codigoEntrada:=CAST(CAST(new.festa as text)||CAST(nextval('seq_entradas_festa'||cast(new.festa as text)) AS text) AS int);
  	return new;
end;
$csl$ Language plpgsql;


create trigger beforeInsertEntradas 
	before insert on entradas
	for each row
	when (new.codigoEntrada is NULL)
 execute procedure introduce_codigoEntrada();

create or replace function comprobarComprador() returns trigger as $$
begin
	IF NEW.usuario=(SELECT organizador FROM festas where organizador=NEW.usuario AND NEW.festa=festas.id LIMIT 1) THEN
	RAISE EXCEPTION 'Un usuario non pode comprar entradas para unha festa que organiza';
	return null;
	END IF;
	return NEW;	
end;
$$ Language plpgsql;

create trigger insertEntradas
	before insert on entradas
	for each row 
execute procedure comprobarComprador();

CREATE TABLE galerias (
	festa INTEGER default currval('seq_festas_id') NOT NULL,
	nome VARCHAR(30) NOT NULL,
	PRIMARY KEY (festa, nome),
	FOREIGN KEY (festa) REFERENCES festas (id)
		ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE multimedia (
	URL VARCHAR(90) NOT NULL,
	festa INTEGER default currval('seq_festas_id') NOT NULL,
	galeria VARCHAR(30) NOT NULL,
	pe VARCHAR(90),
	PRIMARY KEY (URL,festa,galeria),
	FOREIGN KEY (festa, galeria) REFERENCES galerias (festa, nome)
		ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE fotos(
	URL VARCHAR(90) NOT NULL,
	festa INTEGER default currval('seq_festas_id') NOT NULL,
	galeria VARCHAR(30) ,
	X INTEGER,
	Y INTEGER,
	PRIMARY KEY (URL,festa,galeria),
	FOREIGN KEY (URL,festa,galeria) REFERENCES multimedia (URL,festa,galeria)
		ON DELETE CASCADE ON UPDATE CASCADE
	
);

CREATE TABLE audios(
	URL VARCHAR(90) NOT NULL,
	festa INTEGER default currval('seq_festas_id') NOT NULL,
	galeria VARCHAR(30) ,
	duracion TIME,
	PRIMARY KEY (URL,festa,galeria),
	FOREIGN KEY (URL,festa,galeria) REFERENCES multimedia (URL,festa,galeria)
		ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE etiquetas(
	nome VARCHAR(30) NOT NULL,
	descripcion VARCHAR(90),
	PRIMARY KEY (nome)
);

CREATE TABLE teretiquetas(
	festa INTEGER default currval('seq_festas_id') NOT NULL,
	etiqueta VARCHAR(30) NOT NULL,
	PRIMARY KEY (festa, etiqueta),
	FOREIGN KEY (festa) REFERENCES festas (id)
		ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (etiqueta) REFERENCES etiquetas (nome)
		ON DELETE CASCADE ON UPDATE CASCADE
);



CREATE TABLE valorar(
	usuario VARCHAR(30) NOT NULL,
	festa INTEGER default currval('seq_festas_id') NOT NULL,
	puntuacion PUNTUACION NOT NULL,
	fecha TIMESTAMP NOT NULL DEFAULT now() ,
	comentario VARCHAR(100),
	FOREIGN KEY (usuario) REFERENCES usuarios(nickname)
		ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (festa) REFERENCES festas(id)
		ON DELETE CASCADE ON UPDATE CASCADE,
	PRIMARY KEY (usuario,festa)
);

create or replace function comprobarFechaFesta() returns trigger as $$
begin
	IF EXISTS ( SELECT festas.id
		    FROM festas
		    WHERE NEW.festa=festas.id AND festas.fecha>now()) THEN
	RAISE EXCEPTION 'A festa ainda non tivo lugar: non pode ser valorada';
	return null;
	END IF;
	return NEW;	
end;
$$ Language plpgsql;

create trigger insertValorar 
	before insert on valorar
	for each row 
execute procedure comprobarFechaFesta();

CREATE TABLE bloquear(
	bloqueador VARCHAR(30) NOT NULL,
	bloqueado VARCHAR(30) NOT NULL,
	FOREIGN KEY (bloqueador) REFERENCES particulares(nickname)
	ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (bloqueado) REFERENCES particulares(nickname)
	ON DELETE CASCADE ON UPDATE CASCADE,
	PRIMARY KEY(bloqueador,bloqueado)
);

create or replace function comprobarBloqueo() returns trigger as $$
begin
	IF NEW.bloqueador=NEW.bloqueado THEN
	RAISE EXCEPTION 'Un usuario non se pode bloquear a si mesmo';
	return null;
	END IF;
	return NEW;	
end;
$$ Language plpgsql;

create trigger insertBloquear 
	before insert on bloquear
	for each row 
execute procedure comprobarBloqueo();

CREATE TABLE amigar(
	usuario VARCHAR(30) NOT NULL,
	amigo VARCHAR(30) NOT NULL,
	FOREIGN KEY (usuario) REFERENCES particulares(nickname)
		ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (amigo) REFERENCES particulares(nickname)
		ON DELETE CASCADE ON UPDATE CASCADE,	
	PRIMARY KEY(usuario,amigo)
);

create or replace function comprobarAmigo() returns trigger as $$
begin
	IF NEW.amigo=NEW.usuario THEN
	RAISE EXCEPTION 'Un usuario non pode facerse amigo de si mesmo';
	return null;
	END IF;
	return NEW;	
end;
$$ Language plpgsql;

create trigger insertAmigar 
	before insert on amigar
	for each row 
execute procedure comprobarAmigo();

CREATE TABLE silenciar(
	usuario VARCHAR(30) NOT NULL,
	festa INTEGER default currval('seq_festas_id') NOT NULL,

	FOREIGN KEY (usuario) REFERENCES usuarios (nickname)
		ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (festa) REFERENCES festas(id)
		ON DELETE CASCADE ON UPDATE CASCADE,
	PRIMARY KEY (usuario,festa)
);

CREATE TABLE invitar(
	usuario varchar(30) NOT NULL,
	festa integer default currval('seq_festas_id') NOT NULL,
	fecha TIMESTAMP default now() NOT NULL,
	aceptada boolean NOT NULL,
	comentario varchar(90),
	FOREIGN KEY (usuario) REFERENCES particulares (nickname)
		ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (festa) REFERENCES privadas (festa)
		ON DELETE CASCADE ON UPDATE CASCADE,
	PRIMARY KEY (usuario,festa)
);

create or replace function comprobarInvitacion() returns trigger as $$
begin
	IF NEW.usuario=(SELECT organizador FROM festas where organizador=NEW.usuario AND NEW.festa=festas.id LIMIT 1) THEN
	RAISE EXCEPTION 'Un usuario non pode solicitar asistir a unha festa que organiza';
	return null;
	END IF;
	return NEW;	
end;
$$ Language plpgsql;

create trigger insertInvitar
	before insert on invitar
	for each row 
execute procedure comprobarInvitacion();

CREATE TABLE solicitar(
	usuario varchar(30) NOT NULL,
	festa integer default currval('seq_festas_id') NOT NULL,
	fecha TIMESTAMP default now() NOT NULL,
	aceptada boolean NOT NULL,
	comentario varchar(90),
	PRIMARY KEY (usuario,festa),
	FOREIGN KEY (usuario) REFERENCES particulares (nickname)
		ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (festa) REFERENCES privadas (festa)
		ON DELETE CASCADE ON UPDATE CASCADE
);

create or replace function filtrarSolicitud() returns trigger as $$
begin
	IF NOT EXISTS ( SELECT pr.festa
		    FROM privadas as pr
		    WHERE pr.festa=NEW.festa AND pr.solicitable = true) THEN
	RAISE EXCEPTION 'A festa non e solicitable';
	return null;
	END IF;
							
	IF EXISTS ( SELECT festas.id
		    FROM festas
		    WHERE festas.id=NEW.festa AND NEW.usuario=festas.organizador) THEN
	RAISE EXCEPTION 'O organizador da festa non pode solicitar unha invitacion a sua propia festa';
	return null;
	END IF;
							
	IF NEW.fecha>(SELECT dataLimite FROM privadas WHERE privadas.festa=NEW.festa) THEN
	RAISE EXCEPTION 'A data limite de inscripcion para esta festa xa pasou';
	return null;
	END IF;
	return NEW;	
end;
$$ Language plpgsql;

create trigger insertSolicitar
	before insert on solicitar
	for each row 
execute procedure filtrarSolicitud();

CREATE TABLE postear(
	usuario VARCHAR(30) NOT NULL,
	festa INTEGER default currval('seq_festas_id') NOT NULL,
	data TIMESTAMP default now() not null,
	mensaxe VARCHAR(90) NOT NULL,
	eVisible boolean NOT NULL DEFAULT true,
	PRIMARY KEY (usuario,festa,data),
	FOREIGN KEY (usuario) REFERENCES usuarios (nickname)
		ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (festa) REFERENCES festas (id)
		ON DELETE CASCADE ON UPDATE CASCADE
);

create or replace function comprobarSilencioFesta() returns trigger as $$
begin
	IF EXISTS ( SELECT silenciar.festa
		    FROM silenciar
		    WHERE NEW.festa=silenciar.festa AND NEW.usuario=silenciar.usuario) THEN
	RAISE EXCEPTION 'O usuario esta silenciado nesta festa: non pode postear no seu muro';
	return null;
	END IF;
	return NEW;	
end;
$$ Language plpgsql;

create trigger insertPostear 
	before insert on postear
	for each row 
execute procedure comprobarSilencioFesta();

CREATE TABLE mensaxear(
	emisor VARCHAR(30) NOT NULL,
	receptor VARCHAR(30) NOT NULL,
	fecha TIMESTAMP NOT NULL DEFAULT now(),
	contido VARCHAR(100) NOT NULL DEFAULT 'Bro',
	eBorrado BOOLEAN NOT NULL DEFAULT false,
	FOREIGN KEY (emisor) REFERENCES particulares(nickname)
		ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (emisor) REFERENCES particulares(nickname)
		ON DELETE CASCADE ON UPDATE CASCADE,	
	PRIMARY KEY (emisor,receptor,fecha)
);

create or replace function mensaxesUsuariosBloqueados() returns trigger as $$
begin
	/*Caso 1:O receptor ten bloqueado ao emisor*/
	IF EXISTS ( SELECT bloquear.bloqueador
		    FROM bloquear
		    WHERE NEW.receptor=bloquear.bloqueador AND NEW.emisor=bloquear.bloqueado) THEN
	RAISE EXCEPTION 'O usuario ao que intentas enviar a mensaxe tente bloqueado';
	return null;
	END IF;
	/*Caso 2: O emisor ten bloqueado ao receptor*/
	IF EXISTS ( SELECT bloquear.bloqueador
		    FROM bloquear
		    WHERE NEW.emisor=bloquear.bloqueador AND NEW.receptor=bloquear.bloqueado) THEN
	RAISE EXCEPTION 'Tes bloqueado ao usuario ao que intentas enviar a mensaxe';
	return null;
	END IF;

	return NEW;	
end;
$$ Language plpgsql;

create trigger insertMensaxear
	before insert on mensaxear
	for each row 
execute procedure mensaxesUsuariosBloqueados();

CREATE TABLE movementos(
	usuario VARCHAR(30) NOT NULL,
	data TIMESTAMP DEFAULT now() NOT NULL,
	tipo VARCHAR(10) NOT NULL, /*DELIMITAR VALORES*/
	cantidade partyCoin NOT NULL,
	PRIMARY KEY (usuario, data),
	FOREIGN KEY (usuario) REFERENCES usuarios(nickname)
		ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE FUNCTION calcular_distancia (COORDENADAS, COORDENADAS) RETURNS FLOAT
	AS 'select sqrt(abs($1.latitude - $2.latitude)^2 + abs($1.lonxitude - $2.lonxitude))'
	LANGUAGE SQL
	IMMUTABLE
	RETURNS NULL ON NULL INPUT;


CREATE OR REPLACE FUNCTION festas_cercanas (ubicacionOrixe COORDENADAS, distancia FLOAT)
RETURNS TABLE (
	id INTEGER,
	organizador VARCHAR(30),
	nome VARCHAR(30),
	fecha TIMESTAMP,
	descripcion VARCHAR(90),
	latitude FLOAT,
	lonxitude FLOAT,
	indicacionsLocalizacion VARCHAR(90))
	AS $$
BEGIN	
RETURN QUERY
	(SELECT fe.id, fe.organizador, fe.nome, fe.fecha, fe.descripcion,
		(fe.coordenadas).latitude as latitude, (fe.coordenadas).lonxitude as lonxitude,
		fe.indicacionsLocalizacion
	from festas as fe
	where calcular_distancia (ubicacionOrixe, coordenadas) < distancia
	order by calcular_distancia (ubicacionOrixe, coordenadas) asc);
END; $$
LANGUAGE 'plpgsql'
