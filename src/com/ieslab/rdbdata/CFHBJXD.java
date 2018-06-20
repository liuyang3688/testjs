/**
 * CFHBJXD.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ieslab.rdbdata;

public class CFHBJXD  implements java.io.Serializable {
    private int byJXDType;

    private java.lang.String strInJXD;

    private java.lang.String strOutJXD;

    public CFHBJXD() {
    }

    public CFHBJXD(
           int byJXDType,
           java.lang.String strInJXD,
           java.lang.String strOutJXD) {
           this.byJXDType = byJXDType;
           this.strInJXD = strInJXD;
           this.strOutJXD = strOutJXD;
    }


    /**
     * Gets the byJXDType value for this CFHBJXD.
     * 
     * @return byJXDType
     */
    public int getByJXDType() {
        return byJXDType;
    }


    /**
     * Sets the byJXDType value for this CFHBJXD.
     * 
     * @param byJXDType
     */
    public void setByJXDType(int byJXDType) {
        this.byJXDType = byJXDType;
    }


    /**
     * Gets the strInJXD value for this CFHBJXD.
     * 
     * @return strInJXD
     */
    public java.lang.String getStrInJXD() {
        return strInJXD;
    }


    /**
     * Sets the strInJXD value for this CFHBJXD.
     * 
     * @param strInJXD
     */
    public void setStrInJXD(java.lang.String strInJXD) {
        this.strInJXD = strInJXD;
    }


    /**
     * Gets the strOutJXD value for this CFHBJXD.
     * 
     * @return strOutJXD
     */
    public java.lang.String getStrOutJXD() {
        return strOutJXD;
    }


    /**
     * Sets the strOutJXD value for this CFHBJXD.
     * 
     * @param strOutJXD
     */
    public void setStrOutJXD(java.lang.String strOutJXD) {
        this.strOutJXD = strOutJXD;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CFHBJXD)) return false;
        CFHBJXD other = (CFHBJXD) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.byJXDType == other.getByJXDType() &&
            ((this.strInJXD==null && other.getStrInJXD()==null) || 
             (this.strInJXD!=null &&
              this.strInJXD.equals(other.getStrInJXD()))) &&
            ((this.strOutJXD==null && other.getStrOutJXD()==null) || 
             (this.strOutJXD!=null &&
              this.strOutJXD.equals(other.getStrOutJXD())));
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
        if (getStrInJXD() != null) {
            _hashCode += getStrInJXD().hashCode();
        }
        if (getStrOutJXD() != null) {
            _hashCode += getStrOutJXD().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CFHBJXD.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://localhost/rdbwebservice.wsdl", "CFHBJXD"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("byJXDType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "byJXDType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("strInJXD");
        elemField.setXmlName(new javax.xml.namespace.QName("", "strInJXD"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("strOutJXD");
        elemField.setXmlName(new javax.xml.namespace.QName("", "strOutJXD"));
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
