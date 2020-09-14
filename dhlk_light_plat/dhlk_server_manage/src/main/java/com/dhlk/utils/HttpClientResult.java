package com.dhlk.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Description: 封装httpClient响应结果
 * 
 * @author jlv
 * @date Created on 2020年4月15日
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HttpClientResult implements Serializable {

	/**
	 * 响应状态码
	 */
	private int code;

	/**
	 * 响应数据
	 */
	private String content;

	public HttpClientResult(int code){
	    this.code=code;
    }

}
