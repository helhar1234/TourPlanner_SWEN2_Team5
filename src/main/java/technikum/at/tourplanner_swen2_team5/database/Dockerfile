FROM postgres:latest

# Set environment variables (optional)
ENV POSTGRES_DB=tour_planner_g5
ENV POSTGRES_USER=postgres
ENV POSTGRES_PASSWORD=postgres

# Copy the backup file into the container
COPY ./backup.sql /docker-entrypoint-initdb.d/

# The file in docker-entrypoint-initdb.d will be automatically executed during container startup

# steps in Local CMD in database folder
# docker exec -t tour_planner_g5 pg_dumpall -c -U postgres > backup.sql
# docker build -t helhar1234/tour_planner_g5 .
# docker login
# docker push helhar1234/tour_planner_g5

# start container
#docker run --name mein_tour_planner -e POSTGRES_DB=dbname -e POSTGRES_USER=user -e POSTGRES_PASSWORD=password -d -p 5432:5432 helhar1234/tour_planner_g5



