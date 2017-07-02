<h2>Contacts Filter Service</h2>

<a href="https://circleci.com/gh/Bluebird2121/contacts_filter_app/tree/master"><img src="https://circleci.com/gh/Bluebird2121/contacts_filter_app.png"></a>

This project represents REST Service which gives possibility to filter contacts with Regular Expression. 

<h3>Getting Started</h3>

These instructions will get you a copy of the project up and running on your local machine

<h4>Prerequisites</h4>
  If you not already using Vagrant, follow these steps:
  <ul>
    <li>Install <a href="https://www.vagrantup.com/downloads.html">Vagrant</a></li>
    <li>Install a provider <a href="https://www.virtualbox.org/wiki/Downloads">VirtualBox</a></li>
  </ul>


<h4>Run</h4>
    <ol>
        <li>Go to directory in which you want project to be stored.</li>
        <li>Clone project to local machine: <br><code> git clone https://github.com/Bluebird2121/contacts_filter_app.git</code></li>
        <li>Go the project folder</li>
        <li>Execute <code>vagrant up</code> 
        <br>Note, it will take a while for the first time run.</li>
    </ol>
    After installation just populate database with data by visiting: 
    <a target="_blank" href="http://192.168.50.4:8080/hello/contacts/populate_contacts_data">http://192.168.50.4:8080/hello/contacts/populate_contacts_data</a> 
    <br>
    Now you are able to check filter:
    <a target="_blank" href="http://192.168.50.4:8080/hello/contacts?page=0&nameFilter=^(?!A)">
    http://192.168.50.4:8080/hello/contacts?page=0&nameFilter=^(?!A)
    </a> endpoint and you should see filtered results.

<h3>Resources</h3>

|                     URL                                                   | Description                                                                   | Method   | URL Params                                                                                                         | Success Response                                                                                                                 | Error Response                                                                                                                                                                                                                                                          |
| ------------------------------------------------------------------------- |:------------------------------------------------------------------------------|:--------:| :----------------------------------------------------------------------------------------------------------------- |:--------------------------------------------------------------------------------------------------------------------------------- |:-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| http://192.168.50.4:8080/hello/contacts/populate_contacts_data            | Fill contacts table with fake data. By default it creates 100_000 record.     | GET      |    -                                                                                                               | Code: 200 OK                                                                                                                     | Code: 400 BAD_REQUEST <br> Content: {"message":"Can't populate not empty database."}                                                                                                                                                                                    |
| http://192.168.50.4:8080/hello/contacts?page=:page&nameFilter=:nameFilter | Returns contacts whose names are NOT match :nameFilter. Result is paginated.  | GET      | **page** - result page (starts from 0) <br> **nameFilter** - regular expression to filter NOT match contacts names | Code: 200 OK <br> Content: {"contacts":[],"pagination":{"prev":"link","next":"link"}}                                            | Code: 400 BAD_REQUEST <br> Content: {"timestamp":1499019688374,"status":400,"error":"Bad Request","exception":"org.springframework.web.bind.MissingServletRequestParameterException","message":"Required int parameter 'page' is not present","path":"/hello/contacts"} |



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
