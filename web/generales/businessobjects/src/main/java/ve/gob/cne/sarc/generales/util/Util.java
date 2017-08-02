package ve.gob.cne.sarc.generales.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.ssl.TrustStrategy;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Util {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Util.class);
	
	static{
		
		TrustStrategy acceptingTrustStrategy = new TrustStrategy() {

            @Override
            public boolean isTrusted(java.security.cert.X509Certificate[] xcs, String string)
                    throws CertificateException {           	
                return true;
            }
        };

        SSLContext sslContext = null;
        try {
            sslContext = org.apache.http.ssl.SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy)
                    .build();
        } catch (NoSuchAlgorithmException ex) {
            LOGGER.error("NoSuchAlgorithmException", ex);
        } catch (KeyStoreException ex) {
            LOGGER.error("KeyStoreException", ex);
        } catch (KeyManagementException ex) {
            LOGGER.error("KeyManagementException", ex);
        }
		HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
		HttpsURLConnection.setDefaultHostnameVerifier(new NoopHostnameVerifier() {
		});
	}
	
	
	
	
	
	public static Map<String, Object> introspect(Object obj) throws Exception {
	    Map<String, Object> result = new HashMap<String, Object>();
	    BeanInfo info = Introspector.getBeanInfo(obj.getClass());
	    for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
	        Method reader = pd.getReadMethod();
	        if (reader != null)
	            result.put(pd.getName(), reader.invoke(obj));
	    }
	    return result;
	}
	/**
	 * <p>Obtiene el recurso solicitado conectando al modulo web y transmitiendo el token de sesion</p>
	 * @param token
	 * @param data
	 * @param url
	 * @return
	 */
	public static String obtenerRecurso(String token, Map<String,String> data, String url){
		StringBuffer html = new StringBuffer();
		try {

			URL obj = new URL(url);
			HttpsURLConnection conn = (HttpsURLConnection) obj.openConnection();
			conn.setRequestProperty ("Authorization", token);
			ObjectMapper mapper=new ObjectMapper();
			if(data !=null)
				conn.setRequestProperty("datos", mapper.writeValueAsString(data));
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("Accept-Charset", "iso-8859-1");
			conn.setUseCaches(false);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				html.append(inputLine);
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return html.toString();
	}
	
	/**
	 * <p>Obtiene el recurso solicitado conectando al modulo web y transmitiendo el token de sesion</p>
	 * @param token
	 * @param data
	 * @param url
	 * @return
	 */
	public static String obtenerRecurso(String token, Map<String,String> data, URL url){
		StringBuffer html = new StringBuffer();
		try {

			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setRequestProperty ("Authorization", token);
			ObjectMapper mapper=new ObjectMapper();
			if(data !=null)
				conn.setRequestProperty("datos", mapper.writeValueAsString(data));
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("Accept-Charset", "iso-8859-1");
			conn.setUseCaches(false);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				html.append(inputLine);
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return html.toString();
	}
	

	public static String obtenerRecurso(String token, String data, String url){
		StringBuffer html = new StringBuffer();
		try {

			URL obj = new URL(url);
						
			HttpsURLConnection conn = (HttpsURLConnection) obj.openConnection();
			conn.setRequestProperty ("Authorization", token);
			if(data != null)
				conn.setRequestProperty("datos", data);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("Accept-Charset", "iso-8859-1");
			conn.setUseCaches(false);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				html.append(inputLine);
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return html.toString();
	}
	
		public static String obtenerRecursoPorMetodo(String token, String setData, Map<String, String> data, String url,String metodo){
        StringBuffer html = new StringBuffer();
        try {

            URL obj = new URL(url);
                        
            HttpsURLConnection conn = (HttpsURLConnection) obj.openConnection();
            conn.setRequestProperty ("Authorization", token);
            ObjectMapper mapper = new ObjectMapper();
            if(data != null)
                conn.setRequestProperty(setData,mapper.writeValueAsString(data));
            conn.setRequestMethod(metodo);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Accept-Charset", "iso-8859-1");
            conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                html.append(inputLine);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return html.toString();
    }

	/**
	 * <p>Lee un archivo cuya ruta se pasa como par&aacute;metro</p>
	 * @param ruta
	 * @return
	 * @throws IOException
	 */
	public static String leerArchivo(String ruta) throws IOException{
		String stringObject=null;
		File file=new File(ruta);
		try {
			FileInputStream fis=new FileInputStream(file);
			BufferedReader bfr = new BufferedReader(new InputStreamReader(fis));
			String inputLine;
			StringBuffer html = new StringBuffer();
			while ((inputLine = bfr.readLine()) != null) {
				html.append(inputLine);
			}
			bfr.close();
			stringObject=html.toString();
		}catch(IOException e){
			throw e;
		}
		return stringObject;
	}
    public static Properties obtenerPropiedad(String endPointClave) {
    	Properties props=null;
        File configFile = new File(endPointClave);
        try {
            FileReader reader = new FileReader(configFile);
            props = new Properties();
            props.load(reader);
            reader.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return props;
    }
	
	public static String conectarRutaUrl(String url){
		StringBuffer html = null;
		try {
			URL obj =  new URL(url);
			HttpsURLConnection conn = (HttpsURLConnection) obj.openConnection();		
			BufferedReader in = new BufferedReader( new InputStreamReader(conn.getInputStream()));
			String inputLine;
	        html = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				html.append(inputLine);
			}
			in.close();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return html.toString();
	}
    
}
