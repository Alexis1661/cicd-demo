// DemoInsecureCode.java - codigo con deuda tecnica intencional
package au.com.equifax.cicddemo;

public class DemoInsecureCode {
    // SonarQube detectara estos problemas
    private String password = "admin123";  // hardcoded credential
    
    public void riskyMethod() {
        try {
            int[] arr = new int[5];
            arr[10] = 1; // array out of bounds
        } catch (Exception e) {
            // empty catch block - bad practice
        }
    }
}
