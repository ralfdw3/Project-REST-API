# Project REST API - Webvote

# Resolução do problema:
1) O primeiro passo foi criar um projeto utilizando o Spring Boot e adicionar as dependências; 
2) Realizar a configuração do banco de dados MySQL 
3) Após, criei uma classe para realizar a verificação da conexão com o banco;
4) Criar a classes model, controller e repository;
5) Desenvolver os testes unitários;

# Problemas encontrados: 
1) Como nunca havia utilizado o Spring Boot, encontrei algumas dificuldades iniciais de configuração quando tentei utilizar o Maven. Primeiramente utilizei 
um banco de dados H2 e, quando fiz a leitura da atividade novamente, identifiquei que não poderia ser um banco de memória, entretanto 
o Spring não conseguia se conectar com banco MySQL. Por fim, acabei iniciando um novo projeto utilizando Gradle, onde dei start com as mesmas dependências
e a configuração funcionou sem problemas;
2) Tive dificuldade com a utilização dos Mocks para o desenvolvimento dos testes unitários;

# Tarefas bônus
1) Na primeira tarefa, o link https://user-info.herokuapp.com/users/{cpf} estava offline, mas tentei desenvolver um código que poderia ser utilizado nestas situações;
2) Acredito que para este tipo de projeto, a melhor maneira de monitorar seria através de Load testing, onde poderia identificar o tempo de resposta durante
as trocas de informação entre o banco de dados e outras aplicações;
3) Eu versionaria utilizando um dos principais métodos que seria pelo caminho da URL, visto que facilita na implementação e no suporte para versões antigas;

# Para testar o código:
1) JDK Java 17.0.4.1;
2) Configurar o path, username e password para conexão com o banco de dados, através do arquivo application.properties;
3) Para testar a API aconselho utilizar o Swagger, visto que está implementado no projeto (http://localhost:8080/swagger-ui)

