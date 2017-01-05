/*
 * shariff-backend-java
 *
 * Copyright (C) 2015 Richard "Shred" Körber
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 */
package org.shredzone.shariff.target;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Pinterest target.
 *
 * @author Richard "Shred" Körber
 */
public class Pinterest extends JSONTarget<JSONObject> {

    @Override
    public String getName() {
        return "pinterest";
    }

    @Override
    protected HttpURLConnection connect(String url) throws IOException {
        URL connectUrl = new URL("http://api.pinterest.com/v1/urls/count.json?callback=x&url="
                        + URLEncoder.encode(url, "utf-8"));

        return openConnection(connectUrl);
    }

    @Override
    protected JSONObject read(InputStream in) throws IOException {
        JSONTokener tokener = new JSONTokener(in);
        tokener.nextValue(); // Ignore function call
        return (JSONObject) tokener.nextValue();
    }

    @Override
    protected int extractCount(JSONObject json) {
        return json.getInt("count");
    }

}
