# **Diretrizes para Spring Boot**

## **1. Prefira Injeção por Construtor ao invés de Injeção por Campo/Setter**

* Declare todas as dependências obrigatórias como campos `final` e injete-as através do construtor.
* O Spring detecta automaticamente quando existe apenas um construtor — não é necessário usar `@Autowired`.
* Evite injeção por campo ou setter em código de produção.

---

## **2. Prefira visibilidade *package-private* ao invés de *public* para componentes Spring**

* Controllers, métodos de requisição, classes `@Configuration`, serviços e beans devem usar visibilidade padrão **sempre que possível**.
* Não torne classes públicas sem necessidade.

---

## **3. Organize Configurações com Propriedades Tipadas**

* Agrupe propriedades com prefixos no `application.yml/properties`.
* Utilize `@ConfigurationProperties` + validação para falhar rápido.
* Prefira **variáveis de ambiente** para diferenças entre ambientes (dev/homolog/prod).

---

## **4. Defina Fronteiras Claras de Transação**

* Cada método da camada de serviço representa uma unidade transacional.
* Utilize:

    * `@Transactional(readOnly = true)` → consultas
    * `@Transactional` → escrita/alteração
* Mantenha a menor quantidade possível de lógica dentro da transação.

---

## **5. Desative o Padrão Open Session in View**

Configure:

```
spring.jpa.open-in-view=false
```

Isso evita N+1 e traz mais clareza ao ciclo transacional.

---

## **6. Separe a Camada Web da Camada de Persistência**

* Nunca exponha **entidades JPA** em controllers.
* Utilize DTOs para entrada e saída.
* Use MapStruct para conversão.
* Valide entradas com **Jakarta Validation**.

---

## **7. Siga Princípios de Design REST**

* Versionamento obrigatório: `/api/v1/...`
* URLs baseadas em recursos.
* Utilize `ResponseEntity<T>` sempre com tipo explícito.
* JSON sempre começa como **objeto**, não array.
* Use camelCase consistentemente.
* Utilize paginação para listas volumosas.

---

## **8. Utilize Objetos de DTO**

* Para operações de negócio, utilize records como:

    * `CriarBloqueioDTO`
    * `AtualizarClienteDTO`
* Service recebe DTOs ao invés de parâmetros soltos.

---

## **9. Centralize Exception Handling**

* Utilize `@RestControllerAdvice`.
* Forneça respostas de erro padronizadas.
* Considere usar o padrão **ProblemDetails (RFC 9457)**.

---

## **10. Actuator**

* Exponha publicamente apenas:

    * `/health`
    * `/info`
    * `/metrics`
* Demais endpoints devem ser protegidos.

---

## **11. Logging**

* Utilize SLF4J + Logback/Log4j2.
* Proíba `System.out.println`.
* Evite logs com dados sensíveis.
* Utilize guard clauses para logs pesados:

```java
if (logger.isDebugEnabled()) {
    logger.debug("Estado: {}", calculoPesado());
}
```

---

# **Diretrizes Especificas**
## **12. Nomenclatura e Escrita**
* Métodos em Ingles.
* Atributos em Ingles.
* Classes em Ingles, usando CamelCase.
* Sufixos obrigatórios:

    * `NomeDaClasseService`
    * `NomeDaClasseController`
    * `NomeDaClasseRepository`

**Exemplos:**
* Request: `NomeRecursoRequest`
* Response: `NomeRecursoResponse`
* DTO: `NomeRecursoDto`
* Controller: `NomeRecursoController`
* Service: `NomeRecursoService`
* Repository: `NomeRecursoRepository`
* Mapper: `NomeRecursoMapper`

---

## **13 . Mensagens e Constantes**
* **Todas** as mensagens devem ser em português.
* Mensagens devem estar em variáveis `private static final`.
* Nada de strings "soltas" espalhadas pelo código.


---

## **14. Boas Práticas**
* Todos os atributos devem ser `private`.
* Siga SOLID.
* Aplique Clean Code.
* Os métodos e funções devem executar uma ação clara e bem definida, e isso deve ser refletido no seu nome,
    que deve começar por um verbo, nunca um substantivo.
* Sempre que possível, evite passar mais de 3 parâmetros, dê preferência para o uso de objetos caso
  necessário.
* Nunca faça o aninhamento de mais de dois if/else, sempre dê preferência por early returns.
* Evite métodos longos, com mais de 50 linhas.
* Evite classes longas, com mais de 300 linhas.

---

## **15. Regras de Implementacao**
A IA deve:

* Criar **endpoint REST** para cada caso de uso que for definido como um recurso na API.
* Criar **Swagger/OpenAPI** automaticamente
* Criar **testes unitários**
* Retornar mensagens em português
* Jamais retornar entidades nos controllers
* Jamais criar variáveis ou métodos em portugues


## **16. Diretrizes Específicas**

### **16.1 Tecnologias**
* MapStruct para conversão
* Java 21
* Testes usando Mockito + JUnit 5
* Usar **JPA** em persistência
* Criar logs organizados com SLF4J
* A aplicacao deve ser conteinerizada.
* Utilize um banco de dados relacional (H2).

### **16.2. Padrões de Arquitetura**
A IA deve **sempre** gerar código seguindo:
* Controller → Service → Repository
* DTOs para request e response

---

### **16.3. Estrutura de Pastas**

```plaintext
├── src/
│   ├── controller/         # Controllers REST
│   ├── model/              # Entidades, VOs
│   ├── service/            # Regras de negócio
│   ├── repository/         # Interfaces JPA
│   ├── infrastructure/     # Kafka, DB, HTTP Clients
│   └── config/             # Beans, DI
│
└── test/
    ├── unit/               # Testes de unidade
```
---

### **16.4. Padrão de Testes Unitarios**

* Testar validação de request
* Testar fluxos de sucesso
* Testar fluxos de erro/exceção
---

### **16.5. Entrada e saída dos endpoints REST**
A IA deve gerar endpoints que:

* Sempre retornem `ResponseEntity<T>`
* Sempre versionem a API
* Sempre usem DTO de request
* Sempre usem DTO de response

---

### **16.6. Convenções obrigatórias**

* Proibido:
    * Usar entidades no request/response
    * Usar `System.out.println`
    * Criar métodos em portugues
    * Criar variáveis em portugues
    * Criar classes sem sufixo/padrão
---