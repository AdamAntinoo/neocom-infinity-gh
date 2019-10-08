#!/bin/bash
# - PARAMETERS & CONSTANTS
COMMAND=$1

WORKING_DIRECTORY='/home/adam/Development/NeoCom/neocom-infinity/NeoCom.Infinity.Backend/backend.App'
DOCKER_COMMAND="docker-compose --file src/test/resources/docker/docker-compose.yml "

# - S T A R T
start() {
  cd ${WORKING_DIRECTORY}
  $DOCKER_COMMAND up &
}
stop() {
  cd ${WORKING_DIRECTORY}
  $DOCKER_COMMAND down &
}

case $COMMAND in
'start')
  start
  ;;
'stop')
  stop
  ;;
#'restart')
#  stop
#  start
#  ;;
*)
  echo "Usage: $0 { start | stop | restart }"
  echo
  exit 1
  ;;
esac
exit 0
