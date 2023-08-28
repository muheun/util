package me.muheun.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import me.muheun.util.JSON.Builder.MapBuilder;
import org.apache.commons.lang3.ArrayUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

public class JsonTest {

  @Test
  public void stringTest() {

    Debug.json("a25fba73-3611-4ae2-89d1-531555ce3965");


  }

  @Test
  public void mergeMap() {

    Map<String, Object> map1 = MapUtil.chainKeyMap().put("1", "a").toMap();
    Map<String, Object> map2 = MapUtil.chainKeyMap().put("2", "b").toMap();
    Map<String, Object> map1_1 = MapUtil.chainKeyMap().put("1-1", "c").toMap();
    Map<String, Object> map1_2 = MapUtil.chainKeyMap().put("1-2", "c").toMap();
    Map<String, Object> map1_1_1 = MapUtil.chainKeyMap().put("1-1-1", "d").toMap();
    Map<String, Object> map1_1_2 = MapUtil.chainKeyMap().put("1-1-2", "d").toMap();

    map1.put("1-1", map1_1);
    map1.put("1-2", map1_2);
    map1_1.put("1-1-1", map1_1_1);
    map1_1.put("1-1-2", map1_1_2);

    Debug.json(map1);

    Map<String, Object> newMap1 = MapUtil.chainKeyMap().put("1", "a1").toMap();
    Map<String, Object> newMap1_1 = MapUtil.chainKeyMap().put("1-1", "c1").toMap();

    newMap1.put("1-1", newMap1_1);

//    map1 = deepMerge(map1, newMap1);
    map1 = JSON.merge(map1, newMap1);
    Debug.json(map1);
  }

  public static Map<String, Object> deepMerge(Map<String, Object> original, Map<String, Object> newMap) {
    for (String key : newMap.keySet()) {
      if (newMap.get(key) instanceof Map && original.get(key) instanceof Map) {
        Map<String, Object> originalChild = (Map<String, Object>) original.get(key);
        Map<String, Object> newChild = (Map) newMap.get(key);
        original.put(key, deepMerge(originalChild, newChild));
      } else {
        original.put(key, newMap.get(key));
      }
    }
    return original;
  }

  @Test
  public void getTest() {
    Map<String, Object> map1 = MapUtil.chainKeyMap().put("1", "a").toMap();
    Map<String, Object> map1_1 = MapUtil.chainKeyMap().put("1-1", "c").toMap();
    Map<String, Object> map1_2 = MapUtil.chainKeyMap().put("1-2", "c").toMap();
    Map<String, Object> map1_1_1 = MapUtil.chainKeyMap().put("1-1-1", "d").toMap();
    Map<String, Object> map1_1_2 = MapUtil.chainKeyMap().put("1-1-2", "5").toMap();

    map1.put("1-1", map1_1);
    map1.put("1-2", map1_2);
    map1_1.put("1-1-1", map1_1_1);
    map1_1.put("1-1-2", map1_1_2);

    MapBuilder builder = JSON.createObject(map1);
    JSONObject jobj = builder.build();

    builder = builder.path("1-1");

    builder.print();

    Debug.debug(JSON.findJSONObject(jobj, "1-1/1-1-1"));
    Debug.debug(builder.getJSONObject("1-1/1-1-1", new Person()));
    Debug.debug(builder.getString("1-1-1/1-1-1"));
    Debug.debug(builder.getInt("1-1-2/1-1-2"));
    Debug.debug(builder.getDouble("1-1-2/1-1-2"));

  }

  @Test
  public void toStringTest() {
    System.out.println(String.valueOf(new JsonTest()));
    System.out.println(StringUtil.toString(null));
    System.out.println(StringUtil.toString(null));
  }


  @Getter
  @Setter
  class Person {
    private String name = "";
    private int age;
  }

}
