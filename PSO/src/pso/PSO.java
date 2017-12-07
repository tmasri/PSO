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
        globalVel = particles.get(0).getVel();
        for (int i = 0; i < 500; i++) {
            for (int j = 0; j < particles.size(); j++) {
                // run through the equation that she gave us in the assignment
                particles.get(j).evaluateFitness();
            }
            
            for (int j = 0; j < n; j++) {
                pFitness = particles.get(j).getFitness();
                if (pFitness < globalFitness && (pFitness >= -5.12 || pFitness <= 5.12)) {
                    globalFitness = particles.get(j).getFitness();
                    globalVel = particles.get(j).getPosition();
                }
            }
            
//            for (int j = 0; j < particles.size(); j++)
//                particles.get(j).getPersonalBest();
            
            for (int j = 0; j < particles.size(); j++) {
                // run through the equation on the slides
                
                particles.get(j).updateVelocity(W, C1, C2, globalVel, generate(n, range), generate(n, range));
            }
            
        }

//        for (int i = 0; i < 30; i++)
//            particles.get(i).printPosition();
        for (int i = 0; i < n; i++) {
            System.out.println("Fitness = " + particles.get(i).getFitness());
        }
        
        
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
