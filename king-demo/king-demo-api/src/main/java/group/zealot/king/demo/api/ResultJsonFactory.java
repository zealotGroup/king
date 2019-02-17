package group.zealot.king.demo.api;

public class ResultJsonFactory {
    private static final ResultJson resultJson = new ResultJson();

    public static ResultJson create() {
        return resultJson.clone();
    }
}
