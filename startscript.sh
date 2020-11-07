delScript="deletescript.sh"

python3 -m http.server -d "/home/pi/DAT250/assignmentB/src/main/java/com/poll/B" &
pythonId=$!
mvn spring-boot:run &
mavenId=$!

cat > $delScript <<EOF
echo  "Shutting processes down..."
kill -SIGTERM $pythonId
kill -SIGTERM $mavenId
wait $pythonId $mavenId
echo "Processes shut down."
EOF

echo "Processes started. Run ${delScript} to stop processes."
chmod +x $delScript
