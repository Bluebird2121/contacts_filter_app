<h2>Contacts Filter Service</h2>

CircleCI status: <a href="https://circleci.com/gh/Bluebird2121/contacts_filter_app/tree/master"><img src="https://circleci.com/gh/Bluebird2121/contacts_filter_app.png"></a>

This project represents REST Service which gives possibility to filter contacts with Regular Expression. 

<h3>Getting Started</h3>

These instructions will get you a copy of the project up and running on your local machine

<h4>Prerequisites</h4>
  To be able to run the project this software should be installed:  
  <ul>
    <li>VirtualBox</li>
    <li>Vagrant</li>
    <li>Git</li>
    
  </ul>


<h4>Run</h4>
    <ol>
        <li>Go to directory in which you want project to be stored.</li>
        <li>Clone project to local machine: <br><code> git clone https://github.com/Bluebird2121/contacts_filter_app.git</code></li>
        <li>execute <code>$ vagrant up</code> It will take a while for the first time run.</li>
    </ol>
    After installation just try 
    <a href="http://192.168.50.4:8080/hello/contacts?page=0&nameFilter=">
    http://192.168.50.4:8080/hello/contacts?page=0&nameFilter=
    </a> endpoint and you should see filtered results
  

<h3>System Dependencies*</h3>
  <ul>
    <li><a href="http://www.oracle.com/technetwork/java/javase/8-whats-new-2157071.html">Java 8</a> - Programming language</li>
    <li><a href="https://projects.spring.io/spring-boot/">Spring Boot</a> - Tool for easy application Development</li>
    <li><a href="http://hibernate.org/">Hibernate</a> - ORM framework</li>
    <li><a href="https://maven.apache.org/">Maven</a> - Dependency Management</li>
    <li><a href="https://www.vagrantup.com/">Vagrant</a> - Virtual System Container</li>
    <li><a href="https://www.postgresql.org/">Postgresql</a> - Database Management System</li>
  </ul>
  *All the dependencies managed automatically by <a href="https://www.vagrantup.com/">Vagrant</a> container.
