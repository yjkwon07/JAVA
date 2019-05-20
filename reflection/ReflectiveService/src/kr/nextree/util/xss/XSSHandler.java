package kr.nextree.util.xss;

import kr.nextree.util.core.ValueHandler;

public class XSSHandler implements ValueHandler {

    @Override
    public String handle(String value) {
        // 
        // TODO: XSS 취약점을 보완하는 구현을 이곳에 한다.
        // ex) > => &gt;
        
        return "[" + value + "]";
    }
}
