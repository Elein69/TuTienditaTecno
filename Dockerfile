# Usar la imagen base de GlassFish
FROM glassfish:5.1.0-jdk8

# Copiar el archivo WAR al directorio de despliegue de GlassFish
COPY target/AppWeb-1.0-SNAPSHOT.war $GLASSFISH_HOME/glassfish/domains/domain1/autodeploy/

# Exponer el puerto 8080 para GlassFish
EXPOSE 8080

# Iniciar GlassFish
CMD ["asadmin", "start-domain", "--verbose"]

