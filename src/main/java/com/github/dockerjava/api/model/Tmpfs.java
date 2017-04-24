package com.github.dockerjava.api.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.Map.Entry;

/**
 * A container for tmpfs mounts, made available as a {@link Map} via its {@link #getMounts()} method.
 *
 * @see HostConfig#getTmpfs()
 */
@SuppressWarnings(value = "checkstyle:equalshashcode")
@JsonDeserialize(using = Tmpfs.Deserializer.class)
@JsonSerialize(using = Tmpfs.Serializer.class)
public class Tmpfs implements Serializable {
    private static final long serialVersionUID = 1L;

    private final List<TmpfsMount> mounts = new ArrayList<>();

    /**
     * Creates a {@link Tmpfs} object with no {@link TmpfsMount}s. Use {@link #add(TmpfsMount)} or {@link #add(TmpfsMount...)}
     * to add {@link TmpfsMount}s.
     */
    public Tmpfs() {
    }

    /**
     * Creates a {@link Tmpfs} object with initial {@link TmpfsMount}s. Use
     * {@link #add(TmpfsMount)} or {@link #add(TmpfsMount...)} to add more {@link TmpfsMount}s.
     */
    public Tmpfs(final TmpfsMount[] tmpfsMounts) {
        this.mounts.addAll(Arrays.asList(tmpfsMounts));
    }

    /**
     * Adds a new {@link TmpfsMount} to the current mounts.
     */
    public void add(TmpfsMount... tmpfsMounts) {
        for (TmpfsMount tmpfsMount : tmpfsMounts) {
            add(tmpfsMount);
        }
    }

    /**
     * Adds a new {@link TmpfsMount} to the current mounts.
     */
    public void add(TmpfsMount tmpfsMount) {
        this.mounts.add(tmpfsMount);
    }

    @Override
    public String toString() {
        return mounts.toString();
    }

    /**
     * Returns the port bindings in the format used by the Docker remote API, i.e. the {@link TmpfsMount}s grouped by {@link ExposedPort}.
     *
     * @return the port bindings as a {@link Map} that contains one or more {@link TmpfsMount}s per {@link ExposedPort}.
     */
    public List<TmpfsMount> getMounts() {
        return mounts;
    }


    public static class Deserializer extends JsonDeserializer<Tmpfs> {
        @Override
        public Tmpfs deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
                throws IOException {

            Tmpfs out = new Tmpfs();
            ObjectCodec oc = jsonParser.getCodec();
            JsonNode node = oc.readTree(jsonParser);
            for (Iterator<Entry<String, JsonNode>> it = node.fields(); it.hasNext(); ) {
                Entry<String, JsonNode> mountNode = it.next();
                out.add(new TmpfsMount(mountNode.getKey(), mountNode.getValue().asText()));
            }
            return out;
        }
    }

    public static class Serializer extends JsonSerializer<Tmpfs> {

        @Override
        public void serialize(Tmpfs tmpfs, JsonGenerator jsonGen, SerializerProvider serProvider)
                throws IOException {

            jsonGen.writeStartObject();
            for (TmpfsMount mount : tmpfs.getMounts()) {
                jsonGen.writeStringField(mount.getPath(), mount.getMountOptions());
            }
            jsonGen.writeEndObject();
        }

    }

}
