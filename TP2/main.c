// main.c - Programa unificado para Problemas A e B
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "arvore_a.h"
#include "arvore_b.h"
#include "io.h"

// ============== PROBLEMA A ==============
void lerRegistrosA(AVLTree *arv) {
    char linha[512];

    while (lerLinha(linha, sizeof(linha))) {
        if (strcmp(linha, "FIM") == 0)
            break;

        char *codigo = strtok(linha, ";");
        char *descricao = strtok(NULL, ";");
        char *estoqueStr = strtok(NULL, ";");
        char *precoStr = strtok(NULL, ";");

        if (!codigo || !descricao || !estoqueStr || !precoStr) {
            continue;
        }

        char *endptr;
        long estoque = strtol(estoqueStr, &endptr, 10);
        if (*endptr != '\0' || estoque < 0) {
            continue;
        }

        float preco = strtof(precoStr, &endptr);
        if (*endptr != '\0' || preco <= 0) {
            continue;
        }

        inserirAVL(arv, codigo, descricao, (int)estoque, preco);
    }
}

void lerOperacoesA(AVLTree *arv) {
    char linha[512];

    while (lerLinha(linha, sizeof(linha))) {
        if (strcmp(linha, "FIM") == 0)
            break;

        if (strncmp(linha, "INSERIR;", 8) == 0) {
            char *rest = linha + 8;

            char *codigo = strtok(rest, ";");
            char *descricao = strtok(NULL, ";");
            char *estoqueStr = strtok(NULL, ";");
            char *precoStr = strtok(NULL, ";");

            if (!codigo || !descricao || !estoqueStr || !precoStr) {
                printf("ERRO\n");
                continue;
            }

            char *end;
            long estoque = strtol(estoqueStr, &end, 10);
            if (*end != '\0' || estoque < 0) {
                printf("ERRO\n");
                continue;
            }

            float preco = strtof(precoStr, &end);
            if (*end != '\0' || preco <= 0) {
                printf("ERRO\n");
                continue;
            }

            int r = inserirAVL(arv, codigo, descricao, (int)estoque, preco);
            if (r == 1) printf("INSERIDO\n");
            else if (r == 2) printf("ATUALIZADO\n");
            else printf("ERRO\n");

            continue;
        }

        if (strncmp(linha, "BUSCAR;", 7) == 0) {
            char *codigo = linha + 7;

            NOA *no = buscarAVL(arv, codigo);

            if (!no) {
                printf("NAO_ENCONTRADO\n");
            } else {
                printf("%s;%s;%d;%.2f\n",
                       no->codigo, no->descricao,
                       no->estoque, no->preco);
            }

            continue;
        }

        if (strncmp(linha, "REMOVER;", 8) == 0) {
            char *codigo = linha + 8;

            int r = removerAVL(arv, codigo);
            if (r == 1) printf("REMOVIDO\n");
            else       printf("NAO_ENCONTRADO\n");

            continue;
        }

        if (strcmp(linha, "LISTAR") == 0) {
            listarAVL(arv);
            continue;
        }

        if (strncmp(linha, "INTERVALO;", 10) == 0) {
            char *rest = linha + 10;

            char *L = strtok(rest, ";");
            char *R = strtok(NULL, ";");

            if (!L || !R) {
                printf("ERRO\n");
                continue;
            }
            
            intervaloAVL(arv, L, R);
            continue;
        }

        if (strncmp(linha, "CARREGARCSV;", 12) == 0) {
            printf("ERRO\n");
            continue;
        }

        if (strncmp(linha, "SALVARCSV;", 10) == 0) {
            printf("ERRO\n");
            continue;
        }

        if (strcmp(linha, "ESTATISTICAS") == 0) {
            imprimirEstatisticasA(arv);
            continue;
        }
    }
}

void processarProblemaA(void) {
    AVLTree *arv = criarAVL();

    char linha[256];
    lerLinha(linha, sizeof(linha));

    lerRegistrosA(arv);

    lerLinha(linha, sizeof(linha));

    lerOperacoesA(arv);

    liberarAVL(arv);
}

// ============== PROBLEMA B ==============
void lerRegistrosB(ArvLLRB *raiz) {
    char linha[512];

    while (lerLinha(linha, sizeof(linha))) {
        if (strcmp(linha, "FIM") == 0)
            break;

        EventoB e;
        if (sscanf(linha, "%d;%60[^;];%lld;%lld",
                   &e.id, e.titulo, &e.inicio, &e.fim) == 4) {
            if (e.fim >= e.inicio) {
                inserirLLRB(raiz, e);
            }
        }
    }
}

void processarOperacaoB(char* linha, ArvLLRB* raiz) {
    if (strncmp(linha,"INSERIR;",8)==0) {
        char* rest = linha+8;
        char copia[512];
        strcpy(copia, rest);
        char* id_s = strtok(copia,";");
        char* titulo = strtok(NULL,";");
        char* inicio_s = strtok(NULL,";");
        char* fim_s = strtok(NULL,";");
        if (!id_s||!titulo||!inicio_s||!fim_s || strlen(titulo)>60) {
            printf("ERRO\n"); return;
        }
        long long ini = atoll(inicio_s);
        long long fim = atoll(fim_s);
        if (fim < ini) { printf("ERRO\n"); return; }

        EventoB e = {atoi(id_s),"",ini,fim};
        strcpy(e.titulo, titulo);

        int r = inserirLLRB(raiz,e);
        if (r==1) printf("INSERIDO\n");
        else if (r==2) printf("ATUALIZADO\n");
        else printf("ERRO\n");
        return;
    }

    if (strncmp(linha,"BUSCAR;",7)==0) {
        int id = atoi(linha+7);
        EventoB* e = buscarIDLLRB(*raiz,id);
        if (!e) printf("NAO_ENCONTRADO\n");
        else printf("%d;%s;%lld;%lld\n",e->id,e->titulo,e->inicio,e->fim);
        return;
    }

    if (strncmp(linha,"REMOVER;",8)==0) {
        int id = atoi(linha+8);
        EventoB* e = buscarIDLLRB(*raiz,id);
        if (!e) { printf("NAO_ENCONTRADO\n"); return; }
        removerLLRB(raiz,id);
        printf("REMOVIDO\n");
        return;
    }

    if (strcmp(linha,"LISTAR")==0) {
        listarLLRB(*raiz);
        return;
    }

    if (strncmp(linha,"INTERVALO;",10)==0) {
        char* rest = linha+10;
        char copia[512];
        strcpy(copia, rest);
        char* a = strtok(copia,";");
        char* b = strtok(NULL,";");
        if (!a||!b) { printf("ERRO\n"); return; }
        listarIntervaloLLRB(*raiz, atoll(a), atoll(b));
        return;
    }

    if (strncmp(linha,"PROXIMO;",8)==0) {
        long long T = atoll(linha+8);
        NOB* n = sucessorTemporalLLRB(*raiz,T);
        if (!n) printf("SEM_SUCESSOR\n");
        else printf("%d;%s;%lld;%lld\n",
                    n->dado.id,n->dado.titulo,n->dado.inicio,n->dado.fim);
        return;
    }

    if (strncmp(linha,"CARREGARCSV;",12)==0) {
        char* c = linha+12;
        if (carregarCSVLLRB(raiz,c)) printf("OK\n");
        else printf("ERRO\n");
        return;
    }

    if (strncmp(linha,"SALVARCSV;",10)==0) {
        char* c = linha+10;
        salvarCSVLLRB(*raiz,c);
        return;
    }

    if (strncmp(linha,"MESCLARCSV;",11)==0) {
        char* c = linha+11;
        if (mesclarCSVLLRB(raiz,c)) printf("OK\n");
        else printf("ERRO\n");
        return;
    }

    if (strcmp(linha,"ESTATISTICAS")==0) {
        imprimirEstatisticasB(*raiz);
        return;
    }
}

void lerOperacoesB(ArvLLRB *raiz) {
    char linha[1024];

    while (lerLinha(linha, sizeof(linha))) {
        if (strcmp(linha, "FIM") == 0)
            break;

        processarOperacaoB(linha, raiz);
    }
}

void processarProblemaB(void) {
    ArvLLRB raiz = NULL;

    char linha[256];
    lerLinha(linha, sizeof(linha));

    lerRegistrosB(&raiz);

    lerLinha(linha, sizeof(linha));

    lerOperacoesB(&raiz);

    liberarArveLLRB(raiz);
}

// ============== MAIN ==============
int main(void) {
    char linha[256];

    if (!lerLinha(linha, sizeof(linha)))
        return 0;

    if (strcmp(linha, "PROBLEMA=A") == 0) {
        processarProblemaA();
    } else if (strcmp(linha, "PROBLEMA=B") == 0) {
        processarProblemaB();
    }

    return 0;
}