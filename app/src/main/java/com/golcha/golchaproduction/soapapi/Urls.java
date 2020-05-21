package com.golcha.golchaproduction.soapapi;
public class Urls {



//    private static String base_url = "http://myerp.golchagroup.com:1132";
//    private static String company_name = "DummyGST";

    private static String base_url = "http://myerp.golchagroup.com:7048";
    private static String company_name = "DynamicsNAV90";

    public static String planned_production_list_url = base_url +  "/" + company_name +
            "/WS/UMDS%20Pvt.Ltd./Page/PPOListPage";

    public static String planned_production_list_namespace =  "urn:microsoft-dynamics-schemas/page/ppolistpage";


    public static String planned_production_card_url = base_url +  "/" + company_name +
            "/WS/UMDS%20Pvt.Ltd./Page/PlannedProdOrder";
    public static String planned_production_card_namespace = "urn:microsoft-dynamics-schemas/page/plannedprodorder";


    public static String updated_production_list_url = base_url +  "/" + company_name +
            "/WS/UMDS%20Pvt.Ltd./Page/rpo";
    public static String updated_production_list_namespace = "urn:microsoft-dynamics-schemas/page/rpo";

    public static String employeelocations_list_url=base_url + "/" +company_name +
            "/WS/UMDS%20Pvt.Ltd./Page/EmployeeLocations";
    public static String employeelocations_list_namespace = "urn:microsoft-dynamics-schemas/page/employeelocations";

    public static  String source_no_item_url = base_url + "/" +company_name +
            "/WS/UMDS%20Pvt.Ltd./Page/ItemList";
    public static  String source_no_item_namespace = "urn:microsoft-dynamics-schemas/page/itemlist";

    public static  String get_depart_machine_url = base_url + "/" +company_name +
            "/WS/UMDS%20Pvt.Ltd./Page/DepptPPOAPI";
    public static  String get_depart_machine_namespace = "urn:microsoft-dynamics-schemas/page/depptppoapi";

    public static  String refreshbutton_url = base_url + "/" +company_name +
            "/WS/UMDS%20Pvt.Ltd./Codeunit/RefProdOrderActionButtonAPi";
    public static  String refresh_button_namespace = "urn:microsoft-dynamics-schemas/codeunit/RefProdOrderActionButtonAPi";

    public static  String change_statusbutton_url = base_url + "/" +company_name +
            "/WS/UMDS%20Pvt.Ltd./Codeunit/CahngeActionButtonApi";
    public static  String change_statusbutton_namespace = "urn:microsoft-dynamics-schemas/codeunit/CahngeActionButtonApi";



    public static  String Read_ReleaseProduction_url = base_url + "/" +company_name +
            "/WS/UMDS%20Pvt.Ltd./Page/RPODocPage";
    public static  String Read_ReleaseProduction_namespace = "urn:microsoft-dynamics-schemas/page/rpodocpage";


    public static  String ChangeStatus_Release_url = base_url + "/" +company_name +
            "/WS/UMDS%20Pvt.Ltd./Codeunit/CahngeActionButtonApi";
    public static  String ChangeStatus_Release_namespace = "urn:microsoft-dynamics-schemas/codeunit/CahngeActionButtonApi";

    public static  String CreateInspection_Release_url = base_url + "/" +company_name +
            "/WS/UMDS%20Pvt.Ltd./Codeunit/CreateInspDataSheetActButonAPI";
    public static  String CreateInspection_Release_namespace = "urn:microsoft-dynamics-schemas/codeunit/CreateInspDataSheetActButonAPI";

}
