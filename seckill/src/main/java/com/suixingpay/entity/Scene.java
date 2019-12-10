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
public class Scene {
    /** 主键 **/
    private Integer sceneId;
    /** 开始时间 **/
    private String sceneStarttime;
    /** 结束时间 **/
    private String sceneEndtime;
    /** 实际持续时间 **/
    private String sceneContinurtime;
    /** 活动省份 **/
    private String sceneProvince;
    /** 沉默用户数量 **/
    private Integer sceneCount;
    /** 未分配沉默用户数量 **/
    private Integer sceneUnallocated;
}
