package org.chinalbs.logistics.controller;

import org.chinalbs.logistics.annotation.OperationDefinition;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Jason on 15/4/24.
 * All Rights Reserved.
 */
@Controller
public class ThemeController {
    private String themeFile = "blue_theme.css";

    @OperationDefinition(name = "theme", anonymous = true)
    @RequestMapping("/pages/css/theme.css")
        public String theme() {
        return "redirect:/pages/css/" + themeFile;
    }

    @OperationDefinition(name = "set_theme", anonymous = true)
    @RequestMapping("/theme/{path}")
    @ResponseBody
    public String theme(@PathVariable String path) {
        this.themeFile = path + ".css";
        return themeFile;
    }
}
