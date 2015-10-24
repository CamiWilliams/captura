# Captura
A language education application created for PennApps 2015. Captura showcases Clarifai's tagging endpoint (v1.0) using Java Maven and Apache Tomcat. When writing a Java Maven project, it helps to have [Spring Tool Suite](https://spring.io/tools/sts/all) to organize your code and run Maven.

It will help if you read up on Tomcat and Java Maven to understand this project!

##Setting up your environment
Once you have STS downloaded, open it and set up your [environment for Tomcat](http://crunchify.com/step-by-step-guide-to-setup-and-install-apache-tomcat-server-in-eclipse-development-environment-ide/). Once you see Tomcat under your 'Servers' tab, create a new Maven project and select 'archetype-quickstart' for your Archetype. Your GroupID is who owns the project (ie your name) and the ArtifactID is the name of your application.

Now you are ready to start using Maven! It is pretty icky, but I will try and explain everything to the best of my ability...

__pom.xml__ This is the first file you need to know about, and it is the most important file ever. This is where your app will be verified and where all of your dependencies will be clarified (heh). Maven uses pom.xml dependencies instead of downloading jars. Yay. In order to see the raw code in STS, when you click on the file, go to the pom.xml tab. For now, you should just add the Clarifai dependency:
```
 <dependency>
	  <groupId>com.clarifai</groupId>
	  <artifactId>clarifai-api-java</artifactId>
	  <version>1.1.0</version>
	</dependency>
```
You can add more dependencies as you need them. To search for dependencies, go to [MVNRepository] (http://mvnrepository.com/)



