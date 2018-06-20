/**
 * SelBJInfo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ieslab.rdbdata;

public class SelBJInfo  implements java.io.Serializable {
    private int byBJType;

    private java.lang.String strBJID;

    public SelBJInfo() {
    }

    public SelBJInfo(
           int byBJType,
           java.lang.String strBJID) {
           this.byBJType = byBJType;
           this.strBJID = strBJID;
    }


    /**
     * Gets the byBJType value for this SelBJInfo.
     * 
     * @return byBJType
     */
    public int getByBJType() {
        return byBJType;
    }


    /**
     * Sets the byBJType value for this SelBJInfo.
     * 
     * @param byBJType
     */
    public void setByBJType(int byBJType) {
        this.byBJType = byBJType;
    }


    /**
     * Gets the strBJID value for this SelBJInfo.
     * 
     * @return strBJID
     */
    public java.lang.String getStrBJID() {
        return strBJID;
    }


    /**
     * Sets the strBJID value for this SelBJInfo.
     * 
     * @param strBJID
     */
    public void setStrBJID(java.lang.String strBJID) {
        this.strBJID = strBJID;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SelBJInfo)) return false;
        SelBJInfo other = (SelBJInfo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.byBJType == other.getByBJType() &&
            ((this.strBJID==null && other.getStrBJID()==null) || 
             (this.strBJID!=null &&
              this.strBJID.equals(other.getStrBJID())));
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
        _hashCode += getByBJType();
        if (getStrBJID() != null) {
            _hashCode += getStrBJID().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SelBJInfo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://localhost/rdbwebservice.wsdl", "SelBJInfo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("byBJType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "byBJType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("strBJID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "strBJID"));
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
