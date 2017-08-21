#!/usr/bin/env bash
#
# Copy env variables to app module gradle properties file
#
set +x
printenv | tr ' ' '\n' > keystore.properties
set -x