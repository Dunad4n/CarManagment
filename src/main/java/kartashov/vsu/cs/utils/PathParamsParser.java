package kartashov.vsu.cs.utils;

import kartashov.vsu.cs.annotations.Component;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
@Component
public class PathParamsParser {

    public Map<String, Long> getParams(String path) {
        Map<String, Long> paramsMap = new HashMap<>();
        for (int i = 0; i < path.length(); i++) {
            if (path.charAt(i) == '?') {
                path = path.substring(0, i + 1);
                break;
            }
        }
        int start = 0;
        for (int i = 0; i < path.length(); i++) {
            if (path.charAt(i) == '&' || i == path.length() - 1) {
                String[] params = path.substring(start, i + 1).split("=");
                paramsMap.put(params[0], Long.parseLong(params[1]));
                start = i + 1;
            }
        }
        return paramsMap;
    }

}
