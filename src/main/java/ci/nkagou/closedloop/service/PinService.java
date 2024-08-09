package ci.nkagou.closedloop.service;

public interface PinService  {

    String hashPinCode(String originalPinCode);

    Boolean checkPinCode(String originalPinCode, String hashPinCode);
}
