/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Bertrand Martel
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package fr.bmartel.protocol.http.constants;

import fr.bmartel.protocol.http.StatusCodeObject;

/**
 * Status code list
 * 
 * @author Bertrand Martel
 *
 */
public class StatusCodeList {
	
	public final static StatusCodeObject CONTINUE                         = new StatusCodeObject(100,  "Continue"                        );
	public final static StatusCodeObject SWITCHING_PROTOCOL               = new StatusCodeObject(101,  "Switching Protocols"             );
	public final static StatusCodeObject OK                               = new StatusCodeObject(200,  "OK"                              );
	public final static StatusCodeObject CREATED                          = new StatusCodeObject(201,  "Created"                         );
	public final static StatusCodeObject ACCEPTED                         = new StatusCodeObject(202,  "Accepted"                        );
	public final static StatusCodeObject NON_AUTHORITATIVE_INFORMATION    = new StatusCodeObject(203,  "Non-Authoritative Information"   );
	public final static StatusCodeObject NO_CONTENT                       = new StatusCodeObject(204,  "No Content"                      );
	public final static StatusCodeObject RESET_CONTENT                    = new StatusCodeObject(205,  "Reset Content"                   );
	public final static StatusCodeObject PARTIAL_CONTENT                  = new StatusCodeObject(206,  "Partial Content"                 );
	public final static StatusCodeObject MULTIPLE_CHOICES                 = new StatusCodeObject(300,  "Multiple Choices"                );
	public final static StatusCodeObject MOVED_PERMANENTLY                = new StatusCodeObject(301,  "Moved Permanently"               );
	public final static StatusCodeObject FOUND                            = new StatusCodeObject(302,  "Found"                           );
	public final static StatusCodeObject SEE_OTHER                        = new StatusCodeObject(303,  "See Other"                       );
	public final static StatusCodeObject NOT_MODIFIED                     = new StatusCodeObject(304,  "Not Modified"                    );
	public final static StatusCodeObject USE_PROXY                        = new StatusCodeObject(305,  "Use Proxy"                       );
	public final static StatusCodeObject TEMPORARY_REDIRECT               = new StatusCodeObject(307,  "Temporary Redirect"              );
	public final static StatusCodeObject BAD_REQUEST                      = new StatusCodeObject(400,  "Bad Request"                     );
	public final static StatusCodeObject UNAUTHORIZED                     = new StatusCodeObject(401,  "Unauthorized"                    );
	public final static StatusCodeObject PAYMENT_REQUIRED                 = new StatusCodeObject(402,  "Payment Required"                );
	public final static StatusCodeObject FORBIDDEN                        = new StatusCodeObject(403,  "Forbidden"                       );
	public final static StatusCodeObject NOT_FOUND                        = new StatusCodeObject(404,  "Not Found"                       );
	public final static StatusCodeObject METHOD_NOT_ALLOWED               = new StatusCodeObject(405,  "Method Not Allowed"              );
	public final static StatusCodeObject NOT_ACCEPTABLE                   = new StatusCodeObject(406,  "Not Acceptable"                  );
	public final static StatusCodeObject PROXY_AUTHENTICATION_REQUIRED    = new StatusCodeObject(407,  "Proxy Authentication Required"   );
	public final static StatusCodeObject REQUEST_TIME_OUT                 = new StatusCodeObject(408,  "Request Time-out"                );
	public final static StatusCodeObject CONFLICT                         = new StatusCodeObject(409,  "Conflict"                        );
	public final static StatusCodeObject GONE                             = new StatusCodeObject(410,  "Gone"                            );
	public final static StatusCodeObject LENGTH_REQUIRED                  = new StatusCodeObject(411,  "Length Required"                 );
	public final static StatusCodeObject PRECONDITION_FAILED              = new StatusCodeObject(412,  "Precondition Failed"             );
	public final static StatusCodeObject REQUEST_ENTITY_TOO_LARGE         = new StatusCodeObject(413,  "Request Entity Too Large"        );
	public final static StatusCodeObject REQUEST_URI_TOO_LARGE            = new StatusCodeObject(414,  "Request-URI Too Large"           );
	public final static StatusCodeObject UNSUPPORTED_MEDIA_TYPE           = new StatusCodeObject(415,  "Unsupported Media Type"          );
	public final static StatusCodeObject REQUESTED_RANGE_NOT_SATISFIABLE  = new StatusCodeObject(416,  "Requested range not satisfiable" );
	public final static StatusCodeObject EXPECTATION_FAILED               = new StatusCodeObject(417,  "Expectation Failed"              );
	public final static StatusCodeObject INTERNAL_SERVER_ERROR            = new StatusCodeObject(500,  "Internal Server Error"           );
	public final static StatusCodeObject NOT_IMPLEMENTED                  = new StatusCodeObject(501,  "Not Implemented"                 );
	public final static StatusCodeObject BAD_GATEWAY                      = new StatusCodeObject(502,  "Bad Gateway"                     );
	public final static StatusCodeObject SERVICE_UNAVAILABLE              = new StatusCodeObject(503,  "Service Unavailable"             );
	public final static StatusCodeObject GATEWAY_TIME_OUT                 = new StatusCodeObject(504,  "Gateway Time-out"                );
	public final static StatusCodeObject HTTP_VERSION_NOT_SUPPORTED       = new StatusCodeObject(505,  "HTTP Version not supported"      );
}
