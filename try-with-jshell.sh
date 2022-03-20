#!/usr/bin/env bash

set -o pipefail

rm -rf jshell/dependencies/

mvn dependency:copy-dependencies -DoutputDirectory=jshell/dependencies/ >/dev/null 2>&1

DEPS=$(ls -d -1 jshell/dependencies/** | tr '\n' ':')

jshell --class-path "${DEPS}target/poiji.jar" jshell/snippets
