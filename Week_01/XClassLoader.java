package first;

import java.io.*;

/**
 * @author kaithy.xu
 * @date 2020-10-18 10:59
 */
public class XClassLoader extends ClassLoader {
    String PATH = "/Users/xukaixi/Downloads/Hello/";

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] originalCode = null;
        
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(new File(PATH,name+".xlass")))){
            byte[] byteCodes = bis.readAllBytes();
            originalCode = new byte[byteCodes.length];
            for (int i = 0; i < byteCodes.length; i++) {
                originalCode[i] = (byte) (255 - (int)byteCodes[i]);
            }

        } catch (IOException e) {
            e.printStackTrace();
            throw new IOError(e);
        }

        return defineClass(name, originalCode, 0, originalCode.length);;
    }

    public static void main(String[] args) throws Exception {
        Class clazz = new XClassLoader().findClass("Hello");

        clazz.getDeclaredMethod("hello").invoke(clazz.getDeclaredConstructor().newInstance());
    }
}
