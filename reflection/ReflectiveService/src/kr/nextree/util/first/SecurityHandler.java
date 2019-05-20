package kr.nextree.util.first;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SecurityHandler {
    
    /** 처리된 객체레퍼런스들 */
    private List<Object> references;
    
    /**
     * 생성자
     * 
     * @param handler
     */
    private SecurityHandler() {
        //
    }

    public static Object execute(Object obj) {
        //
        SecurityHandler handler = new SecurityHandler();
        
        handler.references = new ArrayList<>();
        obj = handler.handle(obj);
        handler.references = null;
        return obj;
    }
    
    //--------------------------------------------------------------------------
    // private methods
    //--------------------------------------------------------------------------
    
    /**
     * 객체를 처리한다.
     * 
     * @param obj
     * @return
     */
    private Object handle(Object obj) {
        //
        if (obj == null) return null;
        
        // 이미 처리된 객체는 skip
        if (references.contains(obj)) return obj; 
        
        if (obj instanceof String) { 
            return handleStringValue((String) obj);
        } else {
            // 중복처리 방지용
            references.add(obj);
            
            if (obj.getClass().isArray()) { 
                handleArray((Object[]) obj);
            } else {
                reflectObject(obj);
            }
        }
        
        return obj;
    }
        
    /**
     * 객체의 필드들에 대하여 처리한다.
     * 
     * @param obj
     * @return
     */
    private Object reflectObject(Object obj) {
        //
        List<Field> fields = extractFields(obj.getClass());
        for (Field field : fields) {
            if  (available(field)) {
                try {
                    field.setAccessible(true);
                    field.set(obj, handle(field.get(obj)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return obj;
    }
    
    /**
     * 해당 클래스의 모든 필드를 추출한다.
     * 
     * @param clazz
     * @return
     */
    @SuppressWarnings("rawtypes")
    private List<Field> extractFields(Class clazz) {
        //
        List<Field> fields = new ArrayList<>();
        fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
        
        // 
        Class superClazz = clazz.getSuperclass();
        if (superClazz != null) {
            fields.addAll(extractFields(superClazz));
        }
        
        return fields;
    }
    
    /**
     * 배열을 처리하는 메소드
     * @param array
     */
    private void handleArray(Object[] array) {
        //
        if (array != null && array.length > 0) {
            int length = array.length;
            for (int i = 0; i < length; i++) {
                Object item = array[i];
                if (item == null) continue;
                
                if (item instanceof String) {
                    array[i] = handleStringValue((String) item);
                } else {
                    handle(item);
                }
            }
        }
    }
    
    /**
     * 주어진 필드가 처리가능한지 여부를 리턴한다.
     *  - primitive type 제외
     *  - immutable object 제외 
     *  - static final 필드 제외
     * @param obj
     * @return
     */
    @SuppressWarnings("rawtypes")
    private boolean available(Field field) {
        //
        Class clazz = field.getType();
        
        // primitive type
        if (clazz == int.class) return false;
        else if (clazz == short.class) return false;
        else if (clazz == long.class) return false;
        else if (clazz == float.class) return false;
        else if (clazz == byte.class) return false;
        else if (clazz == double.class) return false;
        else if (clazz == char.class) return false;
        else if (clazz == boolean.class) return false;

        // immutable object type
        else if (clazz == BigDecimal.class) return false;
        else {
            // static final field
            int mod = field.getModifiers();
            if ( Modifier.isStatic(mod) && Modifier.isFinal(mod) ) return false;
        }
        
        return true;
    }
    
    /**
     * 문자열을 처리한다.
     * 
     * @param value
     * @return
     */
    private String handleStringValue(String value) {
        //
        return SecurityUtil.covert(value);
    }
}
