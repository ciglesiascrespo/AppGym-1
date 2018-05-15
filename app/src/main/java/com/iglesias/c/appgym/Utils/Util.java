package com.iglesias.c.appgym.Utils;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by Ciglesias on 14/05/2018.
 */

public class Util {
    public static byte[] intToByteArray (final int integer) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);
        dos.writeInt(integer);
        dos.flush();
        return bos.toByteArray();
    }
}
