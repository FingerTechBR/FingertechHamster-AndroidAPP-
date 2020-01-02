package com.fingertech.fingertechcapture;

import android.content.Context;
import android.util.Log;

import com.nitgen.SDK.AndroidBSP.NBioBSPJNI;
import com.nitgen.SDK.AndroidBSP.StaticVals;
import com.nitgen.SDK.AndroidBSP.Trace;


import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Nitgen  {

    private NBioBSPJNI bsp;
    private NBioBSPJNI.Export exportEngine;
    private static NBioBSPJNI.IndexSearch indexSearch;
    private byte[] byTemplate1;
    private byte[] byTemplate2;

    private byte[] byCapturedRaw1;
    private int nCapturedRawWidth1;
    private int nCapturedRawHeight1;

    private byte[] byCapturedRaw2;
    private int nCapturedRawWidth2;
    private int nCapturedRawHeight2;
    private View view;

    private int QUALITY_LIMIT = 60;
    private int nFIQ = 0;
    private String msg = "";

    public static boolean status = false;

    private boolean bAutoOn = false;
    private Context context;

    public Nitgen(View view, Context context) {
        this.view = view;
        this.context = context;
        initData();
    }

    public void setView(View v){
        this.view =v;

    }


    //inicializa o leitor e adiciona serial ao sdk
    public void initData() {
        NBioBSPJNI.CURRENT_PRODUCT_ID = 0;
        if (bsp == null) {
            bsp = new NBioBSPJNI("010701-613E5C7F4CC7C4B0-72E340B47E034015",context, mCallback);
            String msg;
            if (bsp.IsErrorOccured())
                msg = "NBioBSP Error: " + bsp.GetErrorCode();
            else {
                msg = "Versão SDK: " + bsp.GetVersion();
                exportEngine = bsp.new Export();
                indexSearch = bsp.new IndexSearch();
            }
            view.onVersion(msg);
        }
    }

    public void clearData(){
        if (bsp != null) {

            bsp.dispose();
            bsp = null;
            status = false;
        }
    }



    // retorno dos méetodos
    NBioBSPJNI.CAPTURE_CALLBACK mCallback = new NBioBSPJNI.CAPTURE_CALLBACK() {
        public void OnDisConnected() {
            NBioBSPJNI.CURRENT_PRODUCT_ID = 0;
            NBioBSPJNI.isConnected = false;

            String message = "NBioBSP Disconnected: " + bsp.GetErrorCode();
            view.onDeviceDisconnected();
            view.onDeviceMessage(message);
            view.hideLoading();
        }

        //método para se conectar ao dispositivo
        public void OnConnected() {
            Trace.d("OnConnected");
            NBioBSPJNI.isConnected = true;

            String message = "Device Open Success : " + bsp.GetErrorCode();
            view.onDeviceConnected();
            view.onDeviceMessage(message);
            view.hideLoading();
        }



        //faz a captura gera a imagem e diz a qualidade da imagem
        public int OnCaptured(NBioBSPJNI.CAPTURED_DATA capturedData) {
            view.onDeviceMessage("IMAGE Quality: " + capturedData.getImageQuality());
            view.onCapture(capturedData);

            // quality : 40~100
            if (capturedData.getImageQuality() >= QUALITY_LIMIT) {
                view.hideLoading();
                return NBioBSPJNI.ERROR.NBioAPIERROR_USER_CANCEL;
            } else if (capturedData.getDeviceError() != NBioBSPJNI.ERROR.NBioAPIERROR_NONE) {
                view.hideLoading();
                return capturedData.getDeviceError();
            } else {
                return NBioBSPJNI.ERROR.NBioAPIERROR_NONE;
            }
        }
    };

    private void getDeviceID() {
        ByteBuffer deviceId = ByteBuffer.allocate(StaticVals.wLength_GET_ID);
        deviceId.order(ByteOrder.BIG_ENDIAN);
        bsp.getDeviceID(deviceId.array());

        if (bsp.IsErrorOccured()) {
            Trace.d("getDeviceID Failure");
        } else {
            Trace.d("getDeviceID Success");
        }
    }

    private void setValue(byte[] value) {
        bsp.setValue(value);

        if (bsp.IsErrorOccured()) {
            Trace.d("setValue Failure");
        } else {
            Trace.d("setValue Success");
        }
    }



    private void getValue() {
        byte[] value = new byte[StaticVals.wLength_GET_VALUE];
        bsp.getValue(value);

        if (bsp.IsErrorOccured()) {
            Trace.d("getValue Failure");
        } else {
            Trace.d("getValue Success");
        }

    }

    private void getInitInfo() {
        NBioBSPJNI.INIT_INFO_0 init_info_0 = bsp.new INIT_INFO_0();
        bsp.GetInitInfo(init_info_0);

        if (bsp.IsErrorOccured()) {
            Trace.d("getInitInfo Fail");
        } else {
            Trace.d("getInitInfo " + init_info_0.DefaultTimeout);
        }
    }


    //set init info
    private void setInitInfo() {
        NBioBSPJNI.INIT_INFO_0 init_info_0 = bsp.new INIT_INFO_0();
        init_info_0.DefaultTimeout = 11000;
        init_info_0.EnrollImageQuality = 50;
        init_info_0.IdentifyImageQuality = 50;
        init_info_0.MaxFingersForEnroll = 10;
        init_info_0.SamplesPerFinger = 2;
        init_info_0.SecurityLevel = NBioBSPJNI.FIR_SECURITY_LEVEL.LOW;
        init_info_0.VerifyImageQuality = 30;
        bsp.SetInitInfo(init_info_0);

        if (bsp.IsErrorOccured()) {
            Trace.d("setInitInfo Fail");
        } else {
            Trace.d("setInitInfo " + init_info_0.DefaultTimeout);
        }
    }

    //deetermina a qualidade ao iniciar
    private void setCaptureQualityInfo() {
        NBioBSPJNI.CAPTURE_QUALITY_INFO mCAPTURE_QUALITY_INFO = bsp.new CAPTURE_QUALITY_INFO();
        mCAPTURE_QUALITY_INFO.EnrollCoreQuality = 71;
        mCAPTURE_QUALITY_INFO.EnrollFeatureQuality = 30;
        mCAPTURE_QUALITY_INFO.VerifyCoreQuality = 70;
        mCAPTURE_QUALITY_INFO.VerifyFeatureQuality = 30;
        bsp.SetCaptureQualityInfo(mCAPTURE_QUALITY_INFO);

        if (bsp.IsErrorOccured()) {
            Trace.d("SetCaptureQualityInfo Fail");
        } else {
            Trace.d("SetCaptureQualityInfo Success " + mCAPTURE_QUALITY_INFO.EnrollCoreQuality);
        }
    }

    //get quality info
    private void getCaptureQualityInfo() {
        NBioBSPJNI.CAPTURE_QUALITY_INFO mCAPTURE_QUALITY_INFO = bsp.new CAPTURE_QUALITY_INFO();
        bsp.GetCaptureQualityInfo(mCAPTURE_QUALITY_INFO);

        if (bsp.IsErrorOccured()) {
            Trace.d("getCaptureQualityInfo Fail");
        } else {
            Trace.d("getCaptureQualityInfo " + mCAPTURE_QUALITY_INFO.EnrollCoreQuality);
        }
    }

    //fechar o auto on
    public void onCaptureCancel(){
        bAutoOn = false;
        if(bsp != null){
            bsp.CaptureCancel();
        }
    }



    //capture
    public void onCapture1(int timeout) {
        NBioBSPJNI.FIR_HANDLE hCapturedFIR, hAuditFIR;
        NBioBSPJNI.CAPTURED_DATA capturedData;

        hCapturedFIR = bsp.new FIR_HANDLE();
        hAuditFIR = bsp.new FIR_HANDLE();
        capturedData = bsp.new CAPTURED_DATA();
        bsp.Capture(NBioBSPJNI.FIR_PURPOSE.ENROLL, hCapturedFIR, timeout, hAuditFIR, capturedData);

        if (bsp.IsErrorOccured()) {
            msg = "NBioBSP Capture Error: " + bsp.GetErrorCode();
        } else {
            NBioBSPJNI.INPUT_FIR inputFIR;
            inputFIR = bsp.new INPUT_FIR();

            // Make ISO 19794-2 data
            NBioBSPJNI.Export.DATA exportData;
            inputFIR.SetFIRHandle(hCapturedFIR);
            exportData = exportEngine.new DATA();
            exportEngine.ExportFIR(inputFIR, exportData, NBioBSPJNI.EXPORT_MINCONV_TYPE.OLD_FDA);

            if (bsp.IsErrorOccured()) {
                msg = "NBioBSP ExportFIR Error: " + bsp.GetErrorCode();
                view.onInforMessage(msg);
                view.showToast(msg);
                return;
            }

            if (byTemplate1 != null)
                byTemplate1 = null;

            byTemplate1 = new byte[exportData.FingerData[0].Template[0].Data.length];
            byTemplate1 = exportData.FingerData[0].Template[0].Data;

            // Make Raw Image data
            NBioBSPJNI.Export.AUDIT exportAudit;
            inputFIR.SetFIRHandle(hAuditFIR);
            exportAudit = exportEngine.new AUDIT();
            exportEngine.ExportAudit(inputFIR, exportAudit);

            if (bsp.IsErrorOccured()) {
                msg = "NBioBSP ExportAudit Error: " + bsp.GetErrorCode();
                view.onInforMessage(msg);
                view.showToast(msg);
                return;
            }

            byCapturedRaw1 = new byte[exportAudit.FingerData[0].Template[0].Data.length];
            byCapturedRaw1 = exportAudit.FingerData[0].Template[0].Data;

            nCapturedRawWidth1 = exportAudit.ImageWidth;
            nCapturedRawHeight1 = exportAudit.ImageHeight;

            msg = "First Capture Success";

            NBioBSPJNI.NFIQInfo info = bsp.new NFIQInfo();
            info.pRawImage = byCapturedRaw1;
            info.nImgWidth = nCapturedRawWidth1;
            info.nImgHeight = nCapturedRawHeight1;

            bsp.getNFIQInfoFromRaw(info);

            if (bsp.IsErrorOccured()) {
                msg = "NBioBSP getNFIQInfoFromRaw Error: " + bsp.GetErrorCode();
                view.onInforMessage(msg);
                view.showToast(msg);
                return;
            }
            nFIQ = info.pNFIQ;
            // ISO 19794-4
            NBioBSPJNI.ISOBUFFER ISOBuffer = bsp.new ISOBUFFER();
            bsp.ExportRawToISOV1(exportAudit, ISOBuffer, false, NBioBSPJNI.COMPRESS_MODE.NONE);
            if (bsp.IsErrorOccured()) {
                msg = "NBioBSP ExportRawToISOV1 Error: " + bsp.GetErrorCode();
                view.onInforMessage(msg);
                view.showToast(msg);
                return;
            }

            NBioBSPJNI.NIMPORTRAWSET rawSet = bsp.new NIMPORTRAWSET();
            bsp.ImportISOToRaw(ISOBuffer, rawSet);

            if (bsp.IsErrorOccured()) {
                msg = "NBioBSP ImportISOToRaw Error: " + bsp.GetErrorCode();
                view.onInforMessage(msg);
                view.showToast(msg);
                return;
            }

            for (int i = 0; i < rawSet.Count; i++) {
                msg += "  DataLen: " + rawSet.RawData[i].Data.length + "  " +
                        "FingerID: " + rawSet.RawData[i].FingerID + "  " +
                        "Width: " + rawSet.RawData[i].ImgWidth + "  " +
                        "Height: " + rawSet.RawData[i].ImgHeight + "  ";
            }

            byCapturedRaw1 = new byte[rawSet.RawData[0].Data.length];
            byCapturedRaw1 = rawSet.RawData[0].Data;

            nCapturedRawWidth1 = rawSet.RawData[0].ImgWidth;
            nCapturedRawHeight1 = rawSet.RawData[0].ImgHeight;



            NBioBSPJNI.FIR_TEXTENCODE digitalstring = bsp.new FIR_TEXTENCODE();
            bsp.GetTextFIRFromHandle(hCapturedFIR,digitalstring);
            view.digitalText(digitalstring.TextFIR);
            //Log.i("oncapture", "onCapture1: "+digitalstring.TextFIR);


            view.onInforMessage(msg);
        }

        view.onInforMessage(msg + ",NFIQ:" + nFIQ);
        if (byTemplate1 != null && byTemplate2 != null) {
            view.setISOButton(true);
        } else {
            view.setISOButton(false);
        }

        if (byCapturedRaw1 != null && byCapturedRaw2 != null) {
            view.setRAWButton(true);
        } else {
            view.setRAWButton(false);
        }
    }




    public void onCapture2(int timeout) {
        NBioBSPJNI.FIR_HANDLE hCapturedFIR, hAuditFIR;
        NBioBSPJNI.CAPTURED_DATA capturedData;

        hCapturedFIR = bsp.new FIR_HANDLE();
        hAuditFIR = bsp.new FIR_HANDLE();
        capturedData = bsp.new CAPTURED_DATA();
        bsp.Capture(NBioBSPJNI.FIR_PURPOSE.ENROLL, hCapturedFIR, timeout, hAuditFIR, capturedData);

        if (bsp.IsErrorOccured()) {
            msg = "NBioBSP Capture Error: " + bsp.GetErrorCode();
        } else {
            NBioBSPJNI.INPUT_FIR inputFIR;
            inputFIR = bsp.new INPUT_FIR();

            // Make ISO 19794-2 data
            NBioBSPJNI.Export.DATA exportData;
            inputFIR.SetFIRHandle(hCapturedFIR);
            exportData = exportEngine.new DATA();
            exportEngine.ExportFIR(inputFIR, exportData, NBioBSPJNI.EXPORT_MINCONV_TYPE.OLD_FDA);

            if (bsp.IsErrorOccured()) {
                msg = "NBioBSP ExportFIR Error: " + bsp.GetErrorCode();
                view.onInforMessage(msg);
                view.showToast(msg);
                return;
            }

            if (byTemplate2 != null)
                byTemplate2 = null;

            byTemplate2 = new byte[exportData.FingerData[0].Template[0].Data.length];
            byTemplate2 = exportData.FingerData[0].Template[0].Data;

            // Make Raw Image data
            NBioBSPJNI.Export.AUDIT exportAudit;
            inputFIR.SetFIRHandle(hAuditFIR);
            exportAudit = exportEngine.new AUDIT();
            exportEngine.ExportAudit(inputFIR, exportAudit);

            if (bsp.IsErrorOccured()) {
                msg = "NBioBSP ExportAudit Error: " + bsp.GetErrorCode();
                view.onInforMessage(msg);
                view.showToast(msg);
                return;
            }

            byCapturedRaw2 = new byte[exportAudit.FingerData[0].Template[0].Data.length];
            byCapturedRaw2 = exportAudit.FingerData[0].Template[0].Data;

            nCapturedRawWidth2 = exportAudit.ImageWidth;
            nCapturedRawHeight2 = exportAudit.ImageHeight;

            msg = "First Capture Success";

            NBioBSPJNI.NFIQInfo info = bsp.new NFIQInfo();
            info.pRawImage = byCapturedRaw1;
            info.nImgWidth = nCapturedRawWidth1;
            info.nImgHeight = nCapturedRawHeight1;

            bsp.getNFIQInfoFromRaw(info);

            if (bsp.IsErrorOccured()) {
                msg = "NBioBSP getNFIQInfoFromRaw Error: " + bsp.GetErrorCode();
                view.onInforMessage(msg);
                view.showToast(msg);
                return;
            }
            nFIQ = info.pNFIQ;
            // ISO 19794-4
            NBioBSPJNI.ISOBUFFER ISOBuffer = bsp.new ISOBUFFER();
            bsp.ExportRawToISOV1(exportAudit, ISOBuffer, false, NBioBSPJNI.COMPRESS_MODE.NONE);
            if (bsp.IsErrorOccured()) {
                msg = "NBioBSP ExportRawToISOV1 Error: " + bsp.GetErrorCode();
                view.onInforMessage(msg);
                view.showToast(msg);
                return;
            }


            NBioBSPJNI.NIMPORTRAWSET rawSet = bsp.new NIMPORTRAWSET();
            bsp.ImportISOToRaw(ISOBuffer, rawSet);

            if (bsp.IsErrorOccured()) {
                msg = "NBioBSP ImportISOToRaw Error: " + bsp.GetErrorCode();
                view.onInforMessage(msg);
                view.showToast(msg);
                return;
            }

            for (int i = 0; i < rawSet.Count; i++) {
                msg += "  DataLen: " + rawSet.RawData[i].Data.length + "  " +
                        "FingerID: " + rawSet.RawData[i].FingerID + "  " +
                        "Width: " + rawSet.RawData[i].ImgWidth + "  " +
                        "Height: " + rawSet.RawData[i].ImgHeight + "  ";
            }

            byCapturedRaw2 = new byte[rawSet.RawData[0].Data.length];
            byCapturedRaw2 = rawSet.RawData[0].Data;

            nCapturedRawWidth2 = rawSet.RawData[0].ImgWidth;
            nCapturedRawHeight2 = rawSet.RawData[0].ImgHeight;


            view.onInforMessage(msg);
        }

        view.onInforMessage(msg + ",NFIQ:" + nFIQ);
        if (byTemplate1 != null && byTemplate2 != null) {
            view.setISOButton(true);
        } else {
            view.setISOButton(false);
        }

        if (byCapturedRaw1 != null && byCapturedRaw2 != null) {
            view.setRAWButton(true);
        } else {
            view.setRAWButton(false);
        }
    }

    Thread authCaptureThread;



    //auto on
    public void onAuthCapture1(){

        try {
            if(authCaptureThread != null && authCaptureThread.isAlive()){
                bAutoOn = false;
                authCaptureThread.join();
            }
            bAutoOn = true;
            authCaptureThread = new Thread(() -> {
                while (bAutoOn) {
                    //Log.e("thead", "thread1");
                    byte[] bFingerExist = new byte[1];
                    bFingerExist[0] = 0;
                    bsp.CheckFinger(bFingerExist);


                    if (bFingerExist[0] == 1) {
                        Log.e("thead", "capture");
                        onCapture1(10000);

                    }
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

            });
            authCaptureThread.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setAuto(boolean isAuto){
        bAutoOn = isAuto;
    }

    public void onAuthCapture2(){
        try {
            if(authCaptureThread != null && authCaptureThread.isAlive()){
                bAutoOn = false;
                authCaptureThread.join();
            }
            bAutoOn = true;
            authCaptureThread = new Thread(() -> {
                while (bAutoOn) {
                    byte[] bFingerExist = new byte[1];
                    bFingerExist[0] = 0;
                    bsp.CheckFinger(bFingerExist);

                    if (bFingerExist[0] == 1) {
                        onCapture2(1000);
                    }
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

            });
            authCaptureThread.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    //abrir dispositivo
    public void openDevice(){
        if (bsp.isConnected) {
            status = false;
            bsp.releaseDevice();
        } else {
            view.showLoading();
            status = true;
            bsp.OpenDevice();
        }
    }

    public void verifyIso(){
        String msg = "";

        if (byTemplate1 != null && byTemplate2 != null) {
            NBioBSPJNI.FIR_HANDLE hLoadFIR1, hLoadFIR2;

            hLoadFIR1 = bsp.new FIR_HANDLE();
            exportEngine.ImportFIR(byTemplate1, byTemplate1.length, NBioBSPJNI.EXPORT_MINCONV_TYPE.OLD_FDA, hLoadFIR1);
            if (bsp.IsErrorOccured()) {
                msg = "Template NBioBSP ImportFIR Error: " + bsp.GetErrorCode();
                view.onInforMessage(msg);
                return;
            }

            hLoadFIR2 = bsp.new FIR_HANDLE();
            exportEngine.ImportFIR(byTemplate2, byTemplate2.length, NBioBSPJNI.EXPORT_MINCONV_TYPE.OLD_FDA, hLoadFIR2);
            if (bsp.IsErrorOccured()) {
                hLoadFIR1.dispose();
                msg = "Template NBioBSP ImportFIR Error: " + bsp.GetErrorCode();
                view.onInforMessage(msg);
                return;
            }
            Boolean bResult = new Boolean(false);
            // Verify Match
            NBioBSPJNI.INPUT_FIR inputFIR1, inputFIR2;
            inputFIR1 = bsp.new INPUT_FIR();
            inputFIR2 = bsp.new INPUT_FIR();

            inputFIR1.SetFIRHandle(hLoadFIR1);
            inputFIR2.SetFIRHandle(hLoadFIR2);

            bsp.VerifyMatch(inputFIR1, inputFIR2, bResult, null);

            if (bsp.IsErrorOccured()) {
                msg = "Template NBioBSP VerifyMatch Error: " + bsp.GetErrorCode();
            } else {
                if (bResult) {
                    msg = "Template VerifyMatch Successed";
                } else {
                    msg = "Template VerifyMatch Failed";
                }
            }

            hLoadFIR1.dispose();
            hLoadFIR2.dispose();
        } else {
            msg = "Can not find captured data";
        }

        view.onInforMessage(msg);
    }

    public void verifyRaw(){
        String msg = "";
        if (byCapturedRaw1 != null && byCapturedRaw2 != null) {
            NBioBSPJNI.FIR_HANDLE hLoadAudit1, hLoadAudit2;
            NBioBSPJNI.FIR_HANDLE hPorcessedFIR1, hPorcessedFIR2;
            {
                NBioBSPJNI.Export.AUDIT importAudit = exportEngine.new AUDIT();

                importAudit.FingerNum = (byte) 1;
                importAudit.SamplesPerFinger = 1;
                importAudit.ImageWidth = nCapturedRawWidth1;
                importAudit.ImageHeight = nCapturedRawHeight1;
                importAudit.FingerData = new NBioBSPJNI.Export.FINGER_DATA[importAudit.FingerNum];
                importAudit.FingerData[0] = exportEngine.new FINGER_DATA();
                importAudit.FingerData[0].Template = new NBioBSPJNI.Export.TEMPLATE_DATA[importAudit.SamplesPerFinger];
                importAudit.FingerData[0].Template[0] = exportEngine.new TEMPLATE_DATA();
                importAudit.FingerData[0].FingerID = NBioBSPJNI.FINGER_ID.UNKNOWN;
                importAudit.FingerData[0].Template[0].Data = new byte[byCapturedRaw1.length];
                importAudit.FingerData[0].Template[0].Data = byCapturedRaw1;

                hLoadAudit1 = bsp.new FIR_HANDLE();

                exportEngine.ImportAudit(importAudit, hLoadAudit1);

                if (bsp.IsErrorOccured()) {
                    msg = "RawData NBioBSP ImportAudit Error: " + bsp.GetErrorCode();
                    view.onInforMessage(msg);
                    return;
                }

                hPorcessedFIR1 = bsp.new FIR_HANDLE();
                NBioBSPJNI.INPUT_FIR inputFIR = bsp.new INPUT_FIR();
                inputFIR.SetFIRHandle(hLoadAudit1);
                bsp.Process(inputFIR, hPorcessedFIR1);

                if (bsp.IsErrorOccured()) {
                    hLoadAudit1.dispose();
                    msg = "RawData NBioBSP Process Error: " + bsp.GetErrorCode();
                    view.onInforMessage(msg);
                    return;
                }
            }
            {
                NBioBSPJNI.Export.AUDIT importAudit = exportEngine.new AUDIT();

                importAudit.FingerNum = (byte) 1;
                importAudit.SamplesPerFinger = 1;
                importAudit.ImageWidth = nCapturedRawWidth2;
                importAudit.ImageHeight = nCapturedRawHeight2;
                importAudit.FingerData = new NBioBSPJNI.Export.FINGER_DATA[importAudit.FingerNum];
                importAudit.FingerData[0] = exportEngine.new FINGER_DATA();
                importAudit.FingerData[0].Template = new NBioBSPJNI.Export.TEMPLATE_DATA[importAudit.SamplesPerFinger];
                importAudit.FingerData[0].Template[0] = exportEngine.new TEMPLATE_DATA();
                importAudit.FingerData[0].FingerID = NBioBSPJNI.FINGER_ID.UNKNOWN;
                importAudit.FingerData[0].Template[0].Data = new byte[byCapturedRaw2.length];
                importAudit.FingerData[0].Template[0].Data = byCapturedRaw2;

                hLoadAudit2 = bsp.new FIR_HANDLE();

                exportEngine.ImportAudit(importAudit, hLoadAudit2);

                if (bsp.IsErrorOccured()) {
                    hLoadAudit1.dispose();
                    msg = "RawData NBioBSP ImportAudit Error: " + bsp.GetErrorCode();
                    view.onInforMessage(msg);
                    return;
                }
            }

            NBioBSPJNI.INPUT_FIR inputFIR = bsp.new INPUT_FIR();
            hPorcessedFIR2 = bsp.new FIR_HANDLE();
            inputFIR.SetFIRHandle(hLoadAudit2);
            bsp.Process(inputFIR, hPorcessedFIR2);

            if (bsp.IsErrorOccured()) {
                hLoadAudit1.dispose();
                hPorcessedFIR1.dispose();
                hLoadAudit2.dispose();
                msg = "RawData NBioBSP Process Error: " + bsp.GetErrorCode();
                view.onInforMessage(msg);
                return;
            }

            NBioBSPJNI.INPUT_FIR inputFIR1, inputFIR2;
            Boolean bResult = new Boolean(false);

            inputFIR1 = bsp.new INPUT_FIR();
            inputFIR2 = bsp.new INPUT_FIR();

            inputFIR1.SetFIRHandle(hPorcessedFIR1);
            inputFIR2.SetFIRHandle(hPorcessedFIR2);

            bsp.VerifyMatch(inputFIR1, inputFIR2, bResult, null);

            if (bsp.IsErrorOccured()) {
                msg = "RawData NBioBSP VerifyMatch Error: " + bsp.GetErrorCode();
            } else {
                if (bResult) {
                    msg = "RawData VerifyMatch Successed";
                } else {
                    msg = "RawData VerifyMatch Failed";
                }
            }

            hLoadAudit1.dispose();
            hLoadAudit2.dispose();
            hPorcessedFIR1.dispose();
            hPorcessedFIR2.dispose();

        } else {
            msg = "Can not find captured data";
        }
        view.onInforMessage(msg);
    }

    /**
     * NScan-FM do not permitted
     */
    public void setBrightness(String brightnessStr){
        if (!"".equals(brightnessStr)) {
            bsp.SetBrightness(Integer.parseInt(brightnessStr));
        }
    }


    //remover usario do array do objeto indexsearch
    public void removeUser(String id){
        indexSearch.RemoveUser(Integer.parseInt(id));
        if (bsp.IsErrorOccured()) {
            view.showToast(id + " Delete Failure");
        } else {
            view.showToast(id + " Delete Success");
        }
    }




    //identify faz uma captura e compara com a ultima captura
    public void identify(int timeout){
        NBioBSPJNI.FIR_HANDLE hCapturedFIR, hAuditFIR;
        NBioBSPJNI.CAPTURED_DATA capturedData;
        hCapturedFIR = bsp.new FIR_HANDLE();
        hAuditFIR = bsp.new FIR_HANDLE();
        capturedData = bsp.new CAPTURED_DATA();

        bsp.Capture(NBioBSPJNI.FIR_PURPOSE.ENROLL, hCapturedFIR, timeout, hAuditFIR, capturedData);


        if (bsp.IsErrorOccured()) {
            msg = "NBioBSP Capture Error: " + bsp.GetErrorCode();
        } else {
            NBioBSPJNI.INPUT_FIR inputFIR;
            inputFIR = bsp.new INPUT_FIR();
            inputFIR.SetFIRHandle(hCapturedFIR);

            NBioBSPJNI.IndexSearch.FP_INFO fpInfo = indexSearch.new FP_INFO();
            indexSearch.Identify(inputFIR, 1, fpInfo, 2000);

            if (fpInfo.ID != 0) {

                view.resultadoIndexSearch(fpInfo.ID);
            } else {
                view.resultadoIndexSearch(-1);;
            }
        }
    }

    public void onAddFIR(int timeout, String id) {
        NBioBSPJNI.FIR_HANDLE hCapturedFIR, hAuditFIR;
        NBioBSPJNI.CAPTURED_DATA capturedData;
        hCapturedFIR = bsp.new FIR_HANDLE();
        hAuditFIR = bsp.new FIR_HANDLE();
        capturedData = bsp.new CAPTURED_DATA();

        bsp.Capture(NBioBSPJNI.FIR_PURPOSE.ENROLL, hCapturedFIR, timeout, hAuditFIR, capturedData);

        if (bsp.IsErrorOccured()) {
            msg = "NBioBSP Capture Error: " + bsp.GetErrorCode();
        } else {
            NBioBSPJNI.INPUT_FIR inputFIR;
            inputFIR = bsp.new INPUT_FIR();
            inputFIR.SetFIRHandle(hCapturedFIR);
            NBioBSPJNI.IndexSearch.SAMPLE_INFO sampleInfo = indexSearch.new SAMPLE_INFO();

            indexSearch.AddFIR(inputFIR, Integer.parseInt(id), sampleInfo);
            if (bsp.IsErrorOccured()) {
                //view.showToast(id + " Add Failure");
            } else {
                //view.showToast(id + " Add Success");
            }
        }
    }


    public void onAddFIRstring(String digital, long id) {
        Log.i("onAddFIRstring", "fir onadd e id"+digital+" "+ id);
        NBioBSPJNI.FIR_TEXTENCODE textencode = bsp.new FIR_TEXTENCODE();
        if (bsp.IsErrorOccured()) {
            msg = "NBioBSP Capture Error: " + bsp.GetErrorCode();
        } else {
            textencode.TextFIR = digital;
            Log.i("onAddFIRstring", "usuario  add: "+textencode.TextFIR);
            Log.i("onAddFIRstring", "usuario  add: digital addfir "+digital);
            NBioBSPJNI.INPUT_FIR inputFIR;
            inputFIR = bsp.new INPUT_FIR();
            inputFIR.SetTextFIR(textencode);

            NBioBSPJNI.IndexSearch.SAMPLE_INFO sampleInfo = indexSearch.new SAMPLE_INFO();

            indexSearch.AddFIR(inputFIR, (int) id, sampleInfo);
            if (bsp.IsErrorOccured()) {
                //view.showToast(id + " Add Failure");
            } else {
                //view.showToast(id + " Add Success");

            }
        }
    }



    public interface View {


        void onDeviceConnected();
        void onDeviceDisconnected();
        void onCapture(NBioBSPJNI.CAPTURED_DATA capturedData);
        void onDeviceMessage(String msg);
        void onInforMessage(String msg);
        void onVersion(String msg);
        void showToast(String msg);
        void showLoading();
        void hideLoading();
        void setISOButton(boolean enable);
        void setRAWButton(boolean enable);
        void digitalText(String digital);
        void resultadoIndexSearch(long id);

    }
}
