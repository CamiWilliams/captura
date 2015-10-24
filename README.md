# Captura
A language education application created for PennApps 2015. Captura showcases Clarifai's tagging endpoint (v1.0) using Java Maven and Apache Tomcat. When writing a Java Maven project, it helps to have [Spring Tool Suite](https://spring.io/tools/sts/all) to organize your code and run Maven.

It will help if you read up on Tomcat and Java Maven to understand this project!

##Setting up your environment
Once you have STS downloaded, open it and set up your [environment for Tomcat](http://crunchify.com/step-by-step-guide-to-setup-and-install-apache-tomcat-server-in-eclipse-development-environment-ide/). Once you see Tomcat under your 'Servers' tab, create a new Maven project and select 'archetype-quickstart' for your Archetype. Your GroupID is who owns the project (ie your name) and the ArtifactID is the name of your application.

Now you are ready to start using Maven! It is pretty icky, but I will try and explain everything to the best of my ability...

###This is what you NEED to do first

__pom.xml__ This is the first file you need to know about, and it is the most important file ever. This is where your app will be verified and where all of your dependencies will be clarified (heh). Maven uses `pom.xml` dependencies instead of downloading jars. Yay. In order to see the raw code in STS, when you click on the file, go to the pom.xml tab. For now, you should just add the Clarifai dependency:
```
<dependency>
 	<groupId>com.clarifai</groupId>
 	<artifactId>clarifai-api-java</artifactId>
 	<version>1.1.0</version>
</dependency>
```
You can add more dependencies as you need them. To search for dependencies, go to [MVNRepository] (http://mvnrepository.com/)

__/WebContent/WEB-INF/web.xml__ This file is important because it defines where to find your server logic and your HTML files. In Maven, HTML files are called JSP files. To define that you want the JSP to be viewed as HTML, you need to add `servlet-mappings` to your homepage. For this first mapping, your `servlet-name` should be your application name, and the `url-pattern` should be whatever file your initial page is under.
```
  <servlet-mapping>
    <servlet-name>captura</servlet-name>
    <url-pattern>/index.jsp</url-pattern>
    <url-pattern>/index.html</url-pattern>
    <url-pattern>*.html</url-pattern>
  </servlet-mapping>
```
In Maven, there are `Servlets` and `Controllers`. A servlet handles authentication logic like logging in, verifying and account, etc. A controller handles simple page logic, like using Clarifa or other APIs, or counting the number of llamas on a website. In 'web.xml`, you will define your servlets. The 'servlet-name' will be what you will refer to your servlet as in your JSP and the 'servlet-class' is the filepath to your servlet. For the 'servlet-mapping', the name is the same as before, and the 'url-pattern' should be /serv#, # being incremented with each servlet you add.
```
  <servlet>
    <servlet-name>LoginServlet</servlet-name>
    <servlet-class>camiwilliams.captura.servlet.LoginServlet</servlet-class>
  </servlet>
  <servlet-mapping>
    <servlet-name>LoginServlet</servlet-name>
    <url-pattern>/serv1</url-pattern>
  </servlet-mapping>
```
__/WebContent/WEB-INF/yourprojectname-servlet.xml__ In this file, you define your controller and finalize the mapping between JSP and HTML. You can copy this [file](https://github.com/CamiWilliams/captura/blob/master/captura/WebContent/WEB-INF/captura-servlet.xml) from me, because generally it is the same for everyone. The only thing you need to change here is `context:component-scan` to your controller package.
