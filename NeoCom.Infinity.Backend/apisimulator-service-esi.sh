#!/bin/bash
# - PARAMETERS & CONSTANTS
COMMAND=$1

SERVICE_PORT=6091
ADMIN_PORT=$(($SERVICE_PORT + 100))
SIMULATION_NAME='esi-simulation'

WORKING_DIRECTORY="${HOME}/Development/NeoCom/neocom-infinity/NeoCom.Infinity.Backend/backend.App"
APISIMULATOR_COMMAND="${WORKING_DIRECTORY}/src/test/apisimulator-http-1.4/bin/apisimulator"
export APISIMULATOR_LOG_LEVEL=debug

export APISIMULATOR_JAVA='/usr/lib/jvm/java-1.11.0-openjdk-amd64'

# - S T A R T
start() {
  cd ${WORKING_DIRECTORY}
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
  APISIMULATOR_SIMULATION="${WORKING_DIRECTORY}/src/test/resources/$SIMULATION_NAME"
  $APISIMULATOR_COMMAND stop "src/test/resources/$SIMULATION_NAME"
#  rm -rf *.log $APISIMULATOR_SIMULATION
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
