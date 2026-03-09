# 🗣️ API do ForumHub

<p align="center">
  <img src="https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=java" />
  <img src="https://img.shields.io/badge/Spring%20Boot-4.0.3-brightgreen?style=for-the-badge&logo=springboot" />
  <img src="https://img.shields.io/badge/MySQL-8.0-blue?style=for-the-badge&logo=mysql" />
  <img src="https://img.shields.io/badge/JWT-Auth0-black?style=for-the-badge&logo=jsonwebtokens" />
  <img src="https://img.shields.io/badge/Status-Concluído-green?style=for-the-badge" />
</p>

> API REST desenvolvida como parte do **Challenge Back End** da [Alura](https://www.alura.com.br/), simulando o back-end de um fórum de perguntas e respostas.

---

## 📋 Índice

- [Sobre o Projeto](#sobre-o-projeto)
- [Funcionalidades](#funcionalidades)
- [Tecnologias](#tecnologias)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Diagrama do Banco de Dados](#diagrama-do-banco-de-dados)
- [Como Executar](#como-executar)
- [Endpoints da API](#endpoints-da-api)
- [Autenticação](#autenticação)

---

## 📖 Sobre o Projeto

O **ForumHub** é uma API REST que replica o funcionamento de um fórum no back-end. A aplicação permite que usuários autenticados criem, visualizem, atualizem e deletem tópicos de discussão — operações conhecidas como **CRUD**.

O projeto foi desenvolvido seguindo as boas práticas do modelo REST, com autenticação via JWT, validações de regras de negócio e persistência de dados em banco relacional.

---

## ✅ Funcionalidades

- [x] Autenticação de usuários com JWT
- [x] Criar um novo tópico
- [x] Listar todos os tópicos com paginação e filtros por curso e ano
- [x] Buscar um tópico específico por ID
- [x] Atualizar um tópico
- [x] Deletar um tópico
- [x] Validação de campos obrigatórios
- [x] Bloqueio de tópicos duplicados (mesmo título e mensagem)
- [x] Senhas armazenadas com criptografia BCrypt

---

## 🛠️ Tecnologias

| Tecnologia | Versão |
|---|---|
| Java | 17 |
| Spring Boot | 4.0.3 |
| Spring Security | 7.0.3 |
| Spring Data JPA | 4.0.3 |
| JWT (Auth0) | 4.5.1 |
| Flyway | 11.14.1 |
| MySQL | 8.0 |
| Lombok | 1.18.42 |
| Maven | 4 |

---

## 📁 Estrutura do Projeto

```
src/main/java/br/com/alura/forumhub/
├── controller/
│   ├── AuthenticationController.java
│   └── TopicoController.java
├── domain/
│   ├── curso/
│   │   ├── Curso.java
│   │   └── CursoRepository.java
│   ├── topico/
│   │   ├── DadosAtualizacaoTopico.java
│   │   ├── DadosCadastroTopico.java
│   │   ├── DadosDetalhamentoTopico.java
│   │   ├── DadosListagemTopico.java
│   │   ├── StatusTopico.java
│   │   ├── Topico.java
│   │   └── TopicoRepository.java
│   └── usuario/
│       ├── AutenticacaoService.java
│       ├── DadosAutenticacao.java
│       ├── Usuario.java
│       └── UsuarioRepository.java
└── infra/
    ├── DadosTokenJWT.java
    ├── SecurityConfigurations.java
    ├── SecurityFilter.java
    └── TokenService.java
```

---

## 🗄️ Diagrama do Banco de Dados

```
USUARIOS                    CURSOS
├── id (PK)                 ├── id (PK)
├── nome                    ├── nome
├── email (unique)          └── categoria
└── senha (BCrypt)

TOPICOS
├── id (PK)
├── titulo
├── mensagem
├── data_criacao
├── status (ABERTO/FECHADO/SOLUCIONADO)
├── autor_id (FK → USUARIOS)
└── curso_id (FK → CURSOS)

RESPOSTAS
├── id (PK)
├── mensagem
├── data_criacao
├── solucao (boolean)
├── autor_id (FK → USUARIOS)
└── topico_id (FK → TOPICOS)
```

---

## 🚀 Como Executar

### Pré-requisitos

- Java 17+
- Maven
- MySQL 8.0

### Configuração

1. **Clone o repositório:**
```bash
git clone https://github.com/seu-usuario/forumhub.git
cd forumhub
```

2. **Crie o banco de dados:**
```sql
CREATE DATABASE forumhub;
```

3. **Configure o `application.properties`** em `src/main/resources/`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/forumhub
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
api.security.token.secret=seu_secret_jwt
```

4. **Execute a aplicação:**
```bash
./mvnw spring-boot:run
```

A API estará disponível em `http://localhost:8080`.

---

## 📡 Endpoints da API

### Autenticação
| Método | Endpoint | Descrição | Auth |
|---|---|---|---|
| POST | `/login` | Autenticar usuário e obter token JWT | ❌ |

### Tópicos
| Método | Endpoint | Descrição | Auth |
|---|---|---|---|
| POST | `/topicos` | Criar novo tópico | ✅ |
| GET | `/topicos` | Listar tópicos (paginado, com filtros) | ✅ |
| GET | `/topicos/{id}` | Buscar tópico por ID | ✅ |
| PUT | `/topicos/{id}` | Atualizar tópico | ✅ |
| DELETE | `/topicos/{id}` | Deletar tópico | ✅ |

### Filtros disponíveis na listagem
| Parâmetro | Exemplo | Descrição |
|---|---|---|
| `nomeCurso` | `?nomeCurso=Spring Boot` | Filtra por nome do curso |
| `ano` | `?ano=2026` | Filtra por ano de criação |
| `size` | `?size=5` | Quantidade de itens por página |
| `page` | `?page=0` | Número da página |

---

## 🔐 Autenticação

A API utiliza autenticação **JWT (JSON Web Token)**. Para acessar os endpoints protegidos:

1. Faça uma requisição `POST /login` com suas credenciais:
```json
{
  "email": "usuario@email.com",
  "senha": "sua_senha"
}
```

2. Use o token retornado no header das próximas requisições:
```
Authorization: Bearer <seu_token>
```

