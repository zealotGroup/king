package group.zealot.king.demo.api.config;

public class ResultJsonFactory {
    private static final ResultJson resultJson = new ResultJson();

    public static ResultJson create() {
        return resultJson.clone();
    }
}
