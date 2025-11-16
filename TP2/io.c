// io.c - Implementação de funções de I/O
#include <stdio.h>
#include <string.h>
#include "io.h"

char* lerLinha(char *buffer, int tam) {
    if (fgets(buffer, tam, stdin) == NULL)
        return NULL;

    buffer[strcspn(buffer, "\n")] = '\0';

    return buffer;
}