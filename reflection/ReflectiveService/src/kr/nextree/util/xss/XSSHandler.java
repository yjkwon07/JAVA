package kr.nextree.util.xss;

import kr.nextree.util.core.ValueHandler;

public class XSSHandler implements ValueHandler {

    @Override
    public String handle(String value) {
        // 
        // TODO: XSS ������� �����ϴ� ������ �̰��� �Ѵ�.
        // ex) > => &gt;
        
        return "[" + value + "]";
    }
}
