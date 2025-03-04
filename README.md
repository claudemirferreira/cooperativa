## Projeto votação de assembleia

## Tecnologia utilizadas
- Java 
- Spring boot
- Mysql
- Docker
- Kafka
- Zookeeper

## Diagrama Entidade Realiocnamento

![img_1.png](img_1.png)


## System Design
![img_2.png](img_2.png)

## Startar o projeto

- Na raiz do projeto execute o camando abaixo:
```
docker compose build
docker compose up
```

### Realizar testes via swagger

Você pode acessar a documentação da API através do Swagger UI no seguinte link:

[Swagger UI](http://localhost:8080/swagger-ui/index.html#/)

![img.png](img.png)

### Passos para execução dos teste

- Criar uma Pauta;
- Criar uma Sessão;
- Registrar Voto
- Schedule a cada um minutos fechar as sessões abertas e que a data fim é menor que a data atual; 
- Schedule public no topico o resultado da votação;

