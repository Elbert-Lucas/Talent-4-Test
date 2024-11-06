# Talent-4-Test (JDK 17 e MySql)

# Instruções

1 - Antes de iniciar o projeto você deve executar manualmente o script SQL abaixo. Certifique-se de mudar o nome do schema no script
e também  nas VM Options: 
###  CREATE DATABASE "nome schema";

2 - Em seguida deve-se configurar as VM options:

-DSPRING_PORT=8080 \
-DDB_PORT= "PORTA DO SEU BANCO DE DADOS MYSQL" \
-DDB_SCHEMA= "nome schema" \
-DDB_USER= "SEU USUARIO DO BANCO DE DADOS MYSQL" \
-DDB_PASSWORD= "A SENHA DO SEU BANCO DE DADOS MYSQL"\
-DJWT_SECRET=hQchrJOpB5U3/lbT6c2sMTDx6WnABLmuKc12QBma0CwCFpu8qUyO+AlnGR2qMDl9


3 - A aplicação possui documentação swagger no endpoint: http://localhost:8080/talent4/api/v1/swagger-ui/index.html e 
também possui uma collection e enviroment Postman na pasta "postman" que esta no diretorio root do projeto.

## Sobre

* A aplicação usa Java 17, Spring 3.3.5 e MySql 8.3
* A aplicação utiliza de FlyWay para versionamento do banco de dados
* A aplicação usa autenticação JWT, antes de iniciar a utilizar você deve registrar um usuario
* A aplicação utiliza de triggers para criar o historico de mudanças nos dados
