package org.dromara.web.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.utils.SpringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 首页
 *
 * @author Lion Li
 */
@SaIgnore
@RequiredArgsConstructor
@RestController
public class IndexController {

    /**
     * 访问首页，提示语
     */
    @GetMapping("/")
    public ResponseEntity<String> index() {
        String appName = SpringUtils.getApplicationName();
        String html = buildWelcomePage(appName);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_HTML);

        return new ResponseEntity<>(html, headers, HttpStatus.OK);
    }

    /**
     * 构建欢迎页面 HTML
     */
    private String buildWelcomePage(String appName) {
        return "<!DOCTYPE html>\n" +
            "<html lang=\"zh-CN\">\n" +
            "<head>\n" +
            "    <meta charset=\"UTF-8\">\n" +
            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
            "    <title>" + appName + "</title>\n" +
            "    <style>\n" +
            "        * {\n" +
            "            margin: 0;\n" +
            "            padding: 0;\n" +
            "            box-sizing: border-box;\n" +
            "        }\n" +
            "        body {\n" +
            "            font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', Arial, sans-serif;\n" +
            "            background: linear-gradient(135deg, #1890ff 0%, #096dd9 100%);\n" +
            "            min-height: 100vh;\n" +
            "            display: flex;\n" +
            "            align-items: center;\n" +
            "            justify-content: center;\n" +
            "            padding: 20px;\n" +
            "        }\n" +
            "        .container {\n" +
            "            background: white;\n" +
            "            border-radius: 20px;\n" +
            "            box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3);\n" +
            "            max-width: 600px;\n" +
            "            width: 100%;\n" +
            "            padding: 60px 40px;\n" +
            "            text-align: center;\n" +
            "        }\n" +
            "        .logo {\n" +
            "            width: 120px;\n" +
            "            height: 120px;\n" +
            "            background: linear-gradient(135deg, #1890ff 0%, #096dd9 100%);\n" +
            "            border-radius: 50%;\n" +
            "            margin: 0 auto 30px;\n" +
            "            display: flex;\n" +
            "            align-items: center;\n" +
            "            justify-content: center;\n" +
            "            font-size: 48px;\n" +
            "            color: white;\n" +
            "            box-shadow: 0 10px 30px rgba(24, 144, 255, 0.4);\n" +
            "        }\n" +
            "        h1 {\n" +
            "            color: #333;\n" +
            "            font-size: 32px;\n" +
            "            margin-bottom: 20px;\n" +
            "            font-weight: 600;\n" +
            "        }\n" +
            "        .subtitle {\n" +
            "            color: #666;\n" +
            "            font-size: 18px;\n" +
            "            line-height: 1.6;\n" +
            "            margin-bottom: 40px;\n" +
            "        }\n" +
            "        .info-card {\n" +
            "            background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);\n" +
            "            border-radius: 12px;\n" +
            "            padding: 25px;\n" +
            "            margin-bottom: 30px;\n" +
            "            text-align: left;\n" +
            "        }\n" +
            "        .info-card h3 {\n" +
            "            color: #333;\n" +
            "            font-size: 16px;\n" +
            "            margin-bottom: 12px;\n" +
            "            font-weight: 600;\n" +
            "        }\n" +
            "        .info-card p {\n" +
            "            color: #666;\n" +
            "            font-size: 14px;\n" +
            "            line-height: 1.8;\n" +
            "            margin-bottom: 8px;\n" +
            "        }\n" +
            "        .info-card code {\n" +
            "            background: white;\n" +
            "            padding: 2px 8px;\n" +
            "            border-radius: 4px;\n" +
            "            font-family: 'Courier New', monospace;\n" +
            "            color: #e74c3c;\n" +
            "            font-weight: 500;\n" +
            "        }\n" +
            "        .btn {\n" +
            "            display: inline-block;\n" +
            "            padding: 14px 32px;\n" +
            "            background: linear-gradient(135deg, #1890ff 0%, #096dd9 100%);\n" +
            "            color: white;\n" +
            "            text-decoration: none;\n" +
            "            border-radius: 30px;\n" +
            "            font-size: 16px;\n" +
            "            font-weight: 600;\n" +
            "            transition: all 0.3s ease;\n" +
            "            box-shadow: 0 5px 20px rgba(24, 144, 255, 0.4);\n" +
            "        }\n" +
            "        .btn:hover {\n" +
            "            transform: translateY(-2px);\n" +
            "            box-shadow: 0 8px 25px rgba(24, 144, 255, 0.6);\n" +
            "        }\n" +
            "        .footer {\n" +
            "            margin-top: 40px;\n" +
            "            color: #999;\n" +
            "            font-size: 14px;\n" +
            "        }\n" +
            "        .footer a {\n" +
            "            color: #1890ff;\n" +
            "            text-decoration: none;\n" +
            "            transition: color 0.3s ease;\n" +
            "        }\n" +
            "        .footer a:hover {\n" +
            "            color: #096dd9;\n" +
            "        }\n" +
            "        .status {\n" +
            "            display: inline-block;\n" +
            "            padding: 6px 16px;\n" +
            "            background: #e8f5e9;\n" +
            "            color: #2e7d32;\n" +
            "            border-radius: 20px;\n" +
            "            font-size: 13px;\n" +
            "            font-weight: 500;\n" +
            "            margin-bottom: 20px;\n" +
            "        }\n" +
            "    </style>\n" +
            "</head>\n" +
            "<body>\n" +
            "    <div class=\"container\">\n" +
            "        <div class=\"logo\">⚡</div>\n" +
            "        <div class=\"status\">✓ 服务运行正常</div>\n" +
            "        <h1>欢迎使用 " + appName + "</h1>\n" +
            "        <p class=\"subtitle\">这是一个功能强大的后台管理系统框架</p>\n" +
            "        \n" +
            "        <div class=\"info-card\">\n" +
            "            <h3>📌 访问说明</h3>\n" +
            "            <p>• 后端API地址：<code>http://localhost:8080</code></p>\n" +
            "            <p>• 前端页面地址：<code>http://localhost</code></p>\n" +
            "            <p>• API文档地址：<code>http://localhost:8080/swagger-ui.html</code></p>\n" +
            "        </div>\n" +
            "        \n" +
            "        <a href=\"http://localhost\" class=\"btn\">前往前端页面</a>\n" +
            "        \n" +
            "        <div class=\"footer\">\n" +
            "            <p>© 2025 RuoYi-Vue-Plus | 基于 Spring Boot 开发</p>\n" +
            "            <p><a href=\"https://gitee.com/dromara/RuoYi-Vue-Plus\" target=\"_blank\">查看项目源码</a></p>\n" +
            "        </div>\n" +
            "    </div>\n" +
            "</body>\n" +
            "</html>";
    }

}
