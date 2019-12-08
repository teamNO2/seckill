/**
 * All rights Reserved, Designed By Suixingpay.
 *
 * @author: sunkeqiang<sun_kq@suixingpay.com>
 * @date: 2019/12/08 14:45
 * @Copyright: 2019 Suixingpay. All rights reserved.
 * 注意：本内容仅限于随行付支付有限公司内部传阅，禁止外泄以及用于其他的商业用途。
 */
package com.suixingpay.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.lang.Integer;

/**
 * @description:
 * @author: sunkeqiang<sun_kq@suixingpay.com>
 * @date: 2019/12/08 14:45
 * @version: V1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Distribution{
    /** 主键 **/
    private Integer disId;
    /** 管家id **/
    private Integer disManageid;
    /** 沉默用户id **/
    private Integer disUserid;
}
