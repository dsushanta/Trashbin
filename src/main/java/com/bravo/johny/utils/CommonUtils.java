package com.bravo.johny.utils;

import com.google.common.io.Resources;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import java.io.File;
import java.net.URI;

public class CommonUtils {

    public static URI getURISelf(@Context UriInfo uriInfo, String id, Object object) {

        return uriInfo.getBaseUriBuilder()
                .path(object.getClass())
                .path(id)
                .build();
    }

    public static File getResourceAsFileObject(Object object, String resourceFilename) {
        File file = new File(
                Resources
                        .getResource(resourceFilename)
                        .getFile()
        );

        /*String s = CommonUtils.class.getClassLoader().getResource(resourceFilename).getFile();
        File file = new File(s);*/

        return file;
    }

    public static String getResourceFullPath(Object object, String resourceFilename) {
        return object.getClass()
                .getClassLoader()
                .getResource(resourceFilename)
                .getFile();
    }
}
