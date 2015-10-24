# Captura
A langauge education application created for PennApps 2015. Captura showcases Clarifai's tagging endpoint (v1.0) using Java Maven and Apache Tomcat. When writing a Java Maven project, it helps to have [Spring Tool Suite](https://spring.io/tools/sts/all) to organize your code and run Maven.

In this explanation, I am assuming you have basic knowledge of Maven and Tomcat.

##Files Explanation
A _folder_ will be italicized, a __file__ will be bolded.

* _Server_ : Holds the files to run tomcat

* _captura_

  * _WebContent_ : Contains all of your .jsp files you will need

  * _src/main_ : Contains all of your logic/server files you will need

  * _target_ : .class files you can ignore for the most part

  * __appengine-agentimpl.jar__ : Boy I don't remember what this does.
  
  * __firebase-client-android-2.3.1.jar__ : Firebase jar
  
  * __pom.xml__ : Dependencies for your Maven project
