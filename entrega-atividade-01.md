# Atividade Prática 01: Micro serviço Venda de Ingressos

Documentação referente à entrega da atividade prática 01: Micro serviço de Venda de Ingressos

## Como executar

No diretório [./ticket-sales-services/docker](./ticket-sales-services/docker) basta iniciar os containers, referenciado o arquivo ``.yml`` e o arquivo ``.env.example`` .

```bash
docker compose -f compose-dev.yml --env-file .env.example up
```

## Organização do repositório 

Os componentes utilizados foram os seguintes:

```
ticket-sales-services/
├── docker 
│   ├── compose-dev.yml # orquestração dos containers
│   ├── database        # scripts de inicialização dos banco de dados
│   ├── Makefile        # Make para os comandos docker, up build etc..
│   └── volumes         # Para os volumes (volume do DB (local))
├── README.md
└── tickets
    ├── nameserver      # Serviço de name server com OpenFeign
    ├── _rest-tests     # Arquivos .rest com testes manuais dos endpoints
    ├── sales           # Serviço implementado nessa atividade
    └── users           # Serviço implementado a partir das aulas
```


Os serviços de banco de dados (PostgreSQL), ``users-service``; ``sales-service`` e ``nameserver`` rodam em containers e são orquestrados via docker compose. 

- Conforme definido no arquivo: [compose-dev.yml](./ticket-sales-services/docker/compose-dev.yml).

## Endpoints

Os endpoints implementados para o ``sales-service`` são descritos a seguir. O arquivo [/rest-tests/sales.rest](./ticket-sales-services/tickets/_rest-tests/sales.rest)  contém os endpoints já com os dados utilizados para testes manuais.

## Eventos

### GET `/events`
- Listar todos os eventos

### GET `/events/id/{eventId}`
- Encontrar evento por **eventId (UUID)**

### GET `/events/name/{name}`
- Encontrar evento por  **name (String)**

### GET `/events/type/{type}`
- Encontrar evento por **type (ENUM)**

---

### POST `/events`
- Criar novo evento

**Body:**
- organizerId (UUID)
- name (String)
- description (String)
- type (ENUM)
- allotedTickets (Integer)
- eventDate (LocalDateTime)
- expectedDuration (LocalDateTime)
- startingSales (LocalDateTime)
- endingSales (LocalDateTime)
- price (Decimal)

---

### PATCH `/events/id/{eventId}`
- Atualizar  evento (parcial ou completo)

**Body (all optional):**
- organizerId (UUID)
- name (String)
- description (String)
- type (ENUM)
- allotedTickets (Integer)
- eventDate (LocalDateTime)
- expectedDuration (LocalDateTime)
- startingSales (LocalDateTime)
- endingSales (LocalDateTime)
- price (Decimal)

---

### DELETE `/events`
- Excluir event  

**Body:**
- eventId (UUID)
- organizerId (UUID)

---

# Vendas

### POST `/sales`
- Criar novo pedido de venda

**Body:**
- eventId (UUID)
- userId (UUID)

---

### GET `/sales`
- Listar todas as vendas

### GET `/sales/id/{saleId}`
- Encontrar venda por **saleId (UUID)**

### GET `/sales/event/{eventId}`
- Listar venda por **eventId (UUID)**

### GET `/sales/user/{userId}`
- Listar venda por **userId (UUID)**

---

# Sale Status Actions

### POST `/sales/process-payment`
- Processar pagamento

**Body:**
- userId (UUID)
- saleId (UUID)

---

### POST `/sales/cancel`
- Cancelar venda sale  
  - OPEN → CANCELLED  
  - PAID → REFUNDED  

**Body:**
- userId (UUID)
- saleId (UUID)

