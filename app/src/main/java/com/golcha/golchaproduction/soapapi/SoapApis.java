package com.golcha.golchaproduction.soapapi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.golcha.golchaproduction.MainActivity;
import com.google.gson.Gson;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;

import java.util.ArrayList;
import java.util.Collections;

public class SoapApis {
    private static final String TAG = "SoapApis";
    public static String userName = "aman.srivastav";
    public static String password = "Change@123";
    public static String domain = "gghojai";


    private static boolean True=true;


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


    public static SoapObject getPlannedOrderList(Activity activity,String myusername, String mypassword) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
        Boolean full_Access = sharedPreferences.getBoolean("fullaccess",false);
        String namespace2 = Urls.planned_production_list_namespace;
        String url2 = Urls.planned_production_list_url;
        String method_name2 = "ReadMultiple";
        String soap_action2 = namespace2 + ":" + method_name2;
        SoapObject result = null;

        try {
            SoapObject request = new SoapObject(namespace2, method_name2);
            if (full_Access == false) {
                Log.i("access", String.valueOf(full_Access));
                String location_filter = sharedPreferences.getString("planproduction_filter_|_","");
                SoapObject filter = new SoapObject(namespace2,"filter");
                filter.addProperty("Field","Location_Code");
                filter.addProperty("Criteria",location_filter);
                request.addSoapObject(filter);
            }

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            NtlmTransport ntlm = new NtlmTransport();
            ntlm.debug = true;
            ntlm.setCredentials(url2, myusername, mypassword, domain, "");

            ntlm.call(soap_action2, envelope); // Receive Error here!

            result = (SoapObject) envelope.getResponse();



        } catch (Exception e) {
            e.printStackTrace();
            String error = e.toString();
            Log.e(TAG, "error " + error);
        }
        return result;
    }

    public static SoapObject getUpdatedOrderList(Activity activity ,String myusername, String mypassword) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
        Boolean full_Access = sharedPreferences.getBoolean("fullaccess",false);
        String namespace2 = Urls.updated_production_list_namespace;
        String url2 = Urls.updated_production_list_url;
        String method_name2 = "ReadMultiple";
        String soap_action2 = namespace2 + ":" + method_name2;
        SoapObject result = null;

        try {
            SoapObject request = new SoapObject(namespace2, method_name2);
            if (full_Access == false) {
                Log.i("access", String.valueOf(full_Access));
                String location_filter = sharedPreferences.getString("planproduction_filter_|_","");
                SoapObject filter = new SoapObject(namespace2,"filter");
                filter.addProperty("Field","Location_Code");
                filter.addProperty("Criteria",location_filter);
                request.addSoapObject(filter);
            }
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            NtlmTransport ntlm = new NtlmTransport();
            ntlm.debug = true;
            ntlm.setCredentials(url2, myusername,mypassword, domain, "");

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

    public static void Login(Activity activity, String myusername, String mypassword){
        Gson gson = new Gson();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String namespace2 = Urls.employeelocations_list_namespace;
        String url2 = Urls.employeelocations_list_url;
        String method_name2 = "ReadMultiple";
        String soap_action2 = namespace2 + ":" + method_name2;
        SoapObject result= null;

        String piping_locationCode = "";
        String loction_code ="";
        String loc_code ="";
        ArrayList<String> list = new ArrayList<>();
        ArrayList<String> list_loc= new ArrayList<>();


        try {
            SoapObject request = new SoapObject(namespace2,method_name2);
            SoapObject filter = new SoapObject(namespace2,"filter");
            filter.addProperty("Field","User_ID");
            filter.addProperty("Criteria","*"+myusername.toUpperCase());
            request.addSoapObject(filter);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            NtlmTransport ntlm = new NtlmTransport();
            ntlm.debug = true;
            ntlm.setCredentials(url2, myusername, mypassword, domain, "");
            ntlm.call(soap_action2, envelope);
            try {
                result= (SoapObject) envelope.getResponse();
                if(result.getPropertyCount() == 0){

                    editor.putBoolean("fullaccess", true); //sharedpreference to check location access

                }
                else {
                    for (int i = 0; i < result.getPropertyCount(); i++) {

                        SoapObject result2 = (SoapObject) result.getProperty(i);
                        try {
                            loction_code = String.valueOf(result2.getProperty("Location_Code"));
                            String location_name = String.valueOf(result2.getProperty("Location_Name"));
                            String code_name = loction_code + "-" + location_name;
                            piping_locationCode = piping_locationCode + loction_code + "|";//concanate string with "|" to filter productions
                            list_loc.add(loction_code);
                            list.add(code_name); // list concanate of location code and location name


                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                    loc_code = gson.toJson(list_loc);
                    int length=piping_locationCode.length()-1;
                    piping_locationCode = piping_locationCode.substring(0,length);
                }


            }
            catch (SoapFault soapFault) {
                soapFault.printStackTrace();
            }


            editor.putBoolean("Loginaccess", true);//savespreference to check logins
            editor.putString("username",myusername);
            editor.putString("password",mypassword);
            editor.putString("Location_code",loc_code);// storing location make concanate location code with " | " to filter planproduction
            editor.putString("planproduction_filter_|_",piping_locationCode);//// make concanate location code with " | " to filter planproduction
            editor.commit();
            Log.i("Locations",loc_code);


            //Moving to next Activity
            Intent intent = new Intent(activity, MainActivity.class);
            activity.startActivity(intent);
//            Log.i("Logins :  " , String.valueOf(sharedPreferences.getBoolean("Loginaccess",false)));
//            Log.i("Logins :  " , String.valueOf(sharedPreferences.getString("username","")));
//            Log.i("Logins :  " , String.valueOf(sharedPreferences.getString("password","")));
//            Log.i("Logins :  " , String.valueOf(sharedPreferences.getString("Location_code","")));
//
//            Log.i("Logins :  " , String.valueOf(sharedPreferences.getString("planproduction_filter_|_","")));

        }
        catch (Exception e){
            e.printStackTrace();
            String earror =e.toString();
            Log.e(TAG,"earror " + earror);

        };



    }
    public static ArrayList<String> getSource_no(Activity activity,String myusername,String mypassword,String key_item){

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String namespace2 = Urls.source_no_item_namespace;
        String url2 = Urls.source_no_item_url;
        String method_name2 = "ReadMultiple";
        String soap_action2 = namespace2 + ":" + method_name2;
        SoapObject result= null;
        String myresult = "";

        String No,desc1,desc2;

        ArrayList<String> list = new ArrayList<>();

        try {
            SoapObject request = new SoapObject(namespace2,method_name2);
            SoapObject filter = new SoapObject(namespace2,"filter");
            filter.addProperty("Field","No");
            filter.addProperty("Criteria",key_item);
            request.addSoapObject(filter);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            NtlmTransport ntlm = new NtlmTransport();
            ntlm.debug = true;
            ntlm.setCredentials(url2, myusername, mypassword, domain, "");
            ntlm.call(soap_action2, envelope);
            try {
                result= (SoapObject) envelope.getResponse();
                for (int i = 0; i < result.getPropertyCount(); i++) {

                    SoapObject result2 = (SoapObject) result.getProperty(i);
                    try {
                        No = String.valueOf(result2.getProperty("No"));
                        desc1 = String.valueOf(result2.getProperty("Description"));
                        desc2 = String.valueOf(result2.getProperty("Description_2"));
                        myresult = No +" "+ desc1 + " " + desc2;
                        Log.i("Sourceno",myresult);
                        list.add(myresult);


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

        };



        return  list;
    }
    public static ArrayList<String> get_deprt_machine(Activity activity,String myusername,String mypassword,String key_item){

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String namespace2 = Urls.get_depart_machine_namespace;
        String url2 = Urls.get_depart_machine_url;
        String method_name2 = "ReadMultiple";
        String soap_action2 = namespace2 + ":" + method_name2;
        SoapObject result= null;
        String myresult = "";

        String code,name;

        ArrayList<String> list = new ArrayList<>();

        try {
            SoapObject request = new SoapObject(namespace2,method_name2);
            SoapObject filter = new SoapObject(namespace2,"filter");
            filter.addProperty("Field","Global_Dimension_No");
            filter.addProperty("Criteria",key_item);
            request.addSoapObject(filter);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            NtlmTransport ntlm = new NtlmTransport();
            ntlm.debug = true;
            ntlm.setCredentials(url2, myusername, mypassword, domain, "");
            ntlm.call(soap_action2, envelope);
            try {
                result= (SoapObject) envelope.getResponse();
                for (int i = 0; i < result.getPropertyCount(); i++) {

                    SoapObject result2 = (SoapObject) result.getProperty(i);
                    try {
                        name = String.valueOf(result2.getProperty("Name"));
                        code = String.valueOf(result2.getProperty("Code"));

                        myresult = code + " " + name;
                        Log.i("code_name",myresult);
                        list.add(myresult);


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

        };



        return  list;
    }

}