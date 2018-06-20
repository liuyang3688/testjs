/**
 * Tpwebservice_Service.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ieslab.topo;

public interface Tpwebservice_Service extends javax.xml.rpc.Service {

/**
 * gSOAP 2.8.39 generated service definition
 */
    public java.lang.String gettpwebserviceAddress();

    public com.ieslab.topo.TpwebservicePortType gettpwebservice() throws javax.xml.rpc.ServiceException;

    public com.ieslab.topo.TpwebservicePortType gettpwebservice(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
