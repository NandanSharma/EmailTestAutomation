package com.email.qa.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class HtmlToTextConverter {

    public static String convertHtmlToText(String html) {
        // Parse the HTML string using Jsoup
        Document document = Jsoup.parse(html);

        // Extract the text content from the parsed HTML
        String text = document.text();

        return text;
    }

    public static void main(String[] args) {
        String html = "<body><svg class=\"proton-hidden\"><g id=\"ic-file-shapes\"><path fill-rule=\"evenodd\" d=\"M13 13a1 1 0 0 1-1 1H4a1 1 0 0 1-1-1V3a1 1 0 0 1 1-1h5v2.5A1.5 1.5 0 0 0 10.5 6H13v7Zm-.414-8L10 2.414V4.5a.5.5 0 0 0 .5.5h2.086ZM2 3a2 2 0 0 1 2-2h5.172a2 2 0 0 1 1.414.586l2.828 2.828A2 2 0 0 1 14 5.828V13a2 2 0 0 1-2 2H4a2 2 0 0 1-2-2V3Zm3 8v1h1v-1H5Zm-.5-1a.5.5 0 0 0-.5.5v2a.5.5 0 0 0 .5.5h2a.5.5 0 0 0 .5-.5v-2a.5.5 0 0 0-.5-.5h-2Z\"></path><path fill-rule=\"evenodd\" d=\"M9.5 9a.5.5 0 1 0 0-1 .5.5 0 0 0 0 1Zm0 1a1.5 1.5 0 1 0 0-3 1.5 1.5 0 0 0 0 3Z\"></path></g><g id=\"ic-cross-circle\"><path fill-rule=\"evenodd\" d=\"M8 14A6 6 0 1 0 8 2a6 6 0 0 0 0 12Zm0 1A7 7 0 1 0 8 1a7 7 0 0 0 0 14Z\"></path><path fill-rule=\"evenodd\" d=\"M5.146 5.146a.5.5 0 0 1 .708 0L8 7.293l2.146-2.147a.5.5 0 0 1 .708.708L8.707 8l2.147 2.146a.5.5 0 0 1-.708.708L8 8.707l-2.146 2.147a.5.5 0 0 1-.708-.708L7.293 8 5.146 5.854a.5.5 0 0 1 0-.708Z\"></path></g><g id=\"ic-three-dots-horizontal\"><path d=\"M4 8a1 1 0 1 1-2 0 1 1 0 0 1 2 0Z\"></path><path d=\"M9 8a1 1 0 1 1-2 0 1 1 0 0 1 2 0Z\"></path><path d=\"M14 8a1 1 0 1 1-2 0 1 1 0 0 1 2 0Z\"></path></g></svg><div id=\"proton-root\"><div style=\"display: flex !important; width: 100% !important;\"><div style=\"width: 100% !important;padding-bottom:10px;!important\"><div class=\"null\" style=\"word-wrap:break-word\"><div class=\"WordSection1\"><p class=\"MsoNormal\">Hi,</p><p class=\"MsoNormal\">&nbsp;</p><p class=\"MsoNormal\">This is a Test Automation&nbsp; - <mark class=\"proton-search-highlight\" data-auto-scroll=\"true\">Email</mark> <mark class=\"proton-search-highlight\">Assignment</mark> 2 test</p><p class=\"MsoNormal\">&nbsp;</p><p class=\"MsoNormal\">Random Strings: IwaFesW</p><p class=\"MsoNormal\">&nbsp;</p><p class=\"MsoNormal\">Regards,</p><p class=\"MsoNormal\">Nandan G R</p><p class=\"MsoNormal\">&nbsp;</p></div><div id=\"proton-toggle\"></div><div id=\"proton-blockquote\"></div></div></div></div></div></body>";
        String text = convertHtmlToText(html);
        System.out.println(text);
    }
}

