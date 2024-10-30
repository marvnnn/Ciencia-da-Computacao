#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#include <math.h>
#include <time.h>

#define MAX_POKEMONS 801
#define MAX_NAME_LENGTH 100
#define MAX_DESC_LENGTH 256
#define MAX_TYPES 2
#define MAX_ABILITIES 10

typedef struct {
    int id;
    int generation;
    char name[MAX_NAME_LENGTH];
    char description[MAX_DESC_LENGTH];
    char types[MAX_TYPES][MAX_NAME_LENGTH];
    char abilities[1000];
    double weight;
    double height;
    int captureRate;
    bool isLegendary;
    time_t captureDate;
} Pokemon;

void initializePokemon(Pokemon *p) {
    p->id = 0;
    p->generation = 0;
    strcpy(p->name, "");
    strcpy(p->description, "");
    for (int i = 0; i < MAX_TYPES; i++) {
        strcpy(p->types[i], "");
    }
    strcpy(p->abilities, "");
    p->weight = 0.0;
    p->height = 0.0;
    p->captureRate = 0;
    p->isLegendary = false;
    p->captureDate = time(NULL);
}

void printPokemon(const Pokemon *p) {
    struct tm *tm_info = localtime(&p->captureDate);
    char buffer[26];
    removeAspasDuplas(p->abilities);
    strftime(buffer, sizeof(buffer), "%d/%m/%Y", tm_info);
    if(strlen(p->types[1]) > 1) {
        printf("[#%d -> %s: %s - ['%s', '%s'] - [%s] - %.1fkg - %.1fm - %d%% - %s - %d gen] - %s\n",
        p->id, p->name, p->description, p->types[0], p->types[1], p->abilities,
        p->weight, p->height, p->captureRate, p->isLegendary ? "true" : "false",
        p->generation, buffer);
    }
    else {
        printf("[#%d -> %s: %s - ['%s'] - [%s] - %.1fkg - %.1fm - %d%% - %s - %d gen] - %s\n",
        p->id, p->name, p->description, p->types[0], p->abilities,
        p->weight, p->height, p->captureRate, p->isLegendary ? "true" : "false",
        p->generation, buffer);
    }
    
}

Pokemon clonePokemon(const Pokemon *original) {
    Pokemon clone;
    clone.id = original->id;
    clone.generation = original->generation;
    strcpy(clone.name, original->name);
    strcpy(clone.description, original->description);
    strcpy(clone.types[0], original->types[0]);
    strcpy(clone.types[1], original->types[1]);
    strcpy(clone.abilities, original->abilities);
    clone.weight = original->weight;
    clone.height = original->height;
    clone.captureRate = original->captureRate;
    clone.isLegendary = original->isLegendary;
    clone.captureDate = original->captureDate;
    return clone;
}


void addSeparatorsWithSpaces(char *line) {
    char result[2048] = ""; // Buffer para armazenar a nova linha
    int i = 0;
    
    while (line[i] != '\0') {
        // Verifica se o caractere atual é '}' e o próximo também
        if (line[i] == '}') {
            strcat(result, "}");
            while (line[i + 1] == '}') {
                strcat(result, "n}"); // Adiciona 'n' antes do próximo '}'
                i++; // Move para o próximo '}' encontrado
            }
        } else {
            strncat(result, &line[i], 1); // Adiciona o caractere atual ao resultado
        }
        i++; // Move para o próximo caractere
    }
    
    strcpy(line, result); // Copia a nova linha de volta
}

double storeWithOneDecimal(double value) {
    return round(value * 10) / 10.0; // Arredonda para uma casa decimal
}

void readPokemon(Pokemon *p, const char *data) {
    char attributes[12][MAX_DESC_LENGTH];
    char *token = strtok(data, "}");

    for (int i = 0; i < 12 && token != NULL; i++) {
        strcpy(attributes[i], token);
        token = strtok(NULL, "}");
    }

    p->id = atoi(attributes[0]);
    p->generation = atoi(attributes[1]);
    strcpy(p->name, attributes[2]);
    strcpy(p->description, attributes[3]);

    // Leitura dos tipos
    strcpy(p->types[0], attributes[4]); // Sempre haverá um tipo
    strcpy(p->types[1], attributes[5]);

    // Leitura das habilidades
    strcpy(p->abilities, attributes[6]);

    // Lida com peso e altura
    p->weight = attributes[7][0] ? storeWithOneDecimal(atof(attributes[7])) : 0.0;
    p->height = attributes[8][0] ? storeWithOneDecimal(atof(attributes[8])) : 0.0;

    p->captureRate = atoi(attributes[9]);
    p->isLegendary = atoi(attributes[10]) == 1;

    struct tm tm = {0};
    strptime(attributes[11], "%d/%m/%Y", &tm);
    p->captureDate = mktime(&tm);
}

void removeAspasDuplas(char *x) {
    char y[MAX_DESC_LENGTH] = "";
    for(int i = 0; i < strlen(x); i++) {
        if(x[i] != '"') {
            y[strlen(y)] = x[i];
        }
    }
    strcpy(x, y);
}

void simplifyString(char *x) {
    char y[MAX_DESC_LENGTH] = "";
    int contador = 0;
    
    for (int i = 0; i < strlen(x); i++) {
        if (x[i] != '[') {
            if (x[i] == ',')
                y[strlen(y)] = '}';
            else
                y[strlen(y)] = x[i];
        } else {
            for (int j = i + 1; x[j] != ']' && x[j] != '\0'; j++) {
                y[strlen(y)] = x[j];
                contador++;
            }
            i += contador + 1; // Move para o próximo caractere após o fechamento do colchete
        }
    }
    strcpy(x, y); // Copia a string simplificada de volta
    //printf("%s\n", x);
}

int shellSort(Pokemon array[], int n) {
    int cont = 0;
    for(int gap = n / 2; gap > 0; gap /= 2) {
        for(int i = gap; i < n; i++) {
            Pokemon tmp = array[i];
            int j;
            for(j = i; j >= gap && array[j - gap].weight > tmp.weight; j -= gap) {
                array[j] = array[j - gap];
                cont++;
            }
            if(j >= gap && array[j - gap].weight == tmp.weight) {
                if(strcmp(array[j - gap].name, tmp.name) > 0) {
                    array[j] = array[j - gap];
                    j = j - gap;
                    cont++;
                }
            }
            array[j] = tmp;
        }
    }
    return cont;
}

int main() {
    clock_t t;
    t = clock();
    FILE *file = fopen("/tmp/pokemon.csv", "r");
    if (!file) {
        perror("Failed to open file");
        return EXIT_FAILURE;
    }
    
    Pokemon *pokemons = malloc(MAX_POKEMONS * sizeof(Pokemon));
    for (int i = 0; i < MAX_POKEMONS; i++) {
        initializePokemon(&pokemons[i]);
    }

    char line[1024];
    fgets(line, sizeof(line), file); // Skip header
    int i = 0;
    while (fgets(line, sizeof(line), file) && i < MAX_POKEMONS) {
        line[strcspn(line, "\n")] = 0; // Remove newline character
  //    printf("%s\n", line);
        simplifyString(line);
        addSeparatorsWithSpaces(line);
        readPokemon(&pokemons[i], line);
        i++;
    }
    fclose(file);

    char id[10];
    scanf("%s", id);
    int cont = 0;
    Pokemon *p = malloc(100 * sizeof(Pokemon));
    while (strcmp(id, "FIM") != 0) {
        int index = atoi(id) - 1;
        p[cont++] = clonePokemon(&pokemons[index]);
        scanf("%s", id);
    }
    //p[0].id = 99;
    //printf("%d %d\n", p[0].id, pokemons[0].id);
    int numComp = shellSort(p, cont);
    for(int z = 0; z < cont; z++) {
        printPokemon(&p[z]);
    }
    t = clock() - t;
    FILE *arquivo = fopen("844619_shellsort.txt", "w");
    fprintf(arquivo, "%d\t", 844619);
    fprintf(arquivo, "%lf\t", (double)t/((CLOCKS_PER_SEC/1000)));
    fprintf(arquivo, "%d\t", numComp);
    fclose(arquivo);
    free(pokemons);
    return 0;
}
