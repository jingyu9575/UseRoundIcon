package local.linux_2g64.qw.useroundicon;

import android.content.res.XResources;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Objects;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XSharedPreferences;

@SuppressWarnings("unused")
public class Main implements IXposedHookZygoteInit {

    @Override
    public void initZygote(StartupParam startupParam) throws Throwable {
        boolean useRound = true;

        XSharedPreferences prefs = new XSharedPreferences(Main.class.getPackage().getName());
        try (InputStream is = new FileInputStream(prefs.getFile())) {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(is, null);
            document.getDocumentElement().normalize();

            NodeList nodes = document.getElementsByTagName("boolean");
            for (int i = 0; i < nodes.getLength(); ++i) {
                Element element = (Element) nodes.item(i);
                if (Objects.equals(element.getAttribute("name"), "pref_use_round")) {
                    useRound = !element.getAttribute("value").equals("false");
                    break;
                }
            }
        }

        XResources.setSystemWideReplacement("android", "bool", "config_useRoundIcon", useRound);
    }
}
