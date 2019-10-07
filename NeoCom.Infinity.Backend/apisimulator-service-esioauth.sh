#!/bin/bash
# - PARAMETERS & CONSTANTS
COMMAND=$1

SERVICE_PORT=6090
ADMIN_PORT=$(($SERVICE_PORT + 100))
SIMULATION_NAME='esioauth-simulation'

WORKING_DIRECTORY='/home/adam/Development/NeoCom/neocom-infinity/NeoCom.Infinity.Backend/backend.App'
#DOCKER_COMMAND="docker-compose --file src/test/resources/docker/docker-compose.yml "
APISIMULATOR_COMMAND="${WORKING_DIRECTORY}/src/test/apisimulator-http-1.4/bin/apisimulator"
APISIMULATOR_OPTIONS=" -Dapisimulator.log.level=debug "
export APISIMULATOR_LOG_LEVEL=debug

export APISIMULATOR_JAVA='/usr/lib/jvm/java-1.11.0-openjdk-amd64'

# - S T A R T
start() {
  cd ${WORKING_DIRECTORY}
  SERVICE_PORT=6090
  ADMIN_PORT=$(($SERVICE_PORT + 100))
  SIMULATION_NAME='esioauth-simulation'
  APISIMULATOR_SIMULATION="${WORKING_DIRECTORY}/src/test/resources/$SIMULATION_NAME"
  echo ">> Starting api simulator with: $APISIMULATOR_SIMULATION"
  echo ">>> Service port: $SERVICE_PORT"
  echo ">>> Administration port: $ADMIN_PORT"
  echo ">>> Simulation: $SIMULATION_NAME"
  $APISIMULATOR_COMMAND start $APISIMULATOR_SIMULATION -p ${SERVICE_PORT} -admin_port ${ADMIN_PORT} &
}
stop() {
  cd ${WORKING_DIRECTORY}
  echo "Stopping api simulator..."
  SIMULATION_NAME='esioauth-simulation'
  APISIMULATOR_SIMULATION="src/test/resources/$SIMULATION_NAME"
  $APISIMULATOR_COMMAND stop $APISIMULATOR_SIMULATION &
}

case $COMMAND in
'start')
  start
  ;;
'stop')
  stop
  ;;
'restart')
  stop
  start
  ;;
*)
  echo "Usage: $0 { start | stop | restart }"
  echo
  exit 1
  ;;
esac
exit 0
