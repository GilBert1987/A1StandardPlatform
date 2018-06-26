package com.common.filter;

import java.util.Enumeration;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.ehcache.constructs.blocking.LockTimeoutException;
import net.sf.ehcache.constructs.web.AlreadyCommittedException;
import net.sf.ehcache.constructs.web.AlreadyGzippedException;
import net.sf.ehcache.constructs.web.filter.FilterNonReentrantException;
import net.sf.ehcache.constructs.web.filter.SimplePageCachingFilter;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import com.common.entity.login.User;
 
/**
 * <b>function:</b> mobile 页面缓存过滤器
 * @author hoojo
 * @createDate 2012-7-4 上午09:34:30
 * @file PageEhCacheFilter.java
 * @package com.hoo.ehcache.filter
 * @project Ehcache
 * @blog http://blog.csdn.net/IBM_hoojo
 * @email hoojo_@126.com
 * @version 1.0
 */
public class PageEhCacheFilter extends SimplePageCachingFilter {
 
	
    private final static Logger log = Logger.getLogger(PageEhCacheFilter.class);
    
    private FilterConfig filterConfig;
    
    @Override
    public void doInit(FilterConfig filterConfig) throws CacheException {
    	this.filterConfig=filterConfig;
    	super.doInit(filterConfig);
    }
    
    @Override
    protected void doFilter(final HttpServletRequest request,
            final HttpServletResponse response, final FilterChain chain)
            throws AlreadyGzippedException, AlreadyCommittedException,
            FilterNonReentrantException, LockTimeoutException, Exception {
    	Boolean blInfo;
    	Ehcache cache;
    	String strcacheName;
    	CacheManager manager;
    	User user;
    	int isize;
    	Subject subject;
    	subject = SecurityUtils.getSubject();
    	user = (User)subject.getPrincipal();
    	blInfo=false;
    	strcacheName=filterConfig.getInitParameter("cacheName");
    	if(request.getMethod().toUpperCase().equals("GET")==true && null == request.getHeader("X-Requested-With"))
    	{
    		blInfo=true;
    	}
    	if(blInfo==true)
    	{
    		super.doFilter(request, response, chain);
    	}
    	else
    	{
    		if(null!=user)
    		{
	    		manager=this.getCacheManager();
	    		cache=manager.getEhcache(strcacheName);
	    		if(null != cache)
	    		{
		    		for(Object obj:cache.getKeys())
		    		{
		    			isize=user.getId().length()+1;
		    			if(obj.toString().length()>=isize && (user.getId()+":").equals(obj.toString().substring(0, isize))==true)
		    			{
		    				cache.remove(obj);
		    			}
		    		}
	    		}
    		}
    		chain.doFilter(request, response);
    	}
    }
    
    @SuppressWarnings("unchecked")
    private boolean headerContains(final HttpServletRequest request, final String header, final String value) {
        logRequestHeaders(request);
        final Enumeration accepted = request.getHeaders(header);
        while (accepted.hasMoreElements()) {
            final String headerValue = (String) accepted.nextElement();
            if (headerValue.indexOf(value) != -1) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * @see net.sf.ehcache.constructs.web.filter.Filter#acceptsGzipEncoding(javax.servlet.http.HttpServletRequest)
     * <b>function:</b> 兼容ie6/7 gzip压缩
     * @author hoojo
     * @createDate 2012-7-4 上午11:07:11
     */
    @Override
    protected boolean acceptsGzipEncoding(HttpServletRequest request) {
        boolean ie6 = headerContains(request, "User-Agent", "MSIE 6.0");
        boolean ie7 = headerContains(request, "User-Agent", "MSIE 7.0");
        return acceptsEncoding(request, "gzip") || ie6 || ie7;
    }
    
    @Override
    protected String calculateKey(HttpServletRequest httpRequest) {
    	User user;
    	Subject subject;
    	String key;
    	StringBuffer stringBuffer;
    	subject = SecurityUtils.getSubject();
    	user = (User)subject.getPrincipal();
        stringBuffer = new StringBuffer();
        if(null!=user)
        {
        	stringBuffer.append(user.getId()+":");
        }
        stringBuffer.append(httpRequest.getMethod());
        stringBuffer.append(httpRequest.getRequestURI());
        stringBuffer.append(httpRequest.getQueryString()==null?"":"?"+httpRequest.getQueryString());
        key = stringBuffer.toString();
        return key;
    }
    
    @Override
    protected void doDestroy() {
    	filterConfig = null;
    }

}
