package pso;

import java.util.ArrayList;
import java.util.Random;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.IOException;

public class PSO {

    ArrayList<Particle> particles;
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
        double range = (5.12 - (-5.12)) - 5.12;
        
        // generate n random particles
        for (int i = 0; i < swarmSize; i++) {
            particle = new Particle(generate(range));
            particles.add(particle);
        }
        
        // initialize global best to the first particle
        globalPos = particles.get(0).getVel();
        globalFitness = particles.get(0).getBestFit();
        
        // move particles, update global best
        for (int i = 0; i < 1000; i++) {
            
            // evaluating fitness
            for (int j = 0; j < particles.size(); j++) {
                // calculate fitness
                particles.get(j).evaluateFitness();
            }
            
            // update neighborhood best position
            for (int j = 0; j < swarmSize; j++) {
                if (particles.get(j).getBestFit() < globalFitness) {
                    if (inBound(particles.get(j).getPosition())) {
                        globalPos = particles.get(j).getPosition();
                        globalFitness = particles.get(j).getBestFit();
                    }
                }
            }
            
            // update velocity
            for (int j = 0; j < particles.size(); j++) {
                particles.get(j).updateVelocity(W, C1, C2, globalPos, generate(1), generate(1));
            }
            
            // move particles
            for (int j = 0; j < particles.size(); j++) {
                particles.get(j).move();
            }

        }
        
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
        
        for (int i = 0; i < position.length; i++)
            position[i] = Math.random() * r;
        
        return position;
        
    }
    
    public double getGlobalBest() {
        return this.globalFitness;
    }
    
    public static void main(String[] args) {
        
        double bestPSO = Double.MAX_VALUE;
        double sum = 0;
        // have 2 double arrays one for w and one for c
        
        for (int i=0; i< 100; i++) {
            PSO pso = new PSO();
            bestPSO = Math.min(bestPSO, pso.getGlobalBest());
            System.out.println((i+1)  + "- Best Fitness: "+bestPSO);
            sum += bestPSO;
        }
        
        System.out.println("Mean = " + (sum/100));
 
    }
}
