package org.vertexarmy.dsr.core;

import com.google.gson.Gson;
import org.nustaq.serialization.FSTObjectInput;
import org.nustaq.serialization.FSTObjectOutput;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * Created by alex
 * on 27.03.2015.
 */
public class Serialization {
    public static <T> void serialize(OutputStream stream, T object) throws IOException, ClassNotFoundException {
        GsonSerialize(stream, object);
    }

    public static <T> T deserialize(InputStream inputStream, Class objectClass) throws IOException, ClassNotFoundException {
        return GsonDeserialize(inputStream, objectClass);
    }

    private static <T> T FSTDeserialize(InputStream inputStream) throws IOException, ClassNotFoundException {
        FSTObjectInput in = new FSTObjectInput(inputStream);
        T result = (T) in.readObject();
        in.close();
        return result;
    }

    private static <T> void FSTSerialize(OutputStream stream, T object) throws IOException {
        FSTObjectOutput out = new FSTObjectOutput(stream);
        out.writeObject(object);
        out.close();
    }

    private static <T> void GsonSerialize(OutputStream stream, T object) {
        Gson gson = new Gson();
        String result = gson.toJson(object);
        try {
            stream.write(result.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static <T> T GsonDeserialize(InputStream inputStream, Class objectClass) {
        Gson gson = new Gson();
        return (T) gson.fromJson(new InputStreamReader(inputStream), objectClass);
    }
}
