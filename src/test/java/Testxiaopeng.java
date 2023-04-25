import com.alibaba.fastjson.JSONObject;

import java.util.*;

/**
 * @ClassName: Testxiaopeng
 * @Author: Bugpeng
 * @Since: 2023/3/31
 * @Remark:
 */
public class Testxiaopeng {
    public static void main(String[] args) {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1,1);
        map.put(12,12);
        map.put(123,123);
        map.put(1234,1234);
        List<Integer> integers = new ArrayList<>(map.keySet());
        System.out.println(JSONObject.toJSONString(map.keySet()));
    }
}
