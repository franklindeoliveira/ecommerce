Conceito geral do Apache Kafka:
No Kafka é criado um topico com N partições.
O produtor envia uma mensagem para o topico. Essa mensagem é armazenada em uma das N partições conforme o partition key da mensagem.
Todos os grupos de consumidores do topico recebem a mensagem, onde cada consumidor do grupo lê as mensagens de uma ou mais partições.
Se houver mais consumidores no grupo do que partições, alguns consumidores ficarão ociosos. Da mesma forma, se houver menos consumidores no grupo do que partições, alguns consumidores lerão de mais de uma partição.

docker run -p 9092:9092 --name kafka apache/kafka:4.1.1

docker start kafka
docker stop kafka
docker logs -f kafka

docker exec --workdir /opt/kafka/bin/ -it kafka sh

Mudar numero de particoes:
vi ../config/server.properties
num.partitions=3
Mudar o numero de particoes de um topico ja existente
./kafka-topics.sh --alter --bootstrap-server localhost:9092 --topic ECOMMERCE_NEW_ORDER --partitions 3

Criar o topico LOJA_NOVO_PEDIDO no kafka rodando em localhost:9092:
./kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic LOJA_NOVO_PEDIDO

Listar os topicos armazenados no kafka localhost:9082:
./kafka-topics.sh --list --bootstrap-server localhost:9092

Descrever os topicos e partições armazenados no kafka localhost:9082:
./kafka-topics.sh --describe --bootstrap-server localhost:9092

Criar mensagens no topico LOJA_NOVO_PEDIDO:
./kafka-console-producer.sh --bootstrap-server localhost:9092 --topic LOJA_NOVO_PEDIDO

Consumir mensagens do topico LOJA_NOVO_PEDIDO:
./kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic LOJA_NOVO_PEDIDO

Consumir mensagens desde o inicio do topico LOJA_NOVO_PEDIDO:
./kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic LOJA_NOVO_PEDIDO --from-beginning

