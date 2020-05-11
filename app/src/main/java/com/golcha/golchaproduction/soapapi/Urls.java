package com.golcha.golchaproduction.soapapi;

public class Urls {
    private static String base_url = "http://myerp.golchagroup.com:1132";
    private static String company_name = "DummyGST";

    public static String planned_production_list_url = base_url +  "/" + company_name +
            "/WS/UMDS%20Pvt.Ltd./Page/PlannedProductionOrder";
    public static String planned_production_list_namespace = "urn:microsoft-dynamics-schemas/page/plannedproductionorder";

    public static String updated_production_list_url = base_url +  "/" + company_name +
            "/WS/UMDS%20Pvt.Ltd./Page/rpo";
    public static String updated_production_list_namespace = "urn:microsoft-dynamics-schemas/page/rpo";
}
