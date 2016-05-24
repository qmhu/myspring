package my.bean;

import my.annotation.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by I311862 on 2016/5/8.
 */
public class BeanDefinationXMLLoader {

    private List<BeanDefinition> beanDefinitions = new ArrayList<>();

    public List<BeanDefinition> getBeanDefinitions(){
        return beanDefinitions;
    }

    public void loadBeanDefinations(String configFile){
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();

        Document document = null;
        try {
            //DOM parser instance
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            //parse an XML file into a DOM tree
            document = builder.parse(new File(configFile));
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Element rootElement = document.getDocumentElement();

        NodeList scanList = rootElement.getElementsByTagName("component-scan");
        if (scanList != null){
            for(int i = 0; i < scanList.getLength(); i++){
                Element scanNode = (Element)scanList.item(i);
                String packageScanRoot = scanNode.getAttribute("base-package");
                scanBeanDefination(packageScanRoot,this.beanDefinitions);
            }
        }

        NodeList nodeList = rootElement.getElementsByTagName("bean");
        if(nodeList != null) {
            for (int i = 0 ; i < nodeList.getLength(); i++) {
                Element element = (Element)nodeList.item(i);
                BeanDefinition beanDefination = new BeanDefinition();
                String id = element.getAttribute("id");
                String className = element.getAttribute("class");
                beanDefination.setBeanClass(className);
                beanDefination.setId(id);
                beanDefination.setBeanProperties(new ArrayList<BeanProperty>());

                NodeList nodes = element.getChildNodes();
                for (int j=0; j < nodes.getLength(); j++) {
                    Node node = nodes.item(j);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element child = (Element) node;
                        BeanProperty beanProperty = new BeanProperty();
                        beanProperty.setName(child.getAttribute("id"));
                        beanProperty.setValue(child.getAttribute("value") == "" ? null : child.getAttribute("value"));
                        beanProperty.setRefBean(child.getAttribute("ref") == "" ? null : child.getAttribute("ref"));
                        beanDefination.getBeanProperties().add(beanProperty);
                    }

                }

                this.beanDefinitions.add(beanDefination);
            }
        }

    }

    private void scanBeanDefination(String packagePath,List<BeanDefinition> beanDefinations){
        packagePath = packagePath.replace('.', '/');
        try {
            Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources(packagePath);

            while (urls.hasMoreElements()){
                URL url = urls.nextElement();
                String protocal = url.getProtocol();
                if (protocal.equals("file")){
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    addPackageWithPath(packagePath, filePath, beanDefinations);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void addPackageWithPath(String packageName, String path, List<BeanDefinition> beanDefinations){
        File pathFile = new File(path);
        if (!pathFile.exists() || !pathFile.isDirectory()){
            return;
        }

        File[] subFiles = pathFile.listFiles();
        for(File subFile : subFiles){
            if (subFile.isDirectory()){
                addPackageWithPath(packageName + "." + subFile.getName(), subFile.getPath(), beanDefinations);
            }

            if (subFile.getName().endsWith(".class")){
                String classFileName = subFile.getName().substring(0, subFile.getName().indexOf(".class"));
                try {
                    Class clazz = Thread.currentThread().getContextClassLoader().loadClass(packageName + "." + classFileName);
                    if (clazz.isAnnotationPresent(Service.class)) {
                        BeanDefinition beanDefination = new BeanDefinition();
                        beanDefination.setId(this.toLowerForFirstChar(classFileName));
                        beanDefination.setBeanClass(packageName + "." +classFileName);
                        beanDefination.setAnnotation(true);
                        beanDefination.setBeanProperties(new ArrayList<BeanProperty>());

                        beanDefinations.add(beanDefination);
                    }

                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String toLowerForFirstChar(String str){
        char[] chars = str.toCharArray();
        if (chars.length > 0){
            chars[0] = Character.toLowerCase(chars[0]);
        }

        return String.valueOf(chars);
    }

}
