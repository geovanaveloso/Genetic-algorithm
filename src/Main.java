
import java.text.DecimalFormat;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author geovana
 */
public class Main {

    public static final int SIZE_CHROMOSOMES = 15; // chromosomes size
    public static final int SIZE_POPULATION = 10; // population size
    public static final int N_GENERATIONS = 50;
    public static final int LOWER = -1; // limite inferior
    public static final int UPPER = 1; // limite superior
    public static final int K = 5; // quantos bits para cada variavel
    public static final double RATE_CROSSOVER = 0.6;
    public static final double RATE_MUTATION = 0.1;
    public static boolean ELITISMO = false;
    public static boolean esc_sigma = false;
    public static boolean NORMALIZATION = true;
    public static final int K_N = 40; 
    public static final int T = 1; 

    public static void main(String[] args) {
        CHROMOSOMES c[] = new CHROMOSOMES[SIZE_POPULATION];
        int best_i = 0;
        double best_f;
        AG u = new AG();
        CHROMOSOMES best = new CHROMOSOMES();
        DecimalFormat df = new DecimalFormat("0.000");
        int rep;

        for (int l = 0; l < 10; l++) {
            rep = 0;
            for (int i = 0; i < Main.SIZE_POPULATION; i++) {
                c[i] = new CHROMOSOMES();
            }

            while (rep < N_GENERATIONS) {
                CHROMOSOMES cr[];
                double fitness[];
                best_f = 0.0;
                if (ELITISMO) {
                    cr = new CHROMOSOMES[SIZE_POPULATION];
                    for (int i = 0; i < SIZE_POPULATION - 1; i++) {
                        int j = u.torneio(c);
                        cr[i] = new CHROMOSOMES(c[j].x);
                    }
                    cr[SIZE_POPULATION - 1] = new CHROMOSOMES(c[best_i].x);

                } else {
                    cr = new CHROMOSOMES[SIZE_POPULATION];
                    best_i = 0;
                    for (int i = 0; i < SIZE_POPULATION; i++) {
                        int j = u.torneio(c);
                        cr[i] = new CHROMOSOMES(c[j].x);
                    }
                }

                double n = RATE_CROSSOVER * SIZE_POPULATION;
                while (n != 0.0) {
                    u.crossover2pt(cr);
                    n--;
                }

                n = RATE_MUTATION * SIZE_POPULATION;
                while (n != 0.0) {
                    u.mutation(cr);
                    n--;
                }

                fitness = u.calculateFitness(cr);

                String msg = null;
                for (int i = 0; i < cr.length; i++) {
                    msg += i + " - " + cr[i].toString() + "> Fitness " + df.format(fitness[i]).replace(",", ".") + " \n";
                    if (fitness[i] > best_f) {
                        best_i = i;
                        best_f = fitness[i];
                    }
                }
                double[] v = u.binary2real(cr[best_i]);
                rep++;
                best = new CHROMOSOMES(cr[best_i].x);
                for (int i = 0; i < SIZE_POPULATION; i++) {
                    c[i] = new CHROMOSOMES(cr[i].x);
                }
            }
            System.out.println(u.calculateFitness(best));
        }
    }
}
