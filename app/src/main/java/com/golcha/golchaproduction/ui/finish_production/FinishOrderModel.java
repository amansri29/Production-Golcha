package com.golcha.golchaproduction.ui.finish_production;

import java.util.ArrayList;

public class FinishOrderModel {
    String sourceno,desc,no,routingno,quantity;

    public FinishOrderModel(String sourceno, String desc, String no, String routingno, String quantity){
        this.desc=desc;
        this.sourceno=sourceno;
        this.no=no;
        this.quantity=quantity;
        this.routingno=routingno;
    }


    public  String getSourceno()
    {
        return sourceno;
    }


    public  String getDesc() {
        return desc;
    }

    public String getNo() {
        return no;
    }

    public  String getQuantity() {
        return quantity;
    }

    public  String getRoutingno() {
        return routingno;
    }
}
