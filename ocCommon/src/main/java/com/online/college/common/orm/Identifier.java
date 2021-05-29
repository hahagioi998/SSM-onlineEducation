package com.online.college.common.orm;

import java.io.Serializable;

/** 标识符类
 *  @param <KEY>
 */
public interface Identifier<KEY extends Serializable> {

	public KEY getId(); 
	
}
