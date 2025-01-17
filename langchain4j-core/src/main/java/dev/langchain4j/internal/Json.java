package dev.langchain4j.internal;

import dev.langchain4j.spi.json.JsonCodecFactory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

import static dev.langchain4j.spi.ServiceHelper.loadFactories;

/**
 * A utility class for JSON.
 *
 * @deprecated Do not use {@link Json} from third-party modules.
 * If you need to serialize/deserialize JSON in third-party modules, use Jackson's ObjectMapper.
 * {@link Json} can be used only by {@code langchain4j-core} and {@code langchain4j} modules.
 */
@Deprecated
public class Json {
    private Json() {
    }

    /**
     * The abstract JSON codec interface.
     */
    public interface JsonCodec {

        /**
         * Convert the given object to JSON.
         *
         * @param o the object to convert.
         * @return the JSON string.
         */
        String toJson(Object o);

        /**
         * Convert the given JSON string to an object of the given class.
         *
         * @param json the JSON string.
         * @param type the class of the object.
         * @param <T>  the type of the object.
         * @return the object.
         */
        <T> T fromJson(String json, Class<T> type);

        /**
         * Convert the given JSON string to an object of the given type.
         *
         * @param json the JSON string.
         * @param type the type of the object.
         * @param <T>  the type of the object.
         * @return the object.
         */
        <T> T fromJson(String json, Type type);

        /**
         * Convert the given object to an {@link InputStream}.
         *
         * @param o    the object to convert.
         * @param type the type of the object.
         * @return the {@link InputStream}.
         * @throws IOException if an I/O error occurs.
         */
        InputStream toInputStream(Object o, Class<?> type) throws IOException;
    }

    private static final JsonCodec CODEC = loadCodec();

    private static JsonCodec loadCodec() {
        for (JsonCodecFactory factory : loadFactories(JsonCodecFactory.class)) {
            return factory.create();
        }
        return new GsonJsonCodec();
    }

    /**
     * Convert the given object to JSON.
     *
     * @param o the object to convert.
     * @return the JSON string.
     * @deprecated use Jackson's ObjectMapper
     */
    @Deprecated
    public static String toJson(Object o) {
        return CODEC.toJson(o);
    }

    /**
     * Convert the given JSON string to an object of the given class.
     *
     * @param json the JSON string.
     * @param type the class of the object.
     * @param <T>  the type of the object.
     * @return the object.
     * @deprecated use Jackson's ObjectMapper
     */
    @Deprecated
    public static <T> T fromJson(String json, Class<T> type) {
        return CODEC.fromJson(json, type);
    }

    /**
     * Convert the given JSON string to an object of the given type.
     *
     * @param json the JSON string.
     * @param type the type of the object.
     * @param <T>  the type of the object.
     * @return the object.
     * @deprecated use Jackson's ObjectMapper
     */
    @Deprecated
    public static <T> T fromJson(String json, Type type) {
        return CODEC.fromJson(json, type);
    }
    /**
     * Convert the given object to an {@link InputStream}.
     *
     * @param o    the object to convert.
     * @param type the type of the object.
     * @return the {@link InputStream}.
     * @throws IOException if an I/O error occurs.
     * @deprecated use Jackson's ObjectMapper
     */
    @Deprecated
    public static InputStream toInputStream(Object o, Class<?> type) throws IOException {
        return CODEC.toInputStream(o, type);
    }
}
