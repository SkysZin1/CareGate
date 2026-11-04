# CareGate

Sistema de gestão de clínica desenvolvido em Java, com foco no cadastro e gerenciamento de médicos, pacientes e consultas.

## Sobre o projeto

O CareGate é um software simples de gestão para clínicas. A aplicação permite organizar dados essenciais de atendimento, mantendo registros de profissionais, pacientes e consultas por meio de uma interface de console e uma interface gráfica em Java Swing.

## Funcionalidades

- Cadastro, listagem e remoção de médicos.
- Cadastro, listagem e remoção de pacientes.
- Agendamento, cancelamento e listagem de consultas.
- Edição de médicos, pacientes e consultas.
- Busca e filtros nas tabelas da interface gráfica.
- Tela de detalhes para médicos, pacientes e consultas.
- Bloqueio de conflitos de agenda para médico e paciente no mesmo horário.
- Geração de relatório geral em `data/relatorio-caregate.txt`.
- Visualização do histórico de consultas por paciente.
- Persistência local dos dados em SQLite na pasta `data`.
- Organização dos médicos por tipos de atendimento, como clínico, cirurgião e odontólogo.
- Interface gráfica para dashboard, médicos, pacientes e consultas.

## Tecnologias utilizadas

- Java
- Java Swing
- SQLite
- FlatLaf
- Programação orientada a objetos
- Manipulação de arquivos
- Estruturas de dados como `ArrayList` e `HashMap`
- JDBC

## Estrutura do projeto

```text
CareGate/
|-- src/
|   |-- application/   # Inicialização, menu e execução do sistema
|   |-- entities/      # Classes principais do domínio da clínica
|   |-- gui/           # Interface gráfica em Java Swing
|   |-- repository/    # Acesso ao banco SQLite
|   |-- service/       # Regras de negócio e validações
|   `-- interfaces/    # Contratos usados por entidades do sistema
|-- data/              # Banco local SQLite gerado pela aplicação
|-- lib/               # Bibliotecas externas: SQLite JDBC e FlatLaf
|-- run.bat            # Script para compilar e executar a versão console
|-- run-gui.bat        # Script para compilar e executar a interface gráfica
`-- README.md
```

## Como executar

Requisito: JDK 17 ou superior, com java e javac disponiveis no PATH.

Antes de executar, a pasta `lib` precisa conter:

- `sqlite-jdbc.jar`, usado para conectar o sistema ao banco SQLite.
- `flatlaf.jar`, usado pelo visual moderno da interface grafica.

Para executar a versão com interface gráfica no Windows:

```bat
run-gui.bat
```

Os scripts ja iniciam o Java com `--enable-native-access=ALL-UNNAMED`, necessario para evitar avisos recentes do Java ao carregar o driver SQLite.

Se `data/caregate.db` nao existir, o sistema cria o banco e carrega a base inicial de exemplo uma unica vez.
Depois disso, alteracoes feitas pelo usuario sao preservadas. Se uma consulta de exemplo for removida, ela nao volta automaticamente ao abrir o sistema.

Login padrao da interface grafica:

```text
Usuario: admin
Senha: admin
```

Na interface gráfica, use:

- `Dashboard` para indicadores, agendamento rapido, relatorio e reset manual do banco de dados.
- `Medicos` para cadastrar, buscar, editar, detalhar e remover médicos.
- `Pacientes` para cadastrar, buscar, editar, detalhar e remover pacientes.
- `Consultas` para buscar, filtrar por data, remarcar, cancelar, detalhar e gerar relatório.

O botao `Resetar banco` apaga os dados atuais e recria a base de exemplo. Use apenas quando quiser voltar ao estado inicial do projeto.

Para executar a versão de console:

```bat
run.bat
```

Ou compile e execute manualmente:

```bat
javac -d build src\repository\*.java src\service\*.java src\application\*.java src\entities\*.java src\interfaces\*.java
java -cp "build;lib\*" application.Programa
```

## Componentes do grupo

- Daniel Carneiro
- Davi Chagas
- Gustavo Neves
- Miguel Carmo
