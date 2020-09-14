package utils;

import com.dhlk.utils.CheckUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * Content: 单元测试
 * Author:jpdong
 * Date:2020/3/6
 */
public class CheckUtilsTest {

    @Test
    public void isNull() {
        Boolean re = CheckUtils.isNull("abc");
        Assert.assertTrue(!re);
    }

    @Test
    public void isNumeric() {
    }
}