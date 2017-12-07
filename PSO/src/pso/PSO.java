package pso;

import java.util.ArrayList;
import java.util.Random;

public class PSO {

    ArrayList<Particle> particles;
    Random rand;
    private double globalFitness;
    private double[] globalPos;
    private int n = 30;
    
    private static final double W = 0.4;
    private static final double C1 = 1.2;
    private static final double C2 = 1.2;
    
    public PSO() {
        
        // initialize particles
        particles = new ArrayList<Particle>();
        Particle particle;
        rand = new Random();
        double range = (5.12 - (-5.12)) - 5.12;
        
        // generate n random particles
        for (int i = 0; i < n; i++) {
            particle = new Particle(generate(range));
            particles.add(particle);
        }
        
//        for (int i = 0; i < n; i++)
//            particles.get(i).printPosition();
        
        // do updates
        double pFitness;
        boolean inBounds = true;
        globalPos = particles.get(0).getVel();
        globalFitness = 9999;
        for (int i = 0; i < 500; i++) {
            System.out.println("ITERATION " + i);
            for (int j = 0; j < particles.size(); j++) {
                // calculate fitness
                particles.get(j).evaluateFitness();
            }
            System.out.println("DONE EVALUATING FITNESS FOR THIS RUN");
            
            // when you want to pass an array from one class
            // to another is it better to clone it or should
            // you just pass it
            
            // go through particles
            for (int j = 0; j < n; j++) {
                if (particles.get(j).getBestFit() < globalFitness) {
                    if (inBound(particles.get(j).getPosition())) {
                        System.out.println("in bounds");
                        globalPos = particles.get(j).getPos();
                        globalFitness = particles.get(j).getBestFit();
                    } else {
                        System.out.println("not in bounds");
                    }
                } else {
                    System.out.println(particles.get(j).getFitness() + " is not better than global " + globalFitness);
                }
            }
//            System.out.println("global best = " + globalFitness);
//            for (int j = 0; j < particles.size(); j++)
//                particles.get(j).getPersonalBest();
            
            for (int j = 0; j < particles.size(); j++) {
                // update velocity
//                System.out.println("updating velocity");
                particles.get(j).updateVelocity(W, C1, C2, globalPos, generate(1), generate(1));
            }
            
            // this should print every iteration
//            for (int j = 0; j < n; j++)
//                particles.get(j).printPosition();
            
            System.out.println("");
            System.out.println("");

        }

        
        for (int i = 0; i < n; i++) {
            System.out.println("Fitness = " + particles.get(i).getBestFit());
        }
        System.out.println("global best = " + globalFitness);
        
        
    }
    
    private boolean inBound(double[] position) {
        
        for (int i = 0; i < n; i++)
            if (position[i] < -5.12 && position[i] > 5.12)
                return false;
        
        return true;
    }
    
    public double[] generate(double r) {
        
        double[] position = new double[n];
        
        for (int i = 0; i < n; i++)
            position[i] = Math.random() * r;
        
        return position;
        
    }
    
    public static void main(String[] args) {
        new PSO();
    }
    
}
