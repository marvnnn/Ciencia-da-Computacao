import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.io.IOException;
import java.util.Scanner;

/*
 * Author: Mateus Martins Parreiras
 * TP03 Q01
 * Date: 22/10/2024
 */

public class TP03Q01 {
    public static boolean compString(String x, String y) { // Função para comparar duas strings, semelhante a .equals.
        boolean r = false;
        int tam1 = x.length(), tam2 = y.length();
        if(tam1 == tam2) {
            r = true;
            for(int i = 0; i < tam1; i++) {
                char c1 = x.charAt(i), c2 = y.charAt(i);
                if(c1 != c2) {
                    r = false;
                    i = tam1;
                }
            }
        }
        return r;
    }
    public static void main(String[] args) {
        
        String arquivo = "/tmp/pokemon.csv"; // Ler o arquivo csv.

        ListaPokemons lista1 = new ListaPokemons(); // Criar a primeira lista que vai conter os 801 pokemons.
        ListaPokemons lista2 = new ListaPokemons(); // Criar a segunda lista que terá apenas os pokemons que o usuário digitar.

        try (BufferedReader ler = new BufferedReader(new FileReader(arquivo))) { // Ler o arquivo.
            String linha; 
            ler.readLine(); // Descartar o cabeçalho.

            while((linha = ler.readLine()) != null) { // Enquanto tiver linha para ser lida dentro do arquivo, continue.
                Pokemons p = new Pokemons(); // Cria uma nova instancia para o objeto pokemon.
                p.lerPokemon(linha); // Armazena o conteúdo da linha dentro dos atributos do pokemon.
                lista1.inserirLista(p); // Insere na lista1.
            }

        } catch(IOException e) {
            e.printStackTrace();
        }

        Scanner scan = new Scanner(System.in); // Declaração do scanner utilizado para ler entradas.
        String id = scan.nextLine(); // Como tem a possibilidade o usuário digitar "FIM", armazeno o id digitado por ele em uma string.

        int cont = 0;
        while(!compString(id, "FIM")) { // Compara se "id" é igual a "FIM", caso contrario, continua o loop.
            int ids = Integer.parseInt(id); // Transforma o id(string) em um inteiro.
            lista2.inserirLista(lista1.copyPokemons(ids - 1)); // Insere na lista2 o pokemon digitado pelo usuário.
            cont++;
            id = scan.nextLine(); // Lê o próximo id.
        }

        int numOperacao = scan.nextInt(); // Armazena o número de operações que o usuário vai realizar.

        for(int i = 0; i < numOperacao; i++) {
            String operacao = scan.next();
            int x = 0;
            int ids = 0;
            Pokemons p = new Pokemons();

            switch (operacao) {
                case "II": // Caso a entrada seja "II", insere o id digitado no inicio da lista2.
                    ids = scan.nextInt();
                    lista2.inserirInicio(lista1.copyPokemons(ids-1));
                    break;
                case "IF": // Caso seja "IF" insere o id digitado no fim da lista2.
                    ids = scan.nextInt();
                    lista2.inserirFim(lista1.copyPokemons(ids-1));
                    break;
                case "I*": // Caso seja "I*" insere o id digitado na posição x.
                    x = scan.nextInt();
                    ids = scan.nextInt();
                    lista2.inserirPos(lista1.copyPokemons(ids-1), x);
                    break;
                case "RI":
                    p = lista2.removerInicio();
                    System.out.println("(R)" + p.getName());
                    break;
                case "RF":
                    p = lista2.removerFim();
                    System.out.println("(R)" + p.getName());
                    break;
                case "R*":
                    x = scan.nextInt();
                    p = lista2.removerPos(x);
                    System.out.println("(R)" + p.getName());
                    break;
                
                default:
                    break;
            }
        }
        for(int i = 0; i < cont; i++) {
            System.out.println("[" + i + "] ");
            lista2.impressPokemon(i);
        }
        
        scan.close();
    }
}

class Pokemons {

    // Características dos pokemons.
    private int id;
    private int generation;
    private String name;
    private String description;
    private List<String> types;
    private List<String> abilities;
    private double weight;
    private double height;
    private int captureRate;
    private boolean isLegendary;
    private Date captureDate;

    // Construtor
    Pokemons() {
        this.id = 0;
        this.generation = 0;
        this.name = "";
        this.description = "";
        this.types = new ArrayList<>();
        this.abilities = new ArrayList<>();
        this.weight = 0;
        this.height = 0;
        this.captureRate = 0;
        this.isLegendary = false;
        this.captureDate = new Date();
    }

    // Métodos get
    public int getId() {
        return this.id;
    }
    public int getGeneration() {
        return this.generation;
    }
    public String getName() {
        return this.name;
    }
    public String getDescription() {
        return this.description;
    }
    public List<String> getTypes() {
        return this.types;
    }
    public List<String> getAbilities() {
        return this.abilities;
    }
    public double getWeight() {
        return this.weight;
    }
    public double getHeight() {
        return this.height;
    }
    public int getCaptureRate() {
        return this.captureRate;
    }
    public boolean getIsLegendary() {
        return this.isLegendary;
    }
    public Date getCaptureDate() {
        return this.captureDate;
    }

    // Métodos set
    public void setId(int id) {
        this.id = id;
    }
    public void setGeneration(int generation) {
        this.generation = generation;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setTypes(String type1, String type2) {
        this.types.add("'" + type1 + "'");
        if(!type2.isEmpty())
            this.types.add("'" + type2 + "'");
    }
    public void setAbilities(String abilities) {
        this.abilities.add(abilities);
    }
    public void setWeight(double weight) {
        this.weight = weight;
    }
    public void setHeight(double height) {
        this.height = height;
    }
    public void setCaptureRate(int captureRate) {
        this.captureRate = captureRate;
    }
    public void setIsLegendary(boolean is) {
        this.isLegendary = is;
    }
    public void setCaptureDate(String captureDate) throws ParseException {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        this.captureDate = formato.parse(captureDate);
    }
    public void setAbilitiesList(List<String> abilities) {
        this.abilities = abilities;
    }
    public void setCaptureDateList(Date captureDate) {
        this.captureDate = captureDate;
    }
    public void setTypesList(List<String> types) {
        this.types = types;
    }

    // Métodos imprimir e ler.
    public void imprimirPokemon() {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        String x = "[#" + getId() + " -> " + getName() + ": " + 
        getDescription() + " - " + getTypes() + " - " + getAbilities()
        + " - " + getWeight() + "kg - " + getHeight() + "m - " + getCaptureRate()
        + "% - " + getIsLegendary() + " - " + getGeneration() + " gen] - " + 
        formato.format(getCaptureDate());
        x = removeAspasDuplas(x);
        System.out.println(x);
    }

    public void lerPokemon(String p) {

        String pokemon = simplifyString(p);
        pokemon = removeSpacesAfterCommas(pokemon);
        pokemon = addSpacesAfterCommas(pokemon);

        // Separar coisa por coisa dentro da string
        String atributos[] = pokemon.split("}");

        // Ler id
        setId(Integer.parseInt(atributos[0]));

        // Ler generation
        setGeneration(Integer.parseInt(atributos[1]));

        // Ler name
        setName(atributos[2]);

        // Ler description
        setDescription(atributos[3]);

        // Ler types
        setTypes(atributos[4], atributos[5]);

        // Ler abilities
        setAbilities(atributos[6]);

        // Ler weight
        if(atributos[7].isEmpty()) {
            setWeight(0.0);
        }
        else {
            setWeight(Double.parseDouble(atributos[7]));
        }

        // Ler height
        if(atributos[8].isEmpty()) {
            setHeight(0.0);
        }
        else {
            setHeight(Double.parseDouble(atributos[8]));
        }

        // Ler captureRate
        setCaptureRate(Integer.parseInt(atributos[9]));

        // Ler isLegendary
        switch(Integer.parseInt(atributos[10])) {
            case 0:
                setIsLegendary(false);
                break;
            case 1:
                setIsLegendary(true);
                break;
            default:
                break;
        }

        // Ler captureDate
        try {
            setCaptureDate(atributos[11]);
        } catch(ParseException e) {
            System.out.println("ERRO");
        }   
    }

    // Método clone
    public Pokemons clonePokemon() {
        Pokemons clone = new Pokemons();
        clone.setId(this.id);
        clone.setGeneration(this.generation);
        clone.setName(this.name);
        clone.setAbilitiesList(this.abilities);
        clone.setCaptureDateList(this.captureDate);
        clone.setDescription(this.description);
        clone.setCaptureRate(this.captureRate);
        clone.setHeight(this.height);
        clone.setWeight(this.weight);
        clone.setIsLegendary(this.isLegendary);
        clone.setTypesList(this.types);

        return clone;
    }

    // Métodos para simplificar string 

    public static String simplifyString(String x) {
        String y = "";
        int contador = 0;
        for(int i = 0; i < x.length(); i++){
            if(x.charAt(i) != '[') {
                if(x.charAt(i) == ',')
                    y += "}";
                else
                    y += x.charAt(i);
            }
            else {
                for(int j = i+1; x.charAt(j) != ']'; j++) {
                    y += x.charAt(j);
                    contador++;
                }
                i += contador+1;
            }
        }
        return y;
    }

    public static String removeSpacesAfterCommas(String input) {
        // Remove espaços após cada vírgula
        return input.replaceAll(",\\s*", ",");
    }

    public static String addSpacesAfterCommas(String input) {
        // Adiciona um espaço após cada vírgula, se não houver
        return input.replaceAll(",(?!\\s)", ", "); // Adiciona espaço se não houver já
    }

    public static String removeAspasDuplas(String input) {
        // Remove espaços após cada vírgula
        return input.replaceAll("\"", "");
    }
} 

class ListaPokemons {
    private Pokemons[] array;
    private int n;

    ListaPokemons() {
        array = new Pokemons[1500];
        n = 0;
    }

    public void impressPokemon(int index) {
        array[index].imprimirPokemon();
    }

    public int getNumberOfPokemons() {
        return this.n;
    }
    
    public void inserirLista(Pokemons p) {
        array[n++] = p; 
    }

    public void inserirInicio(Pokemons p) {

        for(int i = n; i >= 0; i--) {
            array[i+1] = array[i];
        }
    
        array[0] = p;
    }

    public void inserirFim(Pokemons p) {
        array[n+1] = p;
    }

    public void inserirPos(Pokemons p, int indice) {
    
        if(indice == n) {
            inserirFim(p);
        } 
        else if(indice == 0) {
            inserirInicio(p);
        } 
        else if(indice > n || indice < 0) {
            throw new IndexOutOfBoundsException("Indice invalido");
        } 
        else  {                       
            for(int i = n; i >= indice; i--) {
                array[i+1] = array[i];
            }
            array[indice] = p;
        }
    }

    public Pokemons removerInicio() {
        Pokemons p = array[0];
        for(int i = 0; i < n; i++) {
            array[i] = array[i+1];
        }
        n--;
        return p;
    }

    public Pokemons removerFim() {
        Pokemons p = array[n--];
        return p;
    }

    public Pokemons removerPos(int indice) {
        Pokemons p = array[indice];
        for(int i = indice; i < n; i++) {
            array[i] = array[i+1];
        }
        n--;
        return p;
    }

    public Pokemons copyPokemons(int indice) {
        if(indice > n || indice < 0) {
            throw new IndexOutOfBoundsException("Indice invalido.");
        }
        return array[indice];
    }
}

class FilaPokemons {
    private ListaPokemons fila;

    FilaPokemons() {
        fila = new ListaPokemons();
    }

    public void inserir(Pokemons p) {
        fila.inserirFim(p);
    }

    public Pokemons remover() {
        return fila.removerInicio();
    }
}

class PilhaPokemons {
    private ListaPokemons pilha;

    PilhaPokemons() {
        pilha = new ListaPokemons();
    }

    public void inserir(Pokemons p) {
        pilha.inserirFim(p);
    }

    public Pokemons remover() {
        return pilha.removerFim();
    }
}

class Celula {
    public Pokemons elemento;
    public Celula prox; 

    public Celula() {
        this(null);
    }

    public Celula(Pokemons elemento) {
        this.elemento = elemento;
        this.prox = null;
    }
}

class FlexList {
    private Celula first;
    private Celula last;
    private int n;

    FlexList() {
        first = new Celula();
        last = first;
        n = 0;
    }

    public void inserirInicio(Pokemons p) {
        Celula i = new Celula(p);
        i.prox = first.prox;
        first.prox = i;
        if(first == last) {
            last = i;
        }
        i = null;
        n++;
    }

    public void inserirFim(Pokemons p) {
        last.prox = new Celula(p);
        last = last.prox; // Anda com o ponteiro para o último elemento
        n++;
    }

    public void inserirPos(Pokemons p, int indice) {
        if(indice > n || indice < 0) {
            throw new IndexOutOfBoundsException("Indice invalido");
        }
        else if(indice == n) {
            inserirFim(p);
        }
        else if(indice == 0) {
            inserirInicio(p);
        }
        else {
            Celula i = new Celula(p);
            Celula tmp = first.prox;
            for(int z = 0; z < indice-1; z++) {
                tmp = tmp.prox;
            }
            i.prox = tmp.prox;
            tmp.prox = i;
            tmp = i = null;
        }
    }

    public Pokemons removerInicio() {
        if(first == last) {
            throw new IndexOutOfBoundsException("Arranjo vazio");
        }
        Pokemons p = first.prox.elemento;
        Celula tmp = first.prox;
        first.prox = tmp.prox;
        tmp.prox = null;
        tmp = null;
        return p;
    }

    public Pokemons removerFim() {
        if(first == last) {
            throw new IndexOutOfBoundsException("Arranjo vazio");
        }
        Pokemons p = last.elemento;
        Celula tmp = first.prox;
        while(tmp.prox != last) {
            tmp = tmp.prox;
        }
        last = tmp;
        last.prox = null;
        tmp = null;
        return p;
    }

    public Pokemons removerPos(int indice) {
        Pokemons p;
        if(first == last ) {
            throw new IndexOutOfBoundsException("Arranjo vazio");
        } 
        else if(indice > n || indice < 0) {
            throw new IndexOutOfBoundsException("Indice invalido");
        } 
        else if(indice == 0) {
            p = removerInicio();
        }
        else if(indice == n) {
            p = removerFim();
        }
        else {
            Celula tmp = first.prox;
            for(int i = 0; i < indice-1; i++) {
                tmp = tmp.prox;
            }
            p = tmp.prox.elemento;
            tmp.prox = tmp.prox.prox;
            tmp.prox.prox = null;
            tmp = null;
        }
        return p;
    }
}

class FlexQueue {
    private FlexList queue;

    FlexQueue() {
        queue = new FlexList();
    }

    public void insert(Pokemons p) {
        queue.inserirFim(p);
    }

    public Pokemons remove() {
        return queue.removerInicio();
    }
}

class FlexStack {
    private FlexList stack;

    FlexStack() {
        stack = new FlexList();
    }

    public void insert(Pokemons p) {
        stack.inserirFim(p);
    }

    public Pokemons remove() {
        return stack.removerFim();
    }
}

class CelulaDupla {
	public Pokemons elemento;
	public CelulaDupla ant;
	public CelulaDupla prox;

	public CelulaDupla() {
		this(null);
	}

	public CelulaDupla(Pokemons elemento) {
		this.elemento = elemento;
		this.ant = this.prox = null;
	}

    public void setElemento(Pokemons elemento) {
        this.elemento = elemento;
    }
}

class DinamicListDoubly {
    private CelulaDupla first, last;
    private int n;

    DinamicListDoubly() {
        first = new CelulaDupla();
        last = first;
        n = 0;
    }

    public void insertFirst(Pokemons p) {
        CelulaDupla tmp = new CelulaDupla(p);
        if(first == last) {
            last = tmp;
            last.ant = first;
            first.prox = last;
            tmp = null;
            n++;
        } 
        else {
            tmp.prox = first.prox;
            tmp.ant = first;
            first.prox = tmp;
            tmp = null;
            n++;
        }       
    }
    
    public void insertLast(Pokemons p) {
        CelulaDupla tmp = new CelulaDupla(p);
        tmp.ant = last;
        last.prox = tmp;
        last = tmp;
        tmp = null;
        n++;
    }

    public void insertPos(Pokemons p, int index) {
        if(index < 0 || index > n) 
            throw new IndexOutOfBoundsException("Invalid index");
        else if(index == 0) 
            insertFirst(p);
        else if(index > n)
            insertLast(p);
        else {
            CelulaDupla tmp = new CelulaDupla(p);
            if(catchMinorDifference(0, n, index)) {
                CelulaDupla i = last;
                for(int z = n; z > index; z--) {
                    i = i.ant;
                }
                tmp.prox = i;
                tmp.ant = i.ant;
                i.ant = tmp;
                tmp = i = null;
            }
            else {
                CelulaDupla i = first.prox;
                for(int z = 0; z < index; z++) {
                    i = i.prox;
                }
                tmp.prox = i;
                tmp.ant = i.ant;
                i.ant = tmp;
                tmp = i = null;
            }
        }
    }

    public boolean catchMinorDifference(int i, int y, int index) {
        boolean resposta = false;
        if(index - i > y - index) 
            resposta = true;
        return resposta;
    }

    public Pokemons removeFirst() {
        if(first == last) {
            throw new IndexOutOfBoundsException("Empty list.");
        }
        Pokemons p = first.prox.elemento;
        CelulaDupla tmp = first.prox;
        CelulaDupla i = tmp.prox;
        i.ant = first;
        first.prox = i;       
        tmp.ant = tmp.prox = null;
        tmp = i = null; 

        return p;
    }

    public Pokemons removeLast() {
        if(first == last) {
            throw new IndexOutOfBoundsException("Empty list");
        }
        Pokemons p = last.elemento;
        CelulaDupla tmp = last;
        last = last.ant;
        last.prox = tmp.ant = null;
        tmp = null;

        return p;
    }

//     public Pokemons removePos(int index) {
//         Pokemons p;
//         if(last == first) {
//             throw new IndexOutOfBoundsException("Empty list.");
//         }
//         else if(index > n || index < 0) {
//             throw new IndexOutOfBoundsException("Invalid index.");
//         }
//         else if(index == 0) {
//             p = removeFirst();
//         }
//         else if(index == n) {
//             p = removeLast();
//         }
//         else {
//             if(catchMinorDifference(0, n, index)) {
//                 CelulaDupla i = last;
//                 for(int z = n; z > index; z--) {
//                     i = i.ant;
//                 }
//                 CelulaDupla tmp = i.ant;
//                 p = i.elemento;
//                 tmp.prox = i.prox;
//                 tmp = i.prox;
//                 tmp.ant = i.ant;
//                 i.ant = i.prox = null;
//                 tmp = i = null;
//             }
//         }

//         return p;
//     }
}