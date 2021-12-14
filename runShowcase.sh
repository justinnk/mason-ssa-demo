#!/bin/sh

classpath="target/demo-0.0.1-SNAPSHOT.jar:target/dependency/*"

java -cp "$classpath" demo.ssa.sir.SirModelWithUI
