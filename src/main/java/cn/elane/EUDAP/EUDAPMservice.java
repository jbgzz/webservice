package cn.elane.EUDAP;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * Created by elane on 2018/6/6.
 */
@WebService
public interface EUDAPMservice {
    @WebMethod(operationName = "ResolveXml")
    public String resolveXml(
            @WebParam(name = "xml")
                    String xml
    );


    @WebMethod(operationName = "ResolveXMLTYPE")
    public String resolveXMLTYPE(
            @WebParam(name = "xml")
                    String xml
    );
}
