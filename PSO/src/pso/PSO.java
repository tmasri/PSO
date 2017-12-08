package pso;

import java.util.ArrayList;
import java.util.Random;

public class PSO {

    ArrayList<Particle> particles;
    private double globalFitness;
    private double[] globalPos;
    private int swarmSize = 30;
    private int dimensions = 30;
    
    private static final double W = 0.4;
    private static final double C1 = 1.2;
    private static final double C2 = 1.2;
    
    public PSO() {
        
        double mean = 0;
        
        // initialize particles
        particles = new ArrayList<Particle>();
        Particle particle;
        double range = (5.12 - (-5.12)) - 5.12;
        for (int m = 0; m < 100; m++) {
            System.out.println(m+1);
        // generate n random particles
        for (int i = 0; i < swarmSize; i++) {
            particle = new Particle(generate(range));
            particles.add(particle);
        }
        
        // do updates
        globalPos = particles.get(0).getVel();
        globalFitness = Double.MAX_VALUE;
        
        for (int i = 0; i < 5000; i++) {
            
            // evaluating fitness
            // updating personal best position
            for (int j = 0; j < particles.size(); j++) {
                // calculate fitness
                particles.get(j).evaluateFitness();
            }
            
            // update neighborhood best position
            // go through particles
            for (int j = 0; j < swarmSize; j++) {
                if (particles.get(j).getBestFit() < globalFitness) {
                    if (inBound(particles.get(j).getPosition())) {
                        globalPos = particles.get(j).getPosition();
                        globalFitness = particles.get(j).getBestFit();
//                        System.out.println("New global best fitness is " + globalFitness);
                    }
                }
            }
            
            for (int j = 0; j < particles.size(); j++) {
                // update velocity
                particles.get(j).updateVelocity(W, C1, C2, globalPos, generate(1), generate(1));
            }

        }
        mean += globalFitness;
        }
        
        System.out.println("Mean: " + (mean/100));
        
//        System.out.println("global best = " + globalFitness);
        
        
    }
    
    private boolean inBound(double[] position) {
        
        // check if particle is out of bounds
        for (int i = 0; i < 30; i++)
            if (position[i] < -5.12 || position[i] > 5.12)
                return false;
        
        return true;
    }
    
    public double[] generate(double r) {
        
        double[] position = new double[dimensions];
        
        for (int i = 0; i < 30; i++)
            position[i] = Math.random() * r;
        
        return position;
        
    }
    
    public static void main(String[] args) {
        new PSO();
    }
    
}
