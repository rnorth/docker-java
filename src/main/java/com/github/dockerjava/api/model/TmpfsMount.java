package com.github.dockerjava.api.model;

import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * A {@link TmpfsMount} represents a directory that docker will mount with tmpfs. It is characterized by an
 * {@link #getPath()} directory path} and {@link #getMountOptions()} mount options}. Both properties are mandatory.
 *
 * @see TmpfsMount#bind(String, String)
 */
public class TmpfsMount {

    private final String path;
    private final String mountOptions;

    public static TmpfsMount bind(String path, String mountOptions) {
        return new TmpfsMount(path, mountOptions);
    }

    /**
     * Creates a {@link TmpfsMount} for the given {@link #getPath()} directory} with {@link #getMountOptions()} mount
     * options}.
     *
     * @see TmpfsMount#bind(String, String)
     */
    public TmpfsMount(String path, String mountOptions) {
        this.path = path;
        this.mountOptions = mountOptions;
    }

    /**
     * @return path of the directory to be mounted with tmpfs
     */
    public String getPath() {
        return path;
    }

    /**
     * @return mount options for the tmpfs volume
     */
    public String getMountOptions() {
        return mountOptions;
    }

    @Override
    public String toString() {
        return path + ":" + mountOptions;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TmpfsMount) {
            TmpfsMount other = (TmpfsMount) obj;
            return new EqualsBuilder()
                    .append(path, other.path)
                    .append(mountOptions, other.mountOptions)
                    .isEquals();
        } else {
            return super.equals(obj);
        }
    }

    @Override
    public int hashCode() {
        int result = path.hashCode();
        result = 31 * result + mountOptions.hashCode();
        return result;
    }
}
