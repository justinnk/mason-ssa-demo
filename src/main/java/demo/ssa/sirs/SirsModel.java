/*
 * Copyright 2021 Justin Kreikemeyer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package demo.ssa.sirs;

import demo.InfectionState;
import org.justinnk.masonssa.extension.SSASimState;
import org.justinnk.masonssa.extension.graphs.ErdosRenyiGraphCreator;
import org.justinnk.masonssa.extension.graphs.GraphCreator;
import org.justinnk.masonssa.extension.ssa.FirstReactionMethod;
import org.justinnk.masonssa.extension.ssa.StochasticSimulationAlgorithm;
import sim.field.continuous.Continuous2D;
import sim.field.network.Network;
import sim.util.Bag;
import sim.util.Double2D;
import sim.util.Interval;

/* TODO: at the end add serialisability to extension. */

/**
 * Basic network-based susceptible-infectious-recovered-susceptible model using the SSA-extension to
 * MASON.
 */
public class SirsModel extends SSASimState {

  private static final long serialVersionUID = 1L;

  public static void main(String[] args) {
    doLoop(SirsModel.class, args);
    System.exit(0);
  }

  public Continuous2D world = new Continuous2D(1.0, 100.0, 100.0);
  public Network contacts = new Network(false);
  public GraphCreator graph;

  private int numHumans = 100;

  public int getNumHumans() {
    return numHumans;
  }

  public void setNumHumans(int value) {
    numHumans = value;
  }

  private int initialInfected = 45;

  public int getInitialInfected() {
    return initialInfected;
  }

  public void setInitialInfected(int value) {
    initialInfected = value;
  }

  private int initialRecovered = 45;

  public int getInitialRecovered() {
    return initialRecovered;
  }

  public void setInitialRecovered(int value) {
    initialRecovered = value;
  }

  public Object domInitialInfected() {
    return new Interval(0, numHumans);
  }

  private double recoveryTime = 20.0;

  public double getRecoveryRate() {
    return 1.0 / recoveryTime;
  }

  private double infectionTime = 5.0;

  public double getInfectionRate() {
    return 1.0 / infectionTime;
  }

  private double loseImmunityTime = 20.0;

  public double getLoseImmunityRate() {
    return 1.0 / loseImmunityTime;
  }

  private double spontaneousInfectionRate = 200.0;

  public double getSpontaneousInfectionTime() {
    return 1.0 / spontaneousInfectionRate;
  }

  public int getNumSusceptible() {
    Bag humans = contacts.getAllNodes();
    int num = 0;
    for (int i = 0; i < humans.size(); i++) {
      if (((Human) humans.get(i)).infectionState == InfectionState.SUSCEPTIBLE) {
        num += 1;
      }
    }
    return num;
  }

  public int getNumInfected() {
    Bag humans = contacts.getAllNodes();
    int num = 0;
    for (int i = 0; i < humans.size(); i++) {
      if (((Human) humans.get(i)).infectionState == InfectionState.INFECTIOUS) {
        num += 1;
      }
    }
    return num;
  }

  public int getNumRecovered() {
    Bag humans = contacts.getAllNodes();
    int num = 0;
    for (int i = 0; i < humans.size(); i++) {
      if (((Human) humans.get(i)).infectionState == InfectionState.RECOVERED) {
        num += 1;
      }
    }
    return num;
  }

  public SirsModel(long seed) {
    super(seed, new FirstReactionMethod());
    this.graph = new ErdosRenyiGraphCreator(42, 0.2);
  }

  public SirsModel(long seed, StochasticSimulationAlgorithm simulator, GraphCreator graph) {
    super(seed, simulator);
    this.graph = graph;
  }

  public void start() {
    super.start();
    world.clear();
    contacts.clear();
    for (int i = 0; i < numHumans; i++) {
      Human h = new Human(this);
      /* Set initial infection */
      if (i < initialInfected) {
        h.infectionState = InfectionState.INFECTIOUS;
      } else if (i < initialInfected + initialRecovered) {
        h.infectionState = InfectionState.RECOVERED;
      }
      /* Place humans in grid shape */
      int rows = (int) Math.sqrt(numHumans);
      world.setObjectLocation(
          h,
          new Double2D(
              world.getWidth() * 0.1 + (float) (i / rows) * 8.0,
              world.getHeight() * 0.1 + (i % rows) * 8.0));
      contacts.addNode(h);
    }
    graph.create(contacts);
  }

  @Override
  public void finish() {
    super.finish();
  }
}
