package com.golcha.golchaproduction.soapapi;
import android.content.Context;
import android.util.Log;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;

public class SoapApis {
    private static final String TAG = "SoapApis";
    public static String userName = "aman.srivastav";
    public static String password = "Change@123";
    public static String domain = "gghojai";

    public static void getFgInventoryData()
    {
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
                    }
                    catch (Exception e)
                    {
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



    public static SoapObject getPlannedOrderList()
    {
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
}