# **Sistema de Gestão de Locação de Equipamentos - Construtec**

## **Descrição**
Este projeto é um sistema de gestão para locação de equipamentos voltado à empresa **Construtec**, que permite:
- Cadastro de equipamentos.
- Registro de locações associadas a clientes.
- Devolução de equipamentos com cálculo de multas por atraso.
- Geração de relatórios de equipamentos mais alugados e clientes com multas acumuladas.

O sistema foi desenvolvido utilizando **Java** com interface gráfica (Swing) e segue os princípios da Programação Orientada a Objetos (POO). 

---

## **Requisitos**

### **Software**
- **Java JDK** 11 ou superior.
- IDE recomendada: **NetBeans** 12+.
- **Apache Maven** para gerenciamento de dependências.

### **Dependências**
Este projeto utiliza as seguintes bibliotecas:
- **JUnit 5** para testes unitários.

Certifique-se de que todas as dependências estão instaladas e configuradas corretamente no ambiente de desenvolvimento.

---

## **Estrutura do Projeto**

```plaintext
/src
    /controller
        Gestor.java           # Controlador principal do sistema
    /model
        Cliente.java          # Modelo para os clientes
        Equipamento.java      # Modelo para os equipamentos
        Locacao.java          # Modelo para as locações
        Devolucao.java        # Modelo para devoluções
    /service
        Relatorio.java        # Geração de relatórios
    /gui
        SistemaConstrutecGUI.java # Interface gráfica principal
/tests
    GestorTest.java           # Testes para o controlador Gestor
    LocacaoTest.java          # Testes para o modelo Locacao
    RelatorioTest.java        # Testes para a classe Relatorio
README.md                    # Instruções de uso
pom.xml                      # Configuração do Maven
```

---

## **Como Usar**

### **1. Clonar o Repositório**

```bash
git clone <URL_DO_REPOSITORIO>
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

Ou diretamente pela IDE (NetBeans):

1. Abra o projeto no NetBeans.
2. Localize o arquivo SistemaConstrutecGUI.
3. Clique com o botão direito e selecione Run File.

---

## **Funcionalidades**

### **Cadastro de Equipamentos**

```plaintext
- Preencha os campos **Nome**, **Descrição**, **Valor Diário** e **Quantidade**.
- Clique em **Salvar** para adicionar o equipamento.
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

```plaintext
Este projeto é individual. Para qualquer dúvida ou sugestão, entre em contato com **Saul de Souza Duarte**.
```