import java.util.ArrayList;
import java.util.Collections;

public class PSO {

    ArrayList<Particle> particles;
    private double globalFitness;
    private double[] globalPos;
    private int swarmSize = 30;
    private int dimensions = 30;
    
    public PSO(int n) {
        
        double w = getW(n);
        double c1 = getC(n);
        double c2 = getC(n);
        
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
        for (int i = 0; i < 2000; i++) {
            
            for (int j = 0; j < particles.size(); j++) {
                // calculate fitness
                particles.get(j).evaluateFitness();
            }
            
            // update neighborhood best position
            for (int j = 0; j < swarmSize; j++) {
                if (particles.get(j).getBestFit() < globalFitness) {
                    globalPos = particles.get(j).getPosition();
                    globalFitness = particles.get(j).getBestFit();
                }
            }
            
            // update velocity
            for (int j = 0; j < particles.size(); j++) {
                particles.get(j).updateVelocity(w, c1, c2, globalPos, generate(1), generate(1));
                particles.get(j).move();
            }

        }
        
        
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
    
    public double getW(int i) {
        
        if (i == 0) return 0.729844;
        if (i == 1) return 0.4;
        if (i == 2) return 1.0;
        else return -1.0;
        
    }
    
    public double getC(int i) {
        
        if (i == 0) return 1.496180;
        if (i == 1) return 1.2;
        if (i == 2) return 2.0;
        else return 2.0;
        
    }
    
    public static void calculations(double mean, ArrayList<Double> best) {
        
        double sum = 0;
        
        // mean
        System.out.println("Mean = " + mean);
        
        // standard deviation
        for (int i = 0; i < best.size(); i++) {
            sum += Math.pow(best.get(i) - mean, 2);
        }
        System.out.println("SD = " + Math.sqrt(0.01 * sum));
        
        // median
        Collections.sort(best);
        System.out.println("Median = " + best.get(best.size()/2));
        System.out.println("");
        
    }
    
    public static void main(String[] args) {
        
        double mean, sum;
        ArrayList<Double> best = new ArrayList<Double>();
        
        for (int i = 0; i < 4; i++) {
            
            System.out.println("TEST " + (i+1));
            sum = 0;
            
            for (int j=0; j< 100; j++) {
                PSO pso = new PSO(i);
                if (j < 9) System.out.print("00" + (j+1) + " ");
                else if (j >= 9 && j < 99) System.out.print("0" + (j+1) + " ");
                else System.out.print((j+1) + " ");
                if ((j+1) % 25 == 0) System.out.println("");
                // System.out.println((j+1)  + "- Best Fitness: "+ pso.getGlobalBest());
                sum += pso.getGlobalBest();
                
                best.add(pso.getGlobalBest());
            }
            System.out.println("");
            
            mean = sum/100;
            calculations(mean, best);
            best.clear();
        }
 
    }
}
