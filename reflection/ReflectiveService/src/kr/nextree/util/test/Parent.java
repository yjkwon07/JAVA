package kr.nextree.util.test;

public class Parent {

    private int id;
    private String name;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        // 
        StringBuilder builder = new StringBuilder();
        builder.append("id:" + id);
        builder.append("\nname:" + name);
        return builder.toString();
    }
}
