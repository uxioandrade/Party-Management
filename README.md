# Party Management App

> Java+Postgresql implementation of a party management app

### Setting everything up

* First of all, navigate to the root directory and create a file called ```baseDatos.properties``
* This file must contain the following lines: 
```gestor=postgresql
  servidor=localhost
  puerto=5432
  baseDatos=partymanagement
  usuario=postgres
  clave=admin```

* Now, navigate to the src/ directory run each one of the following commands:

```
docker run --name party-management-db --network=postgres-network -e "POSTGRES_PASSWORD=admin" -p 5432:5432 -v "$PWD"/PostgreSQL:/var/lib/postgresql/data -d postgres
docker cp ddl.sql party-management-db:/docker-entrypoint-initdb.d/ddl.sql
docker cp creacionDatos.sql party-management-db:/docker-entrypoint-initdb.d/creacionDatos.sql
docker exec -it party-management-db psql -U postgres -f docker-entrypoint-initdb.d/ddl.sql
docker exec -it party-management-db psql -U postgres -f docker-entrypoint-initdb.d/creacionDatos.sql
```

* Then, you should add the following jars to your project:

 * PostgreSQL JDBC Driver
 * jdatepicker-1.3.4
 * mail-1.4.7