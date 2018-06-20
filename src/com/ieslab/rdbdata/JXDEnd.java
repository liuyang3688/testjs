/**
 * JXDEnd.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ieslab.rdbdata;

public class JXDEnd  implements java.io.Serializable {
    private int byJXDType;

    private java.lang.String strJXDID;

    public JXDEnd() {
    }

    public JXDEnd(
           int byJXDType,
           java.lang.String strJXDID) {
           this.byJXDType = byJXDType;
           this.strJXDID = strJXDID;
    }


    /**
     * Gets the byJXDType value for this JXDEnd.
     * 
     * @return byJXDType
     */
    public int getByJXDType() {
        return byJXDType;
    }


    /**
     * Sets the byJXDType value for this JXDEnd.
     * 
     * @param byJXDType
     */
    public void setByJXDType(int byJXDType) {
        this.byJXDType = byJXDType;
    }


    /**
     * Gets the strJXDID value for this JXDEnd.
     * 
     * @return strJXDID
     */
    public java.lang.String getStrJXDID() {
        return strJXDID;
    }


    /**
     * Sets the strJXDID value for this JXDEnd.
     * 
     * @param strJXDID
     */
    public void setStrJXDID(java.lang.String strJXDID) {
        this.strJXDID = strJXDID;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof JXDEnd)) return false;
        JXDEnd other = (JXDEnd) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.byJXDType == other.getByJXDType() &&
            ((this.strJXDID==null && other.getStrJXDID()==null) || 
             (this.strJXDID!=null &&
              this.strJXDID.equals(other.getStrJXDID())));
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
        _hashCode += getByJXDType();
        if (getStrJXDID() != null) {
            _hashCode += getStrJXDID().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(JXDEnd.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://localhost/rdbwebservice.wsdl", "JXDEnd"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("byJXDType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "byJXDType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("strJXDID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "strJXDID"));
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
