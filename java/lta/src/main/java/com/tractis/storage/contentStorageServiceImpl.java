
package com.tractis.storage;

import javax.jws.WebService;

@WebService(serviceName = "contentStorageService", targetNamespace = "http://storage.tractis.com/", endpointInterface = "com.tractis.storage.SOAPport")
public class contentStorageServiceImpl
    implements SOAPport
{


    public ContentRemoveResponse remove(com.tractis.storage.ContentRemoveRequest ContentRemoveRequest) {
        throw new UnsupportedOperationException();
    }

    public ContentStoreResponse store(com.tractis.storage.ContentStoreRequest ContentStoreRequest) {
        throw new UnsupportedOperationException();
    }

    public ContentListResponse list(com.tractis.storage.ContentListRequest ContentListRequest) {
        throw new UnsupportedOperationException();
    }

    public ContentRecoverResponse recover(com.tractis.storage.ContentRecoverRequest ContentRecoverRequest) {
        throw new UnsupportedOperationException();
    }

}
