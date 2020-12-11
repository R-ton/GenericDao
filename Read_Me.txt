Etape 1 : Importer ETU830-ETU8 dans le projet en cours

Etape 2 : 
Configurer les fichiers de configuration
	-fichier de configuration des bases de données:
		-location : bin/database.json
		- ouvrir le fichier dans un éditeur de texte et modifier les configurations pour chaque base de données: (oracle et postgresql seront les bases par défault dans
		le fichier de configuration)
			{
	"oracle" :{
		"driver" : "oracle.jdbc.driver.OracleDriver",
		"url" : "jdbc:oracle:thin:@",
		"address" : "localhost",
		"port" : "1521",
		"user" : "framework",
		"password" : "framework"
	}
	"postgresql" :{
		"driver" : "org.postgresql.Driver",
		"url" : "jdbc:postgresql://",	
		"address" : "localhost",
		"port" : "5432",
		"user" : "framework",
		"password" : "1234",
		"database" : "frame"
	}
}
