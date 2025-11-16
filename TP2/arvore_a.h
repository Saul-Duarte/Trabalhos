// arvore_a.h - TAD para Problema A (Catálogo de Componentes - AVL Tree)
#ifndef ARVORE_A_H
#define ARVORE_A_H

typedef struct NOA {
    char *codigo;
    char *descricao;
    int estoque;
    float preco;
    int altura;
    struct NOA *esq;
    struct NOA *dir;
} NOA;

typedef struct {
    NOA *raiz;
    int N;
    int rotacoes;
    long long comp_busca;
    int num_buscas;
} AVLTree;

AVLTree* criarAVL(void);
int inserirAVL(AVLTree *arv, char *codigo, char *descricao, int estoque, float preco);
NOA* buscarAVL(AVLTree *arv, const char *codigo);
int removerAVL(AVLTree *arv, const char *codigo);
void listarAVL(AVLTree *arv);
void intervaloAVL(AVLTree *arv, const char *L, const char *R);
void imprimirEstatisticasA(AVLTree *arv);
void liberarAVL(AVLTree *arv);

#endif