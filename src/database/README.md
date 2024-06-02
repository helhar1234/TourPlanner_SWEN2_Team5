# backup erstellen
docker ps
docker exec -t <container_name_or_id> pg_dump -U postgres -d tour_planner_g5_db > backup.sql
!WICHTIG!
Nach dem Erstellen des Backups, musst du in backup.sql gehen 
und unten rechts von UTF-16 auf UTF-8 ändern!

# container starten
docker-compose up -d

# container beenden
docker-compose down -v

# DATEN FÜR PGADMIN
host: localhost
maintenencedb: postgres
user: postgres
pw: postgres