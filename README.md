# README #

TEI - "Tilastoista Enemman Iloa"

### What is this repository for? ###

* TEI a questionnaire application
* Create questionnaire template and send link for answering them
* Analytics of closed questions
* Basic user management
* 0.1-SNAPSHOT

### How do I get set up? ###

* Install MongoDB
* Start application with spring.data.mongodb.database=database name
* Go to <application address>/initialize to set up user "admin" password
* Login and explore

### Contribution guidelines ###

* Writing tests
* Code review
* Other guidelines
* o3d.xml contains code formating for eclipse

### Who do I talk to? ###

* Me

### Design rant and notes (Ask me if you want to know more)

* Currently service or domain layer uses database entity classes, but the idea is that only services are used in the business logic so it will be decoupled from the underlying data storage
* Storing and restoring the template does not maintain the question order, this need to be fixed. Might work, but needs more testing
* Tags for template, adding and searching by them
* Graphs for analytic purposes (closed questions can be represented as graphs)
* Sending link to fill out the questionnaire (sending email, generating hash for id)