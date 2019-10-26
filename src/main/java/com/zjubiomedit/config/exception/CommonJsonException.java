package com.zjubiomedit.config.exception;

import com.zjubiomedit.util.enums.ErrorEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * @author : leiyi sheng
 * 本系统使用的自定义错误类
 *
 */
public class CommonJsonException extends RuntimeException {
	@Getter
	@Setter
	private ErrorEnum error;

	/**
	 * @param errorEnum 以错误的ErrorEnum做参数
	 */
	public CommonJsonException(ErrorEnum errorEnum) {
		this.error = errorEnum;
	}

}
