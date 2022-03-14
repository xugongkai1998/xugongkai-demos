package com.xgk.demo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Random;

/**
 * @author: xugongkai
 * @created: 2022-03-14 15:00
 */
@Getter
@Setter
@ToString
public class OrderInfo implements Serializable {

    public Integer platformNo;
    public Integer shopId;
    public Integer orderTotal;
    public BigDecimal totalPrice;
    public Date createTime;

    public static OrderInfo randomOrderInfo() {
        OrderInfo orderInfo = new OrderInfo();
        Random random = new Random();
        orderInfo.setPlatformNo(random.nextInt(100) + 1);
        orderInfo.setShopId(random.nextInt(8888)+1111);
        orderInfo.setOrderTotal(random.nextInt(10) + 5);
        orderInfo.setTotalPrice(BigDecimal.valueOf(random.nextFloat() + 1));
        orderInfo.setCreateTime(new Date());
        return orderInfo;
    }

}
