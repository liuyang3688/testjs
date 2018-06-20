/**
 * DZQueryParam.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ieslab.rdbdata;

public class DZQueryParam  implements java.io.Serializable {
    private java.lang.String strQYGongSiID;

    private java.lang.String strChangZhanID;

    private java.lang.String strXingHaoID;

    private com.ieslab.rdbdata.SelBJInfo selBJInfo;

    private int filter;

    private float filterVal1;

    private float filterVal2;

    public DZQueryParam() {
    }

    public DZQueryParam(
           java.lang.String strQYGongSiID,
           java.lang.String strChangZhanID,
           java.lang.String strXingHaoID,
           com.ieslab.rdbdata.SelBJInfo selBJInfo,
           int filter,
           float filterVal1,
           float filterVal2) {
           this.strQYGongSiID = strQYGongSiID;
           this.strChangZhanID = strChangZhanID;
           this.strXingHaoID = strXingHaoID;
           this.selBJInfo = selBJInfo;
           this.filter = filter;
           this.filterVal1 = filterVal1;
           this.filterVal2 = filterVal2;
    }


    /**
     * Gets the strQYGongSiID value for this DZQueryParam.
     * 
     * @return strQYGongSiID
     */
    public java.lang.String getStrQYGongSiID() {
        return strQYGongSiID;
    }


    /**
     * Sets the strQYGongSiID value for this DZQueryParam.
     * 
     * @param strQYGongSiID
     */
    public void setStrQYGongSiID(java.lang.String strQYGongSiID) {
        this.strQYGongSiID = strQYGongSiID;
    }


    /**
     * Gets the strChangZhanID value for this DZQueryParam.
     * 
     * @return strChangZhanID
     */
    public java.lang.String getStrChangZhanID() {
        return strChangZhanID;
    }


    /**
     * Sets the strChangZhanID value for this DZQueryParam.
     * 
     * @param strChangZhanID
     */
    public void setStrChangZhanID(java.lang.String strChangZhanID) {
        this.strChangZhanID = strChangZhanID;
    }


    /**
     * Gets the strXingHaoID value for this DZQueryParam.
     * 
     * @return strXingHaoID
     */
    public java.lang.String getStrXingHaoID() {
        return strXingHaoID;
    }


    /**
     * Sets the strXingHaoID value for this DZQueryParam.
     * 
     * @param strXingHaoID
     */
    public void setStrXingHaoID(java.lang.String strXingHaoID) {
        this.strXingHaoID = strXingHaoID;
    }


    /**
     * Gets the selBJInfo value for this DZQueryParam.
     * 
     * @return selBJInfo
     */
    public com.ieslab.rdbdata.SelBJInfo getSelBJInfo() {
        return selBJInfo;
    }


    /**
     * Sets the selBJInfo value for this DZQueryParam.
     * 
     * @param selBJInfo
     */
    public void setSelBJInfo(com.ieslab.rdbdata.SelBJInfo selBJInfo) {
        this.selBJInfo = selBJInfo;
    }


    /**
     * Gets the filter value for this DZQueryParam.
     * 
     * @return filter
     */
    public int getFilter() {
        return filter;
    }


    /**
     * Sets the filter value for this DZQueryParam.
     * 
     * @param filter
     */
    public void setFilter(int filter) {
        this.filter = filter;
    }


    /**
     * Gets the filterVal1 value for this DZQueryParam.
     * 
     * @return filterVal1
     */
    public float getFilterVal1() {
        return filterVal1;
    }


    /**
     * Sets the filterVal1 value for this DZQueryParam.
     * 
     * @param filterVal1
     */
    public void setFilterVal1(float filterVal1) {
        this.filterVal1 = filterVal1;
    }


    /**
     * Gets the filterVal2 value for this DZQueryParam.
     * 
     * @return filterVal2
     */
    public float getFilterVal2() {
        return filterVal2;
    }


    /**
     * Sets the filterVal2 value for this DZQueryParam.
     * 
     * @param filterVal2
     */
    public void setFilterVal2(float filterVal2) {
        this.filterVal2 = filterVal2;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DZQueryParam)) return false;
        DZQueryParam other = (DZQueryParam) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.strQYGongSiID==null && other.getStrQYGongSiID()==null) || 
             (this.strQYGongSiID!=null &&
              this.strQYGongSiID.equals(other.getStrQYGongSiID()))) &&
            ((this.strChangZhanID==null && other.getStrChangZhanID()==null) || 
             (this.strChangZhanID!=null &&
              this.strChangZhanID.equals(other.getStrChangZhanID()))) &&
            ((this.strXingHaoID==null && other.getStrXingHaoID()==null) || 
             (this.strXingHaoID!=null &&
              this.strXingHaoID.equals(other.getStrXingHaoID()))) &&
            ((this.selBJInfo==null && other.getSelBJInfo()==null) || 
             (this.selBJInfo!=null &&
              this.selBJInfo.equals(other.getSelBJInfo()))) &&
            this.filter == other.getFilter() &&
            this.filterVal1 == other.getFilterVal1() &&
            this.filterVal2 == other.getFilterVal2();
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
        if (getStrQYGongSiID() != null) {
            _hashCode += getStrQYGongSiID().hashCode();
        }
        if (getStrChangZhanID() != null) {
            _hashCode += getStrChangZhanID().hashCode();
        }
        if (getStrXingHaoID() != null) {
            _hashCode += getStrXingHaoID().hashCode();
        }
        if (getSelBJInfo() != null) {
            _hashCode += getSelBJInfo().hashCode();
        }
        _hashCode += getFilter();
        _hashCode += new Float(getFilterVal1()).hashCode();
        _hashCode += new Float(getFilterVal2()).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DZQueryParam.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://localhost/rdbwebservice.wsdl", "DZQueryParam"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("strQYGongSiID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "strQYGongSiID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("strChangZhanID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "strChangZhanID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("strXingHaoID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "strXingHaoID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("selBJInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "SelBJInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://localhost/rdbwebservice.wsdl", "SelBJInfo"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("filter");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Filter"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("filterVal1");
        elemField.setXmlName(new javax.xml.namespace.QName("", "filterVal1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("filterVal2");
        elemField.setXmlName(new javax.xml.namespace.QName("", "filterVal2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
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
