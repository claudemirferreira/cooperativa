## Projeto votação de assembleia

## Tecnologia utilizadas
- Java 
- Spring boot
- Mysql
- Docker
- Kafka
- Kafka UI
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

### Acessar o topico do kafka

- Para visualizar as mensagens publicada no kafka

[Kafka UI](http://localhost:8081/ui/clusters/local/all-topics/voto-topic)

![img_4.png](img_4.png)


### Passos para execução dos teste

- Criar uma Pauta;
- Criar uma Sessão;
- Registrar Voto
- Schedule a cada um minutos fechar as sessões abertas e que a data fim expirada; 
- Schedule public no topico o resultado da votação;
