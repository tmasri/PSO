package net.metaopt.swarm.pso;

import net.metaopt.swarm.FitnessFunction;
import net.metaopt.swarm.pso.neighbour.Neighborhood;
import net.metaopt.swarm.pso.neighbour.Neighborhood1D;

/**
 * PSO test on Rosenbrock function (30 dimensions)
 * General form
 * 
 * 		f( x1 , x2 ) = \sum_{i=1}^{n} { 100 (x_{i+1} - x_i^2)^2 + (1 - x_i)^2 }
 * 		
 * @author Alvaro Jaramillo Duque <aduque@inescporto.pt>
 */
public class RosenbrockFunction30 {
    
    public void optimize() {
        
        System.out.println("Begin: Example Rosenbrock\n");
        Swarm swarm = new Swarm(1000, new Particle(30), new FitnessFunction(false) {

            @Override
            public double evaluate(double[] position) {
                double f = 0;
		for (int i = 0; i < (position.length - 1); i++) {
                    double p1 = position[i] * position[i]; 	
                    double p2 = (1 - position[i]); 			
                    p2 *= p2;  								
                    double p3 = position[i + 1] - p1;  		
                    p3 *= p3;  								
                    f += 100 * p3 + p2; 
		}
		return f; 
            }
                    
        });
        // Use neighborhood
        Neighborhood neigh = new Neighborhood1D(Swarm.DEFAULT_NUMBER_OF_PARTICLES / 5, true);
        swarm.setNeighborhood(neigh);
        swarm.setNeighborhoodIncrement(0.9);

        // Set position (and velocity) constraints. I.e.: where to look for solutions
        swarm.setInertia(0.9);
        swarm.setGlobalIncrement(0.9);
        swarm.setParticleIncrement(0.9);

        swarm.setMaxPosition(100);
        swarm.setMinPosition(0);
        swarm.setMaxMinVelocity(0.1);

        int numberOfIterations = 50000;

        for (int i = 0; i < numberOfIterations; i++) {
            swarm.evolve();
            if (i % 1000 == 0) {
                int idxBest = swarm.getBestParticleIndex();
                System.out.println("Iteration: " + i + "\tBest particle (" + idxBest + "):\t" + swarm.getParticle(idxBest).toString());
            }
        }
        System.out.println(swarm.toStringStats());
    }
    
    public static void main(String[] args) { 
        new RosenbrockFunction30().optimize();
    }
    
}
