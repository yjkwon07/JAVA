package kr.nextree.util.test;

public class Child extends Parent {

    private String nickname;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        // 
        StringBuilder builder = new StringBuilder();
        builder.append(super.toString());
        builder.append("\nnickname : " + nickname);
        return builder.toString();
    }
}
