package com.mbhonsle.batch_resttemplate_sample;

import java.util.ArrayList;
import java.util.List;

public class Data {

    public static List<String> getList() {
        return new ArrayList<String>() {{
            add("https://api.github.com/users/facebook");
            add("https://api.github.com/users/linkedin");
            add("https://api.github.com/users/google");
            add("https://api.github.com/users/square");
        }};
    }

}
