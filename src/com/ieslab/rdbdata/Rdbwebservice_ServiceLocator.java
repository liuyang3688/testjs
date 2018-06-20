/**
 * Rdbwebservice_ServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ieslab.rdbdata;

import com.ieslab.util.QueryConfig;

public class Rdbwebservice_ServiceLocator extends org.apache.axis.client.Service implements com.ieslab.rdbdata.Rdbwebservice_Service {

/**
 * gSOAP 2.8.39 generated service definition
 */

    public Rdbwebservice_ServiceLocator() {
    }


    public Rdbwebservice_ServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public Rdbwebservice_ServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for rdbwebservice
    private java.lang.String rdbwebservice_address = QueryConfig.getInstance().getRtdatawebservice1();

    public java.lang.String getRdbwebservice_address() {
		return rdbwebservice_address;
	}


	public void setRdbwebservice_address(java.lang.String rdbwebservice_address) {
		this.rdbwebservice_address = rdbwebservice_address;
	}


	public java.lang.String getrdbwebserviceAddress() {
        return rdbwebservice_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String rdbwebserviceWSDDServiceName = "rdbwebservice";

    public java.lang.String getrdbwebserviceWSDDServiceName() {
        return rdbwebserviceWSDDServiceName;
    }

    public void setrdbwebserviceWSDDServiceName(java.lang.String name) {
        rdbwebserviceWSDDServiceName = name;
    }

    public com.ieslab.rdbdata.RdbwebservicePortType getrdbwebservice() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(rdbwebservice_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getrdbwebservice(endpoint);
    }

    public com.ieslab.rdbdata.RdbwebservicePortType getrdbwebservice(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.ieslab.rdbdata.Rdbwebservice_BindingStub _stub = new com.ieslab.rdbdata.Rdbwebservice_BindingStub(portAddress, this);
            _stub.setPortName(getrdbwebserviceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setrdbwebserviceEndpointAddress(java.lang.String address) {
        rdbwebservice_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.ieslab.rdbdata.RdbwebservicePortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.ieslab.rdbdata.Rdbwebservice_BindingStub _stub = new com.ieslab.rdbdata.Rdbwebservice_BindingStub(new java.net.URL(rdbwebservice_address), this);
                _stub.setPortName(getrdbwebserviceWSDDServiceName());
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
        if ("rdbwebservice".equals(inputPortName)) {
            return getrdbwebservice();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://localhost/rdbwebservice.wsdl", "rdbwebservice");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://localhost/rdbwebservice.wsdl", "rdbwebservice"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("rdbwebservice".equals(portName)) {
            setrdbwebserviceEndpointAddress(address);
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
