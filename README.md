# Visão geral do projeto
E-commerce com microserviços de compra, envio de e-mail, detecção de fraude e registro de logs se comunicando via mensageria com Kafka.

# Conceito geral do Apache Kafka

- No Kafka é criado um tópico com N partições.
- O produtor envia uma mensagem para o tópico. Essa mensagem é armazenada em uma das N partições conforme a partition key da mensagem.
- Todos os grupos de consumidores do tópico recebem a mensagem; cada consumidor do grupo lê as mensagens de uma ou mais partições.
- Se houver mais consumidores no grupo do que partições, alguns consumidores ficarão ociosos. Se houver menos consumidores no grupo do que partições, alguns consumidores lerão de mais de uma partição.

---

## Comandos Docker (exemplo de execução)

Iniciar um container Kafka:

```bash
docker run -p 9092:9092 --name kafka apache/kafka:4.1.1
```

Gerenciar o container:

```bash
docker start kafka
docker stop kafka
docker logs -f kafka

docker exec --workdir /opt/kafka/bin/ -it kafka sh
```

---

## Alterar número de partições (configuração)

Editar `../config/server.properties` e ajustar:

```text
num.partitions=3
```

Alterar o número de partições de um tópico já existente:

```bash
./kafka-topics.sh --alter --bootstrap-server localhost:9092 --topic ECOMMERCE_NEW_ORDER --partitions 3
```

---

## Criar tópico

Criar o tópico `LOJA_NOVO_PEDIDO` no Kafka rodando em `localhost:9092`:

```bash
./kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic LOJA_NOVO_PEDIDO
```

---

## Listar e descrever tópicos

Listar tópicos:

```bash
./kafka-topics.sh --list --bootstrap-server localhost:9092
```

Descrever tópicos e partições:

```bash
./kafka-topics.sh --describe --bootstrap-server localhost:9092
```

---

## Produzir e consumir mensagens

Produzir mensagens no tópico `LOJA_NOVO_PEDIDO`:

```bash
./kafka-console-producer.sh --bootstrap-server localhost:9092 --topic LOJA_NOVO_PEDIDO
```

Consumir mensagens do tópico `LOJA_NOVO_PEDIDO`:

```bash
./kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic LOJA_NOVO_PEDIDO
```

Consumir desde o início do tópico:

```bash
./kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic LOJA_NOVO_PEDIDO --from-beginning
```
