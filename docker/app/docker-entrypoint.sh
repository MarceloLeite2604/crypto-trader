#!/bin/sh

attempts=0;

echo "Checking if database service is reachable.";
until nc -z "${DATABASE_HOST}" "${DATABASE_PORT}";
do
  if [ $attempts -eq 20 ]; then
    echo "Database service cannot be reached yet.";
    attempts=0;
  fi;
  sleep 0.5;
  attempts=$((attempts + 1));
done;

exec "$@";