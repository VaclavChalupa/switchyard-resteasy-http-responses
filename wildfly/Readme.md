SwitchYard REST Interface Example (WildFly, Java8)
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

        mvn clean package wildfly:deploy

3. Integration test:

        mvn clean package integration-test -Pdeploy

Note:
-----

This example uses JAX-RS provider classes which are currently not yet supported in SwitchYard.

Until this feature will be merged to SwitchYard please use:

 * This fork of SwitchYard components: https://github.com/HEm3R/components
 * This release and build WildFly installer yourself: https://github.com/HEm3R/release
