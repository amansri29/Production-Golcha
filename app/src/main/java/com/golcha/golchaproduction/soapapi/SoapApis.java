package com.golcha.golchaproduction.soapapi;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;

import java.util.ArrayList;

public class SoapApis {
    private static final String TAG = "SoapApis";
    public static String userName = "aman.srivastav";
    public static String password = "Change@123";
    public static String domain = "gghojai";

    public static void getFgInventoryData() {
        String namespace2 = "urn:microsoft-dynamics-schemas/page/fgdateandlocationwiserating";
        String url2 = "http://myerp.golchagroup.com:7048/DynamicsNAV90/WS/UMDS%20Pvt.Ltd./Page/FGDateandLocationWiseRating";
        String method_name2 = "ReadMultiple";
        String soap_action2 = namespace2 + ":" + method_name2;

        try {
            SoapObject request = new SoapObject(namespace2, method_name2);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            NtlmTransport ntlm = new NtlmTransport();
            ntlm.debug = true;
            ntlm.setCredentials(url2, userName, password, domain, "");

            ntlm.call(soap_action2, envelope); // Receive Error here!

            try {
                SoapObject result = (SoapObject) envelope.getResponse();
                Log.i(TAG, "Aman: " + result);
                for (int i = 0; i < result.getPropertyCount(); i++) {
                    SoapObject result2 = (SoapObject) result.getProperty(i);
                    try {

                        Log.i(TAG, "Response FG Rating " + result2.getProperty("Planned_Delivery_Date") + " " + result2.getProperty("Location_Code") + " " + result2.getProperty("Rating") + " " + result2.getProperty("Overall_Rating"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                Log.e(TAG, "doInBackground: catch response FG Inventory Data " + e.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            String error = e.toString();
            Log.i(TAG, "doInBackground: catch " + error);
        }
    }


    public static SoapObject getPlannedOrderList() {
        String namespace2 = Urls.planned_production_list_namespace;
        String url2 = Urls.planned_production_list_url;
        String method_name2 = "ReadMultiple";
        String soap_action2 = namespace2 + ":" + method_name2;
        SoapObject result = null;

        try {
            SoapObject request = new SoapObject(namespace2, method_name2);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            NtlmTransport ntlm = new NtlmTransport();
            ntlm.debug = true;
            ntlm.setCredentials(url2, userName, password, domain, "");

            ntlm.call(soap_action2, envelope); // Receive Error here!

            result = (SoapObject) envelope.getResponse();



        } catch (Exception e) {
            e.printStackTrace();
            String error = e.toString();
            Log.e(TAG, "error " + error);
        }
        return result;
    }

    public static SoapObject getUpdatedOrderList() {
        String namespace2 = Urls.updated_production_list_namespace;
        String url2 = Urls.updated_production_list_url;
        String method_name2 = "ReadMultiple";
        String soap_action2 = namespace2 + ":" + method_name2;
        SoapObject result = null;

        try {
            SoapObject request = new SoapObject(namespace2, method_name2);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            NtlmTransport ntlm = new NtlmTransport();
            ntlm.debug = true;
            ntlm.setCredentials(url2, userName, password, domain, "");

            ntlm.call(soap_action2, envelope); // Receive Error here!

            result = (SoapObject) envelope.getResponse();

        } catch (Exception e) {
            e.printStackTrace();
            String error = e.toString();
            Log.e(TAG, "error " + error);
        }
        return result;
    }


    public static SoapObject getPlannedCardDetails(String no) {
        String namespace2 = Urls.planned_production_card_namespace;
        String url2 = Urls.planned_production_card_url;
        String method_name2 = "Read";
        String soap_action2 = namespace2 + ":" + method_name2;
        SoapObject result2 = null;


        try {
            SoapObject request = new SoapObject(namespace2, method_name2);
            request.addProperty("No", no);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;

//            SoapObject filter = new SoapObject(namespace2,"filter");
//            envelope.bodyOut = request;
            envelope.dotNet = true;

            envelope.setOutputSoapObject(request);

            NtlmTransport ntlm = new NtlmTransport();
            ntlm.debug = true;
            ntlm.setCredentials(url2, userName, password, domain, "");

            ntlm.call(soap_action2, envelope); // Receive Error here!

            result2 = (SoapObject) envelope.getResponse();


        } catch (Exception e) {
            e.printStackTrace();
            String error = e.toString();
            Log.e(TAG, "error " + error);
        }
        return result2;
    }
    public static ArrayList<String> getLocationlist(){
        String namespace2 = Urls.employeelocations_list_namespace;
        String url2 = Urls.employeelocations_list_url;
        String method_name2 = "ReadMultiple";
        String soap_action2 = namespace2 + ":" + method_name2;
        SoapObject result= null;
        ArrayList<String> list = new ArrayList<>();

        try {
            SoapObject request = new SoapObject(namespace2,method_name2);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            NtlmTransport ntlm = new NtlmTransport();
            ntlm.debug = true;
            ntlm.setCredentials(url2, userName, password, domain, "");
            ntlm.call(soap_action2, envelope);
            try {
                result= (SoapObject) envelope.getResponse();
                for(int i=0;i<result.getPropertyCount();i++){
                    SoapObject result2 = (SoapObject)result.getProperty(i);
                    try {
                        String loction_code = String.valueOf(result2.getProperty("Location_Code"));
                        String location_name = String.valueOf(result2.getProperty("Location_Name"));
                        String code_name = loction_code + "-" +location_name;
                        list.add(code_name);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            catch (SoapFault soapFault) {
                soapFault.printStackTrace();
            }

        }
        catch (Exception e){
            e.printStackTrace();
            String earror =e.toString();
            Log.e(TAG,"earror " + earror);

        }
        return list;

    }
    public static String CreatenewPlan(String source_no , String production_quan , String location_code){
        String namespace2 = Urls.planned_production_card_namespace;
        String url2 = Urls.planned_production_card_url;
        String method_name2 = "Create";
        String soap_action2 = namespace2 + ":" + method_name2;
        SoapObject result= null;
        String result2 = null;
        try {
            SoapObject request = new SoapObject(namespace2,method_name2);
            SoapObject plannedProdOrder = new SoapObject(namespace2,"PlannedProdOrder");
            plannedProdOrder.addProperty("Source_No",source_no);
            plannedProdOrder.addProperty("Production_Quantity",production_quan);
            plannedProdOrder.addProperty("Location_Code",location_code);
            request.addSoapObject(plannedProdOrder);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            NtlmTransport ntlmTransport = new NtlmTransport();
            ntlmTransport.debug = true;
            ntlmTransport.setCredentials(url2,userName,password,domain,"");
            ntlmTransport.call(soap_action2,envelope);
            try {
                result = (SoapObject)envelope.getResponse();
                result2=String.valueOf(result.getProperty("No"));
               Log.i("number",result2);
            }
            catch (SoapFault soapFault) {
                result2 =String.valueOf(soapFault);

                soapFault.printStackTrace();
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return result2;
    }

}