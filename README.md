# Excel Employee Importer

API REST Spring Boot para importação em lote de dados de funcionários a partir de arquivos Excel (XLSX/XLS).

## 🎯 Sobre o projeto

Sistema que processa planilhas Excel contendo dados de funcionários e persiste as informações em banco de dados PostgreSQL. Inclui validação de entrada, conversão de tipos (especialmente `BigDecimal` para salários), tratamento centralizado de exceções com `@RestControllerAdvice` e persistência via Spring Data JPA.

## 🛠️ Tecnologias

- **Java 17+**
- **Spring Boot 3.x**
- **Spring Data JPA**
- **PostgreSQL**
- **Apache POI** (leitura de Excel)
- **Maven**
- **Jakarta Persistence API**

## 📋 Pré-requisitos

- JDK 17 ou superior
- Maven 3.8+
- PostgreSQL 12+
- Git

## 🚀 Instalação e configuração

### 1. Clonar o repositório

```bash
git clone https://github.com/andreluizsantana/excel-employee-importer.git
cd excel-employee-importer
```

### 2. Configurar banco de dados

Crie um banco de dados PostgreSQL:

```sql
CREATE DATABASE excel_importer;
```

### 3. Configurar `application.properties`

No arquivo `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/excel_importer
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.show-sql=false

server.port=8080
```

### 4. Instalar dependências e executar

```bash
mvn clean install
mvn spring-boot:run
```

A aplicação estará disponível em `http://localhost:8080`.

## 📁 Estrutura do projeto

```
src/main/java/com/example/lerArquivo/
├── controller/
│   └── ControllerLerArquivo.java      # Endpoints da API
├── service/
│   └── ServiceLerArquivo.java          # Lógica de importação
├── entity/
│   └── Funcionario.java                # Entidade JPA
├── repository/
│   └── RepositoryLerArquivo.java       # Data access layer
└── exception/
    ├── ArquivoInvalidoException.java   # Exceção customizada
    └── GlobalExceptionHandler.java     # Tratamento centralizado
```

## 🔌 Endpoints

### Importar arquivo Excel

**POST** `/api/importar/arquivoexcel`

**Request:**
- `Content-Type: multipart/form-data`
- Parâmetro: `file` (arquivo XLSX ou XLS)

**cURL:**
```bash
curl -X POST http://localhost:8080/api/importar/arquivoexcel \
  -F "file=@funcionarios.xlsx"
```

**Response de sucesso (200):**
```json
"Arquivo importado com sucesso."
```

**Response de erro (400):**
```json
"Erro de validação, arquivo inválido: Arquivo vazio"
```

## 📊 Formato esperado do Excel

A planilha deve ter as seguintes colunas (a partir da linha 2):

| Coluna | Campo | Tipo |
|--------|-------|------|
| A | ID | Ignorado (será gerado) |
| B | CPF | String |
| C | Nome | String |
| D | Telefone | String |
| E | Salário | BigDecimal (use ponto ou vírgula) |

**Exemplo:**
```
ID    | CPF           | Nome              | Telefone      | Salário
      | 123.456.789-0 | João Silva        | (83) 98765-4321 | 3.500,00
      | 987.654.321-0 | Maria Santos      | (83) 99876-5432 | 4.200,50
```

## ⚙️ Recursos principais

✅ Suporte para arquivos XLSX e XLS  
✅ Validação de dados de entrada  
✅ Conversão automática de tipos (BigDecimal, String)  
✅ Tratamento robusto de exceções  
✅ Persistência em banco de dados com JPA/Hibernate  
✅ Logs estruturados  
✅ Transações atômicas  

## 🔍 Tratamento de erros

A API trata os seguintes cenários:

| Erro | Status | Mensagem |
|------|--------|----------|
| Arquivo vazio | 400 | `Arquivo vazio` |
| Formato não suportado | 400 | `Formato inválido` |
| Arquivo corrompido | 400 | `Erro ao ler o arquivo` |
| Salário inválido | 400 | `Erro ao converter salário na linha X` |
| Erro interno | 500 | `Erro interno` |

## 📝 Exemplos práticos

### Importar funcionários via Postman

1. Abra **Postman**
2. Crie um novo request **POST**
3. URL: `http://localhost:8080/api/importar/arquivoexcel`
4. Aba **Body**:
   - Selecione **form-data**
   - Key: `file` (tipo: File)
   - Value: selecione seu arquivo Excel
5. Envie o request

### Via linha de comando (Windows PowerShell)

```powershell
$uri = "http://localhost:8080/api/importar/arquivoexcel"
$filePath = "C:\Users\seu_usuario\Downloads\funcionarios.xlsx"

$form = @{
    file = Get-Item -Path $filePath
}

Invoke-WebRequest -Uri $uri -Method Post -Form $form
```

## 🏗️ Próximos passos

- [ ] Adicionar validação de CPF (Modulo 11)
- [ ] Implementar suporte para importação de CSV
- [ ] Adicionar endpoint para consultar funcionários importados
- [ ] Implementar paginação nos resultados
- [ ] Adicionar logs estruturados com SLF4J
- [ ] Cobertura de testes com JUnit 5

## 📚 Referências

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [Apache POI](https://poi.apache.org/)
- [PostgreSQL](https://www.postgresql.org/)

## 👤 Autor

André Luiz Santana  
GitHub: [@andreluizsantana](https://github.com/andreluizsantana)
