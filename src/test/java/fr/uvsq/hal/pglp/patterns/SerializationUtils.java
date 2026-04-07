package fr.uvsq.hal.pglp.patterns;

import java.io.*;

/**
 * La classe <code>SerializationUtils</code> fournit des méthodes utilitaires pour la sérialisation.
 *
 * @author hal
 * @version 2022
 */
public class SerializationUtils {
  public static <T extends Serializable> byte[] serialize(T obj) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ObjectOutputStream oos = new ObjectOutputStream(baos);
    oos.writeObject(obj);
    oos.close();
    return baos.toByteArray();
  }

  public static <T extends Serializable> T deserialize(byte[] b, Class<T> cl) throws IOException, ClassNotFoundException {
    ByteArrayInputStream bais = new ByteArrayInputStream(b);
    ObjectInputStream ois = new ObjectInputStream(bais);
    Object o = ois.readObject();
    return cl.cast(o);
  }
}
