package org.chinalbs.logistics.controller;

import org.chinalbs.logistics.annotation.OperationDefinition;
import org.chinalbs.logistics.common.utils.IOUtils;
import org.chinalbs.logistics.common.utils.ResponseEntityUtils;
import org.chinalbs.logistics.config.PathConfig;
import org.chinalbs.logistics.session.SessionInfo;
import org.chinalbs.logistics.session.UserInfo;
import org.chinalbs.logistics.utils.Consts;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Jason on 15/4/21.
 * All Rights Reserved.
 */
@Controller()
public class PagesController {

	private static Logger logger = LoggerFactory.getLogger(PagesController.class); 
	
    public static final String HEADER_START_TAG = "<!--HEADER_START-->";
    public static final String HEADER_END_TAG = "<!--HEADER_END-->";

    public static final String FOOTER_START_TAG = "<!--FOOTER_START-->";
    public static final String FOOTER_END_TAG = "<!--FOOTER_END-->";

    //车主菜单
    private static MenuItem TRUCK_HOME_PAGE = new MenuItem("首页", "truck_index.html");
    private static MenuItem TRUCK_ADD_PLAN = new MenuItem("我要找货", "truck_search_goods.html");
    private static MenuItem SEARCH_GOODS = new MenuItem("发布车源", "truck_addplan.html");
    private static MenuItem TRUCK_MY_GOODS = new MenuItem("我的抢单", "truck_my_goods.html");
    private static MenuItem TRUCK_PLAN = new MenuItem("我的车源", "truck_myplan.html", "truck_edit_plan.html");
    private static MenuItem CAP_CARE = new MenuItem("车辆监控", "cap_care.html");
    private static MenuItem TRUCK_AIDER_CENTER = new MenuItem("个人中心", "aidercenter.html");
    private static MenuItem FEEDBACK = new MenuItem("意见反馈", "feedback.html");

    //货主菜单
    private static MenuItem GOODS_HOME_PAGE = new MenuItem("首页", "goods_index.html");
    private static MenuItem GOODS_ADD = new MenuItem("我要发货", "goods_add.html");
    private static MenuItem GOODS_SEARCH_WAREHOUSE = new MenuItem("找仓库", "goods_search_warehouse.html");
    private static MenuItem GOODS_SEARCH_TRUCK = new MenuItem("找车", "goods_search_truck.html", "goods_search_truck_detailed.html");
    private static MenuItem GOODS_LIST = new MenuItem("我的货物", "goods_list.html", "goods_update.html", "goods_detailed.html");
    private static MenuItem GOODS_AIDER_CENTER = new MenuItem("个人中心", "aidercenter.html", "truck_historyorder_item.html");

    //仓库主菜单
    private static MenuItem WAREHOUSE_HOME_PAGE = new MenuItem("首页", "goods_index.html");
    private static MenuItem WAREHOUSE_LIST = new MenuItem("我的仓库", "warehouse_list.html", "warehouse_update.html");
    private static MenuItem WAREHOUSE_ADD = new MenuItem("发布仓库", "warehouse_add.html");

    private static final MenuItem[] TRUCK_OWNER_MENU = new MenuItem[] {
            TRUCK_HOME_PAGE, TRUCK_ADD_PLAN, SEARCH_GOODS, TRUCK_MY_GOODS, TRUCK_PLAN, CAP_CARE, TRUCK_AIDER_CENTER, FEEDBACK
    };

    private static final MenuItem[] GOODS_OWNER_MENU = new MenuItem[] {
            GOODS_HOME_PAGE, GOODS_ADD, GOODS_SEARCH_WAREHOUSE, GOODS_SEARCH_TRUCK, GOODS_LIST, GOODS_AIDER_CENTER, FEEDBACK
    };

    private static final MenuItem[] WAREHOUSE_OWNER_MENU = new MenuItem[] {
            WAREHOUSE_HOME_PAGE, GOODS_ADD,WAREHOUSE_ADD, GOODS_SEARCH_TRUCK, GOODS_LIST, WAREHOUSE_LIST, GOODS_AIDER_CENTER, FEEDBACK
    };

    public static final String HEADER_TEMPLATE_FILE  = "/pages/tmpl/header.html";
    public static final String FOOTER_TEMPLATE_FILE  = "/pages/tmpl/bottom.html";
    public static final String MODEL_TEMPLATE_FILE  = "/pages/tmpl/model.html";

    private HeaderBuilder headerBuilder;

    @OperationDefinition(name = "pages", anonymous = true)
    @RequestMapping(value = "**/*.html", produces = {MediaType.TEXT_HTML_VALUE})
    @ResponseBody
    public ResponseEntity<?> page(HttpServletRequest request) throws Exception {
        long start = System.currentTimeMillis();
        initHeaderBuilder(request);
        String path = request.getServletPath();
        String header = populateHeader(path);
        String footer = populateFooter(new File(request.getServletContext().getRealPath(FOOTER_TEMPLATE_FILE)));
        String model = getModel(new File(request.getServletContext().getRealPath(MODEL_TEMPLATE_FILE)));
        File file = new File(request.getServletContext().getRealPath(path));
        if (!file.exists()) {
            return ResponseEntityUtils.createNotFoundResponse("Not found " + path , MediaType.TEXT_HTML);
        }
        String content = readFile(file);
        content = replaceHeader(content, header);
        content = replaceFooter(content, footer);
        if (path.contains("pages")) {
            content = content.replace("</body>", model + "</body>");
        }
        if (SessionInfo.getCurrent() != null) {
            logger.info("userId="+SessionInfo.getCurrent().getUserInfo().getUserId() +
            		",userName="+SessionInfo.getCurrent().getUserInfo().getUsername() +
            		",url="+path);
            content = content.replace("CAP_CARE_TOKEN", SessionInfo.getCurrent().getUserInfo().getCapcareToken());
        }
        long end = System.currentTimeMillis();
        content += "<!--" + (end - start) + "-->";
        return ResponseEntityUtils.creatOKResponse(new ByteArrayResource(content.getBytes("utf-8")), MediaType.TEXT_HTML);
    }

    private void initHeaderBuilder(HttpServletRequest request) {
        if (headerBuilder == null) {
            headerBuilder = new HeaderBuilder(request.getServletContext().getRealPath(HEADER_TEMPLATE_FILE));
        }
    }

    private String populateHeader(String path) throws IOException {
        SessionInfo sessionInfo = SessionInfo.getCurrent();
        if (sessionInfo == null) {
            return "";
        }
        UserInfo userInfo = sessionInfo.getUserInfo();
        if (userInfo == null) {
            return "";
        }
        String username = userInfo.getUsername();
        StringBuilder html = new StringBuilder();
        MenuItem[] menu = null;
        if (userInfo.getRole() == Consts.Role.TRUCKOWNER || userInfo.getRole() == Consts.Role.DRIVER) {
            menu = TRUCK_OWNER_MENU;
        } else if(userInfo.getRole() == Consts.Role.GOODSOWNER) {
            menu = GOODS_OWNER_MENU;
        } else if (userInfo.getRole() == Consts.Role.WAREHOUSEOWNER) {
            menu = WAREHOUSE_OWNER_MENU;
        } else {
            menu = new MenuItem[0];
        }
        for (MenuItem mi : menu) {
            html.append(mi.toHtml(mi == TRUCK_OWNER_MENU[0], mi.isActive(path)));
        }

        return headerBuilder.build(username, html.toString());
    }

    private String populateFooter(File file) throws IOException {
        return readFile(file);
    }

    private String getModel(File file) throws IOException {
        return readFile(file);
    }

    private String replaceHeader(String content, String header) {
        int startPos = content.indexOf(HEADER_START_TAG);
        int endPos = content.indexOf(HEADER_END_TAG);
        if (startPos < 0 || endPos < 0) {
            return content;
        }
        return content.substring(0, startPos) + header + content.substring(endPos + HEADER_END_TAG.length());
    }

    private String replaceFooter(String content, String footer) {
        int startPos = content.indexOf(FOOTER_START_TAG);
        int endPos = content.indexOf(FOOTER_END_TAG);
        if (startPos < 0 || endPos < 0) {
            return content;
        }
        return content.substring(0, startPos) + footer + content.substring(endPos + HEADER_END_TAG.length());
    }


    private static class MenuItem {
        String title;
        String link;
        List<String> childLinks = new ArrayList<String>();
        MenuItem(String title, String link) {
            this.title = title;
            this.link = link;
        }
        MenuItem(String title, String link, String... childLinks) {
            this.title = title;
            this.link = link;
            this.childLinks.addAll(Arrays.asList(childLinks));
        }
        public String toHtml(boolean first, boolean active) {
            return "                    <li" + (first ? " style=\"margin-left:0px;\"" : "") +
                    (active ? " class=\"active\"" : "") + ("><a href=\"")
                    + this.link + "\">" + this.title + "</a></li>\n";
        }

        public boolean isActive(String url) {
            if (url.contains(this.link)) return true;
            for (String link : childLinks) {
                if (url.contains(link)) {
                    return true;
                }
            }
            return false;
        }
    }

    private static class HeaderBuilder {
        public static final String USERNAME_TAG = "<!--USERNAME-->";
        public static final String MENU_START_TAG = "<!--MENU_START-->";
        public static final String MENU_END_TAG = "<!--MENU_END-->";
        private String templateFile;
        private String template = "";
        HeaderBuilder(String templateFile) {
            this.templateFile = templateFile;
        }
        public String build(String username, String menu) throws IOException {
            if (template == null || template.isEmpty()) {
                synchronized (this) {
                    template = readFile(new File(templateFile));
                    String roles = PathConfig.INSTANCE.getLoginPermission();
                    if (roles.contains("4") && roles.contains("5")) {
                        template = template.replace("images/logo.png", "images/logo_c.png");
                    }
                    template = template.replace(USERNAME_TAG, "%s");
                    int menuStartPos = template.indexOf(MENU_START_TAG);
                    int menuEndPos = template.indexOf(MENU_END_TAG);
                    if (menuStartPos >= 0 && menuEndPos >= 0) {
                        template = template.substring(0, menuStartPos) + "%s" + template.substring(menuEndPos + MENU_END_TAG.length());
                    } else {
                        template = "header template file is not valid, please check it!";
                    }

                }
            }
            return String.format(template, username, menu);
        }
    }

    private static String readFile(File file) throws IOException {
        if (!file.exists()) {
            return "";
        }
        byte[] buffer = new byte[(int)file.length()];
        InputStreamReader fileReader = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(buffer);
        } finally {
            IOUtils.closeQuietly(fileReader);
        }
        return new String(buffer, "utf-8");
    }
}
