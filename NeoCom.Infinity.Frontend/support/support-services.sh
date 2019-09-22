#!/bin/bash
# - PARAMETERS & CONSTANTS
COMMAND=$1
APISIMULATOR_COMMAND='apisimulator/apisimulator-http-1.4/bin/apisimulator'
APISUMULATOR_ADMIN_PORT=' -admin_port 6191 '
APISIMULATOR_OPTIONS=' -p 6091 '
APISIMULATOR_SIMULATION='apisimulator/neocom-backend-simulation'
WORKING_DIRECTORY='/home/adam/Development/NeoCom/neocom-infinity/NeoCom.Infinity.Frontend/support'

export APISIMULATOR_JAVA='/usr/lib/jvm/java-1.11.0-openjdk-amd64'

# - S T A R T
start() {
  cd ${WORKING_DIRECTORY}
  echo "Starting api sumulator with: $APISIMULATOR_SIMULATION"
  $APISIMULATOR_COMMAND start $APISIMULATOR_SIMULATION $APISIMULATOR_OPTIONS $APISUMULATOR_ADMIN_PORT &
}
stop() {
  cd ${WORKING_DIRECTORY}
  echo "Stopping api sumulator..."
  $APISIMULATOR_COMMAND stop $APISIMULATOR_SIMULATION $APISUMULATOR_ADMIN_PORT &
}

case $COMMAND in
'start')
  start
  ;;
'stop')
  stop
  ;;
*)
  echo "Usage: $0 { start | stop }"
  echo
  exit 1
  ;;
esac
exit 0
