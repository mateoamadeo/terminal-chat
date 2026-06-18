#!/bin/bash

echo "Downloading SQLite JDBC driver..."
wget https://github.com/xerial/sqlite-jdbc/releases/download/3.45.0.0/sqlite-jdbc-3.45.0.0.jar

echo "Downloading SLF4J API..."
wget https://repo1.maven.org/maven2/org/slf4j/slf4j-api/2.0.9/slf4j-api-2.0.9.jar

echo "Downloading SLF4J Simple implementation..."
wget https://repo1.maven.org/maven2/org/slf4j/slf4j-simple/2.0.9/slf4j-simple-2.0.9.jar

if [ -f "sqlite-jdbc-3.45.0.0.jar" ] && [ -f "slf4j-api-2.0.9.jar" ] && [ -f "slf4j-simple-2.0.9.jar" ]; then
    echo ""
    echo "✓ All libraries downloaded successfully!"
    echo ""
    echo "Now you can compile with:"
    echo "  javac -cp \".:sqlite-jdbc-3.45.0.0.jar:slf4j-api-2.0.9.jar:slf4j-simple-2.0.9.jar\" *.java"
    echo ""
    echo "And run with:"
    echo "  java -cp \".:sqlite-jdbc-3.45.0.0.jar:slf4j-api-2.0.9.jar:slf4j-simple-2.0.9.jar\" Server"
    echo "  java -cp \".:sqlite-jdbc-3.45.0.0.jar:slf4j-api-2.0.9.jar:slf4j-simple-2.0.9.jar\" Client"
    echo ""
    echo "On Windows, use semicolons instead of colons in the classpath:"
    echo "  javac -cp \".;sqlite-jdbc-3.45.0.0.jar;slf4j-api-2.0.9.jar;slf4j-simple-2.0.9.jar\" *.java"
else
    echo ""
    echo "✗ Download failed. Please download manually from:"
    echo "  SQLite JDBC: https://github.com/xerial/sqlite-jdbc/releases"
    echo "  SLF4J: https://repo1.maven.org/maven2/org/slf4j/"
fi
