package com.suixingpay.service;

import com.suixingpay.entity.Manager;
import com.suixingpay.utils.GenericResponse;

import java.util.concurrent.Callable;

/**
 * @ClassName ClickService
 * @Description TODO
 * @Author ShiMengyao
 * @Date 2019 年 12 月 8 日 0008 17:24:54
 * @Version 1.0
 */
public interface ClickService {
  Manager Click(Integer managerId);
}
