delScript="deletescript.sh"

python3 -m http.server -d "/home/pi/DAT250/assignmentB/src/main/java/com/poll/B" &
pythonId=$!
mvn spring-boot:run &
mavenId=$!

cat > $delScript <<EOF
echo  "Shutting processes down..."
kill -SIGTERM $pythonId
kill -SIGTERM $mavenId
while $(kill -0 $pythonId $mavenId 2>/dev/null); do
	sleep 1
done
echo "Processes shut down."
rm -f $delScript
EOF

chmod +x $delScript
echo "Processes started. Run ${delScript} to stop processes."
