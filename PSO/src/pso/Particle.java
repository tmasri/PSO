package pso;

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
        this.position = pos.clone();
        this.velocity = new double[pos.length];
        this.bestPos = new double[pos.length];
        this.size = this.position.length;
        this.bestFit = Double.MAX_VALUE;
        this.fitness = 0;
    }
    
    public void evaluateFitness() {
        double pos, cos, sum, total;
        sum = 0;
        
        for (int i = 0; i < this.position.length; i++) {
            // equation for sum
            pos = this.position[i];
            cos = this.position[i];
            
            pos *= pos;
            cos = 10 * Math.cos(2 * Math.PI * cos);
            
            pos -= cos;
            sum += pos;
            
        }
        
        total = (10 * this.position.length) + sum;
        if (total < this.bestFit) {
            this.bestFit = total;
            this.bestPos = this.position;
            System.out.println("new best fitness is " + total);
        }
        this.fitness = total;
        System.out.println("fitness = " + total);
        
//        System.out.println("Sum is " + pos);
        
    }
    
    public void updateVelocity(double inertia, double cognitive, double social, double[] global, double[] r1, double[] r2) {
        
        // vi(t+1) = wvi(t) + c1r1(y(t)(bestX) - xi(t)(currentX)) + c2r2(y'(t)(globalBestX) - xi(t)(currentX))
        // wvi(t)
        
        System.out.println("velocity: ");
        System.out.print("(");
        for (int i = 0; i < this.size; i++) {
            System.out.print(this.velocity[i] + ", ");
        }
        System.out.println(")");
        
        // inertia applies a portion (w) of the previous velocity to the current velocity
        double[] oldVel = this.velocity;
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
    
    public void move(double global) {
        // updating velocity
        // w -> applies a portion of the previous velocity to the current velocity --> typically between 0 and 1
        // c1 -> cognitive component --> between 0 and 2
        //      - high cognitive component gives more self-aware particles
        // c2 -> social component --> between 0 and 2
        //      - high social component gives more socially aware particles
        // vi(t+1) = wvi(t) + cognitive term(local/personal) + social term(global)
                    
        // best = personal best position in 30 dimensional space?
        // globalBest = global best position in 30 dimensional space
        // current = the particles current position
        
    }
    
    public double[] getPosition() {
        return this.position;
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
    
    public double[] getPos() {
        return this.position;
    }
    
    public double[] getVel() {
        return this.velocity;
    }
    
    public void getPersonalBest() {
        System.out.println("Personal best is: " + this.bestFit);
    }
    
    public void printPosition() {
        System.out.print("(");
        for (int i = 0; i < this.size; i++)
            System.out.print(this.position[i] + ", ");
        System.out.println(")");
    }
    
}
