package com.telusko.rewards.util;

import java.util.Base64;

public class Util
{
    public String decryptPwd(String encPwd)
    {
        Base64.Decoder decoder = Base64.getDecoder();
        return new String(decoder.decode(encPwd));
    }
}
