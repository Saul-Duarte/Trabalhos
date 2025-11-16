// arvore_a.c - Implementação AVL Tree para Problema A
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "arvore_a.h"

char *my_strdup(const char *s) {
    char *p = malloc(strlen(s) + 1);
    if (!p) return NULL;
    strcpy(p, s);
    return p;
}

AVLTree* criarAVL(void) {
    AVLTree *arv = (AVLTree*) malloc(sizeof(AVLTree));
    if (!arv) return NULL;
    arv->raiz = NULL;
    arv->N = 0;
    arv->rotacoes = 0;
    arv->comp_busca = 0;
    arv->num_buscas = 0;
    return arv;
}

NOA* criarNo(char *codigo, char *descricao, int estoque, float preco) {
    NOA *no = (NOA*) malloc(sizeof(NOA));
    if (!no) return NULL;
    no->codigo = my_strdup(codigo);
    no->descricao = my_strdup(descricao);
    no->estoque = estoque;
    no->preco = preco;
    no->altura = 0;
    no->esq = NULL;
    no->dir = NULL;
    return no;
}

int alturaNo(NOA *no) {
    if (no == NULL) return -1;
    return no->altura;
}

int fatorBalanceamento(NOA *no) {
    return alturaNo(no->esq) - alturaNo(no->dir);
}

void atualizarAltura(NOA *no) {
    int h_esq = alturaNo(no->esq);
    int h_dir = alturaNo(no->dir);
    no->altura = (h_esq > h_dir ? h_esq : h_dir) + 1;
}

NOA* rotacaoLL(NOA *no, AVLTree *arv) {
    NOA *p = no->esq;
    no->esq = p->dir;
    p->dir = no;
    atualizarAltura(no);
    atualizarAltura(p);
    arv->rotacoes++;
    return p;
}

NOA* rotacaoRR(NOA *no, AVLTree *arv) {
    NOA *p = no->dir;
    no->dir = p->esq;
    p->esq = no;
    atualizarAltura(no);
    atualizarAltura(p);
    arv->rotacoes++;
    return p;
}

NOA* rotacaoLR(NOA *no, AVLTree *arv) {
    no->esq = rotacaoRR(no->esq, arv);
    return rotacaoLL(no, arv);
}

NOA* rotacaoRL(NOA *no, AVLTree *arv) {
    no->dir = rotacaoLL(no->dir, arv);
    return rotacaoRR(no, arv);
}

NOA* inserirRec(NOA *no, AVLTree *arv, char *codigo, char *descricao, int estoque, float preco, int *resultado) {
    if (no == NULL) {
        NOA *novo = criarNo(codigo, descricao, estoque, preco);
        if (!novo) {
            *resultado = 0;
            return NULL;
        }
        *resultado = 1;
        arv->N++;
        return novo;
    }

    int cmp = strcmp(codigo, no->codigo);

    if (cmp == 0) {
        free(no->descricao);
        no->descricao = my_strdup(descricao);
        no->estoque = estoque;
        no->preco = preco;
        *resultado = 2;
        return no;
    }
    else if (cmp < 0) {
        no->esq = inserirRec(no->esq, arv, codigo, descricao, estoque, preco, resultado);
    }
    else {
        no->dir = inserirRec(no->dir, arv, codigo, descricao, estoque, preco, resultado);
    }

    atualizarAltura(no);

    int fb = fatorBalanceamento(no);

    if (fb >= 2 && strcmp(codigo, no->esq->codigo) < 0) {
        return rotacaoLL(no, arv);
    }

    if (fb >= 2 && strcmp(codigo, no->esq->codigo) > 0) {
        return rotacaoLR(no, arv);
    }

    if (fb <= -2 && strcmp(codigo, no->dir->codigo) > 0) {
        return rotacaoRR(no, arv);
    }

    if (fb <= -2 && strcmp(codigo, no->dir->codigo) < 0) {
        return rotacaoRL(no, arv);
    }

    return no;
}

int inserirAVL(AVLTree *arv, char *codigo, char *descricao, int estoque, float preco) {
    int res = 0;
    arv->raiz = inserirRec(arv->raiz, arv, codigo, descricao, estoque, preco, &res);
    return res;
}

NOA* buscarRec(NOA *no, const char *codigo, AVLTree *arv) {
    if (no == NULL)
        return NULL;

    arv->comp_busca++;
    int cmp = strcmp(codigo, no->codigo);

    if (cmp == 0)
        return no;

    if (cmp < 0)
        return buscarRec(no->esq, codigo, arv);
    else
        return buscarRec(no->dir, codigo, arv);
}

NOA* buscarAVL(AVLTree *arv, const char *codigo) {
    arv->num_buscas++;
    return buscarRec(arv->raiz, codigo, arv);
}

void listarRec(NOA *no) {
    if (no == NULL)
        return;

    listarRec(no->esq);

    printf("%s;%s;%d;%.2f\n", no->codigo, no->descricao, no->estoque, no->preco);

    listarRec(no->dir);
}

void listarAVL(AVLTree *arv) {
    listarRec(arv->raiz);
}

void intervaloRec(NOA *no, const char *L, const char *R) {
    if (no == NULL)
        return;

    if (strcmp(no->codigo, L) >= 0)
        intervaloRec(no->esq, L, R);

    if (strcmp(no->codigo, L) >= 0 && strcmp(no->codigo, R) <= 0) {
        printf("%s;%s;%d;%.2f\n", no->codigo, no->descricao, no->estoque, no->preco);
    }

    if (strcmp(no->codigo, R) <= 0)
        intervaloRec(no->dir, L, R);
}

void intervaloAVL(AVLTree *arv, const char *L, const char *R) {
    intervaloRec(arv->raiz, L, R);
}

NOA* menorNo(NOA *no) {
    while (no->esq != NULL)
        no = no->esq;
    return no;
}

void liberarNo(NOA *no) {
    if (!no) return;
    free(no->codigo);
    free(no->descricao);
    free(no);
}

NOA* removerRec(NOA *no, AVLTree *arv, const char *codigo, int *resultado) {
    if (no == NULL) {
        *resultado = 0;
        return NULL;
    }

    int cmp = strcmp(codigo, no->codigo);

    if (cmp < 0) {
        no->esq = removerRec(no->esq, arv, codigo, resultado);
    }
    else if (cmp > 0) {
        no->dir = removerRec(no->dir, arv, codigo, resultado);
    }
    else {
        *resultado = 1;
        arv->N--;

        if (no->esq == NULL && no->dir == NULL) {
            liberarNo(no);
            return NULL;
        }

        if (no->esq == NULL) {
            NOA *temp = no->dir;
            liberarNo(no);
            return temp;
        }

        if (no->dir == NULL) {
            NOA *temp = no->esq;
            liberarNo(no);
            return temp;
        }

        NOA *sucessor = menorNo(no->dir);

        free(no->codigo);
        free(no->descricao);

        no->codigo = my_strdup(sucessor->codigo);
        no->descricao = my_strdup(sucessor->descricao);
        no->estoque = sucessor->estoque;
        no->preco = sucessor->preco;

        no->dir = removerRec(no->dir, arv, sucessor->codigo, resultado);
    }

    atualizarAltura(no);

    int fb = fatorBalanceamento(no);

    if (fb >= 2 && fatorBalanceamento(no->esq) >= 0)
        return rotacaoLL(no, arv);

    if (fb >= 2 && fatorBalanceamento(no->esq) < 0)
        return rotacaoLR(no, arv);

    if (fb <= -2 && fatorBalanceamento(no->dir) <= 0)
        return rotacaoRR(no, arv);

    if (fb <= -2 && fatorBalanceamento(no->dir) > 0)
        return rotacaoRL(no, arv);

    return no;
}

int removerAVL(AVLTree *arv, const char *codigo) {
    int res = 0;
    arv->raiz = removerRec(arv->raiz, arv, codigo, &res);
    return res;
}

int alturaArvore(AVLTree *arv) {
    if (arv == NULL || arv->raiz == NULL) return 0;
    return arv->raiz->altura + 1;
}

void imprimirEstatisticasA(AVLTree *arv) {
    int n = (arv ? arv->N : 0);
    int altura = alturaArvore(arv);
    int rot = (arv ? arv->rotacoes : 0);

    double media = 0.0;
    if (arv && arv->num_buscas > 0) {
        media = (double) arv->comp_busca / (double) arv->num_buscas;
    }

    printf("N=%d; ALTURA=%d; ROTACOES=%d; COMP_BUSCA_MEDIA=%.2f\n", n, altura, rot, media);
}

void liberarArvoreRec(NOA *no) {
    if (no == NULL)
        return;

    liberarArvoreRec(no->esq);
    liberarArvoreRec(no->dir);

    free(no->codigo);
    free(no->descricao);
    free(no);
}

void liberarAVL(AVLTree *arv) {
    if (!arv) return;

    liberarArvoreRec(arv->raiz);

    free(arv);
}