BUILD
-----
mvn -q -e -DskipTests package

RUN
---
cd equinox-runner/target/runtime

Linux/macOS:
  ./run.sh

Windows:
  run.bat

Then open http://localhost:8181/hello?wsdl

Stop Equinox with 'close' or Ctrl+C in the console.