package client.model;

import com.beust.jcommander.Parameter;

public class Model {

    private transient final String[] args;

    @Parameter(names = "-in")
    private String in;

    @Parameter(names = "-t")
    private String type;

    @Parameter(names = "-k")
    private String key;

    @Parameter(names = "-v")
    private String value;

    public Model(String[] args) {

        this.args = args;

    }

    public String[] getArgs() {

        return args;

    }

    public String getIn() {

        return in;

    }

    public String getType() {

        return type;

    }

}
