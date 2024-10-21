package org.ProgettoP.Bean;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;

@SessionScoped
@Named
public class UtenteView implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	private long id;
	private String username;
	private String password;
	private String passwordRipeti;
	private long article;
	public UtenteView() {
	}
	public String getPasswordRipeti() {
		return passwordRipeti;
	}
	public void setPasswordRipeti(String passwordRipeti) {
		this.passwordRipeti = passwordRipeti;
	}
	public long getId() {
		return id;
	}
	public void setId(long iden) {
		this.id = iden;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public double getArticle() {
		return article;
	}
	public void setArticle(long article) {
		this.article = article;
	}
}