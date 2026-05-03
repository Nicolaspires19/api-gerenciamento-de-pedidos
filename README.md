# 🛵 DeliveryAPI

API REST para gerenciamento de entregas, simulando o backend de um sistema estilo iFood/Rappi. Desenvolvida com Java 17 e Spring Boot 3.4.

---

## 📋 Sobre o Projeto

A **DeliveryAPI** é um projeto de portfólio que demonstra a construção de uma API REST completa com autenticação, regras de negócio reais e boas práticas de desenvolvimento backend.

O sistema permite o gerenciamento completo do ciclo de vida de um pedido — desde a criação pelo cliente até a entrega pelo entregador — com validação de transições de status e controle de acesso por roles.

---

## ✅ Funcionalidades

- 🔐 Autenticação com JWT (registro e login)
- 👤 CRUD de usuários com roles (`CLIENTE`, `ENTREGADOR`, `ADMIN`)
- 📦 Criação de pedidos com itens e cálculo automático do valor total
- 🔄 Fluxo de status com validação de transições de negócio
- 🚫 Tratamento global de erros com respostas padronizadas
- 📄 Documentação automática com Swagger/OpenAPI

---

## 🔄 Fluxo de Status do Pedido

```
PENDENTE → ACEITO → EM_ROTA → ENTREGUE
    └──────────────────────→ CANCELADO
```

> Transições inválidas retornam `422 Unprocessable Entity` com mensagem descritiva.

---

## 🏗️ Arquitetura

O projeto segue o padrão **MVC em camadas**:

```
Controller  →  Service  →  Repository  →  Model
   ↑               ↑
  DTO           Regras de negócio
```

```
src/
├── config/          # SecurityConfig, JwtService, JwtAuthFilter, SwaggerConfig
├── controller/      # AuthController, PedidoController, UserController, TrackingController
├── dto/             # PedidoRequestDTO, PedidoResponseDTO, ItemPedidoDTO...
├── exceptions/      # GlobalExceptionHandler, ResourceNotFoundException...
├── model/
│   └── enums/       # Role, StatusPedido
├── repository/      # PedidoRepository, UserRepository
└── service/         # AuthService, PedidoService, UserService, UserDetailsServiceImpl
```

---

## 🛠️ Stack

| Tecnologia | Versão |
|---|---|
| Java | 17 |
| Spring Boot | 3.4.5 |
| Spring Security | 6.4 |
| Spring Data JPA | 3.4 |
| JWT (jjwt) | 0.11.5 |
| H2 Database | Runtime |
| Swagger/OpenAPI | 2.8.8 |
| Lombok | Latest |
| Maven | 3.x |

---

---

## 🔑 Endpoints

### Autenticação
| Método | Rota | Descrição | Auth |
|---|---|---|---|
| `POST` | `/auth/register` | Cadastrar usuário | Público |
| `POST` | `/auth/login` | Login e geração do JWT | Público |

### Pedidos
| Método | Rota | Descrição | Auth |
|---|---|---|---|
| `POST` | `/pedidos` | Criar pedido | JWT |
| `GET` | `/pedidos` | Listar pedidos | JWT |
| `GET` | `/pedidos/{id}` | Buscar pedido por ID | JWT |
| `PATCH` | `/pedidos/{id}/aceitar` | Aceitar pedido | JWT |
| `PATCH` | `/pedidos/{id}/em-rota` | Marcar em rota | JWT |
| `PATCH` | `/pedidos/{id}/entregar` | Marcar como entregue | JWT |
| `PATCH` | `/pedidos/{id}/cancelar` | Cancelar pedido | JWT |
| `DELETE` | `/pedidos/{id}` | Remover pedido | JWT |

---

## 📬 Exemplos de Requisição

### Cadastro de usuário
```http
POST /auth/register
Content-Type: application/json

{
  "name": "Nicolas Pires",
  "email": "nicolas@email.com",
  "password": "123456",
  "role": "CLIENTE"
}
```

**Resposta `200 OK`:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "type": "Bearer"
}
```

### Criar pedido
```http
POST /pedidos
Authorization: Bearer {token}
Content-Type: application/json

{
  "clienteId": 1,
  "enderecoEntrega": "Rua das Flores, 123 - SP",
  "itens": [
    {
      "descricao": "Pizza Margherita",
      "quantidade": 2,
      "precoUnitario": 45.90
    }
  ]
}
```

**Resposta `201 Created`:**
```json
{
  "id": 1,
  "status": "PENDENTE",
  "valorTotal": 91.80,
  "enderecoEntrega": "Rua das Flores, 123 - SP",
  "criadoEm": "2026-05-03T19:28:02"
}
```

### Transição de status inválida
```http
PATCH /pedidos/1/cancelar
Authorization: Bearer {token}
```

**Resposta `422 Unprocessable Entity`:**
```json
{
  "status": 422,
  "erro": "Operação inválida",
  "mensagem": "Não é possível mudar de ENTREGUE para CANCELADO",
  "timestamp": "2026-05-03T19:45:00"
}
```

---

## 🗄️ Banco de Dados

O projeto utiliza **H2 (in-memory)** para facilitar a execução local sem configuração adicional.

Console disponível em: `http://localhost:8080/h2-console`

| Campo | Valor |
|---|---|
| JDBC URL | `jdbc:h2:mem:pedidos-db` |
| Username | `sa` |
| Password | *(vazio)* |

---

## 👨‍💻 Autor

**Nicolas Pires**

[![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](www.linkedin.com/in/nicolas-ferreira-pires)
[![GitHub](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/Nicolaspires19)

---

## 📝 Licença

Este projeto está sob a licença MIT.
