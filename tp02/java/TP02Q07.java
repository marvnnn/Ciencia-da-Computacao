import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.io.IOException;
import java.util.Scanner;

public class TP02Q07 {
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
        public List getTypes() {
            return this.types;
        }
        public List getAbilities() {
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
        public void lerPokemon(String pokemon) {
            // Separar coisa por coisa dentro da string
            String atributos[] = pokemon.split("}");

            // for(int i = 0; i < 12; i++) {
            //     System.out.println(atributos[i]);
            // }

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
    } 
    public static String removeAspasDuplas(String input) {
        // Remove espaços após cada vírgula
        return input.replaceAll("\"", "");
    }
    public static boolean compString(String x, String y) {
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

    public static boolean isBigger(String name1, String name2) {
        boolean resposta = false;
        if(name1.compareTo(name2) > 0) {
            resposta = true;
        } 
        return resposta;
    }

    public static int insertionSort(Pokemons[] array, int cont) throws ParseException{
        int c = 0;
        for(int i = 1; i < cont; i++) {
            Pokemons key = array[i];
            int j = i - 1;
            while(j >= 0 && ((key.getCaptureDate().compareTo(array[j].getCaptureDate())) < 0 ||
            (key.getCaptureDate().compareTo(array[j].getCaptureDate())) == 0 && key.getName().compareTo(array[j].getName()) < 0)) {
                array[j+1] = array[j];
                j = j - 1;
            }
            array[j + 1] = key;
        }
        return c;
    }
        
    public static void main(String[] args) {
        long tempoInicial = System.currentTimeMillis();
        String arquivo = "/tmp/pokemon.csv";
        String arquivoMat = "844619_insercao.txt";
        int i = 0;
        TP02Q07 classe = new TP02Q07();
        Pokemons[] pokemons = new Pokemons[801];
        for(int j = 0; j < 801; j++) {
            pokemons[j] = classe.new Pokemons();
        }
        try (BufferedReader ler = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            ler.readLine();
            while((linha = ler.readLine()) != null) {
                //System.out.println(addSpaces(linha));
                linha = simplifyString(linha);
                pokemons[i].lerPokemon(addSpacesAfterCommas(removeSpacesAfterCommas(linha)));
                i++;
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
        Scanner scan = new Scanner(System.in);
        String id = scan.nextLine();
        Pokemons[] arrayOrdenado = new Pokemons[801];
        int cont = 0;
        while(!compString(id, "FIM")) {
            arrayOrdenado[cont++] = pokemons[Integer.parseInt(id)-1].clonePokemon();
            id = scan.nextLine();
        }
        int numComparacao = 0;
        try {
            numComparacao = insertionSort(arrayOrdenado, cont);
        } catch(ParseException e) {
            System.out.println("ERRO");
        }
        for(int z = 0; z < cont; z++) {
            arrayOrdenado[z].imprimirPokemon();
        }
        long tempoFinal = System.currentTimeMillis();
        long tempoExec = tempoFinal - tempoInicial;
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(arquivoMat))) {
            writer.write("844619\t");
            writer.write(tempoExec + "\t");
            writer.write(numComparacao + "\t");
        } catch(IOException e) {
            e.printStackTrace();
        }
        scan.close();
    }
}