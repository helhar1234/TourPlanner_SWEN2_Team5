
# Anleitung für das Management der Docker-Container

Dieses Dokument bietet eine klare Anleitung, wie man die Datenbank-Änderungen pushen und wie man die Docker-Container und Volumes effektiv verwalten kann.

## DB Pushen

Führen Sie diese Schritte aus, wenn in der Datenbank Änderungen vorgenommen wurden oder wichtige Daten hinzugefügt wurden.

### 1. Sichern der Datenbankdaten
Um die aktuellen Datenbankdaten zu sichern, verwenden Sie den folgenden Befehl:

\```bash
docker exec -t tour_planner_g5 pg_dumpall -c -U postgres > backup.sql
\```

### 2. Image bauen und pushen
Nach dem Sichern der Daten bauen Sie das Docker Image und pushen es zu Docker Hub:

\```bash
docker build -t helhar1234/tour_planner_g5 .
docker push helhar1234/tour_planner_g5
\```

## DB Pullen

Verwenden Sie diese Anweisungen, um den Container zu starten und später zu löschen, inklusive der Volumes, um eine saubere Umgebung zu gewährleisten.

### 1. Container starten
Um den Container zu starten, nutzen Sie:

\```bash
docker-compose up -d
\```

### 2. Container löschen
Um den Container wieder zu löschen, verwenden Sie:

\```bash
docker-compose down
\```

### 3. Container und Volume löschen
Wenn Sie auch das Volume entfernen möchten, führen Sie folgenden Befehl aus:

\```bash
docker-compose down --volumes
\```
