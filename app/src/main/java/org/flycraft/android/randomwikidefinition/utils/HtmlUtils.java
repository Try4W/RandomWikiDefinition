package org.flycraft.android.randomwikidefinition.utils;

public class HtmlUtils {

    /**
     * Remove first new line if needed
     * @param html raw html
     * @return trimmed html
     */

    public static String trim(String html) {
        if(html.startsWith("<p><br />")) {
            html = html.replace("\"<p><br />\"", "<p>");
        }
        return html;
    }

}
