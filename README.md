# http-endec

[![Build Status](https://travis-ci.org/bertrandmartel/http-endec.svg?branch=master)](https://travis-ci.org/bertrandmartel/http-endec)
[![Download](https://api.bintray.com/packages/bertrandmartel/maven/http-endec/images/download.svg) ](https://bintray.com/bertrandmartel/maven/http-endec/_latestVersion)
[![Coverage Status](https://coveralls.io/repos/github/bertrandmartel/http-endec/badge.svg?branch=master)](https://coveralls.io/github/bertrandmartel/http-endec?branch=master)
[![License](http://img.shields.io/:license-mit-blue.svg)](LICENSE.md)

Http encoder/decoder for Java/Android

<hr/>

* This endec works with streaming data in a socket-blocking configuration. 
* It will block until the whole current HTTP frame is totally parsed.

## Include in your project

* with Gradle, from jcenter :

```
compile 'fr.bmartel:http-endec:1.03'
```

## How to use ?

#### Build HTTP request

```
HttpFrame frameRequest = new HttpFrame(
							HttpMethod.POST_REQUEST      ,
							new HttpVersion(1, 1)        ,
							new HashMap<String, String>(),
							"/rest/help/todo"            ,
							new ListOfBytes("some body")
						);
```

#### Build HTTP response

```
HttpResponseFrame frameResponse = new HttpResponseFrame(
										new StatusCodeObject(200, "OK"),
										new HttpVersion(1, 1),
										new HashMap<String, String>(),
										""
									);
```

#### Parse HTTP inputstream

```
HttpFrame newFrame = new HttpFrame();

newFrame.parseHttp(inputstream);
```

You can access your result in sub methods :

|   Method                                   |    Type       |  description |
|--------------------------------------------|---------------|--------------|
| `getUri()`                                 | String        |  http uri    |
| `getHttpVersion()`                         | HttpVersion   |  http version |
| `getMethod()`                              |     String    |   http code   |
| `getQueryString()`                         | String        |  http query string |
| `getHost()`                                | String        |  host              |
| `getBody()`                                | IByteList     |  body content   |

#### Change your buffer size

In `fr.bmartel.protocol.socket.DataBufferConst`

Change the value : 

`public final static int DATA_BLOCK_SIZE_LIMIT = 4089;`

## JavaDoc

http://bertrandmartel.github.io/http-endec

## Compatibility

JRE 1.7 compliant

## Build

Gradle using IntelliJ IDEA or Eclipse

## Specifications 

Specification from RFC specified in https://www.ietf.org/rfc/rfc2616.txt

## License

The MIT License (MIT) Copyright (c) 2016 Bertrand Martel
