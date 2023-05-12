package me.muheun.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import me.muheun.exception.JSONParseException;
import me.muheun.util.JSON.Builder.CollectionBuilder;
import me.muheun.util.JSON.Builder.MapBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

@Slf4j
public class JSON {

  private static final JsonNodeFactory factory;
  private static final ObjectMapper mapper;

  static {
    factory = JsonNodeFactory.withExactBigDecimals(true);
    mapper = new ObjectMapper().setNodeFactory(factory);
    mapper.registerModule(new JavaTimeModule());
  }

  public static Builder.MapBuilder createObject() {
    return create(MapUtil.newMap());
  }

  public static Builder.MapBuilder createObject(Object obj) {
    try {
      if (obj instanceof JSONObject) {
        return create(((JSONObject) obj).toMap());
      } else if (obj instanceof String) {
        if (StringUtil.isEmpty(obj)) {
          return create(MapUtil.newMap());
        }
        return create(mapper.readValue((String) obj, Map.class));
      } else if (obj instanceof Map) {
        return create((Map) obj);
      } else {
        return create(mapper.convertValue(obj, Map.class));
      }
    } catch (IOException e) {
      e.printStackTrace();
      return create(MapUtil.newMap());
    }
  }

  private static Builder.MapBuilder create(Map<String, Object> jobj) {
    return Builder.newMap(jobj);
  }

  public static Builder.CollectionBuilder createArray() {
    return create(ListUtil.newList());
  }

  public static Builder.CollectionBuilder createArray(Object obj) {
    try {
      if (obj instanceof JSONArray) {
        return create(((JSONArray) obj).toList());
      } else if (obj instanceof String) {
        if (StringUtil.isEmpty(obj)) {
          return create(ListUtil.newList());
        }
        return create(mapper.readValue((String) obj, List.class));
      } else if (obj instanceof List) {
        return create((List) obj);
      } else if (obj instanceof Map) {
        return create(ListUtil.toList(obj));
      } else {
        return create(mapper.convertValue(obj, List.class));
      }
    } catch (IOException e) {
      e.printStackTrace();
      return create(ListUtil.newList());
    }
  }

  private static Builder.CollectionBuilder create(List<Object> list) {
    return Builder.newList(list);
  }

  public static Map<String, Object> merge(Map<String, Object> originMap, Map<String, Object> newMap) {
    try {
      return mapper.convertValue(
          mapper.readerForUpdating(originMap).readValue(mapper.writeValueAsString(newMap)),
          new TypeReference<Map<String, Object>>() {
          }
      );
    } catch (JsonProcessingException e) {
      throw new JSONParseException(e);
    }
  }

  static class Builder {

    private static MapBuilder newMap(Map<String, Object> map) {
      return new MapBuilder(map);
    }

    private static CollectionBuilder newList(List<Object> list) {
      return new CollectionBuilder(list);
    }

    public static class CollectionBuilder {

      public CollectionBuilder(List<Object> list) {
        this.list = new ArrayList<>(list);
      }

      private List<Object> list;

      public CollectionBuilder add(Object obj) {
        if (obj instanceof CollectionBuilder) {
          obj = ((CollectionBuilder) obj).toList();
        } else if (obj instanceof MapBuilder) {
          obj = ((MapBuilder) obj).toMap();
        } else if (obj instanceof JSONArray) {
          obj = ((JSONArray) obj).toList();
        } else if (obj instanceof JSONObject) {
          obj = ((JSONObject) obj).toMap();
        }
        list.add(obj);
        return this;
      }

      public List<Object> toList() {
        return list;
      }

      public JSONArray build() {
        return new JSONArray(toString());
      }

      public <T> List<T> build(Class<T> clazz) {
        return mapper.convertValue(list, TypeFactory.defaultInstance().constructCollectionType(List.class, clazz));
      }

      @Override
      public String toString() {
        try {
          return mapper.writeValueAsString(list);
        } catch (JsonProcessingException e) {
          e.printStackTrace();
          return "[]";
        }
      }
    }

    public static class MapBuilder {

      private Map<String, Object> map;

      public MapBuilder(Map<String, Object> map) {
        this.map = map;
      }

      public MapBuilder put(String key, Object value) {
        if (value instanceof JSONObject) {
          value = ((JSONObject) value).toMap();
        } else if (value instanceof JSONArray) {
          value = ((JSONArray) value).toList();
        } else if (value instanceof MapBuilder) {
          value = ((MapBuilder) value).toMap();
        } else if (value instanceof CollectionBuilder) {
          value = ((CollectionBuilder) value).toList();
        }
        map.put(key, value);
        return this;
      }

      public Map<String, Object> toMap() {
        return map;
      }

      public JSONObject build() {
        return new JSONObject(map);
      }

      public <E> E build(Class<E> clazz) {
        return mapper.convertValue(map, clazz);
      }

      @Override
      public String toString() {
        try {
          return mapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
          e.printStackTrace();
          return "{}";
        }
      }
    }
  }

  public static boolean isValidJSON(final String json) throws IOException {
    boolean valid = true;
    try {
      mapper.readTree(json);
    } catch (JsonProcessingException e) {
      valid = false;
    }
    return valid;
  }

  public static String prettyString(Object obj) {
    try {
      if (obj instanceof JSONObject) {
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(((JSONObject) obj).toMap());
      } else if (obj instanceof JSONArray) {
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(((JSONArray) obj).toList());
      } else if (obj instanceof MapBuilder) {
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(((MapBuilder) obj).build().toMap());
      } else if (obj instanceof CollectionBuilder) {
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(((CollectionBuilder) obj).build().toList());
      } else if (obj instanceof String) {
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mapper.readTree((String) obj));
      } else {
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
      }
    } catch (IOException e) {
      log.error("error: {}", e);
      return "";
    }
  }
}
