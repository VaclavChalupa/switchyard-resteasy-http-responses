SwitchYard REST Interface Example (EAP 6.3, Java7)
==================================================

This SwitchYard example shows how to implement a correct REST interface with the Resteasy component.

Features:
---------

 * Appropriate HTTP response codes
 * Exception handling and propagation from SwitchYard to Resteasy
 * Injection of all Resteasy params to component implementation

Usage:
------

1. Create 2 new application users:
  * `authenticated` with password `authenticated.p1` and `authenticated` role
  * `authorized` with password `authorized.p1` and `authorized` role

            $JBOSS_HOME/bin/add-user.sh

2. Deployment:

        mvn clean package jboss-as:deploy

3. Integration test:

        mvn clean package integration-test -Pdeploy
