package com.wm.brta.security;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.tomcat.util.http.fileupload.IOUtils;

public class MyRequestWrapper extends HttpServletRequestWrapper {
	private ByteArrayOutputStream cachedBytes;

	  public MyRequestWrapper(HttpServletRequest request) {
	    super(request);
	  }

	  @Override
	  public ServletInputStream getInputStream() throws IOException {
	    if (cachedBytes == null)
	      cacheInputStream();

	      return new CachedServletInputStream(cachedBytes);
	  }

	  @Override
	  public BufferedReader getReader() throws IOException{
	    return new BufferedReader(new InputStreamReader(getInputStream()));
	  }

	  private void cacheInputStream() throws IOException {
	    /* Cache the inputstream in order to read it multiple times. For
	     * convenience, I use apache.commons IOUtils
	     */
	    cachedBytes = new ByteArrayOutputStream();
	    IOUtils.copy(super.getInputStream(), cachedBytes);
	  }
}
	  
