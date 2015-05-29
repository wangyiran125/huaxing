package org.chinalbs.logistics.controller;

import org.chinalbs.logistics.annotation.OperationDefinition;
import org.chinalbs.logistics.config.PathConfig;
import org.chinalbs.logistics.session.SessionInfo;
import org.chinalbs.logistics.session.UserInfo;
import org.chinalbs.logistics.utils.Consts;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Jason on 15/4/23.
 * All Rights Reserved.
 */
@Controller
public class RedirectController {
    @OperationDefinition(name="index", anonymous = true)
    @RequestMapping("/**/index.html")
    public String index() {
        SessionInfo sessionInfo = SessionInfo.getCurrent();
        UserInfo userInfo = sessionInfo == null ? null : sessionInfo.getUserInfo();
        if (userInfo == null) {
            return "redirect:/pages/login.html";
        } else if (userInfo.getRole() == Consts.Role.TRUCKOWNER || userInfo.getRole() == Consts.Role.DRIVER) {
            return "redirect:/pages/truck_index.html";
        } else if (userInfo.getRole() == Consts.Role.GOODSOWNER || userInfo.getRole() == Consts.Role.WAREHOUSEOWNER) {
            return "redirect:/pages/goods_index.html";
        }
        return "redirect:/pages/login.html";
    }

    @OperationDefinition(name="aider_center", anonymous = true)
    @RequestMapping("/pages/aidercenter.html")
    public String aiderCenter() {
        SessionInfo sessionInfo = SessionInfo.getCurrent();
        UserInfo userInfo = sessionInfo == null ? null : sessionInfo.getUserInfo();
        if (userInfo != null) {
            if (userInfo.getRole() == Consts.Role.TRUCKOWNER) {
                return "redirect:/pages/truck_aidercenter.html";
            } else if (userInfo.getRole() == Consts.Role.GOODSOWNER || userInfo.getRole() == Consts.Role.WAREHOUSEOWNER) {
                return "redirect:/pages/goods_aidercenter.html";
            } else if (userInfo.getRole() == Consts.Role.DRIVER) {
                return "redirect:/pages/driver_aidercenter.html";
            }
        }
        return "redirect:/pages/index.html";
    }

    @OperationDefinition(name="register", anonymous = true)
    @RequestMapping("/pages/register.html")
    public String register() {
        String roles = PathConfig.INSTANCE.getLoginPermission();
        if (roles.contains("2") && roles.contains("3")) {
                return "redirect:/pages/goods_register.html";
        } if (roles.contains("4") && roles.contains("5")) {
            return "redirect:/pages/truck_register.html";
        } else {
            return "redirect:/pages/all_register.html";
        }
    }
}
