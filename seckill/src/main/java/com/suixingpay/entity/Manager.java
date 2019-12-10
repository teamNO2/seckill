/**
 * All rights Reserved, Designed By Suixingpay.
 *
 * @author: 孙克强<sun_kq@suixingpay.com>
 * @date: 2019/12/08 14:48
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
 * @date: 2019/12/08 14:48
 * @version: V1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Manager {
    /** 鑫管家ID **/
    private Integer manageId;
    /** 鑫管家名字 **/
    private String manageName;
    /** 鑫管家密码 **/
    private String managePassword;
    /** 鑫管家省份 **/
    private String manageProvince;
    /** 鑫管家是否抢到沉默用户0:没抢到，1：抢到 **/
    private Integer manageIsgrab;
        }
