package ar.com.svc.qr.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Component
public class QRCodeUtils {

    private QRCodeWriter qrCodeWriter;

    private MultiFormatReader reader;

    private ObjectMapper objectMapper;

    private static QRCodeUtils instance;

    private QRCodeUtils() {
        this.qrCodeWriter = new QRCodeWriter();
        this.reader = new MultiFormatReader();
        this.objectMapper = new ObjectMapper();
    }

    public static QRCodeUtils getInstance(){
        if(instance == null){
            instance = new QRCodeUtils();
        }
        return instance;
    }

    @Autowired
    public static void setQRCodeWriter(QRCodeWriter codeWriter){
        QRCodeUtils.getInstance().qrCodeWriter = codeWriter;
    }

    private static RandomStringGenerator generator = new RandomStringGenerator.Builder()
            .withinRange('0', 'z')
            .filteredBy(Character::isLetter, Character::isDigit)
            .build();

    /**
     * This method takes the text to be encoded, the width and height of the QR Code and returns the QR Code.
     *
     * @param text   Text to encode
     * @param size  The QR code width and height
     * @return QR code
     * @throws WriterException
     * @throws IOException
     */
    public static BitMatrix generateQRCode(String text, int size) throws WriterException, IOException {
        return QRCodeUtils.getInstance().qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, size, size);
    }

    /**
     * This method takes the text to be encoded, the width and height of the QR Code,
     * and returns the QR Code PNG Image.
     *
     * @param text   Text to encode
     * @param size  The QR code width and height
     * @return QR Code PNG Image
     * @throws WriterException
     * @throws IOException
     */
    public static ByteArrayOutputStream generateQRCodeImageStream(String text, int size) throws WriterException, IOException {
        try(ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream()) {
            return (ByteArrayOutputStream) generateQRCodeImage(pngOutputStream, text, size);
        }
    }

    public static FileOutputStream generateQRCodeImageFileStream(String text, int size) throws WriterException, IOException {
        try(FileOutputStream pngOutputStream = new FileOutputStream("qr.png")) {
            return (FileOutputStream) generateQRCodeImage(pngOutputStream, text, size);
        }
    }

    public static OutputStream generateQRCodeImage(OutputStream os, String text, int size) throws IOException, WriterException {
        BitMatrix bitMatrix = generateQRCode(text, size);
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", os);
        return os;
    }

    public static String generateQRCodeBase64(String text, int size) throws WriterException, IOException {
        return b64EncodeImage(generateQRCodeImageStream(text, size));
    }


    /**
     * This method takes the text to be encoded, the width and height of the QR Code,
     * and returns the QR Code PNG Image byte array.
     *
     * @param text  Text to encode
     * @param size  The QR code width and height
     * @return QR Code PNG Image bytes
     * @throws WriterException
     * @throws IOException
     */
    public static byte[] generateQRCodeImageBytes(String text, int size) throws WriterException, IOException {
        return generateQRCodeImageStream(text, size).toByteArray();
    }

    /**
     * Base64 image encoder
     *
     * @param image ByteArrayOutputStream Image
     * @return Base64 String Image
     */
    public static String b64EncodeImage(ByteArrayOutputStream image) {
        return Base64.getEncoder().encodeToString(image.toByteArray());
    }

    /**
     * Base64 String image decoder
     *
     * @param base64Image B64 String Image encode
     * @return The image decoded
     * @throws IOException
     */
    public static ByteArrayInputStream b64DecodeImage(String base64Image) throws IOException {
        byte[] imageByteArray = Base64.getDecoder().decode(base64Image);
        ByteArrayInputStream bais = new ByteArrayInputStream(imageByteArray);
        return bais;
    }

    public static byte[] getImage(ByteArrayInputStream stream) throws IOException {
        BufferedImage image = ImageIO.read(stream);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        return baos.toByteArray();
    }

    /**
     * Read QR Code and returns string value
     * @param qrCode The QR Code to read
     * @return String value
     * @throws IOException
     * @throws FormatException
     * @throws ChecksumException
     * @throws NotFoundException
     */
    public static String readQRCodeImage(ByteArrayInputStream qrCode) throws IOException, FormatException, ChecksumException, NotFoundException {
        BufferedImage barCodeBufferedImage = ImageIO.read(qrCode);
        LuminanceSource source = new BufferedImageLuminanceSource(barCodeBufferedImage);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Result result = QRCodeUtils.getInstance().reader.decode(bitmap);
        return result.getText();
    }

    public static String getQRCodeData(String base64Image) throws IOException, FormatException, ChecksumException, NotFoundException {
        return readQRCodeImage(b64DecodeImage(base64Image));
    }

    /**
     * Generates UUID
     *
     * @return Unique ID
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    public static String generateUUID() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return generator.generate(16).toUpperCase();
    }

    public static Map<String, Object> json2map (String json) throws IOException {
        return QRCodeUtils.getInstance().objectMapper.readValue(json, HashMap.class);
    }
}
