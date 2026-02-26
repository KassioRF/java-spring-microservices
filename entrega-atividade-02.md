# Atividade Prática 01: Micro serviço Venda de Ingressos

> Documentação referente à entrega da atividade prática 01: Micro serviço de Venda de Ingressos.

Os marcos do desenvolvimento desta tarefa podem ser identificados pelos seguintes PRs:

- PR#10: Adicionado serviço de Gateway com registro dos serviços users e sales
- PR#11: Adicionada a aplicação Web administrativa para o sistema de vendas de ingressos.
  - O app web também foi incluido no Gateway

## Como executar

1. Executar os serviços de backend via Docker compose

No diretório [./ticket-sales-services/docker](./ticket-sales-services/docker) basta iniciar os containers, referenciado o arquivo `.yml` e o arquivo `.env.example` .

```bash
docker compose -f compose-dev.yml --env-file .env.example up
```

2. Executar o app web

No diretório [./ticket-sales-services/web](./ticket-sales-services/web) basta inicalizar o Vite

```bash
# Ex. com pnpm
pnpm dev
```

## Organização do repositório da aplicação WEB

Os componentes utilizados foram os seguintes:

```
.src
├── components    # Componentes de UI, incluind Shadcn
├── env.ts
├── hooks         # Hooks utilizados para consumir os serviços externos dentro dos componentes
│   ├── events/
│   ├── sales/
│   └── users/
├── index.css
├── layout.tsx    # Layout base da interface
├── main.tsx
├── pages/        # Páginas da aplicação
├── providers/    # Provider para centralizar notifiações e aviso
├── routes.tsx
├── schemas/      # Tipagem dos DTOS de entrada e saída para cada serviço
├── services/     # Centraliza as chamadas para as apis externas
└── types/        # Define constantes como status e tipo de evento

```

> Observações

As chamadas para os serviços são feitas via `Axios` e estão implementadas em [./src/services](./ticket-sales-services/web/src/services/)

Os tipos de entrada e saída estão definidos em [./src/schemas](./ticket-sales-services/web/src/schemas/).

- Utiliza o `Zod` pra validação

O consumo dos serviços pelos componentes é feito via hooks implementados em [./src/hooks](./ticket-sales-services/web/src/hooks/)

- Utiliza `react-query`
