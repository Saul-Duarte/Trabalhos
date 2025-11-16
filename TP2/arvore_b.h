// arvore_b.h - TAD para Problema B (Agenda - Left-Leaning Red-Black Tree)
#ifndef ARVORE_B_H
#define ARVORE_B_H

typedef struct {
    int id;
    char titulo[61];
    long long inicio;
    long long fim;
} EventoB;

typedef struct NOB {
    EventoB dado;
    struct NOB *esq, *dir;
    int cor;
} NOB;

typedef NOB* ArvLLRB;

ArvLLRB criarArvLLRB(void);
int inserirLLRB(ArvLLRB* raiz, EventoB e);
EventoB* buscarIDLLRB(ArvLLRB h, int id);
void listarLLRB(ArvLLRB h);
void listarIntervaloLLRB(ArvLLRB h, long long Ta, long long Tb);
NOB* sucessorTemporalLLRB(ArvLLRB raiz, long long T);
int removerLLRB(ArvLLRB* raiz, int id);
void salvarCSVLLRB(ArvLLRB raiz, const char* caminho);
int carregarCSVLLRB(ArvLLRB* raiz, const char* caminho);
int mesclarCSVLLRB(ArvLLRB* raiz, const char* caminho);
void liberarArveLLRB(ArvLLRB h);
int contaNosLLRB(ArvLLRB h);
int alturaLLRB(ArvLLRB h);
void imprimirEstatisticasB(ArvLLRB raiz);

extern long long total_rotacoes_b;
extern long long total_comparacoes_sucessor;
extern long long qtd_sucessor;

#endif