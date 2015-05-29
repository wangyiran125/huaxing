package org.chinalbs.logistics.common.json;




public class ResponseHelper {

    public static <T> Response<T> createSuccessResponse() {
        return createResponse(ReturnCode.SUCCESS, null);
    }

    public static <T> Response<T> createSuccessResponse(T payload) {
        Response<T> response = createResponse(ReturnCode.SUCCESS, null);
        response.setPayload(payload);
        return response;
    }
    
    
    public static <T> Response<T> createResponse() {
        return createResponse(ReturnCode.NEVER_USED_CODE, null);
    }

    public static <T> Response<T> createResponse(int code, String description) {
        Response<T> response = new Response<T>();
        if (code != ReturnCode.NEVER_USED_CODE) response.setCode(code);
        if (description != null) response.setDescription(description);
        return response;
    }
    
    public static <T> Response<T> createResponse(int code, long lastUpdateTime) {
        Response<T> response = new Response<T>();
        if (code != ReturnCode.NEVER_USED_CODE) response.setCode(code);
        response.setLastUpdateTime(lastUpdateTime);
        return response;
    }
    
    public static <T> Response<T> createBusinessErrorResponse(String description) {
        return createResponse(ReturnCode.BUSINESS_ERROR, description);
    }
    
    public static <T> Response<T> createExceptionResponse(Exception e) {
        return createResponse(ReturnCode.EXCEPTION, "系统异常，请联系管理人员处理！");
    }

//    public static Response createConstraintViolationErrorResponse(Request request, ConstraintViolationException e) {
//        StringBuilder sb = new StringBuilder();
//        for (ConstraintViolation<?> cv : e.getConstraintViolations()) {
//            if (sb.length() > 0) sb.append(", ");
//            sb.append(cv.getMessage());
//        }
//        return createResponse(request, ReturnCode.CONSTRAINT_VIOLATION_ERROR, sb.toString());
//    }

}
