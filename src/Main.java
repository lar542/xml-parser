import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class Main {

    public static void setNodeAttribute(Map<String, Integer> resultMap, NamedNodeMap attributes) {
        for (int i = 0; i < attributes.getLength(); i++) {
            Node attribute = attributes.item(i);
            String attrName = attribute.getNodeName();
            if (!resultMap.containsKey(attrName)) {
                resultMap.put(attrName, 0);
            }
            resultMap.put(attrName, resultMap.get(attrName) + 1);
        }
    }

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {

        // xml 파일 빌드업
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        // xml 파일을 document로 파싱하기
        Document document = builder.parse("xml/form.xml");

        // root 요소 가져오기
        Element root = document.getDocumentElement();

        Map<String, Integer> formResult = new LinkedHashMap<>();
        Map<String, Integer> paperResult = new LinkedHashMap<>();

        // #Text : 태그와 태그 사이의 공백을 하나의 노드로 인식

        NodeList formList = root.getChildNodes();
        for (int i = 0; i < formList.getLength(); i++) {
            Node form = formList.item(i);
            if (form.getNodeType() == Node.ELEMENT_NODE) { // 노드 타입이 Element일 경우(공백이 아닌 경우)
                NamedNodeMap formAttributes = form.getAttributes();
                setNodeAttribute(formResult, formAttributes);

                NodeList dssList = form.getChildNodes();
                for (int j = 0; j < dssList.getLength(); j++) {
                    Node dss = dssList.item(j);
                    if (dss.getNodeType() == Node.ELEMENT_NODE) {
                        NamedNodeMap dssAttributes = dss.getAttributes();
                        setNodeAttribute(paperResult, dssAttributes);
                    }
                }
            }
        }

        System.out.println(formResult);
        System.out.println(paperResult);

    }

}