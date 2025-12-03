# 📚 Library API

API RESTful para gerenciamento de uma livraria, desenvolvida como parte de um estudo aprofundado sobre o ecossistema **Spring Boot**.

O projeto foca em boas práticas de engenharia de software, incluindo arquitetura em camadas, validação de dados, tratamento de exceções personalizado e integridade referencial.

---

## 🚀 Tecnologias Utilizadas
- **Java 21+**
- **Spring Boot 3**
- **Spring Data JPA** (Persistência de dados)
- **PostgreSQL** (Banco de dados relacional)
- **Lombok** (Redução de boilerplate)
- **Bean Validation** (Validações de DTOs)
- **Maven** (Gerenciamento de dependências)

---

## ⚙️ Funcionalidades e Práticas Implementadas

## 👤 Gestão de Autores

### ✨ Estruturas e Validações
- Uso de **DTOs com Java Records** (`AuthorDTO`) para imutabilidade.
- **Validações customizadas** via `AuthorValidator`:
  - Evita cadastros duplicados validando **nome**, **data de nascimento** e **nacionalidade**.

### 🔍 Busca Dinâmica
- Implementada com **Query by Example (ExampleMatcher)**:
  - Filtros flexíveis, **case-insensitive** e que ignoram campos nulos.

### 🔒 Integridade de Dados
- Exclusão bloqueada para autores com livros associados.
- Retorna `OperationNotAllowed` em caso de violação.

### 🕒 Auditoria automática
- Campos com `@CreatedDate` e `@LastModifiedDate`.
---

## 📖 Gestão de Livros

### ✨ Estruturas
- Entidade `Book` com relacionamento `@ManyToOne` para `Author`.
- Enum `GenreBook` para gêneros literários.

### 🔎 Validações de Negócio
- `BookValidator`:
  - Controla unicidade do **ISBN**.
  - Regra: se o ano de publicação ≥ 2020, o **preço** é obrigatório.
    - Caso contrário → `InvalidFieldException`.

### 🔍 Filtros Avançados (Specifications)
- Implementados com **JPA Specifications** permitindo filtros combinados:
  - ISBN
  - Título (contains, ignore-case)
  - Gênero
  - Ano de publicação
  - Nome do autor
- Uso de `Join` e funções SQL (ex.: `to_char`) para otimização.

### 📄 Paginação
- Implementada com `Pageable`.
- Endpoints de listagem retornam `Page<BookSearchResultDTO>`.

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

## 🔌 Endpoints (API Reference)

### **Autores** (`/authors`)

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `POST` | `/authors` | Cria um autor. Retorna `201 Created` com Location no header. |
| `GET` | `/authors/{id}` | Busca autor por ID (UUID). |
| `GET` | `/authors` | Lista autores com filtros opcionais: `?name=...&nationality=...`. |
| `PUT` | `/authors/{id}` | Atualiza um autor existente. |
| `DELETE` | `/authors/{id}` | Remove um autor (somente se não houver livros associados). |

#### 📦 **Exemplo de Payload (Criação)**

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

#### 2. Configure o banco de dados no arquivo:
```
src/main/resources/application.properties
```

Exemplo:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/library_db
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

#### 3. Execute a aplicação:
```bash
mvn spring-boot:run
```

---

## 📌 Status do Projeto
🚧 **Em construção** — evoluindo conforme avanço no curso de Spring Boot.

### Próximas implementações:
- 🔐 Autenticação e Autorização (Spring Security)
- 🧪 Testes unitários e de integração
- 📘 Documentação com Swagger/OpenAPI

---

## 👨‍💻 Autor

**Desenvolvido por:** *Wilton Reis*

GitHub: *https://github.com/WiltonReis*

LinkedIn: *https://www.linkedin.com/in/wilton-reis-73aa772a4/*

---

