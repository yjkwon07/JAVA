package sec06.ex02;

public class User {
	private String user;
	private String pwd;
	User(String user , String pwd){
		this.pwd = pwd;
		this.user = user;
	}
	User(LoginImpl loginImpl){
		this.user = loginImpl.user_id;
		this.pwd = loginImpl.user_pw;
	}
	public void _setuser(String user) {
		this.user = user;
	}
	
	public void _setpwd(String pwd) {
		this.pwd = pwd;
	}
	
	public String _getuser() {
		return user;
	}
	
	public String _getpwd() {
		return pwd;
	}
}	

