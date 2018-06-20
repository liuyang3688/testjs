/**
 * FaultResp.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ieslab.rdbdata;

public class FaultResp  implements java.io.Serializable {
    private int nID;

    private int faultBuJian;

    private java.lang.String pEvtData;

    public FaultResp() {
    }

    public FaultResp(
           int nID,
           int faultBuJian,
           java.lang.String pEvtData) {
           this.nID = nID;
           this.faultBuJian = faultBuJian;
           this.pEvtData = pEvtData;
    }


    /**
     * Gets the nID value for this FaultResp.
     * 
     * @return nID
     */
    public int getNID() {
        return nID;
    }


    /**
     * Sets the nID value for this FaultResp.
     * 
     * @param nID
     */
    public void setNID(int nID) {
        this.nID = nID;
    }


    /**
     * Gets the faultBuJian value for this FaultResp.
     * 
     * @return faultBuJian
     */
    public int getFaultBuJian() {
        return faultBuJian;
    }


    /**
     * Sets the faultBuJian value for this FaultResp.
     * 
     * @param faultBuJian
     */
    public void setFaultBuJian(int faultBuJian) {
        this.faultBuJian = faultBuJian;
    }


    /**
     * Gets the pEvtData value for this FaultResp.
     * 
     * @return pEvtData
     */
    public java.lang.String getPEvtData() {
        return pEvtData;
    }


    /**
     * Sets the pEvtData value for this FaultResp.
     * 
     * @param pEvtData
     */
    public void setPEvtData(java.lang.String pEvtData) {
        this.pEvtData = pEvtData;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FaultResp)) return false;
        FaultResp other = (FaultResp) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.nID == other.getNID() &&
            this.faultBuJian == other.getFaultBuJian() &&
            ((this.pEvtData==null && other.getPEvtData()==null) || 
             (this.pEvtData!=null &&
              this.pEvtData.equals(other.getPEvtData())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        _hashCode += getNID();
        _hashCode += getFaultBuJian();
        if (getPEvtData() != null) {
            _hashCode += getPEvtData().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(FaultResp.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://localhost/rdbwebservice.wsdl", "FaultResp"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("NID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("faultBuJian");
        elemField.setXmlName(new javax.xml.namespace.QName("", "faultBuJian"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("PEvtData");
        elemField.setXmlName(new javax.xml.namespace.QName("", "pEvtData"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
