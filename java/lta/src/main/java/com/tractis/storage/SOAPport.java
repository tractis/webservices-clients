
package com.tractis.storage;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService(name = "SOAPport", targetNamespace = "http://storage.tractis.com/")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface SOAPport {


    @WebMethod(operationName = "remove", action = "")
    @WebResult(name = "ContentRemoveResponse", targetNamespace = "http://storage.tractis.com/")
    public ContentRemoveResponse remove(
        @WebParam(name = "ContentRemoveRequest", targetNamespace = "http://storage.tractis.com/")
        com.tractis.storage.ContentRemoveRequest ContentRemoveRequest);

    @WebMethod(operationName = "store", action = "")
    @WebResult(name = "ContentStoreResponse", targetNamespace = "http://storage.tractis.com/")
    public ContentStoreResponse store(
        @WebParam(name = "ContentStoreRequest", targetNamespace = "http://storage.tractis.com/")
        com.tractis.storage.ContentStoreRequest ContentStoreRequest);

    @WebMethod(operationName = "list", action = "")
    @WebResult(name = "ContentListResponse", targetNamespace = "http://storage.tractis.com/")
    public ContentListResponse list(
        @WebParam(name = "ContentListRequest", targetNamespace = "http://storage.tractis.com/")
        com.tractis.storage.ContentListRequest ContentListRequest);

    @WebMethod(operationName = "recover", action = "")
    @WebResult(name = "ContentRecoverResponse", targetNamespace = "http://storage.tractis.com/")
    public ContentRecoverResponse recover(
        @WebParam(name = "ContentRecoverRequest", targetNamespace = "http://storage.tractis.com/")
        com.tractis.storage.ContentRecoverRequest ContentRecoverRequest);

}
