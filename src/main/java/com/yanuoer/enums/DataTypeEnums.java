package com.yanuoer.enums;

/**
 * mybatis日志数据类型解析枚举
 *
 * @author tanzhuo
 * @version 1.0.3
 */
public enum DataTypeEnums {

	Byte("(Byte)", java.lang.Byte.class),
	Short("(Short)", java.lang.Short.class),
	Integer("(Integer)", java.lang.Integer.class),
	Float("(Float)", java.lang.Float.class),
	Double("(Double)", java.lang.Double.class),
	Long("(Long)", java.lang.Long.class),
	Boolean("(Boolean)", java.lang.Boolean.class),
	BigInteger("(BigInteger)", java.math.BigInteger.class),
	BigDecimal("(BigDecimal)", java.math.BigDecimal.class),
	String("(String)", String.class),
	Date("(Date)", java.util.Date.class),
	Timestamp("(Timestamp)", java.sql.Timestamp.class),
	Time("(Time)", java.sql.Time.class);

	private String dataType;

	private Class dataObjClass;

	public static Object getDataType(String data) {
		DataTypeEnums[] typeEnums = DataTypeEnums.values();
		for (DataTypeEnums typeEnum : typeEnums) {
			if (data.contains(typeEnum.getDataType())) {
				data = data.replace(typeEnum.getDataType(), "");
				if (DataTypeEnums.Byte.getDataType().equals(typeEnum.getDataType())) {
					return java.lang.Byte.valueOf(data);
				}
				if (DataTypeEnums.Short.getDataType().equals(typeEnum.getDataType())) {
					return java.lang.Short.valueOf(data);
				}
				if (DataTypeEnums.Integer.getDataType().equals(typeEnum.getDataType())) {
					return java.lang.Integer.valueOf(data);
				}
				if (DataTypeEnums.Float.getDataType().equals(typeEnum.getDataType())) {
					return java.lang.Float.valueOf(data);
				}
				if (DataTypeEnums.Double.getDataType().equals(typeEnum.getDataType())) {
					return java.lang.Double.valueOf(data);
				}
				if (DataTypeEnums.Long.getDataType().equals(typeEnum.getDataType())) {
					return java.lang.Long.valueOf(data);
				}
				if (DataTypeEnums.Boolean.getDataType().equals(typeEnum.getDataType())) {
					return java.lang.Boolean.valueOf(data);
				}
				if (DataTypeEnums.BigInteger.getDataType().equals(typeEnum.getDataType())) {
					return new java.math.BigInteger(data);
				}
				if (DataTypeEnums.BigDecimal.getDataType().equals(typeEnum.getDataType())) {
					return new java.math.BigDecimal(data);
				}
				if (DataTypeEnums.String.getDataType().equals(typeEnum.getDataType())) {
					return data;
				}
				if (DataTypeEnums.Date.getDataType().equals(typeEnum.getDataType())) {
					return data;
				}
				if (DataTypeEnums.Timestamp.getDataType().equals(typeEnum.getDataType())) {
					return data;
				}
				if (DataTypeEnums.Time.getDataType().equals(typeEnum.getDataType())) {
					return data;
				}
			}
		}
		return null;
	}


	public Class getDataObjClass() {
		return dataObjClass;
	}

	public java.lang.String getDataType() {
		return dataType;
	}

	private DataTypeEnums(String dataType, Class dataObjClass) {
		this.dataType = dataType;
		this.dataObjClass = dataObjClass;
	}

}
