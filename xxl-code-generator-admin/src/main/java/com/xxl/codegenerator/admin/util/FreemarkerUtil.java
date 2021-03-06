package com.xxl.codegenerator.admin.util;

import com.xxl.codegenerator.core.CodeGeneratorTool;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Locale;
import java.util.Map;

/**
 * freemarker tool
 *
 * @author xuxueli 2018-05-02 19:56:00
 */
public class FreemarkerUtil {
    private static final Logger logger = LoggerFactory.getLogger(CodeGeneratorTool.class);

    /**
     * freemarker config
     */
    private static Configuration freemarkerConfig = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
    private static Configuration stringFreemarkerConfig = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);

    static {
        String templatePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        int wei = templatePath.lastIndexOf("WEB-INF/classes/");
        if (wei > -1) {
            templatePath = templatePath.substring(0, wei);
        }

            freemarkerConfig.setClassForTemplateLoading(FreemarkerUtil.class,"/xxl-code-generator");
//            freemarkerConfig.setDirectoryForTemplateLoading(new File(templatePath, "xxl-code-generator"));
            freemarkerConfig.setNumberFormat("#");
            freemarkerConfig.setClassicCompatible(true);
            freemarkerConfig.setDefaultEncoding("UTF-8");
            freemarkerConfig.setLocale(Locale.CHINA);
            freemarkerConfig.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);



            stringFreemarkerConfig.setNumberFormat("#");
            stringFreemarkerConfig.setClassicCompatible(true);
            stringFreemarkerConfig.setDefaultEncoding("UTF-8");
            stringFreemarkerConfig.setLocale(Locale.CHINA);
            stringFreemarkerConfig.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

    }

    /**
     * process Template Into String
     *
     * @param template
     * @param model
     * @return
     * @throws IOException
     * @throws TemplateException
     */
    public static String processTemplateIntoString(Template template, Object model)
            throws IOException, TemplateException {

        StringWriter result = new StringWriter();
        template.process(model, result);
        return result.toString();
    }

    /**
     * process String
     *
     * @param templateName
     * @param params
     * @return
     * @throws IOException
     * @throws TemplateException
     */
    public static String processString(String templateName, Map<String, Object> params)
            throws IOException, TemplateException {

        Template template = freemarkerConfig.getTemplate(templateName);
        String htmlText = processTemplateIntoString(template, params);
        return htmlText;
    }

    public static String processString(String templateName, String templateContent, Map<String, Object> params)
            throws IOException, TemplateException {
        // 如果原来的模板加载器不是字符串的（默认是文件加载器），则新建
        StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
        stringTemplateLoader.putTemplate(templateName, templateContent);
        stringFreemarkerConfig.setTemplateLoader(stringTemplateLoader);
        stringFreemarkerConfig.clearTemplateCache();
        Template template = stringFreemarkerConfig.getTemplate(templateName, "utf-8");
        String htmlText = processTemplateIntoString(template, params);
        stringFreemarkerConfig.removeTemplateFromCache(templateName);
        return htmlText;
    }


}
