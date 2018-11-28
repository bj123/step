package com.aiwsport.core.constant;

/**
 * 公共静态枚举
 * @author yangjian9
 *
 */
public interface stepConstant {
	
	/**
	 * 服务执行 是否成功
	 */
	public interface IsSuccess{
		public static final int YES = 1;
		public static final int NO = 0;
	}
	
	/**
	 * 是/否
	 */
	public interface YesOrNo{
		public static final int YES = 1;
		public static final int NO = 0;
	}
	
	/**
	 * 返回消息模式   1、context 2、exception
	 */
	public interface MessageMode{
		public static final int CONTEXT = 1;
		public static final int EXCEPTION = 2;
	}

	/**
	 * 状态,1-有效,2-无效
	 */
	public interface ValidState{
		public static final String YES = "1";//1-有效,0-无效
		public static final String NO = "0";//1-有效,0-无效
	}

}
