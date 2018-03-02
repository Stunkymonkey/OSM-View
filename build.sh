#!/bin/bash

BASE="$(dirname $(readlink -f $0))"

cd $BASE && mvn clean package
