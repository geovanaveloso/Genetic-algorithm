import java.util.Random;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author geovana
 */
public class CHROMOSOMES implements Comparable<CHROMOSOMES> {

    int x[] = new int[Main.SIZE_CHROMOSOMES];

    public CHROMOSOMES() {
        Random r = new Random();
        for (int i = 0; i < Main.SIZE_CHROMOSOMES; i++) {
            x[i] = r.nextInt(2);
        }
    }

    public CHROMOSOMES(int[] x) {
        for (int i = 0; i < x.length; i++) {
            this.x[i] = x[i];
        }
    }

    @Override
    public String toString() {
        String msg = "[";
        for (int i = 0; i < x.length; i++) {
            msg += x[i] + ",";
        }
        msg = msg.substring(0, msg.lastIndexOf(","));
        msg += "]";
        return msg;
    }

    @Override
    public int compareTo(CHROMOSOMES c) {
        AG g = new AG();
        double f1 = g.calculateFitness(this);
        double f2 = g.calculateFitness(c);
        if ( f1 < f2) {
            return 1;
        }
        if (f1 > f2) {
            return -1;
        }
        return 0;
    }

}
