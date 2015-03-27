package org.vertexarmy.dsr.core;

import org.nustaq.serialization.FSTObjectInput;
import org.nustaq.serialization.FSTObjectOutput;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by alex
 * on 27.03.2015.
 */
public class Serialization {
    public static <T> void serialize(OutputStream stream, T object) throws IOException, ClassNotFoundException {
        FSTObjectOutput out = new FSTObjectOutput(stream);
        out.writeObject(object);
        out.close();
    }

    public static <T> T deserialize(InputStream inputStream) throws IOException, ClassNotFoundException {
        FSTObjectInput in = new FSTObjectInput(inputStream);
        T result = (T) in.readObject();
        in.close();
        return result;
    }
}
