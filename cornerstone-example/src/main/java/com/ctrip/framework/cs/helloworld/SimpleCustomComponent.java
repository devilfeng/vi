package com.ctrip.framework.cs.helloworld;

import com.ctrip.framework.cs.annotation.ComponentStatus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by jiang.j on 2016/6/15.
 */
@ComponentStatus(id = "example.simplecustom", name = "simple custom component status", description = "自定义组件状态实例", custom = true)
public class SimpleCustomComponent {

    List<Order> orders = new ArrayList<>();
    String companyName;
    public SimpleCustomComponent() {

        companyName = "Ctrip framework";

        for (int i = 0; i < 100; i++) {
            Order newItem = new Order();
            newItem.no = "200161900-" + i;
            newItem.createDate = new Date();
            newItem.price = 1000 + i;
            orders.add(newItem);
        }

    }

    public class Order {
        String no;
        Date createDate;
        double price;
    }
}
