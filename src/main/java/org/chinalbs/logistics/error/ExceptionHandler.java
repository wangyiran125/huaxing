package org.chinalbs.logistics.error;

import org.chinalbs.logistics.common.json.Response;
import org.chinalbs.logistics.common.json.ResponseHelper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

/**
 * General error handler for the application.
 */
@ControllerAdvice
class ExceptionHandler {
//
//	/**
//	 * Handle exceptions thrown by handlers.
//	 */
//	@org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)	
//	public ModelAndView exception(Exception exception, WebRequest request) {
//		ModelAndView modelAndView = new ModelAndView("error/general");
//		modelAndView.addObject("errorMessage", Throwables.getRootCause(exception));
//		return modelAndView;
//	}
	/**
	 * Handle exceptions thrown by handlers.
	 */
	@org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public Response<?> exception(Exception exception, WebRequest request) {
		exception.printStackTrace();
        if (exception instanceof CodeException) {
            ResponseHelper.createResponse(((CodeException)exception).getCode(), exception.getMessage());
        }
		return ResponseHelper.createExceptionResponse(exception);
	}
	
	
	/**
	 * Handle exceptions thrown by handlers.
	 */
	@org.springframework.web.bind.annotation.ExceptionHandler(value = CodeException.class)	
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public Response<?> handleCodeException(CodeException exception, WebRequest request) {
		exception.printStackTrace();
         return ResponseHelper.createResponse(exception.getCode(), exception.getMessage());

	}
}