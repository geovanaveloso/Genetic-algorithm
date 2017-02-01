
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 *
 * @author geovana
 */
public class AG {

    public AG() {
    }

    public int torneio(CHROMOSOMES c[]) {
        for (int i = 0; i < c.length; i++) {
            int c1 = new Random().nextInt(Main.SIZE_POPULATION);
            int c2 = new Random().nextInt(Main.SIZE_POPULATION);
            while (c1 == c2) {
                c2 = new Random().nextInt(Main.SIZE_POPULATION);
            }
            double fitness_c1, fitness_c2;
            if (Main.esc_sigma) {
                fitness_c1 = calcularFitnessEscSigma(c, c1);
                fitness_c2 = calcularFitnessEscSigma(c, c2);
            } else {
                if (Main.NORMALIZATION) {
                    fitness_c1 = calcularFitnessNormalizacao(c, c1);
                    fitness_c2 = calcularFitnessNormalizacao(c, c2);
                } else {
                    fitness_c1 = calculateFitness(c[c1]);
                    fitness_c2 = calculateFitness(c[c2]);
                }
            }

            if (fitness_c1 > fitness_c2) {
                return c1;
            } else {
                return c2;
            }
        }
        return 0;
    }

    public double calcularFitnessNormalizacao(CHROMOSOMES c[], int ind) {
        List<CHROMOSOMES> cr = Arrays.asList(c);
        Collections.sort(cr);
        double fitness[] = new double[c.length];

        for (int i = 0; i < cr.size(); i++) {
            if (i == 0) {
                fitness[i] = Main.K_N;
            } else {
                fitness[i] = fitness[i - 1] - Main.T;
            }
        }
        return fitness[ind];
    }

    public double calcularFitnessEscSigma(CHROMOSOMES c[], int ind) {
        double media, dv;
        media = mediaFitnessPop(c);
        dv = desvioPFitnessPop(c, media);

        if (dv == 0) {
            return 1;
        } else {
            return 1 + ((calculateFitness(c[ind]) - media) / 2 * dv);
        }
    }

    public double mediaFitnessPop(CHROMOSOMES c[]) {
        int tam = c.length;
        double v = 0;
        for (int i = 0; i < tam; i++) {
            v += fitness(c[i].x[0], c[i].x[1], c[i].x[2]);
        }
        return v / tam;
    }

    public double desvioPFitnessPop(CHROMOSOMES c[], double media) {
        int tam = c.length;
        double df = 0;
        for (int i = 0; i < tam; i++) {
            df += Math.pow(media - this.fitness(c[i].x[0], c[i].x[1], c[i].x[2]), 2);
        }
        return Math.sqrt(df * (1 / (tam - 1)));
    }

    public double[] decimal2real(int[] x) {
        double sum[] = new double[3];
        for (int i = 0; i < 3; i++) {
            sum[i] = Main.LOWER + ((Main.UPPER - Main.LOWER) / ((Math.pow(2, Main.K)) - 1)) * x[i];
        }
        return sum;
    }

    public int[] binary2decimal(CHROMOSOMES c) {
        int sum[] = new int[3];
        int t[][] = new int[3][Main.K];
        int x = 0;
        int y = 0;
        int z = 0;
        for (int j = 0; j < Main.SIZE_CHROMOSOMES; j++) {
            if (j < 5) {
                t[0][x] = c.x[j];
                x++;
            } else {
                if (j < 10) {
                    t[1][y] = c.x[j];
                    y++;
                } else {
                    t[2][z] = c.x[j];
                    z++;

                }
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < Main.K; j++) {
                if (t[i][j] == 1) {
                    sum[i] += Math.pow(2, j);
                }
            }

        }
        return sum;
    }

    public double[] binary2real(CHROMOSOMES c) {
        int sum_dec_c1[] = binary2decimal(c);
        return decimal2real(sum_dec_c1);
    }

    public double fitness(double x, double y, double z) {
        return 10 * Math.pow((x - (Math.pow(y, 2))), 2) + 2 * Math.sin(x) + z * Math.pow(Math.E, (z * -1)) + Math.pow(y, 2) * Math.sin(y);
    }

    public double[] calculateFitness(CHROMOSOMES c[]) {
        double fitness[] = new double[c.length];
        for (int i = 0; i < c.length; i++) {
            int sum_dec[] = binary2decimal(c[i]);
            double sum_real[] = decimal2real(sum_dec);
            fitness[i] = fitness(sum_real[0], sum_real[1], sum_real[2]);
        }
        return fitness;
    }

    public double calculateFitness(CHROMOSOMES c) {
        int sum_dec[] = binary2decimal(c);
        double sum_real[] = decimal2real(sum_dec);
        return fitness(sum_real[0], sum_real[1], sum_real[2]);
    }

    public void crossover1pt(CHROMOSOMES c[]) {
        int c1 = new Random().nextInt(Main.SIZE_POPULATION);
        int c2 = new Random().nextInt(Main.SIZE_POPULATION);
        int aux[] = new int[Main.SIZE_CHROMOSOMES];

        int pt_corte = new Random().nextInt(Main.SIZE_CHROMOSOMES);

        for (int i = 0; i < pt_corte; i++) {
            aux[i] = c[c1].x[i];
            c[c1].x[i] = c[c2].x[i];
        }
        for (int i = 0; i < pt_corte; i++) {
            c[c2].x[i] = aux[i];
        }

    }

    public void crossover2pt(CHROMOSOMES c[]) {
        int c1 = new Random().nextInt(Main.SIZE_POPULATION);
        int c2 = new Random().nextInt(Main.SIZE_POPULATION);
        int aux[] = new int[Main.SIZE_CHROMOSOMES];

        int pt_corte1 = new Random().nextInt(Main.SIZE_CHROMOSOMES);
        int pt_corte2 = new Random().nextInt(Main.SIZE_CHROMOSOMES);

        for (int i = 0; i < pt_corte1; i++) {
            aux[i] = c[c1].x[i];
            c[c1].x[i] = c[c2].x[i];
        }
        for (int i = 0; i < pt_corte1; i++) {
            c[c2].x[i] = aux[i];
        }

        for (int i = 0; i < pt_corte2; i++) {
            aux[i] = c[c1].x[i];
            c[c1].x[i] = c[c2].x[i];
        }
        for (int i = 0; i < pt_corte2; i++) {
            c[c2].x[i] = aux[i];
        }

    }

    public void mutation(CHROMOSOMES c[]) {
        int m = new Random().nextInt(Main.SIZE_CHROMOSOMES);
        int c1 = new Random().nextInt(Main.SIZE_POPULATION);
        if (c[c1].x[m] == 0) {
            c[c1].x[m] = 1;
        } else {
            c[c1].x[m] = 0;
        }
    }

}
