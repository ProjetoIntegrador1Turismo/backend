
![Logo](https://i.imgur.com/TsUF38y.png)
#

| [![documentacao](https://img.shields.io/badge/Documento%20de%20Requisitos-5EFC8D)](https://docs.google.com/document/d/1s4IiIkMch77QEb4AsXgQft0SF_Wt4L-HIttrgpllwz4/edit)               | [![der](https://img.shields.io/badge/Diagrama%20Entidade%20Relacionamento-8377D1)](https://docs.google.com/document/d/1Jlap-J_DCGzz2mSHkwiluHYFC-glpN_yMfCTaZC9zcQ/edit)  | [![prototipos](https://img.shields.io/badge/Prototipos-8EF9F3)](https://docs.google.com/document/d/1cns8ZQx-IloOT37II7HL7QG5QmlmkImn__JLi6Oy2OA/edit)             | [![uso](https://img.shields.io/badge/Caso%20de%20Uso%20Geral-DB8A74)](https://docs.google.com/document/d/12tASeT8vkA-c-te8HTjTGsqxc5C2NsNzPSVHRCxbuq0/edit)               |
| ----------------- | ----------- | --------------- | ----------------- |

Este repositório contém o código fonte da API desenvolvida para atuar como servidor de recursos da aplicação Tourlink.

Referida aplicação foi apresentada como parte do trabalho final de conclusão de curso de análise e desenvolvimento de sistemas, junto ao Campus do IFPR em Foz do Iguaçu/PR.
## Stack utilizada

Spring Boot, Docker, Keyclock, Mysql


## Rodando localmente

Tenha certeza que o ambiente onde a aplicação será executada conta com [JDK](https://www.oracle.com/br/java/technologies/downloads/), [Maven](https://maven.apache.org/) e [Docker](https://www.docker.com/).

Neste tutorial será utilizado o [Intellij](https://www.jetbrains.com/idea/download/?section=windows) para execução do projeto, que dispensa a instalação do Maven e torna mais intuitíva a execução da aplicação.

#### 1) Dependências

Crie os containers necessários para a inicialização da aplicação.

- Banco de dados
  Execute no terminal o seguinte comando:

```bash
  docker run --name roteirodb \
-p 3307:3306 \
-e MYSQL_ROOT_PASSWORD=admin \
-e MYSQL_DATABASE=roteiro \
-e MYSQL_USER=projeto \
-e MYSQL_PASSWORD=12345 \
-d mysql:latest
```

- Keyclock

Faça o download do container do [Keyclock](https://drive.google.com/drive/folders/1nZfLNxesISv-DR6LQ6r0s8YHdb4t7y_e) criado e configurado para a API. Abra no terminal a pasta onde o arquivo da imagem foi baixado e execute o comando:

```bash
docker load -i keycloaktemaroteiro.tar
```

Tenha certeza que ambos os containers estão ativos:
```bash
docker ps
```

#### 2) Projeto

Abra o [Intellij](https://www.jetbrains.com/idea/download/?section=windows) e selecione no menu superior `File > New > Project From Version Control`.

Cole o link do repositório e selecione o botão `Clone`

Após verificar que as dependencias já foram baixadas - processo que ocorre automaticamente - inicialize a aplicação clicando no botão `Run` no canto superior direito.


## Autores

- [@DocAnder](https://github.com/DocAnder)
- [@bernardotonin](https://github.com/bernardotonin)
- [@guilhermelaz](https://github.com/guilhermelaz)


## Relacionados

Repositório do
[Front-end](https://github.com/ProjetoIntegrador1Turismo/frontend)



