package top.ashun.myspringmvc.core;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author 18483
 */
@Data
@AllArgsConstructor
public class Handler {

    private Object object;

    private String methodName;

}
