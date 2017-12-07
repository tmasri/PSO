package pso;

import java.util.ArrayList;
import java.util.Random;

public class PSO {

    ArrayList<Particle> particles;
    Random rand;
    private double globalFitness;
    private double[] globalVel;
    
    private static final double W = 0.4;
    private static final double C1 = 1.2;
    private static final double C2 = 1.2;
    
    public PSO() {
        
        // initialize particles
        particles = new ArrayList<Particle>();
        Particle particle;
        rand = new Random(30);
        int n = 30;
        double range = (5.12 - (-5.12)) - 5.12;
        
        for (int i = 0; i < 30; i++) {
            particle = new Particle(generate(n,range));
            particles.add(particle);
        }
        
        // do updates
        double pFitness;
        boolean inBounds = true;
        globalVel = particles.get(0).getVel();
        globalFitness = 9999;
        for (int i = 0; i < 500; i++) {
            for (int j = 0; j < particles.size(); j++) {
                // calculate fitness
                particles.get(j).evaluateFitness();
            }
            
            for (int j = 0; j < n; j++) {
                if (particles.get(j).getFitness() < globalFitness) {
                    for (int k = 0; k < particles.get(j).size(); k++) {
                        if (outsideBounds(j,k)) {
                            System.out.println("out");
                            inBounds = false;
                        }
                    }
                    if (inBounds) {
                        globalVel = particles.get(j).getVel();
                        globalFitness = particles.get(j).getFitness();
                    }
                }
            }
            
//            for (int j = 0; j < particles.size(); j++)
//                particles.get(j).getPersonalBest();
            
            for (int j = 0; j < particles.size(); j++) {
                // update velocity
                particles.get(j).updateVelocity(W, C1, C2, globalVel, generate(n, range), generate(n, range));
            }
            
        }

//        for (int i = 0; i < 30; i++)
//            particles.get(i).printPosition();
        for (int i = 0; i < n; i++) {
            System.out.println("Fitness = " + particles.get(i).getFitness());
        }
        
        
    }
    
    private boolean outsideBounds(int i, int j) {
        return particles.get(i).seek(j) >= -5.12 && particles.get(i).seek(j) <= 5.12;
    }
    
    public double[] generate(int n, double range) {
        
        double[] position = new double[n];
        
        for (int i = 0; i < n; i++)
            position[i] = rand.nextDouble() * range;
        
        return position;
        
    }
    
    public static void main(String[] args) {
        new PSO();
    }
    
}
