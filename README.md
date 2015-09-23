# devday-cdi
VaadinCDI based example project with MVP integration
==============

Template for a full-blown Vaadin Java EE application that requires JavaEE 7 and Java8 compatible application server such as WildFly 8.2

Project Structure
=================

* devday-parent: project property and sub module definitions
* devday-common: project containing dependencies needed by both, UI layer and Backend layer such as Service interfaces
* devday-backend: project containing the backend bean implementations
* devday-ui: project containing Vaadin UI
* devday-deploy: Deployable war project that overlays devday-ui. Overlaying is done to avoid direct dependencies from UI project to Backend project on code level but still to include also the backend's jar file to deployable artifact.


Getting started
=================

* mvn clean install devday-parent project and if necessary skip tests to avoid running Vaadin TestBench tests.
* Open in IDE of your choice and deploy devday-deploy project to Wildfly 8 server (or equal)
* Start up with browser from localhost:8080/devday-deploy (or other user configured url)
