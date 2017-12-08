package pso;

import java.util.ArrayList;
import java.util.Random;

public class PSO {

    ArrayList<Particle> particles;
    Random rand;
    private double globalFitness;
    private double[] globalPos;
    private int swarmSize = 30;
    private int dimensions = 30;
    
    private static final double W = 0.729844;
    private static final double C1 = 1.496180;
    private static final double C2 = 1.496180;
    
    public PSO() {
        
        // initialize particles
        particles = new ArrayList<Particle>();
        Particle particle;
        rand = new Random();
        double range = (5.12 - (-5.12)) - 5.12;
        
        // generate n random particles
        for (int i = 0; i < swarmSize; i++) {
            particle = new Particle(generate(range));
            particles.add(particle);
        }
        
        // do updates
        double pFitness;
        boolean inBounds = true;
        globalPos = particles.get(0).getVel();
        globalFitness = Double.MAX_VALUE;
        for (int i = 0; i < 5000; i++) {
            
            // evaluating fitness
            // updating personal best position
            for (int j = 0; j < particles.size(); j++) {
                // calculate fitness
                particles.get(j).evaluateFitness();
            }
            
            
            
            
//            System.out.println("DONE EVALUATING FITNESS FOR THIS RUN");
            
            // update neighborhood best position
            // go through particles
            for (int j = 0; j < swarmSize; j++) {
                if (particles.get(j).getBestFit() < globalFitness) {
                    if (inBound(particles.get(j).getPosition())) {
                        globalPos = particles.get(j).getPosition();
                        globalFitness = particles.get(j).getBestFit();
                        System.out.println("New global best fitness is " + globalFitness);
                    }
                }
            }
            
            
            
            
            for (int j = 0; j < particles.size(); j++) {
                // update velocity
                particles.get(j).updateVelocity(W, C1, C2, globalPos, generate(1), generate(1));
            }

        }

        
        for (int i = 0; i < swarmSize; i++) {
            System.out.println("Fitness = " + particles.get(i).getFitness());
        }
        System.out.println("global best = " + globalFitness);
        
        
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
