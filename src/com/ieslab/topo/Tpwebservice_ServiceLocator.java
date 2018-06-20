/**
 * Tpwebservice_ServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ieslab.topo;

import com.ieslab.util.QueryConfig;

public class Tpwebservice_ServiceLocator extends org.apache.axis.client.Service implements com.ieslab.topo.Tpwebservice_Service {

/**
 * gSOAP 2.8.39 generated service definition
 */

    public Tpwebservice_ServiceLocator() {
    }


    public Tpwebservice_ServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public Tpwebservice_ServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for tpwebservice
    private java.lang.String tpwebservice_address = QueryConfig.getInstance().getTopowebservice();

    public java.lang.String gettpwebserviceAddress() {
        return tpwebservice_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String tpwebserviceWSDDServiceName = "tpwebservice";

    public java.lang.String gettpwebserviceWSDDServiceName() {
        return tpwebserviceWSDDServiceName;
    }

    public void settpwebserviceWSDDServiceName(java.lang.String name) {
        tpwebserviceWSDDServiceName = name;
    }

    public com.ieslab.topo.TpwebservicePortType gettpwebservice() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(tpwebservice_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return gettpwebservice(endpoint);
    }

    public com.ieslab.topo.TpwebservicePortType gettpwebservice(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.ieslab.topo.Tpwebservice_BindingStub _stub = new com.ieslab.topo.Tpwebservice_BindingStub(portAddress, this);
            _stub.setPortName(gettpwebserviceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void settpwebserviceEndpointAddress(java.lang.String address) {
        tpwebservice_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.ieslab.topo.TpwebservicePortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.ieslab.topo.Tpwebservice_BindingStub _stub = new com.ieslab.topo.Tpwebservice_BindingStub(new java.net.URL(tpwebservice_address), this);
                _stub.setPortName(gettpwebserviceWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("tpwebservice".equals(inputPortName)) {
            return gettpwebservice();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://localhost/tpwebservice.wsdl", "tpwebservice");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://localhost/tpwebservice.wsdl", "tpwebservice"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("tpwebservice".equals(portName)) {
            settpwebserviceEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
