/**
 * All rights Reserved, Designed By Suixingpay.
 *
 * @author: 孙克强<sun_kq@suixingpay.com>
 * @date: 2019/12/08 14:49
 * @Copyright: 2019 Suixingpay. All rights reserved.
 * 注意：本内容仅限于随行付支付有限公司内部传阅，禁止外泄以及用于其他的商业用途。
 */
package com.suixingpay.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @description:
 * @author: 孙克强<sun_kq@suixingpay.com>
 * @date: 2019/12/08 14:49
 * @version: V1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Silentuser{
    /** 沉默用户主键 **/
    private Integer userId;
    /** 名字 **/
    private String userName;
    /** 电话 **/
    private String userTel;
    /** 性别0：男，1：女 **/
    private Integer userSex;
    /** 地址 **/
    private String userAddress;
    /** 是否白名单0：不白名单，1：白名单 **/
    private Integer userIswhite;
    /** 鑫管家id **/
    private Integer manageId;
    /** 沉默用户被分配时间 **/
    private String userTime;
    /** 轮空标记0：不轮空，1：轮空 **/
    private Integer userIsbyebye;
    /** 沉默用户数量 **/
    private Integer countNotWhite;
    /** 白名单数量**/
    private Integer countWhite;
    /** 可参加用户数量 **/
    private Integer countManagerId;
    /** 省份表 **/
    private String userProvince;


}
