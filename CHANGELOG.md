# CHANGELOG

## V0.4
* CHANGE: new cdi-properties version

## V0.3
* CHANGE: refactor maven group and project name for better gradle-composite builds integration
* CHANGE: AuthenticationUserInfo is now a data class

## V0.2
* CHANGE: AuthenticationExceptionMapper: allow config of user-realm name
* CHANGE: @HTTPBasic qualifier removed. Also removed interfaces (as long as there are no alternatives)
* CHANGE: Extract concrete authentication into separate class
* BUGFIX: add beans.xml

## V0.1
* initial version