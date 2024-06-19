#!/bin/bash

# Compilation du module classes
cd classes

echo "Compilation de la classe Restaurant.java"
javac -cp .:json-20131018.jar Restaurant.java

# Copie de Restaurant.class vers M1 et M3
echo "Copie de Restaurant.class vers M1 et M3"
cp Restaurant.class ../M1/
cp Restaurant.class ../M3/


# Compilation du module M1
cd ../M1

echo "Compilation du module M1"
javac -cp .:json-20131018.jar:ojdbc11.jar *.java

# Copie de ServiceRestaurantInterface.class vers M3
echo "Copie de ServiceRestaurantInterface.class vers M3"
cp ServiceRestaurantInterface.class ../M3


# Compilation du module M2
cd ../M2

echo "Compilation du module M2"
javac *.java

# Copie de ServiceEtabSupInterface.class et ServiceIncidentTraficInterface.class vers M3
echo "Copie de ServiceEtabSupInterface.class et ServiceIncidentTraficInterface.class vers M3"
cp ServiceEtabSupInterface.class ../M3
cp ServiceIncidentTraficInterface.class ../M3


# Compilation du module M3
cd ../M3

echo "Compilation du module M3"
javac -cp .:json-20131018.jar *.java

# Instructions pour démarrer les serveurs
echo ""
echo "Pour démarrer les serveurs, utilisez les commandes suivantes:"
echo "	1. Dans le répertoire M1:"
echo "		java -cp .:json-20131018.jar:ojdbc11.jar LancerM1"
echo "	2. Dans le répertoire M2:"
echo "  	 java LancerM2"
echo "	3. Dans le répertoire M3:"
echo "   	java -cp .:json-20131018.jar LancerM3"
echo ""
echo "Pour finir, lancer le site internet"
