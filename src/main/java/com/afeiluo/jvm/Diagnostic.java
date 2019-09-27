package com.afeiluo.jvm;

import com.sun.management.VMOption;
import sun.management.HotSpotDiagnostic;

import java.util.List;

/**
 * 通过Java代码调用该API得到所有可动态修改的JVM参数
 *
 * @author ben
 * @date 2019/9/19
 */
public class Diagnostic {
    public static void main(String[] args) {
        HotSpotDiagnostic mxBean = new HotSpotDiagnostic();
        List<VMOption> diagnosticVMOptions = mxBean.getDiagnosticOptions();
        for (VMOption vmOption : diagnosticVMOptions) {
            System.out.println(vmOption.getName() + " = " + vmOption.getValue());
        }
    }
}
