#!/bin/bash
#
# Start script for sic-code-api

PORT=8080

exec java -jar -Dserver.port="${PORT}" "sic-code-api.jar"