package org.liu.master.cs.apimonitor.domain;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

/**
 * @author Mutaz Hussein Kabashi
 * @version 1.0
 */
@Entity
@Table(name="web_service")
@DataObject(generateConverter = true, publicConverter = false)
public class WebService extends BaseModel {
	@Id @GeneratedValue
	//FIXME Id shouldnt have setter method but we will add here as a waorkaround
	private Integer id;

	//@Size(min=13, max=13)
	@URL
	@NotNull
	@Column(name = "url")
	private String url;
	
	/*@NotNull
	@Column(name = "request_uri")
	private String requestURI;*/

	@NotNull @Size(max=100)
	@Column(name = "name", unique=true)
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUrl(String url) {
		this.url = url;
	}



	//FIXME emunarator (GET,POST,...etc)
	@NotNull @Size(min=3)
	@Column(name = "method")
	private String method;

    //FIXME https://stackoverflow.com/questions/21574236/how-to-fix-org-hibernate-lazyinitializationexception-could-not-initialize-prox
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user;

	public WebService() {}

	/**
	 *
	 * @param url
	 * @param name
	 * @param method
	 */
	public WebService(String url, String name, String method) {
		this.url = url;
		this.name = name;
		this.method = method;
	}

	public Integer getId() {
		return id;
	}

	//FIXME workaroud solution
	public void setId(Integer id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}


	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


	public JsonObject toJson() {
		JsonObject json = new JsonObject();
	    WebServiceConverter.toJson(this, json);
	    return json;
	  }
	
	//FIXME could be blocked here
	//FIXME should be available on Util class/interface
	public JsonArray ListToJson(List<WebService> webServicess){
		JsonArray result = new JsonArray();
		for (WebService webService : webServicess) {
			JsonObject json = new JsonObject();
		    WebServiceConverter.toJson(webService, json);
		    result.add(json);
		}
		return result;
	}
	
	public WebService(JsonObject json) {
		WebServiceConverter.fromJson(json, this);
	  }

	public WebService(WebService webService2) {
	this.url = webService2.url;
	this.method = webService2.method;
	this.name = webService2.name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		WebService that = (WebService) o;
		return id.equals(that.id) &&
				url.equals(that.url) &&
				name.equals(that.name) &&
				method.equals(that.method) &&
				user.equals(that.user);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), id, url, name, method, user);
	}

	@Override
	public String toString() {
		return "WebService [id=" + id + ", url=" + url + /*", requestURI=" + requestURI +*/ ", name=" + name + ",method=" + method + "]";
	}
	

}