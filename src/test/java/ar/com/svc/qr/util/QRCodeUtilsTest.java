package ar.com.svc.qr.util;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.assertTrue;

public class QRCodeUtilsTest {

    @Test
    public void getValidUUID() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String result = QRCodeUtils.generateUUID();
        assertTrue(result.length() == 16);
    }
}