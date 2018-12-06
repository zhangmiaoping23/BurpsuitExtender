package tool;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.Key;
import java.security.MessageDigest;
import java.security.PublicKey;
import java.security.Security;
import java.security.cert.CertificateFactory;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipInputStream;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.util.encoders.Base64;

public class Tool {
	public static void sleep(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			//Log.e(Config.TAG, "sleep error", e);
		}
	}

	public static int androidVersionToApiLevel(String v) {
		if (v.startsWith("7.1"))
			return 25;
		if (v.startsWith("7"))
			return 24;
		if (v.startsWith("6"))
			return 23;
		if (v.startsWith("5.1"))
			return 22;
		if (v.startsWith("5"))
			return 21;
		if (v.startsWith("4.4W"))
			return 20;
		if (v.startsWith("4.4"))
			return 19;
		if (v.startsWith("4.3"))
			return 18;
		if (v.startsWith("4.2"))
			return 17;
		if (v.startsWith("4.1"))
			return 16;
		if (v.startsWith("4.0.3") || v.startsWith("4.0.4"))
			return 15;
		if (v.startsWith("4"))
			return 14;
		return 23;
	}

	public static String getMD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte[] btInput = s.getBytes();
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			mdInst.update(btInput);
			byte[] md = mdInst.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}

	public static String ObfuscatedDecode(long[] paramArrayOfLong) {
		int j = paramArrayOfLong.length;
		byte[] localByteArray = new byte[(j - 1) * 8];
		Random localRandom = new Random(paramArrayOfLong[0]);
		int i = 1;
		while (i < j) {
			long l = localRandom.nextLong();
			toBytes(paramArrayOfLong[i] ^ l, localByteArray, (i - 1) * 8);
			i += 1;
		}
		try {
			String localString = new String(localByteArray, new String(new char[] { 85, 84, 70, 56 }));
			i = localString.indexOf(0);
			return localString;
		} catch (UnsupportedEncodingException e) {
			throw new AssertionError(e);
		}
	}

	public static byte[] gzip(byte[] data) {
		if ((data == null) || (data.length == 0)) {
			return null;
		}
		try {
			ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
			GZIPOutputStream localGZIPOutputStream = new GZIPOutputStream(localByteArrayOutputStream);
			localGZIPOutputStream.write(data);
			localGZIPOutputStream.close();
			return localByteArrayOutputStream.toByteArray();
		} catch (IOException e) {
			return null;
		}
	}

	public static String ungzip(byte[] b) throws IOException {
		if (b == null || b.length == 0) {
			return null;
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayInputStream in = new ByteArrayInputStream(b);

		GZIPInputStream gunzip = new GZIPInputStream(in);
		byte[] buffer = new byte[256];
		int n;
		while ((n = gunzip.read(buffer)) >= 0) {
			out.write(buffer, 0, n);
		}

		return out.toString();
	}

	public static byte[] ungzipByte(byte[] b) throws IOException {
		if (b == null || b.length == 0) {
			return null;
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayInputStream in = new ByteArrayInputStream(b);

		GZIPInputStream gunzip = new GZIPInputStream(in);
		byte[] buffer = new byte[256];
		int n;
		while ((n = gunzip.read(buffer)) >= 0) {
			out.write(buffer, 0, n);
		}

		return out.toByteArray();
	}

	public static byte[] unzip(byte[] b) throws IOException {
		if (b == null || b.length == 0) {
			return null;
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayInputStream in = new ByteArrayInputStream(b);

		ZipInputStream gunzip = new ZipInputStream(in);
		byte[] buffer = new byte[256];
		int n;
		while ((n = gunzip.read(buffer)) >= 0) {
			out.write(buffer, 0, n);
		}

		return out.toByteArray();
	}

	public static String paramListToString(Map<String, String> paramList) {
		String rel = "";
		Iterator<Entry<String, String>> iter = paramList.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, String> entry = (Entry<String, String>) iter.next();
			String key = entry.getKey();
			String val = entry.getValue();
			rel += key + "=" + val + "&";
		}
		if (!rel.equals(""))
			rel = rel.substring(0, rel.length() - 1);
		return rel;
	}

	public static String paramListToSortString(Map<String, String> paramList){
	        String[] keysArray = new String[paramList.size()];
	        paramList.keySet().toArray(((Object[])keysArray));
	        Arrays.sort(((Object[])keysArray));
	        StringBuilder stringBuilder = new StringBuilder();
	        int index = 0;
	        do {
	            String value = paramList.get(keysArray[index]);
	            if(value != null) {
	                stringBuilder.append(keysArray[index]).append("=").append(value).append("&");
	            }

	            ++index;
	        }
	        while(index < keysArray.length);

	        int len = stringBuilder.length();
	        if(len > 0 && stringBuilder.charAt(len - 1) == '&') {
	            stringBuilder.deleteCharAt(len - 1);
	        }

	        return stringBuilder.toString();
	}
	
	private static final void toBytes(long paramLong, byte[] paramArrayOfByte, int paramInt) {
		int i = Math.min(paramArrayOfByte.length, paramInt + 8);
		while (paramInt < i) {
			paramArrayOfByte[paramInt] = ((byte) (int) paramLong);
			paramLong >>= 8;
			paramInt += 1;
		}
	}

	public static PublicKey readPublicKeys(String filename) throws Exception {
		CertificateFactory factory = CertificateFactory.getInstance("X.509");
		InputStream in = Class.class.getResourceAsStream("/" + filename);
		java.security.cert.Certificate cate = factory.generateCertificate(in);
		return cate.getPublicKey();
	}
	
	public static byte[] encryptWithPublickey(String paramString, Key paramKey) {
		try {
			return Base64.encode(encrypt(paramString.getBytes(), paramKey));
		} catch (Exception e) {
		}
		return null;
	}
	
	public static byte[] encrypt(byte[] paramArrayOfByte, Key paramKey) throws Exception {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		if (paramArrayOfByte.length > 117) {
			ByteArrayOutputStream localObject = new ByteArrayOutputStream();
			byte[] arrayOfByte = new byte[117];
			Cipher localCipher = Cipher.getInstance("RSA/NONE/PKCS1Padding", "BC");
			localCipher.init(1, paramKey);
			int i = 0;
			while (i < paramArrayOfByte.length) {
				int k = paramArrayOfByte.length - i;
				int j = k;
				if (k > 117) {
					j = 117;
				}
				System.arraycopy(paramArrayOfByte, i, arrayOfByte, 0, j);
				((ByteArrayOutputStream) localObject).write(localCipher.doFinal(arrayOfByte, 0, j));
				i += 117;
			}
			return ((ByteArrayOutputStream) localObject).toByteArray();
		}
		Object localObject = Cipher.getInstance("RSA/NONE/PKCS1Padding", "BC");
		((Cipher) localObject).init(1, paramKey);
		return ((Cipher) localObject).doFinal(paramArrayOfByte);
	}
	
	public static byte[] decryptWithPublickey(String paramString,Key paramKey){
		try{
			return decrypt(Base64.decode(paramString),paramKey);
		}catch(Exception e){
			return null;
		}
	}
	
	public static byte[] decrypt(byte[] paramArrayOfByte,Key paramKey) throws Exception{
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		if(paramArrayOfByte.length > 128){
			ByteArrayOutputStream localObject = new ByteArrayOutputStream();
			byte[] arrayOfByte = new byte[128];
			Cipher localCipher = Cipher.getInstance("RSA/NONE/PKCS1Padding", "BC");
			localCipher.init(2, paramKey);
			int i = 0;
			while(i < paramArrayOfByte.length){
				int k = paramArrayOfByte.length - i;
				int j = k;
				if( k > 128) {
					j = 128;
				}
				System.arraycopy(paramArrayOfByte, i, arrayOfByte, 0, j);
				((ByteArrayOutputStream)localObject).write(localCipher.doFinal(arrayOfByte, 0, j));
				i += 128;
			}
			return ((ByteArrayOutputStream)localObject).toByteArray();
		}
		Object localObject = Cipher.getInstance("RSA/NONE/PKCS1Padding", "BC");
		((Cipher)localObject).init(2, paramKey);
		return ((Cipher)localObject).doFinal(paramArrayOfByte);
	}
	
	public static byte[] getSignature(byte[] data, byte[] key, String alg) {
		try {
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
			SecretKey keySpec = new SecretKeySpec(key, alg);
			Mac localMac = Mac.getInstance(alg);
			localMac.init(keySpec);
			localMac.update(data);
			return localMac.doFinal();
		} catch (Exception e) {
			return null;
		}
	}
	private static InputStream keyfile(){
		File keyhandler = new File("../../keyfile/publickey.crt");
		InputStream input = null;
		try {
			input = new FileInputStream(keyhandler);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return input;
	}
	public static PublicKey getpublickey(){
		InputStream input = keyfile();
		PublicKey publickey = null;
		try{
			publickey = CertificateFactory.getInstance("X.509").generateCertificate(input).getPublicKey();
			if(input == null){
				return publickey;
			}
		}catch(Exception e){
			if(input != null){
				try{
					input.close();
				}catch(Exception e1){
					e1.printStackTrace();
				}
			}
		}
		try{
			input.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return publickey;
	}
}