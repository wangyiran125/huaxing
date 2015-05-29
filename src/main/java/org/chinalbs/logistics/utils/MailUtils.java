package org.chinalbs.logistics.utils;


import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.apache.axis.encoding.Base64;
import org.chinalbs.logistics.vo.AttachmentInfo;

public class MailUtils{
//	/** 公司操作默认邮箱地址 **/
//	String email = SiteUrl.readUrl("mail.address");
//	/** 公司操作默认邮箱密码**/
//	String password = SiteUrl.readUrl("mail.password");
	
	/** 公司操作默认邮箱地址 **/
	private static final String email = "15562369233@163.com";
	/** 公司操作默认邮箱密码**/
	private static final String password = "w0000000";
	private static final String RESET_PASSWORD_SUBJECT = "重置您在中国位置物流管理平台的密码";
	
	/**
	 * 根据Mail对象创建一封邮件并使用默认邮箱发送出去
	 * @param recMail 接收者邮箱
	 * @param subject 主题
	 * @param content 内容
	 * @param attace 附件
	 * @throws Exception
	 */
	public static void sendMail(String recMail, String subject, String content, AttachmentInfo... attachments) throws Exception{
		Properties props = new Properties();
		props.setProperty("mail.smtp.auth", "true"); //指定连接服务器是否需要校验
		props.setProperty("mail.transport.protocol", "smtp");//指定连接服务器使用的协议
		props.setProperty("mail.host", getSmtp(email)); //设置连接服务器的主机地址
		props.setProperty("mail.port", "25");  //设置连接到服务器的哪个端口,不设置使用默认端口
		
		Session session = Session.getInstance(props, new Authenticator() //校验信息返回器，可以返回校验信息
		{
			protected PasswordAuthentication getPasswordAuthentication()
			{
				return new PasswordAuthentication(email.substring(0, email.indexOf("@")), password); //返回用户名和密码
			}
		});
		
//		session.setDebug(true);
		
		javax.mail.Message msg = new MimeMessage(session); //代表一封邮件
		msg.setSubject(MimeUtility.encodeText(subject, "utf-8", "B"));
//		msg.setFrom(new InternetAddress("\"" + MimeUtility.encodeText("翼机通") + "\" <" + email + ">"));
		msg.setFrom(new InternetAddress(email, "中国位置物流管理平台", "utf-8"));
		msg.setReplyTo(null); //设置答复地址
		msg.setRecipients(javax.mail.Message.RecipientType.TO, InternetAddress.parse(MimeUtility.encodeText(recMail) + " <" + recMail + ">"));
		
		// 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
        Multipart multipart = new MimeMultipart();         
      
        //   设置邮件的文本内容
        BodyPart contentPart = new MimeBodyPart();
//        contentPart.setText(content);
        contentPart.setContent(content, "text/html;charset=utf-8");
        System.out.println(content);
        multipart.addBodyPart(contentPart);
        for(AttachmentInfo attachment : attachments){
        	 //添加附件
            BodyPart messageBodyPart= new MimeBodyPart();
            DataSource source = new FileDataSource(attachment.getFile());
            //添加附件的内容
            messageBodyPart.setDataHandler(new DataHandler(source));
            //添加附件的标题
            //这里很重要，通过下面的Base64编码的转换可以保证你的中文附件标题名在发送时不会变成乱码
            messageBodyPart.setFileName("=?utf-8?B?" + Base64.encode(attachment.getAttachmentName().getBytes("utf-8")) + "?=");
            multipart.addBodyPart(messageBodyPart);
        }
      
        //将multipart对象放到message中
        msg.setContent(multipart);
		
		msg.saveChanges();
		
		Transport.send(msg);
	}
	
	public static void sendFindPasswordUrlToMail(String recMail, String userUrl, String username){
		String content = String.format("您好 %s，<br/><br/>您发送了重置中国位置物流管理平台密码的申请。<br/><br/>请点击下边的链接进行确认，之后您将可以设置一个新密码，链接有效时间为30分钟。<br/><br/><a href='%s'>%s</a><br/><br/>感谢您使用中国位置物流管理平台<br/><br/>北斗导航位置服务(北京)有限公司", username, userUrl, userUrl);
		try {
			sendMail(recMail, RESET_PASSWORD_SUBJECT, content);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void sendCustAttendanceToMail(String recMail, AttachmentInfo attachment){
		String fileName = attachment.getAttachmentName();
		String subject = fileName.substring(0, fileName.lastIndexOf("."));
		String content = subject;
		try {
			sendMail(recMail, subject, content, attachment);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//	/**
//	 * 根据邮箱地址返回其pop3服务器地址
//	 * @param mail
//	 * @return
//	 */
//	private static String getPop3(String mail)
//	{
//		int index = mail.indexOf("@");
//		String address = mail.substring(index + 1, mail.indexOf(".", index));
//		String pop3 = "pop3." + address + ".com";
//		return pop3;
//	}
	
	/**
	 * 根据邮箱地址返回其smtp服务器地址
	 * @param mail
	 * @return
	 */
	private static String getSmtp(String mail)
	{
		int index = mail.indexOf("@");
		String address = mail.substring(index + 1, mail.indexOf(".", index));
		String pop3 = "smtp." + address + ".com";
		return pop3;
	}
	
	public static void main(String[] args) throws Exception
	{
		//MailUtils.sendMail("429730367@qq.com", "测试邮件", "测试邮件内容");
		sendFindPasswordUrlToMail("429730367@qq.com", "http://56.chinalbs.org/api/pwdreset/sfdnnkdieo98dn", "刘望望");
	}
}
