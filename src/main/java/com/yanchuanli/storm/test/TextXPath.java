package com.yanchuanli.storm.test;

import org.apache.log4j.Logger;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

/**
 * Copyright Candou.com
 * Author: Yanchuan Li
 * Email: mail@yanchuanli.com
 * Date: 8/18/12
 */
public class TextXPath {

    private static String html = "<html>\n" +
            "<head>\n" +
            "    <title>123</title>\n" +
            "</head>\n" +
            "<body>\n" +
            "\n" +
            "</body>\n" +
            "</html>";

    private static String xpath = "//head//title";

    private static Logger log = Logger.getLogger(TextXPath.class);

    public static void main(String[] args) throws XPatherException {
        HtmlCleaner cleaner = new HtmlCleaner();
        CleanerProperties props = cleaner.getProperties();
        props.setAllowHtmlInsideAttributes(true);

        props.setAllowMultiWordAttributes(true);
        props.setRecognizeUnicodeChars(true);

        props.setOmitComments(true);
        TagNode node = cleaner.clean(html);
        Object[] myNodes = node.evaluateXPath(xpath);
        for (Object o : myNodes) {
            TagNode info_node = (TagNode) o;
            log.info(info_node.getText());
        }
    }

}
