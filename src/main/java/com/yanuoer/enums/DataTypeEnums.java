package com.yanuoer.enums;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * mybatis日志数据类型解析枚举
 *
 * @author tanzhuo
 * @version 1.0.4
 */
public enum DataTypeEnums {

    /**
     * 根据不同的参数类型标识 返回不同的对象
     */
    BYTE("(Byte)", Byte.class),
    SHORT("(Short)", Short.class),
    INTEGER("(Integer)", Integer.class),
    FLOAT("(Float)", Float.class),
    DOUBLE("(Double)", Double.class),
    LONG("(Long)", Long.class),
    BOOLEAN("(Boolean)", Boolean.class),
    BIG_INTEGER("(BigInteger)", BigInteger.class),
    BIG_DECIMAL("(BigDecimal)", BigDecimal.class),
    STRING("(String)", String.class),
    DATE("(Date)", Date.class),
    TIME("(Time)", Time.class),
    TIMESTAMP("(Timestamp)", Timestamp.class),
    LOCALE_DATA("(LocalDate)", LocalDate.class),
    LOCALE_DATA_TIME("(LocalDateTime)", LocalDateTime.class);

    private final String dataType;

    private final Class<?> dataObjClass;

    public static Object getDataType(String data) throws Exception {
        for (DataTypeEnums enums : DataTypeEnums.values()) {
            if (!enums.getDataType().contains(data)) {
                continue;
            }
            // 去掉参数标注内容，准备参数转换
            data = data.replace(enums.getDataType(), "");
            return enums.getDataObjClass().getDeclaredConstructor(String.class).newInstance(data);
        }
        return null;
    }


    public Class<?> getDataObjClass() {
        return dataObjClass;
    }

    public java.lang.String getDataType() {
        return dataType;
    }

    DataTypeEnums(String dataType, Class<?> dataObjClass) {
        this.dataType = dataType;
        this.dataObjClass = dataObjClass;
    }

}
