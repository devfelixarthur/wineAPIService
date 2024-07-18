# 🍷 WineService API

## 📝 Descrição

WineService é uma API destinada a alimentar uma rede social de vinhos, oferecendo funcionalidades como cadastro de usuários, gerenciamento de vinhos (cadastro, edição e inativação), além de permitir a avaliação e comentários sobre os vinhos cadastrados. Desenvolvida em Java 17, esta API utiliza Spring Boot e JPA, garantindo robustez e escalabilidade para o serviço.

## 📖 Documentação da API

A documentação completa da API está disponível no Swagger: [Visualizar Documentação.](https://wineapiservice.onrender.com/swagger-ui/index.html?urls.primaryName=User+Operations#/)
Obs: Para acessar a documentação local a API deve estar rodando e ser acessada [neste link.](http://localhost:8080/swagger-ui/index.html?urls.primaryName=User+Operations#/)

## 🚀 Pré-requisitos

Para executar este projeto localmente, é necessário ter o seguinte instalado:

- Java 17
- Maven 3.6 ou superior

## ⚙️ Configuração e Instalação

### 🔽 Clonando o Repositório

Para obter uma cópia local, siga estas etapas simples:

```bash
git clone https://github.com/seuusuario/WineService.git
cd WineService
```

🛠 Configuração do Ambiente
Se desejar rodar a aplicação localmente, você deve alterar as configurações no arquivo host.properties para apontar para seu ambiente local. Por padrão, a configuração está preparada para usar o ambiente de produção no Render.


### 📦 Instalação das Dependências

Use o Maven para instalar as dependências do projeto:

```bash
mvn clean install
```

Para iniciar a aplicação, execute:

```bash
mvn spring-boot:run
```

A API estará acessível via http://localhost:8080/.


## 💻 Tecnologias Utilizadas

- **Java 17** ☕: Versão atualizada do Java, oferecendo as mais recentes melhorias em performance e segurança.
- **Spring Boot** 🌱: Facilita a criação de aplicações stand-alone baseadas em Spring com mínimo esforço de configuração.
- **Spring Data JPA** 📚: Simplifica o acesso a dados em bancos de dados através do JPA.
- **Spring Security** 🔐: Fornece autenticação e proteção para aplicações baseadas em Spring.
- **PostgreSQL** 🐘: Banco de dados robusto e confiável.
- **Hibernate** 🌿: Framework para mapeamento objeto-relacional que facilita a integração entre Java e bancos de dados.
- **Lombok** 🧰: Biblioteca Java que usa anotações para minimizar o código boilerplate.


## 📬 Contato

Arthur Felix - dev.felixarthur@gmail.com

Projeto Link: [https://github.com/devfelixarthur/wineAPIService](https://github.com/devfelixarthur/wineAPIService)
