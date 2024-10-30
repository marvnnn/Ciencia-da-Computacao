#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <time.h>

struct Pokedex {
    // Atributos do pokemon
    int id;
    int generation;
    char *name;
    char *description;
    char types[2][100] ;
    char abilities[200];
    double weight;
    double height;
    int captureRate;
    bool isLegendary;
    struct captureDate;

    

};

int lenght(char *input) {
    int i;
    for(i = 0; input[i] != '\0'; i++)
    return i;
} 

bool compString(char *x, char *y) {
    bool isEqual = false;
    int tam1 = lenght(x), tam2 = lenght(y);
    if(tam1 == tam2) {
        isEqual = true;
        for(int i = 0; i < tam1; i++) {
            if(x[i] != y[i]) {
                isEqual = false;
                i = tam1;
            }
        }
    }
    return isEqual;
}

int main() {
    
    
    
    return 0;
}