#!/bin/bash

# Compilation du module classes
echo "Compilation de la classe Restaurant"
cd classes
javac -cp .:json-20131018.jar Restaurant.java


# Copie de Restaurant.class vers M1 et M3
echo "Copie de Restaurant.class vers M1 et M3"
cd ../
cp classes/Restaurant.class M1/
cp classes/Restaurant.class M3/

# Compilation des modules M1, M2 et M3
cd M1
javac -cp .:json-20131018.jar:ojdbc11.jar *.java
cp ServiceRestaurantInterface.class ../M3
cd ../M2
javac *.java
cp ServiceEtabSupInterface.class ../M3
cp ServiceIncidentTraficInterface.class ../M3
cd ../M3
javac -cp .:json-20131018.jar *.java

# Instructions pour démarrer les serveurs
echo "Pour démarrer les serveurs, utilisez les commandes suivantes:"
echo "1. Dans le répertoire M1:"
echo "   java -cp .:json-20131018.jar:ojdbc11.jar LancerM1"
echo "2. Dans le répertoire M2:"
echo "   java LancerM2"
echo "3. Dans le répertoire M3:"
echo "   java -cp .:json-20131018.jar LancerM3"
