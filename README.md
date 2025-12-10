# 📚 Library API

![Java](https://img.shields.io/badge/Java-21%2B-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=spring-security&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)

API RESTful completa para gerenciamento de uma livraria, desenvolvida como parte de um estudo aprofundado sobre o ecossistema **Spring Boot**.

O projeto segue rigorosas boas práticas de engenharia de software, apresentando uma arquitetura em camadas, **segurança robusta com OAuth2**, validação de dados, integridade referencial e tratamento global de exceções.

---

## 🚀 Tecnologias Utilizadas
- **Java 21**
- **Spring Boot 3**
- **Spring Authorization Server** (Emissão de Tokens, JWK Source, OIDC)
- **Spring Security** (Resource Server e Filtros Customizados)
- **OAuth2 Client** (Login Social com Google)
- **Spring Data JPA** (Persistência)
- **PostgreSQL** (Banco de dados)
- **SpringDoc OpenAPI / Swagger UI** (Documentação Dinâmica)
- **MapStruct** (Mapeamento DTO ↔ Entidade)
- **Lombok** (Boilerplate reduction)
- **Bean Validation** (Validação de dados)
- **Maven** (Gerenciamento de dependências)


---

## ⚙️ Arquitetura e Segurança (Novo!)

### 🔐 Authorization Server
A aplicação gera seus próprios tokens de acesso:
- **Assinatura RSA:** Utiliza chaves assimétricas (Públicas/Privadas) geradas via `java.security.KeyPair`.
- **JWT Customizado:** O token inclui *claims* personalizadas como `authorities` (permissões) e `email`.
- **Fluxo OAuth2:** Suporte a *Client Credentials* e fluxos de autorização padrão.

### 🛡️ Controle de Acesso (RBAC)
O sistema define níveis estritos de permissão:
- **`MANAGER`**: Acesso administrativo total. Pode cadastrar **Clientes API**, e tem controle total sobre **Autores** e **Livros**.
- **`OPERATOR`**: Perfil operacional. Pode gerenciar **Livros** e visualizar **Autores**, mas **NÃO** pode cadastrar ou excluir autores.

### 📄 Documentação Interativa
A API possui documentação via Swagger UI, com suporte a autenticação Bearer Token direta no navegador.
- **URL:** `/swagger-ui.html`

---

### 👤 Gestão de Autores
- **Imutabilidade:** Uso de Java Records para DTOs.
- **Validações:** Regras de negócio que impedem duplicidade de registros (Nome, Data Nasc., Nacionalidade).
- **Integridade:** Bloqueio de exclusão caso o autor possua livros vinculados.
- **Auditoria:** Rastreamento automático de data de criação e atualização.

### 📚 Gestão de Livros
- **Regras de Negócio:**
  - Unicidade de ISBN.
  - Preço obrigatório apenas para livros publicados a partir de 2020.
- **Busca Avançada:** Filtros dinâmicos com **JPA Specifications** (Título, Gênero, Ano, Nome do Autor).
- **Paginação:** Otimização de listagens grandes via `Pageable`.

---

## 🔌 Endpoints (API Reference)

> ⚠️ **Atenção:** A maioria das rotas exige autenticação (Header `Authorization: Bearer <token>`).

### 🔑 Gestão de Clientes (`/clients`)
*Responsável por cadastrar aplicações que podem consumir a API.*

| Método | Endpoint | Permissão | Descrição |
|--------|----------|-----------|-----------|
| `POST` | `/clients` | **MANAGER** | Cadastra um novo Client (App) com `client_id`, `secret` e escopos. |

#### 📦 Payload (Client)
```json
{
  "clientId": "front-end-app",
  "clientSecret": "segredo123",
  "redirectUri": "http://localhost:3000/authorized",
  "scope": "read write"
}
```

### 👤 Usuários & Autenticação

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `POST` | `/users` | Cria um novo usuário (Login, Senha, Email, Roles). **Público**. |
| `GET` | `/login` | Página de login (Google ou Credenciais). |

#### 📦 **Exemplo de Payload (Criação)**
### Criar Usuário
```json
{
  "login": "wilton_dev",
  "password": "strongPassword123",
  "email": "dev@example.com",
  "roles": ["ADMIN", "OPERATOR"]
}
```

### **Autores** (`/authors`)

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `POST` | `/authors` | Cria um autor. Retorna `201 Created` com Location no header. |
| `GET` | `/authors/{id}` | Busca autor por ID (UUID). |
| `GET` | `/authors` | Lista autores com filtros opcionais: `?name=...&nationality=...`. |
| `PUT` | `/authors/{id}` | Atualiza um autor existente. |
| `DELETE` | `/authors/{id}` | Remove um autor (somente se não houver livros associados). |

#### 📦 **Exemplo de Payload (Criação)**
### Criar Autor

```json
{
  "name": "J.K. Rowling",
  "birthDate": "1965-07-31",
  "nationality": "British"
}
```

### 📚 Livros – `/books`

| Método | Endpoint        | Descrição                                      |
|--------|------------------|------------------------------------------------|
| **POST** | `/books`         | Cria um livro. Retorna **201 Created** com `Location`. |
| **GET**  | `/books/{id}`    | Busca livro por ID.                           |
| **GET**  | `/books`         | Lista livros com paginação e filtros avançados. |
| **PUT**  | `/books/{id}`    | Atualiza livro existente.                     |
| **DELETE** | `/books/{id}` | Remove um livro.                              |

---

### 📖 Parâmetros de Filtro (Query Params)

| Parâmetro          | Tipo     | Exemplo          | Descrição                         |
|--------------------|----------|------------------|-----------------------------------|
| `isbn`             | String   | `9788532530720`  | Busca por ISBN exato              |
| `title`            | String   | `Harry Potter`   | Contém (case-insensitive)         |
| `author-name`      | String   | `Rowling`        | Nome de autor (contém)            |
| `genre`            | Enum     | `FANTASY`        | Gênero literário                  |
| `publication-year` | Integer  | `2007`           | Ano de publicação exato           |
| `page`             | Integer  | `0`              | Número da página                  |
| `size`             | Integer  | `10`             | Quantidade de itens por página    |

#### 📦 **Exemplo de Payload (Criação)**
### Criar Livro


```json
{
  "isbn": "9786555877840",
  "title": "Clean Code",
  "publicationDate": "2008-08-01",
  "genre": "TECHNICAL",
  "price": 89.90,
  "authorId": "a1b2c3d4-e5f6-7890-1234-567890abcdef"
}

```

---

## 🚨 Tratamento Global de Exceções

Configurado via `@RestControllerAdvice`:

| Exceção | Descrição | HTTP Status |
|--------|-----------|-------------|
| `MethodArgumentNotValidException` | Erro de validação Bean | **422** |
| `DuplicatedRecordException` | Registro duplicado | **409** |
| `OperationNotAllowed` | Operação proibida | **400** |
| `InvalidFieldException` | Regra de negócio violada | **422** |


### ❗ Estrutura Padronizada de Erro (`ErrorResponse`)

Todos os erros seguem o padrão **Problem Details**, garantindo clareza e consistência no retorno da API.

```json
{
  "status": 422,
  "message": "Validation error",
  "errors": [
    {
      "field": "name",
      "message": "Detailed error description"
    }
  ]
}
```
---



## 🖥️ Como Rodar o Projeto Localmente

### ✔️ Pré-requisitos
- Java 21+
- Maven
- PostgreSQL instalado e em execução

---

### 📝 Passos

#### 1. Clone o repositório:
```bash
git clone https://github.com/WiltonReis/library-api.git
```

#### 2. Configure o arquivo application:
```
src/main/resources/application.yaml
```

Exemplo:

```yaml
spring:
  application:
    name: library-api
  datasource:
    url: jdbc:postgresql://localhost:5432/library
    username: postgres
    password: 123
    driver-class-name: org.postgresql.Driver
  security:
    oauth2:
      client:
        registration:
          google:
            client-id: ${GOOGLE_CLIENT_ID}     # Defina nas variáveis de ambiente ou substitua aqui
            client-secret: ${GOOGLE_CLIENT_SECRET}
```

#### 3. Execute a aplicação:
```bash
mvn spring-boot:run
```

---

## 📌 Status do Projeto
🚧 **Em construção** — evoluindo conforme avanço no curso de Spring Boot.

### Próximas implementações:
- 🧪 Testes unitários e de integração
- 📘 Documentação com Swagger/OpenAPI

---

## 👨‍💻 Autor

**Desenvolvido por:** *Wilton Reis*

GitHub: *https://github.com/WiltonReis*

LinkedIn: *https://www.linkedin.com/in/wilton-reis-73aa772a4/*

---

