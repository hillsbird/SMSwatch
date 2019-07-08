package com.wei.smswatch;

public class SmsInfo {
    public String id = "";
    public String thread_id = "";
    public String smsAddress = "";
    public String smsBody = "";
    public String read = "";
    public int action = 0;

    @Override
    public String toString() {
        return "SmsInfo [id=" + id + ", thread_id=" + thread_id
                + ", smsAddress=" + smsAddress + ", smsBody=" + smsBody
                + ", read=" + read + ", action=" + action + "]";
    }
}
