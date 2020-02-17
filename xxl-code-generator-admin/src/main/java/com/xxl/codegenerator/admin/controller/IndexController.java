package com.xxl.codegenerator.admin.controller;

import com.xxl.codegenerator.admin.model.ReturnT;
import com.xxl.codegenerator.admin.util.FreemarkerUtil;
import com.xxl.codegenerator.admin.util.IPUtils;
import com.xxl.codegenerator.core.CodeGeneratorTool;
import com.xxl.codegenerator.core.model.ClassInfo;
import com.xxl.codegenerator.core.model.FieldInfo;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * sso server (for web)
 *
 * @author xuxueli 2017-08-01 21:39:47
 */

@Controller
public class IndexController {
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @RequestMapping("/")
    public String index(HttpServletRequest request) {

        logger.info("index访问ip {}", IPUtils.getIpAddr(request));
        return "index";
    }

    @RequestMapping("/codeGenerate")
    @ResponseBody
    public ReturnT<Map<String, String>> codeGenerate(String tableSql ,HttpServletRequest request) {
        logger.info("codeGenerate访问ip {}", IPUtils.getIpAddr(request));
        try {

            if (StringUtils.isBlank(tableSql)) {
                return new ReturnT<>(ReturnT.FAIL_CODE, "表结构信息不可为空");
            }

            // parse table
            ClassInfo classInfo = CodeGeneratorTool.processTableIntoClassInfo(tableSql);

            // code genarete
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("classInfo", classInfo);

            // result
            Map<String, String> result = new HashMap<String, String>();

            result.put("controller_code", FreemarkerUtil.processString("controller.ftl", params));
            result.put("service_code", FreemarkerUtil.processString("service.ftl", params));
            result.put("service_impl_code", FreemarkerUtil.processString("service_impl.ftl", params));

            result.put("dao_code", FreemarkerUtil.processString("dao.ftl", params));
            result.put("mybatis_code", FreemarkerUtil.processString("mybatis.ftl", params));
            result.put("model_code", FreemarkerUtil.processString("model.ftl", params));
            result.put("iview_code", FreemarkerUtil.processString("iview.ftl", params));
            return new ReturnT<Map<String, String>>(result);
        } catch (IOException | TemplateException e) {
            logger.error(e.getMessage(), e);
            return new ReturnT<Map<String, String>>(ReturnT.FAIL_CODE, "表结构解析失败");
        }

    }


    @RequestMapping("/codeGenerateCustom")
    @ResponseBody
    public ReturnT<Map<String, String>> codeGenerateCustom(String tableSql,String customftl,HttpServletRequest request) {
        logger.info("codeGenerateCustom访问ip {}", IPUtils.getIpAddr(request));
        try {

            if (StringUtils.isBlank(tableSql)) {
                return new ReturnT<Map<String, String>>(ReturnT.FAIL_CODE, "表结构信息不可为空");
            }


            if (StringUtils.isBlank(customftl)) {
                return new ReturnT<Map<String, String>>(ReturnT.FAIL_CODE, "自定义模板信息不可为空");
            }

            // parse table
            ClassInfo classInfo = CodeGeneratorTool.processTableIntoClassInfo(tableSql);

            // code genarete
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("classInfo", classInfo);

            // result
            Map<String, String> result = new HashMap<String, String>();

            result.put("custom_code", FreemarkerUtil.processString("emmmm",customftl, params));


            return new ReturnT<Map<String, String>>(result);
        } catch (IOException | TemplateException e) {
            logger.error(e.getMessage(), e);
            return new ReturnT<Map<String, String>>(ReturnT.FAIL_CODE, "表结构解析失败");
        }

    }

    @RequestMapping("/selectSqlParseGenerate")
    @ResponseBody
    public ReturnT<Map<String, String>> selectSqlStrIntoVO(String selectSql ,HttpServletRequest request) {
        logger.info("codeGenerate访问ip {}", IPUtils.getIpAddr(request));
        try {

            if (StringUtils.isBlank(selectSql)) {
                return new ReturnT<>(ReturnT.FAIL_CODE, "表结构信息不可为空");
            }

            // parse table
            List<FieldInfo> fieldInfos = CodeGeneratorTool.processSelectSqlIntoClassInfo(selectSql);

            // code genarete
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("fieldInfos", fieldInfos);

            // result
            Map<String, String> result = new HashMap<String, String>();

            result.put("selectsqlVO_code", FreemarkerUtil.processString("selectsqlVO.ftl", params));

            return new ReturnT<Map<String, String>>(result);
        } catch (IOException | TemplateException e) {
            logger.error(e.getMessage(), e);
            return new ReturnT<Map<String, String>>(ReturnT.FAIL_CODE, "表结构解析失败");
        }

    }

}