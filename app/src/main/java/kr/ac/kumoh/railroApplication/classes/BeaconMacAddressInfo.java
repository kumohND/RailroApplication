package kr.ac.kumoh.railroApplication.classes;

/**
 * Created by Woocha on 2015-09-02.
 */
public class BeaconMacAddressInfo{
    String mac_Address;
    String setStation;
    int prePicture;
    int postPicture;
    short picutre_change_flag = 0;

    public BeaconMacAddressInfo(String mac_Address, String setStation,int prePicture,int postPicture) {
        this.mac_Address = mac_Address;
        this.setStation = setStation;
        this.prePicture = prePicture;
        this.postPicture = postPicture;
    }

    public String getMac_Address() {
        return mac_Address;
    }

    public String getSetStation() {
        return setStation;
    }

    public int getPicture()
    {
        if(picutre_change_flag == 0)
            return prePicture;
        else
            return postPicture;
    }

    public void FlagOn()
    {
        picutre_change_flag = 1;
    }
}