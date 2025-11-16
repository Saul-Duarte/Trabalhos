// arvore_b.c - Implementação Left-Leaning Red-Black Tree para Problema B
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include "arvore_b.h"

#define RED 1
#define BLACK 0

long long total_rotacoes_b = 0;
long long total_comparacoes_sucessor = 0;
long long qtd_sucessor = 0;

int cor(NOB* h) {
    return h ? h->cor : BLACK;
}

void trocaCor(NOB* h) {
    h->cor = !h->cor;
    if (h->esq) h->esq->cor = !h->esq->cor;
    if (h->dir) h->dir->cor = !h->dir->cor;
}

NOB* rotacionaEsquerda(NOB* h) {
    total_rotacoes_b++;
    NOB* x = h->dir;
    h->dir = x->esq;
    x->esq = h;
    x->cor = h->cor;
    h->cor = RED;
    return x;
}

NOB* rotacionaDireita(NOB* h) {
    total_rotacoes_b++;
    NOB* x = h->esq;
    h->esq = x->dir;
    x->dir = h;
    x->cor = h->cor;
    h->cor = RED;
    return x;
}

NOB* balancear(NOB* h) {
    if (cor(h->dir) == RED && cor(h->esq) == BLACK)
        h = rotacionaEsquerda(h);

    if (cor(h->esq) == RED && cor(h->esq->esq) == RED)
        h = rotacionaDireita(h);

    if (cor(h->esq) == RED && cor(h->dir) == RED)
        trocaCor(h);

    return h;
}

ArvLLRB criarArvLLRB(void) {
    return NULL;
}

NOB* insereNO(NOB* h, EventoB e, int* resp) {
    if (!h) {
        NOB* novo = (NOB*)malloc(sizeof(NOB));
        novo->dado = e;
        novo->esq = novo->dir = NULL;
        novo->cor = RED;
        *resp = 1;
        return novo;
    }

    if (e.id == h->dado.id) {
        h->dado = e;
        *resp = 2;
        return h;
    }

    if (e.id < h->dado.id)
        h->esq = insereNO(h->esq, e, resp);
    else
        h->dir = insereNO(h->dir, e, resp);

    h = balancear(h);
    return h;
}

int inserirLLRB(ArvLLRB* raiz, EventoB e) {
    int r = 0;
    *raiz = insereNO(*raiz, e, &r);
    if (*raiz)
        (*raiz)->cor = BLACK;
    return r;
}

EventoB* buscarIDLLRB(ArvLLRB h, int id) {
    while (h) {
        if (id == h->dado.id) return &h->dado;
        if (id < h->dado.id) h = h->esq;
        else                 h = h->dir;
    }
    return NULL;
}

void listarLLRB(ArvLLRB h) {
    if (!h) return;
    listarLLRB(h->esq);
    printf("%d;%s;%lld;%lld\n", h->dado.id, h->dado.titulo, h->dado.inicio, h->dado.fim);
    listarLLRB(h->dir);
}

void listarIntervaloLLRB(ArvLLRB h, long long Ta, long long Tb) {
    if (!h) return;
    listarIntervaloLLRB(h->esq, Ta, Tb);

    if (h->dado.inicio >= Ta && h->dado.inicio <= Tb)
        printf("%d;%s;%lld;%lld\n", h->dado.id, h->dado.titulo, h->dado.inicio, h->dado.fim);

    listarIntervaloLLRB(h->dir, Ta, Tb);
}

void buscaSucessor(NOB* raiz, long long T, NOB** melhor) {
    if (!raiz) return;

    buscaSucessor(raiz->esq, T, melhor);

    total_comparacoes_sucessor++;

    if (raiz->dado.inicio >= T) {
        if (!*melhor || raiz->dado.inicio < (*melhor)->dado.inicio)
            *melhor = raiz;
    }

    buscaSucessor(raiz->dir, T, melhor);
}

NOB* sucessorTemporalLLRB(ArvLLRB raiz, long long T) {
    qtd_sucessor++;
    NOB* melhor = NULL;
    buscaSucessor(raiz, T, &melhor);
    return melhor;
}

NOB* move2EsqRED(NOB* h) {
    trocaCor(h);
    if (cor(h->dir->esq) == RED) {
        h->dir = rotacionaDireita(h->dir);
        h = rotacionaEsquerda(h);
        trocaCor(h);
    }
    return h;
}

NOB* move2DirRED(NOB* h) {
    trocaCor(h);
    if (cor(h->esq->esq) == RED) {
        h = rotacionaDireita(h);
        trocaCor(h);
    }
    return h;
}

NOB* procuraMenor(NOB* h) {
    while (h->esq) h = h->esq;
    return h;
}

NOB* removerMenor(NOB* h) {
    if (!h->esq) {
        free(h);
        return NULL;
    }

    if (cor(h->esq) == BLACK && cor(h->esq->esq) == BLACK)
        h = move2EsqRED(h);

    h->esq = removerMenor(h->esq);
    return balancear(h);
}

NOB* remove_NO(NOB* h, int id) {
    if (id < h->dado.id) {
        if (cor(h->esq) == BLACK && cor(h->esq->esq) == BLACK)
            h = move2EsqRED(h);
        h->esq = remove_NO(h->esq, id);
    } else {
        if (cor(h->esq) == RED)
            h = rotacionaDireita(h);

        if (id == h->dado.id && !h->dir) {
            free(h);
            return NULL;
        }

        if (cor(h->dir) == BLACK && cor(h->dir->esq) == BLACK)
            h = move2DirRED(h);

        if (id == h->dado.id) {
            NOB* x = procuraMenor(h->dir);
            h->dado = x->dado;
            h->dir = removerMenor(h->dir);
        } else {
            h->dir = remove_NO(h->dir, id);
        }
    }
    return balancear(h);
}

int removerLLRB(ArvLLRB* raiz, int id) {
    if (!*raiz)
        return 0;

    if (cor((*raiz)->esq) == BLACK && cor((*raiz)->dir) == BLACK)
        (*raiz)->cor = RED;

    *raiz = remove_NO(*raiz, id);

    if (*raiz)
        (*raiz)->cor = BLACK;

    return 1;
}

void rstrip(char *s) {
    int len = strlen(s);
    while (len > 0 && (s[len-1]=='\n' || s[len-1]=='\r' || isspace((unsigned char)s[len-1]))) {
        s[len-1] = '\0';
        len--;
    }
}

int parseLinhaCSV(const char* linha, EventoB* out) {
    char buf[1024];
    strcpy(buf, linha);
    rstrip(buf);

    char *p1 = strchr(buf, ';');
    if (!p1) return 0;
    char *p2 = strchr(p1+1, ';');
    if (!p2) return 0;
    char *p3 = strchr(p2+1, ';');
    if (!p3) return 0;

    *p1 = '\0';
    out->id = atoi(buf);

    *p2 = '\0';
    if (strlen(p1+1) > 60) return 0;
    strcpy(out->titulo, p1+1);

    *p3 = '\0';
    out->inicio = atoll(p2+1);

    out->fim = atoll(p3+1);

    if (out->fim < out->inicio) return 0;

    return 1;
}

int parseCSVFile(const char* caminho, EventoB** arrOut, size_t* nOut) {
    FILE* f = fopen(caminho, "r");
    if (!f) return 0;

    EventoB* arr = NULL;
    size_t cap = 0, n = 0;
    char linha[1024];

    while (fgets(linha, sizeof(linha), f)) {
        rstrip(linha);
        if (strlen(linha)==0) continue;

        EventoB e;
        if (!parseLinhaCSV(linha, &e)) {
            free(arr);
            fclose(f);
            return 0;
        }

        if (n == cap) {
            cap = cap ? cap*2 : 16;
            arr = realloc(arr, cap*sizeof(EventoB));
        }
        arr[n++] = e;
    }

    fclose(f);
    *arrOut = arr;
    *nOut = n;
    return 1;
}

void salvar_inorder(NOB* h, FILE* f, int* ok) {
    if (!h || !*ok) return;
    salvar_inorder(h->esq, f, ok);
    if (fprintf(f,"%d;%s;%lld;%lld\n",
                h->dado.id, h->dado.titulo,
                h->dado.inicio, h->dado.fim) < 0)
        *ok = 0;
    salvar_inorder(h->dir, f, ok);
}

void salvarCSVLLRB(ArvLLRB raiz, const char* caminho) {
    FILE* f = fopen(caminho, "w");
    if (!f) {
        printf("ERRO\n");
        return;
    }
    int ok = 1;
    salvar_inorder(raiz, f, &ok);
    fclose(f);
    if (ok)
        printf("OK\n");
    else
        printf("ERRO\n");
}

int carregarCSVLLRB(ArvLLRB* raiz, const char* caminho) {
    EventoB* arr; size_t n;
    if (!parseCSVFile(caminho, &arr, &n)) return 0;

    liberarArveLLRB(*raiz);
    *raiz = NULL;

    for (size_t i=0; i<n; i++)
        inserirLLRB(raiz, arr[i]);

    free(arr);
    return 1;
}

int mesclarCSVLLRB(ArvLLRB* raiz, const char* caminho) {
    EventoB* arr; size_t n;
    if (!parseCSVFile(caminho, &arr, &n)) return 0;

    for (size_t i=0; i<n; i++)
        inserirLLRB(raiz, arr[i]);

    free(arr);
    return 1;
}

void liberarArveLLRB(ArvLLRB h) {
    if (!h) return;
    liberarArveLLRB(h->esq);
    liberarArveLLRB(h->dir);
    free(h);
}

int contaNosLLRB(ArvLLRB h) {
    if (!h) return 0;
    return 1 + contaNosLLRB(h->esq) + contaNosLLRB(h->dir);
}

int alturaLLRB(ArvLLRB h) {
    if (!h) return 0;
    int e = alturaLLRB(h->esq);
    int d = alturaLLRB(h->dir);
    return 1 + (e>d ? e:d);
}

void imprimirEstatisticasB(ArvLLRB raiz) {
    int N = contaNosLLRB(raiz);
    int H = alturaLLRB(raiz);
    double media = 0.0;

    if (qtd_sucessor > 0)
        media = (double)total_comparacoes_sucessor / qtd_sucessor;

    printf("N=%d; ALTURA=%d; ROTACOES=%lld; COMP_SUCESSOR_MEDIA=%.2f\n",
           N, H, total_rotacoes_b, media);
}