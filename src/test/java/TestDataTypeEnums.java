import com.yanuoer.enums.DataTypeEnums;
import org.junit.Test;

/**
 * 单元测试类
 *
 * @author tanzhuo
 * @version 2.5.0
 * @since 2022-12-23 14:47
 */
public class TestDataTypeEnums {

    @Test
    public void testType() {
        try {
            Object obj = DataTypeEnums.getDataType("3(BigDecimal)");
            assert obj != null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
