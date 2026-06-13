# CareGate

Sistema de gestão de clínica desenvolvido em Java, com foco no cadastro e gerenciamento de médicos, pacientes e consultas.

## Sobre o projeto

O CareGate é um software simples de gestão para clínicas. A aplicação permite organizar dados essenciais de atendimento, mantendo registros de profissionais, pacientes e consultas por meio de uma interface de console.

## Funcionalidades

- Cadastro, listagem e remoção de médicos.
- Cadastro, listagem e remoção de pacientes.
- Agendamento, cancelamento e listagem de consultas.
- Visualização do histórico de consultas por paciente.
- Persistência local dos dados em arquivos na pasta `data`.
- Organização dos médicos por tipos de atendimento, como clínico, cirurgião e odontólogo.

## Tecnologias utilizadas

- Java
- Programação orientada a objetos
- Manipulação de arquivos
- Estruturas de dados como `ArrayList` e `HashMap`

## Estrutura do projeto

```text
CareGate/
|-- src/
|   |-- application/   # Inicialização, menu e execução do sistema
|   |-- entities/      # Classes principais do domínio da clínica
|   `-- interfaces/    # Contratos usados por entidades do sistema
|-- data/              # Arquivos locais usados para armazenar os dados
|-- Front/             # Protótipo inicial de interface web
|-- run.bat            # Script para compilar e executar no Windows
`-- README.md
```

## Como executar

No Windows, execute o arquivo:

```bat
run.bat
```

Ou compile e execute manualmente:

```bat
javac -d build src\application\*.java src\entities\*.java src\interfaces\*.java
java -cp build application.Programa
```

## Componentes do grupo

- Daniel Carneiro
- Davi Chagas
- Gustavo Neves
- Miguel Carmo
