package kr.nextree.util.test;

public class RecursiveObj {

    private String name;
    
    private RecursiveObj reference;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RecursiveObj getReference() {
        return reference;
    }

    public void setReference(RecursiveObj reference) {
        this.reference = reference;
    }
}
