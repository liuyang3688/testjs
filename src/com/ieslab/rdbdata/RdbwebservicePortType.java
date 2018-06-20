/**
 * RdbwebservicePortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ieslab.rdbdata;

public interface RdbwebservicePortType extends java.rmi.Remote {

    /**
     * Service definition of function ns__WebServiceTest
     */
    public java.lang.String webServiceTest(java.lang.String strIn) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__GetRealData
     */
    public java.lang.String getRealData(java.lang.String strIn) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__GetFJDZResult
     */
    public java.lang.String getFJDZResult(com.ieslab.rdbdata.DZQueryParam queryParam) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__GetFDNLDZResult
     */
    public java.lang.String getFDNLDZResult(com.ieslab.rdbdata.DZQueryParam queryParam) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__GetDQSBDZResult
     */
    public java.lang.String getDQSBDZResult(com.ieslab.rdbdata.DZQueryParam queryParam) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__GetTXWLDZResult
     */
    public java.lang.String getTXWLDZResult(com.ieslab.rdbdata.DZQueryParam queryParam) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__SetProcJXD
     */
    public void setProcJXD(com.ieslab.rdbdata.ProcJXD jxdinfo) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__SplitJXD
     */
    public void splitJXD(com.ieslab.rdbdata.CFHBJXD jxdinfo) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__MergeJXD
     */
    public void mergeJXD(com.ieslab.rdbdata.CFHBJXD jxdinfo) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__GetFJTipShowInfo
     */
    public java.lang.String getFJTipShowInfo(int nFJID) throws java.rmi.RemoteException;

    /**
     * Service definition of function ns__GetShouLeiFJList
     */
    public java.lang.String getShouLeiFJList(java.lang.String strIn) throws java.rmi.RemoteException;
}
