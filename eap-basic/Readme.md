SwitchYard REST Interface Example (EAP 6.3, Java7)
==================================================

This SwitchYard example shows the default REST interface with the Resteasy component.

Features:
---------

 * 200 or 500 HTTP response codes for every request

Usage:
------

1. Create 2 new application users:
  * `authenticated` with password `authenticated.p1` and `authenticated` role
  * `authorized` with password `authorized.p1` and `authorized` role

            $JBOSS_HOME/bin/add-user.sh

2. Deployment:

        mvn clean package jboss-as:deploy
