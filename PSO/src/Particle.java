//package pso;

public class Particle {
    
    // position
    private double[] position; // current
    private double[] bestPos; // best
    
    // fitness
    private double fitness; // current
    private double bestFit; // best
    
    private double[] velocity;
    private int size;
    
    public Particle(double[] pos) {
        // current
        this.position = pos.clone();
        this.fitness = 0;
        
        // best
        this.bestPos = new double[pos.length];
        this.bestFit = Double.MAX_VALUE;
        
        this.velocity = new double[pos.length]; // initializa particle to 0
        this.size = this.position.length;
    }
    
    public void evaluateFitness() {
        double pos, cos, sum, total, p;
        sum = 0;
        
        for (int i = 0; i < this.position.length; i++) {
            // equation for sum
            p = this.position[i]; // position
            
            pos = p * p;
            cos = 10 * Math.cos(2 * Math.PI * p);
            
            pos -= cos;
            sum += pos;
            
        }
        
        total = (10 * this.position.length) + sum;
        this.fitness = total;
        
        // update local best fitness
        if (total < this.bestFit) {
            this.bestFit = total;
            this.bestPos = this.position.clone();
        }
        
    }
    
    public void updateVelocity(double inertia, double cognitive, double social, double[] global, double[] r1, double[] r2) {
        
        // vi(t+1) = wvi(t) + c1r1(y(t)(bestX) - xi(t)(currentX)) + c2r2(y'(t)(globalBestX) - xi(t)(currentX))
        // wvi(t)
        
        double[] oldVel = this.velocity.clone();
        for (int i = 0; i < this.size; i++)
            oldVel[i] *= inertia;
        
        // c1r1(y(t) - xi(t))
        double[] cog = new double[this.size];
        for (int i = 0; i < this.size; i++)
            cog[i] = cognitive * r1[i] * (this.bestPos[i] - this.position[i]);
        
        // c2r2(y'(t) - xi(t))
        double[] soc = new double[this.size];
        for (int i = 0; i < this.size; i++)
            soc[i] = social * r2[i] * (global[i] - this.position[i]);
        
        // vi(t+1) = ...
        for (int i = 0; i < this.size; i++)
            this.velocity[i] = oldVel[i] + cog[i] + soc[i];
        
        // update velocity
        for (int i = 0; i < this.size; i++)
            this.position[i] += this.velocity[i];
        
    }
    
    public double[] getPosition() {
        return this.position.clone();
    }
    
    public int size() {
        return this.size;
    }
    
    public double getFitness() {
        return this.fitness;
    }
    
    public double getBestFit() {
        return this.bestFit;
    }
    
    public double[] getVel() {
        return this.velocity;
    }
    
}
