
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
        if (total < this.bestFit && inBounds()) {
            this.bestFit = total;
            this.bestPos = this.position.clone();
        }
        
    }
    
    private boolean inBounds() {
        
        // check if particle is out of bounds
        for (int i = 0; i < 30; i++)
            if (this.position[i] < -5.12 || this.position[i] > 5.12)
                return false;
        
        return true;
    }
    
    public void updateVelocity(double inertia, double cognitive, double social, double[] global, double[] r1, double[] r2) {
        
        double[] oldVel = this.velocity.clone();
        double cog, soc;
        
        for (int i = 0; i < this.size; i++) {
            
            // wvi(t)
            oldVel[i] *= inertia;
            
            // c1r1(y(t) - xi(t))
            cog = cognitive * r1[i] * (this.bestPos[i] - this.position[i]);
            
            // c2r2(y'(t) - xi(t))
            soc = social * r2[i] * (global[i] - this.position[i]);
            
            // update velocity
            this.velocity[i] = oldVel[i] + cog + soc;
            
        }
        
    }
    
    public void move() {
        // update velocity
        for (int i = 0; i < this.size; i++) {
            this.position[i] += this.velocity[i];   
        }
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
