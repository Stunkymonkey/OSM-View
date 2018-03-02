#!/bin/bash

BASE="$(dirname $(readlink -f $0))"

java -Xmx4098m -jar $BASE/target/OSM-View-G.jar $BASE/germany.fmi
