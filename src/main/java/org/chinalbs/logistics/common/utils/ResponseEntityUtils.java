package org.chinalbs.logistics.common.utils;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class ResponseEntityUtils {
	public static <T> ResponseEntity<T> creatOKResponse(T body, MediaType contentType) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(contentType);
		return new ResponseEntity<T>(body, headers, HttpStatus.OK);
	}

    public static <T> ResponseEntity<T> createNotFoundResponse(T body, MediaType contentType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(contentType);
        return new ResponseEntity<T>(body, headers, HttpStatus.NOT_FOUND);
    }
    
    public static <T> ResponseEntity<T> creatRedirectResponse(T body, String url) {
		HttpHeaders headers = new HttpHeaders();
		try {
			headers.setLocation(new URI(url));
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<T>(body, headers, HttpStatus.TEMPORARY_REDIRECT);
	}
}
