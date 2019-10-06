INSERT INTO usuarios(nickname,contrasinal,nome,email,biografia,saldo,urlfotoperfil) values ('viqueira','$2a$10$MXGPIf/aNgr3tPD6GxDFqO2IUu28lpN4cviOUJO4XS1UWC7UBcyEi','Xosé Ramón','viqueira@yopmail.com','Mellor Investigador',10.00,'/resources/viqueira.jpg');
/*Contrasinal para todos os particulares: mongodb*/
INSERT INTO particulares(nickname,fechanacimiento,estadosentimental,sexo) values ('viqueira','1995-02-01','Con pareja','M');

INSERT INTO usuarios(nickname,contrasinal,nome,email,biografia,saldo,urlfotoperfil) values ('arkanto','$2a$10$MXGPIf/aNgr3tPD6GxDFqO2IUu28lpN4cviOUJO4XS1UWC7UBcyEi','Euclides Pérez','arkanto@yopmail.com','Deus grego',2.00,'/resources/arkanto.jpg');
INSERT INTO particulares(nickname,fechanacimiento,estadosentimental,sexo) values ('arkanto','1999-08-01','Con pareja','M');

INSERT INTO usuarios(nickname,contrasinal,nome,email,biografia,saldo,urlfotoperfil) values ('uxio21andrade','$2a$10$MXGPIf/aNgr3tPD6GxDFqO2IUu28lpN4cviOUJO4XS1UWC7UBcyEi','Uxío García','uxio@yopmail.com','Pythonista',5000.00,'/resources/uxio21andrade.jpg');
INSERT INTO particulares(nickname,fechanacimiento,estadosentimental,sexo) values ('uxio21andrade','1925-02-10','Soltero','O');

INSERT INTO usuarios(nickname,contrasinal,nome,email,biografia,saldo,urlfotoperfil) values ('pgadmin','$2a$10$MXGPIf/aNgr3tPD6GxDFqO2IUu28lpN4cviOUJO4XS1UWC7UBcyEi','Xosé Rodríguez','xosinho@yopmail.com','Mellor administrador historia',5.00,'/resources/pgadmin.jpg');
INSERT INTO particulares(nickname,fechanacimiento,estadosentimental,sexo) values ('pgadmin','1998-02-01','Con pareja','M');

INSERT INTO usuarios(nickname,contrasinal,nome,email,biografia,saldo,urlfotoperfil) values ('14_hectoor','$2a$10$MXGPIf/aNgr3tPD6GxDFqO2IUu28lpN4cviOUJO4XS1UWC7UBcyEi','Héctor Varela','hector@yopmail.com','Embaixador de Bastille',3.00,'/resources/14_hectoor.jpg');
INSERT INTO particulares(nickname,fechanacimiento,estadosentimental,sexo) values ('14_hectoor','1999-02-01','Con pareja','M');

INSERT INTO usuarios(nickname,contrasinal,nome,email,biografia,saldo,urlfotoperfil) values ('pablomlago','$2a$10$MXGPIf/aNgr3tPD6GxDFqO2IUu28lpN4cviOUJO4XS1UWC7UBcyEi','Pablo Monteagudo','pablom@yopmail.com','Metinme aquí para aprender a poñer ben os corchetes',0.00,'/resources/pablomlago.jpg');
INSERT INTO particulares(nickname,fechanacimiento,estadosentimental,sexo) values ('pablomlago','1999-02-10','Con pareja','M');


/*Contrasinal para as empresas: ocitius*/
INSERT INTO usuarios(nickname,contrasinal,nome,email,biografia,saldo,urlfotoperfil) values ('citius','$2a$10$k6QNK3b/zIJ/fRncL84z/.AtVN41aNXhonK7KoOYJcI49PD5fVeuK','Centro Tecnoloxico','citius@yopmail.com','Mellor centro',8.00,'/resources/p.jpeg');
INSERT INTO empresas(nickname,cif,direccion,web) values ('citius','45843948','Calle Universidad','citius.com');

INSERT INTO usuarios(nickname,contrasinal,nome,email,biografia,saldo,urlfotoperfil) values ('ruta','$2a$10$k6QNK3b/zIJ/fRncL84z/.AtVN41aNXhonK7KoOYJcI49PD5fVeuK','Ruta Discoteca','ruta@yopmail.com','Mellor discoteca',5.00,'/resources/rave.jpeg');
INSERT INTO empresas(nickname,cif,direccion,web) values ('ruta','33243999','Calle de la Noche','ruta.com');

INSERT INTO usuarios(nickname,contrasinal,nome,email,biografia,saldo,urlfotoperfil) values ('partypiso','$2a$10$k6QNK3b/zIJ/fRncL84z/.AtVN41aNXhonK7KoOYJcI49PD5fVeuK','Partypiso','partypiso@yopmail.com','Mellor sala de festas',6.00,'/resources/p.jpeg');
INSERT INTO empresas(nickname,cif,direccion,web) values ('partypiso','15236555','Av Rosalía de Castro','partypiso.com');

INSERT INTO accesos(usuario,fechainicio,dispositivo,fechafin) VALUES ('uxio21andrade','2019-05-02 17:32:32.692714','alumnogreibd-VirtualBox','2019-05-02 17:42:14.180821');
INSERT INTO accesos(usuario,fechainicio,dispositivo,fechafin) VALUES ('uxio21andrade','2019-04-02 18:32:32.692714','iPhone','2019-05-02 17:42:14.180821');
INSERT INTO accesos(usuario,fechainicio,dispositivo,fechafin) VALUES ('arkanto','2019-03-01 19:32:32.692714','alumnogreibd-VirtualBox','2019-05-02 17:42:14.180821');
INSERT INTO accesos(usuario,fechainicio,dispositivo,fechafin) VALUES ('pablomlago','2019-01-02 12:32:32.692714','alumnogreibd-VirtualBox','2019-05-02 17:42:14.180821');
INSERT INTO accesos(usuario,fechainicio,dispositivo,fechafin) VALUES ('14_hectoor','2019-02-02 13:32:32.692714','alumnogreibd-VirtualBox','2019-05-02 17:42:14.180821');
INSERT INTO accesos(usuario,fechainicio,dispositivo,fechafin) VALUES ('pablomlago','2019-03-02 14:32:32.692714','alumnogreibd-VirtualBox','2019-05-02 17:42:14.180821');



insert into etiquetas
  values('matematicas', 'festas para estudantes de matematicas');

insert into etiquetas
  values('informatica', 'festas para estudantes de informatica');

insert into etiquetas
  values('fisica', 'festas para estudantes de fisica');

insert into etiquetas
    values('bases de datos', 'festas para profesionales de bases de datos');

insert into etiquetas
    values('sen lume', 'festas sen tabaco');

insert into etiquetas
    values('solteiros', 'festas para solteiros');

insert into etiquetas
    values('big data', 'festas para profesionales de big data');

insert into etiquetas
    values('sql', 'festas para xente  que quere pasalo ben');

insert into etiquetas
    values('programacion', 'festas para programar');

insert into etiquetas
    values('tetris', 'festas para amantes do tetris');

insert into etiquetas
    values('quimica', 'festas para estudantes de quimica');

insert into etiquetas
    values('usc', 'festas para estudantes da usc');
    /*meter aqui a creacion de usuarios*/

insert into ubicacions
  values('pablomlago',(13.,1.),'Planta baixa','mi casa',true);
insert into ubicacions
  values('pablomlago',(10.3,2.),'Segunda planta','citius',false);
insert into ubicacions
  values('pablomlago',(12.2,3.),'en la estatua','alameda',true);
insert into ubicacions
  values('uxio21andrade',(1.1,4.),'aula de traballo','ETSE',false);
insert into ubicacions
  values('arkanto',(45.5,5.),'no hall','biblioteca',true);
insert into ubicacions
  values('arkanto',(11.2,11.),'Segunda planta','Dr teixeiro 4',false);
insert into ubicacions
  values('uxio21andrade',(12.1,6.),'terceira planta','alameda',true);
insert into ubicacions
  values('arkanto',(51.1,7.),'no garaxe','araguaney',false);
insert into ubicacions
  values('14_hectoor',(32.1,8.),'na biblioteca','mates',true);
insert into ubicacions
  values('14_hectoor',(22.4,9.),'arriba','Horreo 5',false);
insert into ubicacions
  values('uxio21andrade',(17.5,10.),'na esquina','obradoiro',true);
insert into ubicacions
  values('partypiso',(-3.6,11.),'4 A','pza Galicia 5',false);
insert into ubicacions
  values('uxio21andrade',(-13.6,12.),'2 I','casa de mi abuela',true);
insert into ubicacions
  values('partypiso',(-21.1,13.),'3 A','vite',false);
insert into ubicacions
  values('ruta',(-1.01,14.),'baixo','mansion',true);
insert into ubicacions
  values('partypiso',(-2.1,15.),'no banco','preguntoiro',false);
insert into ubicacions
  values('arkanto',(-35.1,16.),'quinto piso','Rosalia 15',true);
insert into ubicacions
  values('pgadmin',(0.1,17.),'Planta de arriba','Uxio´s keli',false);
insert into ubicacions
  values('pgadmin',(-90.1,18.),'na esquina','citius',true);
insert into ubicacions
  values('ruta',(-78.1,19.),'no hall','ETSE',false);
insert into ubicacions
  values('pablomlago',(-12.09,20.),'no trastero','mi casa',true);
insert into ubicacions
  values('14_hectoor',(-1.,11.),'Planta baixa','as Cancelas',false);
insert into ubicacions
  values('pgadmin',(-1.,11.),'Primeira planta','as Cancelas',false);

insert into festas(organizador, nome, fecha,descripcion,coordenadas,indicacionslocalizacion)
  values('pablomlago','feston',now(),'festa do ano',(-1.,11.),'Primeira planta');
insert into privadas(solicitable, datalimite)
  values(true,now()+ '30 days'::interval);
insert into invitar (usuario, fecha,aceptada,comentario)
  values ('arkanto',current_date-1,true,'Leva cacahuetes');
insert into solicitar (usuario, fecha,aceptada,comentario)
  values ('uxio21andrade',current_date-1,true,'Podemos usar a tua mansion?');
insert into solicitar (usuario, fecha,aceptada,comentario)
  values ('pgadmin',current_date-1,true,'Aceptademe porfi');
insert into galerias (nome) values ('Vite photos');
insert into multimedia (url,galeria,pe) values ('/src/Multimedia/rave.jpeg','Vite photos','Imaxe epica do feston');
insert into fotos (url,galeria) values ('/src/Multimedia/rave.jpeg','Vite photos');
insert into multimedia (url,galeria,pe) values ('/src/Multimedia/fifa.jpeg','Vite photos','Partidilla de fifa');
insert into fotos (url,galeria) values ('/src/Multimedia/fifa.jpeg','Vite photos');

insert into valorar (usuario, puntuacion, fecha, comentario)
  values ('pgadmin', 4, now(), 'vaia feston loko');
insert into valorar (usuario, puntuacion, fecha, comentario)
  values ('arkanto', 2, now(), 'faltaron los premios del torneo :(');
 insert into valorar (usuario, puntuacion, fecha, comentario)
  values ('uxio21andrade', 5, now(), 'para cuando la proxima?');

insert into festas(organizador, nome, fecha,descripcion,coordenadas,indicacionslocalizacion)
  values('arkanto','Navidad',NOW() + '10 days'::interval,'festa de navidad paga el primo Paco',(-1.,11.),'Primeira planta');
insert into privadas(solicitable, datalimite)
  values(false,null);
insert into invitar(usuario, fecha,aceptada,comentario)
  values ('pgadmin',current_date,false,'as 22:00 no Burgo');
insert into invitar(usuario, fecha,aceptada,comentario)
  values ('uxio21andrade',current_date,true,'as 22:00 no Burgo');
insert into invitar (usuario, fecha,aceptada,comentario)
  values ('pablomlago',current_date,false,'As 22:00 no burgo');
insert into galerias (nome) values ('Happy Days');
insert into multimedia (url,galeria,pe) values ('/src/Multimedia/simpsons.jpeg','Happy Days','Dias felices na nosa casa');
insert into fotos (url,galeria) values ('/src/Multimedia/simpsons.jpeg','Happy Days'); 
insert into galerias (nome) values ('Regalos');
insert into multimedia (url,galeria,pe) values ('/src/Multimedia/regalo.jpg','Regalos','Sonido gracioso');
insert into fotos (url,galeria) values ('/src/Multimedia/regalo.jpg','Regalos'); 
insert into multimedia (url,galeria,pe) values ('/src/Multimedia/perro.wav','Regalos','Sonido gracioso');
insert into fotos (url,galeria) values ('/src/Multimedia/perro.wav','Regalos'); 

insert into festas(organizador, nome, fecha,descripcion,coordenadas,indicacionslocalizacion)
  values('uxio21andrade','Fin de Curso',NOW() + '30 days'::interval, 'festa para celebrar o fin de exames',(10.3,2.),'Segunda planta');
insert into privadas(solicitable, datalimite)
  values(true,current_date+20);
insert into solicitar (usuario, fecha,aceptada,comentario)
  values ('pgadmin',current_date,true,'Aceptademe porfa');
insert into solicitar (usuario, fecha,aceptada,comentario)
  values ('pablomlago',current_date,false,'Aceptademe porfa');
insert into invitar (usuario, fecha,aceptada,comentario)
  values ('arkanto',current_date,false,'Aceptademe porfa');
insert into galerias (nome) values ('Friends forever');
insert into multimedia (url,galeria,pe) values ('/src/Multimedia/friends.jpg','Friends forever','Logotipo');
insert into fotos (url,galeria) values ('/src/Multimedia/friends.jpg','Friends forever'); 
insert into multimedia (url,galeria,pe) values ('/src/Multimedia/gente.jpg','Friends forever','Personajes random');
insert into fotos (url,galeria) values ('/src/Multimedia/gente.jpg','Friends forever'); 
insert into multimedia (url,galeria,pe) values ('/src/Multimedia/fantasy_orchestra.wav','Friends forever','Musica epica fantastica');
insert into audios (url,galeria) values ('/src/Multimedia/fantasy_orchestra.wav','Friends forever'); 

insert into festas(organizador, nome, fecha,descripcion,coordenadas,indicacionslocalizacion)
  values('14_hectoor','Cumpleaños',now(),'cumplo 20 lokos',(-1.,11.),'Planta baixa');
insert into privadas(solicitable, datalimite)
  values(false,null);
insert into invitar (usuario, fecha,aceptada,comentario)
  values ('pablomlago',current_date-1,true,'está to pagao');
insert into invitar (usuario, fecha,aceptada,comentario)
  values ('arkanto',current_date-1,true,'está to pagao');
insert into invitar (usuario, fecha,aceptada,comentario)
  values ('pgadmin',current_date-1,true,'está to pagao');
insert into invitar (usuario, fecha,aceptada,comentario)
  values ('uxio21andrade',current_date-1,false,'está to pagao');

insert into valorar (usuario, puntuacion, fecha, comentario)
  values ('pablomlago', 5, now(), 'increible loko');
insert into valorar (usuario, puntuacion, fecha, comentario)
  values ('arkanto', 4, now(), 'fieston loko');

insert into festas(organizador, nome, fecha,descripcion,coordenadas,indicacionslocalizacion)
  values('pgadmin','Fiesta del Siglo',NOW()+'29 days'::interval,'Va a ser un fieston',(-90.1,18.),'na esquina');
insert into privadas(solicitable, datalimite)
  values(true,current_date+28);
insert into solicitar (usuario, fecha,aceptada,comentario)
  values ('pablomlago',current_date-1,false,'me aceptas??');
insert into invitar (usuario, fecha,aceptada,comentario)
  values ('arkanto',current_date-1,false,'vente loko');

insert into festas(organizador, nome, fecha,descripcion,coordenadas,indicacionslocalizacion)
  values('pgadmin','Feston',NOW()+'20 days'::interval,'Va a ser la leche',(-90.1,18.),'na esquina');
insert into privadas(solicitable, datalimite)
  values(false,null);


insert into festas(organizador, nome, fecha,descripcion,coordenadas,indicacionslocalizacion)
  values('ruta','fiesta tropical',NOW()+'10 days'::interval, 'traede ropa hawaiiana para ter descontos en copas',(-1.01,14.),'baixo');
insert into publicas(aforo, precio,cobrada)
  values(80,5.50,false);

insert into entradas (usuario, fechaAdquisicion, cantidade)
	values ('viqueira', now()+'1 days'::interval, 5);
insert into entradas (usuario, fechaAdquisicion, cantidade)
	values ('viqueira', now()+'2 days'::interval, 5.50);
insert into entradas (usuario, fechaAdquisicion, cantidade)
	values ('pablomlago', now()+'3 days'::interval, 5);
insert into entradas (usuario, fechaAdquisicion, cantidade)
	values ('arkanto', now()+'4 days'::interval, 5);
insert into entradas (usuario, fechaAdquisicion, cantidade)
	values ('arkanto', now()+'5 days'::interval, 5);
insert into entradas (usuario, fechaAdquisicion, cantidade)
	values ('uxio21andrade', now()+'6 days'::interval, 5.50);


insert into redes (plataforma,id_plataforma,url,usuario) values ('twitter','Scrumb_Master','www.twtter.com/Scrumb_Master','uxio21andrade');
insert into redes (plataforma,id_plataforma,url,usuario) values ('github', 'Scrumb_Master','www.github/Scrumb_Master/Xavi_the_best','uxio21andrade');
insert into redes (plataforma,id_plataforma,url,usuario) values ('instagram','pablo_violin','www.instagram.com/pablo_violin','arkanto');
insert into redes (plataforma,id_plataforma,url,usuario) values ('facebook','pablo_violin','www.facebook.com/Pablo_Rodriguez_499','arkanto');
insert into redes (plataforma,id_plataforma,url,usuario) values ('github','BDTeacher','www.github.com/Biblioteca','viqueira');
insert into redes (plataforma,id_plataforma,url,usuario) values ('linkedin','Pablo_ML','www.linkedin/sdeio32','pablomlago');


insert into bloquear(bloqueador,bloqueado)
  values('pgadmin','viqueira');
insert into bloquear(bloqueador,bloqueado)
  values('viqueira','pgadmin');
insert into bloquear(bloqueador,bloqueado)
  values('viqueira','pablomlago');
insert into bloquear(bloqueador,bloqueado)
  values('viqueira','uxio21andrade');
insert into bloquear(bloqueador,bloqueado)
  values('viqueira','arkanto');
insert into bloquear(bloqueador,bloqueado)
  values('viqueira','14_hectoor');

insert into amigar(usuario, amigo)
  values('pablomlago','uxio21andrade');
insert into amigar(usuario, amigo)
  values('pablomlago','arkanto');
insert into amigar(usuario, amigo)
  values('pablomlago','pgadmin');
insert into amigar(usuario, amigo)
  values('uxio21andrade','14_hectoor');
insert into amigar(usuario, amigo)
  values('uxio21andrade','pablomlago');
insert into amigar(usuario, amigo)
  values('pgadmin','uxio21andrade');
insert into amigar(usuario, amigo)
  values('arkanto','14_hectoor');
insert into amigar(usuario, amigo)
  values('arkanto','pablomlago');
insert into amigar(usuario, amigo)
  values('viqueira','pgadmin');

insert into mensaxear values('arkanto', '14_hectoor', now(), 'Ola Héctor, que tal?', false);
insert into mensaxear values('14_hectoor', 'arkanto', now()+'15 seconds'::interval, 'Ola! Ben, e ti?', false);
insert into mensaxear values('arkanto', '14_hectoor', now()+'120 seconds'::interval, 'Ben tamén', false);
insert into mensaxear values('14_hectoor', 'arkanto', now()+'145 seconds'::interval, 'Alégrome!', false);
insert into mensaxear values('pablomlago', 'uxio21andrade', now(), 'Ei, preparado para a festa?', false);
insert into mensaxear values('uxio21andrade', 'pablomlago', now()+'5 minutes'::interval, 'Home claro, vai estar xenial!', false);
insert into mensaxear values('pablomlago', 'uxio21andrade', now()+'8 minutes'::interval, 'Perfecto entón', false);

insert into postear values('pablomlago',1,now(),'Que tal chavales?', true);
insert into postear values('pablomlago',1,now()+'5 seconds'::interval,'Preparados para a festa?', true);
insert into postear values('14_hectoor',2,now(), 'Vamos compañeiros, vai ser boa!', true);
insert into postear values('ruta',7,now(),'Con entrada anticipada, 3 chupitos gratis!', true);

