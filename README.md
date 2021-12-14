# SSA extension for MASON: Demo models

Collection of Simulations utilizing the [SSA extension](https://github.com/justinnk/mason-ssa) for [MASON](https://cs.gmu.edu/~eclab/projects/mason/) for demonstration purposes.

## Contents

- `pom.xml` Maven [Project Object Model](https://maven.apache.org/guides/introduction/introduction-to-the-pom.html) for building a project with the extension
    - contains the dependencies on `MASON`, `ssamason` and `aspectj`
    - contains an aspectj plugin for weaving the aspects into the models
- `demo.vanilla.sirs` SIRS model using manually woven code
- `demo.ssa.sir` SIR Model using one of the extensions SSA simulators (default: Aspect Oriented Next Reaction Method)
- `demo.ssa.sirs` SIRS model using one of the extensions SSA simulators (default: Aspect Oriented Next Reaction Method)

## Getting Started

### :white_check_mark: Prerequisites

- Maven (tested with 3.8.2)
- Java 8 (or compatible)
- MASON
  - the version found on the Maven Central Repository is out of date (14.0)
  - Version 20 can be installed from its [GitHub repository](https://github.com/eclab/mason)

### :cd: Installation

- clone this repository: `git clone https://github.com/justinnk/mason-ssa-demo.git`
- navigate into the root folder: `cd mason-ssa-demo`

### :rocket: Quickstart

- run `sh build.sh` to build the project
- run `sh runShowcase.sh` to run the showcase
- hit the "play" (triangle shaped) button to start a new simulation run

## :arrow_forward: Usage

- configure the simulator you want to use in the constructor of `demo.ssa.sirs.SirsModelWithUI` or `demo.ssa.sir.SirModelWithUI`
- build the project by running `sh build.sh`
- advanced users can also run maven commands without using the script
  - keep in mind that it is necessary to somehow include the dependencies in the java classpath for execution
  - in the scripts, this is done using the maven dependency plugin and the goal `dependency:copy-dependencies` to export them to the target directory and then adding them to the classpath
- run a main class using the following command: `java -cp "target/demo-0.0.1-SNAPSHOT.jar:target/dependency/*" <full-path-to-main-class>`
- see `runShowcase.sh` for an example

## Documentation

See the repository of the [extension](https://github.com/justinnk/mason-ssa) for further information.
