    WARNING WARNING WARNING WARNING WARNING WARNING WARNING WARNING WARNING WARNING WARNING WARNING WARNING WARNING WARNING W
    W																														W
    W   The current implementation stores users and passwords in a plaintext file, so it is _not_ advisable to use this in  W
    W   production-like environments. It is planned to hash the passwords. If a different `AuthenticationProvider` is 		W
    W   provided, a database, LDAP or whatever might be used.																W
    W																														W
    W   Be warned that using Basic authentication does not provide any encryption. You need some sort of transport 		    W
    W   encryption!																										    W
    W																														W
    WARNING WARNING WARNING WARNING WARNING WARNING WARNING WARNING WARNING WARNING WARNING WARNING WARNING WARNING WARNING W


# Authentication JAX-RS 2.x
This package includes server-side and client-side code for HTTP-Basic authentication using JAX-RS 2.x filters.

published in my bintray repo

	repositories {
    	maven { url "https://dl.bintray.com/skiedrowski/maven" }
    }
    	
    dependencies {
     	compile "com.github.skiedrowski.tools:rest-authentication:$ver.rest-authentication"
    }

Bintray file upload path: `com/github/skiedrowski/tools/rest-authentication/VERSION`

## Customization Points
* `AuthenticationProvider`: authentication mechanism (Basic, Digest, ...) and authentication backend (database, LDAP, ...)
  * There is one implementation `HTTPBasicAuthenticationProvider` using a properties file for user/passwords.
* `AuthenticationExceptionMapper`: Realm Name (for displaying to the user) should be provided in a `config.properties` 
	file (working dir or specified via SystemProperty) with key `www_authenticate_realm_name`. See `cdi-properties` project for details.

## Server
The server side part is more or less described on http://bitsuppliers.com/securing-rest-resources-with-java-ee-7-and-jax-rs-2-0/.

Default: all JAX-RS Methods need authentication.
If a method may be called without authentication, you may annotate it with `@AuthenticationNotRequired`.

`AuthenticationFilter` is a JAX-RS `ContainerRequestFilter` which can be applied to resources dynamically using `AuthenticationFeature` (a JAX-RS `DynamicFeature`).
`AuthenticationFeature` registers `AuthenticationFilter` for all contexts (JAX-RS methods) which are _not_ annotated with `@AuthorizationNotRequired`.

`AuthenticationFilter` uses `AuthenticationProvider` to authenticate the client.
`HTTPBasicAuthenticationProvider` implements HTTP Basic authentication. If authentication fails, an `AuthenticationException` is thrown. This exception is picked up by `AuthenticationExceptionMapper` which returns a HTTP response with status code 401 - Unauthorized. It also adds the `WWW-Authenticate` to the response which could be picked up by a client to display a user/password input dialog.

## Client
The client side part is inspired by http://www.adam-bien.com/roller/abien/entry/client_side_http_basic_access.

`Authenticator` is a JAX-RS `ClientRequestFilter` which may be used to provide credentials to the server.

## Limitations
* Basic Authentication does not provide encryption, so you need to encrypt the data (i.e. using HTTP)
* The challenge-response mechanism using `WWW-Authenticate` is untested.

## Links
http://bitsuppliers.com/securing-rest-resources-with-java-ee-7-and-jax-rs-2-0/ - server side inspiration

http://www.adam-bien.com/roller/abien/entry/client_side_http_basic_access      - client side inspiration

https://www.java2novice.com/restful-web-services/http-basic-authentication/ - more client/server inspiration

https://simplapi.wordpress.com/2015/09/17/jersey-jax-rs-implements-a-http-basic-auth-decoder-for-2-x-branch/

https://en.wikipedia.org/wiki/Basic_access_authentication