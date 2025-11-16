# Instruções de Compilação e Teste

## Compilação

```bash
gcc -O2 -std=c11 -Wall -Wextra -o tp main.c arvore_a.c arvore_b.c io.c
```

## Teste Problema A

**Comando cmd:**
```bash
./tp < exemplo_A.in > exemplo_A.out
```

**Comando PowerShell:**
```bash
Get-Content exemplo_A.in | .\tp.exe > exemplo_A.out
```

**Input (exemplo_A.in):**
```
PROBLEMA=A
REGISTROS
RES-10K-1/4W;Resistor 10K 1/4W;500;0.05
ESP32-DEVKIT;Placa ESP32 DevKit;25;39.90
CAB-USB-C;Cabo USB-C 1m;120;12.00
FIM
OPERACOES
BUSCAR;ESP32-DEVKIT
INSERIR;ESP32-DEVKIT;Placa ESP32 DevKit;30;39.90
INSERIR;CAB-MICRO-USB;Cabo Micro-USB 1m;80;9.50
INTERVALO;CAB-000;ESP999
REMOVER;RES-10K-1/4W
LISTAR
ESTATISTICAS
FIM
```

**Output esperado (exemplo_A.out):**
```
ESP32-DEVKIT;Placa ESP32 DevKit;25;39.90
ATUALIZADO
INSERIDO
CAB-MICRO-USB;Cabo Micro-USB 1m;80;9.50
CAB-USB-C;Cabo USB-C 1m;120;12.00
REMOVIDO
CAB-MICRO-USB;Cabo Micro-USB 1m;80;9.50
CAB-USB-C;Cabo USB-C 1m;120;12.00
ESP32-DEVKIT;Placa ESP32 DevKit;30;39.90
N=3; ALTURA=2; ROTACOES=1; COMP_BUSCA_MEDIA=2.00
```

## Teste Problema B

**Comando cmd:**
```bash
./tp < exemplo_B.in > exemplo_B.out
```

**Comando powershell:**
```bash
Get-Content exemplo_B.in | .\tp.exe > exemplo_B.out
```

**Input (exemplo_B.in):**
```
PROBLEMA=B
REGISTROS
101;Prova ED2;202504151300;202504151500
205;Apresentacao TP;202505021000;202505021100
309;Banca TCC;202506101400;202506101600
FIM
OPERACOES
PROXIMO;202505011200
INTERVALO;202504010000;202505312359
INSERIR;205;Apresentacao TP;202505021100;202505021200
REMOVER;101
LISTAR
ESTATISTICAS
FIM
```

**Output esperado (exemplo_B.out):**
```
205;Apresentacao TP;202505021000;202505021100
101;Prova ED2;202504151300;202504151500
205;Apresentacao TP;202505021000;202505021100
ATUALIZADO
REMOVIDO
205;Apresentacao TP;202505021100;202505021200
309;Banca TCC;202506101400;202506101600
N=2; ALTURA=2; ROTACOES=1; COMP_SUCESSOR_MEDIA=2.00
```

## Verificação de Vazamento de Memória (Valgrind)

```bash
valgrind --leak-check=full --show-leak-kinds=all ./tp < exemplo_A.in > /dev/null
valgrind --leak-check=full --show-leak-kinds=all ./tp < exemplo_B.in > /dev/null
```

## Estrutura de Arquivos

```
.
├── main.c           # Programa principal (orquestração)
├── arvore_a.h       # Header TAD AVL
├── arvore_a.c       # Implementação AVL
├── arvore_b.h       # Header TAD LLRB
├── arvore_b.c       # Implementação LLRB
├── io.h             # Header funções I/O
├── io.c             # Implementação I/O
├── exemplo_A.in     # Entrada Problema A
├── exemplo_B.in     # Entrada Problema B
└── tp               # Executável compilado
```

## Relatório - Estrutura Escolhida

**Problema A - AVL Tree:**
- Motivo: Muitas inserções em rajada + buscas frequentes + intervalo
- AVL mantém melhor O(log n) garantido
- Balanceamento automático durante inserção e remoção

**Problema B - Left-Leaning Red-Black Tree (LLRB):**
- Motivo: Muitas remoções + reprogramações + busca por sucessor
- LLRB mais eficiente para remoções
- Melhor para operações de intervalo temporal