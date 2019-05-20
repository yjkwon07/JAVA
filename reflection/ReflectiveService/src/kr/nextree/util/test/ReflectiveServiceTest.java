package kr.nextree.util.test;

import java.util.ArrayList;
import java.util.List;

import kr.nextree.util.core.ReflectiveService;
import kr.nextree.util.core.ValueHandler;

public class ReflectiveServiceTest {

    public static void main(String[] args) {
        
        ReflectiveService service = getService();
        
        System.out.println("\n[testStringValue] =========================================");
        testStringValue(service);
        System.out.println("\n[testArray] ===============================================");
        testArray(service);
        System.out.println("\n[testList] ================================================");
        testList(service);
        System.out.println("\n[testSimpleObject] ========================================");
        testSimpleObject(service);
        System.out.println("\n[testInheritance] =========================================");
        testInheritance(service);
        System.out.println("\n[testCycling] =========================================");
        testCycling(service);
    }

    public static void testStringValue(ReflectiveService service) {
        //
        String value = "value";
        System.out.println("Before : " + value);
        System.out.println("After  : " + (String) service.execute(value));
    }
    
    public static void testArray(ReflectiveService service) {
        String[] array = new String[]{"Apple", "Banana", "Orange"};
        
        System.out.println("Before : ");
        for (String item : array) {
            System.out.println(item);
        }
        service.execute(array);
        
        System.out.println("After : ");
        for (String item : array) {
            System.out.println(item);
        }
    }
    
    public static void testList(ReflectiveService service) {
        List<String> values = new ArrayList<>();
        values.add("Apple");
        values.add("Banana");
        values.add("Orange");
        
        System.out.println("IsArray?? : "+ values.getClass().isArray());
        
        System.out.println("Before : ");
        for (String item : values) {
            System.out.println(item);
        }
        service.execute(values);
        
        System.out.println("After : ");
        for (String item : values) {
            System.out.println(item);
        }
    }
    
    public static void testSimpleObject(ReflectiveService service) {
        Parent p = new Parent();
        p.setId(100);
        p.setName("김현오");
        System.out.println("Before:\n" + p);
        service.execute(p);
        System.out.println("After:\n" + p);
    }
    
    public static void testInheritance(ReflectiveService service) {
        Child c = new Child();
        c.setId(200);
        c.setName("김현오");
        c.setNickname("김기사");
        
        System.out.println("Before:\n" + c);
        service.execute(c);
        System.out.println("After:\n" + c);
    }
    
    public static void testCycling(ReflectiveService service) {
        RecursiveObj obj = new RecursiveObj();
        obj.setName("순환참조");
        
        obj.setReference(obj);
        System.out.println("Before:\n" + obj.getName());
        service.execute(obj); 
        System.out.println("After:\n" + obj.getName());
        System.out.println();
    }
    
    // 처리자 생성 
    public static ReflectiveService getService() {
        return new ReflectiveService(new ValueHandler() {
            @Override
            public String handle(String value) {
                // 
                return "[" + value + "]";
            }
        });
    }
    
    /*
    
public void saveCustomer(Customer customer) {
    //
    if (customer != null) {
        customer.setName(SecurityUtil.convert(customer.getName()));
        if (customer.getAddress() != null) {
           Address address = customer.getAddress();
           address.setCity(SecurityUtil.convert(address.getCity());
           address.setDetailAddress(SecurityUtil.convert(address.getDetailAddress));
        }
        // ...
    }

    //...
}

    public void saveCustomer(Customer customer) {
        //
        SecurityHandler.execute(customer);

        //...
    }
    
    */
}
