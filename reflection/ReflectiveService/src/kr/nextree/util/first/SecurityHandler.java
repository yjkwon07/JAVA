package kr.nextree.util.first;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SecurityHandler {
    
    /** ó���� ��ü���۷����� */
    private List<Object> references;
    
    /**
     * ������
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
     * ��ü�� ó���Ѵ�.
     * 
     * @param obj
     * @return
     */
    private Object handle(Object obj) {
        //
        if (obj == null) return null;
        
        // �̹� ó���� ��ü�� skip
        if (references.contains(obj)) return obj; 
        
        if (obj instanceof String) { 
            return handleStringValue((String) obj);
        } else {
            // �ߺ�ó�� ������
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
     * ��ü�� �ʵ�鿡 ���Ͽ� ó���Ѵ�.
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
     * �ش� Ŭ������ ��� �ʵ带 �����Ѵ�.
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
     * �迭�� ó���ϴ� �޼ҵ�
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
     * �־��� �ʵ尡 ó���������� ���θ� �����Ѵ�.
     *  - primitive type ����
     *  - immutable object ���� 
     *  - static final �ʵ� ����
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
     * ���ڿ��� ó���Ѵ�.
     * 
     * @param value
     * @return
     */
    private String handleStringValue(String value) {
        //
        return SecurityUtil.covert(value);
    }
}
