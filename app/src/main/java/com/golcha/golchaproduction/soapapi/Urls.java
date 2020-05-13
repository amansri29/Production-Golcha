package com.golcha.golchaproduction.soapapi;
public class Urls {
    private static String base_url = "http://myerp.golchagroup.com:1132";
    private static String company_name = "DummyGST";

    public static String planned_production_list_url = base_url +  "/" + company_name +
            "/WS/UMDS%20Pvt.Ltd./Page/PPOListPage";

    public static String planned_production_list_namespace =  "urn:microsoft-dynamics-schemas/page/ppolistpage";


    public static String planned_production_card_url = "http://myerp.golchagroup.com:1132/DummyGST/WS/UMDS%20Pvt.Ltd./Page/PlannedProdOrder";
    public static String planned_production_card_namespace = "urn:microsoft-dynamics-schemas/page/plannedprodorder";


    public static String updated_production_list_url = base_url +  "/" + company_name +
            "/WS/UMDS%20Pvt.Ltd./Page/rpo";
    public static String updated_production_list_namespace = "urn:microsoft-dynamics-schemas/page/rpo";

    public static String employeelocations_list_url=base_url + "/" +company_name +
            "/WS/UMDS%20Pvt.Ltd./Page/EmployeeLocations";
    public static String employeelocations_list_namespace = "urn:microsoft-dynamics-schemas/page/employeelocations";
}
