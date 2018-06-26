package com.common.entity.login;

import java.io.Serializable;

public class UserPost implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -68205815964822861L;
	
	private User user;
	
	private Post post;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

}
