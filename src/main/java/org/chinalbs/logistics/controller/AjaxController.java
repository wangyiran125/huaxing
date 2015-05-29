package org.chinalbs.logistics.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.security.SecureRandom;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.chinalbs.logistics.annotation.OperationDefinition;
import org.chinalbs.logistics.common.utils.CookieUtils;
import org.chinalbs.logistics.common.utils.VerifyCodeUtils;


@Controller
@RequestMapping(value = "/ajaxcontrol")
public class AjaxController implements AnonymousAccessController {
	
	private Random rand = new SecureRandom();
	
	private static Logger logger = LoggerFactory.getLogger(AjaxController.class);
	
	@RequestMapping(value = "/checkverify", method = RequestMethod.GET)
    public @ResponseBody boolean checkVerify(HttpServletRequest request, HttpServletResponse response) {
    	String vcode = (String)request.getParameter("vcode");
    	
        if (VerifyCodeUtils.checkVerifyCode(vcode, request, response)) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }
	
	@RequestMapping(value = "/checklogin", method = RequestMethod.GET)
    public @ResponseBody boolean checkLoginStatus(HttpServletRequest request) {
        
        if ( CookieUtils.getUserId(request) != null ) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }
	@OperationDefinition(name = "生成验证码",anonymous = true)
    @RequestMapping(value = "/verifycode", method = RequestMethod.GET)
    public void getVerifyCodeImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	
    	logger.info("verifycode requested!");
    	response.setContentType("image/jpeg");
        response.setHeader("Pragma","No-cache");
        response.setHeader("Cache-Control","no-cache");
        response.setDateHeader("Expires", 0);       

        // 图像宽高
        int width = 100, height = 30;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // 创建画图
        Graphics g = image.getGraphics();

        // 背景色
        // g.setColor(getRandColor(200,250));
        g.setColor(new Color(255,255,255));
        g.fillRect(0, 0, width, height);

        // 字体
        g.setFont(new Font("Arial", Font.TRUETYPE_FONT, 25));
        
        // 获取随机颜色
        Color fontColor = getRandColor(0,220);
        StringBuilder vcode = new StringBuilder();
        String aCode = null;
        
        for (int i=0;i<4;i++)
        {
            aCode = String.valueOf(rand.nextInt(10));
            vcode.append(aCode);
            // 设置绘制文字的颜色
            g.setColor(fontColor);
            // g.setColor(new Color(20+random.nextInt(110),20+random.nextInt(110),20+random.nextInt(110)));//���ú����4����ɫ��ͬ����������Ϊ����̫�ӽ�����ֻ��ֱ�����
            g.drawString(aCode, 20*i, 20);
        }

        VerifyCodeUtils.setVerifyCode(vcode.toString(), request, response);
        // 销毁绘图graphics
        g.dispose();
        // 获取输出流
        OutputStream out = response.getOutputStream();
        try
        {
            ImageIO.write(image, "JPEG", out);
            out.flush();
        }
        finally
        {
            out.close();
        }
    }
    
    private Color getRandColor(int fcc, int bcc)
    {   //获取随机颜色
        int fc = fcc;
        int bc = bcc;
        if(fc > 255) fc = 255;
        if(bc > 255) bc = 255;
        int r = fc + rand.nextInt(bc-fc);
        int g = fc + rand.nextInt(bc-fc);
        int b = fc + rand.nextInt(bc-fc);
        return new Color(r,g,b);
    }
}
