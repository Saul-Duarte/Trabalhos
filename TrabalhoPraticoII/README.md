# **Sistema de Gestão de Locação de Equipamentos - Construtec**

## **Descrição**
Este projeto é um sistema de gestão para bibliotecas, que permite:
- Cadastro e exclusão de equipamentos.
- Registro de clientes e gerenciamento de locações.
- Devolução de equipamentos com cálculo de multas por atraso.
- Geração de relatórios de equipamentos mais alugados e clientes com pendências.

O sistema foi desenvolvido utilizando **Java** e segue os princípios da Programação Orientada a Objetos (POO).

---

## **Requisitos**

### **Software**
- **Java JDK** 17 ou superior.
- IDE recomendada: **NetBeans** ou **IntelliJ IDEA**.
- **Apache Maven** para gerenciamento de dependências.

### **Dependências**
Este projeto utiliza as seguintes bibliotecas:
- **JUnit 5** para testes unitários.
- **MySQL Connector** para integração com banco de dados.

Certifique-se de que todas as dependências estão instaladas e configuradas corretamente no ambiente de desenvolvimento.

---

## **Estrutura do Projeto**

```plaintext
/src
    /controller
        ConexaoMySQL.java  # Controlador principal do sistema
    /model
        Cliente.java                # Modelo para os clientes
        Equipamento.java              # Modelo para os equipamentos
        Locacao.java           # Modelo para as locações
    /gui
        SistemaConstrutecGUI.java       # Interface gráfica principal
    /service
        ClienteMulta.java            # Gerenciamento de multas de clientes
        EquipamentoRelatorio.java            # Geração de relatórios para equipamento
        Relatorio.java            # Geração de relatórios
/tests
    ClienteTest.java            # Testes para a classe Cliente
    EquipamentoTest.java           # Testes para a classe Equipamento
    LocacaoTest.java               # Testes para a classe Locacao
    RelatorioTest.java            # Testes para a classe Relatorio
README.md                        # Instruções de uso
pom.xml                          # Configuração do Maven
```

---

## **Como Usar**

### **1. Clonar o Repositório**

```bash
git clone <https://github.com/Saul-Duarte/Trabalhos.git>
cd <PASTA_DO_REPOSITORIO>
```

### **2. Compilar o Projeto**

Se estiver usando Maven:

```bash
mvn clean compile
```

### **3. Executar o Sistema**

Execute o arquivo principal:

```bash
mvn exec:java -Dexec.mainClass="gui.SistemaConstrutecGUI"
```

Ou diretamente pela IDE (NetBeans ou IntelliJ IDEA):

1. Abra o projeto na IDE.
2. Localize o arquivo `SistemaConstrutec.java`.
3. Clique com o botão direito e selecione **Run File**.

---

## **Funcionalidades**

### **Cadastro de Equipamentos**

```plaintext
- Preencha os campos **Nome**, **Descrição**, **Valor Diário** e **Quantidade**.
- Clique em **Salvar** para adicionar o equipamento.
- O botão **Cancelar** limpa os campos nas telas que ele estiver presente.
```

### **Registro de Locações**

```plaintext
- Selecione um equipamento disponível no dropdown.
- Preencha os dados do cliente (**Nome**, **CPF**, **Telefone**) e as **datas** de locação.
- Clique em **Registrar Locação** para confirmar.
```

### **Devolução de Equipamentos**

```plaintext
- Insira o CPF ou Código da Locação.
- Clique em **Confirmar Devolução** para exibir os detalhes e finalizar.
```

### **Geração de Relatórios**

```plaintext
- Selecione o relatório desejado:
  - **Equipamentos Mais Alugados**.
  - **Clientes com Multas Acumuladas**.
- Os resultados serão exibidos no campo de texto.
```

### **Edição de Dados**

```plaintext
- Selecione os dados desejados:
  - **Editar Equipamentos**.
  - **Editar Clientes**.
  - **Editar Locações**.
- Os resultados serão exibidos no campo de texto.
- Para alterar: Insira os dados que deseja alterar nos campos correspondentes, selecione a linha que deseja alterar e clique em **Alterar**.
- Para excluir: selecione a linha que deseja excluir e clique em **Excluir**.
```

---

## **Testes Unitários**

### **Executar os Testes**

```bash
mvn test
```

Os testes cobrem:

```plaintext
- Cadastro de equipamentos.
- Registro de locações.
- Cálculo de multas.
- Geração de relatórios.
```

---

## **Contribuições**

O desenvolvimento deste projeto contou com a participação de:
- **Saul de Souza Duarte**
- **Sergio Vinícius Santos**
- **Pedro Vinícius Marçal Naves Queiroz**

### **Para qualquer dúvidas ou sugestões de melhorias, entre em contato.**
 
Via e-mail: 
- **saul.duarte@estudante.ufla.br**
- **sergio.santos1@estudante.ufla.br**
- **pedro.naves4@estudante.ufla.br**
 
Via GitHub:
- **Saul-Duarte**
- **SergioSantos01**
- **Pedro-viniicius** ou **Pedro-naves**