package kr.nextree.util.xss;

import kr.nextree.util.core.ReflectiveService;
import kr.nextree.util.core.ValueHandler;

public class SecurityHandler {

    private SecurityHandler() {
        //
    }
    
    public static Object execute(Object obj) {
        //
        ValueHandler handler = new XSSHandler();
        ReflectiveService service = new ReflectiveService(handler);
        obj = service.execute(obj);
        return obj;
    }
}
