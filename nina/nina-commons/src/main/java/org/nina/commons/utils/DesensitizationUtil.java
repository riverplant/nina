/**
 * 
 */
package org.nina.commons.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * @author riverplant 用于生成用户信息脱敏 可用于: 用户名 手机号 邮箱 地址等
 */
public class DesensitizationUtil {

	private static final int SIZE = 6;
	private static final String SYMBOL = "*";
    /**
     * 通用脱敏方法
     * @param value
     * @return
     */
	public static String commonDisplay(String value) {
        if(StringUtils.isAllBlank(value)) {
        	return value;
        }
		int len = value.length();
		int pamaone = len / 2 ;
		int pamatwo = pamaone - 1 ;
		int pamathree = len % 2 ;
		StringBuilder stringBuilder = new StringBuilder();
		if(len <= 2) {//需要脱敏的值<=2
			if(pamathree == 1) {//只有一个值，直接返回*
				return SYMBOL;
			}
			//有两个值:在头部加入*
			stringBuilder.append(SYMBOL);
			stringBuilder.append(value.charAt(len - 1));	
		}else {//需要脱敏的值>2
			if(pamatwo <=0) {//有三位，在中间加*
				stringBuilder.append(value.subSequence(0, 1));
				stringBuilder.append(SYMBOL);
				stringBuilder.append(value.subSequence(len - 1, len));
			}else if (pamatwo >= SIZE / 2 && SIZE + 1 != len) {//长度小于设置的6
                int pamafive = (len - SIZE) / 2;
                stringBuilder.append(value.substring(0, pamafive));
                for (int i = 0; i < SIZE; i++) {
                    stringBuilder.append(SYMBOL);
                }
                stringBuilder.append(value.substring(len - (pamafive + 1), len));
            } else {//长度大于设置值
                int pamafour = len - 2;
                stringBuilder.append(value.substring(0, 1));
                for (int i = 0; i < pamafour; i++) {
                    stringBuilder.append(SYMBOL);
                }
                stringBuilder.append(value.substring(len - 1, len));
            }
		}
		
		return stringBuilder.toString();
	}
}
