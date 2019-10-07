#!/usr/bin/env bash

docker run --name party-management-db --network=postgres-network -e "POSTGRES_PASSWORD=admin" -p 5432:5432 -v "$PWD"/PostgreSQL:/var/lib/postgresql/data -d postgres
docker cp ddl.sql party-management-db:/docker-entrypoint-initdb.d/ddl.sql
docker cp creacionDatos.sql party-management-db:/docker-entrypoint-initdb.d/creacionDatos.sql
docker exec -it party-management-db psql -U postgres -f docker-entrypoint-initdb.d/ddl.sql
docker exec -it party-management-db psql -U postgres -f docker-entrypoint-initdb.d/creacionDatos.sql