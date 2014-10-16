package com.zlping.demo.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.ByteArrayBuffer;
import org.apache.http.util.CharArrayBuffer;
/**
 * 网络HttpEntity操作辅助类
 * @author 58tc
 *
 */
public final class EntityUtils {

    private EntityUtils() {
    }
    
    
    /**
     * 根据HttpEntity，得到对应的byte[]
     * @param entity
     * @return
     * @throws IOException
     */
    
    public static byte[] toByteArray(final HttpEntity entity) throws IOException {
        if (entity == null) {
            throw new IllegalArgumentException("HTTP entity may not be null");
        }
        InputStream instream = entity.getContent();
        if (instream == null) {
            return new byte[] {};
        }
        if (entity.getContentLength() > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("HTTP entity too large to be buffered in memory");
        }
        int i = (int)entity.getContentLength();
        if (i < 0) {
            i = 4096;
        }
        ByteArrayBuffer buffer = new ByteArrayBuffer(i);
        try {
            byte[] tmp = new byte[4096];
            int l;
            while((l = instream.read(tmp)) != -1) {
                buffer.append(tmp, 0, l);
            }
        } finally {
            instream.close();
        }
        return buffer.toByteArray();
    }
    
    
    
    
    /**
     * 获取HttpEntity的内容字符集
     * @param entity
     * @return
     * @throws ParseException
     */
        
    public static String getContentCharSet(final HttpEntity entity)
        throws ParseException {

        if (entity == null) {
            throw new IllegalArgumentException("HTTP entity may not be null");
        }
        String charset = null;
        if (entity.getContentType() != null) { 
            HeaderElement values[] = entity.getContentType().getElements();
            if (values.length > 0) {
                NameValuePair param = values[0].getParameterByName("charset");
                if (param != null) {
                    charset = param.getValue();
                }
            }
        }
        return charset;
    }
    
    
    /**
     * Gzip解压缩，返回解压输入流
     * @param entity
     * @return
     * @throws IOException
     */
    public static InputStream getUngzippedContent(HttpEntity entity) throws IOException {
        InputStream responseStream = entity.getContent();
		 if (responseStream == null) {
	         return responseStream;
	     }
	     Header header = entity.getContentEncoding();
	     if (header == null) {
	         return responseStream;
	     }
	     String contentEncoding = header.getValue();
	     if (contentEncoding == null) {
	         return responseStream;
	     }
	     if (contentEncoding.contains("gzip")) {
	         responseStream = new GZIPInputStream(responseStream);
	     }
	     return responseStream;
    }

    public static String toString(
            final HttpEntity entity, final String defaultCharset) throws IOException, ParseException {
        if (entity == null) {
            throw new IllegalArgumentException("HTTP entity may not be null");
        }
        InputStream instream = getUngzippedContent(entity);
        if (instream == null) {
            return "";
        }
        if (entity.getContentLength() > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("HTTP entity too large to be buffered in memory");
        }
        int i = (int)entity.getContentLength();
        if (i < 0) {
            i = 4096;
        }
        String charset = getContentCharSet(entity);
        if (charset == null) {
            charset = defaultCharset;
        }
        if (charset == null) {
            charset = HTTP.DEFAULT_CONTENT_CHARSET;
        }
        Reader reader = new InputStreamReader(instream, charset);
        CharArrayBuffer buffer = new CharArrayBuffer(i); 
        try {
            char[] tmp = new char[1024];
            int l;
            while((l = reader.read(tmp)) != -1) {
                buffer.append(tmp, 0, l);
            }
        } finally {
            reader.close();
            // 关闭io流
            if(instream!=null){
            	instream.close();
            }
        }
        return buffer.toString();
    }

    public static String toString(final HttpEntity entity)
        throws IOException, ParseException {
        return toString(entity, "UTF-8");
    }
    
}

