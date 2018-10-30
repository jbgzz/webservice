package cn.elane.EUDAP.EUDAPImpl;


import cn.elane.EUDAP.EUDAPMservice;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.service.model.BindingInfo;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import javax.jws.WebService;
import javax.xml.namespace.QName;
import java.util.List;

/**
 * Created by elane on 2018/6/6.
 */
//name暴露的服务名称, targetNamespace:命名空间,设置为接口的包名倒写(默认是本类包名倒写). endpointInterface接口地址
@WebService(name = "EUDAPMservice" ,targetNamespace ="http://EUDAP.elane.cn/" ,portName="EUDAPMPort",serviceName="EUDAPMService",  endpointInterface = "cn.elane.EUDAP.EUDAPMservice")
public class EUDAPServiceImpl implements EUDAPMservice {

    //webservice接口地址
    private static String address = "http://192.168.4.224:9009/EUDAPM/web/EUDAP?wsdl";
    private static Logger logger = LogManager.getLogger(EUDAPServiceImpl.class);


    @Override
    public String resolveXml(String xml) {
        Document document = null;
        Object[] objects = new Object[0];
        JaxWsDynamicClientFactory factory = JaxWsDynamicClientFactory.newInstance();

        Client client = factory.createClient(address);
        Endpoint endpoint = client.getEndpoint();

        QName opName = new QName(endpoint.getService().getName().getNamespaceURI(), "ResolveXml");
        BindingInfo bindingInfo = endpoint.getEndpointInfo().getBinding();
        try {
            document = DocumentHelper.parseText(xml.replace("&gt;", ">").replace("&lt;", "<").replace("&quot;", "\"").replaceAll("<\\?xml[^>]*?>", "").replace("<![CDATA[", "").replace("]]>", ""));
            Element root = document.getRootElement();
            Element service = root.element("SERVICE");
            if (service != null) {
                String name=service.attribute("NAME").getValue();
                if("#ECMIS_SQYP_ZHDL".equals(name)) {
                    Element inparam = service.element("INPARAM");
                    if (inparam != null) {
                        String userid = inparam.element("USERID").getTextTrim();
                        String password = inparam.element("PASSWORD").getTextTrim();
                        if (bindingInfo.getOperation(opName) == null) {
                            for (BindingOperationInfo operationInfo : bindingInfo.getOperations()) {
                                if ("ResolveXml".equals(operationInfo.getName().getLocalPart())) {
                                    opName = operationInfo.getName();
                                    break;
                                }
                            }
                        }
                                StringBuffer sb = new StringBuffer();
                                sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
                                sb.append("<WS><SERVICE NAME=\"#ECMIS_SQYP_ZHDL\" XLH=\"K2zMnha3X2IbU9j+puXZI4SmZwNw==\">");
                                sb.append("<INPARAM>");
                                sb.append("<USERID TYPE=\"STRING\">");
                                sb.append(userid);
                                sb.append("</USERID>");
                                sb.append("<PASSWORD TYPE=\"STRING\">");
                                sb.append(password);
                                sb.append("</PASSWORD>");
                                sb.append("</INPARAM></SERVICE></WS>");
                                objects = client.invoke(opName, sb.toString());

                        System.out.println(document.asXML()+"----------++   +++++++++------------");
                                System.out.println(objects[0].toString()+"-----------------------");

                    } else {
                        logger.error("未获取到数据", EUDAPServiceImpl.class);
                    }
            }
            }else{
                logger.error("xml结构不完整", EUDAPServiceImpl.class);
            }
        } catch (DocumentException e) {
            logger.error("xml解析出错", EUDAPServiceImpl.class);
            e.printStackTrace();

        } catch (Exception e) {
            logger.error("请求警宗响应xml解析出错", EUDAPServiceImpl.class);
            e.printStackTrace();
        }


        return objects[0].toString();
    }

    @Override
    public String resolveXMLTYPE(String xml) {


        Document document = null;
        Object[] objects = new Object[0];
        JaxWsDynamicClientFactory factory = JaxWsDynamicClientFactory.newInstance();
        Client client = factory.createClient(address);
        Endpoint endpoint = client.getEndpoint();

        QName opName = new QName(endpoint.getService().getName().getNamespaceURI(), "ResolveXMLTYPE");
        BindingInfo bindingInfo = endpoint.getEndpointInfo().getBinding();
        try {
            document = DocumentHelper.parseText(xml.replace("&gt;", ">").replace("&lt;", "<").replace("&quot;", "\"").replaceAll("<\\?xml[^>]*?>", "").replace("<![CDATA[", "").replace("]]>", ""));
            Element root = document.getRootElement();
            Element service = root.element("SERVICE");
            if (service != null) {
                String name=service.attribute("NAME").getValue();
                if("#XJ_JZPT_HXRYXX".equals(name)) {
                    Element inparam = service.element("INPARAM");
                    if (inparam != null) {
                        String UNITCODE = null;

                        Element node1 = (Element)inparam.selectSingleNode("//UNITCODE");

                        if(node1 != null){
                            UNITCODE = inparam.element("UNITCODE").getText();
                        }

                        if (bindingInfo.getOperation(opName) == null) {
                            for (BindingOperationInfo operationInfo : bindingInfo.getOperations()) {
                                if ("ResolveXMLTYPE".equals(operationInfo.getName().getLocalPart())) {
                                    opName = operationInfo.getName();
                                    break;
                                }
                            }
                        }
                        Element list = inparam.element("DATA_LIST");
                        List<Element> datas = list.elements("DATA");
                        java.util.UUID uuid = java.util.UUID.randomUUID();
                        String guid = uuid.toString().toUpperCase();
                        StringBuffer sb = new StringBuffer();
                        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><WS><SERVICE NAME=\"#XJ_JZPT_HXRYXX\" XLH=\"K2zMnha3X2IbU9j+puXZI4SmZwNw==\">");
                        sb.append("<INPARAM> <DATATYPE>JZPTRYXX_INS</DATATYPE> ");
                        sb.append("<BATCHNO>");
                        sb.append(guid.replace("-", ""));
                        sb.append("</BATCHNO>");
                        sb.append("<UNITCODE>");
                        sb.append(UNITCODE);
                        sb.append("</UNITCODE> ");
                        sb.append("<DATA_LIST><DATA>");
                        for (Element e : datas) {
                            sb.append("<SJLYDWBM></SJLYDWBM>");
                            sb.append("<SOU_KEY></SOU_KEY>");
                            sb.append("<LSYDWBM>");
                            sb.append(UNITCODE);
                            sb.append("</LSYDWBM>");
                            sb.append("<RYBH_JZPT>");
                            sb.append(e.element("RYBH_JZPT").getText());
                            sb.append("</RYBH_JZPT>");
                            sb.append("<XM>");
                            sb.append(e.element("XM").getText());
                            sb.append("</XM>");
                            sb.append("<XBBM>");
                            sb.append(e.element("XBBM").getText());
                            sb.append("</XBBM>");
                            sb.append("<SFZJLXBM>");
                            sb.append(e.element("SFZJLXBM").getText());
                            sb.append("</SFZJLXBM>");
                            sb.append("<SFZJHM>");
                            sb.append(e.element("SFZJHM").getText());
                            sb.append("</SFZJHM>");
                            sb.append("<CHSRQ>");
                            sb.append(e.element("CHSRQ").getText());
                            sb.append("</CHSRQ>");
                            sb.append("<GJDQBM>");
                            sb.append(e.element("GJDQBM").getText());
                            sb.append("</GJDQBM>");
                            sb.append("<JGBM>");
                            sb.append(e.element("JGBM").getText());
                            sb.append("</JGBM>");
                            sb.append("<CYLXBM>");
                            sb.append(e.element("CYLXBM").getText());
                            sb.append("</CYLXBM>");
                            sb.append("<XJZDQBM>");
                            sb.append(e.element("XJZDQBM").getText());
                            sb.append("</XJZDQBM>");
                            sb.append("<XJZDXZ>");
                            sb.append(e.element("XJZDXZ").getText());
                            sb.append("</XJZDXZ>");
                            sb.append("<HKSZDBM>");
                            sb.append(e.element("HKSZDBM").getText());
                            sb.append("</HKSZDBM>");
                            sb.append("<HKSZDXZ>");
                            sb.append(e.element("HKSZDXZ").getText());
                            sb.append("</HKSZDXZ>");
                            sb.append("<SFBM>");
                            sb.append(e.element("SFBM").getText());
                            sb.append("</SFBM>");
                            sb.append("<WHCDBM>");
                            sb.append(e.element("WHCDBM").getText());
                            sb.append("</WHCDBM>");
                            sb.append("<MZBM>");
                            sb.append(e.element("MZBM").getText());
                            sb.append("</MZBM>");
                            sb.append("<ZZMMBM>");
                            sb.append(e.element("ZZMMBM").getTextTrim());
                            sb.append("</ZZMMBM>");
                            sb.append("<ZYBM>");
                            sb.append(e.element("ZYBM").getText());
                            sb.append("</ZYBM>");
                            sb.append("<ZCBM>");
                            sb.append(e.element("ZCBM").getText());
                            sb.append("</ZCBM>");
                            sb.append("<SG>");
                            sb.append(0.0);
                            sb.append("</SG>");
                            sb.append("<ZC>");
                            sb.append(0.0 );
                            sb.append("</ZC>");
                            sb.append("<SFWSZR>");
                            sb.append(e.element("SFWSZR").getText());
                            sb.append("</SFWSZR>");
                            sb.append("<TSSFBM>");
                            sb.append(e.element("TSSFBM").getText());
                            sb.append("</TSSFBM>");
                            sb.append("<SALXBM>");
                            sb.append(e.element("SALXBM").getText());
                            sb.append("</SALXBM>");
                            sb.append("<SSJDBM>");
                            sb.append(e.element("SSJDBM").getText());
                            sb.append("</SSJDBM>");
                            sb.append("<SSJDJZRQ>");
                            sb.append(e.element("SSJDJZRQ").getText());
                            sb.append("</SSJDJZRQ>");
                            sb.append("<SSJDQSRQ>");
                            sb.append(e.element("SSJDQSRQ").getText());
                            sb.append("</SSJDQSRQ>");
                            sb.append("<SSJYQX>");
                            sb.append(e.element("SSJYQX").getText());
                            sb.append("</SSJYQX>");
                            sb.append("<WFFZJLBM>");
                            sb.append(e.element("WFFZJLBM").getText());
                            sb.append("</WFFZJLBM>");
                            sb.append("<PBDWMC>");
                            sb.append(e.element("PBDWMC").getText());
                            sb.append("</PBDWMC>");
                            sb.append("<ZXDBRQ>");
                            sb.append(e.element("ZXDBRQ").getText());
                            sb.append("</ZXDBRQ>");
                            sb.append("<RSRRBM>");
                            sb.append(e.element("RSRRBM").getText());
                            sb.append("</RSRRBM>");
                            sb.append("<AJBH>");
                            sb.append(e.element("AJBH").getText());
                            sb.append("</AJBH>");
                            sb.append("<SXZM>");
                            sb.append(e.element("SXZM").getText());
                            sb.append("</SXZM>");
                            sb.append("<SYDWLXBM>");
                            sb.append(e.element("SYDWLXBM").getText());
                            sb.append("</SYDWLXBM>");
                            sb.append("<SYDWMC>");
                            sb.append(e.element("SYDWMC").getText());
                            sb.append("</SYDWMC>");
                            sb.append("<SYPZLBBM>");
                            sb.append(e.element("SYPZLBBM").getText());
                            sb.append("</SYPZLBBM>");
                            sb.append("<SYRXM></SYRXM>");
                            // sb.append(t.gettKDataRsxx().getrSyr());
                            //sb.append("");
                            sb.append("<SYRLXDH></SYRLXDH>");
                            // sb.append(t);
                            //sb.append("");
                            sb.append("<SYPZZH>");
                            sb.append(e.element("SYPZZH").getText());
                            sb.append("</SYPZZH>");
                            sb.append("<BADWLXBM>");
                            sb.append(e.element("BADWLXBM").getText());
                            sb.append("</BADWLXBM>");
                            sb.append("<BADWMC>");
                            sb.append(e.element("BADWMC").getText());
                            sb.append("</BADWMC>");
                            sb.append("<BARLXDH>");
                            sb.append(e.element("BARLXDH").getText());
                            sb.append("</BARLXDH>");
                            sb.append("<BARXM>");
                            sb.append(e.element("BARXM").getText());
                            sb.append("</BARXM>");
                            sb.append("<TSANLXBM>");
                            sb.append(e.element("TSANLXBM").getText());
                            sb.append("</TSANLXBM>");
                            sb.append("<JYAQ>");
                            sb.append(e.element("JYAQ").getText());
                            sb.append("</JYAQ>");
                            sb.append("<ZRDW></ZRDW>");
                            // sb.append(t);
                            //sb.append("");
                            sb.append("<RYBH>");
                            sb.append(e.element("RYBH").getText());
                            sb.append("</RYBH>");
                            sb.append("<RYZT>");
                            sb.append(-1);
                            sb.append("</RYZT>");
                        }
                        sb.append("</DATA> ");
                        sb.append("</DATA_LIST> ");
                        sb.append("</INPARAM>");
                        sb.append("</SERVICE>");
                        sb.append("</WS>");
                        objects = client.invoke(opName, sb.toString());
                        System.out.println(objects.toString());



                    } else {
                        logger.error("未获取回写到数据");
                }
                }else{

                }
            }else{
                logger.error("xml结构不完整");
            }
        } catch (DocumentException e) {
            logger.error("xml解析出错");
            e.printStackTrace();

        } catch (Exception e) {
            logger.error("请求统一访问平台响应xml解析出错");
            e.printStackTrace();
        }

        return null;
    }
}
