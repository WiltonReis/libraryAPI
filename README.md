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

### 👤 Gestão de Autores
- **DTOs com Java Records** (`AuthorDTO`) garantindo imutabilidade.
- **Validações customizadas:**
    - `AuthorValidator`: evita cadastros duplicados validando *nome*, *data de nascimento* e *nacionalidade*.
- **Busca dinâmica (Query by Example):**
    - Implementado com `ExampleMatcher` para filtragem flexível e case-insensitive.
- **Integridade de dados:**
    - Bloqueio de exclusão com exceção `OperationNotAllowed` caso o autor possua livros cadastrados.
- **Auditoria automática (JPA Audit):**
    - Campos com `@CreatedDate` e `@LastModifiedDate`.

---

### 📖 Gestão de Livros *(Em andamento)*
- Entidade `Book` com relacionamento `@ManyToOne` para `Author`.
- Enum de gêneros literários (`GenreBook`).

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

