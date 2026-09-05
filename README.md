# LANCHETOP - Sistema de Gerenciamento de Lanchonete

> Sistema completo de pedidos para lanchonete, com backend em Spring Boot e frontend em Angular. Desenvolvido aplicando práticas reais de produção: autenticação segura, testes automatizados, otimização de banco de dados e infraestrutura containerizada.

---

## Destaques Técnicos

- Autenticação JWT completa com Access Token + Refresh Token, revogação via banco de dados, e RBAC (controle de acesso por perfil: Cliente/Funcionário)
- Testes automatizados com JUnit 5, Mockito e Testcontainers (testes de integração com PostgreSQL real)
- Otimização de performance: identificação e correção de problema N+1 usando JOIN FETCH
- Containerização completa com Docker multi-stage build (backend, frontend e banco), orquestrados via Docker Compose
- CI/CD com GitHub Actions, rodando testes automaticamente a cada push
- Migrations versionadas com Flyway
- Integração de pagamento com Mercado Pago

---

## Tecnologias Utilizadas

### Backend
- Java 21
- Spring Boot (Web, Security, Data JPA, Validation)
- PostgreSQL 16
- Flyway (versionamento de banco de dados)
- JUnit 5, Mockito, Testcontainers (testes)
- Docker & Docker Compose
- GitHub Actions (CI/CD)
- Maven

### Frontend
- Angular 19+
- RxJS e HttpClient
- Nginx (servindo a build de produção containerizada)

---

## Funcionalidades

- Gerenciamento de Cardápio — CRUD de produtos (lanches, bebidas, sobremesas)
- Fluxo de Pedidos — criação, cancelamento, avanço de status (máquina de estados)
- Clientes — cadastro com validação assíncrona de CPF/login únicos
- Autenticação e Autorização — login com JWT, refresh token, controle de acesso por perfil
- Pagamento — integração com Mercado Pago
- Painel do Atendente — visualização e gestão de pedidos por status

---

## Arquitetura

- Arquitetura em camadas (Controller → Service → Repository)
- DTOs para entrada/saída, nunca expondo entidades diretamente
- Testes unitários (Mockito) para lógica de negócio, e de integração (Testcontainers) para queries reais

---

## Como rodar o projeto

\`\`\`bash
docker-compose up --build
\`\`\`

Isso sobe: backend (porta 8081), frontend (porta 4200) e PostgreSQL, todos containerizados.

---

## Rodando os testes

\`\`\`bash
mvn test
\`\`\`