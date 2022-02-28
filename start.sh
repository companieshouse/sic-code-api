#!/bin/bash
APP_DIR=$(dirname "$0")
PORT=8080
java -Dserver.port="${PORT}" -jar "${APP_DIR}/sic-code-api.jar" 