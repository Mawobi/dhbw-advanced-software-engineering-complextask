# DHBW Mosbach INF19X - Advanced Software Engineering

This is a project for the course INF19X at DHBW Mosbach in Advanced Software Engineering hold by Prof. Dr. Carsten Müller.  
The project is based on the travelling salesman problem.
The objective is to find the shortest path between all cities with BruteForce and Swarm Optimization Algorithms.


## Specification of the Task

**Complex task | 55 points**

- The processing of this task takes place in a team with two students, in the exception a team with 3 students.

- **Implementation:** Java 17.0.2 | no modeling | no JUnit tests

- **Application 01** | 10 points
  - For the problem definition TSP with the data instance [a280](src/resources/a280.tsp) 
  a console application for the search with BruteForce.

- **Application 02** | 30 points
  - For the problem TSP with the data instance [a280](src/resources/a280.tsp) a console application for parallelized optimization
  for **parallelized optimization** with either **Particle Swarm Optimization**,
  **Ant Colony Optimization** or **Artificial Bee Colony**.
  - The search of the agents Particle, Ant or Bee is done with powerful technologies
  from the package **java.util.concurrent**.
  - The parameters are to be optimized in such a way that with respect to the well-known
  **Optimium 2579** a solution quality of at **least 95%** is reached.
  - The swarm behavior is to be logged in a log file in a comprehensible way.
  
- **Application 03** | 15 points
  - For the problem TSP with the data instance [a280](src/resources/a280.tsp) a console application is to be developed
  for the **parallelized search of a best possible parameter configuration for the algorithm
  algorithm** used in application 02. The best possible parameter configuration is stored in a JSON file.
  - The console application for application 02 is to be extended so that via command line
  `- best [filename]` to specify or apply the JSON file.

***Additional information in German can be found in the [Script](script.pdf) file.***