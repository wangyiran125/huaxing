package org.chinalbs.logistics.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.chinalbs.logistics.common.json.ReturnCode;
import org.chinalbs.logistics.common.utils.CommonUtils;
import org.chinalbs.logistics.common.utils.ListSlice;
import org.chinalbs.logistics.config.CommonConfig;
import org.chinalbs.logistics.domain.Truck;
import org.chinalbs.logistics.error.CodeException;
import org.chinalbs.logistics.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.*;
import java.util.Map.Entry;



public class HTTPUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(HTTPUtils.class);
    public static final String HOST = CommonConfig.INSTANCE.getKaibuHost();
    public static final int PORT = CommonConfig.INSTANCE.getKaibuPort();
    public static final String SYSNAME = CommonConfig.INSTANCE.getSysName();
    public static final int RET_100 = 100;
   
    //url
    public static final String URL_ADD_USER = "/LOGISTICS_INTERFACE/user/addUser2.do";
    public static final String URL_GET_TOKEN = "/LOGISTICS_INTERFACE/user/getToken2.do";
    public static final String URL_DEVICE_MGR = "/LOGISTICS_INTERFACE/device/dealWithDevice2.do";
    public static final String URL_DEVICE_CHECK = "/LOGISTICS_INTERFACE/device/deviceCheck.do";
    public static final String URL_QUERY_POSITION= "/LOGISTICS_INTERFACE/position/positionCheck.do";
    
    //key
    public static final String PARAM_USERID = "userId";
    public static final String PARAM_DEVICESN = "deviceSn";
    public static final String PARAM_DEVICEKEY = "deviceKey";
    public static final String PARAM_SYSNAME = "sysName";
    public static final String PARAM_EMAIL = "email";
    public static final String PARAM_PHONE = "phone";
    public static final String PARAM_QQ = "qq";
    public static final String PARAM_NAME= "name";
    public static final String PARAM_USERNAME = "username";
    public static final String PARAM_PASSWORD= "password";    
    // Timeout
    public static final int CONNECT_TIMEOUT = 20 * 1000;
    public static final int SOCKET_TIMEOUT = 20 * 1000;
    
    /*
     * 1.	新用户创建接口
     */
    public static boolean addUser2(Long userId, RegisterInfo registerInfo) {
    	//为了区分多个系统，凯步的接口中增加系统名（不可重复）
    	Map<String, String> paramHash = new HashMap<String, String>();
     	paramHash.put(PARAM_USERID, Long.toString(userId));
    	if (registerInfo.getEmail() != null && !registerInfo.getEmail().equals("")) {
    		paramHash.put(PARAM_EMAIL, registerInfo.getEmail());
    	}
    	else {
    		paramHash.put(PARAM_EMAIL, "");
    	}
    	if (registerInfo.getMobile() != null && !registerInfo.getMobile().equals("")) {
    		paramHash.put(PARAM_PHONE, registerInfo.getMobile());
    	}
    	else {
    		paramHash.put(PARAM_PHONE, "");
    	}
    	if (registerInfo.getQq() != null && !registerInfo.getQq().equals("")) {
    		paramHash.put(PARAM_QQ, registerInfo.getQq());
    	}
    	else {
    		paramHash.put(PARAM_QQ, "");
    	}
    	paramHash.put(PARAM_NAME, registerInfo.getName());
    	paramHash.put(PARAM_USERNAME, registerInfo.getUsername());
    	paramHash.put(PARAM_PASSWORD, registerInfo.getPassword());
    	paramHash.put(PARAM_USERID, Long.toString(userId));
    	paramHash.put(PARAM_SYSNAME, SYSNAME);
    	CapcareBase capcareBase = httpGet(URL_ADD_USER, paramHash, CapcareBase.class);
    	if (capcareBase == null || capcareBase.getRet() != RET_100) {
    		if(capcareBase != null){
    			logger.warn(capcareBase.getExplain());
    		}
    		return false;
    	}
    	return true;
    }
    
    
    /*
     * 2.	获取用户Token信息
     */   
    public static String getToken(Long userId) {
    	Map<String, String> paramHash = new HashMap<String, String>();
    	paramHash.put(PARAM_USERID, Long.toString(userId));
    	//为了区分多个系统，凯步的接口中增加系统名（不可重复）
    	paramHash.put(PARAM_SYSNAME, SYSNAME);
    	CapcareToken capcareBase = httpGet(URL_GET_TOKEN, paramHash, CapcareToken.class);
    	if (capcareBase == null || capcareBase.getRet() != RET_100) {
    		if(capcareBase != null){
    			logger.warn(capcareBase.getExplain());
    		}
    		return null;
    	}
    	return capcareBase.getUserToken();
    }
    
    /*
     * 3.	设备信息管理接口
     */   
    public static void mgrDevice(Map<String, String> paramHash) {
    	//为了区分多个系统，凯步的接口中增加系统名（不可重复）
    	paramHash.put(PARAM_SYSNAME, SYSNAME);
    	CapcareBase capcareBase = httpGet(URL_DEVICE_MGR, paramHash, CapcareBase.class);
    	if (capcareBase == null || capcareBase.getRet() != RET_100) {
    		if(capcareBase != null){
    			logger.warn(capcareBase.getExplain());
    		}
    		throw new CodeException(ReturnCode.BUSINESS_ERROR, capcareBase.getExplain());
    	}
    }
    
    /*
     * 4.	设备校验接口
     */   
    public static void checkDevice(String deviceKey) {
    	Map<String, String> paramHash = new HashMap<String, String>();
    	paramHash.put(PARAM_DEVICESN, deviceKey);
    	CapcareBase capcareBase = httpGet(URL_DEVICE_CHECK, paramHash, CapcareBase.class);
    	if (capcareBase == null || capcareBase.getRet() != RET_100) {
    		if(capcareBase != null){
    			logger.warn(capcareBase.getExplain());
    		}
    		throw new CodeException(ReturnCode.BUSINESS_ERROR, "设备检查返回结果：" + capcareBase.getExplain());
    	}
    }
    
    /*
     * 5.	查询设备位置
     */   
    public static CapcarePosition getPosition(List<String> deviceKeyList) {
    	Map<String, String> paramHash = new HashMap<String, String>();
    	String deviceKeyString = "{\"deviceSns\":[";
    	for (String deviceKey : deviceKeyList) {
    		deviceKeyString += "\""+deviceKey+"\"" +",";
    	}
    	deviceKeyString = deviceKeyString.substring(0, deviceKeyString.length()-1);
    	deviceKeyString += "]}";
    	try {
			paramHash.put(PARAM_DEVICEKEY, URLEncoder.encode(deviceKeyString));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    	CapcarePosition capcareBase = httpGet(URL_QUERY_POSITION, paramHash, CapcarePosition.class);
    	if (capcareBase == null || capcareBase.getRet() != RET_100) {
    		if(capcareBase != null){
    			logger.warn(capcareBase.getExplain());
    		}
    		return null;
    	}
    	return capcareBase;
    }
    
    public static <T> T httpGet(String url, Map<String, String> hashParam, final Class<T> classa ) {
    	
    	T myjson = null;
    	CloseableHttpClient httpclient = HttpClients.createDefault();
    	//设置Timeout
    	RequestConfig requestConfig = RequestConfig.custom()
    	        .setSocketTimeout(SOCKET_TIMEOUT)
    	        .setConnectTimeout(CONNECT_TIMEOUT)
    	        .build();
    	//构造URL
    	URIBuilder builder = new URIBuilder()
    	        .setScheme("http")
    	        .setHost(HOST)
    	        .setPort(PORT)
    	        .setPath(url);
    	
    	//设置参数
    	Iterator<Entry<String, String>> iter = hashParam.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>) iter.next();
			builder.setParameter(entry.getKey(), entry.getValue());
		}
		URI finalUrl = null;
		try {
			finalUrl = builder.build();
		} catch (URISyntaxException e) {
			logger.error("URISyntaxException : "+e.getMessage());
			return myjson;
		}
		logger.info("url = "+ finalUrl.toString());
    	       
    	
    	
    	HttpGet httpget = new HttpGet(finalUrl);
    	httpget.setConfig(requestConfig);

    	//处理返回结果
    	ResponseHandler<T> rh = new ResponseHandler<T>() {

    	    @Override
    	    public T handleResponse(
    	            final HttpResponse response) throws IOException {
    	        StatusLine statusLine = response.getStatusLine();
    	        logger.info("statusLine = "+ statusLine.toString());
    	        HttpEntity entity = response.getEntity();
    	        if (statusLine.getStatusCode() >= 300) {
    	            logger.error("Status = " + statusLine.getStatusCode() + "," + statusLine.getReasonPhrase());
    	            return null;
    	        }
    	        if (entity == null) {
    				logger.error("Response contains no content！");
    				return null;
    	        }
    	        Gson gson = new GsonBuilder().create();
    	        ContentType contentType = ContentType.getOrDefault(entity);
    	        Charset charset = contentType.getCharset();
    	        Reader reader = new InputStreamReader(entity.getContent(), charset);
    	        logger.info("entity = "+ entity.toString());
    	        return gson.fromJson(reader, classa);
    	    }
    	};

		try {
			myjson = httpclient.execute(httpget, rh);
		} catch (ClientProtocolException e) {
			logger.error("ClientProtocolException : "+e.getMessage());
			return myjson;
		} catch (IOException e) {
			logger.error("IOException : "+e.getMessage());
			return myjson;
		}
		if (myjson == null) {
			throw new CodeException(ReturnCode.CAPCARE_EXCEPTION, MessageDes.User.CAPCARE_RETURN_DATA_ERROR);
		}
    	logger.info("myjson = "+ myjson);
    	return myjson;

    }
    
    public static void main(String[] args) {
    	String url = "/LOGISTICS_INTERFACE/user/getToken.do";
    	Map<String, String> paramHash = new HashMap<String, String>();
    	paramHash.put("userId", "1");
    	httpGet(url,paramHash, null);
    }

    public static ListSlice<TruckViewInfo> capcare4Search (ListSlice<TruckViewInfo> listSliceTruckViewInfo) {
        List<String> deviceKeyList = new ArrayList<String>();
        for (TruckViewInfo truckViewInfo : listSliceTruckViewInfo.getList()) {
            if (truckViewInfo.getTruck().getDeviceKey() != null
                    && !truckViewInfo.getTruck().getDeviceKey().equals("")) {
                deviceKeyList.add(truckViewInfo.getTruck().getDeviceKey());
            }
        }
        CapcarePosition capcarePosition = null;
        if (deviceKeyList.size() >= 1) {
            capcarePosition = HTTPUtils.getPosition(deviceKeyList);
        }
        if (capcarePosition != null) {
            for (TruckViewInfo truckViewInfo : listSliceTruckViewInfo.getList()) {
                for (CapcarePosition.Position position : capcarePosition.getList()) {
                    if (truckViewInfo.getTruck().getDeviceKey() != null
                            && truckViewInfo.getTruck().getDeviceKey().equals(position.getDeviceSn())) {
                        TruckInfo truckinfo = new TruckInfo();
                        try {
                            CommonUtils.fatherToChild(truckViewInfo.getTruck(), truckinfo);
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        truckinfo.setLan(position.getLng());
                        truckinfo.setLat(position.getLat());
                        truckViewInfo.setTruck(truckinfo);
                        break;
                    }
                }
            }
        }
        return listSliceTruckViewInfo;
    }

    public static List<? extends Truck> getTruckPositions(List<Truck> trucks) {
        List<String> deviceKeyList = new ArrayList<String>();
        for (Truck truck : trucks) {
            if (truck.getDeviceKey() != null
                    && !truck.getDeviceKey().equals("")) {
                deviceKeyList.add(truck.getDeviceKey());
            }
        }
        CapcarePosition capcarePosition = null;
        if (deviceKeyList.size() >= 1) {
            capcarePosition = HTTPUtils.getPosition(deviceKeyList);
        }
        List<TruckInfo> infoList = new ArrayList<TruckInfo>();
        if (capcarePosition != null) {
            for (Truck truck : trucks) {
                TruckInfo truckinfo = new TruckInfo();
                infoList.add(truckinfo);
                try {
                    CommonUtils.fatherToChild(truck, truckinfo);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                for (CapcarePosition.Position position : capcarePosition.getList()) {
                    if (truck.getDeviceKey() != null
                            && truck.getDeviceKey().equals(position.getDeviceSn())) {
                        truckinfo.setLan(position.getLng());
                        truckinfo.setLat(position.getLat());
                    }
                }
            }
        } else {
            return trucks;
        }
        return infoList;
    }
}
