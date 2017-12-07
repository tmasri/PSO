package pso;

import java.util.ArrayList;
import java.util.Random;

public class PSO {

    ArrayList<Particle> particles;
    Random rand;
    private double globalFitness;
    private double[] globalVel;
    private int n = 10;
    private double range;
    
    private static final double W = 0.4;
    private static final double C1 = 1.2;
    private static final double C2 = 1.2;
    
    public PSO() {
        
        // initialize particles
        particles = new ArrayList<Particle>();
        Particle particle;
        rand = new Random(10);
        range = (5.12 - (-5.12)) - 5.12;
        
        // generate n random particles
        for (int i = 0; i < n; i++) {
            particle = new Particle(generate());
            particles.add(particle);
        }
        
        for (int i = 0; i < n; i++)
            particles.get(i).printPosition();
        
        // do updates
        double pFitness;
        boolean inBounds = true;
        globalVel = particles.get(0).getVel();
        globalFitness = 9999;
        for (int i = 0; i < 50; i++) {
            System.out.println("ITERATION " + i);
            for (int j = 0; j < particles.size(); j++) {
                // calculate fitness
                particles.get(j).evaluateFitness();
            }
            
            // go through particles
            for (int j = 0; j < n; j++) {
                if (particles.get(j).getFitness() < globalFitness) {
                    for (int k = 0; k < particles.get(j).size(); k++) {
                        if (outsideBounds(j,k)) {
                            System.out.println("j = " + j + ", k = " + k);
                            inBounds = false;
                            if (!inBounds) System.out.println("inBounds changed\niteration " + i);
                            break;
                        }
                    }
                    if (inBounds) {
                        System.out.println("gets here");
                        globalVel = particles.get(j).getVel();
                        globalFitness = particles.get(j).getFitness();
                        System.out.println("global fitness " + particles.get(j).getFitness());
                    }
                    inBounds = true;
                } 
//                else {
//                    System.out.println("fitness worse than global");
//                }
            }
//            System.out.println("global best = " + globalFitness);
//            for (int j = 0; j < particles.size(); j++)
//                particles.get(j).getPersonalBest();
            
            for (int j = 0; j < particles.size(); j++) {
                // update velocity
//                System.out.println("updating velocity");
                particles.get(j).updateVelocity(W, C1, C2, globalVel, generate(), generate());
            }
            
            // this should print every iteration
            for (int j = 0; j < n; j++)
                particles.get(j).printPosition();
            
        }

        
        for (int i = 0; i < n; i++) {
            System.out.println("Fitness = " + particles.get(i).getFitness());
        }
        System.out.println("global best = " + globalFitness);
        
        
    }
    
    private boolean outsideBounds(int i, int j) {
        return particles.get(i).seek(j) >= -5.12 && particles.get(i).seek(j) <= 5.12;
    }
    
    public double[] generate() {
        
        double[] position = new double[n];
        
        for (int i = 0; i < n; i++)
            position[i] = rand.nextDouble() * range;
        
        return position;
        
    }
    
    public static void main(String[] args) {
        new PSO();
    }
    
}
