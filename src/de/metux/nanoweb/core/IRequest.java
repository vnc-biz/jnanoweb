package de.metux.nanoweb.core;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

/**
 * Generic web request object
 *
 * Usually generated by an httpd or request multiplexer
 * and passed to an request handler
 */

public interface IRequest {

	/* the requested method */
	public static final String attribute_method = "Method";

	/* the requested http protocol */
	public static final String attribute_protocol = "Protocol";

	/* original request URI */
	public static final String attribute_uri = "URI";

	/* (translated) path */
	public static final String attribute_path = "URL-Path";

	/* publically visible root (url mouting) */
	public static final String attribute_root = "URL-Root";

	/* anchor part of the URI (usually should not be sent to server */
	public static final String attribute_anchor = "URL-Anchor";

	/* query part of the URI (usually will be parsed further into parameter list */
	public static final String attribute_query = "URL-Query";

	/** HTTP response status codes **/
	public static final int status_ok = 200;
	public static final int status_created = 201;

	public static final int status_bad_request = 400;
	public static final int status_unauthorized = 401;
	public static final int status_payment_required = 402;
	public static final int status_forbidden = 403;
	public static final int status_not_found = 404;
	public static final int status_method_not_allowed = 405;
	public static final int status_teapot = 418;

	public static final int status_internal_error = 500;

	/** standard HTTP headers **/
	public static final String header_virtual_host = "Host";
	public static final String header_content_type = "Content-Type";
	public static final String header_content_size = "Content-Size";

	/**
	 * retrieve a full URI of the (original) request
	 *
	 * @result	original request URI (w/o scheme+host prefix)
	 */
	public String getURI();

	/**
	 * retrieve the requested virtual hostname of the request
	 *
	 * @result	requested virtual hostname
	 */
	public String getVirtualHost();

	/**
	 * retrieve the translated request path
	 *
	 * note: request multiplexers may change this for mounting
	 *       thefore request handlers should look at this value
	 *       instead of the original path
	 *
	 * @result	(translated) request location path (relative to root)
	 */
	public String getPath();

	/**
	 * retrive the publically visible URL root (for mounting)
	 *
	 * Without mounting involved, it will be "/".
	 *
	 * With mounting (after rewrite) the url root will be the prefix,
	 * where the webapp had been mounted onto. Request handler(s) will
	 * usually act on the (possibly rewritten) path, but still might
	 * need to know the where it had been mounted to, eg. to generate
	 * publically visible links.
	 *
	 * @result	request location root
	 */
	public String getRoot();

	/**
	 * retrieve the protocol identifier string (eg. HTTP/1.0)
	 *
	 * @result	requested protocol identifier
	 */
	public String getProtocol();

	/**
	 * retrieve the request method
	 *
	 * @result	request method (in uppercase)
	 */
	public String getRequestMethod();

	/**
	 * retrieve request headers as property list
	 *
	 * @result	properly list holding the request headers
	 */
	public Properties getHeaders();

	/**
	 * retrieve parsed URL parameter list (UTF-8)
	 *
	 * @result	property list holding the parsed URL query paramters
	 */
	public Properties getURLParameters() throws UnsupportedEncodingException;

	/**
	 * retrieve parsed URL parameter list (with given encoding)
	 *
	 * @param	enc	requested encoding
	 * @result	property list holding the parsed URL query paramters
	 */
	public Properties getURLParameters(String enc) throws UnsupportedEncodingException;

	/**
	 * retrieve a specific request attribute
	 *
	 * @param name	attribute name (null not allowed)
	 * @result	attribute value (may be null)
	 */
	public String getAttribute(String name);

	/**
	 * set a request attribute
	 *
	 * @param name	attribute name (null not allowed)
	 * @param value	attribute value (null not allowed)
	 */
	public void setAttribute(String name, String value);

	/**
	 * set reply status code
	 *
	 * @param code	(numerical) http response code
	 * @param text	response message text
	 */
	public void replyStatus(int code, String text);

	/**
	 * set reply header
	 *
	 * @param name	header name (null not allowed)
	 * @param value	header value (null not allowed)
	 */
	public void replyHeader(String name, String value);

	/**
	 * send reply body data (binary)
	 *
	 * @param data	binary data to be sent
	 */
	public void replyBody(byte[] data)
	throws IOException;

	/**
	 * send reply body data, limited to x bytes (binary)
	 *
	 * @param data	binary data to be sent
	 * @param sz	maximum size to be sent
	 */
	public void replyBody(byte[] data, int sz)
	throws IOException;

	/**
	 * send reply body data (text)
	 *
	 * @param s	text data to be sent
	 */
	public void replyBody(String s)
	throws IOException;
}