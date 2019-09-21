#!/bin/bash
# - S T A R T
cd ~/Development/NeoCom/neocom-infinity/NeoCom.Infinity.Backend/backend.App
docker-compose --file src/test/resources/docker/docker-compose.yml up &
src/test/resources/apisimulator/apisimulator-http-1.4/bin/apisimulator start src/test/resources/apisimulator/neocom-simulation &

